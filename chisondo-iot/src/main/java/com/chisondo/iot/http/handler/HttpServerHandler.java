package com.chisondo.iot.http.handler;


import com.chisondo.iot.common.utils.IOTUtils;
import com.chisondo.iot.common.utils.SpringContextUtils;
import com.chisondo.iot.device.server.DevTcpChannelManager;
import com.chisondo.iot.http.server.DevHttpChannelManager;
import com.chisondo.model.http.HttpStatus;
import com.chisondo.model.http.resp.DeviceHttpResp;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.timeout.ReadTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


/**
 * HTTP服务 hanlder
 * @author ding.zhong
 */
@Slf4j
@Component
@Qualifier("httpServerHandler")
@ChannelHandler.Sharable
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> { // (1)

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
        Channel httpChannel = ctx.channel();
        log.info("加入 HTTP 通道 = {}", httpChannel.remoteAddress());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
    }


    /**
     * @param ctx
     * @param
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        // TODO http 路由处理 https://blog.csdn.net/joeyon1985/article/details/53586004

        // 接收由 http 发送的消息
        Channel httpChannel = ctx.channel();
        // 校验请求路由
        String uri = request.uri().replace("/", "");
        if (!IOTUtils.getReqUriMap().containsKey(uri)) {
            FullHttpResponse response = IOTUtils.buildResponse(new DeviceHttpResp(HttpStatus.SC_BAD_REQUEST, "错误的请求"));
            httpChannel.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        } else {
            if (request.method() != HttpMethod.POST) {
                FullHttpResponse response = IOTUtils.buildResponse(new DeviceHttpResp(HttpStatus.SC_METHOD_NOT_ALLOWED, "只支持POST请求"));
                httpChannel.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
            } else {
                DevBusiHandler devBusiHandler = (DevBusiHandler) SpringContextUtils.getBean(DevBusiHandler.REQ_PREFIX + uri);
                devBusiHandler.handle(request, httpChannel);
                /*try {
                } catch (Exception e) {
                    log.error("发送请求到设备异常", e);
                    FullHttpResponse response = IOTUtils.buildResponse(new DeviceHttpResp(HttpStatus.SC_INTERNAL_SERVER_ERROR, "发送请求到设备异常"));
                    httpChannel.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
                    DevHttpChannelManager.removeByChannel(httpChannel);
                }*/
            }
        }
    }


    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception { // (5)

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Channel httpChannel = ctx.channel();
        String deviceId = DevHttpChannelManager.removeByChannel(httpChannel);
        Channel deviceChannel = DevTcpChannelManager.getChannelByDeviceId(deviceId);
        // 当出现异常就关闭连接
        log.error(cause instanceof ReadTimeoutException ? "读取设备[{} = " + IOTUtils.getDeviceActionName(DevHttpChannelManager.deviceAction.get(deviceId)) + "]响应超时！" : "http 通道[设备ID = {}]异常！", deviceId, cause);
        /**
         * 设备响应超时不关闭TCP连接
         */
        /*if (null != deviceChannel) {
            deviceChannel.close();
        }*/
        ctx.close();
    }
}