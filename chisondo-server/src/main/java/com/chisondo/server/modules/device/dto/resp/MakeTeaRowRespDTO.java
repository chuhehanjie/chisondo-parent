package com.chisondo.server.modules.device.dto.resp;

public class MakeTeaRowRespDTO {
    private	Integer	makeId	; // 	记录编号
    private	String	phoneNum	; // 	用户手机号码
    private	String	userName	; // 	用户昵称	可为空可为微信昵称
    private	String	userImg	; // 	用户图像	可为空可为微信图像
    private	String	startTime	; // 	启动时间
    private	String	endTime	; // 	结束时间
    private	Integer	makeType	; // 	沏茶类型	0-普通沏茶；1-茶谱沏茶；2-洗茶；3-烧水
    private	Integer	makeMode	; // 	沏茶方式	0-手机终端操作；1-设备面板操作
    private	int	chapuId	; // 	茶谱ID
    private	String	chapuName	; // 	茶谱名称
    private	Integer	chapuIndex	; // 	执行的是第几泡
    private	String	chapuImage	; // 	茶谱图像	可为空
    private	Integer	teaSortId	; // 	茶类ID
    private	String	teaSortName	; // 	茶类名称
    private	Integer	temperature	; // 	温度
    private	Integer	waterlevel	; // 	出水量
    private	Integer	soak	; // 	沏茶时间

    public Integer getMakeId() {
        return makeId;
    }

    public void setMakeId(Integer makeId) {
        this.makeId = makeId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getMakeType() {
        return makeType;
    }

    public void setMakeType(Integer makeType) {
        this.makeType = makeType;
    }

    public Integer getMakeMode() {
        return makeMode;
    }

    public void setMakeMode(Integer makeMode) {
        this.makeMode = makeMode;
    }

    public int getChapuId() {
        return chapuId;
    }

    public void setChapuId(int chapuId) {
        this.chapuId = chapuId;
    }

    public String getChapuName() {
        return chapuName;
    }

    public void setChapuName(String chapuName) {
        this.chapuName = chapuName;
    }

    public Integer getChapuIndex() {
        return chapuIndex;
    }

    public void setChapuIndex(Integer chapuIndex) {
        this.chapuIndex = chapuIndex;
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

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getWaterlevel() {
        return waterlevel;
    }

    public void setWaterlevel(Integer waterlevel) {
        this.waterlevel = waterlevel;
    }

    public Integer getSoak() {
        return soak;
    }

    public void setSoak(Integer soak) {
        this.soak = soak;
    }
}
