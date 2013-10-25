package net.shopxx.payment;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.shopxx.bean.Setting.CurrencyType;
import net.shopxx.entity.PaymentConfig;
import net.shopxx.util.SettingUtil;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 支付宝（担保交易）
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX4AA72A7C04D99AF651FC12C9BE8A88BE
 * ============================================================================
 */

public class AlipayPartner extends BasePaymentProduct {
	
	public static final String PAYMENT_URL = "https://www.alipay.com/cooperate/gateway.do?_input_charset=UTF-8";// 支付请求URL
	public static final String RETURN_URL = "/shop/payment!payreturn.action";// 回调处理URL
	public static final String NOTIFY_URL = "/shop/payment!paynotify.action";// 消息通知URL
	public static final String SHOW_URL = "/";// 商品显示URL
	
	// 支持货币种类
	public static final CurrencyType[] currencyType = {CurrencyType.CNY};
	
	@Override
	public String getPaymentUrl() {
		return PAYMENT_URL;
	}
	
	@Override
	public String getPaymentSn(HttpServletRequest httpServletRequest) {
		if (httpServletRequest == null) {
			return null;
		}
		String outTradeNo = httpServletRequest.getParameter("out_trade_no");
		if (StringUtils.isEmpty(outTradeNo)) {
			return null;
		}
		return outTradeNo;
	}
	
	@Override
	public BigDecimal getPaymentAmount(HttpServletRequest httpServletRequest) {
		if (httpServletRequest == null) {
			return null;
		}
		String totalFee = httpServletRequest.getParameter("total_fee");
		if (StringUtils.isEmpty(totalFee)) {
			return null;
		}
		return new BigDecimal(totalFee);
	}
	
