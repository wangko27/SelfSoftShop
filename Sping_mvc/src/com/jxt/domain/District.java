package com.jxt.domain;

import java.io.Serializable;

/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:26
 */
public class District extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;
	
	private String name;
	
	private Integer city_id;
	
	public District() {

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
	
	public Integer getCity_id() {
		return city_id;
	}

	public void setCity_id(Integer city_id) {
		this.city_id = city_id;
	}
	
}
