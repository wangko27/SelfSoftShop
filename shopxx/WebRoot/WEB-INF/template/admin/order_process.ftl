<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>处理订单 - Powered By SHOP++</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.lSelect.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready( function() {

	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");
	var $tab = $("#tab");
	var $paymentTabButton = $("#paymentTabButton");
	var $shippingTabButton = $("#shippingTabButton");
	var $refundTabButton = $("#refundTabButton");
	var $reshipTabButton = $("#reshipTabButton");

	var $areaSelect = $(".areaSelect");
	var $paymentForm = $("#paymentForm");
	var $shippingForm = $("#shippingForm");
	var $refundForm = $("#refundForm");
	var $reshipForm= $("#reshipForm");
	var $paymentProcessButton = $("#paymentProcessButton");
	var $shippingProcessButton = $("#shippingProcessButton");
	var $completedProcessButton = $("#completedProcessButton");
	var $refundProcessButton = $("#refundProcessButton");
	var $reshipProcessButton = $("#reshipProcessButton");
	var $invalidProcessButton = $("#invalidProcessButton");
	var $shippingDeliveryQuantity = $("#shippingForm .shippingDeliveryQuantity");
	var $reshipDeliveryQuantity = $("#reshipForm .reshipDeliveryQuantity");
	
	// Tab效果
	$tab.tabs(".tabContent", {
		tabs: "input"
	});
	
	// 地区选择菜单
	$areaSelect.lSelect({
		url: "${base}/shop/area!ajaxArea.action"// AJAX数据获取url
	});

	var tabs = $("ul.tab").tabs();
	
	// 订单支付
	$paymentProcessButton.click( function() {
		tabs.click(2);
	});
	
	// 订单发货
	$shippingProcessButton.click( function() {
		tabs.click(3);
	});
	
	// 订单完成
	$completedProcessButton.click( function() {
		var $this = $(this);
		$.dialog({type: "warn", content: "订单完成后将不允许对此订单进行任何操作,确认执行?", ok: "确 定", cancel: "取 消", width: 420, modal: true, okCallback: orderCompleted});
		function orderCompleted() {
			$.ajax({
				url: "order!completed.action",
				data: {id: "${order.id}"},
				type: "POST",
				dataType: "json",
				cache: false,
				beforeSend: function() {
					$this.attr("disabled", true);
				},
				success: function(data) {
					$.message({type: data.status, content: data.message});
					if (data.status == "success") {
						$paymentTabButton.attr("disabled", true);
						$shippingTabButton.attr("disabled", true);
						$refundTabButton.attr("disabled", true);
						$reshipTabButton.attr("disabled", true);
						
						$paymentProcessButton.attr("disabled", true);
						$shippingProcessButton.attr("disabled", true);
						$completedProcessButton.attr("disabled", true);
						$refundProcessButton.attr("disabled", true);
						$reshipProcessButton.attr("disabled", true);
						$invalidProcessButton.attr("disabled", true);
					} else {
						$this.attr("disabled", true);
					}
				}
			});
		}
	});
	
	// 退款
	$refundProcessButton.click( function() {
		tabs.click(4);
	});
	
	// 退货
	$reshipProcessButton.click( function() {
		tabs.click(5);
	});
	
	// 作废
	$invalidProcessButton.click( function() {
		var $this = $(this);
		$.dialog({type: "warn", content: "订单作废后将不允许对此订单进行任何操作,确认执行?", ok: "确 定", cancel: "取 消", modal: true, okCallback: orderInvalid});
		function orderInvalid() {
			$.ajax({
				url: "order!invalid.action",
				data: {id: "${order.id}"},
				type: "POST",
				dataType: "json",
				cache: false,
				beforeSend: function() {
					$this.attr("disabled", true);
				},
				success: function(data) {
					$.message({type: data.status, content: data.message});
					if (data.status == "success") {
						$paymentTabButton.attr("disabled", true);
						$shippingTabButton.attr("disabled", true);
						$refundTabButton.attr("disabled", true);
						$reshipTabButton.attr("disabled", true);
						
						$paymentProcessButton.attr("disabled", true);
						$shippingProcessButton.attr("disabled", true);
						$completedProcessButton.attr("disabled", true);
						$refundProcessButton.attr("disabled", true);
						$reshipProcessButton.attr("disabled", true);
						$invalidProcessButton.attr("disabled", true);
					} else {
						$this.attr("disabled", false);
					}
				}
			});
		}
	});
	
	// 订单支付表单验证
	$paymentForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"payment.totalAmount": {
				required: true,
				positive: true,
				max: ${order.totalAmount - order.paidAmount}
			}
		},
		messages: {
			"payment.totalAmount": {
				required: "请输入付款金额",
				positive: "付款金额必须为正数",
				max: "付款金额必须小于等于${order.totalAmount - order.paidAmount}"
			}
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
	// 发货数修改
	$shippingDeliveryQuantity.change( function() {
		var $this = $(this);
		var maxDeliveryQuantity = $this.attr("maxDeliveryQuantity");
		if (/^[0-9]*[1-9][0-9]*$/.test($this.val()) && parseInt($this.val()) > parseInt(maxDeliveryQuantity)) {
			$.dialog({type: "warn", content: "本次发货数超出应发货数!", modal: true, autoCloseTime: 2000});
			$this.val(maxDeliveryQuantity);
			return false;
		}
	});
	
	// 订单发货表单验证
	$shippingForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"shipping.deliveryFee": {
				required: true,
				min: 0
			},
			"shipping.shipName": "required",
			"shipAreaId": "required",
			"shipping.shipAddress": "required",
			"shipping.shipZipCode": "required",
			"shipping.shipMobile": {
				"requiredOne": "#shipPhone"
			}
		},
		messages: {
			"shipping.deliveryFee": {
				required: "请输入配送费用",
				min: "配送费用必须为零或正数"
			},
			"shipping.shipName": "请输入收货人姓名",
			"shipAreaId": "请选择收货地区",
			"shipping.shipAddress": "请输入收货地址",
			"shipping.shipZipCode": "请输入邮编",
			"shipping.shipMobile": {
				"requiredOne": "电话、手机必须填写其中一项"
			}
		},
		submitHandler: function(form) {
			var totalShippingDeliveryQuantity = 0;
			$shippingDeliveryQuantity.each(function(){
				var $this = $(this);
				totalShippingDeliveryQuantity += $this.val();
			});
			if (totalShippingDeliveryQuantity == 0) {
				$.dialog({type: "warn", content: "发货总数不允许为0!", modal: true, autoCloseTime: 2000});
			} else {
				$(form).find(":submit").attr("disabled", true);
				form.submit();
			}
		}
	});
	
	$.validator.addMethod("shippingDeliveryQuantityRequired", $.validator.methods.required, "请填写本次发货数");
	$.validator.addMethod("shippingDeliveryQuantityDigits", $.validator.methods.digits, "本次发货数必须为零或正整数");
	
	$.validator.addClassRules("shippingDeliveryQuantity", {
		shippingDeliveryQuantityRequired: true,
		shippingDeliveryQuantityDigits: 0
	});
	
	// 订单退款表单验证
	$refundForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"refund.totalAmount": {
				required: true,
				positive: true,
				max: ${order.paidAmount}
			}
		},
		messages: {
			"refund.totalAmount": {
				required: "请输入退款金额",
				positive: "退款金额必须为正数",
				max: "退款金额必须小于等于${order.paidAmount}"
			}
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
	// 退货数修改
	$reshipDeliveryQuantity.change( function() {
		var $this = $(this);
		var maxDeliveryQuantity = $this.attr("maxDeliveryQuantity");
		if (/^[0-9]*[1-9][0-9]*$/.test($this.val()) && parseInt($this.val()) > parseInt(maxDeliveryQuantity)) {
			$.dialog({type: "warn", content: "本次退货数超出已发货数!", modal: true, autoCloseTime: 2000});
			$this.val(maxDeliveryQuantity);
			return false;
		}
	});
	
	// 订单退货表单验证
	$reshipForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"reship.deliveryFee": {
				required: true,
				min: 0
			},
			"reship.reshipName": "required",
			"reshipAreaId": "required",
			"reship.reshipAddress": "required",
			"reship.reshipZipCode": "required",
			"reship.reshipMobile": {
				"requiredOne": "#reshipPhone"
			}
		},
		messages: {
			"reship.deliveryFee": {
				required: "请输入物流费用",
				min: "配送费用必须为零或正数"
			},
			"reship.reshipName": "请输入退货人姓名",
			"reshipAreaId": "请选择退货地区",
			"reship.reshipAddress": "请输入退货地址",
			"reship.reshipZipCode": "请输入邮编",
			"reship.reshipMobile": {
				"requiredOne": "电话、手机必须填写其中一项"
			}
		},
		submitHandler: function(form) {
			var totalReshipDeliveryQuantity = 0;
			$reshipDeliveryQuantity.each(function(){
				var $this = $(this);
				totalReshipDeliveryQuantity += $this.val();
			});
			if (totalReshipDeliveryQuantity == 0) {
				$.dialog({type: "warn", content: "退货总数不允许为0!", modal: true, autoCloseTime: 2000});
			} else {
				$(form).find(":submit").attr("disabled", true);
				form.submit();
			}
		}
	});
	
	$.validator.addMethod("reshipDeliveryQuantityRequired", $.validator.methods.required, "请填写本次退货数");
	$.validator.addMethod("reshipDeliveryQuantityDigits", $.validator.methods.digits, "本次退货数必须为零或正整数");
	
	$.validator.addClassRules("reshipDeliveryQuantity", {
		reshipDeliveryQuantityRequired: true,
		reshipDeliveryQuantityDigits: 0
	});

});
</script>
</head>
<body class="input">
	<div class="bar">处理订单</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<ul id="tab" class="tab">
			<li>
				<input type="button" value="基本信息" hidefocus />
			</li>
			<li>
				<input type="button" value="商品信息" hidefocus />
			</li>
			<li>
				<input type="button" id="paymentTabButton" value="订单支付"<#if order.orderStatus == "completed" || order.orderStatus == "invalid" || order.paymentStatus == "paid" || order.paymentStatus == "partRefund" || order.paymentStatus == "refunded"> disabled</#if> hidefocus />
			</li>
			<li>
				<input type="button" id="shippingTabButton" value="订单发货"<#if order.orderStatus == "completed" || order.orderStatus == "invalid" || order.shippingStatus == "shipped"> disabled</#if> hidefocus />
			</li>
			<li>
				<input type="button" id="refundTabButton" value="退款"<#if order.orderStatus == "completed" || order.orderStatus == "invalid" || order.paymentStatus == "unpaid" || order.paymentStatus == "refunded"> disabled</#if> hidefocus />
			</li>
			<li>
				<input type="button" id="reshipTabButton" value="退货"<#if order.orderStatus == "completed" || order.orderStatus == "invalid" || order.shippingStatus == "unshipped" || order.shippingStatus == "reshiped"> disabled</#if> hidefocus />
			</li>
		</ul>
		<div class="tabContent">
			<table class="inputTable">
				<tr>
					<th>
						订单状态操作: 
					</th>
					<td>
						<input type="button" id="paymentProcessButton" name="paymentProcess" class="formButton" value="订单支付"<#if order.orderStatus == "completed" || order.orderStatus == "invalid" || order.paymentStatus == "paid" || order.paymentStatus == "partRefund" || order.paymentStatus == "refunded"> disabled</#if> hidefocus />
						<input type="button" id="shippingProcessButton" name="shippingProcess" class="formButton" value="订单发货"<#if order.orderStatus == "completed" || order.orderStatus == "invalid" || order.shippingStatus == "shipped"> disabled</#if> hidefocus />
						<input type="button" id="completedProcessButton" name="completedProcess" class="formButton" value="订单完成"<#if order.orderStatus == "completed" || order.orderStatus == "invalid"> disabled</#if> hidefocus />
					</td>
					<td colspan="2">
						<input type="button" id="refundProcessButton" name="refundProcess" class="formButton" value="退款"<#if order.orderStatus == "completed" || order.orderStatus == "invalid" || order.paymentStatus == "unpaid" || order.paymentStatus == "refunded"> disabled</#if> hidefocus />
						<input type="button" id="reshipProcessButton" name="reshipProcess" class="formButton" value="退货"<#if order.orderStatus == "completed" || order.orderStatus == "invalid" || order.shippingStatus == "unshipped" || order.shippingStatus == "reshiped"> disabled</#if> hidefocus />
						<input type="button" id="invalidProcessButton" name="invalidProcess" class="formButton" value="作废"<#if order.orderStatus == "completed" || order.orderStatus == "invalid" || order.paymentStatus != "unpaid" || order.shippingStatus != "unshipped"> disabled</#if> hidefocus />
					</td>
				</tr>
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
						<span id="totalProductPrice" class="red">${order.totalProductPrice?string(currencyFormat)}</span>
					</td>
					<th>
						订单总金额: 
					</th>
					<td>
						<span id="totalAmount" class="red">${order.totalAmount?string(currencyFormat)}</span>&nbsp;&nbsp;
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
						商品重量: 
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
					<tr>
						<td colspan="4">
							<div class="buttonArea">
								<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
							</div>
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
		</div>
		<div class="tabContent">
			<table class="inputTable">
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
				<tr>
					<td colspan="4">
						<div class="buttonArea">
							<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div class="tabContent">
			<#if order.orderStatus != "completed" && order.orderStatus != "invalid" && order.paymentStatus != "paid" && order.paymentStatus != "partRefund" && order.paymentStatus != "refunded">
				<form id="paymentForm" action="order!payment.action" method="post">
					<input type="hidden" name="id" value="${order.id}" />
					<table class="inputTable">
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
								订单总金额: 
							</th>
							<td>
								<span class="red">${order.totalAmount?string(currencyFormat)}</span>
							</td>
							<th>
								已付金额: 
							</th>
							<td>
								<span class="red">${order.paidAmount?string(currencyFormat)}</span>
							</td>
						</tr>
						<tr>
							<th>
								收款银行: 
							</th>
							<td>
								<input type="text" name="payment.bankName" class="formText" />
							</td>
							<th>
								收款账号: 
							</th>
							<td>
								<input type="text" name="payment.bankAccount" class="formText" />
							</td>
						</tr>
						<tr>
							<th>
								支付类型: 
							</th>
							<td>
								<select name="payment.paymentType">
									<#list nonRechargePaymentTypeList as paymentType>
										<option value="${paymentType}">
											${action.getText("PaymentType." + paymentType)}
										</option>
									</#list>
								</select>
							</td>
							<th>
								支付方式: 
							</th>
							<td>
								<select name="paymentConfig.id">
									<#list allPaymentConfigList as paymentConfig>
										<option value="${paymentConfig.id}"<#if (paymentConfig == order.paymentConfig)!> selected</#if>>
											${paymentConfig.name}
										</option>
									</#list>
								</select>
							</td>
						</tr>
						<tr>
							<th>
								付款金额: 
							</th>
							<td>
								<input type="text" name="payment.totalAmount" class="formText" value="${order.totalAmount - order.paidAmount}" />
							</td>
							<th>
								付款人: 
							</th>
							<td>
								<input type="text" name="payment.payer" class="formText" />
							</td>
						</tr>
						<tr>
							<th>
								收款单备注: 
							</th>
							<td colspan="3">
								<input type="text" name="payment.memo" class="formText" />
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<div class="buttonArea">
									<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
									<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
								</div>
							</td>
						</tr>
					</table>
				</form>
			</#if>
		</div>
		<div class="tabContent">
			<#if order.orderStatus != "completed" && order.orderStatus != "invalid" && order.shippingStatus != "shipped">
				<form id="shippingForm" action="order!shipping.action" method="post">
					<input type="hidden" name="id" value="${order.id}" />
					<table class="inputTable">
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
								配送方式: 
							</th>
							<td>
								<select name="deliveryType.id">
									<#list allDeliveryTypeList as deliveryType>
										<option value="${deliveryType.id}"<#if (deliveryType == order.deliveryType)!> selected</#if>>
											${deliveryType.name}
										</option>
									</#list>
								</select>
							</td>
							<th>
								配送费用: 
							</th>
							<td>
								<span class="red">${order.deliveryFee?string(currencyFormat)}</span>
							</td>
						</tr>
						<tr>
							<th>
								物流公司: 
							</th>
							<td>
								<select name="deliveryCorp.id">
									<#list allDeliveryCorpList as deliveryCorp>
										<option value="${deliveryCorp.id}"<#if (deliveryCorp == order.deliveryType.defaultDeliveryCorp)!> selected</#if>>
											${deliveryCorp.name}
										</option>
									</#list>
								</select>
							</td>
							<th>
								物流单号: 
							</th>
							<td>
								<input type="text" name="shipping.deliverySn" class="formText" />
							</td>
						</tr>
						<tr>
							<th>
								物流费用: 
							</th>
							<td colspan="3">
								<input type="text" name="shipping.deliveryFee" class="formText" value="${order.deliveryFee}" />
								<label class="requireField">*</label>
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
								<input type="text" name="shipping.shipName" class="formText" value="${order.shipName}" />
								<label class="requireField">*</label>
							</td>
							<th>
								收货地区: 
							</th>
							<td>
								<input type="text" name="shipAreaId" class="areaSelect" value="${order.shipArea.id}" defaultSelectedPath="${order.shipArea.path}" />
								<label class="requireField">*</label>
							</td>
						</tr>
						<tr>
							<th>
								收货地址: 
							</th>
							<td>
								<input type="text" name="shipping.shipAddress" class="formText" value="${order.shipAddress}" />
								<label class="requireField">*</label>
							</td>
							<th>
								邮编: 
							</th>
							<td>
								<input type="text" name="shipping.shipZipCode" class="formText" value="${order.shipZipCode}" />
								<label class="requireField">*</label>
							</td>
						</tr>
						<tr>
							<th>
								电话: 
							</th>
							<td>
								<input type="text" id="shipPhone" name="shipping.shipPhone" class="formText" value="${order.shipPhone}" />
								<label class="requireField">*</label>
							</td>
							<th>
								手机: 
							</th>
							<td>
								<input type="text" name="shipping.shipMobile" class="formText" value="${order.shipMobile}" />
								<label class="requireField">*</label>
							</td>
						</tr>
						<tr>
							<th>
								发货备注: 
							</th>
							<td colspan="3">
								<input type="text" name="shipping.memo" class="formText" />
							</td>
						</tr>
						<tr>
							<td colspan="4">
								&nbsp;
							</td>
						</tr>
					</table>
					<table class="inputTable">
						<tr class="title">
							<th>货号</th>
							<th>商品名称</th>
							<th>购买数量</th>
							<th>当前库存</th>
							<th>已发货数</th>
							<th>本次发货数</th>
						</tr>
						<#list order.orderItemSet as orderItem>
							<tr>
								<td>
									<input type="hidden" name="deliveryItemList[${orderItem_index}].product.id" value="${orderItem.product.id}" />
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
									${orderItem.productQuantity}
								</td>
								<td>
									<#if (orderItem.product.store)??>
										${orderItem.product.store}
										[被占用: ${orderItem.product.freezeStore}]
									<#else>
										不计库存
									</#if>
								</td>
								<td>
									${orderItem.deliveryQuantity}
								</td>
								<td>
									<input type="text" name="deliveryItemList[${orderItem_index}].deliveryQuantity" class="formText shippingDeliveryQuantity" value="${orderItem.productQuantity - orderItem.deliveryQuantity}" maxDeliveryQuantity="${orderItem.productQuantity - orderItem.deliveryQuantity}" style="width: 50px;" />
								</td>
							</tr>
						</#list>
						<tr>
							<td colspan="6">
								<div class="buttonArea">
									<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
									<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
								</div>
							</td>
						</tr>
					</table>
				</form>
			</#if>
		</div>
		<div class="tabContent">
			<#if order.orderStatus != "completed" && order.orderStatus != "invalid" && order.paymentStatus != "unpaid" && order.paymentStatus != "refunded">
				<form id="refundForm" action="order!refund.action" method="post">
					<input type="hidden" name="id" value="${order.id}" />
					<table class="inputTable">
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
								订单总金额: 
							</th>
							<td>
								<span class="red">${order.totalAmount?string(currencyFormat)}</span>
							</td>
							<th>
								已付金额: 
							</th>
							<td>
								<span class="red">${order.paidAmount?string(currencyFormat)}</span>
							</td>
						</tr>
						<tr>
							<th>
								退款银行: 
							</th>
							<td>
								<input type="text" name="refund.bankName" class="formText" />
							</td>
							<th>
								退款账号: 
							</th>
							<td>
								<input type="text" name="refund.bankAccount" class="formText" />
							</td>
						</tr>
						<tr>
							<th>
								退款类型: 
							</th>
							<td>
								<select name="refund.refundType">
									<#list refundTypeList as refundType>
										<option value="${refundType}">
											${action.getText("RefundType." + refundType)}
										</option>
									</#list>
								</select>
							</td>
							<th>
								退款方式: 
							</th>
							<td>
								<select name="paymentConfig.id">
									<#list allPaymentConfigList as paymentConfig>
										<option value="${paymentConfig.id}"<#if (paymentConfig == order.paymentConfig)!> selected</#if>>
											${paymentConfig.name}
										</option>
									</#list>
								</select>
							</td>
						</tr>
						<tr>
							<th>
								退款金额: 
							</th>
							<td>
								<input type="text" name="refund.totalAmount" class="formText" value="${order.paidAmount}" />
								<label class="requireField">*</label>
							</td>
							<th>
								收款人: 
							</th>
							<td>
								<input type="text" name="refund.payee" class="formText" />
							</td>
						</tr>
						<tr>
							<th>
								退款备注: 
							</th>
							<td colspan="3">
								<input type="text" name="refund.memo" class="formText" />
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<div class="buttonArea">
									<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
									<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
								</div>
							</td>
						</tr>
					</table>
				</form>
			</#if>
		</div>
		<div class="tabContent">
			<#if order.orderStatus != "completed" && order.orderStatus != "invalid" && order.shippingStatus != "unshipped" && order.shippingStatus != "reshiped">
				<form id="reshipForm" action="order!reship.action" method="post">
					<input type="hidden" name="id" value="${order.id}" />
					<table class="inputTable">
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
								配送方式: 
							</th>
							<td>
								<select name="deliveryType.id">
									<#list allDeliveryTypeList as deliveryType>
										<option value="${deliveryType.id}"<#if (deliveryType == order.deliveryType)!> selected</#if>>
											${deliveryType.name}
										</option>
									</#list>
								</select>
							</td>
							<th>
								物流公司: 
							</th>
							<td>
								<select name="deliveryCorp.id">
									<#list allDeliveryCorpList as deliveryCorp>
										<option value="${deliveryCorp.id}">
											${deliveryCorp.name}
										</option>
									</#list>
								</select>
							</td>
						</tr>
						<tr>
							<th>
								物流费用: 
							</th>
							<td>
								<input type="text" name="reship.deliveryFee" class="formText" value="${order.deliveryFee}" />
								<label class="requireField">*</label>
							</td>
							<th>
								物流单号: 
							</th>
							<td>
								<input type="text" name="reship.deliverySn" class="formText" />
							</td>
						</tr>
						<tr>
							<td colspan="4">
								&nbsp;
							</td>
						</tr>
						<tr>
							<th>
								退货人姓名: 
							</th>
							<td>
								<input type="text" name="reship.reshipName" class="formText" value="${order.shipName}" />
								<label class="requireField">*</label>
							</td>
							<th>
								退货地区: 
							</th>
							<td>
								<input type="text" name="reshipAreaId" class="areaSelect" value="${order.shipArea.id}" defaultSelectedPath="${order.shipArea.path}" />
								<label class="requireField">*</label>
							</td>
						</tr>
						<tr>
							<th>
								退货地址: 
							</th>
							<td>
								<input type="text" name="reship.reshipAddress" class="formText" value="${order.shipAddress}" />
								<label class="requireField">*</label>
							</td>
							<th>
								邮编: 
							</th>
							<td>
								<input type="text" name="reship.reshipZipCode" class="formText" value="${order.shipZipCode}" />
								<label class="requireField">*</label>
							</td>
						</tr>
						<tr>
							<th>
								电话: 
							</th>
							<td>
								<input type="text" id="reshipPhone" name="reship.reshipPhone" class="formText" value="${order.shipPhone}" />
								<label class="requireField">*</label>
							</td>
							<th>
								手机: 
							</th>
							<td>
								<input type="text" name="reship.reshipMobile" class="formText" value="${order.shipMobile}" />
								<label class="requireField">*</label>
							</td>
						</tr>
						<tr>
							<th>
								退货备注: 
							</th>
							<td colspan="3">
								<input type="text" name="reship.memo" class="formText" />
							</td>
						</tr>
						<tr>
							<td colspan="4">
								&nbsp;
							</td>
						</tr>
					</table>
					<table class="inputTable">
						<tr class="title">
							<th>货号</th>
							<th>商品名称</th>
							<th>购买数量</th>
							<th>已发货数</th>
							<th>本次退货数</th>
						</tr>
						<#list order.orderItemSet as orderItem>
							<tr>
								<td>
									<input type="hidden" name="deliveryItemList[${orderItem_index}].product.id" value="${orderItem.product.id}" />
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
									${orderItem.productQuantity}
								</td>
								<td>
									${orderItem.deliveryQuantity}
								</td>
								<td>
									<input type="text" name="deliveryItemList[${orderItem_index}].deliveryQuantity" class="formText reshipDeliveryQuantity" value="${orderItem.deliveryQuantity}" maxDeliveryQuantity="${orderItem.deliveryQuantity}" style="width: 50px;" />
								</td>
							</tr>
						</#list>
						<tr>
							<td colspan="6">
								<div class="buttonArea">
									<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
									<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
								</div>
							</td>
						</tr>
					</table>
				</form>
			</#if>
		</div>
	</div>
</body>
</html>