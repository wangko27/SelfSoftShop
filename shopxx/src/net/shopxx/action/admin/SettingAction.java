package net.shopxx.action.admin;

import java.io.File;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import net.shopxx.bean.Setting;
import net.shopxx.bean.Setting.CurrencyType;
import net.shopxx.bean.Setting.Operator;
import net.shopxx.bean.Setting.RoundType;
import net.shopxx.bean.Setting.ScoreType;
import net.shopxx.bean.Setting.StoreFreezeTime;
import net.shopxx.bean.Setting.WatermarkPosition;
import net.shopxx.service.CacheService;
import net.shopxx.service.JobService;
import net.shopxx.service.MailService;
import net.shopxx.util.ImageUtil;
import net.shopxx.util.SettingUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 系统配置
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXCB0831367FDD7078CE4312A6DBF9737D
 * ============================================================================
 */

@ParentPackage("admin")
public class SettingAction extends BaseAdminAction {

	private static final long serialVersionUID = -6200425957233641240L;

	private Setting setting;
	private String smtpToMail;
	private File shopLogo;
	private File defaultBigGoodsImage;
	private File defaultSmallGoodsImage;
	private File defaultThumbnailGoodsImage;
	private File watermarkImage;
	
	@Resource(name = "mailServiceImpl")
	private MailService mailService;
	@Resource(name = "jobServiceImpl")
	private JobService jobService;
	@Resource(name = "cacheServiceImpl")
	private CacheService cacheService;
	
