<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑商品分类 - Powered By SHOP++</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.translate.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready( function() {

	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	
	var $validateForm = $("#validateForm");
	var $goodsCategoryName = $("#goodsCategoryName");
	var $goodsCategorySign = $("#goodsCategorySign");
	var $goodsCategorySignLoadingIcon = $("#goodsCategorySignLoadingIcon");

	$goodsCategoryName.change( function() {
		var $this = $(this);
		$this.translate("zh", "en", {
			data: true,
			replace: false,
			start: function() {
				$goodsCategorySignLoadingIcon.show();
			},
			complete: function() {
				var goodsCategorySignValue = $.trim($this.data("translation").en.value.toLowerCase()).replace(/\s+/g, "_").replace(/[^\w]+/g, "");
				$goodsCategorySign.val(goodsCategorySignValue);
				$goodsCategorySignLoadingIcon.hide();
			},
			error: function() {
				$goodsCategorySignLoadingIcon.hide();
			}
		});

	});
	
	// 表单验证
	$validateForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"goodsCategory.name": "required",
			"goodsCategory.sign": {
				required: true,
				alphanumeric: true,
				<#if isAddAction>
					remote: "goods_category!checkSign.action"
				<#else>
					remote: "goods_category!checkSign.action?oldValue=${goodsCategory.sign?url}"
				</#if>
			},
			"goodsCategory.orderList": "digits"
		},
		messages: {
			"goodsCategory.name": "请填写分类名称",
			"goodsCategory.sign": {
				required: "请填写标识",
				alphanumeric: "标识只允许包含英文、数字和下划线",
				remote: "标识已存在"
			},
			"goodsCategory.orderList": "排序必须为零或正整数"
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
		<#if isAddAction>添加商品分类<#else>编辑商品分类</#if>
	</div>
	<div id="validateErrorContainer" class="validateErrorContainer">
		<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
		<ul></ul>
	</div>
	<div class="body">
		<form id="validateForm" action="<#if isAddAction>goods_category!save.action<#else>goods_category!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
				<tr>
					<th>
						分类名称: 
					</th>
					<td>
						<input type="text" id="goodsCategoryName" name="goodsCategory.name" class="formText" value="${(goodsCategory.name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						上级分类: 
					</th>
					<td>
						<#if isAddAction>
							<select name="parentId">
								<option value="">顶级分类</option>
								<#list goodsCategoryTreeList as goodsCategoryTree>
									<option value="${goodsCategoryTree.id}">
										<#if goodsCategoryTree.grade != 0>
											<#list 1..goodsCategoryTree.grade as i>
												&nbsp;&nbsp;
											</#list>
										</#if>
										${goodsCategoryTree.name}
									</option>
								</#list>
							</select>
						<#else>
							${(goodsCategory.parent.name)!'顶级分类'}
						</#if>
					</td>
				</tr>
				<tr>
					<th>
						商品类型: 
					</th>
					<td>
						<select name="goodsCategory.goodsType.id">
							<option value="">通用商品类型</option>
							<#list allGoodsTypeList as goodsType>
								<option value="${goodsType.id}"<#if (goodsType == goodsCategory.goodsType)!> selected</#if>>
									${goodsType.name}
								</option>
							</#list>
						</select>
					</td>
				</tr>
				<tr>
					<th>
						标识: 
					</th>
					<td>
						<input type="text" id="goodsCategorySign" name="goodsCategory.sign" class="formText" value="${(goodsCategory.sign)!}" title="该分类的唯一标识,用于分类路径和模板标识" />
						<label class="requireField">*</label>
						<span id="goodsCategorySignLoadingIcon" class="loadingIcon hidden">&nbsp;</span>
					</td>
				</tr>
				<tr>
					<th>
						排序: 
					</th>
					<td>
						<input type="text" name="goodsCategory.orderList" class="formText" value="${(goodsCategory.orderList)!}" title="只允许输入零或正整数" />
					</td>
				</tr>
				<tr>
					<th>
						页面关键词: 
					</th>
					<td>
						<input type="text" name="goodsCategory.metaKeywords" class="formText" value="${(goodsCategory.metaKeywords)!}" />
					</td>
				</tr>
				<tr>
					<th>
						页面描述: 
					</th>
					<td>
						<textarea name="goodsCategory.metaDescription" class="formTextarea">${(goodsCategory.metaDescription)!}</textarea>
					</td>
				</tr>
				<tr>
					<th>
						&nbsp;
					</th>
					<td>
						<span class="warnInfo"><span class="icon">&nbsp;</span>页面关键词、页面描述可以更好的使用户通过搜索引擎搜索到站点</span>
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