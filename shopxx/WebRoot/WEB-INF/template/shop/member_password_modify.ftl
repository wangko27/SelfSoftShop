<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>密码修改<#if setting.isShowPoweredInfo> - Powered By SHOP++</#if></title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/shop/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/shop.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/template/shop/js/base.js"></script>
<script type="text/javascript" src="${base}/template/shop/js/shop.js"></script>
<script type="text/javascript">
$().ready( function() {
	
	var $passwordModifyForm = $("#passwordModifyForm");
	var $memberPassword = $("#memberPassword");
	var $reMemberPassword = $("#reMemberPassword");
	var $submitButton = $("#submitButton");
	
	$passwordModifyForm.submit( function() {
		if ($.trim($memberPassword.val()) == "") {
			$memberPassword.focus();
			$.dialog({type: "warn", content: "请输入新密码!", modal: true, autoCloseTime: 3000});
			return false;
		}
		if ($.trim($reMemberPassword.val()) == "") {
			$reMemberPassword.focus();
			$.dialog({type: "warn", content: "请输入确认新密码!", modal: true, autoCloseTime: 3000});
			return false;
		}
		if ($.trim($memberPassword.val()) != $.trim($reMemberPassword.val())) {
			$reMemberPassword.focus();
			$.dialog({type: "warn", content: "两次密码输入不相同!", modal: true, autoCloseTime: 3000});
			return false;
		}
		$submitButton.attr("disabled", true);
	});

});
</script>
<!--[if lte IE 6]>
	<script type="text/javascript" src="${base}/template/common/js/belatedPNG.js"></script>
	<script type="text/javascript">
		// 解决IE6透明PNG图片BUG
		DD_belatedPNG.fix(".belatedPNG");
	</script>
<![endif]-->
</head>
<body class="singlePage">
	<#include "/WEB-INF/template/shop/header.ftl">
	<div class="body passwordRecover">
		<div class="titleBar">
			<div class="left"></div>
			<div class="middle">
				<span class="icon">&nbsp;</span>密码修改
			</div>
			<div class="right"></div>
		</div>
		<div class="blank"></div>
		<div class="singlePageDetail">
			<form id="passwordModifyForm" action="${base}/shop/member!passwordUpdate.action" method="post">
				<input type="hidden" name="id" value="${id}" />
				<input type="hidden" name="passwordRecoverKey" value="${passwordRecoverKey}" />
				<table class="inputTable">
					<tr>
						<th>新密码: </th>
						<td>
							<input type="password" id="memberPassword" name="member.password" class="formText" />
						</td>
					</tr>
					<tr>
						<th>确认新密码: </th>
						<td>
							<input type="password" id="reMemberPassword" name="rePassword" class="formText" />
						</td>
					</tr>
					<tr>
						<th>&nbsp;</th>
						<td>
							<input type="submit" id="submitButton" class="formButton" value="确  定" hidefocus />
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="blank"></div>
		<#include "/WEB-INF/template/shop/friend_link.ftl">
	</div>
	<div class="blank"></div>
	<#include "/WEB-INF/template/shop/footer.ftl">
</body>
</html>