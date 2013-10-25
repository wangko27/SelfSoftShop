package net.shopxx.service.impl;

import javax.annotation.Resource;

import net.shopxx.bean.Pager;
import net.shopxx.dao.LeaveMessageDao;
import net.shopxx.entity.LeaveMessage;
import net.shopxx.service.LeaveMessageService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service实现类 - 在线留言
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX65B94A1C020124AC5E38C179CA8D1CD0
 * ============================================================================
 */

@Service("leaveMessageServiceImpl")
public class LeaveMessageServiceImpl extends BaseServiceImpl<LeaveMessage, String> implements LeaveMessageService {
	
	@Resource(name = "leaveMessageDaoImpl")
	private LeaveMessageDao leaveMessageDao;
	
	@Resource(name = "leaveMessageDaoImpl")
	public void setBaseDao(LeaveMessageDao leaveMessageDao) {
		super.setBaseDao(leaveMessageDao);
	}
	
	@Transactional(readOnly = true)
	public Pager getLeaveMessagePager(Pager pager, boolean isContainUnReply) {
		return leaveMessageDao.getLeaveMessagePager(pager, isContainUnReply);
	}

}