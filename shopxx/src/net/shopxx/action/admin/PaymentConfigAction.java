package net.shopxx.action.admin;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import net.shopxx.entity.PaymentConfig;
import net.shopxx.entity.PaymentConfig.PaymentConfigType;
import net.shopxx.entity.PaymentConfig.PaymentFeeType;
import net.shopxx.payment.BasePaymentProduct;
import net.shopxx.service.PaymentConfigService;
import net.shopxx.util.PaymentProductUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 支付方式
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX026BAB39F30F9C838DE0050825B212E1
 * ============================================================================
 */

@ParentPackage("admin")
public class PaymentConfigAction extends BaseAdminAction {

	private static final long serialVersionUID = 3562311377613294892L;
	
	private PaymentConfig paymentConfig;
	private BasePaymentProduct paymentProduct;
	
	@Resource(name = "paymentConfigServiceImpl")
	private PaymentConfigService paymentConfigService;
	
	// 列表
	public String list() {
		pager = paymentConfigService.findPager(pager);
		return LIST;
	}
	
	// 删除
	public String delete() {
		long totalCount = paymentConfigService.getTotalCount();
		if (ids.length >= totalCount) {
			return ajax(Status.error, "删除失败,必须保留至少一个支付方式!");
		}
		paymentConfigService.delete(ids);
		return ajax(Status.success, "删除成功!");
	}
	
	// 选择支付方式
	public String select() {
		return "select";
	}
	
	// 添加
	@Validations(
		requiredFields = {
			@RequiredFieldValidator(fieldName = "paymentConfig.paymentConfigType", message = "支付配置类型不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String add() {
		if (paymentConfig.getPaymentConfigType() == PaymentConfigType.online) {
			paymentProduct = PaymentProductUtil.getPaymentProduct(paymentConfig.getPaymentProductId());
			if (paymentProduct == null) {
				addActionError("支付产品配置不存在!");
				return ERROR;
			}
		}
		return INPUT;
	}
	
	// 编辑
	public String edit() {
		paymentConfig = paymentConfigService.load(id);
		if (paymentConfig.getPaymentConfigType() == PaymentConfigType.online) {
			paymentProduct = PaymentProductUtil.getPaymentProduct(paymentConfig.getPaymentProductId());
			if (paymentProduct == null) {
				addActionError("支付产品配置不存在!");
				return ERROR;
			}
		}
		return INPUT;
	}
	
	// 保存
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "paymentConfig.name", message = "支付方式名称不允许为空!")
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "paymentConfig.paymentConfigType", message = "支付方式类型不允许为空!"),
			@RequiredFieldValidator(fieldName = "paymentConfig.paymentFeeType", message = "支付手续费设置不允许为空!"),
			@RequiredFieldValidator(fieldName = "paymentConfig.paymentFee", message = "支付手续费不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "paymentConfig.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		if (paymentConfig.getPaymentFee().compareTo(new BigDecimal(0)) < 0) {
			addActionError("支付手续费金额不允许小于0!");
			return ERROR;
		}
		if (paymentConfig.getPaymentConfigType() == PaymentConfigType.online) {
			paymentProduct = PaymentProductUtil.getPaymentProduct(paymentConfig.getPaymentProductId());
			if (paymentProduct == null) {
				addActionError("支付产品配置不存在!");
				return ERROR;
			}
			if (StringUtils.isEmpty(paymentConfig.getBargainorId())) {
				addActionError(paymentProduct.getBargainorIdName() + "不允许为空!");
				return ERROR;
			}
			if (StringUtils.isEmpty(paymentConfig.getBargainorKey())) {
				addActionError(paymentProduct.getBargainorKeyName() + "不允许为空!");
				return ERROR;
			}
		} else {
			paymentConfig.setPaymentProductId(null);
			paymentConfig.setBargainorId(null);
			paymentConfig.setBargainorKey(null);
		}
		paymentConfigService.save(paymentConfig);
		redirectUrl = "payment_config!list.action";
		return SUCCESS;
	}
	
	// 更新
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "paymentConfig.name", message = "支付方式名称不允许为空!")
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "paymentConfig.paymentConfigType", message = "支付方式类型不允许为空!"),
			@RequiredFieldValidator(fieldName = "paymentConfig.paymentFeeType", message = "支付手续费设置不允许为空!"),
			@RequiredFieldValidator(fieldName = "paymentConfig.paymentFee", message = "支付手续费不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "paymentConfig.orderList", min = "0", message = "排序必须为零或正整数!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		if (paymentConfig.getPaymentFee().compareTo(new BigDecimal(0)) < 0) {
			addActionError("支付手续费金额不允许小于0!");
			return ERROR;
		}
		PaymentConfig persistent = paymentConfigService.load(id);
		if (persistent.getPaymentConfigType() == PaymentConfigType.online) {
			paymentProduct = PaymentProductUtil.getPaymentProduct(paymentConfig.getPaymentProductId());
			if (paymentProduct == null) {
				addActionError("支付产品配置不存在!");
				return ERROR;
			}
			if (StringUtils.isEmpty(paymentConfig.getBargainorId())) {
				addActionError(paymentProduct.getBargainorIdName() + "不允许为空!");
				return ERROR;
			}
			if (StringUtils.isEmpty(paymentConfig.getBargainorKey())) {
				addActionError(paymentProduct.getBargainorKeyName() + "不允许为空!");
				return ERROR;
			}
		} else {
			paymentConfig.setPaymentProductId(null);
			paymentConfig.setBargainorId(null);
			paymentConfig.setBargainorKey(null);
		}
		BeanUtils.copyProperties(paymentConfig, persistent, new String[] {"id", "createDate", "modifyDate", "paymentConfigType", "paymentProductId"});
		paymentConfigService.update(persistent);
		redirectUrl = "payment_config!list.action";
		return SUCCESS;
	}
	
	// 获取支付手续费类型集合
	public List<PaymentFeeType> getPaymentFeeTypeList() {
		return Arrays.asList(PaymentFeeType.values());
	}
	
	// 获取所有支付产品集合
	public List<BasePaymentProduct> getPaymentProductList() {
		return PaymentProductUtil.getPaymentProductList();
	}

	public PaymentConfig getPaymentConfig() {
		return paymentConfig;
	}

	public void setPaymentConfig(PaymentConfig paymentConfig) {
		this.paymentConfig = paymentConfig;
	}

	public BasePaymentProduct getPaymentProduct() {
		return paymentProduct;
	}

	public void setPaymentProduct(BasePaymentProduct paymentProduct) {
		this.paymentProduct = paymentProduct;
	}

}