package net.shopxx.interceptor;

import java.util.Map;
import java.util.regex.Pattern;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 拦截器 - 安全
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司,并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前,您不能将本软件应用于商业用途,否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXCF908840E8D9DB3A559EB83A2FE5F57A
 * ============================================================================
 */

public class SecurityInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = -374803404527649448L;
	
	private static Pattern scriptPattern = Pattern.compile("script", Pattern.CASE_INSENSITIVE);

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		Map<String, Object> parameters = actionInvocation.getInvocationContext().getParameters();
		for (String key : parameters.keySet()) {
			Object value = parameters.get(key);
			if (value instanceof String[]) {
				String[] values = (String[]) value;
				for (int i = 0; i < values.length; i++) {
					values[i] = scriptPattern.matcher(values[i]).replaceAll("&#x73;cript");
					values[i] = values[i].replaceAll("<", "&lt;");
					values[i] = values[i].replaceAll(">", "&gt;");
				}
				parameters.put(key, values);
			}
		}
		return actionInvocation.invoke();
	}

}