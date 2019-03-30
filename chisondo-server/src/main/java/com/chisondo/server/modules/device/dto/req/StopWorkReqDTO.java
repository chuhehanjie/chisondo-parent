package com.chisondo.server.modules.device.dto.req;

import java.io.Serializable;

public class StopWorkReqDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String deviceId; // 设备ID
    /**
     * 操作标志
     * 0-停止沏茶 1-停止洗茶 2-停止烧水 （为空则默：0）
     */
    private int operFlag;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getOperFlag() {
        return operFlag;
    }

    public void setOperFlag(int operFlag) {
        this.operFlag = operFlag;
    }
}
