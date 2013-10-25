package net.shopxx.action.shop;

import javax.annotation.Resource;

import net.shopxx.bean.Setting;
import net.shopxx.bean.Setting.LeaveMessageDisplayType;
import net.shopxx.entity.LeaveMessage;
import net.shopxx.entity.Member;
import net.shopxx.service.CacheService;
import net.shopxx.service.LeaveMessageService;
import net.shopxx.util.CaptchaUtil;
import net.shopxx.util.SettingUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 前台Action类 - 在线留言
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
public class LeaveMessageAction extends BaseShopAction {

	private static final long serialVersionUID = -8081978251791210036L;
	
	private LeaveMessage leaveMessage;// 在线留言
	
	@Resource(name = "leaveMessageServiceImpl")
	private LeaveMessageService leaveMessageService;
	@Resource(name = "cacheServiceImpl")
	private CacheService cacheService;

	// 列表
	public String list() {
		Setting setting = SettingUtil.getSetting();
		if (!setting.getIsLeaveMessageEnabled()) {
			addActionError("本站在线留言功能现已关闭!");
			return ERROR;
		}
		if (setting.getLeaveMessageDisplayType() == LeaveMessageDisplayType.direct) {
			pager = leaveMessageService.getLeaveMessagePager(pager, true);
		} else {
			pager = leaveMessageService.getLeaveMessagePager(pager, false);
		}
		return LIST;
	}
	
	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "leaveMessage.title", message = "在线留言标题不允许为空!"),
			@RequiredStringValidator(fieldName = "leaveMessage.content", message = "在线留言内容不允许为空!")
		},
		stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "leaveMessage.title", maxLength = "200", message = "在线留言标题长度超出限制!"),
			@StringLengthFieldValidator(fieldName = "leaveMessage.content", maxLength = "10000", message = "在线留言内容长度超出限制!"),
			@StringLengthFieldValidator(fieldName = "leaveMessage.contact", maxLength = "200", message = "联系方式长度超出限制!")
		}
	)
	@InputConfig(resultName = "ajaxError")
	public String ajaxSave() {
		Setting setting = SettingUtil.getSetting();
		if (!setting.getIsLeaveMessageEnabled()) {
			return ajax(Status.error, "本站在线留言功能现已关闭!");
		}
		if (setting.getIsLeaveMessageCaptchaEnabled() && !CaptchaUtil.validateCaptchaByRequest(getRequest())) {
			return ajax(Status.error, "验证码输入错误!");
		}
		
		Member loginMember = getLoginMember();
		if (loginMember != null) {
			leaveMessage.setUsername(loginMember.getUsername());
			if (StringUtils.isEmpty(leaveMessage.getContact())) {
				leaveMessage.setContact(loginMember.getEmail());
			}
		} else {
			leaveMessage.setUsername(null);
		}
		
		leaveMessage.setIp(getRequest().getRemoteAddr());
		leaveMessage.setForLeaveMessage(null);
		leaveMessageService.save(leaveMessage);
		
		if (setting.getLeaveMessageDisplayType() == LeaveMessageDisplayType.direct) {
			cacheService.flushLeaveMessagePageCache(getRequest());
			return ajax(Status.success, "在线留言成功!");
		} else {
			return ajax(Status.success, "在线留言成功,请等待管理员审核!");
		}
	}

	public LeaveMessage getLeaveMessage() {
		return leaveMessage;
	}

	public void setLeaveMessage(LeaveMessage leaveMessage) {
		this.leaveMessage = leaveMessage;
	}
	
}