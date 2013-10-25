package net.shopxx.bean;


/**
 * Bean类 - 打印模板配置
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXB7808BFD2543A7A80440B320DC4A3B9B
 * ============================================================================
 */

public class PrintTemplateConfig {
	
	public static final String ORDER = "order";// 订单
	public static final String GOODS = "goods";// 购物单
	public static final String SHIPPING = "shipping";// 配送单
	
	private String name;// 配置名称
	private String description;// 描述
	private String templatePath;// Freemarker模板文件路径

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

}