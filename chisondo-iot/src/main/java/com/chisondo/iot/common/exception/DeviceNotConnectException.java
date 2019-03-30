package com.chisondo.iot.common.exception;

public class DeviceNotConnectException extends RuntimeException {

    private String deviceId;

    public DeviceNotConnectException() {
        super();
    }

    public DeviceNotConnectException(String deviceId) {
        super();
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }
}
