package com.chisondo.model.http.req;

import java.io.Serializable;

public class LockOrUnlockDevHttpReq implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * devicelock
     * 锁定设备或解锁设备
     */
    private String action;
    /**
     * 1-电源键 2-保温键 3-确认键 4--
     */
    private int actionflag;
    private String deviceID;
    /**
     * 1-锁定 2-解锁
      */
    private int type;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
