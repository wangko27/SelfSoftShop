package com.jxt.service.impl;

import java.util.List;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.jxt.dao.User2SchoolDao;
import com.jxt.domain.User2School;
import com.jxt.service.User2SchoolService;


/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:29
 */
@Service
public class User2SchoolServiceImpl implements User2SchoolService {

	private User2SchoolDao user2SchoolDao;
	

	public Long createUser2School(User2School t) {
		return this.user2SchoolDao.insertEntity(t);
	}

	public User2School getUser2School(User2School t) {
		return this.user2SchoolDao.selectEntity(t);
	}

	public Long getUser2SchoolCount(User2School t) {
		return this.user2SchoolDao.selectEntityCount(t);
	}

	public List<User2School> getUser2SchoolList(User2School t) {
		return this.user2SchoolDao.selectEntityList(t);
	}

	public int modifyUser2School(User2School t) {
		return this.user2SchoolDao.updateEntity(t);
	}

	public int removeUser2School(User2School t) {
		return this.user2SchoolDao.deleteEntity(t);
	}

	public List<User2School> getUser2SchoolPaginatedList(User2School t) {
		return this.user2SchoolDao.selectEntityPaginatedList(t);
	}

}
