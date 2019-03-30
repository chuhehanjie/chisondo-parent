package com.chisondo.model.http.resp;

public class DeviceMsgResp {
    /**
     * 状态值，可扩展
     */
    private int state;

    /**
     * 对应状态值的说明
     * 如： 0-操作成功，非0为操作失败 4-设备缺水 5-设备茶盒未放入 6-。。。。 7-	其它异常错误
     */
    private int stateinfo;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getStateinfo() {
        return stateinfo;
    }

    public void setStateinfo(int stateinfo) {
        this.stateinfo = stateinfo;
    }
}
