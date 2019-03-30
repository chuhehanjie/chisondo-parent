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
public class AppChapuMineEntity  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer userId;
	//
	private Integer chapuId;
	//
	private Integer flag;
	//
	private Date operTime;
	//
	private Integer useTimes;
	//
	private Date lastUseTime;

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserId() {
		return userId;
	}
	public void setChapuId(Integer chapuId) {
		this.chapuId = chapuId;
	}

	public Integer getChapuId() {
		return chapuId;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getFlag() {
		return flag;
	}
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	public Date getOperTime() {
		return operTime;
	}
	public void setUseTimes(Integer useTimes) {
		this.useTimes = useTimes;
	}

	public Integer getUseTimes() {
		return useTimes;
	}
	public void setLastUseTime(Date lastUseTime) {
		this.lastUseTime = lastUseTime;
	}

	public Date getLastUseTime() {
		return lastUseTime;
	}
}
