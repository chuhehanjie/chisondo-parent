package com.chisondo.server.modules.sys.dto;

import java.io.Serializable;

public class CompanyConfigDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String apiBaseUrl;

    private Integer companyId;

    private String companyKey;

    private String tenxunKey;

    public String getApiBaseUrl() {
        return apiBaseUrl;
    }

    public void setApiBaseUrl(String apiBaseUrl) {
        this.apiBaseUrl = apiBaseUrl;
    }

    public String getCompanyKey() {
        return companyKey;
    }

    public void setCompanyKey(String companyKey) {
        this.companyKey = companyKey;
    }

    public String getTenxunKey() {
        return tenxunKey;
    }

    public void setTenxunKey(String tenxunKey) {
        this.tenxunKey = tenxunKey;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
