package com.chisondo.server.modules.tea.dto;

import java.io.Serializable;

public class DelOrFinishTeaSpectrumReqDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String phoneNum;
    private	Integer	operFlag;
    private	Integer	chapuId;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Integer getOperFlag() {
        return operFlag;
    }

    public void setOperFlag(Integer operFlag) {
        this.operFlag = operFlag;
    }

    public Integer getChapuId() {
        return chapuId;
    }

    public void setChapuId(Integer chapuId) {
        this.chapuId = chapuId;
    }
}
