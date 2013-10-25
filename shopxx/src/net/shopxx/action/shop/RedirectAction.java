package net.shopxx.action.shop;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

/**
 * 前台Action类 - 站内URL跳转
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX392AA3637D2315A30E492D893AD58FA1
 * ============================================================================
 */

@ParentPackage("shop")
@Results({
	@Result(name = "index", location = "/", type = "redirect"),
	@Result(name = "memberCenter", location = "/shop/member_center!index.action", type = "redirect"),
	@Result(name = "urlRedirect", location = "${redirectUrl}", type = "redirect"),
})
public class RedirectAction extends BaseShopAction {

	private static final long serialVersionUID = 5832516589562009249L;
	
	private static final String INDEX_REDIRECT_URL_PARAM_VALUE = "index";// 首页跳转参数值
	private static final String MEMBER_CENTER_REDIRECT_URL_PARAM_VALUE = "memberCenter";// 会员中心跳转参数值
	
	private String redirectUrl;
	
	@Override
	public String execute() throws Exception {
		if (StringUtils.isEmpty(redirectUrl) || redirectUrl.equals(INDEX_REDIRECT_URL_PARAM_VALUE)) {
			return INDEX_REDIRECT_URL_PARAM_VALUE;
		} else if (redirectUrl.equals(MEMBER_CENTER_REDIRECT_URL_PARAM_VALUE)) {
			return MEMBER_CENTER_REDIRECT_URL_PARAM_VALUE;
		} else if (redirectUrl.startsWith("/")) {
			return "urlRedirect";
		} else {
			return INDEX_REDIRECT_URL_PARAM_VALUE;
		}
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

}