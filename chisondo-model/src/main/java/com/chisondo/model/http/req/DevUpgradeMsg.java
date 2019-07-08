package com.chisondo.model.http.req;

public class DevUpgradeMsg {

    private String companyCode;
    private String version;
    private String standyServer;

    public DevUpgradeMsg() {
    }

    public DevUpgradeMsg(String companyCode, String version, String standyServer) {
        this.companyCode = companyCode;
        this.version = version;
        this.standyServer = standyServer;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStandyServer() {
        return standyServer;
    }

    public void setStandyServer(String standyServer) {
        this.standyServer = standyServer;
    }
}
