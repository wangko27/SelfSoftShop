package com.jxt.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:26
 */
public class SmsOperationHistory extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;
	
	private Integer operation_user_id;
	
	private Integer type;
	
	private Integer school_id;
	
	private Integer class_id;
	
	private String content;
	
	private Date scheduled_time;
	
	public SmsOperationHistory() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getOperation_user_id() {
		return operation_user_id;
	}

	public void setOperation_user_id(Integer operation_user_id) {
		this.operation_user_id = operation_user_id;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public Integer getSchool_id() {
		return school_id;
	}

	public void setSchool_id(Integer school_id) {
		this.school_id = school_id;
	}
	
	public Integer getClass_id() {
		return class_id;
	}

	public void setClass_id(Integer class_id) {
		this.class_id = class_id;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public Date getScheduled_time() {
		return scheduled_time;
	}

	public void setScheduled_time(Date scheduled_time) {
		this.scheduled_time = scheduled_time;
	}
	
}
