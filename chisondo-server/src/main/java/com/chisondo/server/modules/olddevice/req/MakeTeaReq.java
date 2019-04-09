package com.chisondo.server.modules.olddevice.req;

import com.chisondo.server.modules.device.dto.req.DeviceCtrlReqDTO;

import java.io.Serializable;

public class MakeTeaReq implements Serializable {
    private static final long serialVersionUID = 1L;
    private String sessionId;
    private String ver;
    private int temperature;
    private int warm;
    private int soak;

    public MakeTeaReq(String sessionId, DeviceCtrlReqDTO devCtrlReqDTO) {
        this.sessionId = sessionId;
        this.ver = "1.0.0";
        this.temperature = devCtrlReqDTO.getTemperature();
        this.warm = devCtrlReqDTO.getWarm();
        this.soak = devCtrlReqDTO.getSoak();
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getWarm() {
        return warm;
    }

    public void setWarm(int warm) {
        this.warm = warm;
    }

    public int getSoak() {
        return soak;
    }

    public void setSoak(int soak) {
        this.soak = soak;
    }
}
