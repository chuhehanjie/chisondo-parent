package com.chisondo.server.modules.device.entity;

import java.io.Serializable;


/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 12.19
 */
public class DeviceTypeEntity  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer typeId;
	//
	private String typeName;

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}
}
