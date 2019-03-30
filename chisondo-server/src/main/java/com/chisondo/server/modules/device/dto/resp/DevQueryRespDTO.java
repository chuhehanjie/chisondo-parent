package com.chisondo.server.modules.device.dto.resp;

import java.io.Serializable;
import java.util.List;

public class DevQueryRespDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private int count; // 设备个数
    private List<DeviceInfoRespDTO> deviceInfo;

    public DevQueryRespDTO(List<DeviceInfoRespDTO> devInfoList) {
        this.deviceInfo = devInfoList;
        this.count = devInfoList.size();
    }

    public DevQueryRespDTO() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DeviceInfoRespDTO> getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(List<DeviceInfoRespDTO> deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
