package com.chisondo.server.modules.device.dto.req;

import java.io.Serializable;

/**
 * 使用茶谱沏茶请求DTO
 */
public class MakeTeaByTeaSpectrumReqDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String deviceId; // 设备ID
    private String phoneNum; // 手机号码	设备绑定的手机号码
    private int chapuId; // 茶谱ID
    private int index; // 第几泡	为0或为空时执行第1泡

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getChapuId() {
        return chapuId;
    }

    public void setChapuId(int chapuId) {
        this.chapuId = chapuId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
