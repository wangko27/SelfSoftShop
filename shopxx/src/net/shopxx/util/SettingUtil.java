package net.shopxx.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import net.shopxx.bean.Setting;
import net.shopxx.bean.Setting.CommentAuthority;
import net.shopxx.bean.Setting.CommentDisplayType;
import net.shopxx.bean.Setting.CurrencyType;
import net.shopxx.bean.Setting.InstantMessagingPosition;
import net.shopxx.bean.Setting.LeaveMessageDisplayType;
import net.shopxx.bean.Setting.Operator;
import net.shopxx.bean.Setting.RoundType;
import net.shopxx.bean.Setting.ScoreType;
import net.shopxx.bean.Setting.StoreFreezeTime;
import net.shopxx.bean.Setting.WatermarkPosition;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.core.io.ClassPathResource;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

/**
 * 工具类 - 系统配置
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX6A15A4BF2AF0A65F46FF20145368F1F4
 * ============================================================================
 */

public class SettingUtil {
	
	private static final String CACHE_MANAGER_BEAN_NAME = "cacheManager";// cacheManager Bean名称
	private static final String SHOPXX_XML_FILE_NAME = "shopxx.xml";// shopxx.xml配置文件名称
	private static final String SETTING_CACHE_KEY = "SHOPXX_SETTING";// Setting缓存Key
	
