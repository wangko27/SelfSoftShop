package com.jxt.domain;

import java.io.Serializable;

/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:27
 */
public class UserInfo extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;
	
	private String name;
	
	private String password;
	
	private String phone_num;
	
	private Integer phone_type;
	
	private String charge_phone_num;
	
	private Integer charge_phone_type;
	
	private Integer status;
	
	private Integer sms_flag;
	
	public UserInfo() {

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
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPhone_num() {
		return phone_num;
	}

	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}
	
	public Integer getPhone_type() {
		return phone_type;
	}

	public void setPhone_type(Integer phone_type) {
		this.phone_type = phone_type;
	}
	
	public String getCharge_phone_num() {
		return charge_phone_num;
	}

	public void setCharge_phone_num(String charge_phone_num) {
		this.charge_phone_num = charge_phone_num;
	}
	
	public Integer getCharge_phone_type() {
		return charge_phone_type;
	}

	public void setCharge_phone_type(Integer charge_phone_type) {
		this.charge_phone_type = charge_phone_type;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getSms_flag() {
		return sms_flag;
	}

	public void setSms_flag(Integer sms_flag) {
		this.sms_flag = sms_flag;
	}
	
}
