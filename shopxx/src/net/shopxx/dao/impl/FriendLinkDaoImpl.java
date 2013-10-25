package net.shopxx.dao.impl;

import java.util.List;

import net.shopxx.dao.FriendLinkDao;
import net.shopxx.entity.FriendLink;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 友情链接
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX101A4BDAB567AF3A03292AE07AFE4253
 * ============================================================================
 */

@Repository("friendLinkDaoImpl")
public class FriendLinkDaoImpl extends BaseDaoImpl<FriendLink, String> implements FriendLinkDao {
	
	@SuppressWarnings("unchecked")
	public List<FriendLink> getFriendLinkList(String type, Integer maxResults) {
		Query query = null;
		if (StringUtils.equalsIgnoreCase(type, "picture")) {
			String hql = "from FriendLink as friendLink where friendLink.logoPath is not null order by friendLink.orderList asc";
			query = getSession().createQuery(hql);
		} else if (StringUtils.equalsIgnoreCase(type, "text")) {
			String hql = "from FriendLink as friendLink where friendLink.logoPath is null order by friendLink.orderList asc";
			query = getSession().createQuery(hql);
		} else {
			String hql = "from FriendLink as friendLink order by friendLink.orderList asc";
			query = getSession().createQuery(hql);
		}
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}
		return query.list();
	}
	
}