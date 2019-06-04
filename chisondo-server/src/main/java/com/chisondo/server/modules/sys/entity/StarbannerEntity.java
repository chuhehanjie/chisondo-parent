package com.chisondo.server.modules.sys.entity;

import com.chisondo.server.common.utils.CommonUtils;

import java.io.Serializable;


/**
 * 
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since May 06.19
 */
public class StarbannerEntity  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//
	private Integer companyId;
	//
	private String imageUrl;
	//
	private Integer bannerType;
	//
	private String linkUrl;
	//
	private String startTime;
	//
	private String endTime;
	//
	private Integer showTime;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getCompanyId() {
		return companyId;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}
	public void setBannerType(Integer bannerType) {
		this.bannerType = bannerType;
	}

	public Integer getBannerType() {
		return bannerType;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getLinkUrl() {
		return linkUrl;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStartTime() {
		return CommonUtils.convertDateStr(startTime);
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getEndTime() {
		return CommonUtils.convertDateStr(endTime);
	}
	public void setShowTime(Integer showTime) {
		this.showTime = showTime;
	}

	public Integer getShowTime() {
		return showTime;
	}
}
