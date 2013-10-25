package com.jxt.service;

import java.util.List;

import com.jxt.domain.ParentInfo;



/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:28
 */
public interface ParentInfoService {

	Long createParentInfo(ParentInfo t);

	int modifyParentInfo(ParentInfo t);

	int removeParentInfo(ParentInfo t);

	ParentInfo getParentInfo(ParentInfo t);

	List<ParentInfo> getParentInfoList(ParentInfo t);

	Long getParentInfoCount(ParentInfo t);

	List<ParentInfo> getParentInfoPaginatedList(ParentInfo t);

}