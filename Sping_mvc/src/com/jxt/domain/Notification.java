package com.jxt.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:26
 */
public class Notification extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer id;
	
	private String subject;
	
	private String content;
	
	private Integer from_user_id;
	
	private Integer to_user_id;
	
	private Integer status;
	
	private Date created_date;
	
	public Notification() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public Integer getFrom_user_id() {
		return from_user_id;
	}

	public void setFrom_user_id(Integer from_user_id) {
		this.from_user_id = from_user_id;
	}
	
	public Integer getTo_user_id() {
		return to_user_id;
	}

	public void setTo_user_id(Integer to_user_id) {
		this.to_user_id = to_user_id;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	
}
