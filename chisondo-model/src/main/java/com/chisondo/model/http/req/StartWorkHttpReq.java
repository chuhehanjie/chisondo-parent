package com.chisondo.model.http.req;

import com.chisondo.model.http.resp.DevParamMsg;

import java.io.Serializable;

public class StartWorkHttpReq implements Serializable {
    private static final long serialVersionUID = 1L;

    private String action;

    private String deviceID;

    private DevParamMsg msg;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public DevParamMsg getMsg() {
        return msg;
    }

    public void setMsg(DevParamMsg msg) {
        this.msg = msg;
    }
}
