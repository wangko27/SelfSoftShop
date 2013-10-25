package net.shopxx.dao.impl;

import net.shopxx.bean.Pager;
import net.shopxx.dao.GoodsNotifyDao;
import net.shopxx.entity.GoodsNotify;
import net.shopxx.entity.Member;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 到货通知
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXAC8DDA6F41F51B7BA7B541180E9FE7F7
 * ============================================================================
 */

@Repository("goodsNotifyDaoImpl")
public class GoodsNotifyDaoImpl extends BaseDaoImpl<GoodsNotify, String> implements GoodsNotifyDao {
	
	public Pager findPager(Member member, Pager pager) {
		return super.findPager(pager, Restrictions.eq("member", member));
	}
	
	public Long getUnprocessedGoodsNotifyCount() {
		String hql = "select count(*) from GoodsNotify as goodsNotify where goodsNotify.isSent = :isSent";
		return (Long) getSession().createQuery(hql).setParameter("isSent", false).uniqueResult();
	}

}