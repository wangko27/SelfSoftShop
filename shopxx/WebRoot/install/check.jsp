<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@page import="java.io.*"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@include file="common.jsp"%>
<%
	String isAgreeAgreement = request.getParameter("isAgreeAgreement");
	if (StringUtils.equalsIgnoreCase(isAgreeAgreement, "true")) {
		boolean isShopxxXmlCanWrite = isCanWrite(realPath + "/WEB-INF/classes/shopxx.xml");
		boolean isDatabasePropertiesWrite = isCanWrite(realPath + "/WEB-INF/classes/database.properties");
		boolean isTemplateCanWrite = isCanWrite(realPath + "/WEB-INF/template/");
		boolean isUploadCanWrite = isCanWrite(realPath + "/upload/");
		boolean isHtmlCanWrite = isCanWrite(realPath + "/html/");
		
		String isShopxxXmlCanWriteInfo = null;
		if (isShopxxXmlCanWrite) {
			isShopxxXmlCanWriteInfo = "<span class=\"green\">可写</span>";
		} else {
			isShopxxXmlCanWriteInfo = "<span class=\"red\">不可写</span>";
		}
		String isDatabasePropertiesInfo = null;
		if (isDatabasePropertiesWrite) {
			isDatabasePropertiesInfo = "<span class=\"green\">可写</span>";
		} else {
			isDatabasePropertiesInfo = "<span class=\"red\">不可写</span>";
		}
		String isTemplateCanWriteInfo = null;
		if (isTemplateCanWrite) {
			isTemplateCanWriteInfo = "<span class=\"green\">可写</span>";
		} else {
			isTemplateCanWriteInfo = "<span class=\"red\">不可写</span>";
		}
		String isUploadCanWriteInfo = null;
		if (isUploadCanWrite) {
			isUploadCanWriteInfo = "<span class=\"green\">可写</span>";
		} else {
			isUploadCanWriteInfo = "<span class=\"red\">不可写</span>";
		}
		String isHtmlCanWriteInfo = null;
		if (isHtmlCanWrite) {
			isHtmlCanWriteInfo = "<span class=\"green\">可写</span>";
		} else {
			isHtmlCanWriteInfo = "<span class=\"red\">不可写</span>";
		}
		
		String canWriteInfo = "";
		if (!isShopxxXmlCanWrite || !isDatabasePropertiesWrite || !isTemplateCanWrite || !isUploadCanWrite || !isHtmlCanWrite) {
			canWriteInfo = "<span class=\"red\">&nbsp;提示: 您的安装环境有误,请配置正确的安装环境!</span>";
		}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>检查安装环境 - Powered By SHOP++</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="css/install.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery.js"></script>
<%if (canWriteInfo != "") { %>
<script type="text/javascript">
$().ready( function() {
	
	var $checkForm = $("#checkForm");
	
	$checkForm.submit( function() {
		if (confirm("您的安装环境有误,可能导致安装失败,您确定要安装吗?") == false) {
			return false;
		}
	});

});
</script>
<% } %>
</head>
<body class="install">
	<div class="header">
		<div class="title">SHOP++ 安装程序 - 检查安装环境</div>
		<div class="banner"></div>
	</div>
	<div class="body">
		<form id="checkForm" action="setting.jsp" method="post">
			<div class="bodyLeft">
				<ul>
					<li>
						<span class="icon">&nbsp;</span>许可协议
					</li>
					<li>
						<span class="icon current">&nbsp;</span>检查安装环境
					</li>
					<li>
						<span class="icon">&nbsp;</span>系统配置
					</li>
					<li>
						<span class="icon">&nbsp;</span>系统安装
					</li>
					<li>
						<span class="icon">&nbsp;</span>完成安装
					</li>
				</ul>
			</div>
			<div class="bodyRight">
				<table>
					<tr>
						<th>
							&nbsp;
						</th>
						<th>
							支持环境
						</th>
						<th>
							推荐环境
						</th>
						<th>
							当前环境
						</th>
					</tr>
					<tr>
						<td>
							<strong>操作系统:</strong>
						</td>
						<td>
							Linux/Unix/FreeBSD/Windows 2000/2003/XP ...
						</td>
						<td>
							Linux/Unix/FreeBSD
						</td>
						<td>
							<%=System.getProperty("os.name")%> (<%=System.getProperty("os.arch")%>)
						</td>
					</tr>
					<tr>
						<td>
							<strong>JDK版本:</strong>
						</td>
						<td>
							JDK 1.5+
						</td>
						<td>
							JDK 1.6
						</td>
						<td>
							<%=System.getProperty("java.version")%>
						</td>
					</tr>
					<tr>
						<td>
							<strong>WEB服务器:</strong>
						</td>
						<td>
							Tomcat
						</td>
						<td>
							Tomcat 6.0
						</td>
						<td>
							<span title="<%=application.getServerInfo()%>">
								<%=StringUtils.substring(application.getServerInfo(), 0, 25)%>
							</span>
						</td>
					</tr>
					<tr>
						<td>
							<strong>数据库:</strong>
						</td>
						<td>
							MySQL/Oracle/SQL Server
						</td>
						<td>
							MySQL 5.0
						</td>
						<td>
							-
						</td>
					</tr>
					<tr>
						<td>
							<strong>磁盘空间:</strong>
						</td>
						<td>
							30M +
						</td>
						<td>
							100M +
						</td>
						<td>
							-
						</td>
					</tr>
				</table>
				<div class="blank"></div>
				<table>
					<tr>
						<th>
							文件目录
						</th>
						<th>
							所需状态
						</th>
						<th>
							当前状态
						</th>
					</tr>
					<tr>
						<td>
							/WEB-INF/classes/shopxx.xml
						</td>
						<td>
							可写
						</td>
						<td>
							<%=isShopxxXmlCanWriteInfo%>
						</td>
					</tr>
					<tr>
						<td>
							/WEB-INF/classes/database.properties
						</td>
						<td>
							可写
						</td>
						<td>
							<%=isDatabasePropertiesInfo%>
						</td>
					</tr>
					<tr>
						<td>
							/WEB-INF/template/
						</td>
						<td>
							可写
						</td>
						<td>
							<%=isTemplateCanWriteInfo%>
						</td>
					</tr>
					<tr>
						<td>
							/upload/
						</td>
						<td>
							可写
						</td>
						<td>
							<%=isUploadCanWriteInfo%>
						</td>
					</tr>
					<tr>
						<td>
							/html/
						</td>
						<td>
							可写
						</td>
						<td>
							<%=isHtmlCanWriteInfo%>
						</td>
					</tr>
				</table>
				<div class="blank"></div>
				<%=canWriteInfo%>
			</div>
			<div class="blank"></div>
			<div class="buttonArea">
				<input type="button" class="formButton" value="上 一 步" onclick="window.location.href='index.html'" hidefocus />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="submit" class="formButton" value="下 一 步" hidefocus />
			</div>
		</form>
	</div>
	<div class="footer">
		<p class="copyright">Copyright © 2005-2011 shopxx.net All Rights Reserved.</p>
	</div>
</body>
</html>
<%
	} else {
%>
	参数错误!
<%
}
%>