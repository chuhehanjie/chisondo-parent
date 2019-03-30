package com.chisondo.model.http.resp;

public class DevSettingMsgResp {
    private	Integer state; // 数值	0 1 2 3	Y	状态值，可扩展
    private	Integer stateinfo; // 数值	0 1 2 3…	Y	对应状态值的说明，如：0-操作成功，非0为操作失败 1-其它异常错误
    private	Integer volflag; // 1-开启提示声 2-关闭提示音
    private	Integer gmsflag; // 1-优先2G网络（默认）  2-优先Wi-Fi网络

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getStateinfo() {
        return stateinfo;
    }

    public void setStateinfo(Integer stateinfo) {
        this.stateinfo = stateinfo;
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
}
