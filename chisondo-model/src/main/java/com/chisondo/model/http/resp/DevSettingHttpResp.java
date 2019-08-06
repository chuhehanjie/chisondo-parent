package com.chisondo.model.http.resp;

import java.util.List;

public class DevSettingHttpResp extends CommonHttpResp {

    private	String action; //固定：qrydevparmok 	请求传入的action+ok	请求传入的action+ok
    private	String deviceID; // 被操作的设备ID

    private DevSettingMsgResp msg;

    private DevParamMsg washteamsg;

    private DevParamMsg boilwatermsg;

    private List<TeaSpectrumMsgResp> chapumsg;

    private String version;

    public DevSettingHttpResp(int retn, String desc) {
        super(retn, desc);
    }

    public DevSettingHttpResp() {

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

    public DevSettingMsgResp getMsg() {
        return msg;
    }

    public void setMsg(DevSettingMsgResp msg) {
        this.msg = msg;
    }

    public DevParamMsg getWashteamsg() {
        return washteamsg;
    }

    public void setWashteamsg(DevParamMsg washteamsg) {
        this.washteamsg = washteamsg;
    }

    public DevParamMsg getBoilwatermsg() {
        return boilwatermsg;
    }

    public void setBoilwatermsg(DevParamMsg boilwatermsg) {
        this.boilwatermsg = boilwatermsg;
    }

    public List<TeaSpectrumMsgResp> getChapumsg() {
        return chapumsg;
    }

    public void setChapumsg(List<TeaSpectrumMsgResp> chapumsg) {
        this.chapumsg = chapumsg;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
