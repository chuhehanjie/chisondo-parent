package com.chisondo.iot.device.handler;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.iot.common.redis.RedisUtils;
import com.chisondo.iot.common.utils.HttpUtils;
import com.chisondo.iot.common.utils.IOTUtils;
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
    private HttpUtils httpUtils;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * A thread-safe Set  Using ChannelGroup, you can categorize Channels into a meaningful group.
     * A closed Channel is automatically removed from the collection,
     */
    public static ChannelGroup globalDevChannels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

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
        // globalDevChannels.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " 加入,总人数:" + globalDevChannels.size() + "\n");

        globalDevChannels.add(deviceChannel);

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
        // 同时更新设备状态
        this.updateDevState2Redis(resp.getMsg(), resp.getDeviceID(), false);
        // TODO 不需要发送设备状态到 HTTP update 20190705
        //this.httpUtils.sendDevState2Http(resp.getDeviceID(), false);
        this.sendTCPResp2Http(resp, resp.getDeviceID());
    }



    /**
     * 更新设备状态到 redis
     * @param devMsg
     * @param deviceId
     */
    private void updateDevState2Redis(DevStatusMsgResp devMsg, String deviceId, boolean newCreate) {
        DevStatusRespDTO devStatusResp = this.redisUtils.get(deviceId, DevStatusRespDTO.class);
        if (null == devStatusResp) {
            if (newCreate) {
                devStatusResp = new DevStatusRespDTO();
                devStatusResp.setDeviceId(deviceId);
                devStatusResp.setOnlineStatus(1);
            } else {
                log.error("设备[{}]状态信息在 redis 中不存在！", deviceId);
                return;
            }
        }
        if (null != devMsg) {
            if (null != devMsg.getTemperature()) {
                devStatusResp.setTemp(devMsg.getTemperature());
            }
            if (null != devMsg.getChapuId()) {
                devStatusResp.setChapuId(devMsg.getChapuId());
            }
            if (null != devMsg.getStep()) {
                devStatusResp.setIndex(devMsg.getStep());
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
            // 需要将 remain 时间多加 2 秒，因为设备已经在倒计时了，而服务端会有延时
            devStatusResp.setReamin(this.getWorkRemainTime(devMsg.getRemaintime()));
            devStatusResp.setTea(2 == devMsg.getErrorstatus() ? 1 : 0);
            devStatusResp.setWater(1 == devMsg.getErrorstatus() ? 1 : 0);
            devStatusResp.setWork(devMsg.getWorkstatus());
        }
        this.redisUtils.set(devStatusResp.getDeviceId(), devStatusResp);
    }

    private Integer getWorkRemainTime(Integer remainTime) {
        return StringUtils.isEmpty(remainTime) ? null : (remainTime > 0 ? remainTime + 2 : 0);
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
                log.error("通道不相同，需要重新覆盖, 原 IP = {}, 新 IP = {}", existedDevChannel.remoteAddress(), deviceChannel.remoteAddress());
                DevTcpChannelManager.addDeviceChannel(deviceId, deviceChannel);
            }
        }
        reportResp.setTcpValTime(new Date());
        // 设置连接设备的客户端IP
        reportResp.setClientIP(this.convertClientIP(deviceChannel.remoteAddress().toString()));
        this.updateDevState2Redis(reportResp.getMsg(), deviceId, true);
        this.httpUtils.reportDevStatus2App(reportResp);
    }

    private String convertClientIP(String clientIP) {
        return clientIP.replace("/", "");
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception { // (5)
        log.info("设备激活， IP = {}", ctx.channel().remoteAddress());
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

                        globalDevChannels.add(incoming);
                    }
                });
        log.debug("SimpleChatClient:" + incoming.remoteAddress() + "在线,总人数:" + globalDevChannels.size() + "\n");*/
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
        this.doOffLineAction(ctx);
    }

    /**
     * 离线操作
     * @param ctx
     */
    private void doOffLineAction(ChannelHandlerContext ctx) {
        Channel deviceChannel = ctx.channel();
        String deviceId = DevTcpChannelManager.removeByChannel(deviceChannel);
        ctx.close();
        globalDevChannels.remove(deviceChannel);
        if (StringUtils.isEmpty(deviceId)) {
            log.error("非设备通道");
            return;
        }
        DevHttpChannelManager.removeByDeviceId(deviceId);
        this.redisUtils.updateStatus4Dev(deviceId);
        log.error("设备[{}]离线，从 redis 中更新状态并发送离线请求到 HTTP, 当前连接总数 = {}", deviceId, DevTcpChannelManager.count());
        this.httpUtils.sendDevState2Http(deviceId, true);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Channel deviceChannel = ctx.channel();
        // TODO 是否需要写异常日志？
        // 当出现异常就关闭连接
        log.error("设备[{}]发生异常，总设备数 = {} ", deviceChannel.remoteAddress(), globalDevChannels.size(), cause);
        this.doOffLineAction(ctx);
    }
}