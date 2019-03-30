package com.chisondo.model.http.resp;

public class DeviceHttpResp extends CommonHttpResp {

    /**
     * 请求传入的action+ok
     */
    private String action;
    private String deviceID;

    private DeviceMsgResp msg;

    public DeviceHttpResp() {
    }

    public DeviceHttpResp(int retn, String desc) {
        super.setRetn(retn);
        super.setDesc(desc);
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

    public DeviceMsgResp getMsg() {
        return msg;
    }

    public void setMsg(DeviceMsgResp msg) {
        this.msg = msg;
    }
}
