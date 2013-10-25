package net.shopxx.action.shop;

import java.math.BigDecimal;
import java.util.Map;

import javax.annotation.Resource;

import net.shopxx.bean.Setting.StoreFreezeTime;
import net.shopxx.entity.Deposit;
import net.shopxx.entity.Member;
import net.shopxx.entity.Order;
import net.shopxx.entity.OrderItem;
import net.shopxx.entity.OrderLog;
import net.shopxx.entity.Payment;
import net.shopxx.entity.PaymentConfig;
import net.shopxx.entity.Product;
import net.shopxx.entity.Deposit.DepositType;
import net.shopxx.entity.Order.OrderStatus;
import net.shopxx.entity.OrderLog.OrderLogType;
import net.shopxx.entity.Payment.PaymentStatus;
import net.shopxx.entity.Payment.PaymentType;
import net.shopxx.entity.PaymentConfig.PaymentConfigType;
import net.shopxx.payment.BasePaymentProduct;
import net.shopxx.service.CacheService;
import net.shopxx.service.DepositService;
import net.shopxx.service.OrderLogService;
import net.shopxx.service.OrderService;
import net.shopxx.service.PaymentConfigService;
import net.shopxx.service.PaymentService;
import net.shopxx.service.ProductService;
import net.shopxx.util.PaymentProductUtil;
import net.shopxx.util.SettingUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 前台Action类 - 支付处理
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX0D8A50887A9F4C77410AE65149244875
 * ============================================================================
 */

@ParentPackage("shop")
@InterceptorRefs({
	@InterceptorRef(value = "memberVerifyInterceptor", params = {"excludeMethods", "payreturn,paynotify,result"}),
	@InterceptorRef(value = "shopStack")
})
public class PaymentAction extends BaseShopAction {

	private static final long serialVersionUID = -4817743897444468581L;
	
	private BigDecimal rechargeAmount;// 充值金额
	private Payment payment;
	private String paymentUrl;// 支付请求URL
	private String paymentsn;// 支付编号
	private String payreturnMessage;// 支付返回信息
	private String paynotifyMessage;// 支付通知信息
	
	private PaymentType paymentType;// 支付类型
	private PaymentConfig paymentConfig;// 支付方式
	private Order order;// 订单
	private Map<String, String> parameterMap;// 支付参数
	
