package com.jxt.service.impl;

import java.util.List;

import javax.annotation.Resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jxt.dao.AgentInfoDao;
import com.jxt.domain.AgentInfo;
import com.jxt.service.AgentInfoService;


/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:29
 */
@Service
public class AgentInfoServiceImpl implements AgentInfoService {

	private AgentInfoDao agentInfoDao;
	
	public Long createAgentInfo(AgentInfo t) {
		return this.agentInfoDao.insertEntity(t);
	}

	public AgentInfo getAgentInfo(AgentInfo t) {
		return this.agentInfoDao.selectEntity(t);
	}

	public Long getAgentInfoCount(AgentInfo t) {
		return this.agentInfoDao.selectEntityCount(t);
	}

	public List<AgentInfo> getAgentInfoList(AgentInfo t) {
		return this.agentInfoDao.selectEntityList(t);
	}

	public int modifyAgentInfo(AgentInfo t) {
		return this.agentInfoDao.updateEntity(t);
	}

	public int removeAgentInfo(AgentInfo t) {
		return this.agentInfoDao.deleteEntity(t);
	}

	public List<AgentInfo> getAgentInfoPaginatedList(AgentInfo t) {
		return this.agentInfoDao.selectEntityPaginatedList(t);
	}

}
