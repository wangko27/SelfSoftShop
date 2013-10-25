package com.jxt.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:26
 */
public class SmsInfo extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;
	
	private Integer operation_id;
	
	private Integer receive_type;
	
	private Integer receive_user_id;
	
	private Integer student_id;
	
	private Integer parent_id;
	
	private String phone_num;
	
	private Integer phone_type;
	
	private String charge_phone_num;
	
	private Integer charge_phone_type;
	
	private String content;
	
	private Date scheduled_time;
	
	private Date sent_time;
	
	private Integer status;
	
	private Integer trial_num;
	
	private String failed_reason;
	
	public SmsInfo() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getOperation_id() {
		return operation_id;
	}

	public void setOperation_id(Integer operation_id) {
		this.operation_id = operation_id;
	}
	
	public Integer getReceive_type() {
		return receive_type;
	}

	public void setReceive_type(Integer receive_type) {
		this.receive_type = receive_type;
	}
	
	public Integer getReceive_user_id() {
		return receive_user_id;
	}

	public void setReceive_user_id(Integer receive_user_id) {
		this.receive_user_id = receive_user_id;
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
	
	public Date getSent_time() {
		return sent_time;
	}

	public void setSent_time(Date sent_time) {
		this.sent_time = sent_time;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getTrial_num() {
		return trial_num;
	}

	public void setTrial_num(Integer trial_num) {
		this.trial_num = trial_num;
	}
	
	public String getFailed_reason() {
		return failed_reason;
	}

	public void setFailed_reason(String failed_reason) {
		this.failed_reason = failed_reason;
	}
	
}
