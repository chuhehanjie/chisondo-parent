package com.chisondo.model.http.resp;

public class DevParamMsg {
    private	Integer	temperature; // 设定的温度，需大于60度小于100度。
    private	Integer	soak; // 0 - 不浸泡，1~600  沏茶时间(单位:秒)
    private	Integer	waterlevel; // 取值为：150 200 250 300 350 400 450 550  8个档次 (单位：毫升ml)

    public DevParamMsg() {
    }

    public DevParamMsg(Integer temperature, Integer soak, Integer waterlevel) {
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
