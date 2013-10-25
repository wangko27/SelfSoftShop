package net.shopxx.dao.impl;

import net.shopxx.bean.Pager;
import net.shopxx.dao.MessageDao;
import net.shopxx.entity.Member;
import net.shopxx.entity.Message;
import net.shopxx.entity.Message.DeleteStatus;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 消息
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

@Repository("messageDaoImpl")
public class MessageDaoImpl extends BaseDaoImpl<Message, String> implements MessageDao{
	
	public Pager getMemberInboxPager(Member member, Pager pager) {
		return super.findPager(pager, Restrictions.eq("toMember", member), Restrictions.eq("isSaveDraftbox", false), Restrictions.ne("deleteStatus", DeleteStatus.toDelete));
	}
	
	public Pager getMemberOutboxPager(Member member, Pager pager) {
		return super.findPager(pager, Restrictions.eq("fromMember", member), Restrictions.eq("isSaveDraftbox", false), Restrictions.ne("deleteStatus", DeleteStatus.fromDelete));
	}
	
	public Pager getMemberDraftboxPager(Member member, Pager pager) {
		return super.findPager(pager, Restrictions.eq("fromMember", member), Restrictions.eq("isSaveDraftbox", true), Restrictions.ne("deleteStatus", DeleteStatus.fromDelete));
	}
	
	public Pager getAdminInboxPager(Pager pager) {
		return super.findPager(pager, Restrictions.isNull("toMember"), Restrictions.eq("isSaveDraftbox", false), Restrictions.ne("deleteStatus", DeleteStatus.toDelete));
	}
	
	public Pager getAdminOutboxPager(Pager pager) {
		return super.findPager(pager, Restrictions.isNull("fromMember"), Restrictions.eq("isSaveDraftbox", false), Restrictions.ne("deleteStatus", DeleteStatus.fromDelete));
	}
	
	public Long getUnreadMessageCount(Member member) {
		String hql = "select count(*) from Message as message where message.toMember = :toMember and message.isRead = :isRead and message.isSaveDraftbox = :isSaveDraftbox and message.deleteStatus != :deleteStatus";
		return (Long) getSession().createQuery(hql).setParameter("toMember", member).setParameter("isRead", false).setParameter("isSaveDraftbox", false).setParameter("deleteStatus", DeleteStatus.toDelete).uniqueResult();
	}
	
	public Long getUnreadMessageCount() {
		String hql = "select count(*) from Message as message where message.toMember is null and message.isRead = :isRead and message.isSaveDraftbox = :isSaveDraftbox and message.deleteStatus != :deleteStatus";
		return (Long) getSession().createQuery(hql).setParameter("isRead", false).setParameter("isSaveDraftbox", false).setParameter("deleteStatus", DeleteStatus.toDelete).uniqueResult();
	}

}