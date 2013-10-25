package net.shopxx.entity;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import net.shopxx.bean.GoodsParameter;
import net.shopxx.util.JsonUtil;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.hibernate.annotations.ForeignKey;

/**
 * 实体类 - 商品类型
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX9205E70ECB8E9EA3CF1669CBB8C6FCAF
 * ============================================================================
 */

@Entity
public class GoodsType extends BaseEntity {

	private static final long serialVersionUID = -6173231303962800416L;

	private String name;// 类型名称
	private String goodsParameterStore;// 商品参数存储
	
	private Set<GoodsAttribute> goodsAttributeSet = new HashSet<GoodsAttribute>();// 商品属性
	private Set<Goods> goodsSet = new HashSet<Goods>();// 商品
	private Set<GoodsCategory> goodsCategorySet = new HashSet<GoodsCategory>();// 商品分类
	private Set<Brand> brandSet = new HashSet<Brand>();// 商品品牌
	
	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length = 3000)
	public String getGoodsParameterStore() {
		return goodsParameterStore;
	}

	public void setGoodsParameterStore(String goodsParameterStore) {
		this.goodsParameterStore = goodsParameterStore;
	}

	@OneToMany(mappedBy = "goodsType", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	@OrderBy("orderList asc")
	public Set<GoodsAttribute> getGoodsAttributeSet() {
		return goodsAttributeSet;
	}

	public void setGoodsAttributeSet(Set<GoodsAttribute> goodsAttributeSet) {
		this.goodsAttributeSet = goodsAttributeSet;
	}

	@OneToMany(mappedBy = "goodsType", fetch = FetchType.LAZY)
	public Set<Goods> getGoodsSet() {
		return goodsSet;
	}

	public void setGoodsSet(Set<Goods> goodsSet) {
		this.goodsSet = goodsSet;
	}
	
	@OneToMany(mappedBy = "goodsType", fetch = FetchType.LAZY)
	public Set<GoodsCategory> getGoodsCategorySet() {
		return goodsCategorySet;
	}

	public void setGoodsCategorySet(Set<GoodsCategory> goodsCategorySet) {
		this.goodsCategorySet = goodsCategorySet;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@OrderBy("name asc")
	@ForeignKey(name = "fk_goods_type_brand_set")
	public Set<Brand> getBrandSet() {
		return brandSet;
	}

	public void setBrandSet(Set<Brand> brandSet) {
		this.brandSet = brandSet;
	}

	// 获取商品参数集合
	@SuppressWarnings("unchecked")
	@Transient
	public List<GoodsParameter> getGoodsParameterList() {
		if (StringUtils.isEmpty(goodsParameterStore)) {
			return null;
		}
		try {
			return JsonUtil.toObject(goodsParameterStore, new TypeReference<List<GoodsParameter>>() {});
		} catch (Exception e) {
			return null;
		}
	}
	
	// 设置商品参数集合
	@Transient
	public void setGoodsParameterList(List<GoodsParameter> goodsParameterList) {
		if (goodsParameterList == null || goodsParameterList.size() == 0) {
			goodsParameterStore = null;
			return;
		}
		Collections.sort(goodsParameterList);
		goodsParameterStore = JsonUtil.toJson(goodsParameterList);
	}

}