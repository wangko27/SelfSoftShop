<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑商品类型 - Powered By SHOP++</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");

	var $tab = $("#tab");
	var $goodsAttributeTable = $("#goodsAttributeTable");
	var $addGoodsAttributeButton = $("#addGoodsAttributeButton");
	var $goodsParameterTable = $("#goodsParameterTable");
	var $addGoodsParameterButton = $("#addGoodsParameterButton");
	
	// Tab效果
	$tab.tabs(".tabContent", {
		tabs: "input"
	});
	
	// 增加商品属性
	var goodsAttributeIndex = ${(goodsType.goodsAttributeSet?size)!0};
	$addGoodsAttributeButton.click( function() {
	
		<@compress single_line = true>
			var goodsAttributeTrHtml = 
			'<tr class="goodsAttributeTr">
				<td>
					<input type="text" name="goodsAttributeList[' + goodsAttributeIndex + '].name" class="formText goodsAttributeListName" />
				</td>
				<td>
					<select name="goodsAttributeList[' + goodsAttributeIndex + '].attributeType" class="attributeType">
						<#list attributeTypeList as attributeType>
							<option value="${attributeType}">
								${action.getText("AttributeType." + attributeType)}
							</option>
						</#list>
					</select>
				</td>
				<td>
					<input type="text" name="goodsAttributeList[' + goodsAttributeIndex + '].optionText" class="formText optionText goodsAttributeListOptionText" title="多个可选值.请使用“,”分隔" />
				</td>
				<td>
					<input type="text" name="goodsAttributeList[' + goodsAttributeIndex + '].orderList" class="formText goodsAttributeListOrderList" style="width: 30px;" />
				</td>
				<td>
					<span class="deleteIcon deleteGoodsAttributeIcon" title="删 除">&nbsp;</span>
				</td>
			</tr>';
		</@compress>
		
		if ($goodsAttributeTable.find(".goodsAttributeTr").length >= 20) {
			$.dialog({type: "warn", content: "商品属性个数超出限制!", modal: true, autoCloseTime: 3000});
		} else {
			$goodsAttributeTable.append(goodsAttributeTrHtml);
			goodsAttributeIndex ++;
		}
	});
	
	// 修改商品属性类型
	$("#goodsAttributeTable .attributeType").live("click", function() {
		var $this = $(this);
		var $optionText = $this.parent().parent().find(".optionText")
		if ($this.val() == "filter") {
			$optionText.attr("disabled", false).show();
		} else {
			$optionText.attr("disabled", true).hide();
		}
	});
	
	// 删除商品属性
	$("#goodsAttributeTable .deleteGoodsAttributeIcon").live("click", function() {
		$(this).parent().parent().remove();
	});
	
	// 增加商品参数
	var goodsParameterIndex = ${(goodsType.goodsParameterList?size)!0};
	$addGoodsParameterButton.click( function() {
		
		<@compress single_line = true>
			var goodsParameterTrHtml = 
			'<tr class="goodsParameterTr">
				<td>
					<input type="text" name="goodsParameterList[' + goodsParameterIndex + '].name" class="formText goodsParameterListName" />
				</td>
				<td>
					<input type="text" name="goodsParameterList[' + goodsParameterIndex + '].orderList" class="formText goodsParameterListOrderList" style="width: 30px;" />
				</td>
				<td>
					<span class="deleteIcon deleteGoodsParameterIcon" title="删 除">&nbsp;</span>
				</td>
			</tr>';
		</@compress>
		
		$goodsParameterTable.append(goodsParameterTrHtml);
		goodsParameterIndex ++;
	});
	
	// 删除商品参数
	$("#goodsParameterTable .deleteGoodsParameterIcon").live("click", function() {
		var $this = $(this);
		$this.parent().parent().remove();
	});
	
	// 表单验证
	$validateForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"goodsType.name": {
				required: true
			}
		},
		messages: {
			"goodsType.name": {
				required: "请填写类型名称"
			}
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
	$.validator.addMethod("goodsAttributeListNameRequired", $.validator.methods.required, "请填写商品属性名称");
	$.validator.addMethod("goodsAttributeListOptionTextRequired", $.validator.methods.required, "请填写商品属性可选项");
	$.validator.addMethod("goodsAttributeListOrderListDigits", $.validator.methods.digits, "商品属性排序必须为零或正整数");
	$.validator.addMethod("goodsParameterListNameRequired", $.validator.methods.required, "请填写商品参数名称");
	$.validator.addMethod("goodsParameterListOrderListDigits", $.validator.methods.digits, "商品参数排序必须为零或正整数");
	
	$.validator.addClassRules("goodsAttributeListName", {
		goodsAttributeListNameRequired: true
	});
	$.validator.addClassRules("goodsAttributeListOptionText", {
		goodsAttributeListOptionTextRequired: true
	});
	$.validator.addClassRules("goodsAttributeListOrderList", {
		goodsAttributeListOrderListDigits: true
	});
	$.validator.addClassRules("goodsParameterListName", {
		goodsParameterListNameRequired: true
	});
	$.validator.addClassRules("goodsParameterListOrderList", {
		goodsParameterListOrderListDigits: true
	});

})
</script>
</head>
<body class="input goodsType">
	<div class="bar">
		<#if isAddAction>添加商品类型<#else>编辑商品类型</#if>
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="<#if isAddAction>goods_type!save.action<#else>goods_type!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<ul id="tab" class="tab">
				<li>
					<input type="button" value="基本信息" hidefocus />
				</li>
				<li>
					<input type="button" value="商品品牌" hidefocus />
				</li>
				<li>
					<input type="button" value="商品属性" hidefocus />
				</li>
				<li>
					<input type="button" value="商品参数" hidefocus />
				</li>
			</ul>
			<table class="inputTable tabContent">
				<tr>
					<th>
						类型名称: 
					</th>
					<td>
						<input type="text" name="goodsType.name" class="formText" value="${(goodsType.name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
			</table>
			<table class="inputTable tabContent">
				<tr class="noneHover">
					<th>
						商品品牌: 
					</th>
					<td>
						<div class="brandSelect">
							<ul>
								<#list allBrandList as brand>
									<li>
										<label>
											<input type="checkbox" name="brandList.id" value="${brand.id}"<#if (goodsType.brandSet.contains(brand))!> checked</#if> />${brand.name}
										</label>
									</li>
								</#list>
							</ul>
						</div>
					</td>
				</tr>
				<tr>
					<th>
						&nbsp;
					</th>
					<td>
						<span class="warnInfo"><span class="icon">&nbsp;</span>未选择的商品品牌前台页面无法进行品牌筛选</span>
					</td>
				</tr>
			</table>
			<table id="goodsAttributeTable" class="inputTable tabContent">
				<tr class="noneHover">
					<td colspan="5">
						<input type="button" id="addGoodsAttributeButton" class="formButton" value="增加属性" hidefocus />
					</td>
				</tr>
				<tr class="title">
					<th>
						属性名称
					</th>
					<th>
						属性类型
					</th>
					<th>
						可选项
					</th>
					<th>
						排序
					</th>
					<th>
						删除
					</th>
				</tr>
				<#list (goodsType.goodsAttributeSet)! as goodsAttribute>
					<tr class="goodsAttributeTr">
						<td>
							<input type="hidden" name="goodsAttributeList[${goodsAttribute_index}].id" value="${goodsAttribute.id}" />
							<input type="hidden" name="goodsAttributeList[${goodsAttribute_index}].propertyIndex" value="${goodsAttribute.propertyIndex}" />
							<input type="text" name="goodsAttributeList[${goodsAttribute_index}].name" class="formText goodsAttributeListName" value="${goodsAttribute.name}" />
						</td>
						<td>
							<select name="goodsAttributeList[${goodsAttribute_index}].attributeType" class="attributeType">
								<#list attributeTypeList as attributeType>
									<option value="${attributeType}"<#if (attributeType == goodsAttribute.attributeType)!> selected</#if>>
										${action.getText("AttributeType." + attributeType)}
									</option>
								</#list>
							</select>
						</td>
						<td>
							<#if goodsAttribute.attributeType == "filter">
								<input type="text" name="goodsAttributeList[${goodsAttribute_index}].optionText" class="formText optionText goodsAttributeListOptionText" value="${goodsAttribute.optionText}" title="多个可选值请使用“,”分隔" />
							<#else>
								<input type="text" name="goodsAttributeList[${goodsAttribute_index}].optionText" class="formText hidden optionText goodsAttributeListOptionText" title="多个可选值请使用“,”分隔" disabled />
							</#if>
						</td>
						<td>
							<input type="text" name="goodsAttributeList[${goodsAttribute_index}].orderList" class="formText goodsAttributeListOrderList" value="${goodsAttribute.orderList}" style="width: 30px;" />
						</td>
						<td>
							<span class="deleteIcon deleteGoodsAttributeIcon" title="删 除">&nbsp;</span>
						</td>
					</tr>
				</#list>
			</table>
			<table id="goodsParameterTable" class="inputTable tabContent">
				<tr class="noneHover">
					<td colspan="3">
						<input type="button" id="addGoodsParameterButton" class="formButton" value="增加参数" hidefocus />
					</td>
				</tr>
				<tr class="title">
					<th>
						参数名称
					</th>
					<th>
						排序
					</th>
					<th>
						删除
					</th>
				</tr>
				<#list (goodsType.goodsParameterList)! as goodsParameter>
					<tr class="goodsParameterTr">
						<td>
							<input type="hidden" name="goodsParameterList[${goodsParameter_index}].id" value="${goodsParameter.id}" />
							<input type="text" name="goodsParameterList[${goodsParameter_index}].name" class="formText goodsParameterListName" value="${goodsParameter.name}" />
						</td>
						<td>
							<input type="text" name="goodsParameterList[${goodsParameter_index}].orderList" class="formText goodsParameterListOrderList" value="${goodsParameter.orderList}" style="width: 30px;" />
						</td>
						<td>
							<span class="deleteIcon deleteGoodsParameterIcon" title="删 除">&nbsp;</span>
						</td>
					</tr>
				</#list>
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus />
				&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>