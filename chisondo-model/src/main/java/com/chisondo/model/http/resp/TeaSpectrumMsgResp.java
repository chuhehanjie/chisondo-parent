package com.chisondo.model.http.resp;

public class TeaSpectrumMsgResp {
    private	Integer	index;
    private	Integer	chapuid;
    private	String	chapuname;
    private	Integer	maketimes;
    private DevParamMsg teaparm;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getChapuid() {
        return chapuid;
    }

    public void setChapuid(Integer chapuid) {
        this.chapuid = chapuid;
    }

    public String getChapuname() {
        return chapuname;
    }

    public void setChapuname(String chapuname) {
        this.chapuname = chapuname;
    }

    public Integer getMaketimes() {
        return maketimes;
    }

    public void setMaketimes(Integer maketimes) {
        this.maketimes = maketimes;
    }

    public DevParamMsg getTeaparm() {
        return teaparm;
    }

    public void setTeaparm(DevParamMsg teaparm) {
        this.teaparm = teaparm;
    }
}
