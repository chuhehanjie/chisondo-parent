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
public class TeamanUserEntity  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//
	private String teamanId;
	//
	private String userMobileNo;
	//
	private Date regTime;
	//用户微信昵称
	private String userName;
	//微信唯一号
	private String openid;
	//用户微信图像
	private String userImg;
	//数据来源 0:APP;1:微信小程序
	private Integer dataSource;
	//公司ID 0-泉笙道，1-湘丰集团，2-静硒园,345….,默认-1，表示还没有绑定企业，第一次时修改，不为-1时，不允许再修改
	private Integer companyId;

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
	public void setUserMobileNo(String userMobileNo) {
		this.userMobileNo = userMobileNo;
	}

	public String getUserMobileNo() {
		return userMobileNo;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public Date getRegTime() {
		return regTime;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getOpenid() {
		return openid;
	}
	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	public String getUserImg() {
		return userImg;
	}
	public void setDataSource(Integer dataSource) {
		this.dataSource = dataSource;
	}

	public Integer getDataSource() {
		return dataSource;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getCompanyId() {
		return companyId;
	}
}
