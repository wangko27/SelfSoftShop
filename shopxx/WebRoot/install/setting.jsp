<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@page import="java.io.*"%>
<%@include file="common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>系统配置 - Powered By SHOP++</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="css/install.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
$().ready( function() {

	var $settingForm = $("#settingForm");
	var $databaseType = $("#databaseType");
	var $databaseHost = $("#databaseHost");
	var $databasePort = $("#databasePort");
	var $databaseUsername = $("#databaseUsername");
	var $databasePassword = $("#databasePassword");
	var $databaseName = $("#databaseName");
	var $tablePrefix = $("#tablePrefix");
	var $adminUsername = $("#adminUsername");
	var $adminPassword = $("#adminPassword");
	var $reAdminPassword = $("#reAdminPassword");
	var $isCreateDatabase = $("#isCreateDatabase");
	var $isInitializeDemo = $("#isInitializeDemo");
	
	$databaseType.change( function() {
		if ($databaseType.val() == "mysql") {
			$databasePort.val("3306");
			$isCreateDatabase.attr("disabled", false);
			$isCreateDatabase.attr("checked", true);
		} else if ($databaseType.val() == "sqlserver") {
			$databasePort.val("1433");
			$isCreateDatabase.attr("checked", false);
			$isCreateDatabase.attr("disabled", true);
		} else if ($databaseType.val() == "oracle") {
			$databasePort.val("1521");
			$isCreateDatabase.attr("checked", false);
			$isCreateDatabase.attr("disabled", true);
		}
	});
	
	$settingForm.submit( function() {
		if ($databaseType.val() == "") {
			alert("请选择数据库类型!");
			$databaseType.focus();
			return false;
		}
		if ($.trim($databaseHost.val()) == "") {
			alert("请填写数据库主机!");
			$databaseHost.focus();
			return false;
		}
		if ($.trim($databasePort.val()) == "") {
			alert("请填写数据库端口!");
			$databasePort.focus();
			return false;
		}
		if ($.trim($databaseUsername.val()) == "") {
			alert("请填写数据库用户名!");
			$databaseUsername.focus();
			return false;
		}
		if ($.trim($databaseName.val()) == "") {
			alert("请填写数据库名称!");
			$databaseName.focus();
			return false;
		}
		if ($.trim($tablePrefix.val()) == "") {
			alert("请填写数据库表名前缀!");
			$tablePrefix.focus();
			return false;
		}
		if ($.trim($adminUsername.val()) == "") {
			alert("请填写管理员用户名!");
			$adminUsername.focus();
			return false;
		}
		if ($.trim($adminUsername.val()).length < 2 || $.trim($adminUsername.val()).length > 20) {
			alert("管理员用户名长度必须在2-20之间!");
			$adminUsername.focus();
			return false;
		}
		if ($.trim($adminPassword.val()) == "") {
			alert("请填写管理员密码!");
			$adminPassword.focus();
			return false;
		}
		if ($.trim($adminPassword.val()).length < 4 || $.trim($adminPassword.val()).length > 20) {
			alert("管理员密码长度必须在4-20之间!");
			$adminPassword.focus();
			return false;
		}
		if ($.trim($reAdminPassword.val()) != $.trim($adminPassword.val())) {
			alert("两次管理员密码输入不正确!");
			$reAdminPassword.focus();
			return false;
		}
	});

});
</script>
</head>
<body class="install">
	<div class="header">
		<div class="title">SHOP++ 安装程序 - 系统配置</div>
		<div class="banner"></div>
	</div>
	<div class="body">
		<form id="settingForm" action="install.jsp" method="post">
			<div class="bodyLeft">
				<ul>
					<li>
						<span class="icon">&nbsp;</span>许可协议
					</li>
					<li>
						<span class="icon">&nbsp;</span>检查安装环境
					</li>
					<li>
						<span class="icon current">&nbsp;</span>系统配置
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
						<th colspan="2">
							数据库设置
						</th>
					</tr>
					<tr>
						<td width="120">
							数据库类型:
						</td>
						<td>
							<select id="databaseType" name="databaseType">
								<option value="">请选择...</option>
								<option value="mysql">MySQL</option>
								<option value="sqlserver">SQL Server</option>
								<option value="oracle">Oracle</option>
							</select>
							<label class="requireField">*</label>
						</td>
					</tr>
					<tr>
						<td width="120">
							数据库主机:
						</td>
						<td>
							<input type="text" id="databaseHost" name="databaseHost" class="formText" value="localhost" />
							<label class="requireField">*</label>
						</td>
					</tr>
					<tr>
						<td>
							数据库端口:
						</td>
						<td>
							<input type="text" id="databasePort" name="databasePort" class="formText" value="3306" />
							<label class="requireField">*</label>
						</td>
					</tr>
					<tr>
						<td>
							数据库用户名:
						</td>
						<td>
							<input type="text" id="databaseUsername" name="databaseUsername" class="formText" />
							<label class="requireField">*</label>
						</td>
					</tr>
					<tr>
						<td>
							数据库密码:
						</td>
						<td>
							<input type="password" id="databasePassword" name="databasePassword" class="formText" />
						</td>
					</tr>
					<tr>
						<td>
							数据库名称:
						</td>
						<td>
							<input type="text" id="databaseName" name="databaseName" class="formText" value="shopxx" />
							<label class="requireField">*</label>
						</td>
					</tr>
					<tr>
						<td>
							表名前缀:
						</td>
						<td>
							<input type="text" id="tablePrefix" name="tablePrefix" class="formText" value="xx_" title="非必要情况下.请保持默认值" />
							<label class="requireField">*</label>
						</td>
					</tr>
				</table>
				<div class="blank"></div>
				<table>
					<tr>
						<th colspan="2">
							管理员设置
						</th>
					</tr>
					<tr>
						<td width="120">
							用户名:
						</td>
						<td>
							<input type="text" id="adminUsername" name="adminUsername" class="formText" value="admin" />
							<label class="requireField">*</label>
						</td>
					</tr>
					<tr>
						<td>
							密码:
						</td>
						<td>
							<input type="password" id="adminPassword" name="adminPassword" class="formText" />
							<label class="requireField">*</label>
						</td>
					</tr>
					<tr>
						<td>
							重复密码:
						</td>
						<td>
							<input type="password" id="reAdminPassword" name="reAdminPassword" class="formText" />
							<label class="requireField">*</label>
						</td>
					</tr>
				</table>
				<div class="blank"></div>
				<table>
					<tr>
						<th>
							其它设置
						</th>
					</tr>
					<tr>
						<td>
							<label>
								<input type="checkbox" id="isCreateDatabase" name="isCreateDatabase" value="true" checked="checked" />自动创建数据库
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label>
								<input type="checkbox" id="isInitializeDemo" name="isInitializeDemo" value="true" checked="checked" />初始化DEMO数据
							</label>
						</td>
					</tr>
				</table>
			</div>
			<div class="blank"></div>
			<div class="buttonArea">
				<input type="button" class="formButton" value="上 一 步" onclick="window.location.href='check.jsp?isAgreeAgreement=true'" hidefocus />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="submit" class="formButton" value="立即安装" hidefocus />
			</div>
		</form>
	</div>
	<div class="footer">
		<p class="copyright">Copyright © 2005-2011 shopxx.net All Rights Reserved.</p>
	</div>
</body>
</html>