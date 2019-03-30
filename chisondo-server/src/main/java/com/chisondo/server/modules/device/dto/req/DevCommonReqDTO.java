package com.chisondo.server.modules.device.dto.req;

import java.io.Serializable;

public class DevCommonReqDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String deviceId; // 设备ID
    private String phoneNum; // 用户手机号
    /**
     * 操作标志
     * 0-停止保温；1-启动保温
     */
    private int operFlag;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getOperFlag() {
        return operFlag;
    }

    public void setOperFlag(int operFlag) {
        this.operFlag = operFlag;
    }
}
