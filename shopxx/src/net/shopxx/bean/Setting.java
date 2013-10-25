package net.shopxx.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.shopxx.util.CommonUtil;

import org.apache.commons.lang.StringUtils;

/**
 * Bean类 - 系统设置
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXB9BA734EE584276939CE685A225A0330
 * ============================================================================
 */

public class Setting {
	
	// 货币种类(人民币、美元、欧元、英磅、加拿大元、澳元、卢布、港币、新台币、韩元、新加坡元、新西兰元、日元、马元、瑞士法郎、瑞典克朗、丹麦克朗、兹罗提、挪威克朗、福林、捷克克朗、葡币)
	public enum CurrencyType {
		CNY, USD, EUR, GBP, CAD, AUD, RUB, HKD, TWD, KRW, SGD, NZD, JPY, MYR, CHF, SEK, DKK, PLZ, NOK, HUF, CSK, MOP
	};
	
	// 小数位精确方式(四舍五入、向上取整、向下取整)
	public enum RoundType {
		roundHalfUp, roundUp, roundDown
	}
	
	// 库存预占时间点(下订单、订单付款、订单发货)
	public enum StoreFreezeTime {
		order, payment, ship
	}
	
	// 水印位置(无、左上、右上、居中、左下、右下)
	public enum WatermarkPosition {
		no, topLeft, topRight, center, bottomLeft, bottomRight
	}
	
	// 积分获取方式(禁用积分获取、按订单总额计算、为商品单独设置)
	public enum ScoreType {
		disable, orderAmount, goodsSet
	}
	
	// 在线客服位置(左、右)
	public enum InstantMessagingPosition {
		left, right
	}
	
	// 在线留言显示方式(立即显示、回复后显示)
	public enum LeaveMessageDisplayType {
		direct, reply
	}
	
	// 评论发表权限(任何访问者、注册会员、已购买会员)
	public enum CommentAuthority {
		anyone, member, purchased
	}
	
	// 评论显示方式(立即显示、回复后显示)
	public enum CommentDisplayType {
		direct, reply
	}
	
	// 运算符(加、减、乘、除)
	public enum Operator {
		add, subtract, multiply, divide
	}

	public static final String HOT_SEARCH_SEPARATOR = ",";// 热门搜索分隔符

