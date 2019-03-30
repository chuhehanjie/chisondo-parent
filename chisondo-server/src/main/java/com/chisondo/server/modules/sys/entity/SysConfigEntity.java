package com.chisondo.server.modules.sys.entity;


import java.io.Serializable;

/**
 * 系统配置信息
 * 
 */
public class SysConfigEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String key;
	private String value;
	private String remark;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
