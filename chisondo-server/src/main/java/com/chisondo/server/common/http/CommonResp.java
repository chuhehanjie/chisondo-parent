package com.chisondo.server.common.http;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.chisondo.model.http.HttpStatus;

import java.io.Serializable;

public class CommonResp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 返回码
     */
    private Integer retn;
    /**
     * 返回描述
     */
    private String desc;
    /**
     * 响应体
     * 使用json格式转换成字符串，并进行加密，形成密文,具体参数见具体接口
     */
    private String bizBody;

    public static CommonResp ok(Object obj) {
        return new CommonResp(HttpStatus.SC_OK, "success", JSONObject.toJSONString(obj, SerializerFeature.WriteMapNullValue));
    }

    public static CommonResp error(String errMsg) {
        CommonResp resp = new CommonResp(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        resp.setDesc(errMsg);
        return resp;
    }

    public static CommonResp error(Integer errCode, String errMsg) {
        CommonResp resp = new CommonResp(errCode);
        resp.setDesc(errMsg);
        return resp;
    }

    public CommonResp() {
    }

    public CommonResp(Integer retn) {
        this.retn = retn;
    }

    public CommonResp(Integer retn, String desc) {
        this.retn = retn;
        this.desc = desc;
    }

    public CommonResp(Integer retn, String desc, String bizBody) {
        this.retn = retn;
        this.desc = desc;
        this.bizBody = bizBody;
    }

    public Integer getRetn() {
        return retn;
    }

    public void setRetn(Integer retn) {
        this.retn = retn;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBizBody() {
        return bizBody;
    }

    public void setBizBody(String bizBody) {
        this.bizBody = bizBody;
    }

    public static CommonResp ok() {
        return new CommonResp(HttpStatus.SC_OK, "success", null);
    }
}
