package com.chisondo.server.modules.tea.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 17.19
 */
public class AppChapuEntity  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer chapuId;
	//
	private Integer userId;
	//
	private String name;
	//
	private String image;
	//
	private Integer sort;
	//
	private Integer shape;
	//
	private String brand;
	//
	private Integer amount;
	//
	private Integer awake;
	//
	private Integer makeTimes;
	//
	private String desc;
	//
	private Integer commentTimes;
	//
	private Integer belikeTimes;
	//
	private Date publicTime;
	//
	private Integer standard;
	//
	private Integer temp;
	//
	private Integer status;
	//
	private Integer useTimes;
	//
	private Integer dislikeTimes;
	//
	private Integer variety;
	//
	private String addrProvince;
	//
	private String addrCity;
	//
	private String addrDistrict;
	//
	private String addrDetail;
	//
	private Integer productYear;
	//
	private Integer productSeason;
	//
	private Integer youpinId;
	//
	private Integer teaBrandId;
	//
	private String teaName;
	//
	private String merchantName;
	//
	private Integer teaPrice;
	//
	private Integer texture;
	//
	private Integer authAttr;
	//
	private Integer officialChked;
	//
	private Integer topFlag;

	public void setChapuId(Integer chapuId) {
		this.chapuId = chapuId;
	}

	public Integer getChapuId() {
		return chapuId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserId() {
		return userId;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public String getImage() {
		return image;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getSort() {
		return sort;
	}
	public void setShape(Integer shape) {
		this.shape = shape;
	}

	public Integer getShape() {
		return shape;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getBrand() {
		return brand;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getAmount() {
		return amount;
	}
	public void setAwake(Integer awake) {
		this.awake = awake;
	}

	public Integer getAwake() {
		return awake;
	}
	public void setMakeTimes(Integer makeTimes) {
		this.makeTimes = makeTimes;
	}

	public Integer getMakeTimes() {
		return makeTimes;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
	public void setCommentTimes(Integer commentTimes) {
		this.commentTimes = commentTimes;
	}

	public Integer getCommentTimes() {
		return commentTimes;
	}
	public void setBelikeTimes(Integer belikeTimes) {
		this.belikeTimes = belikeTimes;
	}

	public Integer getBelikeTimes() {
		return belikeTimes;
	}
	public void setPublicTime(Date publicTime) {
		this.publicTime = publicTime;
	}

	public Date getPublicTime() {
		return publicTime;
	}
	public void setStandard(Integer standard) {
		this.standard = standard;
	}

	public Integer getStandard() {
		return standard;
	}
	public void setTemp(Integer temp) {
		this.temp = temp;
	}

	public Integer getTemp() {
		return temp;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}
	public void setUseTimes(Integer useTimes) {
		this.useTimes = useTimes;
	}

	public Integer getUseTimes() {
		return useTimes;
	}
	public void setDislikeTimes(Integer dislikeTimes) {
		this.dislikeTimes = dislikeTimes;
	}

	public Integer getDislikeTimes() {
		return dislikeTimes;
	}
	public void setVariety(Integer variety) {
		this.variety = variety;
	}

	public Integer getVariety() {
		return variety;
	}
	public void setAddrProvince(String addrProvince) {
		this.addrProvince = addrProvince;
	}

	public String getAddrProvince() {
		return addrProvince;
	}
	public void setAddrCity(String addrCity) {
		this.addrCity = addrCity;
	}

	public String getAddrCity() {
		return addrCity;
	}
	public void setAddrDistrict(String addrDistrict) {
		this.addrDistrict = addrDistrict;
	}

	public String getAddrDistrict() {
		return addrDistrict;
	}
	public void setAddrDetail(String addrDetail) {
		this.addrDetail = addrDetail;
	}

	public String getAddrDetail() {
		return addrDetail;
	}
	public void setProductYear(Integer productYear) {
		this.productYear = productYear;
	}

	public Integer getProductYear() {
		return productYear;
	}
	public void setProductSeason(Integer productSeason) {
		this.productSeason = productSeason;
	}

	public Integer getProductSeason() {
		return productSeason;
	}
	public void setYoupinId(Integer youpinId) {
		this.youpinId = youpinId;
	}

	public Integer getYoupinId() {
		return youpinId;
	}
	public void setTeaBrandId(Integer teaBrandId) {
		this.teaBrandId = teaBrandId;
	}

	public Integer getTeaBrandId() {
		return teaBrandId;
	}
	public void setTeaName(String teaName) {
		this.teaName = teaName;
	}

	public String getTeaName() {
		return teaName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantName() {
		return merchantName;
	}
	public void setTeaPrice(Integer teaPrice) {
		this.teaPrice = teaPrice;
	}

	public Integer getTeaPrice() {
		return teaPrice;
	}
	public void setTexture(Integer texture) {
		this.texture = texture;
	}

	public Integer getTexture() {
		return texture;
	}
	public void setAuthAttr(Integer authAttr) {
		this.authAttr = authAttr;
	}

	public Integer getAuthAttr() {
		return authAttr;
	}
	public void setOfficialChked(Integer officialChked) {
		this.officialChked = officialChked;
	}

	public Integer getOfficialChked() {
		return officialChked;
	}
	public void setTopFlag(Integer topFlag) {
		this.topFlag = topFlag;
	}

	public Integer getTopFlag() {
		return topFlag;
	}
}
