package net.shopxx.action.admin;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.shopxx.bean.Setting;
import net.shopxx.bean.Setting.ScoreType;
import net.shopxx.bean.Setting.StoreFreezeTime;
import net.shopxx.entity.Area;
import net.shopxx.entity.DeliveryCenter;
import net.shopxx.entity.DeliveryCorp;
import net.shopxx.entity.DeliveryItem;
import net.shopxx.entity.DeliveryTemplate;
import net.shopxx.entity.DeliveryType;
import net.shopxx.entity.Deposit;
import net.shopxx.entity.Member;
import net.shopxx.entity.MemberRank;
import net.shopxx.entity.Order;
import net.shopxx.entity.OrderItem;
import net.shopxx.entity.OrderLog;
import net.shopxx.entity.Payment;
import net.shopxx.entity.PaymentConfig;
import net.shopxx.entity.Product;
import net.shopxx.entity.Refund;
import net.shopxx.entity.Reship;
import net.shopxx.entity.Shipping;
import net.shopxx.entity.Deposit.DepositType;
import net.shopxx.entity.Order.OrderStatus;
import net.shopxx.entity.Order.ShippingStatus;
import net.shopxx.entity.OrderLog.OrderLogType;
import net.shopxx.entity.Payment.PaymentStatus;
import net.shopxx.entity.Payment.PaymentType;
import net.shopxx.entity.Refund.RefundType;
import net.shopxx.service.AdminService;
import net.shopxx.service.AreaService;
import net.shopxx.service.DeliveryCenterService;
import net.shopxx.service.DeliveryCorpService;
import net.shopxx.service.DeliveryItemService;
import net.shopxx.service.DeliveryTemplateService;
import net.shopxx.service.DeliveryTypeService;
import net.shopxx.service.DepositService;
import net.shopxx.service.MemberRankService;
import net.shopxx.service.MemberService;
import net.shopxx.service.OrderItemService;
import net.shopxx.service.OrderLogService;
import net.shopxx.service.OrderService;
import net.shopxx.service.PaymentConfigService;
import net.shopxx.service.PaymentService;
import net.shopxx.service.ProductService;
import net.shopxx.service.RefundService;
import net.shopxx.service.ReshipService;
import net.shopxx.service.ShippingService;
import net.shopxx.util.SettingUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 订单
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXCC7D55FE70812A2D98AAB420D628038C
 * ============================================================================
 */

@ParentPackage("admin")
@Results({ 
	@Result(name = "processAction", location = "order!process.action", params = {"id", "${order.id}"}, type = "redirect")
})
public class OrderAction extends BaseAdminAction {

	private static final long serialVersionUID = -2080980180511054311L;

	private String shipAreaId;
	private String reshipAreaId;
	
	private Order order;
	private Payment payment;
	private Shipping shipping;
	private Refund refund;
	private Reship reship;
	private DeliveryType deliveryType;
	private PaymentConfig paymentConfig;
	private DeliveryCorp deliveryCorp;
	private DeliveryCenter deliveryCenter;
	private DeliveryTemplate deliveryTemplate;
	
