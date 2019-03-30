package com.chisondo.server.modules.sys.entity;

import java.io.Serializable;


/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 17.19
 */
public class CompanyEntity  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//公司id
	private Integer id;
	//公司名称
	private String companyName;
	//公司地址
	private String companyAddress;
	//联系人
	private String relaName;
	//联系电话
	private String relaPhone;
	//公司网址
	private String website;
	//公司对应key信息
	private String key;
	//备注信息
	private String remark;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setRelaName(String relaName) {
		this.relaName = relaName;
	}

	public String getRelaName() {
		return relaName;
	}
	public void setRelaPhone(String relaPhone) {
		this.relaPhone = relaPhone;
	}

	public String getRelaPhone() {
		return relaPhone;
	}
	public void setWebsite(String website) {
		this.website = website;
	}

	public String getWebsite() {
		return website;
	}
	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
	}
}
