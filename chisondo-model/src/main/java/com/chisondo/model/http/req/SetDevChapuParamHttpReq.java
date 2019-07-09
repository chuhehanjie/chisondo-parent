package com.chisondo.model.http.req;

import com.chisondo.model.http.resp.DevParamMsg;

import java.io.Serializable;
import java.util.List;

/**
 * 设备设备茶谱参数 HTTP 请求对象
 * @author ding.zhong
 */
public class SetDevChapuParamHttpReq implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * setchapuparm 设置内置茶谱参数
     */
    private String action; //	setchapuparm	固定	Y
    private int index; // 面板位置, 1-10
    private Integer chapuid; // 茶谱ID
    private String chapuname; // 茶谱名称
    private Integer maketimes; // 总泡数
    private Integer executeIndex; // 执行第几泡
    private List<DevParamMsg> teaparm; //	每一泡参数

    private String deviceID;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Integer getChapuid() {
        return chapuid;
    }

    public void setChapuid(Integer chapuid) {
        this.chapuid = chapuid;
    }

    public String getChapuname() {
        return chapuname;
    }

    public void setChapuname(String chapuname) {
        this.chapuname = chapuname;
    }

    public Integer getMaketimes() {
        return maketimes;
    }

    public void setMaketimes(Integer maketimes) {
        this.maketimes = maketimes;
    }

    public List<DevParamMsg> getTeaparm() {
        return teaparm;
    }

    public void setTeaparm(List<DevParamMsg> teaparm) {
        this.teaparm = teaparm;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public Integer getExecuteIndex() {
        return executeIndex;
    }

    public void setExecuteIndex(Integer executeIndex) {
        this.executeIndex = executeIndex;
    }
}
