package com.jxt.service;

import java.util.List;

import com.jxt.domain.SchoolInfo;



/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:28
 */
public interface SchoolInfoService {

	Long createSchoolInfo(SchoolInfo t);

	int modifySchoolInfo(SchoolInfo t);

	int removeSchoolInfo(SchoolInfo t);

	SchoolInfo getSchoolInfo(SchoolInfo t);

	List<SchoolInfo> getSchoolInfoList(SchoolInfo t);

	Long getSchoolInfoCount(SchoolInfo t);

	List<SchoolInfo> getSchoolInfoPaginatedList(SchoolInfo t);

}