package com.jxt.service.impl;

import java.util.List;


import org.springframework.stereotype.Service;

import com.jxt.dao.SendStatusDao;
import com.jxt.domain.SendStatus;
import com.jxt.service.SendStatusService;


/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:29
 */
@Service
public class SendStatusServiceImpl implements SendStatusService {
	private SendStatusDao sendStatusDao;
	

	public Long createSendStatus(SendStatus t) {
		return this.sendStatusDao.insertEntity(t);
	}

	public SendStatus getSendStatus(SendStatus t) {
		return this.sendStatusDao.selectEntity(t);
	}

	public Long getSendStatusCount(SendStatus t) {
		return this.sendStatusDao.selectEntityCount(t);
	}

	public List<SendStatus> getSendStatusList(SendStatus t) {
		return this.sendStatusDao.selectEntityList(t);
	}

	public int modifySendStatus(SendStatus t) {
		return this.sendStatusDao.updateEntity(t);
	}

	public int removeSendStatus(SendStatus t) {
		return this.sendStatusDao.deleteEntity(t);
	}

	public List<SendStatus> getSendStatusPaginatedList(SendStatus t) {
		return this.sendStatusDao.selectEntityPaginatedList(t);
	}

}
