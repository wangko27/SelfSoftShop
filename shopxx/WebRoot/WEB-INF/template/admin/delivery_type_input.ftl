<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑配送方式 - Powered By SHOP++</title>
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
$().ready(function() {

	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");
	
	// 表单验证
	$validateForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"deliveryType.name": "required",
			"deliveryType.firstWeight": {
				required: true,
				digits: true
			},
			"deliveryType.continueWeight": {
				required: true,
				positiveInteger: true
			},
			"deliveryType.firstWeightPrice": {
				required: true,
				min: 0
			},
			"deliveryType.continueWeightPrice": {
				required: true,
				min: 0
			},
			"deliveryType.orderList": {
				digits: true
			}
		},
		messages: {
			"deliveryType.name": "请填写配送方式名称",
			"deliveryType.firstWeight": {
				required: "请填写首重量",
				digits: "首重量必须为零或正整数"
			},
			"deliveryType.continueWeight": {
				required: "请填写续重量",
				positiveInteger: "续重量必须为正整数"
			},
			"deliveryType.firstWeightPrice": {
				required: "请填写首重价格",
				min: "首重价格必须为零或正数"
			},
			"deliveryType.continueWeightPrice": {
				required: "请填写续重价格",
				min: "续重价格必须为零或正数"
			},
			"deliveryType.orderList": {
				digits: "排序必须为零或正整数"
			}
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
})
</script>
</head>
<body class="input">
	<div class="bar">
		<#if isAddAction>添加配送方式<#else>编辑配送方式</#if>
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="<#if isAddAction>delivery_type!save.action<#else>delivery_type!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
				<tr>
					<th>
						配送方式名称: 
					</th>
					<td>
						<input type="text" name="deliveryType.name" class="formText" value="${(deliveryType.name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						配送类型: 
					</th>
					<td>
						<select name="deliveryType.deliveryMethod">
							<#list deliveryMethodList as deliveryMethod>
								<option value="${deliveryMethod}"<#if (deliveryMethod == deliveryType.deliveryMethod)!> selected</#if>>
									${action.getText("DeliveryMethod." + deliveryMethod)}
								</option>
							</#list>
						</select>
					</td>
				</tr>
				<tr>
					<th>
						默认物流公司: 
					</th>
					<td>
						<select name="deliveryType.defaultDeliveryCorp.id">
							<option value="">请选择...</option>
							<#list allDeliveryCorpList as deliveryCorp>
								<option value="${deliveryCorp.id}" <#if (deliveryCorp == deliveryType.defaultDeliveryCorp)!> selected</#if>>
									${deliveryCorp.name}
								</option>
							</#list>
						</select>
					</td>
				</tr>
				<tr>
					<th>
						首重量: 
					</th>
					<td>
						<input type="text" name="deliveryType.firstWeight" class="formText" value="${(deliveryType.firstWeight)!}" title="单位: 克" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						续重量: 
					</th>
					<td>
						<input type="text" name="deliveryType.continueWeight" class="formText" value="${(deliveryType.continueWeight)!}" title="单位: 克" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						首重价格: 
					</th>
					<td>
						<input type="text" name="deliveryType.firstWeightPrice" class="formText" value="${(deliveryType.firstWeightPrice)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						续重价格: 
					</th>
					<td>
						<input type="text" name="deliveryType.continueWeightPrice" class="formText" value="${(deliveryType.continueWeightPrice)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						排序: 
					</th>
					<td>
						<input type="text" name="deliveryType.orderList" class="formText" value="${(deliveryType.orderList)!}" title="只允许输入零或正整数" />
					</td>
				</tr>
				<tr>
					<th>
						介绍: 
					</th>
					<td>
						<textarea name="deliveryType.description" id="editor" class="editor">${(deliveryType.description)!}</textarea>
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