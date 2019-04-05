package com.chisondo.server.modules.tea.dto;

import java.io.Serializable;

public class QryTeaSpectrumReqDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private	Integer	sortId; // 茶类ID	为空即查询所有
    private	Integer	standard; // 标准茶谱标识	0-获取所有茶谱；1-获取标准茶谱；2-获取普通茶谱
    private	Integer	auth; // 已鉴定标识	0-所有；1-已鉴定；2-未鉴定
    private	int	order; // 排序	0-不排序(缺省)；1-按沏茶次数降序；2-按发布时间降序
    private	Integer	num; // 每页条数
    private	Integer	page; // 页码

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public Integer getStandard() {
        return standard;
    }

    public void setStandard(Integer standard) {
        this.standard = standard;
    }

    public Integer getAuth() {
        return auth;
    }

    public void setAuth(Integer auth) {
        this.auth = auth;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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
}
