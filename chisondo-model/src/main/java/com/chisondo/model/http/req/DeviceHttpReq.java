package com.chisondo.model.http.req;


import com.chisondo.model.http.resp.DevParamMsg;

import java.io.Serializable;

public class DeviceHttpReq implements Serializable {
    private static final long serialVersionUID = 1L;
    private String action;
    private int actionflag;
    private String deviceId;
    private DevParamMsg msg;

    public DeviceHttpReq() {
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getActionflag() {
        return actionflag;
    }

    public void setActionflag(int actionflag) {
        this.actionflag = actionflag;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public DevParamMsg getMsg() {
        return msg;
    }

    public void setMsg(DevParamMsg msg) {
        this.msg = msg;
    }
}
