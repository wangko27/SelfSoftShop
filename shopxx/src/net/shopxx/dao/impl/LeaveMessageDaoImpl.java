package net.shopxx.dao.impl;

import net.shopxx.bean.Pager;
import net.shopxx.dao.LeaveMessageDao;
import net.shopxx.entity.LeaveMessage;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 在线留言
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXF443BAE0CC7277A3DD71774267D31005
 * ============================================================================
 */

@Repository("leaveMessageDaoImpl")
public class LeaveMessageDaoImpl extends BaseDaoImpl<LeaveMessage, String> implements LeaveMessageDao{

	public Pager getLeaveMessagePager(Pager pager, boolean isContainUnReply) {
		if (isContainUnReply) {
			return super.findPager(pager, Restrictions.isNull("forLeaveMessage"));
		} else {
			return super.findPager(pager, Restrictions.isNull("forLeaveMessage"), Restrictions.isNotEmpty("replyLeaveMessageSet"));
		}
	}
	
}