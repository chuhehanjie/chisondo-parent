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
public class UserDeviceEntity  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//
	private String teamanId;
	//
	private Integer deviceId;
	//
	private Integer privateTag;
	//
	private Integer defaultTag;
	//
	private Date firstLinkTime;
	//
	private Date lastLinkTime;

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
	public void setPrivateTag(Integer privateTag) {
		this.privateTag = privateTag;
	}

	public Integer getPrivateTag() {
		return privateTag;
	}
	public void setDefaultTag(Integer defaultTag) {
		this.defaultTag = defaultTag;
	}

	public Integer getDefaultTag() {
		return defaultTag;
	}
	public void setFirstLinkTime(Date firstLinkTime) {
		this.firstLinkTime = firstLinkTime;
	}

	public Date getFirstLinkTime() {
		return firstLinkTime;
	}
	public void setLastLinkTime(Date lastLinkTime) {
		this.lastLinkTime = lastLinkTime;
	}

	public Date getLastLinkTime() {
		return lastLinkTime;
	}
}
