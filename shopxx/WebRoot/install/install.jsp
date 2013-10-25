<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@page import="java.io.*"%>
<%@page import="java.sql.*"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@include file="common.jsp"%>
<%
	String databaseType = StringUtils.trim(request.getParameter("databaseType"));
	String databaseHost = StringUtils.trim(request.getParameter("databaseHost"));
	String databasePort = StringUtils.trim(request.getParameter("databasePort"));
	String databaseUsername = StringUtils.trim(request.getParameter("databaseUsername"));
	String databasePassword = StringUtils.trim(request.getParameter("databasePassword"));
	String databaseName = StringUtils.trim(request.getParameter("databaseName"));
	String tablePrefix = StringUtils.trim(request.getParameter("tablePrefix"));
	String adminUsername = StringUtils.trim(request.getParameter("adminUsername"));
	String adminPassword = StringUtils.trim(request.getParameter("adminPassword"));
	String isCreateDatabase = StringUtils.trim(request.getParameter("isCreateDatabase"));
	String isInitializeDemo = StringUtils.trim(request.getParameter("isInitializeDemo"));
	
	if (StringUtils.equalsIgnoreCase(isCreateDatabase, "true")) {
		isCreateDatabase = "true";
	} else {
		isCreateDatabase = "false";
	}
	
	if (StringUtils.equalsIgnoreCase(isInitializeDemo, "true")) {
		isInitializeDemo = "true";
	} else {
		isInitializeDemo = "false";
	}
	
	String status = "success";
	String message = "";
	String exception = "";
	
	if (StringUtils.isEmpty(databaseType)) {
		status = "error";
		message = "数据库类型不允许为空!";
	} else if (StringUtils.isEmpty(databaseHost)) {
		status = "error";
		message = "数据库主机不允许为空!";
	} else if (StringUtils.isEmpty(databasePort)) {
		status = "error";
		message = "数据库端口不允许为空!";
	} else if (StringUtils.isEmpty(databaseUsername)) {
		status = "error";
		message = "数据库用户名不允许为空!";
	} else if (StringUtils.isEmpty(databaseName)) {
		status = "error";
		message = "数据库名称不允许为空!";
	} else if (StringUtils.isEmpty(tablePrefix)) {
		status = "error";
		message = "数据库表前缀不允许为空!";
	} else if (StringUtils.isEmpty(adminUsername)) {
		status = "error";
		message = "管理员用户名不允许为空!";
	} else if (adminUsername.length() < 2 || adminUsername.length() > 20) {
		status = "error";
		message = "管理员用户名长度必须在2-20之间!";
	} else if (StringUtils.isEmpty(adminPassword)) {
		status = "error";
		message = "管理员密码不允许为空!";
	} else if (adminPassword.length() < 4 || adminPassword.length() > 40) {
		status = "error";
		message = "管理员密码长度必须在4-20之间!";
	}
	
	if (status.equals("success")) {
		session.setAttribute("databaseType", databaseType);
		session.setAttribute("databaseHost", databaseHost);
		session.setAttribute("databasePort", databasePort);
		session.setAttribute("databaseUsername", databaseUsername);
		session.setAttribute("databasePassword", databasePassword);
		session.setAttribute("databaseName", databaseName);
		session.setAttribute("tablePrefix", tablePrefix);
		session.setAttribute("adminUsername", adminUsername);
		session.setAttribute("adminPassword", adminPassword);
		session.setAttribute("isCreateDatabase", isCreateDatabase);
		session.setAttribute("isInitializeDemo", isInitializeDemo);
	}
	
	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;
	
	if (status.equals("success")) {
		if (databaseType.equalsIgnoreCase("mysql")) {
			String jdbcUrl = "jdbc:mysql://" + databaseHost + ":" + databasePort + "/" + databaseName + "?useUnicode=true&characterEncoding=UTF-8";
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (Exception e) {
				status = "error";
				message = "MySQL JDBC驱动加载失败,请检查JDBC驱动!";
				exception = stackToString(e);
			}
			
			if (status.equals("success")) {
				if (StringUtils.equals(isCreateDatabase, "true")) {
					try {
						connection = DriverManager.getConnection(jdbcUrl, databaseUsername, databasePassword);
					} catch (SQLException e) {
						status = "error";
						message = "数据库连接失败,请检查数据库用户名、密码等配置信息!";
						exception = stackToString(e);
					}
					
					if (connection == null) {
						try {
							connection = DriverManager.getConnection("jdbc:mysql://" + databaseHost + ":" + databasePort + "/mysql?useUnicode=true&characterEncoding=UTF-8", databaseUsername, databasePassword);
						} catch (SQLException e) {}
					}
					
					if (connection == null) {
						try {
							connection = DriverManager.getConnection("jdbc:mysql://" + databaseHost + ":" + databasePort + "/test?useUnicode=true&characterEncoding=UTF-8", databaseUsername, databasePassword);
						} catch (SQLException e) {}
					}
					
					if (connection != null) {
						status = "success";
						message = "";
						exception = "";
						
						statement = connection.createStatement();
						String mysqlVersion = null;
						try {
							resultSet = statement.executeQuery("select version()");
							resultSet.next();
							mysqlVersion = resultSet.getString(1);
							resultSet.close();
						} catch (SQLException e0) {
							status = "error";
							message = "获取MySQL数据库版本失败!";
							exception = stackToString(e0);
							try {
								if(resultSet != null) {
									resultSet.close();
									resultSet = null;
								}
								if(statement != null) {
									statement.close();
									statement = null;
								}
								if(connection != null) {
									connection.close();
									connection = null;
								}
							} catch (SQLException e1) {
								status = "error";
								message = "获取MySQL数据库版本失败!";
								exception = stackToString(e1);
							}
						}
						
						if (status.equals("success")) {
							try {
								if (mysqlVersion.compareTo("4.1") > 0) {
									statement.executeUpdate("create database if not exists `" + databaseName + "` default character set utf8");
								} else {
									statement.executeUpdate("create database if not exists `" + databaseName + "`");
								}
							} catch (SQLException e) {
								status = "error";
								message = "数据库创建失败!";
								exception = stackToString(e);
							} finally {
								try {
									if(resultSet != null) {
										resultSet.close();
										resultSet = null;
									}
									if(statement != null) {
										statement.close();
										statement = null;
									}
									if(connection != null) {
										connection.close();
										connection = null;
									}
								} catch (SQLException e) {
									status = "error";
									message = "数据库创建失败!";
									exception = stackToString(e);
								}
							}
						}
					}
				} else {
					try {
						connection = DriverManager.getConnection(jdbcUrl, databaseUsername, databasePassword);
					} catch (SQLException e) {
						status = "error";
						message = "数据库连接失败,请检查数据库用户名、密码等配置信息!";
						exception = stackToString(e);
					}
				}
			}
		} else if (databaseType.equalsIgnoreCase("sqlserver")) {
			String jdbcUrl = "jdbc:sqlserver://" + databaseHost + ":" + databasePort + ";databasename=" + databaseName;
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			} catch (Exception e) {
				status = "error";
				message = "SQL Server JDBC驱动加载失败,请检查JDBC驱动!";
				exception = stackToString(e);
			}
			if (status.equals("success")) {
				try {
					connection = DriverManager.getConnection(jdbcUrl, databaseUsername, databasePassword);
				} catch (SQLException e) {
					status = "error";
					message = "数据库连接失败,请检查数据库用户名、密码等配置信息!";
					exception = stackToString(e);
				}
			}
		} else if (databaseType.equalsIgnoreCase("oracle")) {
			String jdbcUrl = "jdbc:oracle:thin:@" + databaseHost + ":" + databasePort + ":" + databaseName;
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (Exception e) {
				status = "error";
				message = "Oracle JDBC驱动加载失败,请检查JDBC驱动!";
				exception = stackToString(e);
			}
			
			if (status.equals("success")) {
				try {
					connection = DriverManager.getConnection(jdbcUrl, databaseUsername, databasePassword);
				} catch (SQLException e) {
					status = "error";
					message = "数据库连接失败,请检查数据库用户名、密码等配置信息!";
					exception = stackToString(e);
				}
			}
		}
	}
	
	String homeUrl = null;
	String adminUrl = null;
	if (request.getServerPort() == 80) {
		homeUrl = request.getScheme() + "://" + request.getServerName() + request.getContextPath();
		adminUrl = request.getScheme() + "://" + request.getServerName() + request.getContextPath() + "/admin";
	} else {
		homeUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		adminUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/admin";
	}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>SHOP++安装程序 - Powered By SHOP++</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="css/install.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery.js"></script>
