package com.chisondo.server.modules.user.dto;

import java.io.Serializable;

public class UsedDeviceUserDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String phoneNum; // 手机号码	绑定过该设备的手机号码
    private String userName; // 用户微信昵称	有微信信息时则返回
    private String userImg; // 用户微信图像	有微信信息时则返回
    private String lastUseTime; // 最后使用时间	最后一次使用设备时间

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

    public String getLastUseTime() {
        return lastUseTime;
    }

    public void setLastUseTime(String lastUseTime) {
        this.lastUseTime = lastUseTime;
    }

}
