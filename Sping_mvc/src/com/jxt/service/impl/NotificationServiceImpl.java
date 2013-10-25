package com.jxt.service.impl;

import java.util.List;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.jxt.dao.NotificationDao;
import com.jxt.domain.Notification;
import com.jxt.service.NotificationService;


/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:29
 */
@Service
public class NotificationServiceImpl implements NotificationService {

	private NotificationDao notificationDao;
	

	public Long createNotification(Notification t) {
		return this.notificationDao.insertEntity(t);
	}

	public Notification getNotification(Notification t) {
		return this.notificationDao.selectEntity(t);
	}

	public Long getNotificationCount(Notification t) {
		return this.notificationDao.selectEntityCount(t);
	}

	public List<Notification> getNotificationList(Notification t) {
		return this.notificationDao.selectEntityList(t);
	}

	public int modifyNotification(Notification t) {
		return this.notificationDao.updateEntity(t);
	}

	public int removeNotification(Notification t) {
		return this.notificationDao.deleteEntity(t);
	}

	public List<Notification> getNotificationPaginatedList(Notification t) {
		return this.notificationDao.selectEntityPaginatedList(t);
	}

}
