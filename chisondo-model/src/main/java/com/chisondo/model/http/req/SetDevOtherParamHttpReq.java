package com.chisondo.model.http.req;

import com.chisondo.model.http.resp.DevParamMsg;

import java.io.Serializable;

public class SetDevOtherParamHttpReq implements Serializable {
    private static final long serialVersionUID = 1L;
    private String action; // setotherparm 设置其它参数如 静音 网络优先
    private Integer volflag; // 1-开启提示声 2-关闭提示音
    private Integer gmsflag; // 1-优先2G网络（默认）  2-优先Wi-Fi网络
    private String deviceID;
    private DevParamMsg msg;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getVolflag() {
        return volflag;
    }

    public void setVolflag(Integer volflag) {
        this.volflag = volflag;
    }

    public Integer getGmsflag() {
        return gmsflag;
    }

    public void setGmsflag(Integer gmsflag) {
        this.gmsflag = gmsflag;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public DevParamMsg getMsg() {
        return msg;
    }

    public void setMsg(DevParamMsg msg) {
        this.msg = msg;
    }
}
