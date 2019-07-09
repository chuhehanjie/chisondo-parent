package com.chisondo.model.http.resp;

import java.io.Serializable;

public class DevStatusMsgResp implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 工作状态
     * 0 -空闲 1-沏茶中 2-洗茶中 3-烧水中
     */
    private Integer workstatus;
    /**
     * 温度状态
     * 0-未保温 1-保温中
     */
    private Integer warmstatus;
    /**
     * 浓度状态
     * 1-浓 2-中 3淡
     */
    private Integer taststatus;
    /**
     * 温度值
     * 需大于60度小于100度
     */
    private Integer temperature;
    /**
     * 沏茶时间
     * 0 - 不浸泡，1~600  沏茶时间(单位:秒)
     */
    private Integer soak;
    /**
     * 出水量
     * 取值为：150 200 250 300 350 400 450 550  8个档次 (单位：毫升ml)
     */
    private Integer waterlevel;
    /**
     * 	面板剩余时间
     */
    private Integer remaintime;
    /**
     * 异常状态
     * 0-正常 1-缺水 2-缺茶 3-缺水缺茶 4-未连接Wi-Fi
     */
    private int errorstatus;
    /**
     * 实时温度	60-100
     * 当前发热管的温度
     */
    private Integer nowwarm;

    private Integer chapuId;

    private Integer step;

    public Integer getWorkstatus() {
        return workstatus;
    }

    public void setWorkstatus(Integer workstatus) {
        this.workstatus = workstatus;
    }

    public Integer getWarmstatus() {
        return warmstatus;
    }

    public void setWarmstatus(Integer warmstatus) {
        this.warmstatus = warmstatus;
    }

    public Integer getTaststatus() {
        return taststatus;
    }

    public void setTaststatus(Integer taststatus) {
        this.taststatus = taststatus;
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

    public Integer getRemaintime() {
        return remaintime;
    }

    public void setRemaintime(Integer remaintime) {
        this.remaintime = remaintime;
    }

    public int getErrorstatus() {
        return errorstatus;
    }

    public void setErrorstatus(int errorstatus) {
        this.errorstatus = errorstatus;
    }

    public Integer getNowwarm() {
        return nowwarm;
    }

    public void setNowwarm(Integer nowwarm) {
        this.nowwarm = nowwarm;
    }

    public Integer getChapuId() {
        return chapuId;
    }

    public void setChapuId(Integer chapuId) {
        this.chapuId = chapuId;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }
}
