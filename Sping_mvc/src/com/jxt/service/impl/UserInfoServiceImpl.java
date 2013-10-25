package com.jxt.service.impl;

import java.util.List;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.jxt.dao.UserInfoDao;
import com.jxt.domain.UserInfo;
import com.jxt.service.UserInfoService;


/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:29
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

	private UserInfoDao userInfoDao;
	

	public Long createUserInfo(UserInfo t) {
		return this.userInfoDao.insertEntity(t);
	}

	public UserInfo getUserInfo(UserInfo t) {
		return this.userInfoDao.selectEntity(t);
	}

	public Long getUserInfoCount(UserInfo t) {
		return this.userInfoDao.selectEntityCount(t);
	}

	public List<UserInfo> getUserInfoList(UserInfo t) {
		return this.userInfoDao.selectEntityList(t);
	}

	public int modifyUserInfo(UserInfo t) {
		return this.userInfoDao.updateEntity(t);
	}

	public int removeUserInfo(UserInfo t) {
		return this.userInfoDao.deleteEntity(t);
	}

	public List<UserInfo> getUserInfoPaginatedList(UserInfo t) {
		return this.userInfoDao.selectEntityPaginatedList(t);
	}

}
