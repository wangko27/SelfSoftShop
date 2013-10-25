package com.jxt.service.impl;

import java.util.List;


import org.springframework.stereotype.Service;

import com.jxt.dao.SchoolInfoDao;
import com.jxt.domain.SchoolInfo;
import com.jxt.service.SchoolInfoService;


/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:29
 */
@Service
public class SchoolInfoServiceImpl implements SchoolInfoService {

	private SchoolInfoDao schoolInfoDao;
	

	public Long createSchoolInfo(SchoolInfo t) {
		return this.schoolInfoDao.insertEntity(t);
	}

	public SchoolInfo getSchoolInfo(SchoolInfo t) {
		return this.schoolInfoDao.selectEntity(t);
	}

	public Long getSchoolInfoCount(SchoolInfo t) {
		return this.schoolInfoDao.selectEntityCount(t);
	}

	public List<SchoolInfo> getSchoolInfoList(SchoolInfo t) {
		return this.schoolInfoDao.selectEntityList(t);
	}

	public int modifySchoolInfo(SchoolInfo t) {
		return this.schoolInfoDao.updateEntity(t);
	}

	public int removeSchoolInfo(SchoolInfo t) {
		return this.schoolInfoDao.deleteEntity(t);
	}

	public List<SchoolInfo> getSchoolInfoPaginatedList(SchoolInfo t) {
		return this.schoolInfoDao.selectEntityPaginatedList(t);
	}

}
