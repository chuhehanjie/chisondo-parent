package com.chisondo.server.common.http;

import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.Map;

public class CommonReq implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 生成规则
     * 如：鉴权方式 acckey  =  md5（reqsrc + md5（key +  timestamp(后4位)）
     */
    private String acckey;
    /**
     * 请求来源
     * 0-泉笙道，1-湘丰集团，2-静硒园,345….,默认 0泉笙道
     */
    private Integer reqsrc;
    /**
     * 时间戳
     * 如：198298912
     */
    private String timestamp;
    /**
     * 接口请求体
     * 使用json格式转换成字符串，并进行加密，形成密文,具体参数见个接口
     */
    private String bizBody;

    /**
     * 是否老设备
     */
    private boolean isOldDev;

    private Map<String, Object> extAttrs = Maps.newHashMap();

    public String getAcckey() {
        return acckey;
    }

    public void setAcckey(String acckey) {
        this.acckey = acckey;
    }

    public Integer getReqsrc() {
        return reqsrc;
    }

    public void setReqsrc(Integer reqsrc) {
        this.reqsrc = reqsrc;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getBizBody() {
        return bizBody;
    }

    public void setBizBody(String bizBody) {
        this.bizBody = bizBody;
    }

    public void addAttr(String key, Object obj) {
        this.extAttrs.put(key, obj);
    }

    public Object getAttrByKey(String key) {
        return this.extAttrs.get(key);
    }

    public boolean isOldDev() {
        return isOldDev;
    }

    public void setOldDev(boolean oldDev) {
        isOldDev = oldDev;
    }
}
