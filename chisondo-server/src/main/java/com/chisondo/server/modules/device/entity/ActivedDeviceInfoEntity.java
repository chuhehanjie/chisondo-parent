package com.chisondo.server.modules.device.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 17.19
 */
public class ActivedDeviceInfoEntity  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//设备ID
	private String deviceId;
	//设备名称
	private String deviceName;
	//
	private Integer deviceTypeId;
	//
	private Date activedTime;
	//设备密码
	private String password;
	//设备颜色
	private Integer devColor;
	//
	private String termId;
	//
	private String devDesc;
	//
	private String adminName;
	//
	private String adminPhone;
	//设备部署在餐馆的id，0表示没有关联餐馆
	private Integer restId;
	//是否锁定键盘，0没有锁定，1已经锁定
	private Integer lockPanel;
	//公司ID 0-泉笙道，1-湘丰集团，2-静硒园,345….,默认 0泉笙道
	private Integer companyId;
	//1-开启提示声 2-关闭提示音
	private Integer volFlag;
	//1-优先2G网络（默认）  2-优先Wi-Fi网络
	private Integer gmsFlag;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceTypeId(Integer deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public Integer getDeviceTypeId() {
		return deviceTypeId;
	}
	public void setActivedTime(Date activedTime) {
		this.activedTime = activedTime;
	}

	public Date getActivedTime() {
		return activedTime;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}
	public void setDevColor(Integer devColor) {
		this.devColor = devColor;
	}

	public Integer getDevColor() {
		return devColor;
	}
	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getTermId() {
		return termId;
	}
	public void setDevDesc(String devDesc) {
		this.devDesc = devDesc;
	}

	public String getDevDesc() {
		return devDesc;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminName() {
		return adminName;
	}
	public void setAdminPhone(String adminPhone) {
		this.adminPhone = adminPhone;
	}

	public String getAdminPhone() {
		return adminPhone;
	}
	public void setRestId(Integer restId) {
		this.restId = restId;
	}

	public Integer getRestId() {
		return restId;
	}
	public void setLockPanel(Integer lockPanel) {
		this.lockPanel = lockPanel;
	}

	public Integer getLockPanel() {
		return lockPanel;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getCompanyId() {
		return companyId;
	}
	public void setVolFlag(Integer volFlag) {
		this.volFlag = volFlag;
	}

	public Integer getVolFlag() {
		return volFlag;
	}
	public void setGmsFlag(Integer gmsFlag) {
		this.gmsFlag = gmsFlag;
	}

	public Integer getGmsFlag() {
		return gmsFlag;
	}
}
