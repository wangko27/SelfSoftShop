package com.jxt.service;

import java.util.List;

import com.jxt.domain.ClassInfo;



/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:28
 */
public interface ClassInfoService {

	Long createClassInfo(ClassInfo t);

	int modifyClassInfo(ClassInfo t);

	int removeClassInfo(ClassInfo t);

	ClassInfo getClassInfo(ClassInfo t);

	List<ClassInfo> getClassInfoList(ClassInfo t);

	Long getClassInfoCount(ClassInfo t);

	List<ClassInfo> getClassInfoPaginatedList(ClassInfo t);

}