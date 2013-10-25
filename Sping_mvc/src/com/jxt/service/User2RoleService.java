package com.jxt.service;

import java.util.List;

import com.jxt.domain.User2Role;



/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:28
 */
public interface User2RoleService {

	Long createUser2Role(User2Role t);

	int modifyUser2Role(User2Role t);

	int removeUser2Role(User2Role t);

	User2Role getUser2Role(User2Role t);

	List<User2Role> getUser2RoleList(User2Role t);

	Long getUser2RoleCount(User2Role t);

	List<User2Role> getUser2RolePaginatedList(User2Role t);

}