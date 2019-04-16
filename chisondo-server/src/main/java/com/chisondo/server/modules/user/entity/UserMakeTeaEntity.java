package com.chisondo.server.modules.user.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 12.19
 */
public class UserMakeTeaEntity  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//
	private String teamanId;
	//
	private String deviceId;
	//
	private Integer chapuId;
	//
	private String chapuName;
	//
	private Integer maxNum;
	//
	private Integer makeIndex;
	//
	private Date addTime;
	//
	private Date lastTime;
	//
	private Date cancelTime;
	//
	private Integer status;
	//
	private Integer temperature;
	//
	private Integer warm;
	//
	private Integer soak;
	//
	private Integer teaSortId;
	//
	private String teaSortName;
	//
	private Integer makeType;
	//
	private String barcode;
	//
	private Integer density;

	private Integer waterLevel;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
	public void setTeamanId(String teamanId) {
		this.teamanId = teamanId;
	}

	public String getTeamanId() {
		return teamanId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceId() {
		return deviceId;
	}
	public void setChapuId(Integer chapuId) {
		this.chapuId = chapuId;
	}

	public Integer getChapuId() {
		return chapuId;
	}
	public void setChapuName(String chapuName) {
		this.chapuName = chapuName;
	}

	public String getChapuName() {
		return chapuName;
	}
	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}

	public Integer getMaxNum() {
		return maxNum;
	}
	public void setMakeIndex(Integer makeIndex) {
		this.makeIndex = makeIndex;
	}

	public Integer getMakeIndex() {
		return makeIndex;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getAddTime() {
		return addTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public Date getLastTime() {
		return lastTime;
	}
	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public Date getCancelTime() {
		return cancelTime;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}
	public void setTemperature(Integer temperature) {
		this.temperature = temperature;
	}

	public Integer getTemperature() {
		return temperature;
	}
	public void setWarm(Integer warm) {
		this.warm = warm;
	}

	public Integer getWarm() {
		return warm;
	}
	public void setSoak(Integer soak) {
		this.soak = soak;
	}

	public Integer getSoak() {
		return soak;
	}
	public void setTeaSortId(Integer teaSortId) {
		this.teaSortId = teaSortId;
	}

	public Integer getTeaSortId() {
		return teaSortId;
	}
	public void setTeaSortName(String teaSortName) {
		this.teaSortName = teaSortName;
	}

	public String getTeaSortName() {
		return teaSortName;
	}
	public void setMakeType(Integer makeType) {
		this.makeType = makeType;
	}

	public Integer getMakeType() {
		return makeType;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getBarcode() {
		return barcode;
	}
	public void setDensity(Integer density) {
		this.density = density;
	}

	public Integer getDensity() {
		return density;
	}

	public Integer getWaterLevel() {
		return waterLevel;
	}

	public void setWaterLevel(Integer waterLevel) {
		this.waterLevel = waterLevel;
	}
}
