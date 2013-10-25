package com.jxt.domain;

import java.io.Serializable;

/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:26
 */
public class RoleInfo extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;
	
	private String name;
	
	private Integer status;
	
	public RoleInfo() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
