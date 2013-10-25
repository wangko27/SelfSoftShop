package net.shopxx.bean;

import net.shopxx.util.CommonUtil;

/**
 * Bean类 - 页面模板配置
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX123A1ED9AE54F480CBB633134E7AA677
 * ============================================================================
 */

public class PageTemplateConfig {
	
	public static final String INDEX = "index";// 首页
	public static final String LOGIN = "login";// 登录
	public static final String REGISTER_AGREEMENT = "registerAgreement";// 注册协议
	public static final String ADMIN_JS = "adminJs";// ADMIN.JS
	public static final String SHOP_JS = "shopJs";// SHOP.JS
	public static final String ARTICLE_CONTENT = "articleContent";// 文章内容
	public static final String GOODS_CONTENT = "goodsContent";// 商品内容
	public static final String ERROR_PAGE = "errorPage";// 错误页
	public static final String ERROR_PAGE_ACCESS_DENIED = "errorPageAccessDenied";// 权限错误页
	public static final String ERROR_PAGE_500 = "errorPage500";// 错误页500
	public static final String ERROR_PAGE_404 = "errorPage404";// 错误页404
	public static final String ERROR_PAGE_403 = "errorPage403";// 错误页403
	
	private String name;// 配置名称
	private String description;// 描述
	private String templatePath;// Freemarker模板文件路径
	private String htmlPath;// 生成HTML静态文件路径

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
	
	public String getHtmlPath() {
		return htmlPath;
	}

	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}

	// 获取生成HTML静态文件真实路径
	public String getHtmlRealPath() {
		return CommonUtil.getPreparedStatementPath(htmlPath);
	}

}