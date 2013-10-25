package com.jxt.domain;

import java.io.Serializable;

/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:26
 */
public class ParentInfo extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;
	
	private String name;
	
	private String par_phone_num;
	
	private Integer par_phone_type;
	
	private String par_charge_phone_num;
	
	private Integer par_charge_phone_type;
	
	private Integer student_id;
	
	private Integer status;
	
	public ParentInfo() {

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
	
	public String getPar_phone_num() {
		return par_phone_num;
	}

	public void setPar_phone_num(String par_phone_num) {
		this.par_phone_num = par_phone_num;
	}
	
	public Integer getPar_phone_type() {
		return par_phone_type;
	}

	public void setPar_phone_type(Integer par_phone_type) {
		this.par_phone_type = par_phone_type;
	}
	
	public String getPar_charge_phone_num() {
		return par_charge_phone_num;
	}

	public void setPar_charge_phone_num(String par_charge_phone_num) {
		this.par_charge_phone_num = par_charge_phone_num;
	}
	
	public Integer getPar_charge_phone_type() {
		return par_charge_phone_type;
	}

	public void setPar_charge_phone_type(Integer par_charge_phone_type) {
		this.par_charge_phone_type = par_charge_phone_type;
	}
	
	public Integer getStudent_id() {
		return student_id;
	}

	public void setStudent_id(Integer student_id) {
		this.student_id = student_id;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
