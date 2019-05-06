package com.chisondo.server.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;


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
	private Integer bannerId;
	//
	private Integer companyid;
	//
	private String imageurl;
	//
	private Integer bannertype;
	//
	private String linkurl;
	//
	private Date starttime;
	//
	private Date endtime;
	//
	private Integer showtime;

	public void setBannerId(Integer bannerId) {
		this.bannerId = bannerId;
	}

	public Integer getBannerId() {
		return bannerId;
	}
	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}

	public Integer getCompanyid() {
		return companyid;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public String getImageurl() {
		return imageurl;
	}
	public void setBannertype(Integer bannertype) {
		this.bannertype = bannertype;
	}

	public Integer getBannertype() {
		return bannertype;
	}
	public void setLinkurl(String linkurl) {
		this.linkurl = linkurl;
	}

	public String getLinkurl() {
		return linkurl;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getStarttime() {
		return starttime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public Date getEndtime() {
		return endtime;
	}
	public void setShowtime(Integer showtime) {
		this.showtime = showtime;
	}

	public Integer getShowtime() {
		return showtime;
	}
}
