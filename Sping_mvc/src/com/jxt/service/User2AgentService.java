package com.jxt.service;

import java.util.List;

import com.jxt.domain.User2Agent;



/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:28
 */
public interface User2AgentService {

	Long createUser2Agent(User2Agent t);

	int modifyUser2Agent(User2Agent t);

	int removeUser2Agent(User2Agent t);

	User2Agent getUser2Agent(User2Agent t);

	List<User2Agent> getUser2AgentList(User2Agent t);

	Long getUser2AgentCount(User2Agent t);

	List<User2Agent> getUser2AgentPaginatedList(User2Agent t);

}