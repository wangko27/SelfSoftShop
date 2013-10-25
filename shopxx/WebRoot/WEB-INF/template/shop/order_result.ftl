<#assign s=JspTaglibs["/WEB-INF/tld/struts.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>订单结果<#if setting.isShowPoweredInfo> - Powered By SHOP++</#if></title>
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
</head>
<body class="orderResult">
	<#include "/WEB-INF/template/shop/header.ftl">
	<div class="body">
		<div class="blank"></div>
		<div class="orderResultDetail">
			<div class="message"><span class="icon">&nbsp;</span>&nbsp;&nbsp;您的订单已经成功提交!</div>
			<div class="blank"></div>
			<table class="listTable">
				<tr>
					<th colspan="2">订单信息</th>
				</tr>
				<tr>
					<td class="title">订单编号</td>
					<td>
						${order.orderSn}
						<a href="${base}/shop/order!view.action?id=${order.id}">[查看订单详情]</a>
					</td>
				</tr>
				<tr>
					<td class="title">订单总金额</td>
					<td><span class="red">${order.totalAmount?string(currencyFormat)}</span></td>
				</tr>
			</table>
			<div class="blank"></div>
			<table class="listTable">
				<tr>
					<th colspan="2">配送信息</th>
				</tr>
				<tr>
					<td class="title">配送方式</td>
					<td>${order.deliveryTypeName}</td>
				</tr>
			</table>
			<#if order.paymentConfig??>
				<form action="${base}/shop/payment!submit.action" method="post">
					<@s.token />
					<#if paymentConfig.paymentConfigType == "deposit">
						<input type="hidden" name="paymentType" value="deposit" />
					<#elseif  paymentConfig.paymentConfigType == "offline">
						<input type="hidden" name="paymentType" value="offline" />
					<#else>
						<input type="hidden" name="paymentType" value="online" />
					</#if>
					<input type="hidden" name="order.id" value="${order.id}" />
					<div class="blank"></div>
					<table class="listTable">
						<tr>
							<th colspan="2">支付信息</th>
						</tr>
						<tr>
							<td class="title">支付方式</td>
							<td>${order.paymentConfigName}</td>
						</tr>
						<tr>
							<td colspan="2">
								<input type="submit" class="formButton" value="立即支付" />
							</td>
						</tr>
					</table>
				</form>
			<#else>
				<table class="listTable">
					<tr>
						<th colspan="2">支付信息</th>
					</tr>
					<tr>
						<td class="title">支付方式</td>
						<td>${order.paymentConfigName}</td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="button" class="formButton" onclick="window.location.href='${base}/'" value="确 定" />
						</td>
					</tr>
				</table>
			</#if>
		</div>
		<div class="blank"></div>
		<#include "/WEB-INF/template/shop/friend_link.ftl">
	</div>
	<div class="blank"></div>
	<#include "/WEB-INF/template/shop/footer.ftl">
</body>
</html>