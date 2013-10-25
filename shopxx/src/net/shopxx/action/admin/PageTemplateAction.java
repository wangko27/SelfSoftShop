package net.shopxx.action.admin;

import java.util.List;

import javax.annotation.Resource;

import net.shopxx.bean.PageTemplateConfig;
import net.shopxx.util.TemplateConfigUtil;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.views.freemarker.FreemarkerManager;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

import freemarker.template.TemplateException;

/**
 * 后台Action类 - 页面模板
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX24688E4E855C92B9AB5435A7725A65EA
 * ============================================================================
 */

@ParentPackage("admin")
public class PageTemplateAction extends BaseAdminAction {

	private static final long serialVersionUID = -8595064703705228101L;
	
	private PageTemplateConfig pageTemplateConfig;
	private String templateFileContent;
	
	@Resource(name = "freemarkerManager")
	private FreemarkerManager freemarkerManager;

	// 列表
	public String list() {
		return LIST;
	}

	// 编辑
	public String edit() {
		pageTemplateConfig = TemplateConfigUtil.getPageTemplateConfig(pageTemplateConfig.getName());
		templateFileContent = TemplateConfigUtil.readTemplateFileContent(pageTemplateConfig);
		return INPUT;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "templateFileContent", message = "模板内容不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		pageTemplateConfig = TemplateConfigUtil.getPageTemplateConfig(pageTemplateConfig.getName());
		TemplateConfigUtil.writeTemplateFileContent(pageTemplateConfig, templateFileContent);
		try {
			freemarkerManager.getConfiguration(getServletContext()).clearTemplateCache();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		redirectUrl = "page_template!list.action";
		return SUCCESS;
	}

	// 获取生成静态模板配置集合
	public List<PageTemplateConfig> getAllPageTemplateConfigList() {
		return TemplateConfigUtil.getAllPageTemplateConfigList();
	}

	public PageTemplateConfig getPageTemplateConfig() {
		return pageTemplateConfig;
	}

	public void setPageTemplateConfig(PageTemplateConfig pageTemplateConfig) {
		this.pageTemplateConfig = pageTemplateConfig;
	}

	public String getTemplateFileContent() {
		return templateFileContent;
	}

	public void setTemplateFileContent(String templateFileContent) {
		this.templateFileContent = templateFileContent;
	}

}