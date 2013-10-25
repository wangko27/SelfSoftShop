package com.jxt.service.impl;

import java.util.List;


import org.springframework.stereotype.Service;

import com.jxt.dao.RoleInfoDao;
import com.jxt.domain.RoleInfo;
import com.jxt.service.RoleInfoService;


/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:29
 */
@Service
public class RoleInfoServiceImpl implements RoleInfoService {
	private RoleInfoDao roleInfoDao;
	

	public Long createRoleInfo(RoleInfo t) {
		return this.roleInfoDao.insertEntity(t);
	}

	public RoleInfo getRoleInfo(RoleInfo t) {
		return this.roleInfoDao.selectEntity(t);
	}

	public Long getRoleInfoCount(RoleInfo t) {
		return this.roleInfoDao.selectEntityCount(t);
	}

	public List<RoleInfo> getRoleInfoList(RoleInfo t) {
		return this.roleInfoDao.selectEntityList(t);
	}

	public int modifyRoleInfo(RoleInfo t) {
		return this.roleInfoDao.updateEntity(t);
	}

	public int removeRoleInfo(RoleInfo t) {
		return this.roleInfoDao.deleteEntity(t);
	}

	public List<RoleInfo> getRoleInfoPaginatedList(RoleInfo t) {
		return this.roleInfoDao.selectEntityPaginatedList(t);
	}

}