	/**
	 * 读取系统配置信息
	 * 
	 * @return Setting
	 * 
	 * @throws URISyntaxException 
	 * 
	 * @throws DocumentException 
	 * 
	 * @throws IOException 
	 */
	public static Setting readSetting() throws URISyntaxException, DocumentException, IOException {
		File shopxxXmlFile = new ClassPathResource(SHOPXX_XML_FILE_NAME).getFile();
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(shopxxXmlFile);
		Node systemNameNode = document.selectSingleNode("/shopxx/setting/systemName");
		Node systemVersionNode = document.selectSingleNode("/shopxx/setting/systemVersion");
		Node systemDescriptionNode = document.selectSingleNode("/shopxx/setting/systemDescription");
		Node contextPathNode = document.selectSingleNode("/shopxx/setting/contextPath");
		Node imageUploadPathNode = document.selectSingleNode("/shopxx/setting/imageUploadPath");
		Node imageBrowsePathNode = document.selectSingleNode("/shopxx/setting/imageBrowsePath");
		Node adminLoginUrlNode = document.selectSingleNode("/shopxx/setting/adminLoginUrl");
		Node adminLoginProcessingUrlNode = document.selectSingleNode("/shopxx/setting/adminLoginProcessingUrl");
		Node isShowPoweredInfoNode = document.selectSingleNode("/shopxx/setting/isShowPoweredInfo");
		Node shopNameNode = document.selectSingleNode("/shopxx/setting/shopName");
		Node shopUrlNode = document.selectSingleNode("/shopxx/setting/shopUrl");
		Node shopLogoPathNode = document.selectSingleNode("/shopxx/setting/shopLogoPath");
		Node hotSearchNode = document.selectSingleNode("/shopxx/setting/hotSearch");
		Node metaKeywordsNode = document.selectSingleNode("/shopxx/setting/metaKeywords");
		Node metaDescriptionNode = document.selectSingleNode("/shopxx/setting/metaDescription");
		Node addressNode = document.selectSingleNode("/shopxx/setting/address");
		Node phoneNode = document.selectSingleNode("/shopxx/setting/phone");
		Node zipCodeNode = document.selectSingleNode("/shopxx/setting/zipCode");
		Node emailNode = document.selectSingleNode("/shopxx/setting/email");
		Node certtextNode = document.selectSingleNode("/shopxx/setting/certtext");
		Node currencyTypeNode = document.selectSingleNode("/shopxx/setting/currencyType");
		Node currencySignNode = document.selectSingleNode("/shopxx/setting/currencySign");
		Node currencyUnitNode = document.selectSingleNode("/shopxx/setting/currencyUnit");
		Node priceScaleNode = document.selectSingleNode("/shopxx/setting/priceScale");
		Node priceRoundTypeNode = document.selectSingleNode("/shopxx/setting/priceRoundType");
		Node storeAlertCountNode = document.selectSingleNode("/shopxx/setting/storeAlertCount");
		Node storeFreezeTimeNode = document.selectSingleNode("/shopxx/setting/storeFreezeTime");
		Node isLoginFailureLockNode = document.selectSingleNode("/shopxx/setting/isLoginFailureLock");
		Node loginFailureLockCountNode = document.selectSingleNode("/shopxx/setting/loginFailureLockCount");
		Node loginFailureLockTimeNode = document.selectSingleNode("/shopxx/setting/loginFailureLockTime");
		Node isRegisterEnabledNode = document.selectSingleNode("/shopxx/setting/isRegisterEnabled");
		Node watermarkImagePathNode = document.selectSingleNode("/shopxx/setting/watermarkImagePath");
		Node watermarkPositionNode = document.selectSingleNode("/shopxx/setting/watermarkPosition");
		Node watermarkAlphaNode = document.selectSingleNode("/shopxx/setting/watermarkAlpha");
		Node bigGoodsImageWidthNode = document.selectSingleNode("/shopxx/setting/bigGoodsImageWidth");
		Node bigGoodsImageHeightNode = document.selectSingleNode("/shopxx/setting/bigGoodsImageHeight");
		Node smallGoodsImageWidthNode = document.selectSingleNode("/shopxx/setting/smallGoodsImageWidth");
		Node smallGoodsImageHeightNode = document.selectSingleNode("/shopxx/setting/smallGoodsImageHeight");
		Node thumbnailGoodsImageWidthNode = document.selectSingleNode("/shopxx/setting/thumbnailGoodsImageWidth");
		Node thumbnailGoodsImageHeightNode = document.selectSingleNode("/shopxx/setting/thumbnailGoodsImageHeight");
		Node defaultBigGoodsImagePathNode = document.selectSingleNode("/shopxx/setting/defaultBigGoodsImagePath");
		Node defaultSmallGoodsImagePathNode = document.selectSingleNode("/shopxx/setting/defaultSmallGoodsImagePath");
		Node defaultThumbnailGoodsImagePathNode = document.selectSingleNode("/shopxx/setting/defaultThumbnailGoodsImagePath");
		Node isShowMarketPriceNode = document.selectSingleNode("/shopxx/setting/isShowMarketPrice");
		Node defaultMarketPriceOperatorNode = document.selectSingleNode("/shopxx/setting/defaultMarketPriceOperator");
		Node defaultMarketPriceNumberNode = document.selectSingleNode("/shopxx/setting/defaultMarketPriceNumber");
		Node smtpFromMailNode = document.selectSingleNode("/shopxx/setting/smtpFromMail");
		Node smtpHostNode = document.selectSingleNode("/shopxx/setting/smtpHost");
		Node smtpPortNode = document.selectSingleNode("/shopxx/setting/smtpPort");
		Node smtpUsernameNode = document.selectSingleNode("/shopxx/setting/smtpUsername");
		Node smtpPasswordNode = document.selectSingleNode("/shopxx/setting/smtpPassword");
		Node scoreTypeNode = document.selectSingleNode("/shopxx/setting/scoreType");
		Node scoreScaleNode = document.selectSingleNode("/shopxx/setting/scoreScale");
		Node isGzipEnabledNode = document.selectSingleNode("/shopxx/setting/isGzipEnabled");
		Node buildHtmlDelayTimeNode = document.selectSingleNode("/shopxx/setting/buildHtmlDelayTime");
		Node isInstantMessagingEnabledNode = document.selectSingleNode("/shopxx/setting/isInstantMessagingEnabled");
		Node instantMessagingPositionNode = document.selectSingleNode("/shopxx/setting/instantMessagingPosition");
		Node instantMessagingTitleNode = document.selectSingleNode("/shopxx/setting/instantMessagingTitle");
		Node isLeaveMessageEnabledNode = document.selectSingleNode("/shopxx/setting/isLeaveMessageEnabled");
		Node isLeaveMessageCaptchaEnabledNode = document.selectSingleNode("/shopxx/setting/isLeaveMessageCaptchaEnabled");
		Node leaveMessageDisplayTypeNode = document.selectSingleNode("/shopxx/setting/leaveMessageDisplayType");
		Node isCommentEnabledNode = document.selectSingleNode("/shopxx/setting/isCommentEnabled");
		Node isCommentCaptchaEnabledNode = document.selectSingleNode("/shopxx/setting/isCommentCaptchaEnabled");
		Node commentAuthorityNode = document.selectSingleNode("/shopxx/setting/commentAuthority");
		Node commentDisplayTypeNode = document.selectSingleNode("/shopxx/setting/commentDisplayType");
		
		Setting setting = new Setting();
		setting.setSystemName(systemNameNode.getText());
		setting.setSystemVersion(systemVersionNode.getText());
		setting.setSystemDescription(systemDescriptionNode.getText());
		setting.setContextPath(contextPathNode.getText());
		setting.setImageUploadPath(imageUploadPathNode.getText());
		setting.setImageBrowsePath(imageBrowsePathNode.getText());
		setting.setAdminLoginUrl(adminLoginUrlNode.getText());
		setting.setAdminLoginProcessingUrl(adminLoginProcessingUrlNode.getText());
		setting.setIsShowPoweredInfo(Boolean.valueOf(isShowPoweredInfoNode.getText()));
		setting.setShopName(shopNameNode.getText());
		setting.setShopUrl(shopUrlNode.getText());
		setting.setShopLogoPath(shopLogoPathNode.getText());
		setting.setHotSearch(hotSearchNode.getText());
		setting.setMetaKeywords(metaKeywordsNode.getText());
		setting.setMetaDescription(metaDescriptionNode.getText());
		setting.setAddress(addressNode.getText());
		setting.setPhone(phoneNode.getText());
		setting.setZipCode(zipCodeNode.getText());
		setting.setEmail(emailNode.getText());
		setting.setCerttext(certtextNode.getText());
		setting.setCurrencyType(CurrencyType.valueOf(currencyTypeNode.getText()));
		setting.setCurrencySign(currencySignNode.getText());
		setting.setCurrencyUnit(currencyUnitNode.getText());
		setting.setPriceScale(Integer.valueOf(priceScaleNode.getText()));
		setting.setPriceRoundType(RoundType.valueOf(priceRoundTypeNode.getText()));
		setting.setStoreAlertCount(Integer.valueOf(storeAlertCountNode.getText()));
		setting.setStoreFreezeTime(StoreFreezeTime.valueOf(storeFreezeTimeNode.getText()));
		setting.setIsLoginFailureLock(Boolean.valueOf(isLoginFailureLockNode.getText()));
		setting.setLoginFailureLockCount(Integer.valueOf(loginFailureLockCountNode.getText()));
		setting.setLoginFailureLockTime(Integer.valueOf(loginFailureLockTimeNode.getText()));
		setting.setIsRegisterEnabled(Boolean.valueOf(isRegisterEnabledNode.getText()));
		setting.setWatermarkImagePath(watermarkImagePathNode.getText());
		setting.setWatermarkPosition(WatermarkPosition.valueOf(watermarkPositionNode.getText()));
		setting.setWatermarkAlpha(Integer.valueOf(watermarkAlphaNode.getText()));
		setting.setBigGoodsImageWidth(Integer.valueOf(bigGoodsImageWidthNode.getText()));
		setting.setBigGoodsImageHeight(Integer.valueOf(bigGoodsImageHeightNode.getText()));
		setting.setSmallGoodsImageWidth(Integer.valueOf(smallGoodsImageWidthNode.getText()));
		setting.setSmallGoodsImageHeight(Integer.valueOf(smallGoodsImageHeightNode.getText()));
		setting.setThumbnailGoodsImageWidth(Integer.valueOf(thumbnailGoodsImageWidthNode.getText()));
		setting.setThumbnailGoodsImageHeight(Integer.valueOf(thumbnailGoodsImageHeightNode.getText()));
		setting.setDefaultBigGoodsImagePath(defaultBigGoodsImagePathNode.getText());
		setting.setDefaultSmallGoodsImagePath(defaultSmallGoodsImagePathNode.getText());
		setting.setDefaultThumbnailGoodsImagePath(defaultThumbnailGoodsImagePathNode.getText());
		setting.setIsShowMarketPrice(Boolean.valueOf(isShowMarketPriceNode.getText()));
		setting.setDefaultMarketPriceOperator(Operator.valueOf(defaultMarketPriceOperatorNode.getText()));
		setting.setDefaultMarketPriceNumber(BigDecimal.valueOf(Double.valueOf(defaultMarketPriceNumberNode.getText())));
		setting.setSmtpFromMail(smtpFromMailNode.getText());
		setting.setSmtpHost(smtpHostNode.getText());
		setting.setSmtpPort(Integer.valueOf(smtpPortNode.getText()));
		setting.setSmtpUsername(smtpUsernameNode.getText());
		setting.setSmtpPassword(smtpPasswordNode.getText());
		setting.setScoreType(ScoreType.valueOf(scoreTypeNode.getText()));
		setting.setScoreScale(Double.valueOf(scoreScaleNode.getText()));
		setting.setBuildHtmlDelayTime(Integer.valueOf(buildHtmlDelayTimeNode.getText()));
		setting.setIsGzipEnabled(Boolean.valueOf(isGzipEnabledNode.getText()));
		setting.setIsInstantMessagingEnabled(Boolean.valueOf(isInstantMessagingEnabledNode.getText()));
		setting.setInstantMessagingPosition(InstantMessagingPosition.valueOf(instantMessagingPositionNode.getText()));
		setting.setInstantMessagingTitle(instantMessagingTitleNode.getText());
		setting.setIsLeaveMessageEnabled(Boolean.valueOf(isLeaveMessageEnabledNode.getText()));
		setting.setIsLeaveMessageCaptchaEnabled(Boolean.valueOf(isLeaveMessageCaptchaEnabledNode.getText()));
		setting.setLeaveMessageDisplayType(LeaveMessageDisplayType.valueOf(leaveMessageDisplayTypeNode.getText()));
		setting.setIsCommentEnabled(Boolean.valueOf(isCommentEnabledNode.getText()));
		setting.setIsCommentCaptchaEnabled(Boolean.valueOf(isCommentCaptchaEnabledNode.getText()));
		setting.setCommentAuthority(CommentAuthority.valueOf(commentAuthorityNode.getText()));
		setting.setCommentDisplayType(CommentDisplayType.valueOf(commentDisplayTypeNode.getText()));
		
		return setting;
	}
	