	public boolean isPaySuccess(HttpServletRequest httpServletRequest) {
		if (httpServletRequest == null) {
			return false;
		}
		String tradeStatus = httpServletRequest.getParameter("trade_status");
		if (StringUtils.equals(tradeStatus, "WAIT_SELLER_SEND_GOODS")) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public Map<String, String> getParameterMap(PaymentConfig paymentConfig, String paymentSn, BigDecimal paymentAmount, HttpServletRequest httpServletRequest) {
		String _input_charset = "UTF-8";// 字符集编码格式（UTF-8、GBK）
		String body = paymentSn;// 订单描述
		String logistics_fee = "0";// 物流费用
		String logistics_payment = "SELLER_PAY";// 物流支付方式（SELLER_PAY：卖家承担运费、BUYER_PAY：买家承担运费）
		String logistics_type = "EXPRESS";// 物流类型（EXPRESS：快递、POST：平邮、EMS：EMS）
		String notify_url = SettingUtil.getSetting().getShopUrl() + NOTIFY_URL + "?paymentsn=" + paymentSn;// 消息通知URL
		String out_trade_no = paymentSn;// 支付编号
		String partner = paymentConfig.getBargainorId();// 合作身份者ID
		String payment_type = "1";// 支付类型（固定值：1）
		String price = paymentAmount.toString();// 总金额（单位：元）
		String quantity = "1";// 商品数量
		String return_url = SettingUtil.getSetting().getShopUrl() + RETURN_URL + "?paymentsn=" + paymentSn;// 回调处理URL
		String seller_id = paymentConfig.getBargainorId();// 商家ID
		String service = "create_partner_trade_by_buyer";// 接口类型（create_partner_trade_by_buyer：担保交易）
		String show_url = SettingUtil.getSetting().getShopUrl() + SHOW_URL;// 支付结果显示URL
		String sign_type = "MD5";//签名加密方式（MD5）
		String subject = paymentSn;// 订单的名称、标题、关键字等
		String key = paymentConfig.getBargainorKey();// 密钥
		
		// 生成签名
		Map<String, String> signMap = new LinkedHashMap<String, String>();
		signMap.put("_input_charset", _input_charset);
		signMap.put("body", body);
		signMap.put("logistics_fee", logistics_fee);
		signMap.put("logistics_payment", logistics_payment);
		signMap.put("logistics_type", logistics_type);
		signMap.put("notify_url", notify_url);
		signMap.put("out_trade_no", paymentSn);
		signMap.put("partner", partner);
		signMap.put("payment_type", payment_type);
		signMap.put("price", price);
		signMap.put("quantity", quantity);
		signMap.put("return_url", return_url);
		signMap.put("seller_id", seller_id);
		signMap.put("service", service);
		signMap.put("show_url", show_url);
		signMap.put("subject", subject);
		String sign = DigestUtils.md5Hex(getParameterString(signMap) + key);
		
		// 参数处理
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("body", body);
		parameterMap.put("logistics_fee", logistics_fee);
		parameterMap.put("logistics_payment", logistics_payment);
		parameterMap.put("logistics_type", logistics_type);
		parameterMap.put("notify_url", notify_url);
		parameterMap.put("out_trade_no", out_trade_no);
		parameterMap.put("partner", partner);
		parameterMap.put("payment_type", payment_type);
		parameterMap.put("price", price);
		parameterMap.put("quantity", quantity);
		parameterMap.put("return_url", return_url);
		parameterMap.put("seller_id", seller_id);
		parameterMap.put("service", service);
		parameterMap.put("show_url", show_url);
		parameterMap.put("subject", subject);
		parameterMap.put("sign_type", sign_type);
		parameterMap.put("sign", sign);
		return parameterMap;
	}

	@Override
	public boolean verifySign(PaymentConfig paymentConfig, HttpServletRequest httpServletRequest) {
		// 获取参数
		String body = httpServletRequest.getParameter("body");
		String buyer_email = httpServletRequest.getParameter("buyer_email");
		String buyer_id = httpServletRequest.getParameter("buyer_id");
		String coupon_discount = httpServletRequest.getParameter("coupon_discount");
		String discount = httpServletRequest.getParameter("discount");
		String gmt_close = httpServletRequest.getParameter("gmt_close");
		String gmt_create = httpServletRequest.getParameter("gmt_create");
		String gmt_logistics_modify = httpServletRequest.getParameter("gmt_logistics_modify");
		String gmt_payment = httpServletRequest.getParameter("gmt_payment");
		String gmt_refund = httpServletRequest.getParameter("gmt_refund");
		String gmt_send_goods = httpServletRequest.getParameter("gmt_send_goods");
		String is_success = httpServletRequest.getParameter("is_success");
		String is_total_fee_adjust = httpServletRequest.getParameter("is_total_fee_adjust");
		String logistics_fee = httpServletRequest.getParameter("logistics_fee");
		String logistics_payment = httpServletRequest.getParameter("logistics_payment");
		String logistics_type = httpServletRequest.getParameter("logistics_type");
		String notify_id = httpServletRequest.getParameter("notify_id");
		String notify_time = httpServletRequest.getParameter("notify_time");
		String notify_type = httpServletRequest.getParameter("notify_type");
		String out_trade_no = httpServletRequest.getParameter("out_trade_no");
		String payment_type = httpServletRequest.getParameter("payment_type");
		String price = httpServletRequest.getParameter("price");
		String quantity = httpServletRequest.getParameter("quantity");
		String receive_address = httpServletRequest.getParameter("receive_address");
		String receive_mobile = httpServletRequest.getParameter("receive_mobile");
		String receive_name = httpServletRequest.getParameter("receive_name");
		String receive_zip = httpServletRequest.getParameter("receive_zip");
		String refund_status = httpServletRequest.getParameter("refund_status");
		String seller_actions = httpServletRequest.getParameter("seller_actions");
		String seller_email = httpServletRequest.getParameter("seller_email");
		String seller_id = httpServletRequest.getParameter("seller_id");
		String subject = httpServletRequest.getParameter("subject");
		String total_fee = httpServletRequest.getParameter("total_fee");
		String trade_no = httpServletRequest.getParameter("trade_no");
		String trade_status = httpServletRequest.getParameter("trade_status");
		String use_coupon = httpServletRequest.getParameter("use_coupon");
		String sign = httpServletRequest.getParameter("sign");
		
		// 验证签名
		Map<String, String> parameterMap = new LinkedHashMap<String, String>();
		parameterMap.put("body", body);
		parameterMap.put("buyer_email", buyer_email);
		parameterMap.put("buyer_id", buyer_id);
		parameterMap.put("coupon_discount", coupon_discount);
		parameterMap.put("discount", discount);
		parameterMap.put("gmt_close", gmt_close);
		parameterMap.put("gmt_create", gmt_create);
		parameterMap.put("gmt_logistics_modify", gmt_logistics_modify);
		parameterMap.put("gmt_payment", gmt_payment);
		parameterMap.put("gmt_refund", gmt_refund);
		parameterMap.put("gmt_send_goods", gmt_send_goods);
		parameterMap.put("is_success", is_success);
		parameterMap.put("is_total_fee_adjust", is_total_fee_adjust);
		parameterMap.put("logistics_fee", logistics_fee);
		parameterMap.put("logistics_payment", logistics_payment);
		parameterMap.put("logistics_type", logistics_type);
		parameterMap.put("notify_id", notify_id);
		parameterMap.put("notify_time", notify_time);
		parameterMap.put("notify_type", notify_type);
		parameterMap.put("out_trade_no", out_trade_no);
		parameterMap.put("payment_type", payment_type);
		parameterMap.put("price", price);
		parameterMap.put("quantity", quantity);
		parameterMap.put("receive_address", receive_address);
		parameterMap.put("receive_mobile", receive_mobile);
		parameterMap.put("receive_name", receive_name);
		parameterMap.put("receive_zip", receive_zip);
		parameterMap.put("refund_status", refund_status);
		parameterMap.put("seller_actions", seller_actions);
		parameterMap.put("seller_email", seller_email);
		parameterMap.put("seller_id", seller_id);
		parameterMap.put("subject", subject);
		parameterMap.put("total_fee", total_fee);
		parameterMap.put("trade_no", trade_no);
		parameterMap.put("trade_status", trade_status);
		parameterMap.put("use_coupon", use_coupon);
		
		if (StringUtils.equals(sign, DigestUtils.md5Hex(getParameterString(parameterMap) + paymentConfig.getBargainorKey()))) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String getPayreturnMessage(String paymentSn) {
		return "<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" /><title>页面跳转中..</title></head><body onload=\"javascript: document.forms[0].submit();\"><form action=\"" + SettingUtil.getSetting().getShopUrl() + RESULT_URL + "\"><input type=\"hidden\" name=\"paymentsn\" value=\"" + paymentSn + "\" /></form></body></html>";
	}
	
	@Override
	public String getPaynotifyMessage() {
		return "success";
	}

}