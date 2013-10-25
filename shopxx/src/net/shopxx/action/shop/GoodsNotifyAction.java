package net.shopxx.action.shop;

import javax.annotation.Resource;

import net.shopxx.entity.GoodsNotify;
import net.shopxx.entity.Member;
import net.shopxx.entity.Product;
import net.shopxx.service.GoodsNotifyService;
import net.shopxx.service.ProductService;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 前台Action类 - 到货通知
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXD555C0667F7008EC5F71FFFB78A4F6AB
 * ============================================================================
 */

@ParentPackage("shop")
@InterceptorRefs({
	@InterceptorRef(value = "memberVerifyInterceptor", params = {"excludeMethods", "add,save"}),
	@InterceptorRef(value = "shopStack")
})
public class GoodsNotifyAction extends BaseShopAction {

	private static final long serialVersionUID = 3262342523271202673L;
	
	private Product product;// 货品
	private GoodsNotify goodsNotify;// 到货通知
	
	@Resource(name = "productServiceImpl")
	private ProductService productService;
	@Resource(name = "goodsNotifyServiceImpl")
	private GoodsNotifyService goodsNotifyService;

	// 添加
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "product.id", message = "参数错误!")
		}
	)
	@InputConfig(resultName = "error")
	public String add() {
		product = productService.load(product.getId());
		if (!product.getIsOutOfStock()) {
			addActionError("此货品暂不缺货!");
			return ERROR;
		}
		return "add";
	}
	
	// 保存
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "product.id", message = "参数错误!"),
			@RequiredStringValidator(fieldName = "goodsNotify.email", message = "E-mail不允许为空!")
		},
		emails = {
			@EmailValidator(fieldName = "goodsNotify.email", message = "E-mail格式错误!")
		},
		stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "goodsNotify.email", maxLength = "200", message = "E-mail长度超出限制!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		product = productService.load(product.getId());
		if (!product.getIsOutOfStock()) {
			addActionError("此货品暂不缺货!");
			return ERROR;
		}
		goodsNotify.setProduct(product);
		if (getLoginMember() != null) {
			goodsNotify.setMember(getLoginMember());
		}
		goodsNotify.setIsSent(false);
		goodsNotify.setSendDate(null);
		goodsNotifyService.save(goodsNotify);
		redirectUrl = getContextPath() + "/";
		return SUCCESS;
	}
	
	// 列表
	public String list() {
		pager = goodsNotifyService.findPager(getLoginMember(), pager);
		return LIST;
	}
	
	// 删除
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "id", message = "参数错误!")
		}
	)
	@InputConfig(resultName = "ajaxError")
	public String ajaxDelete() {
		GoodsNotify goodsNotify = goodsNotifyService.load(id);
		Member loginMember = getLoginMember();
		if (loginMember != goodsNotify.getMember()) {
			return ajax(Status.error, "参数错误!");
		}
		goodsNotifyService.delete(goodsNotify);
		return ajax(Status.success, "删除成功!");
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public GoodsNotify getGoodsNotify() {
		return goodsNotify;
	}

	public void setGoodsNotify(GoodsNotify goodsNotify) {
		this.goodsNotify = goodsNotify;
	}
	
}