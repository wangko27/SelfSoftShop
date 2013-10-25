package net.shopxx.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.shopxx.bean.Pager;
import net.shopxx.dao.GoodsDao;
import net.shopxx.entity.Brand;
import net.shopxx.entity.DeliveryItem;
import net.shopxx.entity.Goods;
import net.shopxx.entity.GoodsAttribute;
import net.shopxx.entity.GoodsCategory;
import net.shopxx.entity.Member;
import net.shopxx.entity.OrderItem;
import net.shopxx.entity.Product;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 商品
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX027556F67EB15567DE4DCC7E9D2E5516
 * ============================================================================
 */

@Repository("goodsDaoImpl")
public class GoodsDaoImpl extends BaseDaoImpl<Goods, String> implements GoodsDao {
	
	public boolean isExistByGoodsSn(String goodsSn) {
		String hql = "from Goods as goods where lower(goods.goodsSn) = lower(:goodsSn)";
		Goods goods = (Goods) getSession().createQuery(hql).setParameter("goodsSn", goodsSn).uniqueResult();
		if (goods != null) {
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Goods> getGoodsList(GoodsCategory goodsCategory, String type, boolean isContainChildren, Integer maxResults) {
		Query query = null;
		if (goodsCategory != null) {
			if (StringUtils.equalsIgnoreCase(type, "best")) {
				if (isContainChildren) {
					String hql = "from Goods as goods where goods.isMarketable = :isMarketable and goods.isBest = :isBest and goods.goodsCategory.path like :path order by goods.createDate desc";
					query = getSession().createQuery(hql);
					query.setParameter("isMarketable", true).setParameter("isBest", true).setParameter("path", goodsCategory.getPath() + "%");
				} else {
					String hql = "from Goods as goods where goods.isMarketable = :isMarketable and goods.goodsCategory = :goodsCategory and goods.isBest = :isBest order by goods.createDate desc";
					query = getSession().createQuery(hql);
					query.setParameter("isMarketable", true).setParameter("goodsCategory", goodsCategory).setParameter("isBest", true);
				}
			} else if (StringUtils.equalsIgnoreCase(type, "new")) {
				if (isContainChildren) {
					String hql = "from Goods as goods where goods.isMarketable = :isMarketable and goods.isNew = :isNew and goods.goodsCategory.path like :path order by goods.createDate desc";
					query = getSession().createQuery(hql);
					query.setParameter("isMarketable", true).setParameter("isNew", true).setParameter("path", goodsCategory.getPath() + "%");
				} else {
					String hql = "from Goods as goods where goods.isMarketable = :isMarketable and goods.goodsCategory = :goodsCategory and goods.isNew = :isNew order by goods.createDate desc";
					query = getSession().createQuery(hql);
					query.setParameter("isMarketable", true).setParameter("goodsCategory", goodsCategory).setParameter("isNew", true);
				}
			} else if (StringUtils.equalsIgnoreCase(type, "hot")) {
				if (isContainChildren) {
					String hql = "from Goods as goods where goods.isMarketable = :isMarketable and goods.isHot = :isHot and goods.goodsCategory.path like :path order by goods.createDate desc";
					query = getSession().createQuery(hql);
					query.setParameter("isMarketable", true).setParameter("isHot", true).setParameter("path", goodsCategory.getPath() + "%");
				} else {
					String hql = "from Goods as goods where goods.isMarketable = :isMarketable and goods.goodsCategory = :goodsCategory and goods.isHot = :isHot order by goods.createDate desc";
					query = getSession().createQuery(hql);
					query.setParameter("isMarketable", true).setParameter("goodsCategory", goodsCategory).setParameter("isHot", true);
				}
			} else {
				if (isContainChildren) {
					String hql = "from Goods as goods where goods.isMarketable = :isMarketable and goods.goodsCategory.path like :path order by goods.createDate desc";
					query = getSession().createQuery(hql);
					query.setParameter("isMarketable", true).setParameter("path", goodsCategory.getPath() + "%");
				} else {
					String hql = "from Goods as goods where goods.isMarketable = :isMarketable and goods.goodsCategory = :goodsCategory order by goods.createDate desc";
					query = getSession().createQuery(hql);
					query.setParameter("isMarketable", true).setParameter("goodsCategory", goodsCategory);
				}
			}
		} else {
			if (StringUtils.equalsIgnoreCase(type, "best")) {
				String hql = "from Goods as goods where goods.isMarketable = :isMarketable and goods.isBest = :isBest order by goods.createDate desc";
				query = getSession().createQuery(hql);
				query.setParameter("isMarketable", true).setParameter("isBest", true);
			} else if (StringUtils.equalsIgnoreCase(type, "new")) {
				String hql = "from Goods as goods where goods.isMarketable = :isMarketable and goods.isNew = :isNew order by goods.createDate desc";
				query = getSession().createQuery(hql);
				query.setParameter("isMarketable", true).setParameter("isNew", true);
			} else if (StringUtils.equalsIgnoreCase(type, "hot")) {
				String hql = "from Goods as goods where goods.isMarketable = :isMarketable and goods.isHot = :isHot order by goods.createDate desc";
				query = getSession().createQuery(hql);
				query.setParameter("isMarketable", true).setParameter("isHot", true);
			} else {
				String hql = "from Goods as goods where goods.isMarketable = :isMarketable order by goods.createDate desc";
				query = getSession().createQuery(hql);
				query.setParameter("isMarketable", true);
			}
		}
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Goods> getGoodsList(GoodsCategory goodsCategory, Date beginDate, Date endDate, Integer firstResult, Integer maxResults) {
		Query query = null;
		if (goodsCategory != null) {
			if (beginDate != null && endDate == null) {
				String hql = "from Goods as goods where goods.isMarketable = :isMarketable and goods.goodsCategory.path like :path and goods.createDate > :beginDate order by goods.createDate desc";
				query = getSession().createQuery(hql);
				query.setParameter("isMarketable", true).setParameter("path", goodsCategory.getPath() + "%").setParameter("beginDate", beginDate);
			} else if (endDate != null && beginDate == null) {
				String hql = "from Goods as goods where goods.isMarketable = :isMarketable and goods.goodsCategory.path like :path and goods.createDate < :endDate order by goods.createDate desc";
				query = getSession().createQuery(hql);
				query.setParameter("isMarketable", true).setParameter("path", goodsCategory.getPath() + "%").setParameter("endDate", endDate);
			} else if (endDate != null && beginDate != null) {
				String hql = "from Goods as goods where goods.isMarketable = :isMarketable and goods.goodsCategory.path like :path and goods.createDate > :beginDate and goods.createDate < :endDate order by goods.createDate desc";
				query = getSession().createQuery(hql);
				query.setParameter("isMarketable", true).setParameter("path", goodsCategory.getPath() + "%").setParameter("beginDate", beginDate).setParameter("endDate", endDate);
			} else {
				String hql = "from Goods as goods where goods.isMarketable = :isMarketable and goods.goodsCategory.path like :path order by goods.createDate desc";
				query = getSession().createQuery(hql);
				query.setParameter("isMarketable", true).setParameter("path", goodsCategory.getPath() + "%");
			}
		} else {
			if (beginDate != null && endDate == null) {
				String hql = "from Goods as goods where goods.isMarketable = :isMarketable and goods.createDate > :beginDate order by goods.createDate desc";
				query = getSession().createQuery(hql);
				query.setParameter("isMarketable", true).setParameter("beginDate", beginDate);
			} else if (endDate != null && beginDate == null) {
				String hql = "from Goods as goods where goods.isMarketable = :isMarketable and goods.createDate < :endDate order by goods.createDate desc";
				query = getSession().createQuery(hql);
				query.setParameter("isMarketable", true).setParameter("endDate", endDate);
			} else if (endDate != null && beginDate != null) {
				String hql = "from Goods as goods where goods.isMarketable = :isMarketable and goods.createDate > :beginDate and goods.createDate < :endDate order by goods.createDate desc";
				query = getSession().createQuery(hql);
				query.setParameter("isMarketable", true).setParameter("beginDate", beginDate).setParameter("endDate", endDate);
			} else {
				String hql = "from Goods as goods where goods.isMarketable = :isMarketable order by goods.createDate desc";
				query = getSession().createQuery(hql);
				query.setParameter("isMarketable", true);
			}
		}
		if (firstResult != null) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != null) {
			query.setMaxResults(maxResults);
		}
		return query.list();
	}
	
	public Pager getGoodsPager(GoodsCategory goodsCategory, Pager pager) {
		Criteria criteria = getSession().createCriteria(Goods.class);
		criteria.createAlias("goodsCategory", "goodsCategory");
		criteria.add(Restrictions.eq("isMarketable", true));
		criteria.add(Restrictions.or(Restrictions.eq("goodsCategory", goodsCategory), Restrictions.like("goodsCategory.path", goodsCategory.getPath() + "%")));
		return super.findPager(pager, criteria);
	}
	
	public Pager getGoodsPager(GoodsCategory goodsCategory, Brand brand, Map<GoodsAttribute, String> goodsAttributeMap, Pager pager) {
		Criteria criteria = getSession().createCriteria(Goods.class);
		criteria.createAlias("goodsCategory", "goodsCategory");
		criteria.add(Restrictions.eq("isMarketable", true));
		if (goodsCategory != null) {
			criteria.add(Restrictions.or(Restrictions.eq("goodsCategory", goodsCategory), Restrictions.like("goodsCategory.path", goodsCategory.getPath() + "%")));
		}
		if (brand != null) {
			criteria.add(Restrictions.eq("brand", brand));
		}
		if (goodsAttributeMap != null) {
			for (GoodsAttribute goodsAttribute : goodsAttributeMap.keySet()) {
				String goodsAttributeValue = goodsAttributeMap.get(goodsAttribute);
				if (StringUtils.isNotEmpty(goodsAttributeValue)) {
					criteria.add(Restrictions.eq(Goods.GOODS_ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + goodsAttribute.getPropertyIndex(), goodsAttributeValue));
				}
			}
		}
		return super.findPager(pager, criteria);
	}

	public Pager getFavoriteGoodsPager(Member member, Pager pager) {
		Criteria criteria = getSession().createCriteria(Goods.class);
		criteria.createAlias("favoriteMemberSet", "favoriteMemberSet");
		criteria.add(Restrictions.eq("favoriteMemberSet.id", member.getId()));
		return super.findPager(pager, criteria);
	}
	
	public Long getMarketableGoodsCount() {
		String hql = "select count(*) from Goods as goods where goods.isMarketable = :isMarketable";
		return (Long) getSession().createQuery(hql).setParameter("isMarketable", true).uniqueResult();
	}
	
	public Long getUnMarketableGoodsCount() {
		String hql = "select count(*) from Goods as goods where goods.isMarketable = :isMarketable";
		return (Long) getSession().createQuery(hql).setParameter("isMarketable", false).uniqueResult();
	}
	
	// 关联处理
	@Override
	public void delete(Goods goods) {
		Set<Member> favoriteMemberSet = goods.getFavoriteMemberSet();
		if (favoriteMemberSet != null) {
			for (Member favoriteMember : favoriteMemberSet) {
				favoriteMember.getFavoriteGoodsSet().remove(goods);
			}
		}
		
		Set<Product> productSet = goods.getProductSet();
		for (Product product : productSet) {
			Set<OrderItem> orderItemSet = product.getOrderItemSet();
			if (orderItemSet != null) {
				for (OrderItem orderItem : orderItemSet) {
					orderItem.setProduct(null);
				}
			}
			
			Set<DeliveryItem> deliveryItemSet = product.getDeliveryItemSet();
			if (deliveryItemSet != null) {
				for (DeliveryItem deliveryItem : deliveryItemSet) {
					deliveryItem.setProduct(null);
				}
			}
		}
		
		super.delete(goods);
	}

	// 关联处理
	@Override
	public void delete(String id) {
		Goods goods = load(id);
		this.delete(goods);
	}

	// 关联处理
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			Goods goods = load(id);
			this.delete(goods);
		}
	}

}