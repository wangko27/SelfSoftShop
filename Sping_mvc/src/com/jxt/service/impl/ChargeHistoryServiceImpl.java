package com.jxt.service.impl;

import java.util.List;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.jxt.dao.ChargeHistoryDao;
import com.jxt.domain.ChargeHistory;
import com.jxt.service.ChargeHistoryService;


/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:29
 */
@Service
public class ChargeHistoryServiceImpl implements ChargeHistoryService {

	private ChargeHistoryDao chargeHistoryDao;
	

	public Long createChargeHistory(ChargeHistory t) {
		return this.chargeHistoryDao.insertEntity(t);
	}

	public ChargeHistory getChargeHistory(ChargeHistory t) {
		return this.chargeHistoryDao.selectEntity(t);
	}

	public Long getChargeHistoryCount(ChargeHistory t) {
		return this.chargeHistoryDao.selectEntityCount(t);
	}

	public List<ChargeHistory> getChargeHistoryList(ChargeHistory t) {
		return this.chargeHistoryDao.selectEntityList(t);
	}

	public int modifyChargeHistory(ChargeHistory t) {
		return this.chargeHistoryDao.updateEntity(t);
	}

	public int removeChargeHistory(ChargeHistory t) {
		return this.chargeHistoryDao.deleteEntity(t);
	}

	public List<ChargeHistory> getChargeHistoryPaginatedList(ChargeHistory t) {
		return this.chargeHistoryDao.selectEntityPaginatedList(t);
	}

}
