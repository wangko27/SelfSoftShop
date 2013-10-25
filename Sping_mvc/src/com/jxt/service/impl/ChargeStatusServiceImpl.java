package com.jxt.service.impl;

import java.util.List;


import org.springframework.stereotype.Service;

import com.jxt.dao.ChargeStatusDao;
import com.jxt.domain.ChargeStatus;
import com.jxt.service.ChargeStatusService;


/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:29
 */
@Service
public class ChargeStatusServiceImpl implements ChargeStatusService {

	private ChargeStatusDao chargeStatusDao;
	

	public Long createChargeStatus(ChargeStatus t) {
		return this.chargeStatusDao.insertEntity(t);
	}

	public ChargeStatus getChargeStatus(ChargeStatus t) {
		return this.chargeStatusDao.selectEntity(t);
	}

	public Long getChargeStatusCount(ChargeStatus t) {
		return this.chargeStatusDao.selectEntityCount(t);
	}

	public List<ChargeStatus> getChargeStatusList(ChargeStatus t) {
		return this.chargeStatusDao.selectEntityList(t);
	}

	public int modifyChargeStatus(ChargeStatus t) {
		return this.chargeStatusDao.updateEntity(t);
	}

	public int removeChargeStatus(ChargeStatus t) {
		return this.chargeStatusDao.deleteEntity(t);
	}

	public List<ChargeStatus> getChargeStatusPaginatedList(ChargeStatus t) {
		return this.chargeStatusDao.selectEntityPaginatedList(t);
	}

}
