package com.jxt.service;

import java.util.List;

import com.jxt.domain.AgentInfo;



/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator by Xing,XiuDong
 * @date 2013-07-20 19:16:28
 */
public interface AgentInfoService {

	Long createAgentInfo(AgentInfo t);

	int modifyAgentInfo(AgentInfo t);

	int removeAgentInfo(AgentInfo t);

	AgentInfo getAgentInfo(AgentInfo t);

	List<AgentInfo> getAgentInfoList(AgentInfo t);

	Long getAgentInfoCount(AgentInfo t);

	List<AgentInfo> getAgentInfoPaginatedList(AgentInfo t);

}