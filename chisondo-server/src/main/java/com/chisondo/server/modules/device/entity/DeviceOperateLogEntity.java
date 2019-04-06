package com.chisondo.server.modules.device.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 设备操作日志表
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 17.19
 */
public class DeviceOperateLogEntity  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//设备ID
	private Long id;
	//ID
	private String deviceId;
	//用户唯一编号
	private String teamanId;
	//用户手机号
	private String userMobileNo;
	//操作类型
	private Integer operType;
	//请求操作内容
	private String reqContent;
	//响应操作内容
	private String resContent;
	//开始时间
	private Date startTime;
	//修改时间
	private Date endTime;
	//执行结果
	private Integer operResult;
	//描述
	private String desc;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceId() {
		return deviceId;
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
	public void setOperType(Integer operType) {
		this.operType = operType;
	}

	public Integer getOperType() {
		return operType;
	}
	public void setReqContent(String reqContent) {
		this.reqContent = reqContent;
	}

	public String getReqContent() {
		return reqContent;
	}
	public void setResContent(String resContent) {
		this.resContent = resContent;
	}

	public String getResContent() {
		return resContent;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStartTime() {
		return startTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getEndTime() {
		return endTime;
	}
	public void setOperResult(Integer operResult) {
		this.operResult = operResult;
	}

	public Integer getOperResult() {
		return operResult;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
