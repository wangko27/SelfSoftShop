<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>订单打印</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<!--[if lte IE 6]>
	<script type="text/javascript" src="${base}/template/common/js/belatedPNG.js"></script>
	<script type="text/javascript">
		// 解决IE6透明PNG图片BUG
		DD_belatedPNG.fix(".belatedPNG");
	</script>
<![endif]-->
<script type="text/javascript">
$().ready(function() {
	
	var $printButton = $("#printButton");
	
	$printButton.click(function(){
		window.print();
		return false;
	});

})

try {
	var WScriptShell = new ActiveXObject("WScript.Shell");
	WScriptShell.RegWrite("HKEY_CURRENT_USER\\Software\\Microsoft\\Internet Explorer\\PageSetup\\header", "");
	WScriptShell.RegWrite("HKEY_CURRENT_USER\\Software\\Microsoft\\Internet Explorer\\PageSetup\\footer", "");
} catch(e) {

}

</script>
<style type="text/css" media="screen,print">
<!--

.print .header {
	height: 30px;
	clear: both;
}

.print .printBar {
	width: 100%;
	height: 30px;
	padding: 5px 0px;
	position: fixed;
	top: 0px;
	_position: absolute;
	_top: expression(eval(document.documentElement.scrollTop));
	z-index: 99;
	text-align: center;
	border-bottom: 1px solid #7b7b7b;
	background-color: #e9f0f4;
}

.print .body {
	padding: 20px 20px 60px 20px;
	position: absolute;
	font-size: 12px;
}

.print .title {
	height: 60px;
	line-height: 30px;
	font-size: 12px;
}

.print .title strong {
	font-size: 14px;
}

.print table {
	width: 100%;
	text-align: left;
}

.print table tr {
	line-height: 30px;
}

.print table tr td, .print table tr th {
	padding: 0px 2px;
}

.print .separated th, .print .separated td {
	border-top: 2px solid #000000;
	border-bottom: 2px solid #000000;
}

-->
</style>
<style type="text/css" media="print">

.print .header {
	display: none;
}

</style>
</head>
<body class="print">
	<div class="header">
		<div class="printBar">
			<input type="button" id="printButton" class="formButton" value="打 印" />
		</div>
	</div>
	<div class="body">
		<table>
			<tr class="title">
				<td colspan="2">
					<img class="belatedPNG" src="${base}${(setting.shopLogoPath)!}" />
				</td>
				<td>
					<strong>${(setting.shopName)!}</strong><br />
					${(setting.shopUrl)!}
				</td>
				<td>
					&nbsp;
				</td>
				<td colspan="2">
					客户名称: ${order.shipName}<br />
					会员名称: ${(order.member.username)!"-"}
				</td>
			</tr>
			<tr class="separated">
				<th colspan="2">
					订单编号: ${order.orderSn}
				</th>
				<th colspan="2">
					订购日期: ${order.createDate?string("yyyy-MM-dd")}
				</th>
				<th colspan="2">
					打印日期: <strong>${currentDate?string("yyyy-MM-dd")}</strong>
				</th>
			</tr>
			<tr>
				<td colspan="6">
					&nbsp;
				</td>
			</tr>
			<tr class="separated">
				<th>
					序号
				</th>
				<th>
					货号
				</th>
				<th>
					商品名称
				</th>
				<th>
					单价
				</th>
				<th>
					数量
				</th>
				<th>
					小计
				</th>
			</tr>
			<#list order.orderItemSet as orderItem>
				<tr>
					<td>
						${orderItem_index + 1}
					</td>
					<td>
						${orderItem.productSn}
					</td>
					<td>
						${orderItem.productName}
					</td>
					<td>
						${orderItem.productPrice?string(currencyFormat)}
					</td>
					<td>
						${orderItem.productQuantity}
					</td>
					<td>
						${orderItem.subtotalPrice?string(currencyFormat)}
					</td>
				</tr>
			</#list>
			<tr>
				<td colspan="6">
					&nbsp;
				</td>
			</tr>
			<tr class="separated">
				<td colspan="3">
					订单附言: ${order.memo}<br />
				</td>
				<td colspan="3">
					商品总金额: ${order.totalProductPrice?string(currencyFormat)}<br />
					配送费用: ${order.deliveryFee?string(currencyFormat)}<br />
					支付手续费: ${order.paymentFee?string(currencyFormat)}<br />
					订单总金额: ${order.totalAmount?string(currencyFormat)}
				</td>
			</tr>
			<tr>
				<td colspan="6">
					&nbsp;
				</td>
			</tr>
			<tr class="separated">
				<td colspan="3">
					&nbsp;
				</td>
				<td colspan="3">
					收 货 人: ${order.shipName}<br />
					收货地址: ${order.shipArea.displayName}${order.shipAddress}<br />
					邮政编码: ${order.shipZipCode}<br />
					联系电话: ${order.shipPhone}<br />
					联系手机: ${order.shipMobile}<br />
					配送方式: ${order.deliveryTypeName}
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
				<td colspan="2">
					Powered By shopxx.net
				</td>
			</tr>
		</table>
	</div>
</body>
</html>