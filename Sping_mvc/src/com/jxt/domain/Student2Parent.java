package com.jxt.domain;

import java.io.Serializable;

/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:26
 */
public class Student2Parent extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer student_id;
	
	private Integer parent_id;
	
	private Integer par_phone_send_status;
	
	public Student2Parent() {

	}

	public Integer getStudent_id() {
		return student_id;
	}

	public void setStudent_id(Integer student_id) {
		this.student_id = student_id;
	}
	
	public Integer getParent_id() {
		return parent_id;
	}

	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}
	
	public Integer getPar_phone_send_status() {
		return par_phone_send_status;
	}

	public void setPar_phone_send_status(Integer par_phone_send_status) {
		this.par_phone_send_status = par_phone_send_status;
	}
	
}
