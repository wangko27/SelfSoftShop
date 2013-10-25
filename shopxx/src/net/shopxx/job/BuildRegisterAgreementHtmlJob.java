package net.shopxx.job;

import net.shopxx.service.HtmlService;
import net.shopxx.util.SpringUtil;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * Service接口 - 生成注册协议HTML任务
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX3F6674D6C2E7DF7287EF69622E4F46B5
 * ============================================================================
 */

public class BuildRegisterAgreementHtmlJob implements Job {
	
	public static final String JOB_NAME = "buildRegisterAgreementHtmlJob";// 任务名称
	public static final String TRIGGER_NAME = "buildRegisterAgreementHtmlTrigger";// Trigger名称
	public static final String GROUP_NAME = "buildRegisterAgreementHtmlGroup";// Group名称

	public void execute(JobExecutionContext context) {
		HtmlService htmlService = (HtmlService) SpringUtil.getBean("quartzHtmlServiceImpl");
		htmlService.buildRegisterAgreementHtml();
	}

}