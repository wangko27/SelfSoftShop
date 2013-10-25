package com.jxt.service;

import java.util.List;

import com.jxt.domain.Notification;



/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:28
 */
public interface NotificationService {

	Long createNotification(Notification t);

	int modifyNotification(Notification t);

	int removeNotification(Notification t);

	Notification getNotification(Notification t);

	List<Notification> getNotificationList(Notification t);

	Long getNotificationCount(Notification t);

	List<Notification> getNotificationPaginatedList(Notification t);

}