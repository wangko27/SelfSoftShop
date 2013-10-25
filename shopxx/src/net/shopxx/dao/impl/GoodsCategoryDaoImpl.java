package net.shopxx.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.shopxx.dao.GoodsCategoryDao;
import net.shopxx.entity.GoodsCategory;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 商品分类
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX218DE2372F1FD61042DB651AAC5D5594
 * ============================================================================
 */

@Repository("goodsCategoryDaoImpl")
public class GoodsCategoryDaoImpl extends BaseDaoImpl<GoodsCategory, String> implements GoodsCategoryDao {
	
	public boolean isExistBySign(String sign) {
		String hql = "from GoodsCategory as goodsCategory where lower(goodsCategory.sign) = lower(:sign)";
		GoodsCategory goodsCategory = (GoodsCategory) getSession().createQuery(hql).setParameter("sign", sign).uniqueResult();
		if (goodsCategory != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public GoodsCategory getGoodsCategoryBySign(String sign) {
		String hql = "from GoodsCategory as goodsCategory where lower(goodsCategory.sign) = lower(:sign)";
		GoodsCategory goodsCategory = (GoodsCategory) getSession().createQuery(hql).setParameter("sign", sign).uniqueResult();
		return goodsCategory;
	}
	
	@SuppressWarnings("unchecked")
	public List<GoodsCategory> getGoodsCategoryTree() {
		String hql = "from GoodsCategory as goodsCategory where goodsCategory.parent is null order by goodsCategory.orderList asc";
		List<GoodsCategory> goodsCategoryTreeList = getSession().createQuery(hql).list();
		initializeGoodsCategoryList(goodsCategoryTreeList);
		return goodsCategoryTreeList;
	}
	
	// 递归实例化商品分类对象
	private void initializeGoodsCategoryList(List<GoodsCategory> goodsCategoryList) {
		for (GoodsCategory goodsCategory : goodsCategoryList) {
			Set<GoodsCategory> children = goodsCategory.getChildren();
			if (children != null && children.size() > 0) {
				if (!Hibernate.isInitialized(children)) {
					Hibernate.initialize(children);
				}
				initializeGoodsCategoryList(new ArrayList<GoodsCategory>(children));
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<GoodsCategory> getRootGoodsCategoryList(Integer maxResults) {
		String hql = "from GoodsCategory as goodsCategory where goodsCategory.parent is null order by goodsCategory.orderList asc";
		Query query = getSession().createQuery(hql);
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<GoodsCategory> getParentGoodsCategoryList(GoodsCategory goodsCategory, boolean isContainParent, Integer maxResults) {
		Query query = null;
		if (goodsCategory != null) {
			if (isContainParent) {
				if (goodsCategory.getParent() == null) {
					return null;
				}
				String parentPath = StringUtils.substringBeforeLast(goodsCategory.getPath(), GoodsCategory.PATH_SEPARATOR);
				String[] ids = parentPath.split(GoodsCategory.PATH_SEPARATOR);
				String hql = "from GoodsCategory as goodsCategory where goodsCategory.id in(:ids) order by goodsCategory.grade asc, goodsCategory.orderList asc";
				query = getSession().createQuery(hql);
				query.setParameterList("ids", ids);
			} else {
				List<GoodsCategory> parentGoodsCategoryList = null;
				GoodsCategory parent = goodsCategory.getParent();
				if (maxResults > 0 && parent != null) {
					parentGoodsCategoryList = new ArrayList<GoodsCategory>();
					parentGoodsCategoryList.add(parent);
				}
				return parentGoodsCategoryList;
			}
		} else {
			String hql = "from GoodsCategory as goodsCategory order by goodsCategory.grade asc, goodsCategory.orderList asc";
			query = getSession().createQuery(hql);
		}
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<GoodsCategory> getChildrenGoodsCategoryList(GoodsCategory goodsCategory, boolean isContainChildren, Integer maxResults) {
		Query query = null;
		if (goodsCategory != null) {
			if (isContainChildren) {
				String hql = "from GoodsCategory as goodsCategory where goodsCategory.path like :path order by goodsCategory.grade asc, goodsCategory.orderList asc";
				query = getSession().createQuery(hql);
				query.setParameter("path", goodsCategory.getPath() + GoodsCategory.PATH_SEPARATOR + "%");
			} else {
				String hql = "from GoodsCategory as goodsCategory where goodsCategory.parent = :goodsCategory order by goodsCategory.grade asc, goodsCategory.orderList asc";
				query = getSession().createQuery(hql);
				query.setParameter("goodsCategory", goodsCategory);
			}
		} else {
			String hql = "from GoodsCategory as goodsCategory order by goodsCategory.grade asc, goodsCategory.orderList asc";
			query = getSession().createQuery(hql);
		}
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}
		return query.list();
	}
	
	// 自动设置path、grade
	@Override
	public String save(GoodsCategory goodsCategory) {
		goodsCategory.setPath(goodsCategory.getName());
		goodsCategory.setGrade(0);
		super.save(goodsCategory);
		fillGoodsCategory(goodsCategory);
		super.update(goodsCategory);
		return goodsCategory.getId();
	}
	
	// 自动设置path、grade
	@Override
	public void update(GoodsCategory goodsCategory) {
		fillGoodsCategory(goodsCategory);
		super.update(goodsCategory);
		List<GoodsCategory> childrenGoodsCategoryList = getChildrenGoodsCategoryList(goodsCategory, true, null);
		if (childrenGoodsCategoryList != null) {
			for (int i = 0; i < childrenGoodsCategoryList.size(); i ++) {
				GoodsCategory childrenCategory = childrenGoodsCategoryList.get(i);
				fillGoodsCategory(childrenCategory);
				super.update(childrenCategory);
				if(i % 20 == 0) {
					flush();
					clear();
				}
			}
		}
	}
	
	private void fillGoodsCategory(GoodsCategory goodsCategory) {
		GoodsCategory parent = goodsCategory.getParent();
		if (parent == null) {
			goodsCategory.setPath(goodsCategory.getId());
		} else {
			goodsCategory.setPath(parent.getPath() + GoodsCategory.PATH_SEPARATOR + goodsCategory.getId());
		}
		goodsCategory.setGrade(goodsCategory.getPath().split(GoodsCategory.PATH_SEPARATOR).length - 1);
	}

}