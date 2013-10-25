package com.jxt.service.impl;

import java.util.List;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.jxt.dao.ClassInfoDao;
import com.jxt.domain.ClassInfo;
import com.jxt.service.ClassInfoService;


/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:29
 */
@Service
public class ClassInfoServiceImpl implements ClassInfoService {
	private ClassInfoDao classInfoDao;
	

	public Long createClassInfo(ClassInfo t) {
		return this.classInfoDao.insertEntity(t);
	}

	public ClassInfo getClassInfo(ClassInfo t) {
		return this.classInfoDao.selectEntity(t);
	}

	public Long getClassInfoCount(ClassInfo t) {
		return this.classInfoDao.selectEntityCount(t);
	}

	public List<ClassInfo> getClassInfoList(ClassInfo t) {
		return this.classInfoDao.selectEntityList(t);
	}

	public int modifyClassInfo(ClassInfo t) {
		return this.classInfoDao.updateEntity(t);
	}

	public int removeClassInfo(ClassInfo t) {
		return this.classInfoDao.deleteEntity(t);
	}

	public List<ClassInfo> getClassInfoPaginatedList(ClassInfo t) {
		return this.classInfoDao.selectEntityPaginatedList(t);
	}

}
