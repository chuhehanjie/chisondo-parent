package com.chisondo.model.http.req;

import java.io.Serializable;

public class StopWorkHttpReq implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 停止工作/保温
     * 固定值：stopwork
     */
    private String action;
    /**
     * 操作标识
     * 1-停止沏茶 2-停止烧水 3-停止洗茶 4-停止保温
     */
    private Integer actionflag;
    private String deviceID;

    public StopWorkHttpReq() {
    }

    public StopWorkHttpReq(Integer actionflag, String deviceID) {
        this.actionflag = actionflag;
        this.deviceID = deviceID;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getActionflag() {
        return actionflag;
    }

    public void setActionflag(Integer actionflag) {
        this.actionflag = actionflag;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }
}
