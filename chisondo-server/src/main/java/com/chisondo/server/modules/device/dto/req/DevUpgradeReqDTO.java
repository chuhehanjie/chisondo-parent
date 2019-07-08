package com.chisondo.server.modules.device.dto.req;

import java.io.Serializable;

public class DevUpgradeReqDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String deviceId; // 设备ID
    private String companyCode; // 公司编号
    private String standyServer; // 固件服务器地址	MT123.ota-x.com
    private String version; // 需要更新的版本	MT1000

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getStandyServer() {
        return standyServer;
    }

    public void setStandyServer(String standyServer) {
        this.standyServer = standyServer;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
