package com.jxt.service;

import java.util.List;

import com.jxt.domain.UserInfo;



/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:28
 */
public interface UserInfoService {

	Long createUserInfo(UserInfo t);

	int modifyUserInfo(UserInfo t);

	int removeUserInfo(UserInfo t);

	UserInfo getUserInfo(UserInfo t);

	List<UserInfo> getUserInfoList(UserInfo t);

	Long getUserInfoCount(UserInfo t);

	List<UserInfo> getUserInfoPaginatedList(UserInfo t);

}