<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑商品 - Powered By SHOP++</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/common/editor/kindeditor.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $tab = $("#tab");
	var $goodsForm = $("#goodsForm");
	var $specificationDisabledInfo = $("#infoTable .specificationDisabledInfo");
	var $goodsIsMarketable = $("#goodsIsMarketable");
	
	var $goodsImageTable = $("#goodsImageTable");
	var $addGoodsImageButton = $("#addGoodsImageButton");
	
	var $specificationTab = $("#specificationTab");
	var $specificationTable = $("#specificationTable");
	var $isSpecificationEnabled = $("#isSpecificationEnabled");
	var $specificationValueLoadingIcon  = $("#specificationValueLoadingIcon");
	var $specificationIds = $("#specificationSelect :checkbox");
	var $addProductButton = $("#addProductButton");
	var $productTable = $("#productTable");
	var $productTitle = $("#productTitle");
	var $specificationInsertTh = $("#specificationInsertTh");
	
	var $goodsTypeId = $("#goodsTypeId");
	var $goodsTypeLoadingIcon = $("#goodsTypeLoadingIcon");
	var $goodsAttributeTable = $("#goodsAttributeTable");
	var $goodsParameterTable = $("#goodsParameterTable");

	// Tab效果
	$tab.tabs(".tabContent", {
		tabs: "input"
	});
	
	// 增加商品图片
	var goodsImageIndex = ${(goods.goodsImageList?size)!0};
	$addGoodsImageButton.click( function() {
		
		<@compress single_line = true>
			var goodsImageTrHtml = 
			'<tr class="goodsImageTr">
				<td>
					<input type="file" name="goodsImageFileList[' + goodsImageIndex + ']" class="goodsImageFileList" />
				</td>
				<td>
					<input type="text" name="goodsImageList[' + goodsImageIndex + '].description" class="formText" />
				</td>
				<td>
					<input type="text" name="goodsImageList[' + goodsImageIndex + '].orderList" class="formText goodsImageOrderList" style="width: 50px;" />
				</td>
				<td>
					<span class="deleteIcon deleteGoodsImage" title="删 除">&nbsp;</span>
				</td>
			</tr>';
		</@compress>
		
		$goodsImageTable.append(goodsImageTrHtml);
		goodsImageIndex ++;
	});
	
	// 删除商品图片
	$("#goodsImageTable .deleteGoodsImage").live("click", function() {
		var $this = $(this);
		$.dialog({type: "warn", content: "您确定要删除吗?", ok: "确 定", cancel: "取 消", modal: true, okCallback: deleteGoodsImage});
		function deleteGoodsImage() {
			$this.parent().parent().remove();
		}
	});
	
	// 切换商品规格
	$specificationTab.click( function() {
		if (!$isSpecificationEnabled.attr("checked")) {
			$.dialog({type: "warn", content: "确定启用商品规格?", ok: "确 定", cancel: "取 消", modal: true, okCallback: specificationEnabled});
			function specificationEnabled() {
				$isSpecificationEnabled.attr("checked", true);
				$specificationDisabledInfo.hide().find(":input").attr("disabled", true);
				$specificationTable.find(":checkbox, :button").not($isSpecificationEnabled).attr("disabled", false);
				$productTable.find(":input").attr("disabled", false);
			}
		}
	});
	
	// 开启/关闭商品规格
	$isSpecificationEnabled.click( function() {
		if ($isSpecificationEnabled.attr("checked")) {
			$specificationDisabledInfo.hide().find(":input").attr("disabled", true)
			$specificationTable.find(":checkbox, :button").not($isSpecificationEnabled).attr("disabled", false);
			$productTable.find(":input").attr("disabled", false);
		} else {
			$specificationDisabledInfo.show().find(":input").attr("disabled", false);
			$specificationTable.find(":checkbox, :button").not($isSpecificationEnabled).attr("disabled", true);
			$productTable.find(":input").attr("disabled", true);
		}
	});
	
	// 修改商品规格
	var specificationDatas = {};
	var specificationCheckedDatas = {};
	<#list (goods.specificationSet)! as specification>
		specificationCheckedDatas['${specification.id}'] = ${specification.json};
	</#list>
	$specificationIds.click( function() {
		$this = $(this);
		var id = $this.val();
		var specificationData = specificationDatas[id];
		if(typeof(specificationData) == "undefined") {
			$.ajax({
				url: "goods!ajaxSpecification.action",
				data: {id: id},
				type: "POST",
				dataType: "json",
				async: false,
				cache: false,
				beforeSend: function() {
					$specificationValueLoadingIcon.show();
				},
				success: function(data) {
					specificationDatas[id] = data;
					specificationData = data;
				},
				complete: function() {
					$specificationValueLoadingIcon.hide();
				}
			});
		}

		if ($this.attr("checked") == true) {
			$specificationInsertTh.before('<th class="' + specificationData.id + '">' + specificationData.name + '</th>');
			
			var specificationValueOptionHtml = "";
			$.each(specificationData.specificationValueList, function(i, specificationValue) {
				specificationValueOptionHtml += ('<option value="' + specificationValue.id + '">' + specificationValue.name + '</option>');
			});
			
			$productTitle.nextAll("tr").each(function() {
				var productIndex = $(this).attr("productIndex");
				var specificationValueTdHtml = '<td class="' + specificationData.id + '"><select name="specificationValueIdsList[' + productIndex + ']">' + specificationValueOptionHtml + '</select></td>';
				$(this).find(".specificationInsertTd").before(specificationValueTdHtml);
			});
			
			specificationCheckedDatas[id] = specificationData;
		} else {
			$("." + id).remove();
			var specificationCheckedTempDatas = {};
			$.each(specificationCheckedDatas, function(i, specificationCheckedData) {
				if (specificationCheckedDatas[id].id != specificationCheckedData.id) {
					specificationCheckedTempDatas[i] = specificationCheckedData;
				}
			});
			specificationCheckedDatas = specificationCheckedTempDatas;
		}
	});
	
	// 增加货品
	var productIndex = ${(goods.productSet?size)!0};
	$addProductButton.click( function() {
		
		<@compress single_line = true>
			var productTrHtml = '<tr class="productTr" productIndex="' + productIndex + '">
				<td>
					<input type="text" id="productListProductSn' + productIndex + '" name="productList[' + productIndex + '].productSn" class="formText productListProductSn" style="width: 50px;" title="留空则由系统自动生成" />
				</td>';
		</@compress>
		
		$.each(specificationCheckedDatas, function(i, specificationCheckedData) {
			if (typeof(specificationCheckedData) != "undefined") {
				var specificationValueTdHtml = '<td class="' + specificationCheckedData.id + '"><select name="specificationValueIdsList[' + productIndex + ']">';
				$.each(specificationCheckedData.specificationValueList, function(j, specificationValue) {
					specificationValueTdHtml += ('<option value="' + specificationValue.id + '">' + specificationValue.name + '</option>');
				});
				specificationValueTdHtml += '</select></td>';
				productTrHtml += specificationValueTdHtml;
			}
		});
		
		<@compress single_line = true>
			productTrHtml += 
			'<td class="specificationInsertTd">
					<input type="text" name="productList[' + productIndex + '].price" class="formText productListPrice" value="0" style="width: 50px;" />
				</td>
				<td>
					<input type="text" name="productList[' + productIndex + '].cost" class="formText productListCost" style="width: 50px;" title="前台不会显示,仅供后台使用" />
				</td>
				<td>
					<input type="text" name="productList[' + productIndex + '].marketPrice" class="formText productListMarketPrice" style="width: 50px;" />
				</td>
				<td>
					<input type="text" name="productList[' + productIndex + '].weight" class="formText productListWeight" style="width: 50px;" />
				</td>
				<td>
					<input type="text" name="productList[' + productIndex + '].store" class="formText productListStore" style="width: 50px;" />
				</td>
				<td>
					<input type="text" name="productList[' + productIndex + '].storePlace" class="formText" style="width: 50px;" />
				</td>
				<td>
					<@checkbox name="productList[' + productIndex + '].isDefault" cssClass="productListIsDefault" value="false" />
				</td>
				<td>
					<@checkbox name="productList[' + productIndex + '].isMarketable" cssClass="productListIsMarketable" value="true" />
				</td>
				<td>
					<span class="deleteIcon deleteProduct" title="删 除">&nbsp;</span>
				</td>
			</tr>';
		</@compress>
		
		$productTable.append(productTrHtml);
		
		if ($("#productTable input.productListIsDefault:checked").length == 0) {
			$("#productTable input.productListIsDefault:first").attr("checked", true);
		}
		
		var $productListProductSn = $("#productListProductSn" + productIndex);
		$productListProductSn.rules("add", {
			remote: {
				url: "goods!checkProductSn.action",
				type: "post",
				cache: false,
				dataType: "json",
				data: {
					"defaultProduct.productSn": function () {
						return $productListProductSn.val();
					}
				}
			},
			messages: {
				remote: "货号已存在"
			}
		});
		productIndex ++;
	});
	
	// 删除货品
	$("#productTable .deleteProduct").live("click", function() {
		var $this = $(this);
		$.dialog({type: "warn", content: "您确定要删除吗?", ok: "确 定", cancel: "取 消", modal: true, okCallback: deleteProduct});
		function deleteProduct() {
			$this.parent().parent().remove();
		}
	});
	
	// 修改货品默认
	$("#productTable input.productListIsDefault").live("click", function() {
		var $this = $(this);
		if (!$this.parent().next().find("input.productListIsMarketable").attr("checked")) {
			$.dialog({type: "warn", content: "请选择上架货品为默认货品!", modal: true, autoCloseTime: 3000});
			return false;
		}
		$("#productTable input.productListIsDefault").not($this).attr("checked", false);
	});
	
	// 商品上架
	$goodsIsMarketable.click( function() {
		if ($isSpecificationEnabled.attr("checked") && $("#goodsForm input.productListIsMarketable:checked").size() == 0) {
			$.dialog({type: "warn", content: "货品已全部下架后,商品无法上架!", modal: true, autoCloseTime: 3000});
			return false;
		}
	});
	
	// 货品全部下架
	$("#goodsForm input.productListIsMarketable").live("click", function() {
		var $this = $(this);
		if ($this.parent().prev().find("input.productListIsDefault").attr("checked")) {
			$.dialog({type: "warn", content: "默认货品无法下架!", modal: true, autoCloseTime: 3000});
			return false;
		}
		if ($isSpecificationEnabled.attr("checked") && $("#goodsForm input.productListIsMarketable:checked").size() == 0) {
			$.dialog({type: "warn", content: "货品已全部下架后,商品将自动下架!", modal: true, autoCloseTime: 3000});
			$goodsIsMarketable.attr("checked", false);
		}
	});
	
	// 修改商品类型
	var previousGoodsTypeId = "${(goods.goodsType.id)!}";
	$goodsTypeId.change( function() {
		if (previousGoodsTypeId != "") {
			$.dialog({type: "warn", content: "修改商品类型后当前“属性参数”数据将会丢失,是否继续!", width: 450, ok: "确 定", cancel: "取 消", modal: true, okCallback: goodsTypeChange, cancelCallback: goodsTypeReset});
		} else {
			goodsTypeChange();
		}
		
		function goodsTypeChange() {
			previousGoodsTypeId = $goodsTypeId.val();
			
			if ($goodsTypeId.val() == "") {
				$goodsAttributeTable.hide();
				$goodsParameterTable.hide();
				return;
			}
			
			$.ajax({
				url: "goods!ajaxGoodsAttribute.action",
				data: {id: $goodsTypeId.val()},
				type: "POST",
				dataType: "json",
				async: false,
				cache: false,
				beforeSend: function() {
					$goodsTypeLoadingIcon.show();
					$("#goodsAttributeTable .goodsAttributeTr").remove();
				},
				success: function(data) {
					if (data != null) {
						var goodsAttributeTrHtml = "";
						$.each(data, function(i) {
							if (data[i].attributeType == "filter") {
								var optionHtml = '<option value="">请选择...</option>';
								$.each(data[i].optionList, function(j) {
									<@compress single_line = true>
										optionHtml += 
										'<option value="' + data[i].optionList[j] + '">'
											 + data[i].optionList[j] + 
										'</option>';
									</@compress>
								});
								<@compress single_line = true>
									goodsAttributeTrHtml += 
									'<tr class="goodsAttributeTr">
										<th>' + data[i].name + ': </th>
										<td>
											<select name="goodsAttributeValueMap[\'' + data[i].id + '\']">
												' + optionHtml + '
											</select>
										</td>
									</tr>';
								</@compress>
							} else {
								<@compress single_line = true>
									goodsAttributeTrHtml += 
									'<tr class="goodsAttributeTr">
										<th>' + data[i].name + ': </th>
										<td>
											<input type="text" name="goodsAttributeValueMap[\'' + data[i].id + '\']" class="formText" />
										</td>
									</tr>';
								</@compress>
							}
						});
						$goodsAttributeTable.append(goodsAttributeTrHtml);
						$goodsAttributeTable.show();
					} else {
						$goodsAttributeTable.hide();
					}
				},
				complete: function() {
					$goodsTypeLoadingIcon.hide();
				}
			});
			
			$.ajax({
				url: "goods!ajaxGoodsParameter.action",
				data: {id: $goodsTypeId.val()},
				type: "POST",
				dataType: "json",
				cache: false,
				beforeSend: function() {
					$goodsTypeLoadingIcon.show();
					$("#goodsParameterTable .goodsParameterTr").remove();
				},
				success: function(data) {
					if (data != null) {
						var goodsParameterTrHtml = "";
						$.each(data, function(i) {
							<@compress single_line = true>
								goodsParameterTrHtml += 
								'<tr class="goodsParameterTr">
									<th>' + data[i].name + ': </th>
									<td>
										<input type="text" name="goodsParameterValueMap[\'' + data[i].id + '\']" class="formText" />
									</td>
								</tr>';
							</@compress>
						});
						$goodsParameterTable.append(goodsParameterTrHtml);
						$goodsParameterTable.show();
					} else {
						$goodsParameterTable.hide();
					}
				},
				complete: function() {
					$goodsTypeLoadingIcon.hide();
				}
			});
		}
		
		function goodsTypeReset() {
			$goodsTypeId.val(previousGoodsTypeId);
		}
	});
	
	// 表单验证
	$goodsForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"goods.goodsCategory.id": "required",
			"goods.name": "required",
			"goods.goodsSn": {
				remote: "goods!checkGoodsSn.action<#if (goods.goodsSn)??>?oldValue=${goods.goodsSn?url}</#if>"
			},
			"defaultProduct.productSn": {
				remote: "goods!checkProductSn.action<#if (defaultProduct.productSn)??>?oldValue=${defaultProduct.productSn?url}</#if>"
			},
			"goods.score": {
				required: true,
				digits: true
			},
			"defaultProduct.price": {
				priceRequired: true,
				priceMin: 0
			},
			"defaultProduct.cost": {
				costMin: 0
			},
			"defaultProduct.marketPrice": {
				marketPriceMin: 0
			},
			"defaultProduct.weight": "weightDigits",
			"defaultProduct.store": "storeDigits"
		},
		messages: {
			"goods.goodsCategory.id": "请选择商品分类",
			"goods.name": "请填写商品名称",
			"goods.goodsSn": "商品编号已存在",
			"defaultProduct.productSn": "货号已存在",
			"goods.score": {
				required: "请填写积分",
				digits: "积分必须为零或正整数"
			}
		},
		submitHandler: function(form) {
			if ($isSpecificationEnabled.attr("checked") == true) {
				if ($("#specificationSelect input:checked").size() == 0) {
					$.dialog({type: "warn", content: "请选择至少一种商品规格!", modal: true, autoCloseTime: 3000});
					return false;
				}
				if ($("#productTable .productTr").size() == 0) {
					$.dialog({type: "warn", content: "请选择至少添加一种货品!", modal: true, autoCloseTime: 3000});
					return false;
				}
				if ($("#productTable input.productListIsDefault:checked").length == 0) {
					$.dialog({type: "warn", content: "必须选择一个默认货品!", modal: true, autoCloseTime: 3000});
					return false;
				}
				
				var isProductSnRepeat = false;
				var productSnArray = new Array();
				$("#productTable input.productListProductSn").each(function(i) {
					var $this = $(this);
					if ($.inArray($this.val(), productSnArray) >= 0) {
						isProductSnRepeat = true;
						return false;
					}
					if ($this.val() != "") {
						productSnArray.push($this.val());
					}
				});
				if (isProductSnRepeat) {
					$.dialog({type: "warn", content: "货号重复,请检查!", modal: true, autoCloseTime: 3000});
					return false;
				}
				
				var isSpecificationValueIdsRepeat = false;
				var specificationValueIdsStringArray = new Array();
				$("#productTable tr:gt(0)").each(function(i) {
					var specificationValueIdsString = "";
					$(this).find("select").each(function(i) {
						specificationValueIdsString += $(this).val() + ",";
					});
					if ($.inArray(specificationValueIdsString, specificationValueIdsStringArray) >= 0) {
						isSpecificationValueIdsRepeat = true;
						return false;
					}
					if (specificationValueIdsString != "") {
						specificationValueIdsStringArray.push(specificationValueIdsString);
					}
				});
				if (isSpecificationValueIdsRepeat) {
					$.dialog({type: "warn", content: "货品规格值重复,请检查!", modal: true, autoCloseTime: 3000});
					return false;
				}
			}
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
	<#if (goods.isSpecificationEnabled)!>
		<#list goods.productSet as product>
			var $productListProductSn${product_index} = $("#productListProductSn${product_index}");
			$productListProductSn${product_index}.rules("add", {
				remote: {
					url: "goods!checkProductSn.action?oldValue=${product.productSn?url}",
					type: "post",
					cache: false,
					dataType: "json",
					data: {
						"defaultProduct.productSn": function () {
							return $productListProductSn${product_index}.val();
						}
					}
				},
				messages: {
					remote: "货号已存在"
				}
			});
		</#list>
	</#if>
	
	// 表单验证
	$.validator.addMethod("goodsImageFileListRequired", $.validator.methods.required, "请选择上传商品图片");
	$.validator.addMethod("goodsImageFileListImageFile", $.validator.methods.imageFile, "商品图片格式错误");
	$.validator.addMethod("goodsImageOrderListDigits", $.validator.methods.digits, "商品图片排序必须为零或正整数");
	$.validator.addMethod("priceRequired", $.validator.methods.required, "请填写销售价");
	$.validator.addMethod("priceMin", $.validator.methods.min, "销售价必须为零或正数");
	$.validator.addMethod("costMin", $.validator.methods.min, "成本价必须为零或正数");
	$.validator.addMethod("marketPriceMin", $.validator.methods.min, "市场价必须为零或正数");
	$.validator.addMethod("weightDigits", $.validator.methods.digits, "重量必须为零或正整数");
	$.validator.addMethod("storeDigits", $.validator.methods.digits, "库存必须为零或正整数");
	
	$.validator.addClassRules("goodsImageFileList", {
		goodsImageFileListRequired: true,
		goodsImageFileListImageFile: true
	});
	$.validator.addClassRules("goodsImageOrderList", {
		goodsImageOrderListDigits: true
	});
	$.validator.addClassRules("productListPrice", {
		priceRequired: true,
		priceMin: 0
	});
	$.validator.addClassRules("productListCost", {
		costMin: 0
	});
	$.validator.addClassRules("productListMarketPrice", {
		marketPriceMin: 0
	});
	$.validator.addClassRules("productListWeight", {
		weightDigits: true
	});
	$.validator.addClassRules("productListStore", {
		storeDigits: true
	});
	
})
</script>
<#if (goods.isSpecificationEnabled)!>
	<style type="text/css">
		.specificationDisabledInfo {
			display: none;
		}
	</style>
