<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>缺货登记<#if setting.isShowPoweredInfo> - Powered By SHOP++</#if></title>
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
	
		var $goodsNotifyForm = $("#goodsNotifyForm");
		var $goodsNotifyEmail = $("#goodsNotifyEmail");
		var $submitButton = $("#submitButton");
	
		// 表单验证
		$goodsNotifyForm.submit( function() {
			if ($.trim($goodsNotifyEmail.val()) == "") {
				$.dialog({type: "warn", content: "请输入您的邮箱!", modal: true, autoCloseTime: 3000});
				return false;
			}
			if (!/^([a-zA-Z0-9._-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/.test($goodsNotifyEmail.val())) {
				$.dialog({type: "warn", content: "E-mail格式错误!", modal: true, autoCloseTime: 3000});
				return false;
			}
			$submitButton.attr("disabled", true);
		});
	
	})
</script>
</head>
<body class="singlePage">
	<#include "/WEB-INF/template/shop/header.ftl">
	<div class="body goodsNotify">
		<div class="titleBar">
			<div class="left"></div>
			<div class="middle">
				<span class="icon">&nbsp;</span>缺货登记
			</div>
			<div class="right"></div>
		</div>
		<div class="blank"></div>
		<div class="singlePageDetail">
			<form id="goodsNotifyForm" action="${base}/shop/goods_notify!save.action" method="post">
				<input type="hidden" name="product.id" value="${product.id}" />
				<table class="inputTable">
					<tr>
						<th>商品名称: </th>
						<td>
							${product.name}
						</td>
					</tr>
					<tr>
						<th>货品编号: </th>
						<td>
							${product.productSn}
						</td>
					</tr>
					<tr>
						<th>您的邮箱: </th>
						<td>
							<input type="text" id="goodsNotifyEmail" name="goodsNotify.email" class="formText" value="${(loginMember.email)!}" />
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
						<td>
							<span class="warnIcon">&nbsp;</span>该货品暂时缺货,请输入您的E-mail,当我们有现货供应时我们会及时发送邮件通知您!
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