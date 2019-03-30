package com.chisondo.server.modules.tea.dto;

import java.io.Serializable;

public class TeaSortRowDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private int sortId; // 茶类ID
    private String name; // 茶类名称
    private String imgUrl; // 图片路径
    private String desc; // 描述信息
    private int tempMin; // 建议温度-最小值
    private int tempMax; // 建议温度-最大值
    private int duraMin; // 建议时长-最小值
    private int duraMax; // 建议时长-最大值
    private int waterRecom; // 建议出水量
    private int isDefault; // 是否默认	0-否；1-是
    private int temperature; // 设定温度	0-停止加热
    private int soak; // 设定时间（根据出汤量不同时间的最小值不同）	0 - 不浸泡，1~600  浸泡时间(单位:秒)
    private int waterlevel; // 出水量（分8个档次）	 150 200 250 300   350 400  450 550 (单位：毫升ml)

    public int getSortId() {
        return sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getTempMin() {
        return tempMin;
    }

    public void setTempMin(int tempMin) {
        this.tempMin = tempMin;
    }

    public int getTempMax() {
        return tempMax;
    }

    public void setTempMax(int tempMax) {
        this.tempMax = tempMax;
    }

    public int getDuraMin() {
        return duraMin;
    }

    public void setDuraMin(int duraMin) {
        this.duraMin = duraMin;
    }

    public int getDuraMax() {
        return duraMax;
    }

    public void setDuraMax(int duraMax) {
        this.duraMax = duraMax;
    }

    public int getWaterRecom() {
        return waterRecom;
    }

    public void setWaterRecom(int waterRecom) {
        this.waterRecom = waterRecom;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
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
}
