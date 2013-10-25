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
 * 实体类 - 退货
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXB78A0828B86D9B288F5C60E3181EB7C1
 * ============================================================================
 */

@Entity
public class Reship extends BaseEntity {

	private static final long serialVersionUID = 4439185740262484180L;
	
	private String reshipSn;// 退货编号
	private String deliveryTypeName;// 配送方式名称
	private String deliveryCorpName; // 物流公司名称
	private String deliverySn;// 物流单号
	private BigDecimal deliveryFee;// 物流费用
	private String reshipName;// 退货人姓名
	private String reshipAreaStore;// 退货地区存储
	private String reshipAddress;// 退货地址
	private String reshipZipCode;// 退货邮编
	private String reshipPhone;// 退货电话
	private String reshipMobile;// 退货手机
	private String memo;// 备注
	
	private Order order;// 订单
	private DeliveryType deliveryType;// 配送方式
	
	private Set<DeliveryItem> deliveryItemSet = new HashSet<DeliveryItem>();// 物流项
	
	@Column(nullable = false, updatable = false, unique = true)
	public String getReshipSn() {
		return reshipSn;
	}

	public void setReshipSn(String reshipSn) {
		this.reshipSn = reshipSn;
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
	public String getReshipName() {
		return reshipName;
	}

	public void setReshipName(String reshipName) {
		this.reshipName = reshipName;
	}

	@Column(nullable = false, updatable = false, length = 3000)
	public String getReshipAreaStore() {
		return reshipAreaStore;
	}

	public void setReshipAreaStore(String reshipAreaStore) {
		this.reshipAreaStore = reshipAreaStore;
	}

	@Column(nullable = false, updatable = false)
	public String getReshipAddress() {
		return reshipAddress;
	}

	public void setReshipAddress(String reshipAddress) {
		this.reshipAddress = reshipAddress;
	}

	@Column(nullable = false, updatable = false)
	public String getReshipZipCode() {
		return reshipZipCode;
	}

	public void setReshipZipCode(String reshipZipCode) {
		this.reshipZipCode = reshipZipCode;
	}

	@Column(updatable = false)
	public String getReshipPhone() {
		return reshipPhone;
	}

	public void setReshipPhone(String reshipPhone) {
		this.reshipPhone = reshipPhone;
	}

	@Column(updatable = false)
	public String getReshipMobile() {
		return reshipMobile;
	}

	public void setReshipMobile(String reshipMobile) {
		this.reshipMobile = reshipMobile;
	}
	
	@Column(updatable = false)
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_reship_order")
	public Order getOrder() {
		return order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_reship_delivery_type")
	public DeliveryType getDeliveryType() {
		return deliveryType;
	}
	
	public void setDeliveryType(DeliveryType deliveryType) {
		this.deliveryType = deliveryType;
	}

	@OneToMany(mappedBy = "reship", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	@OrderBy("createDate asc")
	public Set<DeliveryItem> getDeliveryItemSet() {
		return deliveryItemSet;
	}

	public void setDeliveryItemSet(Set<DeliveryItem> deliveryItemSet) {
		this.deliveryItemSet = deliveryItemSet;
	}
	
	// 获取收货地区
	@Transient
	public Area getReshipArea() {
		if (StringUtils.isEmpty(reshipAreaStore)) {
			return null;
		}
		return JsonUtil.toObject(reshipAreaStore, Area.class);
	}
	
	// 设置收货地区
	@Transient
	public void setReshipArea(Area reshipArea) {
		if (reshipArea == null) {
			reshipAreaStore = null;
			return;
		}
		reshipAreaStore = JsonUtil.toJson(reshipArea);
	}
	
	// 保存处理
	@Override
	@Transient
	public void onSave() {
		reshipSn = SerialNumberUtil.buildReshipSn();
	}
	
}