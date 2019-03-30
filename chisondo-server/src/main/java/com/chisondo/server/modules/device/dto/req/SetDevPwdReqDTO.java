package com.chisondo.server.modules.device.dto.req;

public class SetDevPwdReqDTO extends DevCommonReqDTO {
    private String oldPwd; // 旧密码，没有旧密码时不传入此参数
    private String newPwd; // 新密码

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }
}
