package net.shopxx.entity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import net.shopxx.bean.SpecificationValue;
import net.shopxx.util.JsonUtil;
import net.shopxx.util.SerialNumberUtil;
import net.shopxx.util.SettingUtil;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.hibernate.annotations.ForeignKey;

/**
 * 实体类 - 货品
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
public class Product extends BaseEntity {

	private static final long serialVersionUID = -4663151563624172169L;
	
	private String productSn;// 货品编号
	private String name;// 名称
	private BigDecimal price;// 销售价
	private BigDecimal cost;// 成本价
	private BigDecimal marketPrice;// 市场价
	private Integer weight;// 商品重量(单位: 克)
	private Integer store;// 库存
	private Integer freezeStore;// 被占用库存数
	private String storePlace;// 货位
	private Boolean isMarketable;// 是否上架
	private Boolean isDefault;// 是否默认
	private String specificationValueStore;// 商品规格值存储
	
	private Goods goods;// 商品
	
	private Set<CartItem> cartItemSet = new HashSet<CartItem>();// 购物车项
	private Set<OrderItem> orderItemSet = new HashSet<OrderItem>();// 订单项
	private Set<DeliveryItem> deliveryItemSet = new HashSet<DeliveryItem>();// 物流项
	private Set<GoodsNotify> goodsNotifySet = new HashSet<GoodsNotify>();// 到货通知
	
	@Column(nullable = false, unique = true)
	public String getProductSn() {
		return productSn;
	}
	
	public void setProductSn(String productSn) {
		this.productSn = productSn;
	}
	
	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public Integer getStore() {
		return store;
	}
	
	public void setStore(Integer store) {
		this.store = store;
	}
	
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
	public Boolean getIsMarketable() {
		return isMarketable;
	}

	public void setIsMarketable(Boolean isMarketable) {
		this.isMarketable = isMarketable;
	}

	@Column(nullable = false)
	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	@ForeignKey(name = "fk_product_goods")
	public Goods getGoods() {
		return goods;
	}
	
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	
	@Column(length = 3000)
	public String getSpecificationValueStore() {
		return specificationValueStore;
	}

	public void setSpecificationValueStore(String specificationValueStore) {
		this.specificationValueStore = specificationValueStore;
	}

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	public Set<CartItem> getCartItemSet() {
		return cartItemSet;
	}

	public void setCartItemSet(Set<CartItem> cartItemSet) {
		this.cartItemSet = cartItemSet;
	}

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	public Set<OrderItem> getOrderItemSet() {
		return orderItemSet;
	}

	public void setOrderItemSet(Set<OrderItem> orderItemSet) {
		this.orderItemSet = orderItemSet;
	}

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	public Set<DeliveryItem> getDeliveryItemSet() {
		return deliveryItemSet;
	}

	public void setDeliveryItemSet(Set<DeliveryItem> deliveryItemSet) {
		this.deliveryItemSet = deliveryItemSet;
	}
	
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	public Set<GoodsNotify> getGoodsNotifySet() {
		return goodsNotifySet;
	}

	public void setGoodsNotifySet(Set<GoodsNotify> goodsNotifySet) {
		this.goodsNotifySet = goodsNotifySet;
	}
	
	// 获取商品规格值
	@Transient
	public List<SpecificationValue> getSpecificationValueList() {
		if (StringUtils.isEmpty(specificationValueStore)) {
			return null;
		}
		try {
			return JsonUtil.toObject(specificationValueStore, new TypeReference<List<SpecificationValue>>() {});
		} catch (Exception e) {
			return null;
		}
	}
	
	// 设置商品规格值
	@Transient
	public void setSpecificationValueList(List<SpecificationValue> specificationValueList) {
		if (specificationValueList == null || specificationValueList.size() == 0) {
			return;
		}
		Collections.sort(specificationValueList);
		specificationValueStore = JsonUtil.toJson(specificationValueList);
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
		if (freezeStore < 0) {
			freezeStore = 0;
		}
		if (isMarketable == null) {
			isMarketable = false;
		}
		if (isDefault == null) {
			isDefault = false;
		}
		if (StringUtils.isEmpty(productSn)) {
			productSn = SerialNumberUtil.buildProductSn();
		}
		freezeStore = 0;
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
		if (freezeStore < 0) {
			freezeStore = 0;
		}
		if (isMarketable == null) {
			isMarketable = false;
		}
		if (isDefault == null) {
			isDefault = false;
		}
		if (StringUtils.isEmpty(productSn)) {
			productSn = SerialNumberUtil.buildProductSn();
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
	
	/**
	 * 获取优惠价格,若member对象为null则返回原价格
	 */
	@Transient
	public BigDecimal getPreferentialPrice(Member member) {
		if (member != null) {
			BigDecimal preferentialPrice = price.multiply(new BigDecimal(member.getMemberRank().getPreferentialScale().toString()).divide(new BigDecimal(100)));
			preferentialPrice = SettingUtil.setPriceScale(preferentialPrice);
			return preferentialPrice;
		} else {
			return price;
		}
	}

}