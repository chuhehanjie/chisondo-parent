package com.chisondo.server.modules.olddevice.resp;

import org.apache.http.HttpStatus;

import java.io.Serializable;

public class ConnectDevResp implements Serializable {
    private static final long serialVersionUID = 1L;

    private int retn;
    private String desc;
    /**
     * 连接失败原因
     * 1-设备不存在；2-设备为其他用户私有；3-设备离线；4-设备已被其他用户占用；5-密码错误
     */
    private int errCode;
    private int deviceType;
    private String  deviceIp;
    private int port;
    private String ip;
    private String deviceTypeName;
    private int STATE;
    private String sessionId;
    private Integer deviceId;
    private String deviceName;
    private String deviceDesc;

    public int getRetn() {
        return retn;
    }

    public void setRetn(int retn) {
        this.retn = retn;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

    public int getSTATE() {
        return STATE;
    }

    public void setSTATE(int STATE) {
        this.STATE = STATE;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceDesc() {
        return deviceDesc;
    }

    public void setDeviceDesc(String deviceDesc) {
        this.deviceDesc = deviceDesc;
    }

    public boolean isOK() {
        return HttpStatus.SC_OK == this.retn;
    }

    public String getErrorInfo() {
        if (1 == this.errCode) {
            return "设备不存在";
        } else if (2 == this.errCode) {
            return "设备为其他用户私有";
        } else if (3 == this.errCode) {
            return "设备离线";
        } else if (4 == this.errCode) {
            return "设备已被其他用户占用";
        } else if (5 == this.errCode) {
            return "密码错误";
        } else {
            return "连接设备异常";
        }
    }
}
