<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>查看订单 - Powered By SHOP++</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready( function() {

	var $tab = $("#tab");
	
	// Tab效果
	$tab.tabs(".tabContent", {
		tabs: "input"
	});

});
</script>
</head>
<body class="input">
	<div class="bar">
		查看订单
	</div>
	<div class="body">
		<ul id="tab" class="tab" style="padding-left: 80px;">
			<li>
				<input type="button" value="订单信息" hidefocus />
			</li>
			<li>
				<input type="button" value="商品信息" hidefocus />
			</li>
			<li>
				<input type="button" value="收款记录" hidefocus />
			</li>
			<li>
				<input type="button" value="退款记录" hidefocus />
			</li>
			<li>
				<input type="button" value="收货记录" hidefocus />
			</li>
			<li>
				<input type="button" value="退货记录" hidefocus />
			</li>
			<li>
				<input type="button" value="订单日志" hidefocus />
			</li>
		</ul>
		<table class="inputTable tabContent">
			<tr>
				<th>
					订单状态: 
				</th>
				<td colspan="3">
					<span class="red">
						[${action.getText("OrderStatus." + order.orderStatus)}]
						[${action.getText("PaymentStatus." + order.paymentStatus)}]
						[${action.getText("ShippingStatus." + order.shippingStatus)}]
					</span>
				</td>
			</tr>
			<tr>
				<td colspan="4">
					&nbsp;
				</td>
			</tr>
			<tr>
				<th>
					订单编号: 
				</th>
				<td>
					${order.orderSn}
				</td>
				<th>
					下单时间: 
				</th>
				<td>
					${order.createDate?string("yyyy-MM-dd HH:mm:ss")}
				</td>
			</tr>
			<tr>
				<th>
					商品总金额: 
				</th>
				<td>
					<span class="red">${order.totalProductPrice?string(currencyFormat)}</span>
				</td>
				<th>
					订单总金额: 
				</th>
				<td>
					<span class="red">${order.totalAmount?string(currencyFormat)}</span>&nbsp;&nbsp;
					<strong class="red">[已付金额: ${order.paidAmount?string(currencyFormat)}]</strong>
				</td>
			</tr>
			<tr>
				<th>
					配送方式: 
				</th>
				<td>
					${order.deliveryTypeName}
				</td>
				<th>
					支付方式: 
				</th>
				<td>
					${order.paymentConfigName}
				</td>
			</tr>
			<tr>
				<th>
					配送费用: 
				</th>
				<td>
					${order.deliveryFee?string(currencyFormat)}
				</td>
				<th>
					支付手续费: 
				</th>
				<td>
					${order.paymentFee?string(currencyFormat)}
				</td>
			</tr>
			<tr>
				<th>
					商品总重量: 
				</th>
				<td>
					${order.totalProductWeight} 克
				</td>
				<th>
					附言: 
				</th>
				<td>
					${(order.memo)!}
				</td>
			</tr>
			<tr>
				<td colspan="4">
					&nbsp;
				</td>
			</tr>
			<tr>
				<th>
					收货人姓名: 
				</th>
				<td>
					${order.shipName}
				</td>
				<th>
					收货地区: 
				</th>
				<td>
					${order.shipArea.displayName}
				</td>
			</tr>
			<tr>
				<th>
					收货地址: 
				</th>
				<td>
					${order.shipAddress}
				</td>
				<th>
					邮编: 
				</th>
				<td>
					${order.shipZipCode}
				</td>
			</tr>
			<tr>
				<th>
					电话: 
				</th>
				<td>
					${order.shipPhone}
				</td>
				<th>
					手机: 
				</th>
				<td>
					${order.shipMobile}
				</td>
			</tr>
			<tr>
				<td colspan="4">
					&nbsp;
				</td>
			</tr>
			<#if order.member??>
				<#assign member = order.member />
				<tr>
					<th>
						用户名: 
					</th>
					<td>
						${member.username}
					</td>
					<th>
						会员等级: 
					</th>
					<td>
						${member.memberRank.name}
						<#if member.memberRank.preferentialScale != 100>
							<span class="red">[优惠百分比: ${member.memberRank.preferentialScale}%]</span>
						</#if>
					</td>
				</tr>
				<tr>
					<th>
						E-mail: 
					</th>
					<td>
						${member.email}
					</td>
					<th>
						最后登录IP: 
					</th>
					<td>
						${member.loginIp}
					</td>
				</tr>
				<tr>
					<th>
						预存款余额: 
					</th>
					<td>
						${member.deposit?string(currencyFormat)}
					</td>
					<th>
						积分: 
					</th>
					<td>
						${member.score}
					</td>
				</tr>
			<#else>
				<tr>
					<th>
						会员状态: 
					</th>
					<td colspan="3">
						<span class="red">会员不存在</span>
					</td>
				</tr>
			</#if>
		</table>
		<table class="inputTable tabContent">
			<tr class="title">
				<th>货号</th>
				<th>商品名称</th>
				<th>价格</th>
				<th>购买数量</th>
			</tr>
			<#list order.orderItemSet as orderItem>
				<tr>
					<td>
						<a href="${base}${orderItem.goodsHtmlPath}" target="_blank">
							${orderItem.productSn}
						</a>
					</td>
					<td>
						<a href="${base}${orderItem.goodsHtmlPath}" target="_blank">
							${orderItem.productName}
						</a>
					</td>
					<td>
						${orderItem.productPrice?string(currencyFormat)}
					</td>
					<td>
						${orderItem.productQuantity}
					</td>
				</tr>
			</#list>
		</table>
		<table class="inputTable tabContent">
			<tr class="title">
				<th>序号</th>
				<th>支付编号</th>
				<th>支付类型</th>
				<th>支付方式</th>
				<th>支付金额</th>
				<th>支付手续费</th>
				<th>付款人</th>
				<th>支付状态</th>
				<th>支付时间</th>
			</tr>
			<#list order.paymentSet as payment>
				<tr>
					<td>${payment_index + 1}</td>
					<td>
						<a href="payment!view.action?id=${payment.id}">
							${payment.paymentSn}
						</a>
					</td>
					<td>
						${action.getText("PaymentType." + payment.paymentType)}
					</td>
					<td>
						${payment.paymentConfigName}
					</td>
					<td>
						${payment.totalAmount?string(currencyFormat)}
					</td>
					<td>
						${payment.paymentFee?string(currencyFormat)}
					</td>
					<td>
						${payment.payer}
					</td>
					<td>
						${action.getText("PaymentStatus." + payment.paymentStatus)}
					</td>
					<td>
						<span title="${payment.createDate?string("yyyy-MM-dd HH:mm:ss")}">${payment.createDate}</span>
					</td>
				</tr>
			</#list>
		</table>
		<table class="inputTable tabContent">
			<tr class="title">
				<th>序号</th>
				<th>退款编号</th>
				<th>退款类型</th>
				<th>支付方式</th>
				<th>退款金额</th>
				<th>收款人</th>
				<th>退款时间</th>
			</tr>
			<#list order.refundSet as refund>
				<tr>
					<td>${refund_index + 1}</td>
					<td>
						<a href="refund!view.action?id=${refund.id}">
							${refund.refundSn}
						</a>
					</td>
					<td>
						${action.getText("RefundType." + refund.refundType)}
					</td>
					<td>
						${refund.paymentConfigName}
					</td>
					<td>
						${refund.totalAmount?string(currencyFormat)}
					</td>
					<td>
						${refund.payee}
					</td>
					<td>
						<span title="${refund.createDate?string("yyyy-MM-dd HH:mm:ss")}">${refund.createDate}</span>
					</td>
				</tr>
			</#list>
		</table>
		<table class="inputTable tabContent">
			<tr class="title">
				<th>序号</th>
				<th>发货编号</th>
				<th>配送方式名称</th>
				<th>物流公司名称</th>
				<th>物流单号</th>
				<th>物流费用</th>
				<th>收货人姓名</th>
				<th>收货地区</th>
				<th>发货时间</th>
			</tr>
			<#list order.shippingSet as shipping>
				<tr>
					<td>${shipping_index + 1}</td>
					<td>
						<a href="shipping!view.action?id=${shipping.id}">
							${shipping.shippingSn}
						</a>
					</td>
					<td>
						${shipping.deliveryTypeName}
					</td>
					<td>
						${shipping.deliveryCorpName}
					</td>
					<td>
						${shipping.deliverySn}
					</td>
					<td>
						${shipping.deliveryFee?string(currencyFormat)}
					</td>
					<td>
						${shipping.shipName}
					</td>
					<td>
						${shipping.shipArea.displayName}
					</td>
					<td>
						<span title="${shipping.createDate?string("yyyy-MM-dd HH:mm:ss")}">${shipping.createDate}</span>
					</td>
				</tr>
			</#list>
		</table>
		<table class="inputTable tabContent">
			<tr class="title">
				<th>序号</th>
				<th>退货编号</th>
				<th>配送方式名称</th>
				<th>物流公司名称</th>
				<th>物流单号</th>
				<th>物流费用</th>
				<th>退货人姓名</th>
				<th>退货地区</th>
				<th>退货时间</th>
			</tr>
			<#list order.reshipSet as reship>
				<tr>
					<td>${reship_index + 1}</td>
					<td>
						<a href="reship!view.action?id=${reship.id}">
							${reship.reshipSn}
						</a>
					</td>
					<td>
						${reship.deliveryTypeName}
					</td>
					<td>
						${reship.deliveryCorpName}
					</td>
					<td>
						${reship.deliverySn}
					</td>
					<td>
						${reship.deliveryFee?string(currencyFormat)}
					</td>
					<td>
						${reship.reshipName}
					</td>
					<td>
						${reship.reshipArea.displayName}
					</td>
					<td>
						<span title="${reship.createDate?string("yyyy-MM-dd HH:mm:ss")}">${reship.createDate}</span>
					</td>
				</tr>
			</#list>
		</table>
		<table class="inputTable tabContent">
			<tr class="title">
				<th>序号</th>
				<th>日志类型</th>
				<th>操作员</th>
				<th>日志信息</th>
				<th>操作日间</th>
			</tr>
			<#list order.orderLogSet as orderLog>
				<tr>
					<td>${orderLog_index + 1}</td>
					<td>
						${action.getText("OrderLogType." + orderLog.orderLogType)}
					</td>
					<td>
						${orderLog.operator!"[用户]"}
					</td>
					<td>
						${orderLog.info!"-"}
					</td>
					<td>
						<span title="${orderLog.createDate?string("yyyy-MM-dd HH:mm:ss")}">${orderLog.createDate}</span>
					</td>
				</tr>
			</#list>
		</table>
		<div class="buttonArea">
			<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
		</div>
	</div>
</body>
</html>