package com.chisondo.model.http.req;

import java.io.Serializable;

public class QryDevSettingHttpReq implements Serializable {
    private static final long serialVersionUID = 1L;

    private String action; // qrydevparm	固定	Y	查询内置参数信息状态
    private String deviceID; // 操作的设备ID

    public QryDevSettingHttpReq() {
    }

    public QryDevSettingHttpReq(String deviceID) {
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
