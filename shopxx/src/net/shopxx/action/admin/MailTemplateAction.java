package net.shopxx.action.admin;

import java.util.List;

import javax.annotation.Resource;

import net.shopxx.bean.MailTemplateConfig;
import net.shopxx.util.TemplateConfigUtil;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.views.freemarker.FreemarkerManager;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

import freemarker.template.TemplateException;

/**
 * 后台Action类 - 邮件模板
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX3AE912EF052249487D08E69CADDD3FCD
 * ============================================================================
 */

@ParentPackage("admin")
public class MailTemplateAction extends BaseAdminAction {

	private static final long serialVersionUID = -1556710151369333272L;
	
	private MailTemplateConfig mailTemplateConfig;
	private String templateFileContent;
	
	@Resource(name = "freemarkerManager")
	private FreemarkerManager freemarkerManager;

	// 列表
	public String list() {
		return LIST;
	}

	// 编辑
	public String edit() {
		mailTemplateConfig = TemplateConfigUtil.getMailTemplateConfig(mailTemplateConfig.getName());
		templateFileContent = TemplateConfigUtil.readTemplateFileContent(mailTemplateConfig);
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
		mailTemplateConfig = TemplateConfigUtil.getMailTemplateConfig(mailTemplateConfig.getName());
		TemplateConfigUtil.writeTemplateFileContent(mailTemplateConfig, templateFileContent);
		try {
			freemarkerManager.getConfiguration(getServletContext()).clearTemplateCache();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		redirectUrl = "mail_template!list.action";
		return SUCCESS;
	}
	
	// 获取邮件模板配置集合
	public List<MailTemplateConfig> getAllMailTemplateConfigList() {
		return TemplateConfigUtil.getAllMailTemplateConfigList();
	}

	public MailTemplateConfig getMailTemplateConfig() {
		return mailTemplateConfig;
	}

	public void setMailTemplateConfig(MailTemplateConfig mailTemplateConfig) {
		this.mailTemplateConfig = mailTemplateConfig;
	}

	public String getTemplateFileContent() {
		return templateFileContent;
	}

	public void setTemplateFileContent(String templateFileContent) {
		this.templateFileContent = templateFileContent;
	}

}