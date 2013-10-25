package net.shopxx.entity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import net.shopxx.bean.GoodsImage;
import net.shopxx.bean.SpecificationValue;
import net.shopxx.bean.PageTemplateConfig;
import net.shopxx.bean.Setting;
import net.shopxx.entity.GoodsAttribute.AttributeType;
import net.shopxx.util.JsonUtil;
import net.shopxx.util.ReflectionUtil;
import net.shopxx.util.SerialNumberUtil;
import net.shopxx.util.SettingUtil;
import net.shopxx.util.TemplateConfigUtil;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.compass.annotations.Index;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableProperty;
import org.compass.annotations.Store;
import org.hibernate.annotations.ForeignKey;

/**
 * 实体类 - 商品
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXA422025AB3BAEE5940EB4488D12B6691
 * ============================================================================
 */

@Entity
@Searchable
public class Goods extends BaseEntity {

	private static final long serialVersionUID = 8394952361534286179L;
	
	public static final int DEFAULT_GOODS_LIST_PAGE_SIZE = 12;// 商品列表默认每页显示数
	public static final int GOODS_ATTRIBUTE_VALUE_PROPERTY_COUNT = 20;// 商品属性值对象属性个数
	public static final String GOODS_ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX = "goodsAttributeValue";// 商品属性值对象属性名称前缀
	
	private String goodsSn;// 商品编号
	private String name;// 商品名称
	private BigDecimal price;// 销售价
	private BigDecimal cost;// 成本价
	private BigDecimal marketPrice;// 市场价
	private Integer weight;// 商品重量(单位: 克)
	private Integer store;// 库存
	private Integer freezeStore;// 被占用库存数
	private String storePlace;// 货位
	private Integer score;// 积分
	private Boolean isMarketable;// 是否上架
	private Boolean isBest;// 是否为精品商品
	private Boolean isNew;// 是否为新品商品
	private Boolean isHot;// 是否为热销商品
	private Boolean isSpecificationEnabled;// 是否启用商品规格
	private String introduction;// 介绍
	private String metaKeywords;// 页面关键词
	private String metaDescription;// 页面描述
	private String htmlPath;// HTML静态文件路径
	private String goodsImageStore;// 商品图片存储
	private String goodsAttributeValue0;// 商品属性值0
	private String goodsAttributeValue1;// 商品属性值1
	private String goodsAttributeValue2;// 商品属性值2
	private String goodsAttributeValue3;// 商品属性值3
	private String goodsAttributeValue4;// 商品属性值4
	private String goodsAttributeValue5;// 商品属性值5
	private String goodsAttributeValue6;// 商品属性值6
	private String goodsAttributeValue7;// 商品属性值7
	private String goodsAttributeValue8;// 商品属性值8
	private String goodsAttributeValue9;// 商品属性值9
	private String goodsAttributeValue10;// 商品属性值10
	private String goodsAttributeValue11;// 商品属性值11
	private String goodsAttributeValue12;// 商品属性值12
	private String goodsAttributeValue13;// 商品属性值13
	private String goodsAttributeValue14;// 商品属性值14
	private String goodsAttributeValue15;// 商品属性值15
	private String goodsAttributeValue16;// 商品属性值16
	private String goodsAttributeValue17;// 商品属性值17
	private String goodsAttributeValue18;// 商品属性值18
	private String goodsAttributeValue19;// 商品属性值19
	private String goodsParameterValueStore;// 商品参数存储
	
	private GoodsCategory goodsCategory;// 商品分类
	private GoodsType goodsType;// 商品类型
	private Brand brand;// 品牌
	
	private Set<Specification> specificationSet = new HashSet<Specification>();// 规格
	private Set<Product> productSet = new LinkedHashSet<Product>();// 货品
	private Set<Comment> commentSet = new HashSet<Comment>();// 评论
	private Set<Member> favoriteMemberSet = new HashSet<Member>(); // 商品收藏会员

