package net.shopxx.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import net.shopxx.util.SettingUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.ForeignKey;

/**
 * 实体类 - 配送方式 
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXD834B4DDADAC88F9991F5CD5611A0797
 * ============================================================================
 */

@Entity
public class DeliveryType extends BaseEntity {

	private static final long serialVersionUID = 5873163245980853245L;

	// 配送类型：先付款后发货、货到付款
	public enum DeliveryMethod {
		deliveryAgainstPayment, cashOnDelivery
	};

	private String name;// 配送方式名称
	private DeliveryMethod deliveryMethod;// 配送类型
	private Integer firstWeight;// 首重量(单位: 克)
	private Integer continueWeight;// 续重量(单位: 克)
	private BigDecimal firstWeightPrice;// 首重价格
	private BigDecimal continueWeightPrice;// 续重价格
	private String description;// 介绍
	private Integer orderList;// 排序
	
	private DeliveryCorp defaultDeliveryCorp;// 默认物流公司
	
	private Set<Order> orderSet = new HashSet<Order>();// 订单
	private Set<Shipping> shippingSet = new HashSet<Shipping>();// 发货
	private Set<Reship> reshipSet = new HashSet<Reship>();// 退货

	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Enumerated
	@Column(nullable = false)
	public DeliveryMethod getDeliveryMethod() {
		return deliveryMethod;
	}

	public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}

	@Column(nullable = false)
	public Integer getFirstWeight() {
		return firstWeight;
	}

	public void setFirstWeight(Integer firstWeight) {
		this.firstWeight = firstWeight;
	}

	@Column(nullable = false)
	public Integer getContinueWeight() {
		return continueWeight;
	}

	public void setContinueWeight(Integer continueWeight) {
		this.continueWeight = continueWeight;
	}

	@Column(precision = 15, scale = 5, nullable = false)
	public BigDecimal getFirstWeightPrice() {
		return firstWeightPrice;
	}

	public void setFirstWeightPrice(BigDecimal firstWeightPrice) {
		this.firstWeightPrice = SettingUtil.setPriceScale(firstWeightPrice);
	}

	@Column(precision = 15, scale = 5, nullable = false)
	public BigDecimal getContinueWeightPrice() {
		return continueWeightPrice;
	}

	public void setContinueWeightPrice(BigDecimal continueWeightPrice) {
		this.continueWeightPrice = SettingUtil.setPriceScale(continueWeightPrice);
	}

	@Column(length = 3000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getOrderList() {
		return orderList;
	}

	public void setOrderList(Integer orderList) {
		this.orderList = orderList;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@ForeignKey(name = "fk_delivery_item_delivery_corp")
	public DeliveryCorp getDefaultDeliveryCorp() {
		return defaultDeliveryCorp;
	}

	public void setDefaultDeliveryCorp(DeliveryCorp defaultDeliveryCorp) {
		this.defaultDeliveryCorp = defaultDeliveryCorp;
	}
	
	@OneToMany(mappedBy = "deliveryType", fetch = FetchType.LAZY)
	public Set<Order> getOrderSet() {
		return orderSet;
	}

	public void setOrderSet(Set<Order> orderSet) {
		this.orderSet = orderSet;
	}

	@OneToMany(mappedBy = "deliveryType", fetch = FetchType.LAZY)
	public Set<Shipping> getShippingSet() {
		return shippingSet;
	}

	public void setShippingSet(Set<Shipping> shippingSet) {
		this.shippingSet = shippingSet;
	}

	@OneToMany(mappedBy = "deliveryType", fetch = FetchType.LAZY)
	public Set<Reship> getReshipSet() {
		return reshipSet;
	}

	public void setReshipSet(Set<Reship> reshipSet) {
		this.reshipSet = reshipSet;
	}
	
	// 保存处理
	@Override
	@Transient
	public void onSave() {
		if (firstWeight == null || firstWeight < 0) {
			firstWeight = 0;
		}
		if (continueWeight == null || continueWeight < 1) {
			continueWeight = 1;
		}
		if (firstWeightPrice == null || firstWeightPrice.compareTo(new BigDecimal(0)) < 0) {
			firstWeightPrice = new BigDecimal(0);
		}
		if (continueWeightPrice == null || continueWeightPrice.compareTo(new BigDecimal(0)) < 0) {
			continueWeightPrice = new BigDecimal(0);
		}
		if (StringUtils.isEmpty(description)) {
			description = null;
		}
		if (orderList == null || orderList < 0) {
			orderList = 0;
		}
	}
	
	// 更新处理
	@Override
	@Transient
	public void onUpdate() {
		if (firstWeight == null || firstWeight < 0) {
			firstWeight = 0;
		}
		if (continueWeight == null || continueWeight < 1) {
			continueWeight = 1;
		}
		if (firstWeightPrice == null || firstWeightPrice.compareTo(new BigDecimal(0)) < 0) {
			firstWeightPrice = new BigDecimal(0);
		}
		if (continueWeightPrice == null || continueWeightPrice.compareTo(new BigDecimal(0)) < 0) {
			continueWeightPrice = new BigDecimal(0);
		}
		if (StringUtils.isEmpty(description)) {
			description = null;
		}
		if (orderList == null || orderList < 0) {
			orderList = 0;
		}
	}
	
	/**
	 * 根据总重量、重量单位计算配送费用(单位: 克)
	 * 
	 * @return 配送费用
	 */
	@Transient
	public BigDecimal getDeliveryFee(int totalWeight) {
		BigDecimal deliveryFee = new BigDecimal(0);
		if (totalWeight <= firstWeight) {
			deliveryFee = firstWeightPrice;
		} else {
			if (continueWeightPrice.compareTo(new BigDecimal(0)) != 0) {
				double contiuneWeightCount = Math.ceil((totalWeight - firstWeight) / continueWeight);
				deliveryFee = firstWeightPrice.add(continueWeightPrice.multiply(new BigDecimal(contiuneWeightCount)));
			} else {
				deliveryFee = firstWeightPrice.add(continueWeightPrice);
			}
		}
		return SettingUtil.setPriceScale(deliveryFee);
	}

}