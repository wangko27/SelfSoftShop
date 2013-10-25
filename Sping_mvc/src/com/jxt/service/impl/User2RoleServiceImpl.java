package com.jxt.service.impl;

import java.util.List;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.jxt.dao.User2RoleDao;
import com.jxt.domain.User2Role;
import com.jxt.service.User2RoleService;


/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:29
 */
@Service
public class User2RoleServiceImpl implements User2RoleService {

	private User2RoleDao user2RoleDao;
	

	public Long createUser2Role(User2Role t) {
		return this.user2RoleDao.insertEntity(t);
	}

	public User2Role getUser2Role(User2Role t) {
		return this.user2RoleDao.selectEntity(t);
	}

	public Long getUser2RoleCount(User2Role t) {
		return this.user2RoleDao.selectEntityCount(t);
	}

	public List<User2Role> getUser2RoleList(User2Role t) {
		return this.user2RoleDao.selectEntityList(t);
	}

	public int modifyUser2Role(User2Role t) {
		return this.user2RoleDao.updateEntity(t);
	}

	public int removeUser2Role(User2Role t) {
		return this.user2RoleDao.deleteEntity(t);
	}

	public List<User2Role> getUser2RolePaginatedList(User2Role t) {
		return this.user2RoleDao.selectEntityPaginatedList(t);
	}

}
