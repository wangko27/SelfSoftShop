package net.shopxx.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.shopxx.dao.AreaDao;
import net.shopxx.entity.Area;
import net.shopxx.service.AreaService;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

/**
 * Service实现类 - 地区
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX2B3A4FEF05A42E033E79040EB368B28E
 * ============================================================================
 */

@Service("areaServiceImpl")
public class AreaServiceImpl extends BaseServiceImpl<Area, String> implements AreaService {
	
	@Resource(name = "areaDaoImpl")
	private AreaDao areaDao;

	@Resource(name = "areaDaoImpl")
	public void setBaseDao(AreaDao areaDao) {
		super.setBaseDao(areaDao);
	}
	
	@Cacheable(modelId = "areaCaching")
	@Transactional(readOnly = true)
	public List<Area> getAreaList(String id) {
		List<Area> areaList = new ArrayList<Area>();
		if (StringUtils.isNotEmpty(id)) {
			Area area = areaDao.load(id);
			Set<Area> children = area.getChildren();
			if (children != null) {
				areaList = new ArrayList<Area>(children);
			}
		} else {
			areaList = areaDao.getRootAreaList();
		}
		if (areaList != null) {
			for (Area area : areaList) {
				if (!Hibernate.isInitialized(area)) {
					Hibernate.initialize(area);
				}
			}
		}
		return areaList;
	}

	@Transactional(readOnly = true)
	public List<Area> getRootAreaList() {
		return areaDao.getRootAreaList();
	}
	
	@Transactional(readOnly = true)
	public List<Area> getParentAreaList(Area area) {
		return areaDao.getParentAreaList(area);
	}
	
	@Transactional(readOnly = true)
	public List<Area> getChildrenAreaList(Area area) {
		return areaDao.getChildrenAreaList(area);
	}

	@Override
	@CacheFlush(modelId = "areaFlushing")
	public void delete(Area area) {
		areaDao.delete(area);
	}

	@Override
	@CacheFlush(modelId = "areaFlushing")
	public void delete(String id) {
		areaDao.delete(id);
	}

	@Override
	@CacheFlush(modelId = "areaFlushing")
	public void delete(String[] ids) {
		areaDao.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "areaFlushing")
	public String save(Area area) {
		return areaDao.save(area);
	}

	@Override
	@CacheFlush(modelId = "areaFlushing")
	public void update(Area area) {
		areaDao.update(area);
	}

}