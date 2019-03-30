package com.chisondo.server.modules.device.dto.req;

import java.io.Serializable;

public class DeviceStatusDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private	Integer	connStatus	; //	连接状态	0:未连接, 非0:已有用户连接上
    private	Integer	onlineStatus	; //	在线状态	0不在线，1在线
    private	Integer	makeTemp	; //	设定加热温度	1:ON; 0:OFF; 60‐100
    private	Integer	temp	; //	即时温度值	25‐100
    private	Integer	warm	; //	是否保温	0-未保温 1-保温中
    private	Integer	density	; //	浓淡指示灯状态	1:浓; 2:中; 3:淡;
    private	Integer	waterlv	; //	设定出水量	8个档次(单位：毫升ml)
    private	Integer	makeDura	; //	设定沏茶时间	0 - 不浸泡，1~600  浸泡时间(单位:秒)
    private	Integer	reamin	; //	剩余工作时间	面板显示剩余时间
    private	Integer	tea	; //	即时缺茶状态	1:缺茶
    private	Integer	water	; //	即时缺水状态	1:缺水
    private	Integer	work	; //	即时沏茶状态	0:没有工作, 1: 沏茶, 2: 洗茶，3：烧水
    private	Integer	makeType	; //	沏茶类型	0-茶谱沏茶；1-普通沏茶；2-面板操作
    private	Integer	teaSortId	; //	茶类ID
    private	String	teaSortName	; //	茶类名称
    private	Integer	chapuId	; //	使用的茶谱ID	没有在使用茶谱返回0
    private	String	chapuName	; //	茶谱名称
    private	String	chapuImage	; //	茶谱图像
    private	int	chapuMakeTimes	; //	茶谱总泡数
    private	int	index	; //	第几泡	>0：当前正在进行的是第几泡;-1：没有正在使用的茶谱;0：已完成茶谱最大泡数，下一步开始第1泡;999：茶谱正常结束

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

    public int getChapuMakeTimes() {
        return chapuMakeTimes;
    }

    public void setChapuMakeTimes(int chapuMakeTimes) {
        this.chapuMakeTimes = chapuMakeTimes;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
