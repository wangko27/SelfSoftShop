package net.shopxx.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 工具类 - 公用
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX9A02B310F538BFE58B8F04583E341B6E
 * ============================================================================
 */

public class CommonUtil {
	
	public static final String WEB_APP_ROOT_KEY = "shopxx.webAppRoot";// WebRoot路径KEY
	public static final String PATH_PREPARED_STATEMENT_UUID = "\\{uuid\\}";// UUID路径占位符
	public static final String PATH_PREPARED_STATEMENT_DATE = "\\{date(\\(\\w+\\))?\\}";// 日期路径占位符
	
	/**
	 * 获取WebRoot路径
	 * 
	 * @return WebRoot路径
	 */
	public static String getWebRootPath() {
		return System.getProperty(WEB_APP_ROOT_KEY);
	}

	/**
	 * 随机获取UUID字符串(无中划线)
	 * 
	 * @return UUID字符串
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24);
	}
	
	/**
	 * 获取实际路径
	 * 
	 * @param path
	 *            路径
	 */
	public static String getPreparedStatementPath(String path) {
		if (StringUtils.isEmpty(path)) {
			return null;
		}
		StringBuffer uuidStringBuffer = new StringBuffer();
		Matcher uuidMatcher = Pattern.compile(PATH_PREPARED_STATEMENT_UUID).matcher(path);
		while(uuidMatcher.find()) {
			uuidMatcher.appendReplacement(uuidStringBuffer, CommonUtil.getUUID());
		}
		uuidMatcher.appendTail(uuidStringBuffer);
		
		StringBuffer dateStringBuffer = new StringBuffer();
		Matcher dateMatcher = Pattern.compile(PATH_PREPARED_STATEMENT_DATE).matcher(uuidStringBuffer.toString());
		while(dateMatcher.find()) {
			String dateFormate = "yyyyMM";
			Matcher dateFormatMatcher = Pattern.compile("\\(\\w+\\)").matcher(dateMatcher.group());
			if (dateFormatMatcher.find()) {
				String dateFormatMatcherGroup = dateFormatMatcher.group();
				dateFormate = dateFormatMatcherGroup.substring(1, dateFormatMatcherGroup.length() - 1);
			}
			dateMatcher.appendReplacement(dateStringBuffer, new SimpleDateFormat(dateFormate).format(new Date()));
		}
		dateMatcher.appendTail(dateStringBuffer);
		
		return dateStringBuffer.toString();
	}

}