package com.jxt.service;

import java.util.List;

import com.jxt.domain.GradeInfo;



/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:28
 */
public interface GradeInfoService {

	Long createGradeInfo(GradeInfo t);

	int modifyGradeInfo(GradeInfo t);

	int removeGradeInfo(GradeInfo t);

	GradeInfo getGradeInfo(GradeInfo t);

	List<GradeInfo> getGradeInfoList(GradeInfo t);

	Long getGradeInfoCount(GradeInfo t);

	List<GradeInfo> getGradeInfoPaginatedList(GradeInfo t);

}