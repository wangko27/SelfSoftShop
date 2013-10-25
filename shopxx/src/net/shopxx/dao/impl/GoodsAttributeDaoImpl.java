package net.shopxx.dao.impl;

import java.util.ArrayList;
import java.util.List;

import net.shopxx.bean.Pager;
import net.shopxx.dao.GoodsAttributeDao;
import net.shopxx.entity.Goods;
import net.shopxx.entity.GoodsAttribute;
import net.shopxx.entity.GoodsType;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 商品属性
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX9F75E1059319B1C5FEFD644E19AE7400
 * ============================================================================
 */

@Repository("goodsAttributeDaoImpl")
public class GoodsAttributeDaoImpl extends BaseDaoImpl<GoodsAttribute, String> implements GoodsAttributeDao {
	
	@SuppressWarnings("unchecked")
	public Integer getUnusedPropertyIndex(GoodsType goodsType) {
		String hql = "from GoodsAttribute as goodsAttribute where goodsAttribute.goodsType = :goodsType";
		List<GoodsAttribute> goodsAttributeList = getSession().createQuery(hql).setParameter("goodsType", goodsType).list();
		if (goodsAttributeList != null && goodsAttributeList.size() >= Goods.GOODS_ATTRIBUTE_VALUE_PROPERTY_COUNT) {
			return null;
		}
		List<Integer> usedPropertyIndexList = new ArrayList<Integer>();
		for (GoodsAttribute goodsAttribute : goodsAttributeList) {
			usedPropertyIndexList.add(goodsAttribute.getPropertyIndex());
		}
		for (int i = 0; i < Goods.GOODS_ATTRIBUTE_VALUE_PROPERTY_COUNT; i ++) {
			if (!usedPropertyIndexList.contains(new Integer(i))) {
				return i;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<GoodsAttribute> getGoodsAttributeList(GoodsType goodsType) {
		String hql = "from GoodsAttribute as goodsAttribute where goodsAttribute.goodsType = :goodsType order by goodsAttribute.orderList asc";
		return getSession().createQuery(hql).setParameter("goodsType", goodsType).list();
	}
	
	public Pager getGoodsAttributePager(GoodsType goodsType, Pager pager) {
		return findPager(pager, Restrictions.eq("goodsType", goodsType));
	}
	
	// 删除的同时清除商品对应属性值
	@Override
	public void delete(GoodsAttribute goodsAttribute) {
		String propertyName = Goods.GOODS_ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + goodsAttribute.getPropertyIndex();
		String hql = "update Goods set " + propertyName + " = :propertyName where goodsType = :goodsType";
		getSession().createQuery(hql).setParameter("propertyName", null).setParameter("goodsType", goodsAttribute.getGoodsType()).executeUpdate();
		super.delete(goodsAttribute);
	}

	// 删除的同时清除商品对应属性值
	@Override
	public void delete(String id) {
		GoodsAttribute goodsAttribute = load(id);
		this.delete(goodsAttribute);
	}

	// 删除的同时清除商品对应属性值
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

}