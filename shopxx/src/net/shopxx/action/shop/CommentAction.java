package net.shopxx.action.shop;

import javax.annotation.Resource;

import net.shopxx.bean.Setting;
import net.shopxx.bean.Setting.CommentAuthority;
import net.shopxx.bean.Setting.CommentDisplayType;
import net.shopxx.entity.Comment;
import net.shopxx.entity.Goods;
import net.shopxx.entity.Member;
import net.shopxx.service.CacheService;
import net.shopxx.service.CommentService;
import net.shopxx.service.GoodsService;
import net.shopxx.service.MemberService;
import net.shopxx.util.CaptchaUtil;
import net.shopxx.util.SettingUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 前台Action类 - 评论
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
public class CommentAction extends BaseShopAction {

	private static final long serialVersionUID = 576360600777068264L;
	
	private Comment comment;// 评论
	private Goods goods;
	private String forCommentId;// 回复评论ID
	
	@Resource(name = "commentServiceImpl")
	private CommentService commentService;
	@Resource(name = "goodsServiceImpl")
	private GoodsService goodsService;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "cacheServiceImpl")
	private CacheService cacheService;

	// 列表
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "id", message = "参数错误!")
		}
	)
	public String list() {
		Setting setting = SettingUtil.getSetting();
		if (!setting.getIsCommentEnabled()) {
			addActionError("本站商品评论功能现已关闭!");
			return ERROR;
		}
		goods = goodsService.load(id);
		pager = commentService.getCommentPager(pager, goods);
		return LIST;
	}
	
	// 保存
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "comment.goods.id", message = "参数错误!"),
			@RequiredStringValidator(fieldName = "comment.content", message = "评论内容不允许为空!")
		},
		stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "comment.content", maxLength = "10000", message = "评论内容长度超出限制!"),
			@StringLengthFieldValidator(fieldName = "comment.contact", maxLength = "200", message = "联系方式长度超出限制!")
		}
	)
	@InputConfig(resultName = "ajaxError")
	public String ajaxSave() {
		Setting setting = SettingUtil.getSetting();
		if (!setting.getIsCommentEnabled()) {
			return ajax(Status.error, "本站商品评论功能现已关闭!");
		}
		if (setting.getIsCommentCaptchaEnabled() && !CaptchaUtil.validateCaptchaByRequest(getRequest())) {
			return ajax(Status.error, "验证码输入错误!");
		}
		Member loginMember = getLoginMember();
		if (setting.getCommentAuthority() != CommentAuthority.anyone && loginMember == null) {
			return ajax(Status.error, "请进行会员登录!");
		}
		Goods goods = goodsService.load(comment.getGoods().getId());
		if (setting.getCommentAuthority() == CommentAuthority.purchased && !memberService.isPurchased(loginMember, goods)) {
			return ajax(Status.error, "您暂未购买此商品,无法评论!");
		}
		
		if (loginMember != null) {
			comment.setUsername(loginMember.getUsername());
			if (StringUtils.isEmpty(comment.getContact())) {
				comment.setContact(loginMember.getEmail());
			}
		} else {
			comment.setUsername(null);
		}
		if (setting.getCommentDisplayType() == CommentDisplayType.direct) {
			comment.setIsShow(true);
		} else {
			comment.setIsShow(false);
		}
		comment.setIp(getRequest().getRemoteAddr());
		comment.setIsAdminReply(false);
		comment.setForComment(null);
		commentService.save(comment);
		
		if (setting.getCommentDisplayType() == CommentDisplayType.direct) {
			cacheService.flushCommentListPageCache(getRequest());
			return ajax(Status.success, "商品评论成功!");
		} else {
			return ajax(Status.success, "商品评论成功,请等待管理员审核!");
		}
	}
	
	// 回复
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "comment.goods.id", message = "参数错误!"),
			@RequiredStringValidator(fieldName = "forCommentId", message = "参数错误!"),
			@RequiredStringValidator(fieldName = "comment.content", message = "评论内容不允许为空!")
		},
		stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "comment.content", maxLength = "10000", message = "评论内容长度超出限制!"),
			@StringLengthFieldValidator(fieldName = "comment.contact", maxLength = "200", message = "联系方式长度超出限制!")
		}
	)
	@InputConfig(resultName = "ajaxError")
	public String ajaxReply() {
		Setting setting = SettingUtil.getSetting();
		if (!setting.getIsCommentEnabled()) {
			return ajax(Status.error, "本站商品评论言功能现已关闭!");
		}
		if (setting.getIsCommentCaptchaEnabled() && !CaptchaUtil.validateCaptchaByRequest(getRequest())) {
			return ajax(Status.error, "验证码输入错误!");
		}
		Member loginMember = getLoginMember();
		if (setting.getCommentAuthority() != CommentAuthority.anyone && loginMember == null) {
			return ajax(Status.error, "请进行会员登录!");
		}
		Goods goods = goodsService.load(comment.getGoods().getId());
		if (setting.getCommentAuthority() == CommentAuthority.purchased && !memberService.isPurchased(loginMember, goods)) {
			return ajax(Status.error, "您暂未购买此商品,无法评论!");
		}
		
		if (loginMember != null) {
			comment.setUsername(loginMember.getUsername());
			if (StringUtils.isEmpty(comment.getContact())) {
				comment.setContact(loginMember.getEmail());
			}
		} else {
			comment.setUsername(null);
		}
		if (setting.getCommentDisplayType() == CommentDisplayType.direct) {
			comment.setIsShow(true);
		} else {
			comment.setIsShow(false);
		}
		Comment forComment = commentService.load(forCommentId);
		
		comment.setIp(getRequest().getRemoteAddr());
		comment.setIsAdminReply(false);
		comment.setGoods(forComment.getGoods());
		comment.setForComment(forComment);
		commentService.save(comment);
		
		if (setting.getCommentDisplayType() == CommentDisplayType.direct) {
			cacheService.flushCommentListPageCache(getRequest());
			return ajax(Status.success, "商品评论回复成功!");
		} else {
			return ajax(Status.success, "商品评论回复成功,请等待管理员审核!");
		}
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public String getForCommentId() {
		return forCommentId;
	}

	public void setForCommentId(String forCommentId) {
		this.forCommentId = forCommentId;
	}
	
}