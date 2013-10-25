<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>在线留言<#if setting.isShowPoweredInfo> - Powered By SHOP++</#if></title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/shop/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/shop.css" rel="stylesheet" type="text/css" />
<!--[if lte IE 6]>
	<script type="text/javascript" src="${base}/template/common/js/belatedPNG.js"></script>
	<script type="text/javascript">
		// 解决IE6透明PNG图片BUG
		DD_belatedPNG.fix(".belatedPNG");
	</script>
<![endif]-->
<style type="text/css">
<!--
.leaveMessageItem {
	line-height: 18px;
	padding: 5px 10px;
	border: 1px solid #c7dbe5;
	background-color: #ecf2f8;
}

.leaveMessageItem .reply {
	line-height: 18px;
	padding: 5px 10px;
	margin: 3px 0px;
	border: 1px solid #c7dbe5;
	background-color: #ffffff;
}

.sendTable {
	width: 100%;
	line-height: 30px;
	border: 1px solid #c7dbe5;
}

.sendTable .title td {
	height: 30px;
	padding-left: 10px;
	text-align: left;
	font-weight: bold;
	background-color: #ecf2f8;
}

.sendTable th {
	text-align: right;
	font-weight: normal;
}

.sendTable td {
	padding: 5px;
}

.sendTable .captcha {
	width: 95px;
	margin-right: 5px;
	text-transform: uppercase;
}

.sendTable .captchaImage {
	vertical-align: middle;
	cursor: pointer;
}
-->
</style>
</head>
<body class="singlePage">
	<#include "/WEB-INF/template/shop/header.ftl">
	<div class="body leaveMessage">
		<div class="titleBar">
			<div class="left"></div>
			<div class="middle">
				<span class="icon">&nbsp;</span>在线留言
			</div>
			<div class="right"></div>
		</div>
		<div class="blank"></div>
		<div class="singlePageDetail">
			<div id="leaveMessage">
				<#list pager.result as leaveMessage>
					<div class="leaveMessageItem">
						<p>
							<span class="red">${(leaveMessage.username)!"游客"}</span> ${leaveMessage.createDate?string("yyyy-MM-dd HH: mm")}
						</p>
						<p>
							<pre>${leaveMessage.content}</pre>
						</p>
						<#list leaveMessage.replyLeaveMessageSet as replyLeaveMessage>
							<div class="reply">
								<p>
									<span class="red">管理员</span> ${replyLeaveMessage.createDate?string("yyyy-MM-dd HH: mm")}
								</p>
								<p>
									<pre>${replyLeaveMessage.content}</pre>
								</p>
							</div>
						</#list>
					</div>
					<#if leaveMessage_has_next>
						<div class="blank"></div>
					</#if>
				</#list>
				<#if (pager.result?size > 0)>
					<div class="blank"></div>
					<@pagination pager=pager baseUrl="/shop/leave_message.htm">
         				<#include "/WEB-INF/template/shop/pager.ftl">
         			</@pagination>
				<#else>
					<div class="leaveMessageItem">
						暂无留言!
					</div>
				</#if>
				<div class="blank"></div>
				<form id="leaveMessageForm" method="post">
					<table class="sendTable">
						<tr class="title">
							<td width="100">
								发布留言
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
						<tr>
							<th>
								标题: 
							</th>
							<td>
								<input type="text" id="leaveMessageTitle" name="leaveMessage.title" class="formText" />
							</td>
						</tr>
						<tr>
							<th>
								留言内容: 
							</th>
							<td>
								<textarea id="leaveMessageContent" name="leaveMessage.content" class="formTextarea"></textarea>
							</td>
						</tr>
						<tr>
							<th>
								联系方式: 
							</th>
							<td>
								<input type="text" name="leaveMessage.contact" class="formText" />
							</td>
						</tr>
						<#if setting.isLeaveMessageCaptchaEnabled>
							<tr>
			                	<th>
			                		验证码: 
			                	</th>
			                    <td>
			                    	<input type="text" id="leaveMessageCaptcha" name="j_captcha" class="formText captcha" />
			                   		<img id="leaveMessageCaptchaImage" class="captchaImage" src="${base}/captcha.jpeg" alt="换一张" />
			                    </td>
			                </tr>
		                </#if>
						<tr>
							<th>
								&nbsp;
							</th>
							<td>
								<input type="submit" class="formButton" value="提 交" />
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<div class="blank"></div>
		<#include "/WEB-INF/template/shop/friend_link.ftl">
	</div>
	<div class="blank"></div>
	<#include "/WEB-INF/template/shop/footer.ftl">
	<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
	<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
	<script type="text/javascript" src="${base}/template/shop/js/base.js"></script>
	<script type="text/javascript" src="${base}/template/shop/js/shop.js"></script>
	<script type="text/javascript">
		$().ready(function() {
		
			var $leaveMessage = $("#leaveMessage");
			var $leaveMessageForm = $("#leaveMessageForm");
			var $leaveMessageTitle = $("#leaveMessageTitle");
			var $leaveMessageContent = $("#leaveMessageContent");
			var $leaveMessageCaptcha = $("#leaveMessageCaptcha");
			var $leaveMessageCaptchaImage = $("#leaveMessageCaptchaImage");
			
			// 刷新在线留言验证码图片
			function leaveMessageCaptchaImageRefresh() {
				$leaveMessageCaptchaImage.attr("src", shopxx.base + "/captcha.jpeg?timestamp=" + (new Date()).valueOf());
			}
			
			// 刷新在线留言验证码图片
			$leaveMessageCaptchaImage.click( function() {
				leaveMessageCaptchaImageRefresh();
			});
			
			$leaveMessageForm.submit( function() {
				if ($.trim($leaveMessageTitle.val()) == "") {
					$.dialog({type: "warn", content: "请输入标题!", modal: true, autoCloseTime: 3000});
					return false;
				}
				if ($.trim($leaveMessageContent.val()) == "") {
					$.dialog({type: "warn", content: "请输入留言内容!", modal: true, autoCloseTime: 3000});
					return false;
				}
				<#if setting.isLeaveMessageCaptchaEnabled>
					if ($.trim($leaveMessageCaptcha.val()) == "") {
						$.dialog({type: "warn", content: "请输入验证码!", modal: true, autoCloseTime: 3000});
						return false;
					}
				</#if>
				
				$.ajax({
					url: "${base}/shop/leave_message!ajaxSave.action",
					data: $leaveMessageForm.serialize(),
					type: "POST",
					dataType: "json",
					cache: false,
					beforeSend: function() {
						$leaveMessageForm.find("submit").attr("disabled", true);
					},
					success: function(data) {
						$.dialog({type: data.status, content: data.message, modal: true, autoCloseTime: 3000});
						if (data.status == "success") {
							<#if setting.leaveMessageDisplayType == "direct">
								var username = getCookie("memberUsername");
								if (username == null) {
									username = "游客";
								}
								var leaveMessageItemHtml = '<div class="leaveMessageItem"><p><span class="red">' + username + '</span> ' + new Date().toLocaleDateString() + '</p><p><pre>' + htmlEscape($leaveMessageContent.val()) + '</pre></p></div><div class="blank"></div>';
								$leaveMessage.prepend(leaveMessageItemHtml);
							</#if>
							$leaveMessageContent.val("");
						}
					},
					complete: function() {
						$leaveMessageForm.find("submit").attr("disabled", false);
						$leaveMessageCaptcha.val("");
						leaveMessageCaptchaImageRefresh();
					}
				});
				return false;
			});
		
		})
	</script>
</body>
</html>