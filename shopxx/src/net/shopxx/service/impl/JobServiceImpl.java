package net.shopxx.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import net.shopxx.job.BuildAdminJsJob;
import net.shopxx.job.BuildArticleContentHtmlJob;
import net.shopxx.job.BuildErrorHtmlJob;
import net.shopxx.job.BuildGoodsContentHtmlJob;
import net.shopxx.job.BuildIndexHtmlJob;
import net.shopxx.job.BuildLoginHtmlJob;
import net.shopxx.job.BuildRegisterAgreementHtmlJob;
import net.shopxx.job.BuildShopJsJob;
import net.shopxx.job.DeleteArticleContentHtmlJob;
import net.shopxx.job.DeleteGoodsContentHtmlJob;
import net.shopxx.service.JobService;
import net.shopxx.util.SettingUtil;

import org.apache.commons.lang.time.DateUtils;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.springframework.stereotype.Service;

/**
 * Service实现类 - 任务
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXEEB47174264A6259B9A1BA5D141B72C0
 * ============================================================================
 */

@Service("jobServiceImpl")
public class JobServiceImpl implements JobService {
	
	@Resource(name = "scheduler")
	private Scheduler scheduler;
	
	public void buildIndexHtml() {
		try {
			SimpleTrigger simpleTrigger = new SimpleTrigger();
			simpleTrigger.setName(BuildIndexHtmlJob.TRIGGER_NAME);
			simpleTrigger.setGroup(BuildIndexHtmlJob.GROUP_NAME);
			simpleTrigger.setJobName(BuildIndexHtmlJob.JOB_NAME);
			simpleTrigger.setJobGroup(BuildIndexHtmlJob.GROUP_NAME);
			simpleTrigger.setStartTime(DateUtils.addSeconds(new Date(), SettingUtil.getSetting().getBuildHtmlDelayTime()));
			simpleTrigger.setRepeatCount(0);
			simpleTrigger.setRepeatInterval(60);
			
			JobDetail jobDetail = scheduler.getJobDetail(BuildIndexHtmlJob.JOB_NAME, BuildIndexHtmlJob.GROUP_NAME);
			if (jobDetail != null) {
				scheduler.rescheduleJob(BuildIndexHtmlJob.TRIGGER_NAME, BuildIndexHtmlJob.GROUP_NAME, simpleTrigger);
			} else {
				jobDetail = new JobDetail(BuildIndexHtmlJob.JOB_NAME, BuildIndexHtmlJob.GROUP_NAME, BuildIndexHtmlJob.class);
				scheduler.scheduleJob(jobDetail, simpleTrigger);
			}
			
			if (scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public void buildLoginHtml() {
		try {
			SimpleTrigger simpleTrigger = new SimpleTrigger();
			simpleTrigger.setName(BuildLoginHtmlJob.TRIGGER_NAME);
			simpleTrigger.setGroup(BuildLoginHtmlJob.GROUP_NAME);
			simpleTrigger.setJobName(BuildLoginHtmlJob.JOB_NAME);
			simpleTrigger.setJobGroup(BuildLoginHtmlJob.GROUP_NAME);
			simpleTrigger.setStartTime(DateUtils.addSeconds(new Date(), SettingUtil.getSetting().getBuildHtmlDelayTime()));
			simpleTrigger.setRepeatCount(0);
			simpleTrigger.setRepeatInterval(60);
			
			JobDetail jobDetail = scheduler.getJobDetail(BuildLoginHtmlJob.JOB_NAME, BuildLoginHtmlJob.GROUP_NAME);
			if (jobDetail != null) {
				scheduler.rescheduleJob(BuildLoginHtmlJob.TRIGGER_NAME, BuildLoginHtmlJob.GROUP_NAME, simpleTrigger);
			} else {
				jobDetail = new JobDetail(BuildLoginHtmlJob.JOB_NAME, BuildLoginHtmlJob.GROUP_NAME, BuildLoginHtmlJob.class);
				scheduler.scheduleJob(jobDetail, simpleTrigger);
			}
			
			if (scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public void buildRegisterAgreementHtml() {
		try {
			SimpleTrigger simpleTrigger = new SimpleTrigger();
			simpleTrigger.setName(BuildRegisterAgreementHtmlJob.TRIGGER_NAME);
			simpleTrigger.setGroup(BuildRegisterAgreementHtmlJob.GROUP_NAME);
			simpleTrigger.setJobName(BuildRegisterAgreementHtmlJob.JOB_NAME);
			simpleTrigger.setJobGroup(BuildRegisterAgreementHtmlJob.GROUP_NAME);
			simpleTrigger.setStartTime(DateUtils.addSeconds(new Date(), SettingUtil.getSetting().getBuildHtmlDelayTime()));
			simpleTrigger.setRepeatCount(0);
			simpleTrigger.setRepeatInterval(60);
			
			JobDetail jobDetail = scheduler.getJobDetail(BuildRegisterAgreementHtmlJob.JOB_NAME, BuildRegisterAgreementHtmlJob.GROUP_NAME);
			if (jobDetail != null) {
				scheduler.rescheduleJob(BuildRegisterAgreementHtmlJob.TRIGGER_NAME, BuildRegisterAgreementHtmlJob.GROUP_NAME, simpleTrigger);
			} else {
				jobDetail = new JobDetail(BuildRegisterAgreementHtmlJob.JOB_NAME, BuildRegisterAgreementHtmlJob.GROUP_NAME, BuildRegisterAgreementHtmlJob.class);
				scheduler.scheduleJob(jobDetail, simpleTrigger);
			}
			
			if (scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public void buildAdminJs() {
		try {
			SimpleTrigger simpleTrigger = new SimpleTrigger();
			simpleTrigger.setName(BuildAdminJsJob.TRIGGER_NAME);
			simpleTrigger.setGroup(BuildAdminJsJob.GROUP_NAME);
			simpleTrigger.setJobName(BuildAdminJsJob.JOB_NAME);
			simpleTrigger.setJobGroup(BuildAdminJsJob.GROUP_NAME);
			simpleTrigger.setStartTime(DateUtils.addSeconds(new Date(), SettingUtil.getSetting().getBuildHtmlDelayTime()));
			simpleTrigger.setRepeatCount(0);
			simpleTrigger.setRepeatInterval(60);
			
			JobDetail jobDetail = scheduler.getJobDetail(BuildAdminJsJob.JOB_NAME, BuildAdminJsJob.GROUP_NAME);
			if (jobDetail != null) {
				scheduler.rescheduleJob(BuildAdminJsJob.TRIGGER_NAME, BuildAdminJsJob.GROUP_NAME, simpleTrigger);
			} else {
				jobDetail = new JobDetail(BuildAdminJsJob.JOB_NAME, BuildAdminJsJob.GROUP_NAME, BuildAdminJsJob.class);
				scheduler.scheduleJob(jobDetail, simpleTrigger);
			}
			
			if (scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public void buildShopJs() {
		try {
			SimpleTrigger simpleTrigger = new SimpleTrigger();
			simpleTrigger.setName(BuildShopJsJob.TRIGGER_NAME);
			simpleTrigger.setGroup(BuildShopJsJob.GROUP_NAME);
			simpleTrigger.setJobName(BuildShopJsJob.JOB_NAME);
			simpleTrigger.setJobGroup(BuildShopJsJob.GROUP_NAME);
			simpleTrigger.setStartTime(DateUtils.addSeconds(new Date(), SettingUtil.getSetting().getBuildHtmlDelayTime()));
			simpleTrigger.setRepeatCount(0);
			simpleTrigger.setRepeatInterval(60);
			
			JobDetail jobDetail = scheduler.getJobDetail(BuildShopJsJob.JOB_NAME, BuildShopJsJob.GROUP_NAME);
			if (jobDetail != null) {
				scheduler.rescheduleJob(BuildShopJsJob.TRIGGER_NAME, BuildShopJsJob.GROUP_NAME, simpleTrigger);
			} else {
				jobDetail = new JobDetail(BuildShopJsJob.JOB_NAME, BuildShopJsJob.GROUP_NAME, BuildShopJsJob.class);
				scheduler.scheduleJob(jobDetail, simpleTrigger);
			}
			
			if (scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public void buildArticleContentHtml(String id) {
		try {
			String jobName = BuildArticleContentHtmlJob.JOB_NAME + id;
			String triggerName = BuildArticleContentHtmlJob.TRIGGER_NAME + id;
			String groupName = BuildArticleContentHtmlJob.GROUP_NAME;
			
			SimpleTrigger simpleTrigger = new SimpleTrigger();
			simpleTrigger.setName(triggerName);
			simpleTrigger.setGroup(groupName);
			simpleTrigger.setJobName(jobName);
			simpleTrigger.setJobGroup(groupName);
			simpleTrigger.setStartTime(DateUtils.addSeconds(new Date(), SettingUtil.getSetting().getBuildHtmlDelayTime()));
			simpleTrigger.setRepeatCount(0);
			simpleTrigger.setRepeatInterval(60);
			
			JobDetail jobDetail = scheduler.getJobDetail(jobName, groupName);
			if (jobDetail != null) {
				scheduler.rescheduleJob(triggerName, groupName, simpleTrigger);
				jobDetail.getJobDataMap().put("id", id);
			} else {
				jobDetail = new JobDetail(jobName, groupName, BuildArticleContentHtmlJob.class);
				jobDetail.getJobDataMap().put("id", id);
				scheduler.scheduleJob(jobDetail, simpleTrigger);
			}
			
			if (scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public void buildArticleContentHtml() {
		try {
			String jobName = BuildArticleContentHtmlJob.JOB_NAME;
			String triggerName = BuildArticleContentHtmlJob.TRIGGER_NAME;
			String groupName = BuildArticleContentHtmlJob.GROUP_NAME;
			
			SimpleTrigger simpleTrigger = new SimpleTrigger();
			simpleTrigger.setName(triggerName);
			simpleTrigger.setGroup(groupName);
			simpleTrigger.setJobName(jobName);
			simpleTrigger.setJobGroup(groupName);
			simpleTrigger.setStartTime(DateUtils.addSeconds(new Date(), SettingUtil.getSetting().getBuildHtmlDelayTime() * 2));
			simpleTrigger.setRepeatCount(0);
			simpleTrigger.setRepeatInterval(60);
			
			String[] triggerNames = scheduler.getTriggerNames(groupName);
			String[] jobNames = scheduler.getJobNames(groupName);
			if (triggerNames != null) {
				for (String tn : triggerNames) {
					scheduler.pauseTrigger(tn, groupName);
					scheduler.unscheduleJob(tn, groupName);
				}
			}
			if (jobNames != null) {
				for (String jn : jobNames) {
					scheduler.deleteJob(jn, groupName);
				}
			}
			
			JobDetail jobDetail = scheduler.getJobDetail(jobName, groupName);
			if (jobDetail != null) {
				scheduler.rescheduleJob(triggerName, groupName, simpleTrigger);
			} else {
				jobDetail = new JobDetail(jobName, groupName, BuildArticleContentHtmlJob.class);
				scheduler.scheduleJob(jobDetail, simpleTrigger);
			}
			
			if (scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public void buildGoodsContentHtml(String id) {
		try {
			String jobName = BuildGoodsContentHtmlJob.JOB_NAME + id;
			String triggerName = BuildGoodsContentHtmlJob.TRIGGER_NAME + id;
			String groupName = BuildGoodsContentHtmlJob.GROUP_NAME;
			
			SimpleTrigger simpleTrigger = new SimpleTrigger();
			simpleTrigger.setName(triggerName);
			simpleTrigger.setGroup(groupName);
			simpleTrigger.setJobName(jobName);
			simpleTrigger.setJobGroup(groupName);
			simpleTrigger.setStartTime(DateUtils.addSeconds(new Date(), SettingUtil.getSetting().getBuildHtmlDelayTime()));
			simpleTrigger.setRepeatCount(0);
			simpleTrigger.setRepeatInterval(60);
			
			JobDetail jobDetail = scheduler.getJobDetail(jobName, groupName);
			if (jobDetail != null) {
				scheduler.rescheduleJob(triggerName, groupName, simpleTrigger);
				jobDetail.getJobDataMap().put("id", id);
			} else {
				jobDetail = new JobDetail(jobName, groupName, BuildGoodsContentHtmlJob.class);
				jobDetail.getJobDataMap().put("id", id);
				scheduler.scheduleJob(jobDetail, simpleTrigger);
			}
			
			if (scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public void buildGoodsContentHtml() {
		try {
			String jobName = BuildGoodsContentHtmlJob.JOB_NAME;
			String triggerName = BuildGoodsContentHtmlJob.TRIGGER_NAME;
			String groupName = BuildGoodsContentHtmlJob.GROUP_NAME;
			
			SimpleTrigger simpleTrigger = new SimpleTrigger();
			simpleTrigger.setName(triggerName);
			simpleTrigger.setGroup(groupName);
			simpleTrigger.setJobName(jobName);
			simpleTrigger.setJobGroup(groupName);
			simpleTrigger.setStartTime(DateUtils.addSeconds(new Date(), SettingUtil.getSetting().getBuildHtmlDelayTime() * 2));
			simpleTrigger.setRepeatCount(0);
			simpleTrigger.setRepeatInterval(60);
			
			String[] triggerNames = scheduler.getTriggerNames(groupName);
			String[] jobNames = scheduler.getJobNames(groupName);
			if (triggerNames != null) {
				for (String tn : triggerNames) {
					scheduler.pauseTrigger(tn, groupName);
					scheduler.unscheduleJob(tn, groupName);
				}
			}
			if (jobNames != null) {
				for (String jn : jobNames) {
					scheduler.deleteJob(jn, groupName);
				}
			}
			
			JobDetail jobDetail = scheduler.getJobDetail(jobName, groupName);
			if (jobDetail != null) {
				scheduler.rescheduleJob(triggerName, groupName, simpleTrigger);
			} else {
				jobDetail = new JobDetail(jobName, groupName, BuildGoodsContentHtmlJob.class);
				scheduler.scheduleJob(jobDetail, simpleTrigger);
			}
			
			if (scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public void buildErrorHtml() {
		try {
			SimpleTrigger simpleTrigger = new SimpleTrigger();
			simpleTrigger.setName(BuildErrorHtmlJob.TRIGGER_NAME);
			simpleTrigger.setGroup(BuildErrorHtmlJob.GROUP_NAME);
			simpleTrigger.setJobName(BuildErrorHtmlJob.JOB_NAME);
			simpleTrigger.setJobGroup(BuildErrorHtmlJob.GROUP_NAME);
			simpleTrigger.setStartTime(DateUtils.addSeconds(new Date(), SettingUtil.getSetting().getBuildHtmlDelayTime()));
			simpleTrigger.setRepeatCount(0);
			simpleTrigger.setRepeatInterval(60);
			
			JobDetail jobDetail = scheduler.getJobDetail(BuildErrorHtmlJob.JOB_NAME, BuildErrorHtmlJob.GROUP_NAME);
			if (jobDetail != null) {
				scheduler.rescheduleJob(BuildErrorHtmlJob.TRIGGER_NAME, BuildErrorHtmlJob.GROUP_NAME, simpleTrigger);
			} else {
				jobDetail = new JobDetail(BuildErrorHtmlJob.JOB_NAME, BuildErrorHtmlJob.GROUP_NAME, BuildErrorHtmlJob.class);
				scheduler.scheduleJob(jobDetail, simpleTrigger);
			}
			
			if (scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteArticleContentHtml(String htmlPath, Integer pageCount) {
		try {
			String jobName = DeleteArticleContentHtmlJob.JOB_NAME + htmlPath;
			String triggerName = DeleteArticleContentHtmlJob.TRIGGER_NAME + htmlPath;
			String groupName = DeleteArticleContentHtmlJob.GROUP_NAME;
			
			SimpleTrigger simpleTrigger = new SimpleTrigger();
			simpleTrigger.setName(triggerName);
			simpleTrigger.setGroup(groupName);
			simpleTrigger.setJobName(jobName);
			simpleTrigger.setJobGroup(groupName);
			simpleTrigger.setStartTime(DateUtils.addSeconds(new Date(), SettingUtil.getSetting().getBuildHtmlDelayTime()));
			simpleTrigger.setRepeatCount(0);
			simpleTrigger.setRepeatInterval(60);
			
			JobDetail jobDetail = scheduler.getJobDetail(jobName, groupName);
			if (jobDetail != null) {
				scheduler.rescheduleJob(triggerName, groupName, simpleTrigger);
				jobDetail.getJobDataMap().put("htmlPath", htmlPath);
				jobDetail.getJobDataMap().put("pageCount", pageCount);
			} else {
				jobDetail = new JobDetail(jobName, groupName, DeleteGoodsContentHtmlJob.class);
				jobDetail.getJobDataMap().put("htmlPath", htmlPath);
				jobDetail.getJobDataMap().put("pageCount", pageCount);
				scheduler.scheduleJob(jobDetail, simpleTrigger);
			}
			
			if (scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteGoodsContentHtml(String htmlPath) {
		try {
			String jobName = DeleteGoodsContentHtmlJob.JOB_NAME + htmlPath;
			String triggerName = DeleteGoodsContentHtmlJob.TRIGGER_NAME + htmlPath;
			String groupName = DeleteGoodsContentHtmlJob.GROUP_NAME;
			
			SimpleTrigger simpleTrigger = new SimpleTrigger();
			simpleTrigger.setName(triggerName);
			simpleTrigger.setGroup(groupName);
			simpleTrigger.setJobName(jobName);
			simpleTrigger.setJobGroup(groupName);
			simpleTrigger.setStartTime(DateUtils.addSeconds(new Date(), SettingUtil.getSetting().getBuildHtmlDelayTime()));
			simpleTrigger.setRepeatCount(0);
			simpleTrigger.setRepeatInterval(60);
			
			JobDetail jobDetail = scheduler.getJobDetail(jobName, groupName);
			if (jobDetail != null) {
				scheduler.rescheduleJob(triggerName, groupName, simpleTrigger);
				jobDetail.getJobDataMap().put("htmlPath", htmlPath);
			} else {
				jobDetail = new JobDetail(jobName, groupName, DeleteGoodsContentHtmlJob.class);
				jobDetail.getJobDataMap().put("htmlPath", htmlPath);
				scheduler.scheduleJob(jobDetail, simpleTrigger);
			}
			
			if (scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

}