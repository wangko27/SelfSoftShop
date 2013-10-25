package net.shopxx.dao.impl;

import java.util.List;

import net.shopxx.dao.AreaDao;
import net.shopxx.entity.Area;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 地区
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX1C7D46AEE8221AFDAF3E739DCCA1D09E
 * ============================================================================
 */

@Repository("areaDaoImpl")
public class AreaDaoImpl extends BaseDaoImpl<Area, String> implements AreaDao {
	
	@SuppressWarnings("unchecked")
	public List<Area> getRootAreaList() {
		String hql = "from Area as area where area.parent is null order by area.orderList asc";
		return getSession().createQuery(hql).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Area> getParentAreaList(Area area) {
		if (area.getParent() == null) {
			return null;
		}
		String parentPath = StringUtils.substringBeforeLast(area.getPath(), Area.PATH_SEPARATOR);
		String[] ids = parentPath.split(Area.PATH_SEPARATOR);
		String hql = "from Area as area where area.id in(:ids) order by area.grade asc, area.orderList asc";
		return getSession().createQuery(hql).setParameterList("ids", ids).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Area> getChildrenAreaList(Area area) {
		String hql = "from Area as area where area.path like :path order by area.grade asc, area.orderList asc";
		return getSession().createQuery(hql).setParameter("path", area.getPath() + Area.PATH_SEPARATOR + "%").list();
	}

	// 自动设置displayName、path、grade
	@Override
	public String save(Area area) {
		area.setDisplayName(area.getName());
		area.setPath(area.getName());
		area.setGrade(0);
		super.save(area);
		fillArea(area);
		super.update(area);
		return area.getId();
	}
	
	// 自动设置displayName、path、grade
	@Override
	public void update(Area area) {
		fillArea(area);
		super.update(area);
		List<Area> childrenAreaList = getChildrenAreaList(area);
		if (childrenAreaList != null) {
			for (int i = 0; i < childrenAreaList.size(); i ++) {
				Area childrenArea = childrenAreaList.get(i);
				fillArea(childrenArea);
				super.update(childrenArea);
				if(i % 20 == 0) {
					flush();
					clear();
				}
			}
		}
	}
	
	private void fillArea(Area area) {
		Area parent = area.getParent();
		if (parent == null) {
			area.setPath(area.getId());
		} else {
			area.setPath(parent.getPath() + Area.PATH_SEPARATOR + area.getId());
		}
		area.setGrade(area.getPath().split(Area.PATH_SEPARATOR).length - 1);
		
		StringBuilder displayName = new StringBuilder();
		List<Area> parentAreaList = getParentAreaList(area);
		if (parentAreaList != null) {
			for (Area parentArea : parentAreaList) {
				displayName.append(parentArea.getName());
			}
		}
		displayName.append(area.getName());
		area.setDisplayName(displayName.toString());
	}

}