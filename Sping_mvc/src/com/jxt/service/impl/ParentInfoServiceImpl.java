package com.jxt.service.impl;

import java.util.List;


import org.springframework.stereotype.Service;

import com.jxt.dao.ParentInfoDao;
import com.jxt.domain.ParentInfo;
import com.jxt.service.ParentInfoService;


/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:29
 */
@Service
public class ParentInfoServiceImpl implements ParentInfoService {

	private ParentInfoDao parentInfoDao;
	

	public Long createParentInfo(ParentInfo t) {
		return this.parentInfoDao.insertEntity(t);
	}

	public ParentInfo getParentInfo(ParentInfo t) {
		return this.parentInfoDao.selectEntity(t);
	}

	public Long getParentInfoCount(ParentInfo t) {
		return this.parentInfoDao.selectEntityCount(t);
	}

	public List<ParentInfo> getParentInfoList(ParentInfo t) {
		return this.parentInfoDao.selectEntityList(t);
	}

	public int modifyParentInfo(ParentInfo t) {
		return this.parentInfoDao.updateEntity(t);
	}

	public int removeParentInfo(ParentInfo t) {
		return this.parentInfoDao.deleteEntity(t);
	}

	public List<ParentInfo> getParentInfoPaginatedList(ParentInfo t) {
		return this.parentInfoDao.selectEntityPaginatedList(t);
	}

}
