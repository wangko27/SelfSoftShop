package com.jxt.service.impl;

import java.util.List;


import org.springframework.stereotype.Service;

import com.jxt.dao.Student2ParentDao;
import com.jxt.domain.Student2Parent;
import com.jxt.service.Student2ParentService;


/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:29
 */
@Service
public class Student2ParentServiceImpl implements Student2ParentService {
	private Student2ParentDao student2ParentDao;
	

	public Long createStudent2Parent(Student2Parent t) {
		return this.student2ParentDao.insertEntity(t);
	}

	public Student2Parent getStudent2Parent(Student2Parent t) {
		return this.student2ParentDao.selectEntity(t);
	}

	public Long getStudent2ParentCount(Student2Parent t) {
		return this.student2ParentDao.selectEntityCount(t);
	}

	public List<Student2Parent> getStudent2ParentList(Student2Parent t) {
		return this.student2ParentDao.selectEntityList(t);
	}

	public int modifyStudent2Parent(Student2Parent t) {
		return this.student2ParentDao.updateEntity(t);
	}

	public int removeStudent2Parent(Student2Parent t) {
		return this.student2ParentDao.deleteEntity(t);
	}

	public List<Student2Parent> getStudent2ParentPaginatedList(Student2Parent t) {
		return this.student2ParentDao.selectEntityPaginatedList(t);
	}

}
