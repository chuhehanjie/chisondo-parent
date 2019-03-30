package com.chisondo.server.modules.device.dto.req;

public class SetDevNameReqDTO extends DevCommonReqDTO {
    private String deviceName; // 设备名称
    private String deviceDesc; // 设备描述

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceDesc() {
        return deviceDesc;
    }

    public void setDeviceDesc(String deviceDesc) {
        this.deviceDesc = deviceDesc;
    }
}
