package net.shopxx.dao.impl;

import java.util.List;

import net.shopxx.dao.InstantMessagingDao;
import net.shopxx.entity.InstantMessaging;
import net.shopxx.entity.InstantMessaging.InstantMessagingType;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 在线客服
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX64B5A05594CB1C3B74C8A9B4F94F5991
 * ============================================================================
 */

@Repository("instantMessagingDaoImpl")
public class InstantMessagingDaoImpl extends BaseDaoImpl<InstantMessaging, String> implements InstantMessagingDao {
	
	@SuppressWarnings("unchecked")
	public List<InstantMessaging> getInstantMessagingList(InstantMessagingType instantMessagingType, Integer maxResults) {
		Query query = null;
		if (instantMessagingType != null) {
			String hql = "from InstantMessaging as instantMessaging where instantMessaging.instantMessagingType = :instantMessagingType order by instantMessaging.orderList asc";
			query = getSession().createQuery(hql);
			query.setParameter("instantMessagingType", instantMessagingType);
		} else {
			String hql = "from InstantMessaging as instantMessaging order by instantMessaging.orderList asc";
			query = getSession().createQuery(hql);
		}
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}
		return query.list();
	}

}