	@Column(nullable = false, unique = true)
	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	@SearchableProperty(store = Store.YES, index = Index.ANALYZED)
	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@SearchableProperty(store = Store.YES, index = Index.ANALYZED)
	@Column(nullable = false, precision = 15, scale = 5)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = SettingUtil.setPriceScale(price);
	}
	
	@Column(precision = 15, scale = 5)
	public BigDecimal getCost() {
		return cost;
	}
	
	public void setCost(BigDecimal cost) {
		this.cost = SettingUtil.setPriceScale(cost);
	}

	@SearchableProperty(store = Store.YES, index = Index.NO)
	@Column(nullable = false, precision = 15, scale = 5)
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = SettingUtil.setPriceScale(marketPrice);
	}

	@Column(nullable = false)
	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	@SearchableProperty(store = Store.YES, index = Index.NO)
	public Integer getStore() {
		return store;
	}

	public void setStore(Integer store) {
		this.store = store;
	}

	@SearchableProperty(store = Store.YES, index = Index.NO)
	@Column(nullable = false)
	public Integer getFreezeStore() {
		return freezeStore;
	}

	public void setFreezeStore(Integer freezeStore) {
		this.freezeStore = freezeStore;
	}

	public String getStorePlace() {
		return storePlace;
	}

	public void setStorePlace(String storePlace) {
		this.storePlace = storePlace;
	}

	@Column(nullable = false)
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
	@SearchableProperty(store = Store.YES, index = Index.ANALYZED)
	@Column(nullable = false)
	public Boolean getIsMarketable() {
		return isMarketable;
	}

	public void setIsMarketable(Boolean isMarketable) {
		this.isMarketable = isMarketable;
	}

	@SearchableProperty(store = Store.YES, index = Index.ANALYZED)
	@Column(nullable = false)
	public Boolean getIsBest() {
		return isBest;
	}

	public void setIsBest(Boolean isBest) {
		this.isBest = isBest;
	}

	@SearchableProperty(store = Store.YES, index = Index.ANALYZED)
	@Column(nullable = false)
	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	@SearchableProperty(store = Store.YES, index = Index.ANALYZED)
	@Column(nullable = false)
	public Boolean getIsHot() {
		return isHot;
	}

	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
	}

	@Column(nullable = false)
	public Boolean getIsSpecificationEnabled() {
		return isSpecificationEnabled;
	}

	public void setIsSpecificationEnabled(Boolean isSpecificationEnabled) {
		this.isSpecificationEnabled = isSpecificationEnabled;
	}

	@Lob
	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	@Column(length = 3000)
	public String getMetaKeywords() {
		return metaKeywords;
	}

	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}

	@Column(length = 3000)
	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	@SearchableProperty(store = Store.YES, index = Index.NO)
	@Column(nullable = false, updatable = false)
	public String getHtmlPath() {
		return htmlPath;
	}

	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}

	@SearchableProperty(store = Store.YES, index = Index.NO)
	@Column(length = 3000)
	public String getGoodsImageStore() {
		return goodsImageStore;
	}

	public void setGoodsImageStore(String goodsImageStore) {
		this.goodsImageStore = goodsImageStore;
	}

	public String getGoodsAttributeValue0() {
		return goodsAttributeValue0;
	}

	public void setGoodsAttributeValue0(String goodsAttributeValue0) {
		this.goodsAttributeValue0 = goodsAttributeValue0;
	}

	public String getGoodsAttributeValue1() {
		return goodsAttributeValue1;
	}

	public void setGoodsAttributeValue1(String goodsAttributeValue1) {
		this.goodsAttributeValue1 = goodsAttributeValue1;
	}

	public String getGoodsAttributeValue2() {
		return goodsAttributeValue2;
	}

	public void setGoodsAttributeValue2(String goodsAttributeValue2) {
		this.goodsAttributeValue2 = goodsAttributeValue2;
	}

	public String getGoodsAttributeValue3() {
		return goodsAttributeValue3;
	}

	public void setGoodsAttributeValue3(String goodsAttributeValue3) {
		this.goodsAttributeValue3 = goodsAttributeValue3;
	}

	public String getGoodsAttributeValue4() {
		return goodsAttributeValue4;
	}

	public void setGoodsAttributeValue4(String goodsAttributeValue4) {
		this.goodsAttributeValue4 = goodsAttributeValue4;
	}

	public String getGoodsAttributeValue5() {
		return goodsAttributeValue5;
	}

	public void setGoodsAttributeValue5(String goodsAttributeValue5) {
		this.goodsAttributeValue5 = goodsAttributeValue5;
	}

	public String getGoodsAttributeValue6() {
		return goodsAttributeValue6;
	}

	public void setGoodsAttributeValue6(String goodsAttributeValue6) {
		this.goodsAttributeValue6 = goodsAttributeValue6;
	}

	public String getGoodsAttributeValue7() {
		return goodsAttributeValue7;
	}

	public void setGoodsAttributeValue7(String goodsAttributeValue7) {
		this.goodsAttributeValue7 = goodsAttributeValue7;
	}

	public String getGoodsAttributeValue8() {
		return goodsAttributeValue8;
	}

	public void setGoodsAttributeValue8(String goodsAttributeValue8) {
		this.goodsAttributeValue8 = goodsAttributeValue8;
	}

	public String getGoodsAttributeValue9() {
		return goodsAttributeValue9;
	}

	public void setGoodsAttributeValue9(String goodsAttributeValue9) {
		this.goodsAttributeValue9 = goodsAttributeValue9;
	}

	public String getGoodsAttributeValue10() {
		return goodsAttributeValue10;
	}

	public void setGoodsAttributeValue10(String goodsAttributeValue10) {
		this.goodsAttributeValue10 = goodsAttributeValue10;
	}

	public String getGoodsAttributeValue11() {
		return goodsAttributeValue11;
	}

	public void setGoodsAttributeValue11(String goodsAttributeValue11) {
		this.goodsAttributeValue11 = goodsAttributeValue11;
	}

	public String getGoodsAttributeValue12() {
		return goodsAttributeValue12;
	}

	public void setGoodsAttributeValue12(String goodsAttributeValue12) {
		this.goodsAttributeValue12 = goodsAttributeValue12;
	}

	public String getGoodsAttributeValue13() {
		return goodsAttributeValue13;
	}

	public void setGoodsAttributeValue13(String goodsAttributeValue13) {
		this.goodsAttributeValue13 = goodsAttributeValue13;
	}

	public String getGoodsAttributeValue14() {
		return goodsAttributeValue14;
	}

	public void setGoodsAttributeValue14(String goodsAttributeValue14) {
		this.goodsAttributeValue14 = goodsAttributeValue14;
	}

	public String getGoodsAttributeValue15() {
		return goodsAttributeValue15;
	}

	public void setGoodsAttributeValue15(String goodsAttributeValue15) {
		this.goodsAttributeValue15 = goodsAttributeValue15;
	}

	public String getGoodsAttributeValue16() {
		return goodsAttributeValue16;
	}

	public void setGoodsAttributeValue16(String goodsAttributeValue16) {
		this.goodsAttributeValue16 = goodsAttributeValue16;
	}

	public String getGoodsAttributeValue17() {
		return goodsAttributeValue17;
	}

	public void setGoodsAttributeValue17(String goodsAttributeValue17) {
		this.goodsAttributeValue17 = goodsAttributeValue17;
	}

	public String getGoodsAttributeValue18() {
		return goodsAttributeValue18;
	}

	public void setGoodsAttributeValue18(String goodsAttributeValue18) {
		this.goodsAttributeValue18 = goodsAttributeValue18;
	}

	public String getGoodsAttributeValue19() {
		return goodsAttributeValue19;
	}

	public void setGoodsAttributeValue19(String goodsAttributeValue19) {
		this.goodsAttributeValue19 = goodsAttributeValue19;
	}

	@Column(length = 3000)
	public String getGoodsParameterValueStore() {
		return goodsParameterValueStore;
	}

	public void setGoodsParameterValueStore(String goodsParameterValueStore) {
		this.goodsParameterValueStore = goodsParameterValueStore;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_goods_goods_category")
	public GoodsCategory getGoodsCategory() {
		return goodsCategory;
	}

	public void setGoodsCategory(GoodsCategory goodsCategory) {
		this.goodsCategory = goodsCategory;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_goods_goods_type")
	public GoodsType getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(GoodsType goodsType) {
		this.goodsType = goodsType;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_goods_brand")
	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@OrderBy("orderList asc")
	@ForeignKey(name = "fk_specification_set")
	public Set<Specification> getSpecificationSet() {
		return specificationSet;
	}

	public void setSpecificationSet(Set<Specification> specificationSet) {
		this.specificationSet = specificationSet;
	}

	@OneToMany(mappedBy = "goods", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	public Set<Product> getProductSet() {
		return productSet;
	}

	public void setProductSet(Set<Product> productSet) {
		this.productSet = productSet;
	}

	@OneToMany(mappedBy = "goods", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	public Set<Comment> getCommentSet() {
		return commentSet;
	}

	public void setCommentSet(Set<Comment> commentSet) {
		this.commentSet = commentSet;
	}

	@ManyToMany(mappedBy = "favoriteGoodsSet", fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_goods_favorite_member_set")
	public Set<Member> getFavoriteMemberSet() {
		return favoriteMemberSet;
	}

	public void setFavoriteMemberSet(Set<Member> favoriteMemberSet) {
		this.favoriteMemberSet = favoriteMemberSet;
	}
	
	// 获取商品图片
	@Transient
	public List<GoodsImage> getGoodsImageList() {
		if (StringUtils.isEmpty(goodsImageStore)) {
			return null;
		}
		try {
			return JsonUtil.toObject(goodsImageStore, new TypeReference<List<GoodsImage>>() {});
		} catch (Exception e) {
			return null;
		}
	}
	
	// 设置商品图片
	@Transient
	public void setGoodsImageList(List<GoodsImage> goodsImageList) {
		if (goodsImageList == null || goodsImageList.size() == 0) {
			goodsImageStore = null;
			return;
		}
		Collections.sort(goodsImageList);
		goodsImageStore = JsonUtil.toJson(goodsImageList);
	}
	
	// 获取商品属性值
	@Transient
	public Object getGoodsAttributeValue(GoodsAttribute goodsAttribute) {
		if (goodsAttribute == null) {
			return null;
		}
		String propertyName = GOODS_ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + goodsAttribute.getPropertyIndex();
		return (String) ReflectionUtil.invokeGetterMethod(this, propertyName);
	}
	
	// 设置商品属性值
	@Transient
	public void setGoodsAttributeValue(GoodsAttribute goodsAttribute, String goodsAttributeValue) {
		if (goodsAttribute == null) {
			return;
		}
		if (goodsAttribute.getAttributeType() == AttributeType.filter && !goodsAttribute.getOptionList().contains(goodsAttributeValue)) {
			throw new IllegalArgumentException("goodsAttributeValue error");
		}
		String propertyName = GOODS_ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + goodsAttribute.getPropertyIndex();
		ReflectionUtil.invokeSetterMethod(this, propertyName, goodsAttributeValue);
	}
	
	// 获取商品参数值
	@Transient
	public Map<String, String> getGoodsParameterValueMap() {
		if (StringUtils.isEmpty(goodsParameterValueStore)) {
			return null;
		}
		try {
			return JsonUtil.toObject(goodsParameterValueStore, new TypeReference<Map<String, String>>() {});
		} catch (Exception e) {
			return null;
		}
	}
	
	// 设置商品参数值
	@Transient
	public void setGoodsParameterValueMap(Map<String, String> goodsParameterValueMap) {
		if (goodsParameterValueMap == null || goodsParameterValueMap.size() == 0) {
			return;
		}
		goodsParameterValueStore = JsonUtil.toJson(goodsParameterValueMap);
	}
	
	// 保存处理
	@Override
	@Transient
	public void onSave() {
		if (price == null || price.compareTo(new BigDecimal(0)) < 0) {
			price = new BigDecimal(0);
		}
		if (cost != null && cost.compareTo(new BigDecimal(0)) < 0) {
			cost = new BigDecimal(0);
		}
		if (marketPrice == null || marketPrice.compareTo(new BigDecimal(0)) < 0) {
			marketPrice = SettingUtil.getDefaultMarketPrice(price);
		}
		if (weight == null || weight < 0) {
			weight = 0;
		}
		if (store != null && store < 0) {
			store = 0;
		}
		if (score == null || score < 0) {
			score = 0;
		}
		if (isMarketable == null) {
			isMarketable = false;
		}
		if (isBest == null) {
			isBest = false;
		}
		if (isNew == null) {
			isNew = false;
		}
		if (isHot == null) {
			isHot = false;
		}
		if (isSpecificationEnabled == null) {
			isSpecificationEnabled = false;
		}
		freezeStore = 0;
		if (StringUtils.isEmpty(goodsSn)) {
			goodsSn = SerialNumberUtil.buildGoodsSn();
		}
		PageTemplateConfig pageTemplateConfig = TemplateConfigUtil.getPageTemplateConfig(PageTemplateConfig.GOODS_CONTENT);
		htmlPath = pageTemplateConfig.getHtmlRealPath();
	}
	
	// 更新处理
	@Override
	@Transient
	public void onUpdate() {
		if (price == null || price.compareTo(new BigDecimal(0)) < 0) {
			price = new BigDecimal(0);
		}
		if (cost != null && cost.compareTo(new BigDecimal(0)) < 0) {
			cost = new BigDecimal(0);
		}
		if (marketPrice == null || marketPrice.compareTo(new BigDecimal(0)) < 0) {
			marketPrice = SettingUtil.getDefaultMarketPrice(price);
		}
		if (weight == null || weight < 0) {
			weight = 0;
		}
		if (store != null && store < 0) {
			store = 0;
		}
		if (score == null || score < 0) {
			score = 0;
		}
		if (isMarketable == null) {
			isMarketable = false;
		}
		if (isBest == null) {
			isBest = false;
		}
		if (isNew == null) {
			isNew = false;
		}
		if (isHot == null) {
			isHot = false;
		}
		if (isSpecificationEnabled == null) {
			isSpecificationEnabled = false;
		}
		if (StringUtils.isEmpty(goodsSn)) {
			goodsSn = SerialNumberUtil.buildGoodsSn();
		}
	}
	
	// 获取默认货品
	@Transient
	public Product getDefaultProduct() {
		if (isSpecificationEnabled) {
			for (Product product : productSet) {
				if (product.getIsDefault()) {
					return product;
				}
			}
		} else {
			for (Product product : productSet) {
				return product;
			}
		}
		return null;
	}
	
	// 获取默认商品图片（大）
	@Transient
	public String getDefaultBigGoodsImagePath() {
		List<GoodsImage> goodsImageList = getGoodsImageList();
		if (goodsImageList != null && goodsImageList.size() > 0) {
			return goodsImageList.get(0).getBigImagePath();
		} else {
			Setting setting = SettingUtil.getSetting();
			return setting.getDefaultBigGoodsImagePath();
		}
	}
	
	// 获取默认商品图片（小）
	@Transient
	public String getDefaultSmallGoodsImagePath() {
		List<GoodsImage> goodsImageList = getGoodsImageList();
		if (goodsImageList != null && goodsImageList.size() > 0) {
			return goodsImageList.get(0).getSmallImagePath();
		} else {
			Setting setting = SettingUtil.getSetting();
			return setting.getDefaultSmallGoodsImagePath();
		}
	}
	
	// 获取默认商品图片（缩略图）
	@Transient
	public String getDefaultThumbnailGoodsImagePath() {
		List<GoodsImage> goodsImageList = getGoodsImageList();
		if (goodsImageList != null && goodsImageList.size() > 0) {
			return goodsImageList.get(0).getThumbnailImagePath();
		} else {
			Setting setting = SettingUtil.getSetting();
			return setting.getDefaultThumbnailGoodsImagePath();
		}
	}
	
	// 获取商品规格值
	@Transient
	public Set<SpecificationValue> getSpecificationValueSet() {
		Set<SpecificationValue> specificationValueSet = new HashSet<SpecificationValue>();
		for (Product product : productSet) {
			if (product.getIsMarketable()) {
				specificationValueSet.addAll(product.getSpecificationValueList());
			}
		}
		return specificationValueSet;
	}
	
	// 设置商品属性值为NULL
	@Transient
	public void setGoodsAttributeValueToNull() {
		for (int i = 0; i < GOODS_ATTRIBUTE_VALUE_PROPERTY_COUNT; i ++) {
			String propertyName = GOODS_ATTRIBUTE_VALUE_PROPERTY_NAME_PREFIX + i;
			ReflectionUtil.invokeSetterMethod(this, propertyName, null, String.class);
		}
	}
	
	/**
	 * 商品是否缺货
	 */
	@Transient
	public Boolean getIsOutOfStock() {
		if (store != null && freezeStore >= store) {
			return true;
		} else {
			return false;
		}
	}

}