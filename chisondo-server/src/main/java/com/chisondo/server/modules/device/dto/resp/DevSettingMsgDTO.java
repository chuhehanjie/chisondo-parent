package com.chisondo.server.modules.device.dto.resp;

public class DevSettingMsgDTO {
    private Integer	temperature; // 设定温度
    private Integer	soak; // 设定时间	烧水默认 0
    private Integer	waterlevel; // 出水量	分8档

    public DevSettingMsgDTO() {
    }

    public DevSettingMsgDTO(Integer temperature, Integer soak, Integer waterlevel) {
        this.temperature = temperature;
        this.soak = soak;
        this.waterlevel = waterlevel;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getSoak() {
        return soak;
    }

    public void setSoak(Integer soak) {
        this.soak = soak;
    }

    public Integer getWaterlevel() {
        return waterlevel;
    }

    public void setWaterlevel(Integer waterlevel) {
        this.waterlevel = waterlevel;
    }
}
