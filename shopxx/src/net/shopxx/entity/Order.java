package net.shopxx.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import net.shopxx.util.JsonUtil;
import net.shopxx.util.SerialNumberUtil;
import net.shopxx.util.SettingUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.ForeignKey;

/**
 * 实体类 - 订单
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX112C95F35897FDDCA9E38A76E6DE9B8C
 * ============================================================================
 */

@Entity
public class Order extends BaseEntity {

	private static final long serialVersionUID = -8541323033439515148L;

	// 订单状态（未处理、已处理、已完成、已作废）
	public enum OrderStatus {
		unprocessed, processed, completed, invalid
	};

	// 付款状态（未支付、部分支付、已支付、部分退款、全额退款）
	public enum PaymentStatus {
		unpaid, partPayment, paid, partRefund, refunded
	};

	// 配送状态（未发货、部分发货、已发货、部分退货、已退货）
	public enum ShippingStatus {
		unshipped, partShipped, shipped, partReshiped, reshiped
	};

	private String orderSn;// 订单编号
	private OrderStatus orderStatus;// 订单状态
	private PaymentStatus paymentStatus;// 支付状态
	private ShippingStatus shippingStatus;// 发货状态
	private String deliveryTypeName;// 配送方式名称
	private String paymentConfigName;// 支付方式名称
	private BigDecimal totalProductPrice;// 总商品价格
	private BigDecimal deliveryFee;// 配送费用
	private BigDecimal paymentFee;// 支付费用
	private BigDecimal totalAmount;// 订单总额
	private BigDecimal paidAmount;// 已付金额
	private Integer totalProductWeight;// 总商品重量(单位: 克)
	private Integer totalProductQuantity;// 总商品数量
	private String shipName;// 收货人姓名
	private String shipAreaStore;// 收货地区存储
	private String shipAddress;// 收货地址
	private String shipZipCode;// 收货邮编
	private String shipPhone;// 收货电话
	private String shipMobile;// 收货手机
	private String memo;// 附言
	private String goodsIdListStore;// 商品ID集合储存
	
	private Member member;// 会员
	private DeliveryType deliveryType;// 配送方式
	private PaymentConfig paymentConfig;// 支付方式
	
	private Set<OrderItem> orderItemSet = new HashSet<OrderItem>();// 订单项
	private Set<OrderLog> orderLogSet = new HashSet<OrderLog>();// 订单日志
	private Set<Payment> paymentSet = new HashSet<Payment>();// 收款
	private Set<Refund> refundSet = new HashSet<Refund>();// 退款
	private Set<Shipping> shippingSet = new HashSet<Shipping>();// 发货
	private Set<Reship> reshipSet = new HashSet<Reship>();// 退货
	
	@Column(nullable = false, updatable = false, unique = true)
	public String getOrderSn() {
		return orderSn;
	}
	
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	
	@Enumerated
	@Column(nullable = false)
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Enumerated
	@Column(nullable = false)
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	@Enumerated
	@Column(nullable = false)
	public ShippingStatus getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(ShippingStatus shippingStatus) {
		this.shippingStatus = shippingStatus;
	}

	@Column(nullable = false, precision = 15, scale = 5)
	public BigDecimal getTotalProductPrice() {
		return totalProductPrice;
	}
	
	public void setTotalProductPrice(BigDecimal totalProductPrice) {
		this.totalProductPrice = SettingUtil.setPriceScale(totalProductPrice);
	}
	
	@Column(nullable = false, precision = 15, scale = 5)
	public BigDecimal getDeliveryFee() {
		return deliveryFee;
	}
	
	public void setDeliveryFee(BigDecimal deliveryFee) {
		this.deliveryFee = SettingUtil.setPriceScale(deliveryFee);
	}
	
	@Column(nullable = false, precision = 15, scale = 5)
	public BigDecimal getPaymentFee() {
		return paymentFee;
	}

	public void setPaymentFee(BigDecimal paymentFee) {
		this.paymentFee = SettingUtil.setPriceScale(paymentFee);
	}

