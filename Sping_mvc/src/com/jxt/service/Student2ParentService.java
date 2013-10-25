package com.jxt.service;

import java.util.List;

import com.jxt.domain.Student2Parent;



/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:28
 */
public interface Student2ParentService {

	Long createStudent2Parent(Student2Parent t);

	int modifyStudent2Parent(Student2Parent t);

	int removeStudent2Parent(Student2Parent t);

	Student2Parent getStudent2Parent(Student2Parent t);

	List<Student2Parent> getStudent2ParentList(Student2Parent t);

	Long getStudent2ParentCount(Student2Parent t);

	List<Student2Parent> getStudent2ParentPaginatedList(Student2Parent t);

}