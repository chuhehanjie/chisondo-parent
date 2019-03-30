package com.chisondo.server.modules.user.entity;

import java.io.Serializable;


/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 12.19
 */
public class UserSchemeEntity  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer schemeId;
	//
	private Integer deviceId;
	//
	private Integer schemeNo;
	//
	private Integer heatTemp;
	//
	private Integer powerLevel;
	//
	private Integer makeTemp;
	//
	private Integer makeDura;
	//
	private Integer warmTemp;
	//
	private Integer warmDura;

	public void setSchemeId(Integer schemeId) {
		this.schemeId = schemeId;
	}

	public Integer getSchemeId() {
		return schemeId;
	}
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getDeviceId() {
		return deviceId;
	}
	public void setSchemeNo(Integer schemeNo) {
		this.schemeNo = schemeNo;
	}

	public Integer getSchemeNo() {
		return schemeNo;
	}
	public void setHeatTemp(Integer heatTemp) {
		this.heatTemp = heatTemp;
	}

	public Integer getHeatTemp() {
		return heatTemp;
	}
	public void setPowerLevel(Integer powerLevel) {
		this.powerLevel = powerLevel;
	}

	public Integer getPowerLevel() {
		return powerLevel;
	}
	public void setMakeTemp(Integer makeTemp) {
		this.makeTemp = makeTemp;
	}

	public Integer getMakeTemp() {
		return makeTemp;
	}
	public void setMakeDura(Integer makeDura) {
		this.makeDura = makeDura;
	}

	public Integer getMakeDura() {
		return makeDura;
	}
	public void setWarmTemp(Integer warmTemp) {
		this.warmTemp = warmTemp;
	}

	public Integer getWarmTemp() {
		return warmTemp;
	}
	public void setWarmDura(Integer warmDura) {
		this.warmDura = warmDura;
	}

	public Integer getWarmDura() {
		return warmDura;
	}
}
