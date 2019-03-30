package com.chisondo.iot.http.model;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.model.http.req.DeviceHttpReq;
import io.netty.channel.Channel;

import java.io.Serializable;

public class DevBusiModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private String json;
    private Channel deviceChannel;

    public DevBusiModel() {
    }

    public DevBusiModel(String json, Channel deviceChannel) {
        this.json = json;
        this.deviceChannel = deviceChannel;
    }

    public String getJson() {
        return json;
    }

    public Channel getDeviceChannel() {
        return deviceChannel;
    }

    public void sendReq2Dev(Object req) {
        deviceChannel.writeAndFlush(JSONObject.toJSONString(req) + "\n");
    }
}
