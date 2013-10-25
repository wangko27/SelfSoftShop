package net.shopxx.action.admin;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import net.shopxx.bean.Setting;
import net.shopxx.bean.Setting.CommentAuthority;
import net.shopxx.bean.Setting.CommentDisplayType;
import net.shopxx.entity.Comment;
import net.shopxx.service.CacheService;
import net.shopxx.service.CommentService;
import net.shopxx.service.JobService;
import net.shopxx.util.SettingUtil;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 评论
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
public class CommentAction extends BaseAdminAction {

	private static final long serialVersionUID = 7683471852487335481L;
	
	private Comment comment;
	private Boolean isCommentEnabled;// 是否开启评论功能
	private Boolean isCommentCaptchaEnabled;// 是否开启评论验证码功能
	private CommentAuthority commentAuthority;// 评论发表权限
	private CommentDisplayType commentDisplayType;// 评论显示方式

	@Resource(name = "commentServiceImpl")
	private CommentService commentService;
	@Resource(name = "jobServiceImpl")
	private JobService jobService;
	@Resource(name = "cacheServiceImpl")
	private CacheService cacheService;
	
	// 查看
	public String view() {
		comment = commentService.load(id);
		return VIEW;
	}
	
	// 列表
	public String list() {
		pager = commentService.getCommentPager(pager);
		return LIST;
	}
	
	// 删除
	public String delete() {
		commentService.delete(ids);
		
		cacheService.flushCommentListPageCache(getRequest());
		
		return ajax(Status.success, "删除成功!");
	}
	
	// 回复
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "comment.content", message = "内容不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String reply() {
		Comment forComment = commentService.load(id);
		forComment.setIsShow(true);
		commentService.update(forComment);
		
		comment.setUsername(null);
		comment.setContact(null);
		comment.setIp(getRequest().getRemoteAddr());
		comment.setIsShow(true);
		comment.setIsAdminReply(true);
		comment.setGoods(forComment.getGoods());
		comment.setForComment(forComment);
		commentService.save(comment);
		
		cacheService.flushCommentListPageCache(getRequest());
		
		redirectUrl = "comment!view.action?id=" + forComment.getId();
		return SUCCESS;
	}
	
	// 删除回复
	@InputConfig(resultName = "ajaxError")
	public String ajaxDeleteReply() {
		Comment comment = commentService.load(id);
		commentService.delete(comment);
		
		cacheService.flushCommentListPageCache(getRequest());
		
		return ajax(Status.success, "删除成功!");
	}
	
	// 显示评论到页面
	@InputConfig(resultName = "ajaxError")
	public String ajaxShowComment() {
		Comment comment = commentService.load(id);
		comment.setIsShow(true);
		commentService.update(comment);
		
		cacheService.flushCommentListPageCache(getRequest());
		
		return ajax(Status.success, "显示评论到页面成功!");
	}
	
	// 关闭评论显示
	@InputConfig(resultName = "ajaxError")
	public String ajaxHiddenComment() {
		Comment comment = commentService.load(id);
		comment.setIsShow(false);
		commentService.update(comment);
		
		cacheService.flushCommentListPageCache(getRequest());
		
		return ajax(Status.success, "关闭评论显示成功!");
	}
	
	// 显示回复到页面
	@InputConfig(resultName = "ajaxError")
	public String ajaxShowReply() {
		Comment comment = commentService.load(id);
		comment.setIsShow(true);
		commentService.update(comment);
		
		cacheService.flushCommentListPageCache(getRequest());
		
		return ajax(Status.success, "显示回复到页面成功!");
	}
	
	// 关闭回复显示
	@InputConfig(resultName = "ajaxError")
	public String ajaxHiddenReply() {
		Comment comment = commentService.load(id);
		comment.setIsShow(false);
		commentService.update(comment);
		
		cacheService.flushCommentListPageCache(getRequest());
		
		return ajax(Status.success, "关闭回复显示成功!");
	}
	
	// 设置
	public String setting() {
		return "setting";
	}
	
	// 设置更新
	@Validations(
		requiredFields = {
			@RequiredFieldValidator(fieldName = "commentAuthority", message = "评论发表权限不允许为空!"),
			@RequiredFieldValidator(fieldName = "commentDisplayType", message = "显示方式不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String settingUpdate() {
		Setting setting = SettingUtil.getSetting();
		setting.setIsCommentEnabled(isCommentEnabled);
		setting.setIsCommentCaptchaEnabled(isCommentCaptchaEnabled);
		setting.setCommentAuthority(commentAuthority);
		setting.setCommentDisplayType(commentDisplayType);
		SettingUtil.updateSetting(setting);
		
		jobService.buildShopJs();
		jobService.buildGoodsContentHtml();
		
		redirectUrl = "comment!setting.action";
		return SUCCESS;
	}
	
	// 获取评论发表权限集合
	public List<CommentAuthority> getCommentAuthorityList() {
		return Arrays.asList(CommentAuthority.values());
	}
	
	// 获取评论显示方式集合
	public List<CommentDisplayType> getCommentDisplayTypeList() {
		return Arrays.asList(CommentDisplayType.values());
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public Boolean getIsCommentEnabled() {
		return isCommentEnabled;
	}

	public void setIsCommentEnabled(Boolean isCommentEnabled) {
		this.isCommentEnabled = isCommentEnabled;
	}

	public Boolean getIsCommentCaptchaEnabled() {
		return isCommentCaptchaEnabled;
	}

	public void setIsCommentCaptchaEnabled(Boolean isCommentCaptchaEnabled) {
		this.isCommentCaptchaEnabled = isCommentCaptchaEnabled;
	}

	public CommentAuthority getCommentAuthority() {
		return commentAuthority;
	}

	public void setCommentAuthority(CommentAuthority commentAuthority) {
		this.commentAuthority = commentAuthority;
	}

	public CommentDisplayType getCommentDisplayType() {
		return commentDisplayType;
	}

	public void setCommentDisplayType(CommentDisplayType commentDisplayType) {
		this.commentDisplayType = commentDisplayType;
	}

}