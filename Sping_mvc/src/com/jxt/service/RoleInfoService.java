package com.jxt.service;

import java.util.List;

import com.jxt.domain.RoleInfo;



/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:28
 */
public interface RoleInfoService {

	Long createRoleInfo(RoleInfo t);

	int modifyRoleInfo(RoleInfo t);

	int removeRoleInfo(RoleInfo t);

	RoleInfo getRoleInfo(RoleInfo t);

	List<RoleInfo> getRoleInfoList(RoleInfo t);

	Long getRoleInfoCount(RoleInfo t);

	List<RoleInfo> getRoleInfoPaginatedList(RoleInfo t);

}