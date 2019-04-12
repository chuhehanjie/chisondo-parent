package com.chisondo.server.modules.tea.entity;

import java.io.Serializable;


/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 17.19
 */
public class AppChapuParaEntity  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer chapuId;
	//
	private Integer number;
	//
	private Integer temp;
	//
	private Integer dura;

	public AppChapuParaEntity() {
	}

	public AppChapuParaEntity(Integer chapuId, Integer number, Integer temp, Integer dura) {
		this.chapuId = chapuId;
		this.number = number;
		this.temp = temp;
		this.dura = dura;
	}

	public void setChapuId(Integer chapuId) {
		this.chapuId = chapuId;
	}

	public Integer getChapuId() {
		return chapuId;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getNumber() {
		return number;
	}
	public void setTemp(Integer temp) {
		this.temp = temp;
	}

	public Integer getTemp() {
		return temp;
	}
	public void setDura(Integer dura) {
		this.dura = dura;
	}

	public Integer getDura() {
		return dura;
	}
}
