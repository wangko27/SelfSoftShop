package net.shopxx.action.admin;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import net.shopxx.bean.Setting;
import net.shopxx.bean.Setting.LeaveMessageDisplayType;
import net.shopxx.entity.LeaveMessage;
import net.shopxx.service.CacheService;
import net.shopxx.service.LeaveMessageService;
import net.shopxx.util.SettingUtil;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 在线留言
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXE5A2C60595A4C64534D70AC9A5B94FD
 * ============================================================================
 */

@ParentPackage("admin")
public class LeaveMessageAction extends BaseAdminAction {

	private static final long serialVersionUID = 7241631631116518460L;
	
	private LeaveMessage leaveMessage;
	private Boolean isLeaveMessageEnabled;
	private Boolean isLeaveMessageCaptchaEnabled;
	private LeaveMessageDisplayType leaveMessageDisplayType;

	@Resource(name = "leaveMessageServiceImpl")
	private LeaveMessageService leaveMessageService;
	@Resource(name = "cacheServiceImpl")
	private CacheService cacheService;
	
	// 查看
	public String view() {
		leaveMessage = leaveMessageService.load(id);
		return VIEW;
	}
	
	// 列表
	public String list() {
		pager = leaveMessageService.getLeaveMessagePager(pager, true);
		return LIST;
	}
	
	// 删除
	public String delete() {
		leaveMessageService.delete(ids);
		cacheService.flushLeaveMessagePageCache(getRequest());
		return ajax(Status.success, "删除成功!");
	}
	
	// 回复
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "leaveMessage.title", message = "标题不允许为空!"),
			@RequiredStringValidator(fieldName = "leaveMessage.content", message = "内容不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String reply() {
		LeaveMessage forLeaveMessage = leaveMessageService.load(id);
		leaveMessage.setUsername(null);
		leaveMessage.setContact(null);
		leaveMessage.setIp(getRequest().getRemoteAddr());
		leaveMessage.setForLeaveMessage(forLeaveMessage);
		leaveMessageService.save(leaveMessage);
		cacheService.flushLeaveMessagePageCache(getRequest());
		redirectUrl = "leave_message!view.action?id=" + forLeaveMessage.getId();
		return SUCCESS;
	}
	
	// 删除回复
	@InputConfig(resultName = "ajaxError")
	public String ajaxDeleteReply() {
		leaveMessageService.delete(id);
		cacheService.flushLeaveMessagePageCache(getRequest());
		return ajax(Status.success, "删除成功!");
	}
	
	// 设置
	public String setting() {
		return "setting";
	}
	
	// 设置更新
	@Validations(
		requiredFields = {
			@RequiredFieldValidator(fieldName = "leaveMessageDisplayType", message = "显示方式不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String settingUpdate() {
		Setting setting = SettingUtil.getSetting();
		setting.setIsLeaveMessageEnabled(isLeaveMessageEnabled);
		setting.setIsLeaveMessageCaptchaEnabled(isLeaveMessageCaptchaEnabled);
		setting.setLeaveMessageDisplayType(leaveMessageDisplayType);
		SettingUtil.updateSetting(setting);
		cacheService.flushLeaveMessagePageCache(getRequest());
		redirectUrl = "leave_message!setting.action";
		return SUCCESS;
	}
	
	// 获取在线留言显示方式集合
	public List<LeaveMessageDisplayType> getLeaveMessageDisplayTypeList() {
		return Arrays.asList(LeaveMessageDisplayType.values());
	}

	public LeaveMessage getLeaveMessage() {
		return leaveMessage;
	}

	public void setLeaveMessage(LeaveMessage leaveMessage) {
		this.leaveMessage = leaveMessage;
	}

	public Boolean getIsLeaveMessageEnabled() {
		return isLeaveMessageEnabled;
	}

	public void setIsLeaveMessageEnabled(Boolean isLeaveMessageEnabled) {
		this.isLeaveMessageEnabled = isLeaveMessageEnabled;
	}

	public Boolean getIsLeaveMessageCaptchaEnabled() {
		return isLeaveMessageCaptchaEnabled;
	}

	public void setIsLeaveMessageCaptchaEnabled(Boolean isLeaveMessageCaptchaEnabled) {
		this.isLeaveMessageCaptchaEnabled = isLeaveMessageCaptchaEnabled;
	}

	public LeaveMessageDisplayType getLeaveMessageDisplayType() {
		return leaveMessageDisplayType;
	}

	public void setLeaveMessageDisplayType(LeaveMessageDisplayType leaveMessageDisplayType) {
		this.leaveMessageDisplayType = leaveMessageDisplayType;
	}

}