	// 发送SMTP测试邮件
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "setting.smtpFromMail", message = "发件人邮箱不允许为空!"),
			@RequiredStringValidator(fieldName = "setting.smtpHost", message = "SMTP服务器地址不允许为空!"),
			@RequiredStringValidator(fieldName = "setting.smtpUsername", message = "SMTP用户名不允许为空!"),
			@RequiredStringValidator(fieldName = "setting.smtpPassword", message = "SMTP密码不允许为空!"),
			@RequiredStringValidator(fieldName = "smtpToMail", message = "收件人邮箱不允许为空!")
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "setting.smtpPort", message = "SMTP服务器端口不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "setting.smtpPort", min = "0", message = "SMTP端口必须为零正整数!")
		},
		emails = {
			@EmailValidator(fieldName = "setting.smtpFromMail", message = "发件人邮箱格式错误!"),
			@EmailValidator(fieldName = "smtpToMail", message = "收件人邮箱格式错误!")
		}
	)
	
	@InputConfig(resultName = "ajaxError")
	public String ajaxSendSmtpTest() {
		try {
			mailService.sendSmtpTestMail(setting.getSmtpFromMail(), setting.getSmtpHost(), setting.getSmtpPort(), setting.getSmtpUsername(), setting.getSmtpPassword(), smtpToMail);
		} catch (MailAuthenticationException e) {
			return ajax(Status.error, "权限验证失败,请检查SMTP用户名、密码!");
		} catch (MailSendException e) {
			return ajax(Status.error, "邮件发送失败,请检查发件人邮箱、SMTP服务器地址、端口!");
		} catch (Exception e) {
			return ajax(Status.error, "邮件发送失败!");
		}
		return ajax(Status.success, "测试邮件发送成功,请查收邮件!");
	}

	// 编辑
	public String edit() {
		setting = SettingUtil.getSetting();
		return INPUT;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "setting.shopName", message = "网店名称不允许为空!"),
			@RequiredStringValidator(fieldName = "setting.shopUrl", message = "网店网址不允许为空!"),
			@RequiredStringValidator(fieldName = "setting.smtpFromMail", message = "发件人邮箱不允许为空!"),
			@RequiredStringValidator(fieldName = "setting.smtpHost", message = "SMTP服务器地址不允许为空!"),
			@RequiredStringValidator(fieldName = "setting.smtpUsername", message = "SMTP用户名不允许为空!"),
			@RequiredStringValidator(fieldName = "setting.currencySign", message = "货币符号不允许为空!"),
			@RequiredStringValidator(fieldName = "setting.currencyUnit", message = "货币单位不允许为空!")
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "setting.currencyType", message = "货币种类不允许为空!"),
			@RequiredFieldValidator(fieldName = "setting.priceScale", message = "商品价格精确位数不允许为空!"),
			@RequiredFieldValidator(fieldName = "setting.priceRoundType", message = "商品价格精确方式不允许为空!"),
			@RequiredFieldValidator(fieldName = "setting.storeAlertCount", message = "商品库存报警数量不允许为空!"),
			@RequiredFieldValidator(fieldName = "setting.storeFreezeTime", message = "库存预占时间点不允许为空!"),
			@RequiredFieldValidator(fieldName = "setting.isRegisterEnabled", message = "是否开放注册不允许为空!"),
			@RequiredFieldValidator(fieldName = "setting.isLoginFailureLock", message = "是否开启自动锁定账号功能不允许为空!"),
			@RequiredFieldValidator(fieldName = "setting.loginFailureLockCount", message = "连续登录失败最大次数不允许为空!"),
			@RequiredFieldValidator(fieldName = "setting.loginFailureLockTime", message = "自动解锁时间不允许为空!"),
			@RequiredFieldValidator(fieldName = "setting.bigGoodsImageWidth", message = "商品图片（大）宽不允许为空!"),
			@RequiredFieldValidator(fieldName = "setting.bigGoodsImageHeight", message = "商品图片（大）高不允许为空!"),
			@RequiredFieldValidator(fieldName = "setting.smallGoodsImageWidth", message = "商品图片（小）宽不允许为空!"),
			@RequiredFieldValidator(fieldName = "setting.smallGoodsImageHeight", message = "商品图片（小）高不允许为空!"),
			@RequiredFieldValidator(fieldName = "setting.thumbnailGoodsImageWidth", message = "商品缩略图宽不允许为空!"),
			@RequiredFieldValidator(fieldName = "setting.thumbnailGoodsImageHeight", message = "商品缩略图高不允许为空!"),
			@RequiredFieldValidator(fieldName = "setting.smtpPort", message = "SMTP服务器端口不允许为空!"),
			@RequiredFieldValidator(fieldName = "setting.watermarkAlpha", message = "水印透明度不允许为空!"),
			@RequiredFieldValidator(fieldName = "setting.isShowMarketPrice", message = "前台是否显示市场价不允许为空!"),
			@RequiredFieldValidator(fieldName = "setting.defaultMarketPriceOperator", message = "默认市场价运算符不允许为空!"),
			@RequiredFieldValidator(fieldName = "setting.defaultMarketPriceNumber", message = "默认市场价运算基数不允许为空!"),
			@RequiredFieldValidator(fieldName = "setting.scoreType", message = "积分获取方式不允许为空!"),
			@RequiredFieldValidator(fieldName = "setting.scoreScale", message = "积分换算比率不允许为空!"),
			@RequiredFieldValidator(fieldName = "setting.isGzipEnabled", message = "是否开启GZIP功能不允许为空!"),
			@RequiredFieldValidator(fieldName = "setting.buildHtmlDelayTime", message = "HTML自动生成延时不允许为空!")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "setting.priceScale", min = "0", message = "商品价格精确位数必须为零或正整数!"),
			@IntRangeFieldValidator(fieldName = "setting.priceScale", max = "4", message = "商品价格精确位数位不能大于4!"),
			@IntRangeFieldValidator(fieldName = "setting.loginFailureLockCount", min = "1", message = "连续登录失败最大次数必须为正整数!"),
			@IntRangeFieldValidator(fieldName = "setting.loginFailureLockTime", min = "0", message = "自动解锁时间必须为零或正整数!"),
			@IntRangeFieldValidator(fieldName = "setting.bigGoodsImageWidth", min = "1", message = "商品图片（大）宽必须为正整数!"),
			@IntRangeFieldValidator(fieldName = "setting.bigGoodsImageHeight", min = "1", message = "商品图片（大）高必须为正整数!"),
			@IntRangeFieldValidator(fieldName = "setting.smallGoodsImageWidth", min = "1", message = "商品图片（小）宽必须为正整数!"),
			@IntRangeFieldValidator(fieldName = "setting.smallGoodsImageHeight", min = "1", message = "商品图片（小）高必须为正整数!"),
			@IntRangeFieldValidator(fieldName = "setting.thumbnailGoodsImageWidth", min = "1", message = "商品缩略图宽必须为正整数!"),
			@IntRangeFieldValidator(fieldName = "setting.thumbnailGoodsImageHeight", min = "1", message = "商品缩略图高必须为正整数!"),
			@IntRangeFieldValidator(fieldName = "setting.smtpPort", min = "0", message = "SMTP端口必须为零或正整数!"),
			@IntRangeFieldValidator(fieldName = "setting.watermarkAlpha", min = "0", max="100", message = "水印透明度取值范围在${min}-${max}之间!"),
			@IntRangeFieldValidator(fieldName = "setting.buildHtmlDelayTime", min = "0", message = "HTML自动生成延时必须为零或正整数!")
		},
		emails = {
			@EmailValidator(fieldName = "setting.email", message = "E-mail格式错误!"),
			@EmailValidator(fieldName = "setting.smtpFromMail", message = "发件人邮箱格式错误!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() throws Exception {
		if (setting.getScoreScale() < 0) {
			addActionError("积分换算比率不允许小于0!");
			return ERROR;
		}
		if (setting.getDefaultMarketPriceOperator() == Operator.divide && setting.getDefaultMarketPriceNumber().compareTo(new BigDecimal(0)) == 0) {
			addActionError("默认市场价运算基数错误!");
			return ERROR;
		}
		
		Setting persistent = SettingUtil.getSetting();
		
		if (StringUtils.isNotEmpty(setting.getSmtpPassword())) {
			persistent.setSmtpPassword(setting.getSmtpPassword());
		}
		if (!setting.getIsLoginFailureLock()) {
			persistent.setLoginFailureLockCount(3);
			persistent.setLoginFailureLockTime(10);
		}
		if (shopLogo != null) {
			File destShopLogo = new File(getRealPath(persistent.getShopLogoPath()));
			ImageUtil.toJpegImageFile(shopLogo, destShopLogo);
		}
		if (defaultBigGoodsImage != null) {
			File destDefaultBigGoodsImage = new File(getRealPath(persistent.getDefaultBigGoodsImagePath()));
			ImageUtil.toJpegImageFile(defaultBigGoodsImage, destDefaultBigGoodsImage);
		}
		if (defaultSmallGoodsImage != null) {
			File destDefaultSmallGoodsImage = new File(getRealPath(persistent.getDefaultSmallGoodsImagePath()));
			ImageUtil.toJpegImageFile(defaultSmallGoodsImage, destDefaultSmallGoodsImage);
		}
		if (defaultThumbnailGoodsImage != null) {
			File destDefaultThumbnailGoodsImage = new File(getRealPath(persistent.getDefaultThumbnailGoodsImagePath()));
			ImageUtil.toJpegImageFile(defaultThumbnailGoodsImage, destDefaultThumbnailGoodsImage);
		}
		if (watermarkImage != null) {
			File destWatermarkImage = new File(getRealPath(persistent.getWatermarkImagePath()));
			ImageUtil.toJpegImageFile(watermarkImage, destWatermarkImage);
		}
		BeanUtils.copyProperties(setting, persistent, new String[] {"systemName", "systemVersion", "systemDescription", "contextPath", "imageUploadPath", "imageBrowsePath", "adminLoginUrl", "adminLoginProcessingUrl", "isShowPoweredInfo", "shopLogoPath", "defaultBigGoodsImagePath", "defaultSmallGoodsImagePath", "defaultThumbnailGoodsImagePath", "watermarkImagePath", "isInstantMessagingEnabled", "instantMessagingPosition", "instantMessagingTitle", "isLeaveMessageEnabled", "isLeaveMessageCaptchaEnabled", "leaveMessageDisplayType", "isCommentEnabled", "isCommentCaptchaEnabled", "commentAuthority", "commentDisplayType"});
		SettingUtil.updateSetting(persistent);
		
		cacheService.flushAllPageCache(getRequest());
		jobService.buildIndexHtml();
		jobService.buildLoginHtml();
		jobService.buildRegisterAgreementHtml();
		jobService.buildAdminJs();
		jobService.buildShopJs();
		jobService.buildArticleContentHtml();
		jobService.buildGoodsContentHtml();
		
		redirectUrl = "setting!edit.action";
		return SUCCESS;
	}
	
	// 获取货币种类集合
	public List<CurrencyType> getCurrencyTypeList() {
		return Arrays.asList(CurrencyType.values());
	}
	
	// 获取小数位精确方式集合
	public List<RoundType> getRoundTypeList() {
		return Arrays.asList(RoundType.values());
	}
	
	// 获取库存预占时间点集合
	public List<StoreFreezeTime> getStoreFreezeTimeList() {
		return Arrays.asList(StoreFreezeTime.values());
	}

	// 获取水印位置集合
	public List<WatermarkPosition> getWatermarkPositionList() {
		return Arrays.asList(WatermarkPosition.values());
	}
	
	// 获取积分获取方式集合
	public List<ScoreType> getScoreTypeList() {
		return Arrays.asList(ScoreType.values());
	}
	
	// 获取运算符集合
	public List<Operator> getOperatorList() {
		return Arrays.asList(Operator.values());
	}

	public Setting getSetting() {
		return setting;
	}

	public void setSetting(Setting setting) {
		this.setting = setting;
	}

	public String getSmtpToMail() {
		return smtpToMail;
	}

	public void setSmtpToMail(String smtpToMail) {
		this.smtpToMail = smtpToMail;
	}

	public File getShopLogo() {
		return shopLogo;
	}

	public void setShopLogo(File shopLogo) {
		this.shopLogo = shopLogo;
	}

	public File getDefaultBigGoodsImage() {
		return defaultBigGoodsImage;
	}

	public void setDefaultBigGoodsImage(File defaultBigGoodsImage) {
		this.defaultBigGoodsImage = defaultBigGoodsImage;
	}

	public File getDefaultSmallGoodsImage() {
		return defaultSmallGoodsImage;
	}

	public void setDefaultSmallGoodsImage(File defaultSmallGoodsImage) {
		this.defaultSmallGoodsImage = defaultSmallGoodsImage;
	}

	public File getDefaultThumbnailGoodsImage() {
		return defaultThumbnailGoodsImage;
	}

	public void setDefaultThumbnailGoodsImage(File defaultThumbnailGoodsImage) {
		this.defaultThumbnailGoodsImage = defaultThumbnailGoodsImage;
	}

	public File getWatermarkImage() {
		return watermarkImage;
	}

	public void setWatermarkImage(File watermarkImage) {
		this.watermarkImage = watermarkImage;
	}

	public MailService getMailService() {
		return mailService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}
	
	// 获取随机数
	public int getRandom() {
		return (int) (Math.random() * 100000);
	}

}