	private List<OrderItem> orderItemList;
	private List<DeliveryItem> deliveryItemList;

	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "orderItemServiceImpl")
	private OrderItemService orderItemService;
	@Resource(name = "orderLogServiceImpl")
	private OrderLogService orderLogService;
	@Resource(name = "deliveryTypeServiceImpl")
	private DeliveryTypeService deliveryTypeService;
	@Resource(name = "deliveryCorpServiceImpl")
	private DeliveryCorpService deliveryCorpService;
	@Resource(name = "paymentConfigServiceImpl")
	private PaymentConfigService paymentConfigService;
	@Resource(name = "paymentServiceImpl")
	private PaymentService paymentService;
	@Resource(name = "depositServiceImpl")
	private DepositService depositService;
	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "memberRankServiceImpl")
	private MemberRankService memberRankService;
	@Resource(name = "adminServiceImpl")
	private AdminService adminService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "deliveryItemServiceImpl")
	private DeliveryItemService deliveryItemService;
	@Resource(name = "shippingServiceImpl")
	private ShippingService shippingService;
	@Resource(name = "refundServiceImpl")
	private RefundService refundService;
	@Resource(name = "reshipServiceImpl")
	private ReshipService reshipService;
	@Resource(name = "deliveryCenterServiceImpl")
	private DeliveryCenterService deliveryCenterService;
	@Resource(name = "deliveryTemplateServiceImpl")
	private DeliveryTemplateService deliveryTemplateService;

	// 列表
	public String list() {
		pager = orderService.findPager(pager);
		return LIST;
	}
	
	// 订单打印
	public String orderPrint() {
		order = orderService.load(id);
		return "order_print";
	}
	
	// 购物单打印
	public String goodsPrint() {
		order = orderService.load(id);
		return "goods_print";
	}
	
	// 配送单打印
	public String shippingPrint() {
		order = orderService.load(id);
		return "shipping_print";
	}
	
	// 快递单打印
	public String deliveryPrint() {
		if (deliveryCenter == null || StringUtils.isEmpty(deliveryCenter.getId())) {
			deliveryCenter = deliveryCenterService.getDefaultDeliveryCenter();
		} else {
			deliveryCenter = deliveryCenterService.load(deliveryCenter.getId());
		}
		if (deliveryTemplate == null || StringUtils.isEmpty(deliveryTemplate.getId())) {
			deliveryTemplate = deliveryTemplateService.getDefaultDeliveryTemplate();
		} else {
			deliveryTemplate = deliveryTemplateService.load(deliveryTemplate.getId());
		}
		
		if (deliveryCenter == null) {
			addActionError("请设置发货点!");
			return ERROR;
		}
		if (deliveryTemplate == null) {
			addActionError("请设置快递单打印模板!");
			return ERROR;
		}
		
		order = orderService.load(id);
		return "delivery_print";
	}

	// 订单删除
	public String delete() {
		try {
			orderService.delete(ids);
		} catch (Exception e) {
			return ajax(Status.error, "删除失败,订单数据被引用!");
		}
		return ajax(Status.success, "删除成功!");
	}

	// 订单编辑
	public String edit() {
		order = orderService.load(id);
		if (order.getOrderStatus() == OrderStatus.completed || order.getOrderStatus() == OrderStatus.invalid) {
			addActionError("此订单状态无法编辑!");
			return ERROR;
		}
		if (order.getPaymentStatus() != net.shopxx.entity.Order.PaymentStatus.unpaid) {
			addActionError("此订单付款状态无法编辑!");
			return ERROR;
		}
		if (order.getShippingStatus() != ShippingStatus.unshipped) {
			addActionError("此订单发货状态无法编辑!");
			return ERROR;
		}
		return INPUT;
	}
	
	// 订单更新
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "shipAreaId", message = "收货人地区不允许为空!"),
			@RequiredStringValidator(fieldName = "order.shipName", message = "收货人姓名不允许为空!"),
			@RequiredStringValidator(fieldName = "order.shipAddress", message = "收货人地址不允许为空!"),
			@RequiredStringValidator(fieldName = "order.shipZipCode", message = "邮编不允许为空!")
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "deliveryType.id", message = "配送方式不允许为空!"),
			@RequiredFieldValidator(fieldName = "order.deliveryFee", message = "配送费用不允许为空!"),
			@RequiredFieldValidator(fieldName = "order.paymentFee", message = "支付费用不允许为空!"),
			@RequiredFieldValidator(fieldName = "order.totalProductWeight", message = "商品重量不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		Order persistent = orderService.load(id);
		if (persistent.getOrderStatus() == OrderStatus.completed || persistent.getOrderStatus() == OrderStatus.invalid) {
			addActionError("此订单状态无法编辑!");
			return ERROR;
		}
		if (persistent.getPaymentStatus() != net.shopxx.entity.Order.PaymentStatus.unpaid) {
			addActionError("此订单付款状态无法编辑!");
			return ERROR;
		}
		if (persistent.getShippingStatus() != ShippingStatus.unshipped) {
			addActionError("此订单配送状态无法编辑!");
			return ERROR;
		}
		if (order.getDeliveryFee().compareTo(new BigDecimal(0)) < 0) {
			addActionError("配送费用不允许小于0!");
			return ERROR;
		}
		if (order.getPaymentFee().compareTo(new BigDecimal(0)) < 0) {
			addActionError("支付费用不允许小于0!");
			return ERROR;
		}
		if (order.getTotalProductWeight() < 0) {
			addActionError("商品重量不允许小于0!");
			return ERROR;
		}
		if (StringUtils.isEmpty(order.getShipPhone()) && StringUtils.isEmpty(order.getShipMobile())) {
			addActionError("联系电话、联系手机必须填写其中一项!");
			return ERROR;
		}
		if (orderItemList == null || orderItemList.size() == 0) {
			addActionError("请保留至少一个商品!");
			return ERROR;
		}
		
		Area shipArea = areaService.get(shipAreaId);
		if (shipArea == null) {
			addActionError("请选择收货地区!");
			return ERROR;
		}
		
		Setting setting = getSetting();
		for (OrderItem orderItem : orderItemList) {
			if (orderItem.getProductQuantity() < 1) {
				addActionError("商品数量错误!");
				return ERROR;
			}
			if (orderItem.getProductPrice().compareTo(new BigDecimal(0)) < 0) {
				addActionError("商品价格错误!");
				return ERROR;
			}
			OrderItem persistentOrderItem = orderItemService.load(orderItem.getId());
			Product product = persistentOrderItem.getProduct();
			if (product.getStore() != null) {
				Integer availableStore = 0;
				if ((setting.getStoreFreezeTime() == StoreFreezeTime.payment && order.getPaymentStatus() == net.shopxx.entity.Order.PaymentStatus.unpaid) || 
					(setting.getStoreFreezeTime() == StoreFreezeTime.ship && order.getShippingStatus() == ShippingStatus.unshipped)) {
					availableStore = product.getStore() - product.getFreezeStore();
				} else {
					availableStore = product.getStore() - product.getFreezeStore() + persistentOrderItem.getProductQuantity();
				}
				if (orderItem.getProductQuantity() > availableStore) {
					addActionError("商品[" + product.getName() + "]库存不足!");
					return ERROR;
				}
				if (setting.getStoreFreezeTime() == StoreFreezeTime.order || (setting.getStoreFreezeTime() == StoreFreezeTime.payment && order.getPaymentStatus() != net.shopxx.entity.Order.PaymentStatus.unpaid) || 
					(setting.getStoreFreezeTime() == StoreFreezeTime.ship && order.getShippingStatus() != ShippingStatus.unshipped)) {
					product.setFreezeStore(product.getFreezeStore() - persistentOrderItem.getProductQuantity() + orderItem.getProductQuantity());
					productService.update(product);
				}
			}
		}
		
		Integer totalProductQuantity = 0;// 商品总数
		BigDecimal totalProductPrice = new BigDecimal(0);// 商品总价格
		BigDecimal totalAmount = new BigDecimal(0);// 订单总金额
		for (OrderItem orderItem : orderItemList) {
			OrderItem orderItemPersistent = orderItemService.load(orderItem.getId());
			totalProductQuantity += orderItem.getProductQuantity();
			totalProductPrice = totalProductPrice.add(orderItem.getProductPrice().multiply(new BigDecimal(orderItem.getProductQuantity().toString())));
			BeanUtils.copyProperties(orderItem, orderItemPersistent, new String[] {"id", "createDate", "modifyDate", "productSn", "productName", "productHtmlPath", "deliveryQuantity", "totalDeliveryQuantity", "order", "product"});
			orderItemService.update(orderItemPersistent);
		}
		for (OrderItem orderItem : persistent.getOrderItemSet()) {
			if (!orderItemList.contains(orderItem)) {
				orderItemService.delete(orderItem);
			}
		}
		
		deliveryType = deliveryTypeService.load(deliveryType.getId());
		String paymentConfigName = null;
		if (paymentConfig != null && StringUtils.isNotEmpty(paymentConfig.getId())) {
			paymentConfig = paymentConfigService.load(paymentConfig.getId());
			paymentConfigName = paymentConfig.getName();
		} else {
			paymentConfig = null;
			paymentConfigName = "货到付款";
		}
		
		totalAmount = totalProductPrice.add(order.getDeliveryFee()).add(order.getPaymentFee());
		order.setShipArea(shipArea);
		order.setTotalAmount(totalAmount);
		order.setOrderStatus(OrderStatus.processed);
		order.setDeliveryTypeName(deliveryType.getName());
		order.setPaymentConfigName(paymentConfigName);
		order.setTotalProductPrice(totalProductPrice);
		order.setTotalProductQuantity(totalProductQuantity);
		order.setPaymentConfig(paymentConfig);
		BeanUtils.copyProperties(order, persistent, new String[] {"id", "createDate", "modifyDate", "orderSn", "orderStatus", "paymentStatus", "shippingStatus", "paidAmount", "memo", "member"});
		orderService.update(persistent);
		logInfo = "订单编号: " + persistent.getOrderSn();
		
		// 订单日志
		OrderLog orderLog = new OrderLog();
		orderLog.setOrderLogType(OrderLogType.modify);
		orderLog.setOrderSn(persistent.getOrderSn());
		orderLog.setOperator(adminService.getLoginAdmin().getUsername());
		orderLog.setInfo(null);
		orderLog.setOrder(persistent);
		orderLogService.save(orderLog);
		
		redirectUrl = "order!list.action";
		return SUCCESS;
	}
	
	// 订单处理
	public String process() {
		order = orderService.load(id);
		return "process";
	}
	
	// 订单支付
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "paymentConfig.id", message = "支付方式不允许为空!")
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "payment.paymentType", message = "支付类型不允许为空!"),
			@RequiredFieldValidator(fieldName = "payment.totalAmount", message = "支付金额不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String payment() {
		order = orderService.load(id);
		if (order.getOrderStatus() == OrderStatus.completed || order.getOrderStatus() == OrderStatus.invalid) {
			addActionError("此订单状态无法支付!");
			return ERROR;
		}
		if (order.getPaymentStatus() == net.shopxx.entity.Order.PaymentStatus.paid || order.getPaymentStatus() == net.shopxx.entity.Order.PaymentStatus.partRefund || order.getPaymentStatus() == net.shopxx.entity.Order.PaymentStatus.refunded) {
			addActionError("此订单付款状态无法支付!");
			return ERROR;
		}
		if (payment.getPaymentType() == PaymentType.recharge) {
			addActionError("支付类型错误!");
			return ERROR;
		}
		if (payment.getTotalAmount().compareTo(new BigDecimal(0)) < 0) {
			addActionError("支付金额不允许小于0!");
			return ERROR;
		}
		if (payment.getTotalAmount().compareTo(order.getTotalAmount().subtract(order.getPaidAmount())) > 0) {
			addActionError("支付金额超出订单需付金额!");
			return ERROR;
		}
		Deposit deposit = null;
		if (payment.getPaymentType() == PaymentType.deposit) {
			Member member = order.getMember();
			if (member == null) {
				addActionError("会员不存在,不允许使用预存款支付!");
				return ERROR;
			}
			if (payment.getTotalAmount().compareTo(member.getDeposit()) > 0) {
				addActionError("会员预存款余额不足!");
				return ERROR;
			}
			member.setDeposit(member.getDeposit().subtract(payment.getTotalAmount()));
			memberService.update(member);
			
			deposit = new Deposit();
			deposit.setDepositType(DepositType.adminPayment);
			deposit.setCredit(new BigDecimal(0));
			deposit.setDebit(payment.getTotalAmount());
			deposit.setBalance(member.getDeposit());
			deposit.setMember(member);
			depositService.save(deposit);
		}
		
		paymentConfig = paymentConfigService.load(paymentConfig.getId());
		payment.setPaymentConfigName(paymentConfig.getName());
		payment.setPaymentFee(new BigDecimal(0));
		payment.setOperator(adminService.getLoginAdmin().getUsername());
		payment.setPaymentStatus(PaymentStatus.success);
		payment.setDeposit(deposit);
		payment.setOrder(order);
		paymentService.save(payment);
		
		// 库存占用数处理
		Setting setting = getSetting();
		if (setting.getStoreFreezeTime() == StoreFreezeTime.payment && order.getPaymentStatus() == net.shopxx.entity.Order.PaymentStatus.unpaid && order.getShippingStatus() == ShippingStatus.unshipped) {
			for (OrderItem orderItem : order.getOrderItemSet()) {
				Product product = orderItem.getProduct();
				if (product.getStore() != null) {
					product.setFreezeStore(product.getFreezeStore() + orderItem.getProductQuantity());
					productService.update(product);
				}
			}
		}
		
		order.setOrderStatus(OrderStatus.processed);
		if (payment.getTotalAmount().compareTo(order.getTotalAmount().subtract(order.getPaidAmount())) < 0) {
			order.setPaymentStatus(net.shopxx.entity.Order.PaymentStatus.partPayment);
		} else {
			order.setPaymentStatus(net.shopxx.entity.Order.PaymentStatus.paid);
		}
		order.setPaidAmount(payment.getTotalAmount().add(order.getPaidAmount()));
		orderService.update(order);
		logInfo = "订单编号: " + order.getOrderSn();
		
		// 订单日志
		OrderLog orderLog = new OrderLog();
		orderLog.setOrderLogType(OrderLogType.payment);
		orderLog.setOrderSn(order.getOrderSn());
		orderLog.setOperator(adminService.getLoginAdmin().getUsername());
		orderLog.setInfo("支付金额: " + SettingUtil.currencyFormat(payment.getTotalAmount()));
		orderLog.setOrder(order);
		orderLogService.save(orderLog);
		
		return "processAction";
	}
	
	// 订单发货
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "shipAreaId", message = "收货人地区不允许为空!"),
			@RequiredStringValidator(fieldName = "shipping.shipName", message = "收货人姓名不允许为空!"),
			@RequiredStringValidator(fieldName = "shipping.shipAddress", message = "收货人地址不允许为空!"),
			@RequiredStringValidator(fieldName = "shipping.shipZipCode", message = "邮编不允许为空!"),
			@RequiredStringValidator(fieldName = "deliveryType.id", message = "配送方式不允许为空!"),
			@RequiredStringValidator(fieldName = "deliveryCorp.id", message = "物流公司不允许为空!")
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "shipping.deliveryFee", message = "物流费用不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String shipping() {
		order = orderService.load(id);
		if (order.getOrderStatus() == OrderStatus.completed || order.getOrderStatus() == OrderStatus.invalid) {
			addActionError("此订单状态无法发货!");
			return ERROR;
		}
		if (order.getShippingStatus() == ShippingStatus.shipped) {
			addActionError("此订单配送状态无法发货!");
			return ERROR;
		}
		if (shipping.getDeliveryFee().compareTo(new BigDecimal(0)) < 0) {
			addActionError("物流费用不允许小于0!");
			return ERROR;
		}
		if (StringUtils.isEmpty(shipping.getShipPhone()) && StringUtils.isEmpty(shipping.getShipMobile())) {
			addActionError("电话、手机必须填写其中一项!");
			return ERROR;
		}
		
		Setting setting = getSetting();
		int totalDeliveryQuantity = 0;
		Set<OrderItem> orderItemSet = order.getOrderItemSet();
		for (DeliveryItem deliveryItem : deliveryItemList) {
			Product product = productService.load(deliveryItem.getProduct().getId());
			Integer deliveryQuantity = deliveryItem.getDeliveryQuantity();
			if (deliveryQuantity < 0) {
				addActionError("发货数不允许小于0!");
				return ERROR;
			}
			totalDeliveryQuantity += deliveryQuantity;
			
			boolean isExist = false;
			for (OrderItem orderItem : orderItemSet) {
				if (product == orderItem.getProduct()) {
					if (deliveryQuantity > (orderItem.getProductQuantity() - orderItem.getDeliveryQuantity())) {
						addActionError("发货数超出订单购买数!");
						return ERROR;
					}
					if (product.getStore() != null) {
						if ((setting.getStoreFreezeTime() == StoreFreezeTime.payment && order.getPaymentStatus() == net.shopxx.entity.Order.PaymentStatus.unpaid && order.getShippingStatus() == ShippingStatus.unshipped) || (setting.getStoreFreezeTime() == StoreFreezeTime.ship && order.getShippingStatus() == ShippingStatus.unshipped)) {
							if (deliveryQuantity > (product.getStore() - product.getFreezeStore())) {
								addActionError("商品[" + orderItem.getProduct().getName() + "]库存不足!");
								return ERROR;
							}
						} else {
							if (deliveryQuantity > (product.getStore() - product.getFreezeStore() + orderItem.getProductQuantity() - orderItem.getDeliveryQuantity())) {
								addActionError("商品[" + orderItem.getProduct().getName() + "]库存不足!");
								return ERROR;
							}
						}
					}
					isExist = true;
					break;
				}
			}
			if (!isExist) {
				addActionError("发货商品未在订单中!");
				return ERROR;
			}
		}
		if (totalDeliveryQuantity < 1) {
			addActionError("发货总数必须大于0!");
			return ERROR;
		}
		
		Area shipArea = areaService.get(shipAreaId);
		if (shipArea == null) {
			addActionError("请选择收货地区!");
			return ERROR;
		}
		
		deliveryType = deliveryTypeService.load(deliveryType.getId());
		deliveryCorp = deliveryCorpService.load(deliveryCorp.getId());
		
		shipping.setDeliveryTypeName(deliveryType.getName());
		shipping.setDeliveryCorpName(deliveryCorp.getName());
		shipping.setDeliveryCorpUrl(deliveryCorp.getUrl());
		shipping.setShipArea(shipArea);
		shipping.setOrder(order);
		shippingService.save(shipping);
		logInfo = "订单编号: " + order.getOrderSn();
		
		// 库存占用数处理
		if ((setting.getStoreFreezeTime() == StoreFreezeTime.payment && order.getPaymentStatus() == net.shopxx.entity.Order.PaymentStatus.unpaid && order.getShippingStatus() == ShippingStatus.unshipped) || (setting.getStoreFreezeTime() == StoreFreezeTime.ship && order.getShippingStatus() == ShippingStatus.unshipped)) {
			for (OrderItem orderItem : order.getOrderItemSet()) {
				Product product = orderItem.getProduct();
				if (product.getStore() != null) {
					product.setFreezeStore(product.getFreezeStore() + orderItem.getProductQuantity());
					productService.update(product);
				}
			}
		}
		
		StringBuffer orderLogInfo = new StringBuffer();
		if (StringUtils.isNotEmpty(shipping.getDeliveryCorpName())) {
			if (StringUtils.isNotEmpty(shipping.getDeliveryCorpUrl())) {
				orderLogInfo.append("物流公司: <a href=\"" + shipping.getDeliveryCorpUrl() + "\" target=\"_blank\">" + shipping.getDeliveryCorpName() + "</a><br />");
			} else {
				orderLogInfo.append("物流公司: " + shipping.getDeliveryCorpName() + "<br />");
			}
			if (StringUtils.isNotEmpty(shipping.getDeliverySn())) {
				orderLogInfo.append("物流单号: " + shipping.getDeliverySn() + "<br />");
			}
		}
		orderLogInfo.append("商品信息: ");
		
		ShippingStatus shippingStatus = ShippingStatus.shipped;
		for (DeliveryItem deliveryItem : deliveryItemList) {
			Product product = productService.load(deliveryItem.getProduct().getId());
			for (OrderItem orderItem : orderItemSet) {
				if (orderItem.getProduct() == product) {
					orderItem.setDeliveryQuantity(orderItem.getDeliveryQuantity() + deliveryItem.getDeliveryQuantity());
					orderItemService.update(orderItem);
					if (orderItem.getDeliveryQuantity() < orderItem.getProductQuantity()) {
						shippingStatus = ShippingStatus.partShipped;
					}
					
					// 库存占用数处理
					if (product.getStore() != null) {
						product.setFreezeStore(product.getFreezeStore() - deliveryItem.getDeliveryQuantity());
						product.setStore(product.getStore() - deliveryItem.getDeliveryQuantity());
						productService.update(product);
					}
					break;
				}
			}
			orderLogInfo.append(product.getName()).append(" × ").append(deliveryItem.getDeliveryQuantity()).append("<br />");
			
			deliveryItem.setProductSn(product.getProductSn());
			deliveryItem.setProductName(product.getName());
			deliveryItem.setGoodsHtmlPath(product.getGoods().getHtmlPath());
			deliveryItem.setShipping(shipping);
			deliveryItemService.save(deliveryItem);
		}
		order.setOrderStatus(OrderStatus.processed);
		order.setShippingStatus(shippingStatus);
		orderService.update(order);
		
		OrderLog orderLog = new OrderLog();
		orderLog.setOrderLogType(OrderLogType.shipping);
		orderLog.setOrderSn(order.getOrderSn());
		orderLog.setOperator(adminService.getLoginAdmin().getUsername());
		orderLog.setInfo(orderLogInfo.toString());
		orderLog.setOrder(order);
		orderLogService.save(orderLog);
		
		return "processAction";
	}
	
	// 订单退款
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "paymentConfig.id", message = "退款方式不允许为空!")
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "refund.refundType", message = "退款类型不允许为空!"),
			@RequiredFieldValidator(fieldName = "refund.totalAmount", message = "退款金额不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String refund() {
		order = orderService.load(id);
		if (order.getOrderStatus() == OrderStatus.completed || order.getOrderStatus() == OrderStatus.invalid) {
			addActionError("此订单状态无法退款!");
			return ERROR;
		}
		if (order.getPaymentStatus() == net.shopxx.entity.Order.PaymentStatus.unpaid || order.getPaymentStatus() == net.shopxx.entity.Order.PaymentStatus.refunded) {
			addActionError("此订单付款状态无法退款!");
			return ERROR;
		}
		if (refund.getTotalAmount().compareTo(new BigDecimal(0)) < 0) {
			addActionError("退款金额不允许小于0!");
			return ERROR;
		}
		if (refund.getTotalAmount().compareTo(order.getPaidAmount()) > 0) {
			addActionError("退款金额超出订单已付金额!");
			return ERROR;
		}
		Deposit deposit = null;
		if (refund.getRefundType() == RefundType.deposit) {
			Member member = order.getMember();
			if (member == null) {
				addActionError("会员不存在,不允许使用预存款退款!");
				return ERROR;
			}
			
			member.setDeposit(member.getDeposit().add(refund.getTotalAmount()));
			memberService.update(member);
			
			deposit = new Deposit();
			deposit.setDepositType(DepositType.adminRecharge);
			deposit.setCredit(refund.getTotalAmount());
			deposit.setDebit(new BigDecimal(0));
			deposit.setBalance(member.getDeposit());
			deposit.setMember(member);
			depositService.save(deposit);
		}
		
		paymentConfig = paymentConfigService.load(paymentConfig.getId());
		refund.setPaymentConfigName(paymentConfig.getName());
		refund.setOperator(adminService.getLoginAdmin().getUsername());
		refund.setDeposit(deposit);
		refund.setOrder(order);
		refundService.save(refund);
		logInfo = "订单编号: " + order.getOrderSn();
		
		order.setOrderStatus(OrderStatus.processed);
		if (refund.getTotalAmount().compareTo(order.getPaidAmount()) < 0) {
			order.setPaymentStatus(net.shopxx.entity.Order.PaymentStatus.partRefund);
		} else {
			order.setPaymentStatus(net.shopxx.entity.Order.PaymentStatus.refunded);
		}
		order.setPaidAmount(order.getPaidAmount().subtract(refund.getTotalAmount()));
		orderService.update(order);
		
		// 订单日志
		OrderLog orderLog = new OrderLog();
		orderLog.setOrderLogType(OrderLogType.refund);
		orderLog.setOrderSn(order.getOrderSn());
		orderLog.setOperator(adminService.getLoginAdmin().getUsername());
		orderLog.setInfo("退款金额: " + SettingUtil.currencyFormat(refund.getTotalAmount()));
		orderLog.setOrder(order);
		orderLogService.save(orderLog);
		
		return "processAction";
	}
	
	// 订单退货
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "reshipAreaId", message = "退货地区不允许为空!"),
			@RequiredStringValidator(fieldName = "reship.reshipName", message = "退货人姓名不允许为空!"),
			@RequiredStringValidator(fieldName = "reship.reshipAddress", message = "退货人地址不允许为空!"),
			@RequiredStringValidator(fieldName = "reship.reshipZipCode", message = "邮编不允许为空!"),
			@RequiredStringValidator(fieldName = "deliveryType.id", message = "配送方式不允许为空!"),
			@RequiredStringValidator(fieldName = "deliveryCorp.id", message = "物流公司不允许为空!")
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "reship.deliveryFee", message = "物流费用不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String reship() {
		order = orderService.load(id);
		if (order.getOrderStatus() == OrderStatus.completed || order.getOrderStatus() == OrderStatus.invalid) {
			addActionError("此订单状态无法退货!");
			return ERROR;
		}
		if (order.getShippingStatus() == ShippingStatus.unshipped || order.getShippingStatus() == ShippingStatus.reshiped) {
			addActionError("此订单配送状态无法退货!");
			return ERROR;
		}
		if (reship.getDeliveryFee().compareTo(new BigDecimal(0)) < 0) {
			addActionError("物流费用不允许小于0!");
			return ERROR;
		}
		if (StringUtils.isEmpty(reship.getReshipPhone()) && StringUtils.isEmpty(reship.getReshipMobile())) {
			addActionError("电话、手机必须填写其中一项!");
			return ERROR;
		}
		
		Area reshipArea = areaService.get(reshipAreaId);
		if (reshipArea == null) {
			addActionError("请选择退货地区!");
			return ERROR;
		}
		
		Set<OrderItem> orderItemSet = order.getOrderItemSet();
		int totalDeliveryQuantity = 0;
		for (DeliveryItem deliveryItem : deliveryItemList) {
			Product product = productService.load(deliveryItem.getProduct().getId());
			Integer deliveryQuantity = deliveryItem.getDeliveryQuantity();
			if (deliveryQuantity < 0) {
				addActionError("退货数不允许小于0!");
				return ERROR;
			}
			totalDeliveryQuantity += deliveryQuantity;
			
			boolean isExist = false;
			for (OrderItem orderItem : orderItemSet) {
				if (product == orderItem.getProduct()) {
					if (deliveryQuantity > orderItem.getDeliveryQuantity()) {
						addActionError("退货数超出已发货数!");
						return ERROR;
					}
					isExist = true;
					break;
				}
			}
			if (!isExist) {
				addActionError("退货商品未在订单中!");
				return ERROR;
			}
		}
		if (totalDeliveryQuantity < 1) {
			addActionError("退货总数必须大于0!");
			return ERROR;
		}
		
		deliveryType = deliveryTypeService.load(deliveryType.getId());
		deliveryCorp = deliveryCorpService.load(deliveryCorp.getId());
		
		reship.setDeliveryTypeName(deliveryType.getName());
		reship.setDeliveryCorpName(deliveryCorp.getName());
		reship.setReshipArea(reshipArea);
		reship.setOrder(order);
		reshipService.save(reship);
		logInfo = "订单编号: " + order.getOrderSn();
		
		StringBuffer orderLogInfo = new StringBuffer("商品信息: ");
		ShippingStatus shippingStatus = ShippingStatus.reshiped;
		for (DeliveryItem deliveryItem : deliveryItemList) {
			Product product = productService.load(deliveryItem.getProduct().getId());
			for (OrderItem orderItem : orderItemSet) {
				if (orderItem.getProduct() == product) {
					orderItem.setDeliveryQuantity(orderItem.getDeliveryQuantity() - deliveryItem.getDeliveryQuantity());
					orderItemService.update(orderItem);
					if (deliveryItem.getDeliveryQuantity() < orderItem.getDeliveryQuantity()) {
						shippingStatus = ShippingStatus.partReshiped;
					}
				}
			}
			orderLogInfo.append(product.getName()).append(" × ").append(deliveryItem.getDeliveryQuantity()).append("<br />");
			
			deliveryItem.setProductSn(product.getProductSn());
			deliveryItem.setProductName(product.getName());
			deliveryItem.setGoodsHtmlPath(product.getGoods().getHtmlPath());
			deliveryItem.setReship(reship);
			deliveryItemService.save(deliveryItem);
		}
		order.setOrderStatus(OrderStatus.processed);
		order.setShippingStatus(shippingStatus);
		orderService.update(order);
		
		// 订单日志
		OrderLog orderLog = new OrderLog();
		orderLog.setOrderLogType(OrderLogType.reship);
		orderLog.setOrderSn(order.getOrderSn());
		orderLog.setOperator(adminService.getLoginAdmin().getUsername());
		orderLog.setInfo(orderLogInfo.toString());
		orderLog.setOrder(order);
		orderLogService.save(orderLog);
		return "processAction";
	}
	
	// 订单完成
	public String completed() {
		order = orderService.load(id);
		if (order.getOrderStatus() == OrderStatus.completed) {
			return ajax(Status.warn, "此订单已经完成!");
		} else if (order.getOrderStatus() == OrderStatus.invalid) {
			return ajax(Status.error, "此订单已经作废!");
		} else {
			order.setOrderStatus(OrderStatus.completed);
			orderService.update(order);
			logInfo = "订单编号: " + order.getOrderSn();
			
			Setting setting = getSetting();
			Integer totalScore = 0;
			String orderLogInfo = null;
			if (setting.getScoreType() == ScoreType.orderAmount) {
				totalScore = order.getTotalProductPrice().multiply(new BigDecimal(setting.getScoreScale().toString())).setScale(0, RoundingMode.DOWN).intValue();
			} else if (setting.getScoreType() == ScoreType.goodsSet) {
				for (OrderItem orderItem : order.getOrderItemSet()) {
					totalScore = orderItem.getProduct().getGoods().getScore() * orderItem.getProductQuantity() + totalScore;
				}
			}
			if (totalScore > 0) {
				orderLogInfo = "订单完成,获得积分: " + totalScore;
				Member member = order.getMember();
				member.setScore(member.getScore() + totalScore);
				MemberRank upMemberRank = memberRankService.getUpMemberRankByScore(member.getScore());
				if (upMemberRank != null && member.getScore() >= upMemberRank.getScore()) {
					member.setMemberRank(upMemberRank);
				}
				memberService.update(member);
			}
			
			// 订单日志
			OrderLog orderLog = new OrderLog();
			orderLog.setOrderLogType(OrderLogType.completed);
			orderLog.setOrderSn(order.getOrderSn());
			orderLog.setOperator(adminService.getLoginAdmin().getUsername());
			orderLog.setInfo(orderLogInfo);
			orderLog.setOrder(order);
			orderLogService.save(orderLog);
			
			return ajax(Status.success, "您的操作已成功!");
		}
	}
	
	// 作废
	public String invalid() {
		order = orderService.load(id);
		if (order.getOrderStatus() == OrderStatus.completed || order.getOrderStatus() == OrderStatus.invalid) {
			addActionError("此订单状态无法编辑!");
			return ERROR;
		}
		if (order.getPaymentStatus() != net.shopxx.entity.Order.PaymentStatus.unpaid) {
			addActionError("此订单支付状态无法编辑!");
			return ERROR;
		}
		if (order.getShippingStatus() != ShippingStatus.unshipped) {
			addActionError("此订单发货状态无法编辑!");
			return ERROR;
		}
		
		if (order.getOrderStatus() == OrderStatus.completed) {
			return ajax(Status.warn, "此订单已经完成!");
		} else if (order.getOrderStatus() == OrderStatus.invalid) {
			return ajax(Status.error, "此订单已经作废!");
		} else if (order.getPaymentStatus() != net.shopxx.entity.Order.PaymentStatus.unpaid) {
			return ajax(Status.error, "此订单付款状态无法作废!");
		} else if (order.getShippingStatus() != ShippingStatus.unshipped) {
			return ajax(Status.error, "此订单配送状态无法作废!");
		} else {
			order.setOrderStatus(OrderStatus.invalid);
			orderService.update(order);
			logInfo = "订单编号: " + order.getOrderSn();
			
			Setting setting = getSetting();
			if (setting.getStoreFreezeTime() == StoreFreezeTime.order || (setting.getStoreFreezeTime() == StoreFreezeTime.payment && order.getPaymentStatus() != net.shopxx.entity.Order.PaymentStatus.unpaid) || order.getShippingStatus() != ShippingStatus.unshipped) {
				for (OrderItem orderItem : order.getOrderItemSet()) {
					Product product = orderItem.getProduct();
					product.setFreezeStore(product.getFreezeStore() - orderItem.getProductQuantity());
					productService.update(product);
				}
			}
			
			// 订单日志
			OrderLog orderLog = new OrderLog();
			orderLog.setOrderLogType(OrderLogType.invalid);
			orderLog.setOrderSn(order.getOrderSn());
			orderLog.setOperator(adminService.getLoginAdmin().getUsername());
			orderLog.setInfo(null);
			orderLog.setOrder(order);
			orderLogService.save(orderLog);
			
			return ajax(Status.success, "您的操作已成功!");
		}
	}
	
	// 订单查看
	public String view() {
		order = orderService.load(id);
		return "view";
	}
	
	// 获取支付类型集合（不包含在线充值）
	public List<PaymentType> getNonRechargePaymentTypeList() {
		List<PaymentType> paymentTypeList = new ArrayList<PaymentType>();
		for (PaymentType paymentType : PaymentType.values()) {
			if (paymentType != PaymentType.recharge) {
				paymentTypeList.add(paymentType);
			}
		}
		return paymentTypeList;
	}
	
	// 获取退款类型集合
	public List<RefundType> getRefundTypeList() {
		return Arrays.asList(RefundType.values());
	}
	
	// 获取所有配送方式集合
	public List<DeliveryType> getAllDeliveryTypeList() {
		return deliveryTypeService.getAllList();
	}
	
	// 获取所有支付方式集合
	public List<PaymentConfig> getAllPaymentConfigList() {
		return paymentConfigService.getAllList();
	}
	
	// 获取所有物流公司集合
	public List<DeliveryCorp> getAllDeliveryCorpList() {
		return deliveryCorpService.getAllList();
	}
	
	// 获取所有发货点集合
	public List<DeliveryCenter> getAllDeliveryCenterList() {
		return deliveryCenterService.getAllList();
	}
	
	// 获取所有快递单模板集合
	public List<DeliveryTemplate> getAllDeliveryTemplateList() {
		return deliveryTemplateService.getAllList();
	}
	
	// 获取当前日期
	public Date getCurrentDate() {
		return new Date();
	}
	
	// 获取当前日期(年)
	public int getCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}
	
	// 获取当前日期(月)
	public int getCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH) + 1;
	}
	
	// 获取当前日期(日)
	public int getCurrentDay() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_MONTH) + 1;
	}

	public String getShipAreaId() {
		return shipAreaId;
	}

	public void setShipAreaId(String shipAreaId) {
		this.shipAreaId = shipAreaId;
	}

	public String getReshipAreaId() {
		return reshipAreaId;
	}

	public void setReshipAreaId(String reshipAreaId) {
		this.reshipAreaId = reshipAreaId;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Shipping getShipping() {
		return shipping;
	}

	public void setShipping(Shipping shipping) {
		this.shipping = shipping;
	}

	public Refund getRefund() {
		return refund;
	}

	public void setRefund(Refund refund) {
		this.refund = refund;
	}

	public Reship getReship() {
		return reship;
	}

	public void setReship(Reship reship) {
		this.reship = reship;
	}

	public DeliveryType getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(DeliveryType deliveryType) {
		this.deliveryType = deliveryType;
	}

	public PaymentConfig getPaymentConfig() {
		return paymentConfig;
	}

	public void setPaymentConfig(PaymentConfig paymentConfig) {
		this.paymentConfig = paymentConfig;
	}
	
	public DeliveryCorp getDeliveryCorp() {
		return deliveryCorp;
	}

	public void setDeliveryCorp(DeliveryCorp deliveryCorp) {
		this.deliveryCorp = deliveryCorp;
	}

	public DeliveryCenter getDeliveryCenter() {
		return deliveryCenter;
	}

	public void setDeliveryCenter(DeliveryCenter deliveryCenter) {
		this.deliveryCenter = deliveryCenter;
	}

	public DeliveryTemplate getDeliveryTemplate() {
		return deliveryTemplate;
	}

	public void setDeliveryTemplate(DeliveryTemplate deliveryTemplate) {
		this.deliveryTemplate = deliveryTemplate;
	}

	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public List<DeliveryItem> getDeliveryItemList() {
		return deliveryItemList;
	}

	public void setDeliveryItemList(List<DeliveryItem> deliveryItemList) {
		this.deliveryItemList = deliveryItemList;
	}

}