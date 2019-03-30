package com.chisondo.server.modules.device.dto.resp;

import java.io.Serializable;

/**
 * 设备绑定响应DTO
 */
public class DeviceBindRespDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer deviceId; // 设备ID
    private String deviceName; // 设备名称
    private Integer companyId; // 所属企业id
    private String companyName; // 所属企业名称	,0-泉笙道，1-湘丰集团，2-静硒园,345….,默认 0泉笙道
    private String deviceDesc; // 设备描述

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDeviceDesc() {
        return deviceDesc;
    }

    public void setDeviceDesc(String deviceDesc) {
        this.deviceDesc = deviceDesc;
    }
}
