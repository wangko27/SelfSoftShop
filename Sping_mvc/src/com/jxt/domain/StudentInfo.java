package com.jxt.domain;

import java.io.Serializable;

/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:27
 */
public class StudentInfo extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;
	
	private String std_num;
	
	private String name;
	
	private String std_phone_num;
	
	private Integer std_phone_type;
	
	private Integer std_phone_send_status;
	
	private String parent_id;
	
	private Integer class_id;
	
	private Integer status;
	
	public StudentInfo() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getStd_num() {
		return std_num;
	}

	public void setStd_num(String std_num) {
		this.std_num = std_num;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getStd_phone_num() {
		return std_phone_num;
	}

	public void setStd_phone_num(String std_phone_num) {
		this.std_phone_num = std_phone_num;
	}
	
	public Integer getStd_phone_type() {
		return std_phone_type;
	}

	public void setStd_phone_type(Integer std_phone_type) {
		this.std_phone_type = std_phone_type;
	}
	
	public Integer getStd_phone_send_status() {
		return std_phone_send_status;
	}

	public void setStd_phone_send_status(Integer std_phone_send_status) {
		this.std_phone_send_status = std_phone_send_status;
	}
	
	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	
	public Integer getClass_id() {
		return class_id;
	}

	public void setClass_id(Integer class_id) {
		this.class_id = class_id;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
