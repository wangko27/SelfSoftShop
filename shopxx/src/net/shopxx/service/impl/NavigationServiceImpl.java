package net.shopxx.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.shopxx.dao.NavigationDao;
import net.shopxx.entity.Navigation;
import net.shopxx.entity.Navigation.NavigationPosition;
import net.shopxx.service.NavigationService;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

/**
 * Service实现类 - 导航
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXD7D7FC7B8EF1162D69F6EBC8BBFEEC6C
 * ============================================================================
 */

@Service("navigationServiceImpl")
public class NavigationServiceImpl extends BaseServiceImpl<Navigation, String> implements NavigationService {

	@Resource(name = "navigationDaoImpl")
	private NavigationDao navigationDao;

	@Resource(name = "navigationDaoImpl")
	public void setBaseDao(NavigationDao navigationDao) {
		super.setBaseDao(navigationDao);
	}
	
	@Cacheable(modelId = "navigationCaching")
	@Transactional(readOnly = true)
	public List<Navigation> getNavigationList(NavigationPosition navigationPosition, Integer maxResults) {
		List<Navigation> navigationList = navigationDao.getNavigationList(navigationPosition, maxResults);
		if (navigationList != null) {
			for (Navigation navigation : navigationList) {
				if (!Hibernate.isInitialized(navigation)) {
					Hibernate.initialize(navigation);
				}
			}
		}
		return navigationList;
	}

	@Override
	@CacheFlush(modelId = "navigationFlushing")
	public void delete(Navigation navigation) {
		navigationDao.delete(navigation);
	}

	@Override
	@CacheFlush(modelId = "navigationFlushing")
	public void delete(String id) {
		navigationDao.delete(id);
	}

	@Override
	@CacheFlush(modelId = "navigationFlushing")
	public void delete(String[] ids) {
		navigationDao.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "navigationFlushing")
	public String save(Navigation navigation) {
		return navigationDao.save(navigation);
	}

	@Override
	@CacheFlush(modelId = "navigationFlushing")
	public void update(Navigation navigation) {
		navigationDao.update(navigation);
	}

}