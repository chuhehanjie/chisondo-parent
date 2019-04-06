package com.chisondo.server.modules.user.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 18.19
 */
public class UserBookEntity  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//
	private String teamanId;
	//
	private Integer deviceId;
	//
	private String configCmd;
	//
	private Date processTime;
	//
	private Date logTime;
	/**
	 * 0-有效; 1-已成功执行; 2-已取消; 3-已过期且未成功执行
	 */
	private Integer validFlag;
	//
	private Integer chapuId;
	//
	private String chapuName;
	//
	private Integer informFlag;
	//
	private Integer teaSortId;
	//
	private String teaSortName;
	//预约号
	private String reservNo;

	private String reserveParam;

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
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getDeviceId() {
		return deviceId;
	}
	public void setConfigCmd(String configCmd) {
		this.configCmd = configCmd;
	}

	public String getConfigCmd() {
		return configCmd;
	}
	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}

	public Date getProcessTime() {
		return processTime;
	}
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}

	public Date getLogTime() {
		return logTime;
	}
	public void setValidFlag(Integer validFlag) {
		this.validFlag = validFlag;
	}

	public Integer getValidFlag() {
		return validFlag;
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
	public void setInformFlag(Integer informFlag) {
		this.informFlag = informFlag;
	}

	public Integer getInformFlag() {
		return informFlag;
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
	public void setReservNo(String reservNo) {
		this.reservNo = reservNo;
	}

	public String getReservNo() {
		return reservNo;
	}

	public String getReserveParam() {
		return reserveParam;
	}

	public void setReserveParam(String reserveParam) {
		this.reserveParam = reserveParam;
	}
}
