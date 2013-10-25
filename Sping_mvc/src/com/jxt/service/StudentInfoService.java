package com.jxt.service;

import java.util.List;

import com.jxt.domain.StudentInfo;



/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:28
 */
public interface StudentInfoService {

	Long createStudentInfo(StudentInfo t);

	int modifyStudentInfo(StudentInfo t);

	int removeStudentInfo(StudentInfo t);

	StudentInfo getStudentInfo(StudentInfo t);

	List<StudentInfo> getStudentInfoList(StudentInfo t);

	Long getStudentInfoCount(StudentInfo t);

	List<StudentInfo> getStudentInfoPaginatedList(StudentInfo t);

}