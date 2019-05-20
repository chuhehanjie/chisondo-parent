package com.chisondo.server.modules.olddevice.req;

import com.chisondo.server.modules.device.dto.req.DeviceCtrlReqDTO;

import java.io.Serializable;

public class MakeTeaReq implements Serializable {
    private static final long serialVersionUID = 1L;
    private String startTime;
    private String sessionId;
    private String ver;
    private Integer temperature;
    private Integer warm;
    private Integer soak;
    private Integer soup;

    public MakeTeaReq(String sessionId, DeviceCtrlReqDTO devCtrlReqDTO) {
        this.sessionId = sessionId;
        this.ver = "1.0.0";
        this.temperature = devCtrlReqDTO.getTemperature();
        this.warm = devCtrlReqDTO.getWarm();
        this.soak = devCtrlReqDTO.getSoak();
        this.startTime = devCtrlReqDTO.getStartTime();
        this.soup = devCtrlReqDTO.getWaterlevel();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
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

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getWarm() {
        return warm;
    }

    public void setWarm(Integer warm) {
        this.warm = warm;
    }

    public Integer getSoak() {
        return soak;
    }

    public void setSoak(Integer soak) {
        this.soak = soak;
    }

    public Integer getSoup() {
        return soup;
    }

    public void setSoup(Integer soup) {
        this.soup = soup;
    }
}
