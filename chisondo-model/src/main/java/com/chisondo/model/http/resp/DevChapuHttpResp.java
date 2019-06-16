package com.chisondo.model.http.resp;

import java.util.List;

public class DevChapuHttpResp extends CommonHttpResp {

    private	String action; //固定：qrychapuparmok 	请求传入的action+ok	请求传入的action+ok
    private	String deviceID; // 被操作的设备ID

    private List<TeaSpectrumMsgResp> chapumsg;

    public DevChapuHttpResp(int retn, String desc) {
        super(retn, desc);
    }

    public DevChapuHttpResp() {

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

    public List<TeaSpectrumMsgResp> getChapumsg() {
        return chapumsg;
    }

    public void setChapumsg(List<TeaSpectrumMsgResp> chapumsg) {
        this.chapumsg = chapumsg;
    }
}
