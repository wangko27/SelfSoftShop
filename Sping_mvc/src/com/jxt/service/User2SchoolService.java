package com.jxt.service;

import java.util.List;

import com.jxt.domain.User2School;



/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:28
 */
public interface User2SchoolService {

	Long createUser2School(User2School t);

	int modifyUser2School(User2School t);

	int removeUser2School(User2School t);

	User2School getUser2School(User2School t);

	List<User2School> getUser2SchoolList(User2School t);

	Long getUser2SchoolCount(User2School t);

	List<User2School> getUser2SchoolPaginatedList(User2School t);

}