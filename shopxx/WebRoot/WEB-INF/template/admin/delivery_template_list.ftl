<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>快递单模板列表 - Powered By SHOP++</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
</head>
<body class="list">
	<div class="bar">
		快递单模板管理&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="delivery_template!list.action" method="post">
			<div class="listBar">
				<input type="button" class="formButton" onclick="location.href='delivery_template!add.action'" value="添加模板" hidefocus />
			</div>
			<table id="listTable" class="listTable">
				<tr>
					<th class="check">
						<input type="checkbox" class="allCheck" />
					</th>
					<th>
						<a href="#" class="sort" name="name" hidefocus>模板名称</a>
					</th>
					<th>
						<a href="#" class="sort" name="isDefault" hidefocus>是否默认</a>
					</th>
					<th>
						<span>备注</span>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as deliveryTemplate>
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${deliveryTemplate.id}" />
						</td>
						<td>
							${deliveryTemplate.name}
						</td>
						<td>
							<span class="${deliveryTemplate.isDefault?string('true','false')}Icon">&nbsp;</span>
						</td>
						<td>
							${deliveryTemplate.memo}
						</td>
						<td>
							<a href="delivery_template!edit.action?id=${deliveryTemplate.id}" title="编辑">[编辑]</a>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.result?size > 0)>
				<div class="pagerBar">
					<div class="delete">
						<input type="button" id="deleteButton" class="formButton" url="delivery_template!delete.action" value="删 除" disabled hidefocus />
					</div>
					<div class="pager">
						<#include "/WEB-INF/template/admin/pager.ftl" />
					</div>
				<div>
			<#else>
				<div class="noRecord">没有找到任何记录!</div>
			</#if>
		</form>
	</div>
</body>
</html>