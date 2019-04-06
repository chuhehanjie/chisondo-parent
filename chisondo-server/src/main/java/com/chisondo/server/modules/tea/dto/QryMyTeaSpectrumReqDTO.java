package com.chisondo.server.modules.tea.dto;

import java.io.Serializable;

/**
 * 查询我的茶谱校验器
 * @author ding.zhong
 */
public class QryMyTeaSpectrumReqDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String phoneNum; // 0-创建的；1-保存（未创建）；2-我使用过的；缺省不区分类型
    private	Integer	type; // 已鉴定标识	0-所有；1-已鉴定；2-未鉴定
    private	Integer	num; // 每页条数
    private	Integer	page; // 页码

    private Long userId;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
