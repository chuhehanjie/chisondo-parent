package com.chisondo.server.modules.olddevice.resp;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class ConnectDevResp implements Serializable {
    private static final long serialVersionUID = 1L;

    private int STATE;
    /**
     * 连接失败原因
     * 1-设备不存在；2-设备为其他用户私有；3-设备离线；4-设备已被其他用户占用；5-密码错误
     */
    private String STATE_INFO;
    private int deviceType;
    private String  deviceIp;
    private int port;
    private String ip;
    private String deviceTypeName;
    private String sessionId;
    private Integer deviceId;
    private String deviceName;
    private String deviceDesc;
    private int errCode;

    public String getSTATE_INFO() {
        return STATE_INFO;
    }

    public void setSTATE_INFO(String STATE_INFO) {
        this.STATE_INFO = STATE_INFO;
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
        return 0 == this.errCode;
    }

    public String getErrorInfo() {
        if (0 == this.errCode) {
            return "操作成功";
        } else if (1 == this.errCode) {
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

    public static void main(String[] args) {
        String json = "{\"deviceType\":1,\"deviceIp\":\"118.249.101.63\",\"port\":9130,\"ip\":\"120.25.210.78\",\"deviceTypeName\":\"MT123\",\"STATE\":0,\"sessionId\":\"ba5be3c6-b445-4f7a-b6fb-908dd5a857d9\",\"STATE_INFO\":\"操作成功\",\"deviceId\":18170964,\"deviceName\":\"龙小平办公桌旁\",\"deviceDesc\":\"智能泡让您的生活更美好！\"}";
        ConnectDevResp resp = JSONObject.parseObject(json, ConnectDevResp.class);
        System.out.println("result = " + JSONObject.toJSONString(resp));
    }
}
