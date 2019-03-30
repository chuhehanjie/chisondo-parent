package com.chisondo.server.modules.user.entity;

import java.io.Serializable;


/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 12.19
 */
public class TeamanAdminUserEntity  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//
	private String name;
	//
	private String passwd;
	//
	private String desc;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getPasswd() {
		return passwd;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}
