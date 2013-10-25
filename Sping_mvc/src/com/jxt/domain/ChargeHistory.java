package com.jxt.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:26
 */
public class ChargeHistory extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;
	
	private Integer student_id;
	
	private Integer parent_id;
	
	private String phone_num;
	
	private Integer phone_type;
	
	private String charge_phone_num;
	
	private Integer charge_phone_type;
	
	private Date effect_time;
	
	private Date expired_time;
	
	private Integer par_phone_send_status;
	
	private String reason;
	
	public ChargeHistory() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
	
	public Date getEffect_time() {
		return effect_time;
	}

	public void setEffect_time(Date effect_time) {
		this.effect_time = effect_time;
	}
	
	public Date getExpired_time() {
		return expired_time;
	}

	public void setExpired_time(Date expired_time) {
		this.expired_time = expired_time;
	}
	
	public Integer getPar_phone_send_status() {
		return par_phone_send_status;
	}

	public void setPar_phone_send_status(Integer par_phone_send_status) {
		this.par_phone_send_status = par_phone_send_status;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
