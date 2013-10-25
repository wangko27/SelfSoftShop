package com.jxt.service.impl;

import java.util.List;


import org.springframework.stereotype.Service;

import com.jxt.dao.User2AgentDao;
import com.jxt.domain.User2Agent;
import com.jxt.service.User2AgentService;


/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:29
 */
@Service
public class User2AgentServiceImpl implements User2AgentService {

	private User2AgentDao user2AgentDao;
	

	public Long createUser2Agent(User2Agent t) {
		return this.user2AgentDao.insertEntity(t);
	}

	public User2Agent getUser2Agent(User2Agent t) {
		return this.user2AgentDao.selectEntity(t);
	}

	public Long getUser2AgentCount(User2Agent t) {
		return this.user2AgentDao.selectEntityCount(t);
	}

	public List<User2Agent> getUser2AgentList(User2Agent t) {
		return this.user2AgentDao.selectEntityList(t);
	}

	public int modifyUser2Agent(User2Agent t) {
		return this.user2AgentDao.updateEntity(t);
	}

	public int removeUser2Agent(User2Agent t) {
		return this.user2AgentDao.deleteEntity(t);
	}

	public List<User2Agent> getUser2AgentPaginatedList(User2Agent t) {
		return this.user2AgentDao.selectEntityPaginatedList(t);
	}

}
