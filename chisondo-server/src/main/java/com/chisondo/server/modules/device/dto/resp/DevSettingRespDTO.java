package com.chisondo.server.modules.device.dto.resp;

import java.io.Serializable;
import java.util.List;

public class DevSettingRespDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String deviceName; //设备名称
    private String devicePwd; // 连接密码
    private Integer isOpenSound; // 是否静音	0-有提示音；1-无提音
    /**
     * 网络标志
     * 1-优先2G网络  2-优先Wi-Fi网络（默认）
     */
    private Integer gmsflag;
    private DevSettingMsgDTO washTea; // 洗茶参数
    private DevSettingMsgDTO waterHeat; // 烧水参数


    private List<TeaSpectrumDTO> chapuInfo; // 茶谱信息

    private String version; // 当前版本号
    private String newversion; // 新版本号
    private String companyCode; // 公司编号
    private String standyServer; // 服务器地址


    public DevSettingRespDTO() {
    }

    public DevSettingRespDTO(String deviceName, String devicePwd) {
        this.deviceName = deviceName;
        this.devicePwd = devicePwd;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDevicePwd() {
        return devicePwd;
    }

    public void setDevicePwd(String devicePwd) {
        this.devicePwd = devicePwd;
    }

    public Integer getIsOpenSound() {
        return isOpenSound;
    }

    public void setIsOpenSound(Integer isOpenSound) {
        this.isOpenSound = isOpenSound;
    }

    public DevSettingMsgDTO getWaterHeat() {
        return waterHeat;
    }

    public void setWaterHeat(DevSettingMsgDTO waterHeat) {
        this.waterHeat = waterHeat;
    }

    public List<TeaSpectrumDTO> getChapuInfo() {
        return chapuInfo;
    }

    public void setChapuInfo(List<TeaSpectrumDTO> chapuInfo) {
        this.chapuInfo = chapuInfo;
    }

    public Integer getGmsflag() {
        return gmsflag;
    }

    public void setGmsflag(Integer gmsflag) {
        this.gmsflag = gmsflag;
    }

    public DevSettingMsgDTO getWashTea() {
        return washTea;
    }

    public void setWashTea(DevSettingMsgDTO washTea) {
        this.washTea = washTea;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNewversion() {
        return newversion;
    }

    public void setNewversion(String newversion) {
        this.newversion = newversion;
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
}
