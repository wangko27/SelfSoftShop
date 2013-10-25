package net.shopxx.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.oscache.base.Cache;
import com.opensymphony.oscache.web.ServletCacheAdministrator;
import com.opensymphony.oscache.web.filter.CacheFilter;

/**
 * 过滤器 - 页面缓存
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXEC4D751B1DF6BF5801D73AE68A590B58
 * ============================================================================
 */

public class PageCacheFilter extends CacheFilter {

	private static final String GROUPS_PARAMETER_NAME = "groups";
	private static final String PARAMETER_KEYS_PARAMETER_NAME = "parameterKeys";
	private static final String PARAMETER_KEYS_PREFIX = "regex:";
	private static final String KEYS_SEPARATOR = "_";
	private String[] groups;
	private String[] parameterKeys;
	
	public void init(FilterConfig filterConfig) {
		super.init(filterConfig);
		String groupsParameter = filterConfig.getInitParameter(GROUPS_PARAMETER_NAME);
		if (groupsParameter != null) {
			groups = groupsParameter.split(",");
		}
		String parameterKeysParameter = filterConfig.getInitParameter(PARAMETER_KEYS_PARAMETER_NAME);
		if (parameterKeysParameter != null) {
			parameterKeys = parameterKeysParameter.split(",");
		}
	}
	
	@Override
	public String[] createCacheGroups(HttpServletRequest httpRequest, ServletCacheAdministrator scAdmin, Cache cache) {
		if (groups != null) {
			return groups;
		} else {
			return super.createCacheGroups(httpRequest, scAdmin, cache);
		}
	}

	@Override
	public String createCacheKey(HttpServletRequest httpRequest, ServletCacheAdministrator scAdmin, Cache cache) {
		if (parameterKeys != null && parameterKeys.length > 0) {
			StringBuffer keyStringBuffer = new StringBuffer(httpRequest.getRequestURI());
			Arrays.sort(parameterKeys);
			for (String parameterKey : parameterKeys) {
				if (StringUtils.isNotEmpty(parameterKey)) {
					if (StringUtils.startsWithIgnoreCase(parameterKey, PARAMETER_KEYS_PREFIX)) {
						String regexParameterKey = StringUtils.substring(parameterKey, PARAMETER_KEYS_PREFIX.length());
						String[] regexParameterKeyValues = getRegexParameter(httpRequest, regexParameterKey);
						if (regexParameterKeyValues != null && regexParameterKeyValues.length > 0) {
							Arrays.sort(regexParameterKeyValues);
							for (String regexParameterKeyValue : regexParameterKeyValues) {
								if (StringUtils.isNotEmpty(regexParameterKeyValue)) {
									keyStringBuffer.append(KEYS_SEPARATOR);
									keyStringBuffer.append(regexParameterKey);
									keyStringBuffer.append(KEYS_SEPARATOR);
									keyStringBuffer.append(regexParameterKeyValue);
								}
							}
						}
					} else {
						String parameterKeyValue = httpRequest.getParameter(parameterKey);
						if (StringUtils.isNotEmpty(parameterKeyValue)) {
							keyStringBuffer.append(KEYS_SEPARATOR);
							keyStringBuffer.append(parameterKey);
							keyStringBuffer.append(KEYS_SEPARATOR);
							keyStringBuffer.append(parameterKeyValue);
						}
					}
				}
			}
			return keyStringBuffer.toString();
		} else {
			return super.createCacheKey(httpRequest, scAdmin, cache);
		}
	}
	
	@SuppressWarnings("unchecked")
	private String[] getRegexParameter(HttpServletRequest httpRequest, String parameterKey) {
		List<String> regexParameterList = new ArrayList<String>();
		Pattern pattern = Pattern.compile(parameterKey);
		Enumeration<String> parameterNames = httpRequest.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String parameterName = parameterNames.nextElement();
			Matcher matcher = pattern.matcher(parameterName);
			while (matcher.find()) {
				regexParameterList.add(httpRequest.getParameter(matcher.group()));
			}
		}
		return regexParameterList.toArray(new String[regexParameterList.size()]);
	}

}