</#if>
</head>
<body class="input goods">
	<div class="bar">
		<#if isAddAction>添加商品<#else>编辑商品</#if>
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="goodsForm" action="<#if isAddAction>goods!save.action<#else>goods!update.action</#if>" method="post" enctype="multipart/form-data">
			<input type="hidden" name="id" value="${id}" />
			<#if (!goods.isSpecificationEnabled)!>
				<input type="hidden" name="defaultProduct.id" value="${(defaultProduct.id)!}" />
			</#if>
			<ul id="tab" class="tab">
				<li>
					<input type="button" value="基本信息" hidefocus />
				</li>
				<li>
					<input type="button" value="商品描述" hidefocus />
				</li>
				<li>
					<input type="button" value="商品图片" hidefocus />
				</li>
				<li>
					<input type="button" id="specificationTab" value="商品规格" hidefocus />
				</li>
				<li>
					<input type="button" value="属性参数" hidefocus />
				</li>
			</ul>
			<table id="infoTable" class="inputTable tabContent">
				<tr>
					<th>
						商品分类: 
					</th>
					<td>
						<select name="goods.goodsCategory.id">
							<option value="">请选择...</option>
							<#list goodsCategoryTreeList as goodsCategoryTree>
								<option value="${goodsCategoryTree.id}"<#if (goodsCategoryTree == goods.goodsCategory)!> selected</#if>>
									<#if goodsCategoryTree.grade != 0>
										<#list 1..goodsCategoryTree.grade as i>
											&nbsp;&nbsp;
										</#list>
									</#if>
									${goodsCategoryTree.name}
								</option>
							</#list>
						</select>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						商品名称: 
					</th>
					<td>
						<input type="text" name="goods.name" class="formText" value="${(goods.name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						商品编号: 
					</th>
					<td>
						<input type="text" name="goods.goodsSn" class="formText" value="${(goods.goodsSn)!}" title="若留空则由系统自动生成" />
					</td>
				</tr>
				<tr class="specificationDisabledInfo">
					<th>
						货号: 
					</th>
					<td>
						<#if (!goods.isSpecificationEnabled)!>
							<input type="text" name="defaultProduct.productSn" class="formText" value="${(defaultProduct.productSn)!}" title="若留空则由系统自动生成" />
						<#else>
							<input type="text" name="defaultProduct.productSn" class="formText" title="若留空则由系统自动生成" />
						</#if>
					</td>
				</tr>
				<tr>
					<th>
						品牌: 
					</th>
					<td>
						<select name="brandId">
							<option value="">请选择...</option>
							<#list allBrandList as brand>
								<option value="${brand.id}"<#if (brand == goods.brand)!> selected</#if>>
									${brand.name}
								</option>
							</#list>
						</select>
					</td>
				</tr>
				<#if setting.scoreType == "goodsSet">
					<tr>
						<th>
							积分: 
						</th>
						<td>
							<input type="text" name="goods.score" class="formText" value="${(goods.score)!"0"}" />
						</td>
					</tr>
				</#if>
				<tr class="specificationDisabledInfo">
					<th>
						销售价: 
					</th>
					<td>
						<input type="text" name="defaultProduct.price" class="formText" value="${(defaultProduct.price)!"0"}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr class="specificationDisabledInfo">
					<th>
						成本价: 
					</th>
					<td>
						<input type="text" name="defaultProduct.cost" class="formText" value="${(defaultProduct.cost)!}" title="前台不会显示,仅供后台使用" />
					</td>
				</tr>
				<tr class="specificationDisabledInfo">
					<th>
						市场价: 
					</th>
					<td>
						<input type="text" name="defaultProduct.marketPrice" class="formText" value="${(defaultProduct.marketPrice)!}" title="若为空则系统自动计算市场价" />
					</td>
				</tr>
				<tr class="specificationDisabledInfo">
					<th>
						重量: 
					</th>
					<td>
						<input type="text" name="defaultProduct.weight" class="formText" value="${(defaultProduct.weight)!}" title="单位: 克" />
					</td>
				</tr>
				<tr class="specificationDisabledInfo">
					<th>
						库存: 
					</th>
					<td>
						<input type="text" name="defaultProduct.store" class="formText" value="${(goods.store)!}" title="只允许输入零或正整数,为空表示不计库存" />
					</td>
				</tr>
				<tr class="specificationDisabledInfo">
					<th>
						货位: 
					</th>
					<td>
						<input type="text" name="goods.storePlace" class="formText" value="${(goods.storePlace)!}" title="用于记录商品所在的具体仓库位置,便于检索" />				 						
					</td>
				</tr>
				<tr>
					<th>
						设置: 
					</th>
					<td>
						<label>
							<@checkbox id="goodsIsMarketable" name="goods.isMarketable" value="${(goods.isMarketable)!true}" />上架
						</label>
						<label>
							<@checkbox name="goods.isBest" value="${(goods.isBest)!false}" />精品
						</label>
						<label>
							<@checkbox name="goods.isNew" value="${(goods.isNew)!false}" />新品
						</label>
						<label>
							<@checkbox name="goods.isHot" value="${(goods.isHot)!false}" />热销
						</label>
					</td>
				</tr>
				<tr>
					<th>
						页面关键词: 
					</th>
					<td>
						<input type="text" name="goods.metaKeywords" class="formText" value="${(goods.metaKeywords)!}" />
					</td>
				</tr>
				<tr>
					<th>
						页面描述: 
					</th>
					<td>
						<textarea name="goods.metaDescription" class="formTextarea">${(goods.metaDescription)!}</textarea>
					</td>
				</tr>
			</table>
			<table class="inputTable tabContent">
				<tr>
					<td>
						<textarea id="editor" name="goods.introduction" class="editor" style="width: 100%;">${(goods.introduction)!}</textarea>
					</td>
				</tr>
			</table>
			<table id="goodsImageTable" class="inputTable tabContent">
				<tr class="noneHover">
					<td colspan="5">
						<input type="button" id="addGoodsImageButton" class="formButton" value="增加图片" hidefocus />
					</td>
				</tr>
				<tr class="title">
					<th>
						上传图片
					</th>
					<th>
						图片描述
					</th>
					<th>
						排序
					</th>
					<th>
						删除
					</th>
				</tr>
				<#list (goods.goodsImageList)! as goodsImage>
					<tr class="goodsImageTr">
						<td>
							<input type="hidden" name="goodsImageList[${goodsImage_index}].id" value="${goodsImage.id}" />
							<input type="hidden" name="goodsImageList[${goodsImage_index}].path" value="${goodsImage.path}" />
							<input type="hidden" name="goodsImageList[${goodsImage_index}].sourceImageFormatName" value="${goodsImage.sourceImageFormatName}" />
							<a href="${base}${goodsImage.thumbnailImagePath}" title="点击查看" target="_blank">
								<img src="${base}${goodsImage.thumbnailImagePath}" style="width: 50px; height: 50px;" />
							</a>
						</td>
						<td>
							<input type="text" name="goodsImageList[${goodsImage_index}].description" class="formText" value="${goodsImage.description}" />
						</td>
						<td>
							<input type="text" name="goodsImageList[${goodsImage_index}].orderList" class="formText goodsImageOrderList" value="${goodsImage.orderList}" style="width: 50px;" />
						</td>
						<td>
							<span class="deleteIcon deleteGoodsImage" title="删 除">&nbsp;</span>
						</td>
					</tr>
				</#list>
			</table>
			<div class="tabContent">
				<table id="specificationTable" class="inputTable">
					<tr class="noneHover">
						<td colspan="9">
							<label class="red">
								<@checkbox id="isSpecificationEnabled" name="goods.isSpecificationEnabled" value="${(goods.isSpecificationEnabled)!false}" />启用商品规格
							</label>
						</td>
					</tr>
					<tr class="title">
						<th colspan="9">
							请选择商品规格<span id="specificationValueLoadingIcon" class="loadingIcon hidden">&nbsp;</span>
						</th>
					</tr>
					<tr class="noneHover">
						<td colspan="9">
							<div id="specificationSelect" class="specificationSelect">
								<ul>
									<#assign specificationSet = (goods.specificationSet)! />
									<#list allSpecificationList as specification>
										<li>
											<label title="商品规格值: <#list specification.specificationValueList as specificationValue>${specificationValue.name}&nbsp;</#list>">
												<input type="checkbox" name="specificationIds" value="${specification.id}"<#if (specificationSet.contains(specification))!> checked</#if><#if (!goods.isSpecificationEnabled)!true> disabled</#if> />${specification.name}
												<#if specification.memo??>
													<span class="gray">[${specification.memo}]</span>
												</#if>
											</label>
										</li>
									</#list>
								</ul>
							</div>
						</td>
					</tr>
					<tr class="noneHover">
						<td colspan="9">
							<input type="button" id="addProductButton" class="formButton" value="增加货品"<#if (!goods.isSpecificationEnabled)!true> disabled</#if> hidefocus />
						</td>
					</tr>
				</table>
				<table id="productTable" class="inputTable">
					<tr id="productTitle" class="title">
						<th>
							货号
						</th>
						<#list (goods.specificationSet)! as specification>
							<th class="${specification.id}">
								${specification.name}
							</th>
						</#list>
						<th id="specificationInsertTh">
							销售价
						</th>
						<th>
							成本价
						</th>
						<th>
							市场价
						</th>
						<th>
							重量
						</th>
						<th>
							库存
						</th>
						<th>
							货位
						</th>
						<th>
							默认
						</th>
						<th>
							上架
						</th>
						<th>
							删除
						</th>
					</tr>
					<#if (goods.isSpecificationEnabled)!>
						<#list (goods.productSet)! as product>
							<tr class="productTr" productIndex="${product_index}">
								<td>
									<input type="hidden" name="productList[${product_index}].id" class="formText" value="${product.id}" />
									<input type="text" id="productListProductSn${product_index}" name="productList[${product_index}].productSn" class="formText productListProductSn" value="${product.productSn}" style="width: 50px;" title="留空则由系统自动生成" />
								</td>
								<#list (goods.specificationSet)! as specification>
									<td class="${specification.id}">
										<select name="specificationValueIdsList[${product_index}]">
											<#list specification.specificationValueList as specificationValue>
												<option value="${specificationValue.id}"<#if product.specificationValueList.contains(specificationValue)> selected</#if>>
													${specificationValue.name}
												</option>
											</#list>
										</select>
									</td>
								</#list>
								<td class="specificationInsertTd">
									<input type="text" name="productList[${product_index}].price" class="formText productListPrice" value="${product.price}" style="width: 50px;" />
								</td>
								<td>
									<input type="text" name="productList[${product_index}].cost" class="formText productListCost" value="${product.cost}" style="width: 50px;" title="前台不会显示.仅供后台使用" />
								</td>
								<td>
									<input type="text" name="productList[${product_index}].marketPrice" class="formText productListMarketPrice" value="${product.marketPrice}" style="width: 50px;" title="若为空则系统自动计算市场价" />
								</td>
								<td>
									<input type="text" name="productList[${product_index}].weight" class="formText productListWeight" value="${product.weight}" style="width: 50px;" title="单位: 克" />
								</td>
								<td>
									<input type="text" name="productList[${product_index}].store" class="formText productListStore" value="${product.store}" style="width: 50px;" />
								</td>
								<td>
									<input type="text" name="productList[${product_index}].storePlace" class="formText" value="${product.storePlace}" style="width: 50px;" />
								</td>
								<td>
									<@checkbox name="productList[${product_index}].isDefault" cssClass="productListIsDefault" value="${(product.isDefault)!false}" />
								</td>
								<td>
									<@checkbox name="productList[${product_index}].isMarketable" cssClass="productListIsMarketable" value="${(product.isMarketable)!true}" />
								</td>
								<td>
									<#assign hasUnfinishedOrder = false />
									<#assign orderItemSet = product.orderItemSet />
									<#if (orderItemSet?? && orderItemSet?size > 0)>
										<#list orderItemSet as orderItem>
											<#if orderItem.orderStatus != "completed" && orderItem.orderStatus != "invalid">
												<#assign hasUnfinishedOrder = true />
												<#break />
											</#if>
										</#list>
									</#if>
									<#if hasUnfinishedOrder>
										<span class="deleteIcon" title="存在未完成订单,无法删除" disabled>&nbsp;</span>
									<#else>
										<span class="deleteIcon deleteProduct" title="删 除">&nbsp;</span>
									</#if>
								</td>
							</tr>
						</#list>
					</#if>
				</table>
			</div>
			<div class="tabContent">
				<div class="tableItem">
					<table class="inputTable">
						<tr class="title">
							<th>
								请选择商品类型: 
							</th>
							<td>
								<select id="goodsTypeId" name="goodsTypeId">
									<option value="">请选择...</option>
									<#list allGoodsTypeList as goodsType>
										<option value="${goodsType.id}"<#if (goodsType == goods.goodsType)!> selected</#if>>${goodsType.name}</option>
									</#list>
								</select>
								<span id="goodsTypeLoadingIcon" class="loadingIcon hidden">&nbsp;</span>
							</td>
						</tr>
					</table>
					<div class="blank"></div>
					<table id="goodsAttributeTable" class="inputTable<#if (isAddAction || goods.goodsType == null)!> hidden</#if>">
						<tr class="title">
							<th>
								商品属性
							</th>
							<td>
								&nbsp;
							</td>
						</tr>
						<#list (goods.goodsType.goodsAttributeSet)! as goodsAttribute>
							<tr class="goodsAttributeTr">
								<th>${goodsAttribute.name}: </th>
								<td>
									<#if goodsAttribute.attributeType == "filter">
										<select name="goodsAttributeValueMap['${goodsAttribute.id}']">
											<option value="">请选择...</option>
											<#list goodsAttribute.optionList as option>
												<option value="${option}"<#if option == goods.getGoodsAttributeValue(goodsAttribute)> selected</#if>>
													${option}
												</option>
											</#list>
										</select>
									<#else>
										<input type="text" name="goodsAttributeValueMap['${goodsAttribute.id}']" class="formText" value="${(goods.getGoodsAttributeValue(goodsAttribute))!}" />
									</#if>
								</td>
							</tr>
						</#list>
					</table>
					<div class="blank"></div>
					<table id="goodsParameterTable" class="inputTable<#if (isAddAction || goods.goodsType == null)!true> hidden</#if>">
						<tr class="title">
							<th>
								商品参数
							</th>
							<td>
								&nbsp;
							</td>
						</tr>
						<#assign goodsParameterValueMap = (goods.goodsParameterValueMap)! />
						<#list (goods.goodsType.goodsParameterList)! as goodsParameter>
							<tr class="goodsParameterTr">
								<th>${goodsParameter.name}: </th>
								<td>
									<input type="text" name="goodsParameterValueMap['${goodsParameter.id}']" class="formText" value="${(goodsParameterValueMap.get(goodsParameter.id))!}" />
								</td>
							</tr>
						</#list>
					</table>
				</div>
			</div>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
			</div>
		</form>
	</div>
</body>
</html>