	@Column(nullable = false, precision = 15, scale = 5)
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = SettingUtil.setPriceScale(totalAmount);
	}

	@Column(nullable = false, precision = 15, scale = 5)
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}
	
	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = SettingUtil.setPriceScale(paidAmount);
	}
	
	@Column(nullable = false)
	public String getDeliveryTypeName() {
		return deliveryTypeName;
	}
	
	public void setDeliveryTypeName(String deliveryTypeName) {
		this.deliveryTypeName = deliveryTypeName;
	}
	
	@Column(nullable = false)
	public String getPaymentConfigName() {
		return paymentConfigName;
	}

	public void setPaymentConfigName(String paymentConfigName) {
		this.paymentConfigName = paymentConfigName;
	}
	
	@Column(nullable = false)
	public Integer getTotalProductWeight() {
		return totalProductWeight;
	}

	public void setTotalProductWeight(Integer totalProductWeight) {
		this.totalProductWeight = totalProductWeight;
	}
	
	@Column(nullable = false)
	public Integer getTotalProductQuantity() {
		return totalProductQuantity;
	}

	public void setTotalProductQuantity(Integer totalProductQuantity) {
		this.totalProductQuantity = totalProductQuantity;
	}

	@Column(nullable = false)
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

	@Column(nullable = false)
	public String getShipAddress() {
		return shipAddress;
	}

	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
	}

	@Column(nullable = false)
	public String getShipZipCode() {
		return shipZipCode;
	}

	public void setShipZipCode(String shipZipCode) {
		this.shipZipCode = shipZipCode;
	}

	public String getShipPhone() {
		return shipPhone;
	}

	public void setShipPhone(String shipPhone) {
		this.shipPhone = shipPhone;
	}

	public String getShipMobile() {
		return shipMobile;
	}

	public void setShipMobile(String shipMobile) {
		this.shipMobile = shipMobile;
	}
	
	@Column(length = 3000)
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@Column(length = 3000)
	public String getGoodsIdListStore() {
		return goodsIdListStore;
	}

	public void setGoodsIdListStore(String goodsIdListStore) {
		this.goodsIdListStore = goodsIdListStore;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_order_member")
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_order_delivery_type")
	public DeliveryType getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(DeliveryType deliveryType) {
		this.deliveryType = deliveryType;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_order_payment_config")
	public PaymentConfig getPaymentConfig() {
		return paymentConfig;
	}

	public void setPaymentConfig(PaymentConfig paymentConfig) {
		this.paymentConfig = paymentConfig;
	}

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	@OrderBy("createDate asc")
	public Set<OrderItem> getOrderItemSet() {
		return orderItemSet;
	}

	public void setOrderItemSet(Set<OrderItem> orderItemSet) {
		this.orderItemSet = orderItemSet;
	}

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	@OrderBy("createDate asc")
	public Set<OrderLog> getOrderLogSet() {
		return orderLogSet;
	}

	public void setOrderLogSet(Set<OrderLog> orderLogSet) {
		this.orderLogSet = orderLogSet;
	}

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	@OrderBy("createDate desc")
	public Set<Payment> getPaymentSet() {
		return paymentSet;
	}

	public void setPaymentSet(Set<Payment> paymentSet) {
		this.paymentSet = paymentSet;
	}

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	@OrderBy("createDate desc")
	public Set<Refund> getRefundSet() {
		return refundSet;
	}

	public void setRefundSet(Set<Refund> refundSet) {
		this.refundSet = refundSet;
	}

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	@OrderBy("createDate desc")
	public Set<Shipping> getShippingSet() {
		return shippingSet;
	}

	public void setShippingSet(Set<Shipping> shippingSet) {
		this.shippingSet = shippingSet;
	}

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
	@OrderBy("createDate desc")
	public Set<Reship> getReshipSet() {
		return reshipSet;
	}

	public void setReshipSet(Set<Reship> reshipSet) {
		this.reshipSet = reshipSet;
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
	
	// 获取商品ID集合
	@SuppressWarnings("unchecked")
	@Transient
	public List<String> getGoodsIdList() {
		if (StringUtils.isEmpty(goodsIdListStore)) {
			return null;
		}
		return JsonUtil.toObject(goodsIdListStore, ArrayList.class);
	}
	
	// 设置商品ID集合
	@Transient
	public void setGoodsIdList(List<String> goodsIdList) {
		if (goodsIdList == null || goodsIdList.size() == 0) {
			goodsIdListStore = null;
			return;
		}
		goodsIdListStore = JsonUtil.toJson(goodsIdList);
	}
	
	// 保存处理
	@Override
	@Transient
	public void onSave() {
		orderSn = SerialNumberUtil.buildOrderSn();
	}

}