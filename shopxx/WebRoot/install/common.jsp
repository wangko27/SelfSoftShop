<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@page import="java.io.*"%>
<%
	response.setHeader("progma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setDateHeader("Expires", 0);
%>
<%!
	public static final String INSTALL_LOCK_CONFIG_PATH = "/install/install_lock.conf";
	public static final String BUILD_HTML_CONFIG_PATH = "/build_html.conf";
	public static final String DATABASE_PROPERTIES_PATH = "/database.properties";
	public static final String SHOPXX_XML_PATH = "/shopxx.xml";
	public static final String BACKUP_WEB_XML_PATH = "/WEB-INF/backup_web.xml";
	public static final String WEB_XML_PATH = "/WEB-INF/web.xml";
	public static final String TABLE_PREFIX_SIGN = "{shopxx_table_prefix}";
	public static final String TABLE_ENGINE_SIGN = "{shopxx_engine}";
	public static final String ADMIN_USERNAME_SIGN = "{shopxx_admin_username}";
	public static final String ADMIN_PASSWORD_SIGN = "{shopxx_admin_password}";
	public static final int MYSQL_TABLE_NAME_MAX_LENGTH = 64;
	public static final int SQLSERVER_TABLE_NAME_MAX_LENGTH = 128;
	public static final int ORACLE_TABLE_NAME_MAX_LENGTH = 30;
	public static final String MYSQL_ENGINE = "Auto";// Auto、MyISAM、InnoDB

	String stackToString(Exception exception) {
		try {
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			exception.printStackTrace(printWriter);
			return stringWriter.toString().replaceAll("\\r?\\n", "</br>");
		} catch (Exception e) {
			return "stackToString error";
		}
	}
	
	public boolean isCanWrite(String dirPath) {
		File file = new File(dirPath);
		if(!file.exists()) {
			file.mkdir();
		}
		if(file.canWrite()) {
			return true;
		} else{
			return false;
		}
	}
%>
<%
	String realPath = application.getRealPath("/");
	File installLockConfigFile = new File(realPath + INSTALL_LOCK_CONFIG_PATH);
	if(installLockConfigFile.exists()) {
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>系统提示 - Powered By SHOP++</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="css/install.css" rel="stylesheet" type="text/css" />
</head>
<body class="install">
	<p>
		&nbsp;&nbsp;<a href="../index.html">点击进入首页</a>
	</p>
	<p>&nbsp;&nbsp;您无此访问权限,若您需要重新安装SHOP++程序,请删除/install/install_lock.conf文件!</p>
	<p class="red">&nbsp;&nbsp;基于安全考虑,请在安装成功后删除install目录</p>
</body>
</html>
<%
		return;
	}
%>