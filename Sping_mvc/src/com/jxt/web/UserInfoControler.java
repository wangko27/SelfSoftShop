package com.jxt.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jxt.domain.UserInfo;
import com.jxt.service.UserInfoService;

/**
 * 
 * @author alxu
 * 
 */
@Controller
@RequestMapping("/userInfo/*")
public class UserInfoControler {
	@Resource
	private UserInfoService userInfoService;

	/**
	 * login
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request,HttpServletResponse response) {
		
		String userName = (String) request.getAttribute("username");
		String password = (String) request.getAttribute("password");
		
		return "index";
	}
}
