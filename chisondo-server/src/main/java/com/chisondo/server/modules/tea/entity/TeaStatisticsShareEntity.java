package com.chisondo.server.modules.tea.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 12.19
 */
public class TeaStatisticsShareEntity  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer rowId;
	//
	private String userId;
	//
	private String data;
	//
	private Date saveTime;

	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}

	public Integer getRowId() {
		return rowId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}
	public void setData(String data) {
		this.data = data;
	}

	public String getData() {
		return data;
	}
	public void setSaveTime(Date saveTime) {
		this.saveTime = saveTime;
	}

	public Date getSaveTime() {
		return saveTime;
	}
}