	private String systemName;// 系统名称
	private String systemVersion;// 系统版本
	private String systemDescription;// 系统描述
	private String contextPath;// 虚拟路径
	private String imageUploadPath;// 图片上传路径
	private String imageBrowsePath;// 图片浏览路径
	private String adminLoginUrl;// 后台登录URL
	private String adminLoginProcessingUrl;// 后台登录处理URL
	private Boolean isShowPoweredInfo;// 是否显示Powered信息
	private String shopName;// 网店名称
	private String shopUrl;// 网店网址
	private String shopLogoPath;// 网店LOGO路径
	private String hotSearch;// 热门搜索关键词
	private String metaKeywords;// 首页页面关键词
	private String metaDescription;// 首页页面描述
	private String address;// 联系地址
	private String phone;// 联系电话
	private String zipCode;// 邮编
	private String email;// 联系email
	private String certtext;// 备案号
	private CurrencyType currencyType;// 货币种类
	private String currencySign;// 货币符号
	private String currencyUnit;// 货币单位
	private Integer priceScale;// 价格精确位数
	private RoundType priceRoundType;// 价格精确方式
	private Integer storeAlertCount;// 库存报警数量
	private StoreFreezeTime storeFreezeTime;// 库存预占时间点
	private Boolean isLoginFailureLock; // 是否开启登录失败锁定账号功能
	private Integer loginFailureLockCount;// 同一账号允许连续登录失败的最大次数,超出次数后将锁定其账号
	private Integer loginFailureLockTime;// 账号锁定时间(单位: 分钟,0表示永久锁定)
	private Boolean isRegisterEnabled;// 是否开放注册
	private String watermarkImagePath; // 水印图片路径
	private WatermarkPosition watermarkPosition; // 水印位置
	private Integer watermarkAlpha;// 水印透明度
	private Integer bigGoodsImageWidth;// 商品图片(大)宽度
	private Integer bigGoodsImageHeight;// 商品图片(大)高度
	private Integer smallGoodsImageWidth;// 商品图片(小)宽度
	private Integer smallGoodsImageHeight;// 商品图片(小)高度
	private Integer thumbnailGoodsImageWidth;// 商品缩略图宽度
	private Integer thumbnailGoodsImageHeight;// 商品缩略图高度
	private String defaultBigGoodsImagePath;// 默认商品图片(大)
	private String defaultSmallGoodsImagePath;// 默认商品图片(小)
	private String defaultThumbnailGoodsImagePath;// 默认缩略图
	private Boolean isShowMarketPrice;// 前台是否显示市场价
	private Operator defaultMarketPriceOperator;// 默认市场价运算符
	private BigDecimal defaultMarketPriceNumber;// 默认市场价运算基数
	private String smtpFromMail;// 发件人邮箱
	private String smtpHost;// SMTP服务器地址
	private Integer smtpPort;// SMTP服务器端口
	private String smtpUsername;// SMTP用户名
	private String smtpPassword;// SMTP密码
	private ScoreType scoreType;// 积分获取方式
	private Double scoreScale;// 积分换算比率
	private Integer buildHtmlDelayTime;// 生成HTML任务延时(单位: 秒)
	private Boolean isGzipEnabled;// 是否开启GZIP功能
	private Boolean isInstantMessagingEnabled;// 是否开启在线客服功能
	private InstantMessagingPosition instantMessagingPosition;// 在线客服位置
	private String instantMessagingTitle;// 在线客服标题
	private Boolean isLeaveMessageEnabled;// 是否开启在线留言功能
	private Boolean isLeaveMessageCaptchaEnabled;// 是否开启在线留言验证码功能
	private LeaveMessageDisplayType leaveMessageDisplayType;// 在线留言显示方式
	private Boolean isCommentEnabled;// 是否开启评论功能
	private Boolean isCommentCaptchaEnabled;// 是否开启评论验证码功能
	private CommentAuthority commentAuthority;// 评论发表权限
	private CommentDisplayType commentDisplayType;// 评论显示方式
	
	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getSystemVersion() {
		return systemVersion;
	}

	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}

	public String getSystemDescription() {
		return systemDescription;
	}

	public void setSystemDescription(String systemDescription) {
		this.systemDescription = systemDescription;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getImageUploadPath() {
		return imageUploadPath;
	}

	public void setImageUploadPath(String imageUploadPath) {
		this.imageUploadPath = StringUtils.removeEnd(imageUploadPath, "/");
	}
	
	public String getImageBrowsePath() {
		return imageBrowsePath;
	}

	public void setImageBrowsePath(String imageBrowsePath) {
		this.imageBrowsePath = StringUtils.removeEnd(imageBrowsePath, "/");
	}

	public String getAdminLoginUrl() {
		return adminLoginUrl;
	}

	public void setAdminLoginUrl(String adminLoginUrl) {
		this.adminLoginUrl = adminLoginUrl;
	}

	public String getAdminLoginProcessingUrl() {
		return adminLoginProcessingUrl;
	}

	public void setAdminLoginProcessingUrl(String adminLoginProcessingUrl) {
		this.adminLoginProcessingUrl = adminLoginProcessingUrl;
	}

	public Boolean getIsShowPoweredInfo() {
		return isShowPoweredInfo;
	}

	public void setIsShowPoweredInfo(Boolean isShowPoweredInfo) {
		this.isShowPoweredInfo = isShowPoweredInfo;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopUrl() {
		return shopUrl;
	}

	public void setShopUrl(String shopUrl) {
		this.shopUrl = StringUtils.removeEnd(shopUrl, "/");
	}

	public String getShopLogoPath() {
		return shopLogoPath;
	}

	public void setShopLogoPath(String shopLogoPath) {
		this.shopLogoPath = shopLogoPath;
	}

	public String getHotSearch() {
		return hotSearch;
	}

	public void setHotSearch(String hotSearch) {
		this.hotSearch = hotSearch;
	}

	public String getMetaKeywords() {
		return metaKeywords;
	}

	public void setMetaKeywords(String metaKeywords) {
		this.metaKeywords = metaKeywords;
	}

	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getCerttext() {
		return certtext;
	}

	public void setCerttext(String certtext) {
		this.certtext = certtext;
	}

	public CurrencyType getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(CurrencyType currencyType) {
		this.currencyType = currencyType;
	}

	public String getCurrencySign() {
		return currencySign;
	}

	public void setCurrencySign(String currencySign) {
		this.currencySign = currencySign;
	}

	public String getCurrencyUnit() {
		return currencyUnit;
	}

	public void setCurrencyUnit(String currencyUnit) {
		this.currencyUnit = currencyUnit;
	}

	public Integer getPriceScale() {
		return priceScale;
	}

	public void setPriceScale(Integer priceScale) {
		this.priceScale = priceScale;
	}

	public RoundType getPriceRoundType() {
		return priceRoundType;
	}

	public void setPriceRoundType(RoundType priceRoundType) {
		this.priceRoundType = priceRoundType;
	}

	public Integer getStoreAlertCount() {
		return storeAlertCount;
	}

	public void setStoreAlertCount(Integer storeAlertCount) {
		this.storeAlertCount = storeAlertCount;
	}

	public StoreFreezeTime getStoreFreezeTime() {
		return storeFreezeTime;
	}

	public void setStoreFreezeTime(StoreFreezeTime storeFreezeTime) {
		this.storeFreezeTime = storeFreezeTime;
	}

	public Boolean getIsLoginFailureLock() {
		return isLoginFailureLock;
	}

	public void setIsLoginFailureLock(Boolean isLoginFailureLock) {
		this.isLoginFailureLock = isLoginFailureLock;
	}

	public Integer getLoginFailureLockCount() {
		return loginFailureLockCount;
	}

	public void setLoginFailureLockCount(Integer loginFailureLockCount) {
		this.loginFailureLockCount = loginFailureLockCount;
	}

	public Integer getLoginFailureLockTime() {
		return loginFailureLockTime;
	}

	public void setLoginFailureLockTime(Integer loginFailureLockTime) {
		this.loginFailureLockTime = loginFailureLockTime;
	}

	public Boolean getIsRegisterEnabled() {
		return isRegisterEnabled;
	}

	public void setIsRegisterEnabled(Boolean isRegisterEnabled) {
		this.isRegisterEnabled = isRegisterEnabled;
	}

	public String getWatermarkImagePath() {
		return watermarkImagePath;
	}

	public void setWatermarkImagePath(String watermarkImagePath) {
		this.watermarkImagePath = watermarkImagePath;
	}

	public WatermarkPosition getWatermarkPosition() {
		return watermarkPosition;
	}

	public void setWatermarkPosition(WatermarkPosition watermarkPosition) {
		this.watermarkPosition = watermarkPosition;
	}

	public Integer getWatermarkAlpha() {
		return watermarkAlpha;
	}

	public void setWatermarkAlpha(Integer watermarkAlpha) {
		this.watermarkAlpha = watermarkAlpha;
	}

	public Integer getBigGoodsImageWidth() {
		return bigGoodsImageWidth;
	}

	public void setBigGoodsImageWidth(Integer bigGoodsImageWidth) {
		this.bigGoodsImageWidth = bigGoodsImageWidth;
	}

	public Integer getBigGoodsImageHeight() {
		return bigGoodsImageHeight;
	}

	public void setBigGoodsImageHeight(Integer bigGoodsImageHeight) {
		this.bigGoodsImageHeight = bigGoodsImageHeight;
	}

	public Integer getSmallGoodsImageWidth() {
		return smallGoodsImageWidth;
	}

	public void setSmallGoodsImageWidth(Integer smallGoodsImageWidth) {
		this.smallGoodsImageWidth = smallGoodsImageWidth;
	}

	public Integer getSmallGoodsImageHeight() {
		return smallGoodsImageHeight;
	}

	public void setSmallGoodsImageHeight(Integer smallGoodsImageHeight) {
		this.smallGoodsImageHeight = smallGoodsImageHeight;
	}

	public Integer getThumbnailGoodsImageWidth() {
		return thumbnailGoodsImageWidth;
	}

	public void setThumbnailGoodsImageWidth(Integer thumbnailGoodsImageWidth) {
		this.thumbnailGoodsImageWidth = thumbnailGoodsImageWidth;
	}

	public Integer getThumbnailGoodsImageHeight() {
		return thumbnailGoodsImageHeight;
	}

	public void setThumbnailGoodsImageHeight(Integer thumbnailGoodsImageHeight) {
		this.thumbnailGoodsImageHeight = thumbnailGoodsImageHeight;
	}

	public String getDefaultBigGoodsImagePath() {
		return defaultBigGoodsImagePath;
	}

	public void setDefaultBigGoodsImagePath(String defaultBigGoodsImagePath) {
		this.defaultBigGoodsImagePath = defaultBigGoodsImagePath;
	}

	public String getDefaultSmallGoodsImagePath() {
		return defaultSmallGoodsImagePath;
	}

	public void setDefaultSmallGoodsImagePath(String defaultSmallGoodsImagePath) {
		this.defaultSmallGoodsImagePath = defaultSmallGoodsImagePath;
	}

	public String getDefaultThumbnailGoodsImagePath() {
		return defaultThumbnailGoodsImagePath;
	}

	public void setDefaultThumbnailGoodsImagePath(String defaultThumbnailGoodsImagePath) {
		this.defaultThumbnailGoodsImagePath = defaultThumbnailGoodsImagePath;
	}

	public Boolean getIsShowMarketPrice() {
		return isShowMarketPrice;
	}

	public void setIsShowMarketPrice(Boolean isShowMarketPrice) {
		this.isShowMarketPrice = isShowMarketPrice;
	}

	public Operator getDefaultMarketPriceOperator() {
		return defaultMarketPriceOperator;
	}

	public void setDefaultMarketPriceOperator(Operator defaultMarketPriceOperator) {
		this.defaultMarketPriceOperator = defaultMarketPriceOperator;
	}

	public BigDecimal getDefaultMarketPriceNumber() {
		return defaultMarketPriceNumber;
	}

	public void setDefaultMarketPriceNumber(BigDecimal defaultMarketPriceNumber) {
		this.defaultMarketPriceNumber = defaultMarketPriceNumber;
	}

	public String getSmtpFromMail() {
		return smtpFromMail;
	}

	public void setSmtpFromMail(String smtpFromMail) {
		this.smtpFromMail = smtpFromMail;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public Integer getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(Integer smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getSmtpUsername() {
		return smtpUsername;
	}

	public void setSmtpUsername(String smtpUsername) {
		this.smtpUsername = smtpUsername;
	}

	public String getSmtpPassword() {
		return smtpPassword;
	}

	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}

	public ScoreType getScoreType() {
		return scoreType;
	}

	public void setScoreType(ScoreType scoreType) {
		this.scoreType = scoreType;
	}

	public Double getScoreScale() {
		return scoreScale;
	}

	public void setScoreScale(Double scoreScale) {
		this.scoreScale = scoreScale;
	}
	
	public Integer getBuildHtmlDelayTime() {
		return buildHtmlDelayTime;
	}

	public void setBuildHtmlDelayTime(Integer buildHtmlDelayTime) {
		this.buildHtmlDelayTime = buildHtmlDelayTime;
	}

	public Boolean getIsGzipEnabled() {
		return isGzipEnabled;
	}

	public void setIsGzipEnabled(Boolean isGzipEnabled) {
		this.isGzipEnabled = isGzipEnabled;
	}

	public Boolean getIsInstantMessagingEnabled() {
		return isInstantMessagingEnabled;
	}

	public void setIsInstantMessagingEnabled(Boolean isInstantMessagingEnabled) {
		this.isInstantMessagingEnabled = isInstantMessagingEnabled;
	}

	public InstantMessagingPosition getInstantMessagingPosition() {
		return instantMessagingPosition;
	}

	public void setInstantMessagingPosition(InstantMessagingPosition instantMessagingPosition) {
		this.instantMessagingPosition = instantMessagingPosition;
	}

	public String getInstantMessagingTitle() {
		return instantMessagingTitle;
	}

	public void setInstantMessagingTitle(String instantMessagingTitle) {
		this.instantMessagingTitle = instantMessagingTitle;
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
	
	// 获取热门搜索关键词集合
	public List<String> getHotSearchList() {
		return StringUtils.isNotEmpty(hotSearch) ? Arrays.asList(hotSearch.split(HOT_SEARCH_SEPARATOR)) : new ArrayList<String>();
	}
	
	// 获取图片上传真实路径
	public String getImageUploadRealPath() {
		return CommonUtil.getPreparedStatementPath(imageUploadPath);
	}

}