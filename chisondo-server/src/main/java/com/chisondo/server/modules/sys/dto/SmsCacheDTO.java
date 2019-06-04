package com.chisondo.server.modules.sys.dto;

import java.io.Serializable;
import java.util.Date;

public class SmsCacheDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String mobile;

    private String verifyCode;

    private Date sendDate;

    private String ipAddr;

    public SmsCacheDTO(String mobile, String verifyCode, Date sendDate, String ipAddr) {
        this.mobile = mobile;
        this.verifyCode = verifyCode;
        this.sendDate = sendDate;
        this.ipAddr = ipAddr;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }
}
