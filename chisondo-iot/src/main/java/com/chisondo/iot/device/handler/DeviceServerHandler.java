package com.chisondo.iot.device.handler;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.iot.common.redis.RedisUtils;
import com.chisondo.iot.common.utils.IOTUtils;
import com.chisondo.iot.common.utils.RestTemplateUtils;
import com.chisondo.iot.device.server.DevTcpChannelManager;
import com.chisondo.iot.http.server.DevHttpChannelManager;
import com.chisondo.model.http.resp.*;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;


/**
 * 设备服务 hanlder
 * @author ding.zhong
 */
@Slf4j
@Component
@Qualifier("deviceServerHandler")
@ChannelHandler.Sharable
public class DeviceServerHandler extends SimpleChannelInboundHandler<Object> { // (1)

    @Autowired
    private RestTemplateUtils restTemplateUtils;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * A thread-safe Set  Using ChannelGroup, you can categorize Channels into a meaningful group.
     * A closed Channel is automatically removed from the collection,
     */
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    //    private ExecutorService executorService = Executors.newFixedThreadPool(20);
    @Autowired
    org.springframework.core.env.Environment env;

    @Autowired
    @Qualifier("bipool")
    private ExecutorService executorService;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
        Channel deviceChannel = ctx.channel();

        log.info("设备[{}]加入", deviceChannel.remoteAddress());
        // Broadcast a message to multiple Channels
        // channels.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " 加入,总人数:" + channels.size() + "\n");