</head>
<body class="install">
	<div class="header">
		<div class="title">SHOP++ 安装程序</div>
		<div class="banner"></div>
	</div>
	<div class="body">
		<div class="bodyLeft">
			<ul>
				<li>
					<span class="icon">&nbsp;</span>许可协议
				</li>
				<li>
					<span class="icon">&nbsp;</span>检查安装环境
				</li>
				<li>
					<span class="icon">&nbsp;</span>系统配置
				</li>
				<li>
					<span id="installIcon" class="icon current">&nbsp;</span>系统安装
				</li>
				<li>
					<span id="completeIcon" class="icon">&nbsp;</span>完成安装
				</li>
			</ul>
		</div>
		<div class="bodyRight">
			<%
				if (StringUtils.equals(status, "error")) {
			%>
			<table>
				<tr>
					<th>
						系统提示
					</th>
				</tr>
				<tr>
					<td>
						<strong class="message"><%=message%></strong>
					</td>
				</tr>
			</table>
			<div class="blank"></div>
			<table>
				<tr>
					<th>
						异常信息
					</th>
				</tr>
				<tr>
					<td style="padding: 0px;">
						<div class="exception">
							<%=exception%>
						</div>
					</td>
				</tr>
			</table>
			<div class="blank"></div>
			<%
				} else {
			%>
			<table>
				<tr>
					<th>
						安装提示
					</th>
				</tr>
				<tr>
					<td>
						<div class="blank"></div>
						<div id="installMessage">正在检测数据库环境...</div>
						<div class="blank"></div>
						<div id="installLoadingBar" class="loadingBar"></div>
						<div class="blank"></div>
						<div id="installUrl" class="installUrl">
							前台访问地址: <a href="<%=homeUrl%>/index.html" target="_blank"><%=homeUrl%></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							后台管理地址: <a href="<%=adminUrl%>" target="_blank"><%=adminUrl%></a>
						</div>
						<div class="blank"></div>
					</td>
				</tr>
			</table>
			<div class="blank"></div>
			<table id="installExceptionTable" style="display: none;">
				<tr>
					<th>
						异常信息
					</th>
				</tr>
				<tr>
					<td style="padding: 0px;">
						<div id="installException" class="exception">
							<%=exception%>
						</div>
					</td>
				</tr>
			</table>
			<%
				}
			%>
		</div>
		<div class="blank"></div>
		<div class="buttonArea">
			<input type="button" class="formButton" value="上 一 步" onclick="window.location.href='setting.jsp'" hidefocus />&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" id="completeButton" class="formButton" value="完成安装" disabled hidefocus />
		</div>
	</div>
	<div class="footer">
		<p class="copyright">Copyright © 2005-2011 shopxx.net All Rights Reserved.</p>
	</div>
	<%
		if (StringUtils.equals(status, "success")) {
	%>
	<script type="text/javascript">
		// 解决IE6不缓存背景图片问题
		if(!window.XMLHttpRequest) {
			document.execCommand("BackgroundImageCache", false, true);
		}
		
		$().ready(function() {
		
			var $installIcon = $("#installIcon");
			var $completeIcon = $("#completeIcon");
			var $installMessage = $("#installMessage");
			var $installLoadingBar = $("#installLoadingBar");
			var $installExceptionTable = $("#installExceptionTable");
			var $installException = $("#installException");
			var $installUrl = $("#installUrl");
			var $completeButton = $("#completeButton");
			
			installInit();
			
			function installInit() {
				$.ajax({
					type: "POST",
					cache: false,
					url: "install_init.jsp",
					dataType: "json",
					beforeSend: function(data) {
						$installMessage.html("正在初始化数据库结构...");
					},
					success: function(data) {
						if (data.status == "success") {
							installDemo();
						} else {
							$installLoadingBar.hide();
							$installMessage.html(data.message);
							$installException.html(data.exception);
							$installExceptionTable.show();
						}
					}
				});
			}
			
			function installDemo() {
				<%
					if (isInitializeDemo.equals("true")) {
				%>
				$.ajax({
					type: "POST",
					cache: false,
					url: "install_demo.jsp",
					dataType: "json",
					beforeSend: function(data) {
						$installMessage.html("正在初始化DEMO数据...");
					},
					success: function(data) {
						if (data.status == "success") {
							installConfig();
						} else {
							$installLoadingBar.hide();
							$installMessage.html(data.message);
							$installException.html(data.exception);
							$installExceptionTable.show();
						}
					}
				});
				<%
					} else {
				%>
				installConfig();
				<%
					}
				%>
			}
			
			function installConfig() {
				$.ajax({
					type: "POST",
					cache: false,
					url: "install_config.jsp",
					dataType: "json",
					beforeSend: function(data) {
						$installMessage.html("正在初始化系统配置...");
					},
					success: function(data) {
						if (data.status == "success") {
							$installIcon.removeClass("current");
							$completeIcon.addClass("current");
							$installLoadingBar.hide();
							$completeButton.attr("disabled", false);
							$installMessage.html('<div id="installSuccess" class="installSuccess"><strong>恭喜您,SHOP++系统安装成功,请重新启动WEB服务器!</strong><span>基于安全考虑,请在安装成功后删除install目录</span></div>');
							$("#installSuccess").fadeIn(2000);
							$installUrl.fadeIn(2000);
						} else {
							$installLoadingBar.hide();
							$installMessage.html(data.message);
							$installException.html(data.exception);
							$installExceptionTable.show();
						}
					}
				});
			}
			
			$completeButton.click( function() {
				if ($completeButton.attr("disabled") == false) {
					alert("恭喜您,SHOP++系统安装成功,请重新启动WEB服务器!");
				}
				return false;
			});
			
		})
	</script>
	<%
		}
	%>
</body>
</html>