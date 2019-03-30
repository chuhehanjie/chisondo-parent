package com.chisondo.server.modules.device.dto.req;

public class SetDevSoundReqDTO extends DevCommonReqDTO {
    private int gmsflag; // 网络标志	1-优先2G网络（默认）  2-优先Wi-Fi网络
    private int operFlag; // 操作标志	0-开启声音；1-关闭声音

    public int getGmsflag() {
        return gmsflag;
    }

    public void setGmsflag(int gmsflag) {
        this.gmsflag = gmsflag;
    }

    @Override
    public int getOperFlag() {
        return operFlag;
    }

    @Override
    public void setOperFlag(int operFlag) {
        this.operFlag = operFlag;
    }
}
