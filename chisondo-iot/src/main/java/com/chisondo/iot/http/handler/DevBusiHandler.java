package com.chisondo.iot.http.handler;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.iot.common.exception.DeviceNotConnectException;
import com.chisondo.iot.common.utils.IOTUtils;
import com.chisondo.iot.device.server.DevTcpChannelManager;
import com.chisondo.iot.http.model.DevBusiModel;
import com.chisondo.model.http.HttpStatus;
import com.chisondo.model.http.resp.DeviceHttpResp;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Slf4j
public abstract class DevBusiHandler {

    public void handle(FullHttpRequest request, Channel httpChannel) {
        try {
            DevBusiModel devBusiModel = this.validate(request);
            this.processBusi(devBusiModel);
        } catch (DeviceNotConnectException e) {
            log.error("设备[{}]未连接", e.getDeviceId());
            FullHttpResponse response = IOTUtils.buildResponse(new DeviceHttpResp(HttpStatus.SC_INTERNAL_SERVER_ERROR, "设备[" + e.getDeviceId() + "]未连接"));
            httpChannel.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        }
    }

    protected abstract void processBusi(DevBusiModel devBusiModel);

    private DevBusiModel validate(FullHttpRequest request) {
        String json = IOTUtils.getJSONFromRequest(request);
        JSONObject jsonObj = JSONObject.parseObject(json);
        String deviceId = jsonObj.getString("deviceId");
        if (StringUtils.isEmpty(deviceId)) {
            deviceId = jsonObj.getString("deviceID");
        }
        Channel deviceChannel = DevTcpChannelManager.getChannelByDevice(deviceId);
        if (ObjectUtils.isEmpty(deviceChannel)) {
            throw new DeviceNotConnectException(deviceId);
        }

        return new DevBusiModel(json, deviceChannel);
    }
}
