package com.chisondo.model.http.resp;

import com.chisondo.model.http.HttpStatus;

import java.io.Serializable;

public class CommonHttpResp implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 返回码：200表示正常，其它为异常
     * 201-参数格式错误
     * 202-鉴权失败
     */
    private int retn; //

    /**
     * 返回描述：返回码对应的文字说明
     * 如： 200-请求成功 201-参数格式错误 202-鉴权失败 ......等等，可扩展
     */
    private String desc;

    public CommonHttpResp(int retn, String desc) {
        this.retn = retn;
        this.desc = desc;
    }

    public CommonHttpResp() {
    }

    public int getRetn() {
        return retn;
    }

    public void setRetn(int retn) {
        this.retn = retn;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isOK() {
        return HttpStatus.SC_OK == this.retn;
    }
}
