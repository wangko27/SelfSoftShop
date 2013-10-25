<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>密码找回<#if setting.isShowPoweredInfo> - Powered By SHOP++</#if></title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/shop/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/shop.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/template/shop/js/base.js"></script>
<script type="text/javascript" src="${base}/template/shop/js/shop.js"></script>
<!--[if lte IE 6]>
	<script type="text/javascript" src="${base}/template/common/js/belatedPNG.js"></script>
	<script type="text/javascript">
		// 解决IE6透明PNG图片BUG
		DD_belatedPNG.fix(".belatedPNG");
	</script>
<![endif]-->
<script type="text/javascript">
	$().ready(function() {
	
		var $passwordRecoverForm = $("#passwordRecoverForm");
		var $memberUsername = $("#memberUsername");
		var $memberEmail = $("#memberEmail");
		
		var $memberSafeQuestionTr = $("#memberSafeQuestionTr");
		var $memberSafeQuestion = $("#memberSafeQuestion");
		var $memberSafeAnswerTr = $("#memberSafeAnswerTr");
		var $memberSafeAnswer = $("#memberSafeAnswer");
		var $submitButton = $("#submitButton");
		var $status = $("#status");
	
		$passwordRecoverForm.submit( function() {
			if ($.trim($memberUsername.val()) == "") {
				$.dialog({type: "warn", content: "请输入用户名!", modal: true, autoCloseTime: 3000});
				return false;
			}
			if ($.trim($memberEmail.val()) == "") {
				$.dialog({type: "warn", content: "请输入E-mail!", modal: true, autoCloseTime: 3000});
				return false;
			}
			if (!/^([a-zA-Z0-9._-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/.test($memberEmail.val())) {
				$.dialog({type: "warn", content: "E-mail格式错误!", modal: true, autoCloseTime: 3000});
				return false;
			}
			if ($memberSafeAnswer.attr("disabled") == false && $.trim($memberSafeAnswer.val()) == "") {
				$.dialog({type: "warn", content: "请输入密码保护答案!", modal: true, autoCloseTime: 3000});
				return false;
			}
			
			$.ajax({
				url: "member!ajaxSendPasswordRecoverMail.action",
				data: $passwordRecoverForm.serialize(),
				type: "POST",
				dataType: "json",
				cache: false,
				beforeSend: function(data) {
					$status.html('<span class="loadingIcon">&nbsp;</span>正在检测密码保护状态,请稍后...');
					$submitButton.attr("disabled", true);
				},
				success: function(data) {
					if (data.status == "warn") {
						$memberSafeQuestion.text(data.safeQuestion);
						$memberSafeQuestionTr.show();
						$memberSafeAnswer.attr("disabled", false);
						$memberSafeAnswerTr.show();
						$status.text(data.message);
					} else if (data.status == "success") {
						$memberUsername.val("");
						$memberSafeAnswer.val("");
						$memberEmail.val("");
						$memberSafeQuestionTr.hide();
						$memberSafeAnswer.attr("disabled", true);
						$memberSafeAnswerTr.hide();
						$status.text(data.message);
						$.dialog({type: data.status, content: data.message, modal: true, autoCloseTime: 3000});
					} else {
						$status.text(data.message);
						$.dialog({type: data.status, content: data.message, modal: true, autoCloseTime: 3000});
					}
					$submitButton.attr("disabled", false);
				}
			});
			return false;
		});
		
		$memberUsername.change( function() {
			$memberSafeAnswer.val("");
			$memberSafeQuestionTr.hide();
			$memberSafeAnswer.attr("disabled", true);
			$memberSafeAnswerTr.hide();
			$status.text("");
		});
	
	})
</script>
</head>
<body class="singlePage">
	<#include "/WEB-INF/template/shop/header.ftl">
	<div class="body passwordRecover">
		<div class="titleBar">
			<div class="left"></div>
			<div class="middle">
				<span class="icon">&nbsp;</span>找回密码
			</div>
			<div class="right"></div>
		</div>
		<div class="blank"></div>
		<div class="singlePageDetail">
			<form id="passwordRecoverForm">
				<table class="inputTable">
					<tr>
						<th>用户名: </th>
						<td>
							<input type="text" id="memberUsername" name="member.username" class="formText" />
						</td>
					</tr>
					<tr>
						<th>E-mail: </th>
						<td>
							<input type="text" id="memberEmail" name="member.email" class="formText" />
						</td>
					</tr>
					<tr id="memberSafeQuestionTr" class="hidden">
						<th>密码保护问题: </th>
						<td>
							<span id="memberSafeQuestion"></span>
						</td>
					</tr>
					<tr id="memberSafeAnswerTr" class="hidden">
						<th>密码保护答案: </th>
						<td>
							<input type="text" id="memberSafeAnswer" name="member.safeAnswer" class="formText" disabled />
						</td>
					</tr>
					<tr>
						<th>&nbsp;</th>
						<td>
							<input type="submit" id="submitButton" class="formButton" value="确  定" hidefocus />
						</td>
					</tr>
					<tr>
						<th>&nbsp;</th>
						<td height="20">
							<span class="gray"><span id="status"></span></span>&nbsp;
						</td>
					</tr>
					<tr>
						<th>&nbsp;</th>
						<td>
							<span class="warnIcon">&nbsp;</span>如果忘记密码,请填写您的用户名和注册邮箱重新获取密码!
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