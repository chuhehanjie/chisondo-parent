package com.chisondo.server.modules.olddevice.req;

import java.io.Serializable;

public class ConnectDevReq implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;

    private String phoneNum;

    private Integer deviceId;

    private String passwd;

    private int needValidate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public int getNeedValidate() {
        return needValidate;
    }

    public void setNeedValidate(int needValidate) {
        this.needValidate = needValidate;
    }
}
