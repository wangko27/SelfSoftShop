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
 * 财付通（担保交易）
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

public class TenpayPartner extends BasePaymentProduct {
	
	public static final String PAYMENT_URL = "https://www.tenpay.com/cgi-bin/med/show_opentrans.cgi";// 支付请求URL
	public static final String RETURN_URL = "/shop/payment!payreturn.action";// 回调处理URL
	
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
		String cftTid = httpServletRequest.getParameter("cft_tid");
		if (StringUtils.isEmpty(cftTid)) {
			return null;
		}
		return cftTid;
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
		return new BigDecimal(totalFee).divide(new BigDecimal(100));
	}
	
	public boolean isPaySuccess(HttpServletRequest httpServletRequest) {
		if (httpServletRequest == null) {
			return false;
		}
		String status = httpServletRequest.getParameter("status");
		if (StringUtils.equals(status, "3")) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Map<String, String> getParameterMap(PaymentConfig paymentConfig, String paymentSn, BigDecimal paymentAmount, HttpServletRequest httpServletRequest) {
		String totalAmountString = paymentAmount.multiply(new BigDecimal(100)).setScale(0).toString();
		
		String attach = "sh" + "op" + "xx";// 商户数据
		String chnid = paymentConfig.getBargainorId();// 商户号
		String cmdno = "12";// 业务代码（12：担保交易支付）
		String encode_type = "2";// 字符集编码格式（1：GB2312、2：UTF-8）
		String mch_desc = "";// 订单描述
		String mch_name = paymentSn;// 商品名称
		String mch_price = totalAmountString;// 总金额（单位：分）
		String mch_returl = SettingUtil.getSetting().getShopUrl() + RETURN_URL + "?paymentsn=" + paymentSn;// 回调处理URL
		String mch_type = "1";// 交易类型（1、实物交易、2、虚拟交易）
		String mch_vno = paymentSn;// 交易号
		String need_buyerinfo = "2";// 是否需要填写物流信息（1：需要、2：不需要）
		String seller = paymentConfig.getBargainorId();// 商户号
		String show_url = SettingUtil.getSetting().getShopUrl() + RETURN_URL + "?paymentsn=" + paymentSn;// 商品显示URL
		String transport_desc = "";// 物流方式说明
		String transport_fee = "0";// 物流费用（单位：分）
		String version = "2";// 版本号
		String key = paymentConfig.getBargainorKey();// 密钥
		
		// 生成签名
		Map<String, String> signMap = new LinkedHashMap<String, String>();
		signMap.put("attach", attach);
		signMap.put("chnid", chnid);
		signMap.put("cmdno", cmdno);
		signMap.put("encode_type", encode_type);
		signMap.put("mch_desc", mch_desc);
		signMap.put("mch_name", mch_name);
		signMap.put("mch_price", mch_price);
		signMap.put("mch_returl", mch_returl);
		signMap.put("mch_type", mch_type);
		signMap.put("mch_vno", mch_vno);
		signMap.put("need_buyerinfo", need_buyerinfo);
		signMap.put("seller", seller);
		signMap.put("show_url", show_url);
		signMap.put("transport_desc", transport_desc);
		signMap.put("transport_fee", transport_fee);
		signMap.put("version", version);
		signMap.put("key", key);
		String sign = DigestUtils.md5Hex(getParameterString(signMap)).toUpperCase();
		
		// 参数处理
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("attach", attach);
		parameterMap.put("chnid", chnid);
		parameterMap.put("cmdno", cmdno);
		parameterMap.put("encode_type", encode_type);
		parameterMap.put("mch_desc", mch_desc);
		parameterMap.put("mch_name", mch_name);
		parameterMap.put("mch_price", mch_price);
		parameterMap.put("mch_returl", mch_returl);
		parameterMap.put("mch_type", mch_type);
		parameterMap.put("mch_vno", mch_vno);
		parameterMap.put("need_buyerinfo", need_buyerinfo);
		parameterMap.put("seller", seller);
		parameterMap.put("show_url", show_url);
		parameterMap.put("transport_desc", transport_desc);
		parameterMap.put("transport_fee", transport_fee);
		parameterMap.put("version", version);
		parameterMap.put("sign", sign);
		return parameterMap;
	}

	@Override
	public boolean verifySign(PaymentConfig paymentConfig, HttpServletRequest httpServletRequest) {
		// 获取参数
		String attach = httpServletRequest.getParameter("attach");
		String buyer_id = httpServletRequest.getParameter("buyer_id");
		String cft_tid = httpServletRequest.getParameter("cft_tid");
		String chnid = httpServletRequest.getParameter("chnid");
		String cmdno = httpServletRequest.getParameter("cmdno");
		String mch_vno = httpServletRequest.getParameter("mch_vno");
		String retcode = httpServletRequest.getParameter("retcode");
		String seller = httpServletRequest.getParameter("seller");
		String status = httpServletRequest.getParameter("status");
		String total_fee = httpServletRequest.getParameter("total_fee");
		String trade_price = httpServletRequest.getParameter("trade_price");
		String transport_fee = httpServletRequest.getParameter("transport_fee");
		String version = httpServletRequest.getParameter("version");
		String sign = httpServletRequest.getParameter("sign");
		
		// 验证支付签名
		Map<String, String> parameterMap = new LinkedHashMap<String, String>();
		parameterMap.put("attach", attach);
		parameterMap.put("buyer_id", buyer_id);
		parameterMap.put("cft_tid", cft_tid);
		parameterMap.put("chnid", chnid);
		parameterMap.put("cmdno", cmdno);
		parameterMap.put("mch_vno", mch_vno);
		parameterMap.put("retcode", retcode);
		parameterMap.put("seller", seller);
		parameterMap.put("status", status);
		parameterMap.put("total_fee", total_fee);
		parameterMap.put("trade_price", trade_price);
		parameterMap.put("transport_fee", transport_fee);
		parameterMap.put("version", version);
		parameterMap.put("key", paymentConfig.getBargainorKey());
		if (StringUtils.equals(sign, DigestUtils.md5Hex(getParameterString(parameterMap)).toUpperCase())) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String getPayreturnMessage(String paymentSn) {
		return "<html><head><meta name=\"TENCENT_ONLINE_PAYMENT\" content=\"China TENCENT\"><meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" /><title>页面跳转中..</title></head><body onload=\"javascript: document.forms[0].submit();\"><form action=\"" + SettingUtil.getSetting().getShopUrl() + RESULT_URL + "\"><input type=\"hidden\" name=\"paymentsn\" value=\"" + paymentSn + "\" /></form></body></html>";
	}
	
	@Override
	public String getPaynotifyMessage() {
		return null;
	}

}