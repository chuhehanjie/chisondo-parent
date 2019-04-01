package com.chisondo.server.modules.device.dto.req;

import java.io.Serializable;

/**
 * 更换茶谱请求DTO
 */
public class ChgDevTeaSpectrumReqDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer chapuId; // 茶谱ID
    private Integer index; // 面板位置	液晶屏中的茶谱顺序
    private String deviceId; // 设备ID

    public Integer getChapuId() {
        return chapuId;
    }

    public void setChapuId(Integer chapuId) {
        this.chapuId = chapuId;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
