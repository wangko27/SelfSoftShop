package com.jxt.service;

import java.util.List;

import com.jxt.domain.SendStatus;



/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:28
 */
public interface SendStatusService {

	Long createSendStatus(SendStatus t);

	int modifySendStatus(SendStatus t);

	int removeSendStatus(SendStatus t);

	SendStatus getSendStatus(SendStatus t);

	List<SendStatus> getSendStatusList(SendStatus t);

	Long getSendStatusCount(SendStatus t);

	List<SendStatus> getSendStatusPaginatedList(SendStatus t);

}