package com.chisondo.model.http.resp;

import java.io.Serializable;

public class DevStatusRespDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer	connStatus	; // 	0:未连接, 非0:已有用户连接上
    private Integer	onlineStatus	; // 	0不在线，1在线
    private Integer	makeTemp	; // 	1:ON; 0:OFF; 60‐100
    private Integer	temp	; // 	25‐100
    private Integer	warm	; // 	0-未保温 1-保温中
    private Integer	density	; // 	1:浓; 2:中; 3:淡;
    private Integer	waterlv	; // 	8个档次(单位：毫升ml)
    private Integer	makeDura	; // 	0 - 不浸泡，1~600  浸泡时间(单位:秒)
    private Integer	reamin	; // 	面板显示剩余时间
    private Integer	tea	; // 	1:缺茶
    private Integer	water	; // 	1:缺水
    private Integer	work	; // 	0:没有工作, 1: 沏茶, 2: 洗茶，3：烧水
    private Integer	makeType	; // 	0-茶谱沏茶；1-普通沏茶；2-面板操作
    private Integer	teaSortId	; //
    private String	teaSortName	; //
    private Integer	chapuId	; // 	没有在使用茶谱返回0
    private String	chapuName	; //
    private String	chapuImage	; //
    private Integer	chapuMakeTimes	; //
    private Integer	index	; // 	>0：当前正在进行的是第几泡;-1：没有正在使用的茶谱;0：已完成茶谱最大泡数，下一步开始第1泡;999：茶谱正常结束
    private Integer reservLeftTime; // 预约剩余时间

    private boolean countdownFlag;

    private Integer useNum; // 使用次数

    private String deviceId;

    /**
     * 离线时间
     */
    private String offlineTime;

    /**
     * 1 - 心跳  2-电源按键 3-浓度按键 4-启动按键 5 - 保温按键 6-确认按键 7-沏茶自动结束（倒计时到0秒）8-缺水状态 9-缺茶状态
     */
    private Integer actionFlag;

    /**
     * 做停止操作
     */
    private boolean isStopAction;


    /**
     * 当前剩余
     */
    private Integer curRemainTime;

    public Integer getConnStatus() {
        return connStatus;
    }

    public void setConnStatus(Integer connStatus) {
        this.connStatus = connStatus;
    }

    public Integer getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Integer onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public Integer getMakeTemp() {
        return makeTemp;
    }

    public void setMakeTemp(Integer makeTemp) {
        this.makeTemp = makeTemp;
    }

    public Integer getTemp() {
        return temp;
    }

    public void setTemp(Integer temp) {
        this.temp = temp;
    }

    public Integer getWarm() {
        return warm;
    }

    public void setWarm(Integer warm) {
        this.warm = warm;
    }

    public Integer getDensity() {
        return density;
    }

    public void setDensity(Integer density) {
        this.density = density;
    }

    public Integer getWaterlv() {
        return waterlv;
    }

    public void setWaterlv(Integer waterlv) {
        this.waterlv = waterlv;
    }

    public Integer getMakeDura() {
        return makeDura;
    }

    public void setMakeDura(Integer makeDura) {
        this.makeDura = makeDura;
    }

    public Integer getReamin() {
        return reamin;
    }

    public void setReamin(Integer reamin) {
        this.reamin = reamin;
    }

    public Integer getTea() {
        return tea;
    }

    public void setTea(Integer tea) {
        this.tea = tea;
    }

    public Integer getWater() {
        return water;
    }

    public void setWater(Integer water) {
        this.water = water;
    }

    public Integer getWork() {
        return work;
    }

    public void setWork(Integer work) {
        this.work = work;
    }

    public Integer getMakeType() {
        return makeType;
    }

    public void setMakeType(Integer makeType) {
        this.makeType = makeType;
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

    public Integer getChapuMakeTimes() {
        return chapuMakeTimes;
    }

    public void setChapuMakeTimes(Integer chapuMakeTimes) {
        this.chapuMakeTimes = chapuMakeTimes;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getReservLeftTime() {
        return reservLeftTime;
    }

    public void setReservLeftTime(Integer reservLeftTime) {
        this.reservLeftTime = reservLeftTime;
    }

    public Integer getUseNum() {
        return useNum;
    }

    public void setUseNum(Integer useNum) {
        this.useNum = useNum;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getOfflineTime() {
        return offlineTime;
    }

    public void setOfflineTime(String offlineTime) {
        this.offlineTime = offlineTime;
    }

    public boolean isCountdownFlag() {
        return countdownFlag;
    }

    public void setCountdownFlag(boolean countdownFlag) {
        this.countdownFlag = countdownFlag;
    }

    public Integer getActionFlag() {
        return actionFlag;
    }

    public void setActionFlag(Integer actionFlag) {
        this.actionFlag = actionFlag;
    }

    public boolean isStopAction() {
        return isStopAction;
    }

    public void setStopAction(boolean stopAction) {
        this.isStopAction = stopAction;
    }

    public Integer getCurRemainTime() {
        return curRemainTime;
    }

    public void setCurRemainTime(Integer curRemainTime) {
        this.curRemainTime = curRemainTime;
    }

    @Override
    public String toString() {
        return "<< 设备ID = " + this.deviceId + ", actionFlag = " + this.actionFlag + ", 剩余时间 = " + this.reamin + ", 茶谱ID = " + this.chapuId + ", work = " + this.work + " >>";
    }
}
