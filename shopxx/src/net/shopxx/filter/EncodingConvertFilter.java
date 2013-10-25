package net.shopxx.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 过滤器 - 编码格式转换
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

public class EncodingConvertFilter implements Filter {

	private static final String FROM_ENCODING_PARAMETER_NAME = "fromEncoding";
	private static final String TO_ENCODING_PARAMETER_NAME = "toEncoding";
	
	private String fromEncoding = "ISO-8859-1";
	private String toEncoding = "UTF-8";
	
	public void init(FilterConfig filterConfig) {
		String fromEncodingParameter = filterConfig.getInitParameter(FROM_ENCODING_PARAMETER_NAME);
		String toEncodingParameter = filterConfig.getInitParameter(TO_ENCODING_PARAMETER_NAME);
		if (fromEncodingParameter != null) {
			fromEncoding = fromEncodingParameter;
		}
		if (toEncodingParameter != null) {
			toEncoding = toEncodingParameter;
		}
	}

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		if (request.getMethod().equalsIgnoreCase("GET")) {
			Iterator iterator = request.getParameterMap().values().iterator();
			while(iterator.hasNext()) {
				String[] parames = (String[])iterator.next();
				for (int i = 0; i < parames.length; i++) {
					try {
						parames[i] = new String(parames[i].getBytes(fromEncoding), toEncoding);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
		}
		chain.doFilter(servletRequest, servletResponse);
	}

	public void destroy() {}

}