	@Resource(name = "paymentConfigServiceImpl")
	private PaymentConfigService paymentConfigService;
	@Resource(name = "paymentServiceImpl")
	private PaymentService paymentService;
	@Resource(name = "depositServiceImpl")
	private DepositService depositService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;
	@Resource(name = "orderLogServiceImpl")
	private OrderLogService orderLogService;
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "cacheServiceImpl")
	private CacheService cacheService;
	
	// 支付提交
	public String submit() {
		if ((order == null || StringUtils.isEmpty(order.getId())) && (paymentConfig == null || StringUtils.isEmpty(paymentConfig.getId()))) {
			addActionError("参数错误!");
			return ERROR;
		}
		if (order != null) {
			order = orderService.load(order.getId());
			paymentConfig = order.getPaymentConfig();
		} else {
			paymentConfig = paymentConfigService.load(paymentConfig.getId());
		}
		if (paymentConfig == null) {
			addActionError("支付方式不允许为空!");
			return ERROR;
		}
		PaymentConfigType paymentConfigType = paymentConfig.getPaymentConfigType();
		
		BigDecimal paymentFee = null;// 支付手续费
		BigDecimal amountPayable = null;// 应付金额（含支付手续费）
		if (paymentType == PaymentType.recharge) {
			if (paymentConfigType != PaymentConfigType.online) {
				addActionError("支付方式错误!");
				return ERROR;
			}
			if (rechargeAmount == null) {
				addActionError("请输入充值金额!");
				return ERROR;
			}
			if (rechargeAmount.compareTo(new BigDecimal(0)) <= 0) {
				addActionError("充值金额必须大于0!");
				return ERROR;
			}
			if (rechargeAmount.scale() > getSetting().getPriceScale()) {
				addActionError("充值金额小数位超出限制!");
				return ERROR;
			}
			paymentFee = paymentConfig.getPaymentFee(rechargeAmount);
			amountPayable = rechargeAmount.add(paymentFee);
		} else if (paymentType == PaymentType.deposit) {
			if (paymentConfigType != PaymentConfigType.deposit) {
				addActionError("支付方式错误!");
				return ERROR;
			}
			if (order == null || StringUtils.isEmpty(order.getId())) {
				addActionError("订单信息错误!");
				return ERROR;
			}
			order = orderService.load(order.getId());
			if (order.getOrderStatus() == OrderStatus.completed || order.getOrderStatus() == OrderStatus.invalid) {
				addActionError("订单状态错误!");
				return ERROR;
			}
			if (order.getPaymentStatus() == net.shopxx.entity.Order.PaymentStatus.paid) {
				addActionError("订单付款状态错误!");
				return ERROR;
			}
			if (getLoginMember().getDeposit().compareTo(order.getTotalAmount().subtract(order.getPaidAmount())) < 0) {
				addActionError("您的预存款余额不足,请及时充值!");
				redirectUrl = "deposit!recharge.action";
				return ERROR;
			}
			paymentFee = order.getPaymentFee();
			amountPayable = order.getTotalAmount().subtract(order.getPaidAmount());
		} else if (paymentType == PaymentType.offline) {
			if (paymentConfigType != PaymentConfigType.offline) {
				addActionError("支付方式错误!");
				return ERROR;
			}
			if (order == null || StringUtils.isEmpty(order.getId())) {
				addActionError("订单信息错误!");
				return ERROR;
			}
			order = orderService.load(order.getId());
			if (order.getOrderStatus() == OrderStatus.completed || order.getOrderStatus() == OrderStatus.invalid) {
				addActionError("订单状态错误!");
				return ERROR;
			}
			if (order.getPaymentStatus() == net.shopxx.entity.Order.PaymentStatus.paid) {
				addActionError("订单付款状态错误!");
				return ERROR;
			}
			paymentFee = order.getPaymentFee();
			amountPayable = order.getTotalAmount().subtract(order.getPaidAmount());
		} else if (paymentType == PaymentType.online) {
			if (paymentConfigType != PaymentConfigType.online) {
				addActionError("支付方式错误!");
				return ERROR;
			}
			if (order == null || StringUtils.isEmpty(order.getId())) {
				addActionError("订单信息错误!");
				return ERROR;
			}
			order = orderService.load(order.getId());
			if (order.getOrderStatus() == OrderStatus.completed || order.getOrderStatus() == OrderStatus.invalid) {
				addActionError("订单状态错误!");
				return ERROR;
			}
			if (order.getPaymentStatus() == net.shopxx.entity.Order.PaymentStatus.paid) {
				addActionError("订单付款状态错误!");
				return ERROR;
			}
			paymentFee = order.getPaymentFee();
			amountPayable = order.getTotalAmount().subtract(order.getPaidAmount());
		}
		
		Member loginMember = getLoginMember();
		if (paymentConfigType == PaymentConfigType.deposit) {
			order.setPaymentStatus(net.shopxx.entity.Order.PaymentStatus.paid);
			order.setPaidAmount(order.getPaidAmount().add(amountPayable));
			orderService.update(order);
			
			loginMember.setDeposit(loginMember.getDeposit().subtract(amountPayable));
			memberService.update(loginMember);
			
			Deposit deposit = new Deposit();
			deposit.setDepositType(DepositType.memberPayment);
			deposit.setCredit(new BigDecimal(0));
			deposit.setDebit(amountPayable);
			deposit.setBalance(loginMember.getDeposit());
			deposit.setMember(loginMember);
			depositService.save(deposit);
			
			payment = new Payment();
			payment.setPaymentType(paymentType);
			payment.setPaymentConfigName(paymentConfig.getName());
			payment.setBankName(null);
			payment.setBankAccount(null);
			payment.setTotalAmount(amountPayable);
			payment.setPaymentFee(paymentFee);
			payment.setPayer(getLoginMember().getUsername());
			payment.setOperator(null);
			payment.setMemo(null);
			payment.setPaymentStatus(PaymentStatus.success);
			payment.setMember(loginMember);
			payment.setPaymentConfig(paymentConfig);
			payment.setDeposit(deposit);
			payment.setOrder(order);
			paymentService.save(payment);
			
			// 库存处理
			if (getSetting().getStoreFreezeTime() == StoreFreezeTime.payment) {
				for (OrderItem orderItem : order.getOrderItemSet()) {
					Product product = orderItem.getProduct();
					if (product.getStore() != null) {
						product.setFreezeStore(product.getFreezeStore() + orderItem.getProductQuantity());
						productService.update(product);
						if (product.getIsOutOfStock()) {
							cacheService.flushGoodsListPageCache(getRequest());
						}
					}
				}
			}
			
			// 订单日志
			String logInfo = "支付总金额: " + SettingUtil.currencyFormat(payment.getTotalAmount());
			if (paymentFee.compareTo(new BigDecimal(0)) > 0) {
				logInfo += "(含支付手续费: " + SettingUtil.currencyFormat(paymentFee) + ")";
			}
			OrderLog orderLog = new OrderLog();
			orderLog.setOrderLogType(OrderLogType.payment);
			orderLog.setOrderSn(order.getOrderSn());
			orderLog.setOperator(null);
			orderLog.setInfo(logInfo);
			orderLog.setOrder(order);
			orderLogService.save(orderLog);
			
			return "result";
		} else if (paymentConfigType == PaymentConfigType.offline) {
			payment = new Payment();
			payment.setPaymentType(paymentType);
			payment.setPaymentConfigName(paymentConfig.getName());
			payment.setBankName(null);
			payment.setBankAccount(null);
			payment.setTotalAmount(amountPayable);
			payment.setPaymentFee(paymentFee);
			payment.setPayer(getLoginMember().getUsername());
			payment.setOperator(null);
			payment.setMemo(null);
			payment.setPaymentStatus(PaymentStatus.success);
			payment.setMember(loginMember);
			payment.setPaymentConfig(paymentConfig);
			payment.setDeposit(null);
			payment.setOrder(order);
			
			return "result";
		} else if (paymentConfigType == PaymentConfigType.online) {
			BasePaymentProduct paymentProduct = PaymentProductUtil.getPaymentProduct(paymentConfig.getPaymentProductId());
			paymentUrl = paymentProduct.getPaymentUrl();
			
			String bankName = paymentProduct.getName();
			String bankAccount = paymentConfig.getBargainorId();
			
			Payment payment = new Payment();
			payment.setPaymentType(paymentType);
			payment.setPaymentConfigName(paymentConfig.getName());
			payment.setBankName(bankName);
			payment.setBankAccount(bankAccount);
			payment.setTotalAmount(amountPayable);
			payment.setPaymentFee(paymentFee);
			payment.setPayer(getLoginMember().getUsername());
			payment.setOperator(null);
			payment.setMemo(null);
			payment.setPaymentStatus(PaymentStatus.ready);
			payment.setMember(loginMember);
			payment.setPaymentConfig(paymentConfig);
			payment.setDeposit(null);
			if (paymentType == PaymentType.recharge) {
				payment.setOrder(null);
			} else {
				payment.setOrder(order);
			}
			paymentService.save(payment);
			parameterMap = paymentProduct.getParameterMap(paymentConfig, payment.getPaymentSn(), amountPayable, getRequest());
			return "submit";
		}
		return NONE;
	}
	
	// 支付回调处理
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "paymentsn", message = "支付编号不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String payreturn() {
		payment = paymentService.getPaymentByPaymentSn(paymentsn);
		if (payment == null) {
			addActionError("支付记录不存在!");
			return ERROR;
		}
		BasePaymentProduct paymentProduct = PaymentProductUtil.getPaymentProduct(payment.getPaymentConfig().getPaymentProductId());
		if (paymentProduct == null) {
			addActionError("支付产品不存在!");
			return ERROR;
		}
		
		BigDecimal totalAmount = paymentProduct.getPaymentAmount(getRequest());
		boolean isSuccess = paymentProduct.isPaySuccess(getRequest());
		payreturnMessage = paymentProduct.getPayreturnMessage(payment.getPaymentSn());
		
		if (!paymentProduct.verifySign(payment.getPaymentConfig(), getRequest())) {
			addActionError("支付签名错误!");
			return ERROR;
		}
		if (!isSuccess) {
			addActionError("支付失败!");
			return ERROR;
		}
		
		if (payment.getPaymentStatus() == PaymentStatus.success) {
			return "result";
		}
		if (payment.getPaymentStatus() != PaymentStatus.ready) {
			addActionError("交易状态错误!");
			return ERROR;
		}
		if (totalAmount.compareTo(payment.getTotalAmount()) != 0) {
			addActionError("交易金额错误!");
			return ERROR;
		}
		
		payment.setPaymentStatus(PaymentStatus.success);
		paymentService.update(payment);
		
		BigDecimal paymentFee = payment.getPaymentFee();
		if (payment.getPaymentType() == PaymentType.recharge) {
			Member member = payment.getMember();
			member.setDeposit(member.getDeposit().add(totalAmount.subtract(paymentFee)));
			memberService.update(member);
			
			Deposit deposit = new Deposit();
			deposit.setDepositType(DepositType.memberRecharge);
			deposit.setCredit(totalAmount.subtract(payment.getPaymentFee()));
			deposit.setDebit(new BigDecimal(0));
			deposit.setBalance(member.getDeposit());
			deposit.setMember(member);
			deposit.setPayment(payment);
			depositService.save(deposit);
		} else if (payment.getPaymentType() == PaymentType.online) {
			order = payment.getOrder();
			order.setPaymentStatus(net.shopxx.entity.Order.PaymentStatus.paid);
			order.setPaidAmount(order.getPaidAmount().add(totalAmount));
			orderService.update(order);
			
			// 库存处理
			if (getSetting().getStoreFreezeTime() == StoreFreezeTime.payment) {
				for (OrderItem orderItem : order.getOrderItemSet()) {
					Product product = orderItem.getProduct();
					if (product.getStore() != null) {
						product.setFreezeStore(product.getFreezeStore() + orderItem.getProductQuantity());
						productService.update(product);
						if (product.getIsOutOfStock()) {
							cacheService.flushGoodsListPageCache(getRequest());
						}
					}
				}
			}
			
			// 订单日志
			String logInfo = "支付总金额: " + SettingUtil.currencyFormat(payment.getTotalAmount());
			if (paymentFee.compareTo(new BigDecimal(0)) > 0) {
				logInfo += "(含支付手续费: " + SettingUtil.currencyFormat(paymentFee) + ")";
			}
			
			OrderLog orderLog = new OrderLog();
			orderLog.setOrderLogType(OrderLogType.payment);
			orderLog.setOrderSn(order.getOrderSn());
			orderLog.setOperator(null);
			orderLog.setInfo(logInfo);
			orderLog.setOrder(order);
			orderLogService.save(orderLog);
		}
		return "payreturn";
	}
	
	// 支付结果
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "paymentsn", message = "支付编号不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String result() {
		payment = paymentService.getPaymentByPaymentSn(paymentsn);
		if (payment == null || payment.getPaymentStatus() != PaymentStatus.success) {
			addActionError("参数错误!");
			return ERROR;
		}
		return "result";
	}
	
	// 支付通知处理
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "paymentsn", message = "支付编号不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String paynotify() {
		payment = paymentService.getPaymentByPaymentSn(paymentsn);
		if (payment == null) {
			addActionError("支付记录不存在!");
			return ERROR;
		}
		BasePaymentProduct paymentProduct = PaymentProductUtil.getPaymentProduct(payment.getPaymentConfig().getPaymentProductId());
		if (paymentProduct == null) {
			addActionError("支付产品不存在!");
			return ERROR;
		}
		
		BigDecimal totalAmount = paymentProduct.getPaymentAmount(getRequest());
		boolean isSuccess = paymentProduct.isPaySuccess(getRequest());
		payreturnMessage = paymentProduct.getPayreturnMessage(payment.getPaymentSn());
		
		if (!paymentProduct.verifySign(payment.getPaymentConfig(), getRequest())) {
			addActionError("支付签名错误!");
			return ERROR;
		}
		if (!isSuccess) {
			addActionError("支付失败!");
			return ERROR;
		}
		if (payment.getPaymentStatus() == PaymentStatus.success) {
			return "result";
		}
		if (payment.getPaymentStatus() != PaymentStatus.ready) {
			addActionError("交易状态错误!");
			return ERROR;
		}
		if (totalAmount.compareTo(payment.getTotalAmount()) != 0) {
			addActionError("交易金额错误!");
			return ERROR;
		}

		BigDecimal paymentFee = payment.getPaymentFee();
		if (payment.getPaymentType() == PaymentType.recharge) {
			Member member = payment.getMember();
			member.setDeposit(member.getDeposit().add(totalAmount.subtract(paymentFee)));
			memberService.update(member);
			
			Deposit deposit = new Deposit();
			deposit.setDepositType(DepositType.memberRecharge);
			deposit.setCredit(totalAmount.subtract(payment.getPaymentFee()));
			deposit.setDebit(new BigDecimal(0));
			deposit.setBalance(member.getDeposit());
			deposit.setMember(member);
			deposit.setPayment(payment);
			depositService.save(deposit);
		} else if (payment.getPaymentType() == PaymentType.online) {
			order = payment.getOrder();
			order.setPaymentStatus(net.shopxx.entity.Order.PaymentStatus.paid);
			order.setPaidAmount(order.getPaidAmount().add(totalAmount));
			orderService.update(order);
			
			// 库存处理
			if (getSetting().getStoreFreezeTime() == StoreFreezeTime.payment) {
				for (OrderItem orderItem : order.getOrderItemSet()) {
					Product product = orderItem.getProduct();
					if (product.getStore() != null) {
						product.setFreezeStore(product.getFreezeStore() + orderItem.getProductQuantity());
						productService.update(product);
						if (product.getIsOutOfStock()) {
							cacheService.flushGoodsListPageCache(getRequest());
						}
					}
				}
			}
			
			// 订单日志
			String logInfo = "支付总金额: " + payment.getTotalAmount();
			if (paymentFee.compareTo(new BigDecimal(0)) > 0) {
				logInfo += "(含支付手续费: " + SettingUtil.currencyFormat(paymentFee) + ")";
			}
			OrderLog orderLog = new OrderLog();
			orderLog.setOrderLogType(OrderLogType.payment);
			orderLog.setOrderSn(order.getOrderSn());
			orderLog.setOperator(null);
			orderLog.setInfo(logInfo);
			orderLog.setOrder(order);
			orderLogService.save(orderLog);
		}
		payment.setPaymentStatus(PaymentStatus.success);
		paymentService.update(payment);
		return "paynotify";
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public PaymentConfig getPaymentConfig() {
		return paymentConfig;
	}

	public void setPaymentConfig(PaymentConfig paymentConfig) {
		this.paymentConfig = paymentConfig;
	}

	public BigDecimal getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(BigDecimal rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
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
	
	public String getPaymentUrl() {
		return paymentUrl;
	}

	public void setPaymentUrl(String paymentUrl) {
		this.paymentUrl = paymentUrl;
	}

	public Map<String, String> getParameterMap() {
		return parameterMap;
	}

	public void setParameterMap(Map<String, String> parameterMap) {
		this.parameterMap = parameterMap;
	}
	
	public String getPaymentsn() {
		return paymentsn;
	}

	public void setPaymentsn(String paymentsn) {
		this.paymentsn = paymentsn;
	}

	public String getPayreturnMessage() {
		return payreturnMessage;
	}

	public void setPayreturnMessage(String payreturnMessage) {
		this.payreturnMessage = payreturnMessage;
	}

	public String getPaynotifyMessage() {
		return paynotifyMessage;
	}

	public void setPaynotifyMessage(String paynotifyMessage) {
		this.paynotifyMessage = paynotifyMessage;
	}

}