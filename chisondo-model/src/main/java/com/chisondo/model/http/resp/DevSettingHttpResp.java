package com.chisondo.model.http.resp;

public class DevSettingHttpResp extends CommonHttpResp {

    private	String action; //固定：qrydevparmok 	请求传入的action+ok	请求传入的action+ok
    private	String deviceID; // 被操作的设备ID

    private DevParamMsg msg;

    private DevParamMsg washteamsg;

    private BoilWaterMsgResp boilwatermsg;

    private TeaSpectrumMsgResp chapumsg;

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

    public DevParamMsg getMsg() {
        return msg;
    }

    public void setMsg(DevParamMsg msg) {
        this.msg = msg;
    }

    public DevParamMsg getWashteamsg() {
        return washteamsg;
    }

    public void setWashteamsg(DevParamMsg washteamsg) {
        this.washteamsg = washteamsg;
    }

    public BoilWaterMsgResp getBoilwatermsg() {
        return boilwatermsg;
    }

    public void setBoilwatermsg(BoilWaterMsgResp boilwatermsg) {
        this.boilwatermsg = boilwatermsg;
    }

    public TeaSpectrumMsgResp getChapumsg() {
        return chapumsg;
    }

    public void setChapumsg(TeaSpectrumMsgResp chapumsg) {
        this.chapumsg = chapumsg;
    }
}
