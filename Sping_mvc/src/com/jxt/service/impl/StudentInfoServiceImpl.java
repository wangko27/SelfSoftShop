package com.jxt.service.impl;

import java.util.List;


import org.springframework.stereotype.Service;

import com.jxt.dao.StudentInfoDao;
import com.jxt.domain.StudentInfo;
import com.jxt.service.StudentInfoService;


/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:29
 */
@Service
public class StudentInfoServiceImpl implements StudentInfoService {

	private StudentInfoDao studentInfoDao;
	

	public Long createStudentInfo(StudentInfo t) {
		return this.studentInfoDao.insertEntity(t);
	}

	public StudentInfo getStudentInfo(StudentInfo t) {
		return this.studentInfoDao.selectEntity(t);
	}

	public Long getStudentInfoCount(StudentInfo t) {
		return this.studentInfoDao.selectEntityCount(t);
	}

	public List<StudentInfo> getStudentInfoList(StudentInfo t) {
		return this.studentInfoDao.selectEntityList(t);
	}

	public int modifyStudentInfo(StudentInfo t) {
		return this.studentInfoDao.updateEntity(t);
	}

	public int removeStudentInfo(StudentInfo t) {
		return this.studentInfoDao.deleteEntity(t);
	}

	public List<StudentInfo> getStudentInfoPaginatedList(StudentInfo t) {
		return this.studentInfoDao.selectEntityPaginatedList(t);
	}

}
