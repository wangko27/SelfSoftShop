package net.shopxx.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import net.shopxx.util.JsonUtil;
import net.shopxx.util.SerialNumberUtil;
import net.shopxx.util.SettingUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.ForeignKey;

/**
 * 实体类 - 发货
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX00DE02D35525F59256A916D35AE401F1
 * ============================================================================
 */

@Entity
public class Shipping extends BaseEntity {

	private static final long serialVersionUID = 4315245804828793329L;

	private String shippingSn;// 发货编号
	private String deliveryTypeName;// 配送方式名称
	private String deliveryCorpName; // 物流公司名称
	private String deliveryCorpUrl;// 物流公司网址
	private String deliverySn;// 物流单号
	private BigDecimal deliveryFee;// 物流费用
	private String shipName;// 收货人姓名
	private String shipAreaStore;// 收货地区存储
	private String shipAddress;// 收货地址
	private String shipZipCode;// 收货邮编
	private String shipPhone;// 收货电话
	private String shipMobile;// 收货手机
	private String memo;// 备注
	
	private Order order;// 订单
	private DeliveryType deliveryType;// 配送方式
	
	private Set<DeliveryItem> deliveryItemSet = new HashSet<DeliveryItem>();// 物流项
	
	@Column(nullable = false, updatable = false, unique = true)
	public String getShippingSn() {
		return shippingSn;
	}
	
	public void setShippingSn(String shippingSn) {
		this.shippingSn = shippingSn;
	}
	
	@Column(nullable = false, updatable = false)
	public String getDeliveryTypeName() {
		return deliveryTypeName;
	}
	
	public void setDeliveryTypeName(String deliveryTypeName) {
		this.deliveryTypeName = deliveryTypeName;
	}
	
	@Column(nullable = false, updatable = false)
	public String getDeliveryCorpName() {
		return deliveryCorpName;
	}
	
	public void setDeliveryCorpName(String deliveryCorpName) {
		this.deliveryCorpName = deliveryCorpName;
	}
	
	public String getDeliveryCorpUrl() {
		return deliveryCorpUrl;
	}

	public void setDeliveryCorpUrl(String deliveryCorpUrl) {
		this.deliveryCorpUrl = deliveryCorpUrl;
	}

	@Column(updatable = false)
	public String getDeliverySn() {
		return deliverySn;
	}
	
	public void setDeliverySn(String deliverySn) {
		this.deliverySn = deliverySn;
	}
	
	@Column(nullable = false, updatable = false)
	public BigDecimal getDeliveryFee() {
		return deliveryFee;
	}
	
	public void setDeliveryFee(BigDecimal deliveryFee) {
		this.deliveryFee = SettingUtil.setPriceScale(deliveryFee);
	}
	
	@Column(nullable = false, updatable = false)
	public String getShipName() {
		return shipName;
	}
	
	public void setShipName(String shipName) {
		this.shipName = shipName;
	}
	
	@Column(nullable = false, length = 3000)
	public String getShipAreaStore() {
		return shipAreaStore;
	}

	public void setShipAreaStore(String shipAreaStore) {
		this.shipAreaStore = shipAreaStore;
	}
	
	@Column(nullable = false, updatable = false)
	public String getShipAddress() {
		return shipAddress;
	}
	
	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
	}
	
	@Column(nullable = false, updatable = false)
	public String getShipZipCode() {
		return shipZipCode;
	}
	
	public void setShipZipCode(String shipZipCode) {
		this.shipZipCode = shipZipCode;
	}
	
	@Column(updatable = false)
	public String getShipPhone() {
		return shipPhone;
	}
	
	public void setShipPhone(String shipPhone) {
		this.shipPhone = shipPhone;
	}
	
	@Column(updatable = false)
	public String getShipMobile() {
		return shipMobile;
	}
	
	public void setShipMobile(String shipMobile) {
		this.shipMobile = shipMobile;
	}
	
	@Column(updatable = false)
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_shipping_order")
	public Order getOrder() {
		return order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_shipping_delivery_type")
	public DeliveryType getDeliveryType() {
		return deliveryType;
	}
	
	public void setDeliveryType(DeliveryType deliveryType) {
		this.deliveryType = deliveryType;
	}

	@OneToMany(mappedBy = "shipping", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	@OrderBy("createDate asc")
	public Set<DeliveryItem> getDeliveryItemSet() {
		return deliveryItemSet;
	}

	public void setDeliveryItemSet(Set<DeliveryItem> deliveryItemSet) {
		this.deliveryItemSet = deliveryItemSet;
	}
	
	// 获取收货地区
	@Transient
	public Area getShipArea() {
		if (StringUtils.isEmpty(shipAreaStore)) {
			return null;
		}
		return JsonUtil.toObject(shipAreaStore, Area.class);
	}
	
	// 设置收货地区
	@Transient
	public void setShipArea(Area shipArea) {
		if (shipArea == null) {
			shipAreaStore = null;
			return;
		}
		shipAreaStore = JsonUtil.toJson(shipArea);
	}
	
	// 保存处理
	@Override
	@Transient
	public void onSave() {
		shippingSn = SerialNumberUtil.buildShippingSn();
	}
	
}