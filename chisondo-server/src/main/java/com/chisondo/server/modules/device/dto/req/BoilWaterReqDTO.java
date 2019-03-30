package com.chisondo.server.modules.device.dto.req;

/**
 * 烧水请求DTO
 */
public class BoilWaterReqDTO extends DeviceCtrlReqDTO {
    /**
     * 是否更新液晶屏烧水按钮参数。
     * 0-执行烧水功能 1-执行烧水并修改液晶屏烧水按钮参数 2-只修改液晶屏烧水按钮参数，不执行烧水操作（为空则默认：0）
     */
    private int isSave;

    public int getIsSave() {
        return isSave;
    }

    public void setIsSave(int isSave) {
        this.isSave = isSave;
    }
}
