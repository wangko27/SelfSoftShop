package net.shopxx.dao.impl;

import java.util.List;

import net.shopxx.bean.Pager;
import net.shopxx.dao.NavigationDao;
import net.shopxx.entity.Navigation;
import net.shopxx.entity.Navigation.NavigationPosition;

import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 导航
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX873CAD06D89CFF2EE966FE1FAC8850B3
 * ============================================================================
 */

@Repository("navigationDaoImpl")
public class NavigationDaoImpl extends BaseDaoImpl<Navigation, String> implements NavigationDao {
	
	@SuppressWarnings("unchecked")
	public List<Navigation> getNavigationList(NavigationPosition navigationPosition, Integer maxResults) {
		if (navigationPosition != null) {
			String hql = "from Navigation as navigation where navigation.navigationPosition = :navigationPosition and navigation.isVisible = :isVisible order by navigation.orderList asc";
			if (maxResults != null) {
				return getSession().createQuery(hql).setParameter("navigationPosition", navigationPosition).setParameter("isVisible", true).setMaxResults(maxResults).list();
			} else {
				return getSession().createQuery(hql).setParameter("navigationPosition", navigationPosition).setParameter("isVisible", true).list();
			}
		} else {
			String hql = "from Navigation as navigation where navigation.isVisible = :isVisible order by navigation.orderList asc";
			if (maxResults != null) {
				return getSession().createQuery(hql).setParameter("isVisible", true).setMaxResults(maxResults).list();
			} else {
				return getSession().createQuery(hql).setParameter("isVisible", true).list();
			}
		}
	}
	
	// 根据navigationPosition排序
	@Override
	public Pager findPager(Pager pager) {
		return super.findPager(pager, Order.asc("navigationPosition"));
	}

}