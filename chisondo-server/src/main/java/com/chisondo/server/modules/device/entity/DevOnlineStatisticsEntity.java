package com.chisondo.server.modules.device.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 12.19
 */
public class DevOnlineStatisticsEntity  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer rowId;
	//
	private Integer onlineNum;
	//
	private Date saveTime;

	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}

	public Integer getRowId() {
		return rowId;
	}
	public void setOnlineNum(Integer onlineNum) {
		this.onlineNum = onlineNum;
	}

	public Integer getOnlineNum() {
		return onlineNum;
	}
	public void setSaveTime(Date saveTime) {
		this.saveTime = saveTime;
	}

	public Date getSaveTime() {
		return saveTime;
	}
}
