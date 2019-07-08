package com.chisondo.model.http.req;

import java.io.Serializable;

public class DevUpgradeHttpReq implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * OTAparm
     */
    private String action;

    private String deviceID;

    private DevUpgradeMsg msg;

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

    public DevUpgradeMsg getMsg() {
        return msg;
    }

    public void setMsg(DevUpgradeMsg msg) {
        this.msg = msg;
    }
}
