package com.jxt.service.impl;

import java.util.List;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.jxt.dao.SmsInfoDao;
import com.jxt.domain.SmsInfo;
import com.jxt.service.SmsInfoService;


/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:29
 */
@Service
public class SmsInfoServiceImpl implements SmsInfoService {

	private SmsInfoDao smsInfoDao;
	

	public Long createSmsInfo(SmsInfo t) {
		return this.smsInfoDao.insertEntity(t);
	}

	public SmsInfo getSmsInfo(SmsInfo t) {
		return this.smsInfoDao.selectEntity(t);
	}

	public Long getSmsInfoCount(SmsInfo t) {
		return this.smsInfoDao.selectEntityCount(t);
	}

	public List<SmsInfo> getSmsInfoList(SmsInfo t) {
		return this.smsInfoDao.selectEntityList(t);
	}

	public int modifySmsInfo(SmsInfo t) {
		return this.smsInfoDao.updateEntity(t);
	}

	public int removeSmsInfo(SmsInfo t) {
		return this.smsInfoDao.deleteEntity(t);
	}

	public List<SmsInfo> getSmsInfoPaginatedList(SmsInfo t) {
		return this.smsInfoDao.selectEntityPaginatedList(t);
	}

}
