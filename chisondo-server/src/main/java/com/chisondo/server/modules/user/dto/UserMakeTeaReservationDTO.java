package com.chisondo.server.modules.user.dto;

import java.io.Serializable;

public class UserMakeTeaReservationDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private	String	reservNo; // 预约编号
    private	String	phoneNum; // 预约用户手机号码
    private	String	reservTime; // 预约时间
    private	String	startTime; // 启动时间
    private	Integer	chapuId; // 茶谱ID	可为空
    private	String	chapuName; // 茶谱名称	可为空
    private	String	chapuImage; // 茶谱图像	可为空
    private	Integer	teaSortId; // 茶类ID
    private	String	teaSortName; // 茶类名称
    private	Integer soak; // 设定沏茶时间	0 - 不浸泡，1~600  浸泡时间(单位:秒)
    private	Integer warm; // 保温
    private	Integer	waterlv; // 设定出水量	8个档次(单位：毫升ml)
    private	Integer temperature; // 设定加热温度	1:ON; 0:OFF; 60‐100
    private	Integer	valid; // 有效标识	0-有效; 1-已成功执行; 2-已取消; 3-已过期且未成功执行

    public String getReservNo() {
        return reservNo;
    }

    public void setReservNo(String reservNo) {
        this.reservNo = reservNo;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getReservTime() {
        return reservTime;
    }

    public void setReservTime(String reservTime) {
        this.reservTime = reservTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getChapuId() {
        return chapuId;
    }

    public void setChapuId(Integer chapuId) {
        this.chapuId = chapuId;
    }

    public String getChapuName() {
        return chapuName;
    }

    public void setChapuName(String chapuName) {
        this.chapuName = chapuName;
    }

    public String getChapuImage() {
        return chapuImage;
    }

    public void setChapuImage(String chapuImage) {
        this.chapuImage = chapuImage;
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

    public Integer getSoak() {
        return soak;
    }

    public void setSoak(Integer soak) {
        this.soak = soak;
    }

    public Integer getWaterlv() {
        return waterlv;
    }

    public void setWaterlv(Integer waterlv) {
        this.waterlv = waterlv;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Integer getWarm() {
        return warm;
    }

    public void setWarm(Integer warm) {
        this.warm = warm;
    }
}
