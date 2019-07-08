package com.chisondo.model.http.req;


import com.chisondo.model.http.resp.DevParamMsg;

import java.io.Serializable;

public class DeviceHttpReq implements Serializable {
    private static final long serialVersionUID = 1L;
    private String action;
    private int actionflag;
    private String deviceID;
    private Integer teaTypeId; // 茶类ID
    private String teaTypeName; // 茶类名称
    private DevParamMsg msg;

    public DeviceHttpReq() {
    }

    public DeviceHttpReq(String deviceID) {
        this.deviceID = deviceID;
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

    public Integer getTeaTypeId() {
        return teaTypeId;
    }

    public void setTeaTypeId(Integer teaTypeId) {
        this.teaTypeId = teaTypeId;
    }

    public String getTeaTypeName() {
        return teaTypeName;
    }

    public void setTeaTypeName(String teaTypeName) {
        this.teaTypeName = teaTypeName;
    }
}
