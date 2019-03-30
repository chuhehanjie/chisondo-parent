package com.chisondo.server.modules.tea.entity;

import java.io.Serializable;


/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 17.19
 */
public class AppTeaSortEntity  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer sortId;
	//
	private String name;
	//
	private String picPath;
	//
	private String desc;
	//
	private Integer tempMin;
	//
	private Integer tempMax;
	//
	private Integer duraMin;
	//
	private Integer duraMax;
	//
	private Integer isDefault;

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

	public Integer getSortId() {
		return sortId;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getPicPath() {
		return picPath;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
	public void setTempMin(Integer tempMin) {
		this.tempMin = tempMin;
	}

	public Integer getTempMin() {
		return tempMin;
	}
	public void setTempMax(Integer tempMax) {
		this.tempMax = tempMax;
	}

	public Integer getTempMax() {
		return tempMax;
	}
	public void setDuraMin(Integer duraMin) {
		this.duraMin = duraMin;
	}

	public Integer getDuraMin() {
		return duraMin;
	}
	public void setDuraMax(Integer duraMax) {
		this.duraMax = duraMax;
	}

	public Integer getDuraMax() {
		return duraMax;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	public Integer getIsDefault() {
		return isDefault;
	}
}
