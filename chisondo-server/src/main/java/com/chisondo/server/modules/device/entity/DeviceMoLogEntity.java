package com.chisondo.server.modules.device.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 12.19
 */
public class DeviceMoLogEntity  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String logId;
	//
	private Integer deviceId;
	//
	private String moContent;
	//
	private Date moTime;
	//
	private String deviceIp;

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getLogId() {
		return logId;
	}
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getDeviceId() {
		return deviceId;
	}
	public void setMoContent(String moContent) {
		this.moContent = moContent;
	}

	public String getMoContent() {
		return moContent;
	}
	public void setMoTime(Date moTime) {
		this.moTime = moTime;
	}

	public Date getMoTime() {
		return moTime;
	}
	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}

	public String getDeviceIp() {
		return deviceIp;
	}
}