        channels.add(deviceChannel);

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel deviceChannel = ctx.channel();
        log.error("移除设备[{}]通道", deviceChannel.remoteAddress());
    }


    /**
     * @param ctx
     * @param
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel deviceChannel = ctx.channel();

        log.info("读取设备[{}]发送的消息:{}", deviceChannel.remoteAddress(), msg);

        /*if (msg instanceof DeviceHttpReq) {
            DeviceHttpReq req = (DeviceHttpReq) msg;
            // 向设备发送请求
            deviceChannel.writeAndFlush(JSONObject.toJSONString(req));
        }*/
        if (msg instanceof DeviceHttpResp) {
            // 处理设备控制响应
            this.processDevCtrlResp(deviceChannel, msg);
        } else if (msg instanceof DevStatusReportResp) {
            // 处理设备状态上报响应
            this.processDevStatusReport(deviceChannel, msg);
        } else if (msg instanceof DevSettingHttpResp) {
            // 处理查询设备内置参数响应
            this.processQryDevSetParamResp(deviceChannel, msg);
        } else if (msg instanceof DevChapuHttpResp) {
            // 处理查询设备茶谱参数响应
            this.processQryDevChapuInfoResp(deviceChannel, msg);
        } else {
            log.error("未找到对应的TCP响应");
        }
    }

    /**
     * 处理设备控制响应
     * @param deviceChannel
     * @param msg
     */
    private void processDevCtrlResp(Channel deviceChannel, Object msg) {
        // DeviceHttpResp 包含启动设备沏茶/洗茶/烧水响应
        // 接收设备发送的响应，并将响应发送到 http server
        DeviceHttpResp resp = (DeviceHttpResp) msg;
        log.debug("设备控制响应信息 = {}", JSONObject.toJSONString(resp));
        this.sendTCPResp2Http(resp, resp.getDeviceID());
        // 同时更新设备状态
        this.updateDevState2Redis(resp);
        this.sendDevState2Http(resp.getDeviceID(), false);
    }

    private void sendDevState2Http(String deviceId, boolean isOffline) {
        try {
            String  suffix = isOffline ? "setDevStateOffline" : "updateDevStateFromRedis";
            Map<String, Object> params = new HashMap<>();
            params.put("deviceId", deviceId);
            String result = this.restTemplateUtils.httpPostMediaTypeJson(this.restTemplateUtils.appHttpURL + "/api/rest/" + suffix, String.class, params);
            log.error("result = {}", result);
        } catch (Exception e) {
            log.error("更新设备状态信息失败！", e);
        }
    }

    /**
     * 更新设备状态到 redis
     * @param resp
     */
    private void updateDevState2Redis(DeviceHttpResp resp) {
        DevStatusRespDTO devStatusResp = this.redisUtils.get(resp.getDeviceID(), DevStatusRespDTO.class);
        if (null == devStatusResp) {
            log.error("设备[{}]状态信息在 redis 中不存在！", resp.getDeviceID());
            return;
        }
        if (null != resp.getMsg()) {
            DeviceMsgResp devMsg = resp.getMsg();
            if (null != devMsg.getTemperature()) {
                devStatusResp.setTemp(devMsg.getTemperature());
            }
            if (null != devMsg.getWarmstatus()) {
                devStatusResp.setWarm(devMsg.getWarmstatus());
            }
            if (null != devMsg.getTaststatus()) {
                devStatusResp.setDensity(devMsg.getTaststatus());
            }
            if (null != devMsg.getWaterlevel()) {
                devStatusResp.setWaterlv(devMsg.getWaterlevel());
            }
            if (null != devMsg.getSoak()) {
                devStatusResp.setMakeDura(devMsg.getSoak());
            }
            devStatusResp.setReamin(StringUtils.isEmpty(devMsg.getRemaintime()) ? null : Integer.valueOf(devMsg.getRemaintime()));
            devStatusResp.setTea(2 == devMsg.getErrorstatus() ? 1 : 0);
            devStatusResp.setWater(1 == devMsg.getErrorstatus() ? 1 : 0);
            if (null != devMsg.getWorkstatus()) {
                devStatusResp.setWork(devMsg.getWorkstatus());
            }
        }
        this.redisUtils.set(devStatusResp.getDeviceId(), devStatusResp);
    }

    private void sendTCPResp2Http(Object resp, String deviceId) {
        Channel httpChannel = DevHttpChannelManager.getHttpChannelByDeviceId(deviceId);
        if (null != httpChannel) {
            FullHttpResponse response = IOTUtils.buildResponse(resp);
            httpChannel.writeAndFlush(response).addListener((obj) -> {
                ChannelFuture future = (ChannelFuture) obj;
                DevHttpChannelManager.removeHttpChannel(deviceId, future.channel());
                future.channel().close();
            });
        } else {
            log.error("未找到对应的 http channel");
        }
    }

    /**
     * 处理查询设备设置内置参数响应
     * @param deviceChannel
     * @param msg
     */
    @Deprecated
    private void processQryDevSetParamResp(Channel deviceChannel, Object msg) {
        // DeviceHttpResp 包含启动设备沏茶/洗茶/烧水响应
        // 接收设备发送的响应，并将响应发送到 http server
        DevSettingHttpResp resp = (DevSettingHttpResp) msg;
        log.debug("查询设备设置内置参数 = {}", JSONObject.toJSONString(resp));
        this.sendTCPResp2Http(resp, resp.getDeviceID());
    }

    /**
     * 处理查询设备茶谱信息响应
     * @param deviceChannel
     * @param msg
     */
    private void processQryDevChapuInfoResp(Channel deviceChannel, Object msg) {
        // 接收设备发送的响应，并将响应发送到 http server
        DevChapuHttpResp resp = (DevChapuHttpResp) msg;
        log.debug("查询设备茶谱信息 = {}", JSONObject.toJSONString(resp));
        this.sendTCPResp2Http(resp, resp.getDeviceID());
    }

    /**
     * 处理设备状态上报
     * @param deviceChannel
     * @param msg
     */
    private void processDevStatusReport(Channel deviceChannel, Object msg) {
        // 设备心跳上报请求
        DevStatusReportResp reportResp = (DevStatusReportResp) msg;
        log.debug("接收设备[{}]状态上报请求!", reportResp.getDeviceID());
        String deviceId = reportResp.getDeviceID();
        if (null == DevTcpChannelManager.getChannelByDeviceId(deviceId)) {
            DevTcpChannelManager.addDeviceChannel(deviceId, deviceChannel);
        } else {
            Channel existedDevChannel = DevTcpChannelManager.getChannelByDeviceId(deviceId);
            if (!ObjectUtils.nullSafeEquals(existedDevChannel, deviceChannel)) {
                existedDevChannel.close();
                log.error("通道不相同，需要重新覆盖");
                DevTcpChannelManager.addDeviceChannel(deviceId, deviceChannel);
            }
        }
        reportResp.setTcpValTime(new Date());
        // 设置连接设备的客户端IP
        reportResp.setClientIP(this.convertClientIP(deviceChannel.remoteAddress().toString()));
        // TODO 更新 redis 中设备状态
        //this.redisClient.opsForValue().set(reportResp.getDeviceID(), JSONObject.toJSONString(reportResp), 60*20, TimeUnit.SECONDS);
        this.reportDevStatus2App(reportResp);
    }

    private String convertClientIP(String clientIP) {
        return clientIP.replace("/", "");
    }

    private void reportDevStatus2App(DevStatusReportResp reportReq) {
        // 发送请求到 HTTP 应用，更新设备状态
        try {
            String result = this.restTemplateUtils.httpPostMediaTypeJson(this.restTemplateUtils.appHttpURL + "/api/rest/currentState", String.class, reportReq);
            log.error("result = {}", result);
        } catch (Exception e) {
            log.error("上报设备状态信息失败！", e);
        }
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception { // (5)
        log.info("channelActive channel = {}", ctx.channel().remoteAddress());
        /*final Channel incoming = ctx.channel();

        ctx.pipeline().get(SslHandler.class).handshakeFuture().addListener(
                new GenericFutureListener<Future<Channel>>() {
                    public void operationComplete(Future<Channel> future) t
                    @Overridehrows Exception {
                        incoming.writeAndFlush(
                                "Welcome to " + InetAddress.getLocalHost().getHostName() + " secure chat service!\n");
                        incoming.writeAndFlush(
                                "Your session is protected by " +
                                        ctx.pipeline().get(SslHandler.class).engine().getSession().getCipherSuite() +
                                        " cipher suite.\n");

                        channels.add(incoming);
                    }
                });
        log.debug("SimpleChatClient:" + incoming.remoteAddress() + "在线,总人数:" + channels.size() + "\n");*/
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
        Channel deviceChannel = ctx.channel();
        String deviceId = DevTcpChannelManager.removeByChannel(deviceChannel);
        log.error("设备[" + deviceId + "]掉线, 当前连接总数 = " + DevTcpChannelManager.count() + "\n");
        DevHttpChannelManager.removeByDeviceId(deviceId);
        channels.remove(deviceChannel);
        ctx.close();
        this.redisUtils.updateStatus4Dev(deviceId);
        log.error("设备[{}]离线，从 redis 中更新状态", deviceId);
        this.sendDevState2Http(deviceId, true);
        log.error("发送离线请求到HTTP");
//        DeviceChannelManager.removeDeviceChannel();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Channel incoming = ctx.channel();
        // TODO 是否需要写异常日志？
        log.error("SimpleChatClient:" + incoming.remoteAddress() + "异常, 总人数:" + channels.size() + "\n");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}