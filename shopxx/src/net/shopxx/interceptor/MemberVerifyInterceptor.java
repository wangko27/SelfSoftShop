package net.shopxx.interceptor;

import javax.servlet.http.HttpServletRequest;

import net.shopxx.entity.Member;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.opensymphony.xwork2.ognl.OgnlValueStack;

/**
 * 拦截器 - 判断会员是否登录
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

public class MemberVerifyInterceptor extends MethodFilterInterceptor {

	private static final long serialVersionUID = -86246303854807787L;
	
	@Override
	public String doIntercept(ActionInvocation actionInvocation) throws Exception {
		String loginMemberId = (String) actionInvocation.getInvocationContext().getSession().get(Member.MEMBER_ID_SESSION_NAME);
		if (loginMemberId == null) {
			HttpServletRequest request = ServletActionContext.getRequest();
			String loginRedirectUrl = request.getServletPath();
			String queryString = request.getQueryString();
			if (StringUtils.isNotEmpty(queryString)) {
				loginRedirectUrl += "?" + queryString;
			}
			
			OgnlValueStack ognlValueStack = (OgnlValueStack)request.getAttribute("struts.valueStack");
			ognlValueStack.set("loginRedirectUrl", loginRedirectUrl);
			return "login";
		}
		return actionInvocation.invoke();
	}

}