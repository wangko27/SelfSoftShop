<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑快递单模板 - Powered By SHOP++</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/common/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready( function() {

	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");
	var $deliveryTemplateTag = $("#deliveryTemplateTag");
	
	// 插入标签
	$deliveryTemplateTag.change( function() {
		var $this = $(this);
		if ($this.val() != "") {
			KE.insertHtml("editor", $this.val());
		}
	});
	
	// 表单验证
	$validateForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"deliveryTemplate.name": "required",
			"deliveryTemplate.content": "required"
		},
		messages: {
			"deliveryTemplate.name": "请填写模板名称",
			"deliveryTemplate.content": "请填写模板内容"
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});

});
</script>
</head>
<body class="input">
	<div class="bar">
		<#if isAddAction>添加快递单模板<#else>编辑快递单模板</#if>
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="<#if isAddAction>delivery_template!save.action<#else>delivery_template!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${(deliveryTemplate.id)!}" />
			<table class="inputTable">
				<tr>
					<th>
						模板名称: 
					</th>
					<td>
						<input type="text" name="deliveryTemplate.name" class="formText" value="${(deliveryTemplate.name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						插入标签: 
					</th>
					<td>
						<#noparse>
							<select id="deliveryTemplateTag">
								<option value="">请选择...</option>
								<option value="${deliveryCenter.name}">发货人 - 姓名</option>
								<option value="${deliveryCenter.area.displayName}">发货人 - 地区</option>
								<option value="${deliveryCenter.address}">发货人 - 地址</option>
								<option value="${deliveryCenter.zipCode}">发货人 - 邮编</option>
								<option value="${deliveryCenter.phone}">发货人 - 电话</option>
								<option value="${deliveryCenter.mobile}">发货人 - 手机</option>
								<option value=""></option>
								<option value="${order.shipName}">收货人 - 姓名</option>
								<option value="${order.shipArea.displayName}">收货人 - 地区</option>
								<option value="${order.shipAddress}">收货人 - 地址</option>
								<option value="${order.shipZipCode}">收货人 - 邮编</option>
								<option value="${order.shipPhone}">收货人 - 电话</option>
								<option value="${order.shipMobile}">收货人 - 手机</option>
								<option value=""></option>
								<option value="${currentYear}">当前日期 - 年</option>
								<option value="${currentMonth}">当前日期 - 月</option>
								<option value="${currentDay}">当前日期 - 日</option>
								<option value=""></option>
								<option value="${order.orderSn}">订单 - 订单编号</option>
								<option value="${order.totalProductPrice}">订单 - 总商品价格</option>
								<option value="${order.deliveryFee}">订单 - 配送费用</option>
								<option value="${order.paymentFee}">订单 - 支付费用</option>
								<option value="${order.totalAmount}">订单 - 订单总额</option>
								<option value="${order.totalProductWeight}">订单 - 总商品重量</option>
								<option value="${order.totalProductQuantity}">订单 - 总商品数量</option>
								<option value="${order.totalAmount}">订单 - 订单总额</option>
								<option value="${order.memo}">订单 - 附言</option>
								<option value=""></option>
								<option value="${setting.shopName}">网站 - 名称</option>
								<option value="${setting.shopUrl}">网站 - 网址</option>
								<option value="${setting.address}">网站 - 地址</option>
								<option value="${setting.phone}">网站 - 电话</option>
								<option value="${setting.zipCode}">网站 - 邮编</option>
								<option value="${setting.email}">网站 - Email</option>
							</select>
						</#noparse>
					</td>
				</tr>
				<tr>
					<th>
						模板内容: 
					</th>
					<td>
						<textarea id="editor" name="deliveryTemplate.content" class="editor">${(deliveryTemplate.content)!}</textarea>
					</td>
				</tr>
				<tr>
					<th>
						设置: 
					</th>
					<td>
						<label>
							<@checkbox name="deliveryTemplate.isDefault" value="${(deliveryTemplate.isDefault)!false}" />是否默认
						</label>
					</td>
				</tr>
				<tr>
					<th>
						备注: 
					</th>
					<td>
						<textarea name="deliveryTemplate.memo" class="formTextarea">${(deliveryTemplate.memo)!}</textarea>
					</td>
				</tr>
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>