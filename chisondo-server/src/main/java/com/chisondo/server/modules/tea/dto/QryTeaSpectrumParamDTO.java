package com.chisondo.server.modules.tea.dto;

import java.io.Serializable;

public class QryTeaSpectrumParamDTO implements Serializable{
    private static final long serialVersionUID = 1L;

    private Integer water;
    private Integer temp;
    private Integer dura;

    public Integer getWater() {
        return water;
    }

    public void setWater(Integer water) {
        this.water = water;
    }

    public Integer getTemp() {
        return temp;
    }

    public void setTemp(Integer temp) {
        this.temp = temp;
    }

    public Integer getDura() {
        return dura;
    }

    public void setDura(Integer dura) {
        this.dura = dura;
    }
}
