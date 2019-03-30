package com.chisondo.server.modules.device.dto.req;

/**
 * 洗茶请求DTO
 */
public class WashTeaReqDTO extends DeviceCtrlReqDTO {
    /**
     * 是否更新液晶屏洗茶按钮参数。
     * 0-执行洗茶功能 1-执行洗茶并修改液晶屏洗茶按钮参数 2-只修改液晶屏洗茶按钮参数，不执行洗茶操作（为空则默认：0）
     */
    private int isSave;

    public int getIsSave() {
        return isSave;
    }

    public void setIsSave(int isSave) {
        this.isSave = isSave;
    }
}
