package com.chisondo.server.modules.device.dto.req;

import java.io.Serializable;

/**
 *
 */
public class DeviceCtrlReqDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String startTime;
    private String deviceId; // 设备ID
    private String phoneNum; // 手机号码	设备绑定的手机号码
    private int temperature; // 设定温度	60~100,0-停止加热
    private int soak; // 设定时间（根据出汤量不同时间的最小值不同）	0~600秒,0 - 不浸泡，1~600  浸泡时间(单位:秒)
    private int waterlevel; // 出水量（分8个档次）	 150 200 250 300   350 400  450 550 (单位：毫升ml)
    private Integer teaSortId; // 茶类ID
    private String teaSortName; // 茶类名称
    private Integer warm;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getSoak() {
        return soak;
    }

    public void setSoak(int soak) {
        this.soak = soak;
    }

    public int getWaterlevel() {
        return waterlevel;
    }

    public void setWaterlevel(int waterlevel) {
        this.waterlevel = waterlevel;
    }

    public Integer getTeaSortId() {
        return teaSortId;
    }

    public void setTeaSortId(Integer teaSortId) {
        this.teaSortId = teaSortId;
    }

    public String getTeaSortName() {
        return teaSortName;
    }

    public void setTeaSortName(String teaSortName) {
        this.teaSortName = teaSortName;
    }

    public Integer getWarm() {
        return warm;
    }

    public void setWarm(Integer warm) {
        this.warm = warm;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