	/**
	 * 获取系统配置信息
	 * 
	 * @return Setting
	 */
	public static Setting getSetting() {
		Setting setting = null;
		GeneralCacheAdministrator generalCacheAdministrator = (GeneralCacheAdministrator) SpringUtil.getBean(CACHE_MANAGER_BEAN_NAME);
		try {
			setting = (Setting) generalCacheAdministrator.getFromCache(SETTING_CACHE_KEY);
		} catch (NeedsRefreshException needsRefreshException) {
			boolean updateSucceeded = false;
			try {
				setting = readSetting();
				generalCacheAdministrator.putInCache(SETTING_CACHE_KEY, setting);
				updateSucceeded = true;
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
	            if (!updateSucceeded) {
	            	generalCacheAdministrator.cancelUpdate(SETTING_CACHE_KEY);
	            }
	        }
		}
		return setting;
	}
	
	/**
	 * 写入系统配置信息
	 * 
	 * @return Setting
	 */
	public static void writeSetting(Setting setting) {
		File shopxxXmlFile = null;
		Document document = null;
		try {
			shopxxXmlFile = new ClassPathResource(SHOPXX_XML_FILE_NAME).getFile();
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(shopxxXmlFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Element rootElement = document.getRootElement();
		Element settingElement = rootElement.element("setting");
		Node shopNameNode = document.selectSingleNode("/shopxx/setting/shopName");
		Node shopUrlNode = document.selectSingleNode("/shopxx/setting/shopUrl");
		Node hotSearchNode = document.selectSingleNode("/shopxx/setting/hotSearch");
		Node metaKeywordsNode = document.selectSingleNode("/shopxx/setting/metaKeywords");
		Node metaDescriptionNode = document.selectSingleNode("/shopxx/setting/metaDescription");
		Node addressNode = document.selectSingleNode("/shopxx/setting/address");
		Node phoneNode = document.selectSingleNode("/shopxx/setting/phone");
		Node zipCodeNode = document.selectSingleNode("/shopxx/setting/zipCode");
		Node emailNode = document.selectSingleNode("/shopxx/setting/email");
		Node certtextNode = document.selectSingleNode("/shopxx/setting/certtext");
		Node currencyTypeNode = document.selectSingleNode("/shopxx/setting/currencyType");
		Node currencySignNode = document.selectSingleNode("/shopxx/setting/currencySign");
		Node currencyUnitNode = document.selectSingleNode("/shopxx/setting/currencyUnit");
		Node priceScaleNode = document.selectSingleNode("/shopxx/setting/priceScale");
		Node priceRoundTypeNode = document.selectSingleNode("/shopxx/setting/priceRoundType");
		Node storeAlertCountNode = document.selectSingleNode("/shopxx/setting/storeAlertCount");
		Node storeFreezeTimeNode = document.selectSingleNode("/shopxx/setting/storeFreezeTime");
		Node isLoginFailureLockNode = document.selectSingleNode("/shopxx/setting/isLoginFailureLock");
		Node loginFailureLockCountNode = document.selectSingleNode("/shopxx/setting/loginFailureLockCount");
		Node loginFailureLockTimeNode = document.selectSingleNode("/shopxx/setting/loginFailureLockTime");
		Node isRegisterEnabledNode = document.selectSingleNode("/shopxx/setting/isRegisterEnabled");
		Node watermarkPositionNode = document.selectSingleNode("/shopxx/setting/watermarkPosition");
		Node watermarkAlphaNode = document.selectSingleNode("/shopxx/setting/watermarkAlpha");
		Node bigGoodsImageWidthNode = document.selectSingleNode("/shopxx/setting/bigGoodsImageWidth");
		Node bigGoodsImageHeightNode = document.selectSingleNode("/shopxx/setting/bigGoodsImageHeight");
		Node smallGoodsImageWidthNode = document.selectSingleNode("/shopxx/setting/smallGoodsImageWidth");
		Node smallGoodsImageHeightNode = document.selectSingleNode("/shopxx/setting/smallGoodsImageHeight");
		Node thumbnailGoodsImageWidthNode = document.selectSingleNode("/shopxx/setting/thumbnailGoodsImageWidth");
		Node thumbnailGoodsImageHeightNode = document.selectSingleNode("/shopxx/setting/thumbnailGoodsImageHeight");
		Node isShowMarketPriceNode = document.selectSingleNode("/shopxx/setting/isShowMarketPrice");
		Node defaultMarketPriceOperatorNode = document.selectSingleNode("/shopxx/setting/defaultMarketPriceOperator");
		Node defaultMarketPriceNumberNode = document.selectSingleNode("/shopxx/setting/defaultMarketPriceNumber");
		Node smtpFromMailNode = document.selectSingleNode("/shopxx/setting/smtpFromMail");
		Node smtpHostNode = document.selectSingleNode("/shopxx/setting/smtpHost");
		Node smtpPortNode = document.selectSingleNode("/shopxx/setting/smtpPort");
		Node smtpUsernameNode = document.selectSingleNode("/shopxx/setting/smtpUsername");
		Node smtpPasswordNode = document.selectSingleNode("/shopxx/setting/smtpPassword");
		Node scoreTypeNode = document.selectSingleNode("/shopxx/setting/scoreType");
		Node scoreScaleNode = document.selectSingleNode("/shopxx/setting/scoreScale");
		Node buildHtmlDelayTimeNode = document.selectSingleNode("/shopxx/setting/buildHtmlDelayTime");
		Node isGzipEnabledNode = document.selectSingleNode("/shopxx/setting/isGzipEnabled");
		Node isInstantMessagingEnabledNode = document.selectSingleNode("/shopxx/setting/isInstantMessagingEnabled");
		Node instantMessagingPositionNode = document.selectSingleNode("/shopxx/setting/instantMessagingPosition");
		Node instantMessagingTitleNode = document.selectSingleNode("/shopxx/setting/instantMessagingTitle");
		Node isLeaveMessageEnabledNode = document.selectSingleNode("/shopxx/setting/isLeaveMessageEnabled");
		Node isLeaveMessageCaptchaEnabledNode = document.selectSingleNode("/shopxx/setting/isLeaveMessageCaptchaEnabled");
		Node leaveMessageDisplayTypeNode = document.selectSingleNode("/shopxx/setting/leaveMessageDisplayType");
		Node isCommentEnabledNode = document.selectSingleNode("/shopxx/setting/isCommentEnabled");
		Node isCommentCaptchaEnabledNode = document.selectSingleNode("/shopxx/setting/isCommentCaptchaEnabled");
		Node commentAuthorityNode = document.selectSingleNode("/shopxx/setting/commentAuthority");
		Node commentDisplayTypeNode = document.selectSingleNode("/shopxx/setting/commentDisplayType");
		
		if(shopNameNode == null){
			shopNameNode = settingElement.addElement("shopName");
		}
		if(shopUrlNode == null){
			shopUrlNode = settingElement.addElement("shopUrl");
		}
		if(hotSearchNode == null){
			hotSearchNode = settingElement.addElement("hotSearch");
		}
		if(metaKeywordsNode == null){
			metaKeywordsNode = settingElement.addElement("metaKeywords");
		}
		if(metaDescriptionNode == null){
			metaDescriptionNode = settingElement.addElement("metaDescription");
		}
		if(addressNode == null){
			addressNode = settingElement.addElement("address");
		}
		if(phoneNode == null){
			phoneNode = settingElement.addElement("phone");
		}
		if(zipCodeNode == null){
			zipCodeNode = settingElement.addElement("zipCode");
		}
		if(emailNode == null){
			emailNode = settingElement.addElement("email");
		}
		if(certtextNode == null){
			certtextNode = settingElement.addElement("certtext");
		}
		if(currencyTypeNode == null){
			currencyTypeNode = settingElement.addElement("currencyType");
		}
		if(currencySignNode == null){
			currencySignNode = settingElement.addElement("currencySign");
		}
		if(currencyUnitNode == null){
			currencyUnitNode = settingElement.addElement("currencyUnit");
		}
		if(priceScaleNode == null){
			priceScaleNode = settingElement.addElement("priceScale");
		}
		if(priceRoundTypeNode == null){
			priceRoundTypeNode = settingElement.addElement("priceRoundType");
		}
		if(storeAlertCountNode == null){
			storeAlertCountNode = settingElement.addElement("storeAlertCount");
		}
		if(storeFreezeTimeNode == null){
			storeFreezeTimeNode = settingElement.addElement("storeFreezeTime");
		}
		if(isLoginFailureLockNode == null){
			isLoginFailureLockNode = settingElement.addElement("isLoginFailureLock");
		}
		if(loginFailureLockCountNode == null){
			loginFailureLockCountNode = settingElement.addElement("loginFailureLockCount");
		}
		if(loginFailureLockTimeNode == null){
			loginFailureLockTimeNode = settingElement.addElement("loginFailureLockTime");
		}
		if(isRegisterEnabledNode == null){
			isRegisterEnabledNode = settingElement.addElement("isRegisterEnabled");
		}
		if(watermarkPositionNode == null){
			watermarkPositionNode = settingElement.addElement("watermarkPosition");
		}
		if(watermarkAlphaNode == null){
			watermarkAlphaNode = settingElement.addElement("watermarkAlpha");
		}
		if(bigGoodsImageWidthNode == null){
			bigGoodsImageWidthNode = settingElement.addElement("bigGoodsImageWidth");
		}
		if(bigGoodsImageHeightNode == null){
			bigGoodsImageHeightNode = settingElement.addElement("bigGoodsImageHeight");
		}
		if(smallGoodsImageWidthNode == null){
			smallGoodsImageWidthNode = settingElement.addElement("smallGoodsImageWidth");
		}
		if(smallGoodsImageHeightNode == null){
			smallGoodsImageHeightNode = settingElement.addElement("smallGoodsImageHeight");
		}
		if(thumbnailGoodsImageWidthNode == null){
			thumbnailGoodsImageWidthNode = settingElement.addElement("thumbnailGoodsImageWidth");
		}
		if(thumbnailGoodsImageHeightNode == null){
			thumbnailGoodsImageHeightNode = settingElement.addElement("thumbnailGoodsImageHeight");
		}
		if(isShowMarketPriceNode == null){
			isShowMarketPriceNode = settingElement.addElement("isShowMarketPrice");
		}
		if(defaultMarketPriceOperatorNode == null){
			defaultMarketPriceOperatorNode = settingElement.addElement("defaultMarketPriceOperator");
		}
		if(defaultMarketPriceNumberNode == null){
			defaultMarketPriceNumberNode = settingElement.addElement("defaultMarketPriceNumber");
		}
		if(smtpFromMailNode == null){
			smtpFromMailNode = settingElement.addElement("smtpFromMail");
		}
		if(smtpHostNode == null){
			smtpHostNode = settingElement.addElement("smtpHost");
		}
		if(smtpPortNode == null){
			smtpPortNode = settingElement.addElement("smtpPort");
		}
		if(smtpUsernameNode == null){
			smtpUsernameNode = settingElement.addElement("smtpUsername");
		}
		if(smtpPasswordNode == null){
			smtpPasswordNode = settingElement.addElement("smtpPassword");
		}
		if(scoreTypeNode == null){
			scoreTypeNode = settingElement.addElement("scoreType");
		}
		if(scoreScaleNode == null){
			scoreScaleNode = settingElement.addElement("scoreScale");
		}
		if(buildHtmlDelayTimeNode == null){
			buildHtmlDelayTimeNode = settingElement.addElement("buildHtmlDelayTime");
		}
		if(isGzipEnabledNode == null){
			isGzipEnabledNode = settingElement.addElement("isGzipEnabled");
		}
		if(isInstantMessagingEnabledNode == null){
			isInstantMessagingEnabledNode = settingElement.addElement("isInstantMessagingEnabled");
		}
		if(instantMessagingPositionNode == null){
			instantMessagingPositionNode = settingElement.addElement("instantMessagingPosition");
		}
		if(instantMessagingTitleNode == null){
			instantMessagingTitleNode = settingElement.addElement("instantMessagingTitle");
		}
		if(isLeaveMessageEnabledNode == null){
			isLeaveMessageEnabledNode = settingElement.addElement("isLeaveMessageEnabled");
		}
		if(isLeaveMessageCaptchaEnabledNode == null){
			isLeaveMessageCaptchaEnabledNode = settingElement.addElement("isLeaveMessageCaptchaEnabled");
		}
		if(leaveMessageDisplayTypeNode == null){
			leaveMessageDisplayTypeNode = settingElement.addElement("leaveMessageDisplayType");
		}
		if(isCommentEnabledNode == null){
			isCommentEnabledNode = settingElement.addElement("isCommentEnabled");
		}
		if(isCommentCaptchaEnabledNode == null){
			isCommentCaptchaEnabledNode = settingElement.addElement("isCommentCaptchaEnabled");
		}
		if(commentAuthorityNode == null){
			commentAuthorityNode = settingElement.addElement("commentAuthority");
		}
		if(commentDisplayTypeNode == null){
			commentDisplayTypeNode = settingElement.addElement("commentDisplayType");
		}
		
		shopNameNode.setText(setting.getShopName());
		shopUrlNode.setText(setting.getShopUrl());
		hotSearchNode.setText(setting.getHotSearch());
		metaKeywordsNode.setText(setting.getMetaKeywords());
		metaDescriptionNode.setText(setting.getMetaDescription());
		addressNode.setText(setting.getAddress());
		phoneNode.setText(setting.getPhone());
		zipCodeNode.setText(setting.getZipCode());
		emailNode.setText(setting.getEmail());
		certtextNode.setText(setting.getCerttext());
		currencyTypeNode.setText(String.valueOf(setting.getCurrencyType()));
		currencySignNode.setText(setting.getCurrencySign());
		currencyUnitNode.setText(setting.getCurrencyUnit());
		priceScaleNode.setText(String.valueOf(setting.getPriceScale()));
		priceRoundTypeNode.setText(String.valueOf(setting.getPriceRoundType()));
		storeAlertCountNode.setText(String.valueOf(setting.getStoreAlertCount()));
		storeFreezeTimeNode.setText(String.valueOf(setting.getStoreFreezeTime()));
		isLoginFailureLockNode.setText(String.valueOf(setting.getIsLoginFailureLock()));
		loginFailureLockCountNode.setText(String.valueOf(setting.getLoginFailureLockCount()));
		loginFailureLockTimeNode.setText(String.valueOf(setting.getLoginFailureLockTime()));
		isRegisterEnabledNode.setText(String.valueOf(setting.getIsRegisterEnabled()));
		watermarkPositionNode.setText(String.valueOf(setting.getWatermarkPosition()));
		watermarkAlphaNode.setText(String.valueOf(setting.getWatermarkAlpha()));
		bigGoodsImageWidthNode.setText(String.valueOf(setting.getBigGoodsImageWidth()));
		bigGoodsImageHeightNode.setText(String.valueOf(setting.getBigGoodsImageHeight()));
		smallGoodsImageWidthNode.setText(String.valueOf(setting.getSmallGoodsImageWidth()));
		smallGoodsImageHeightNode.setText(String.valueOf(setting.getSmallGoodsImageHeight()));
		thumbnailGoodsImageWidthNode.setText(String.valueOf(setting.getThumbnailGoodsImageWidth()));
		thumbnailGoodsImageHeightNode.setText(String.valueOf(setting.getThumbnailGoodsImageHeight()));
		isShowMarketPriceNode.setText(String.valueOf(setting.getIsShowMarketPrice()));
		defaultMarketPriceOperatorNode.setText(String.valueOf(setting.getDefaultMarketPriceOperator()));
		defaultMarketPriceNumberNode.setText(String.valueOf(setting.getDefaultMarketPriceNumber()));
		smtpFromMailNode.setText(setting.getSmtpFromMail());
		smtpHostNode.setText(setting.getSmtpHost());
		smtpPortNode.setText(String.valueOf(setting.getSmtpPort()));
		smtpUsernameNode.setText(setting.getSmtpUsername());
		smtpPasswordNode.setText(setting.getSmtpPassword());
		scoreTypeNode.setText(setting.getScoreType().toString());
		scoreScaleNode.setText(setting.getScoreScale().toString());
		buildHtmlDelayTimeNode.setText(setting.getBuildHtmlDelayTime().toString());
		isGzipEnabledNode.setText(setting.getIsGzipEnabled().toString());
		isInstantMessagingEnabledNode.setText(setting.getIsInstantMessagingEnabled().toString());
		instantMessagingPositionNode.setText(setting.getInstantMessagingPosition().toString());
		instantMessagingTitleNode.setText(setting.getInstantMessagingTitle());
		isLeaveMessageEnabledNode.setText(setting.getIsLeaveMessageEnabled().toString());
		isLeaveMessageCaptchaEnabledNode.setText(setting.getIsLeaveMessageCaptchaEnabled().toString());
		leaveMessageDisplayTypeNode.setText(setting.getLeaveMessageDisplayType().toString());
		isCommentEnabledNode.setText(setting.getIsCommentEnabled().toString());
		isCommentCaptchaEnabledNode.setText(setting.getIsCommentCaptchaEnabled().toString());
		commentAuthorityNode.setText(setting.getCommentAuthority().toString());
		commentDisplayTypeNode.setText(setting.getCommentDisplayType().toString());
		
		try {
			OutputFormat outputFormat = OutputFormat.createPrettyPrint();// 设置XML文档输出格式
			outputFormat.setEncoding("UTF-8");// 设置XML文档的编码类型
			outputFormat.setIndent(true);// 设置是否缩进
			outputFormat.setIndent("	");// 以TAB方式实现缩进
			outputFormat.setNewlines(true);// 设置是否换行
			XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(shopxxXmlFile), outputFormat);
			xmlWriter.write(document);
			xmlWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新系统配置信息
	 * 
	 * @param setting
	 */
	public static void updateSetting(Setting setting) {
		writeSetting(setting);
		GeneralCacheAdministrator generalCacheAdministrator = (GeneralCacheAdministrator) SpringUtil.getBean(CACHE_MANAGER_BEAN_NAME);
		generalCacheAdministrator.flushEntry(SETTING_CACHE_KEY);
	}
	
	/**
	 * 刷新系统配置信息
	 * 
	 */
	public void flush() {
		GeneralCacheAdministrator generalCacheAdministrator = (GeneralCacheAdministrator) SpringUtil.getBean(CACHE_MANAGER_BEAN_NAME);
		generalCacheAdministrator.flushEntry(SETTING_CACHE_KEY);
	}
	
	/**
	 * 设置价格精确位数
	 * 
	 */
	public static BigDecimal setPriceScale(BigDecimal price) {
		if (price != null) {
			Integer priceScale = getSetting().getPriceScale();
			RoundType priceRoundType = getSetting().getPriceRoundType();
			if (priceRoundType == RoundType.roundHalfUp) {
				return price.setScale(priceScale, BigDecimal.ROUND_HALF_UP);
			} else if (priceRoundType == RoundType.roundUp) {
				return price.setScale(priceScale, BigDecimal.ROUND_UP);
			} else {
				return price.setScale(priceScale, BigDecimal.ROUND_DOWN);
			}
		}
		return null;
	}
	
	/**
	 * 获取货币格式字符串
	 * 
	 */
	public static String getCurrencyFormat() {
		Integer priceScale = getSetting().getPriceScale();
		String currencySign = getSetting().getCurrencySign();
		String currencyUnit = getSetting().getCurrencyUnit();
		if (priceScale == 0) {
			return "'" + currencySign + "'#0'" + currencyUnit + "'";
		} else if (priceScale == 1) {
			return "'" + currencySign + "'#0.0'" + currencyUnit + "'";
		} else if (priceScale == 2) {
			return "'" + currencySign + "'#0.00'" + currencyUnit + "'";
		} else if (priceScale == 3) {
			return "'" + currencySign + "'#0.000'" + currencyUnit + "'";
		} else if (priceScale == 4) {
			return "'" + currencySign + "'#0.0000'" + currencyUnit + "'";
		} else {
			return "'" + currencySign + "'#0.00000'" + currencyUnit + "'";
		}
	}
	
	/**
	 * 获取货币格式化
	 * 
	 */
	public static String currencyFormat(BigDecimal price) {
		String currencyFormat = getCurrencyFormat();
		NumberFormat numberFormat = new DecimalFormat(currencyFormat);
		return numberFormat.format(price);
	}
	
	/**
	 * 根据销售价获取默认市场价
	 * 
	 */
	public static BigDecimal getDefaultMarketPrice(BigDecimal price) {
		Setting setting = getSetting();
		Operator defaultMarketPriceOperator = setting.getDefaultMarketPriceOperator();
		BigDecimal defaultMarketPriceNumber = setting.getDefaultMarketPriceNumber();
		if (defaultMarketPriceOperator == Operator.add) {
			return price.add(defaultMarketPriceNumber);
		} else if (defaultMarketPriceOperator == Operator.subtract) {
			return price.subtract(defaultMarketPriceNumber);
		} else if (defaultMarketPriceOperator == Operator.multiply) {
			return price.multiply(defaultMarketPriceNumber);
		} else if (defaultMarketPriceOperator == Operator.divide) {
			return price.divide(defaultMarketPriceNumber, 5, BigDecimal.ROUND_HALF_UP);
		}
		return null;
	}

}