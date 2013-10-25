package com.jxt.service.impl;

import java.util.List;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.jxt.dao.GradeInfoDao;
import com.jxt.domain.GradeInfo;
import com.jxt.service.GradeInfoService;


/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:29
 */
@Service
public class GradeInfoServiceImpl implements GradeInfoService {

	private GradeInfoDao gradeInfoDao;
	

	public Long createGradeInfo(GradeInfo t) {
		return this.gradeInfoDao.insertEntity(t);
	}

	public GradeInfo getGradeInfo(GradeInfo t) {
		return this.gradeInfoDao.selectEntity(t);
	}

	public Long getGradeInfoCount(GradeInfo t) {
		return this.gradeInfoDao.selectEntityCount(t);
	}

	public List<GradeInfo> getGradeInfoList(GradeInfo t) {
		return this.gradeInfoDao.selectEntityList(t);
	}

	public int modifyGradeInfo(GradeInfo t) {
		return this.gradeInfoDao.updateEntity(t);
	}

	public int removeGradeInfo(GradeInfo t) {
		return this.gradeInfoDao.deleteEntity(t);
	}

	public List<GradeInfo> getGradeInfoPaginatedList(GradeInfo t) {
		return this.gradeInfoDao.selectEntityPaginatedList(t);
	}

}
