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
public class UserDeviceMtLogEntity  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer logId;
	//
	private String teamanId;
	//
	private Integer deviceId;
	//
	private String cmdContent;
	//
	private String userIp;
	//
	private String deviceIp;
	//
	private Date mtTime;

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public Integer getLogId() {
		return logId;
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
	public void setCmdContent(String cmdContent) {
		this.cmdContent = cmdContent;
	}

	public String getCmdContent() {
		return cmdContent;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getUserIp() {
		return userIp;
	}
	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}

	public String getDeviceIp() {
		return deviceIp;
	}
	public void setMtTime(Date mtTime) {
		this.mtTime = mtTime;
	}

	public Date getMtTime() {
		return mtTime;
	}
}
