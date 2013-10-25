package com.jxt.service.impl;

import java.util.List;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.jxt.dao.SmsOperationHistoryDao;
import com.jxt.domain.SmsOperationHistory;
import com.jxt.service.SmsOperationHistoryService;


/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:29
 */
@Service
public class SmsOperationHistoryServiceImpl implements SmsOperationHistoryService {

	private SmsOperationHistoryDao smsOperationHistoryDao;
	

	public Long createSmsOperationHistory(SmsOperationHistory t) {
		return this.smsOperationHistoryDao.insertEntity(t);
	}

	public SmsOperationHistory getSmsOperationHistory(SmsOperationHistory t) {
		return this.smsOperationHistoryDao.selectEntity(t);
	}

	public Long getSmsOperationHistoryCount(SmsOperationHistory t) {
		return this.smsOperationHistoryDao.selectEntityCount(t);
	}

	public List<SmsOperationHistory> getSmsOperationHistoryList(SmsOperationHistory t) {
		return this.smsOperationHistoryDao.selectEntityList(t);
	}

	public int modifySmsOperationHistory(SmsOperationHistory t) {
		return this.smsOperationHistoryDao.updateEntity(t);
	}

	public int removeSmsOperationHistory(SmsOperationHistory t) {
		return this.smsOperationHistoryDao.deleteEntity(t);
	}

	public List<SmsOperationHistory> getSmsOperationHistoryPaginatedList(SmsOperationHistory t) {
		return this.smsOperationHistoryDao.selectEntityPaginatedList(t);
	}

}
