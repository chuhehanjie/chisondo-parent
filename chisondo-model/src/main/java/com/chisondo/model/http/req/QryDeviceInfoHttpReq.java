package com.chisondo.model.http.req;

import java.io.Serializable;

public class QryDeviceInfoHttpReq implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 动作
     * cancelwarm 查询设备状态
     * qrydevparm 查询设备参数
     * qrychapuparm 查询设备茶谱信息
     */
    private String action;
    /**
     * 设备ID
     */
    private String deviceID; // 操作的设备ID

    public QryDeviceInfoHttpReq() {
    }

    public QryDeviceInfoHttpReq(String deviceID) {
        this.deviceID = deviceID;
    }

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
}
