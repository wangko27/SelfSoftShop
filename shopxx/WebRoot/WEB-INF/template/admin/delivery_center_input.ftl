<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑发货点 - Powered By SHOP++</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
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
	var $areaSelect = $("#areaSelect");
	
	// 地区选择菜单
	$areaSelect.lSelect({
		url: "${base}/shop/area!ajaxArea.action"// AJAX数据获取url
	});
	
	// 表单验证
	$validateForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"deliveryCenter.name": "required",
			"deliveryCenter.consignor": "required",
			"areaId": "required",
			"deliveryCenter.address": "required"
		},
		messages: {
			"deliveryCenter.name": "请填写发货点名称",
			"deliveryCenter.consignor": "请填写发货人",
			"areaId": "请选择地区",
			"deliveryCenter.address": "请填写地址"
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
		<#if isAddAction>添加发货点<#else>编辑发货点</#if>
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="<#if isAddAction>delivery_center!save.action<#else>delivery_center!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${(deliveryCenter.id)!}" />
			<table class="inputTable">
				<tr>
					<th>
						发货点名称: 
					</th>
					<td>
						<input type="text" name="deliveryCenter.name" class="formText" value="${(deliveryCenter.name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						发货人: 
					</th>
					<td>
						<input type="text" name="deliveryCenter.consignor" class="formText" value="${(deliveryCenter.consignor)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						地区: 
					</th>
					<td>
						<input type="text" id="areaSelect" name="areaId" class="hidden" value="${(deliveryCenter.area.id)!}" defaultSelectedPath="${(deliveryCenter.area.path)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						地址: 
					</th>
					<td>
						<input type="text" name="deliveryCenter.address" class="formText" value="${(deliveryCenter.address)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						邮编: 
					</th>
					<td>
						<input type="text" name="deliveryCenter.zipCode" class="formText" value="${(deliveryCenter.zipCode)!}" />
					</td>
				</tr>
				<tr>
					<th>
						电话: 
					</th>
					<td>
						<input type="text" name="deliveryCenter.phone" class="formText" value="${(deliveryCenter.phone)!}" />
					</td>
				</tr>
				<tr>
					<th>
						手机: 
					</th>
					<td>
						<input type="text" name="deliveryCenter.mobile" class="formText" value="${(deliveryCenter.mobile)!}" />
					</td>
				</tr>
				<tr>
					<th>
						设置: 
					</th>
					<td>
						<label>
							<@checkbox name="deliveryCenter.isDefault" value="${(deliveryCenter.isDefault)!false}" />是否默认
						</label>
					</td>
				</tr>
				<tr>
					<th>
						备注: 
					</th>
					<td>
						<textarea name="deliveryCenter.memo" class="formTextarea">${(deliveryCenter.memo)!}</textarea>
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