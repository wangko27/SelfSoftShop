<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>发货点列表 - Powered By SHOP++</title>
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
		发货点管理&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="delivery_center!list.action" method="post">
			<div class="listBar">
				<input type="button" class="formButton" onclick="location.href='delivery_center!add.action'" value="添加发货点" hidefocus />
			</div>
			<table id="listTable" class="listTable">
				<tr>
					<th class="check">
						<input type="checkbox" class="allCheck" />
					</th>
					<th>
						<a href="#" class="sort" name="name" hidefocus>发货点名称</a>
					</th>
					<th>
						<a href="#" class="sort" name="consignor" hidefocus>发货人</a>
					</th>
					<th>
						<a href="#" class="sort" name="areaStore" hidefocus>地区</a>
					</th>
					<th>
						<a href="#" class="sort" name="address" hidefocus>地址</a>
					</th>
					<th>
						<a href="#" class="sort" name="isDefault" hidefocus>是否默认</a>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as deliveryCenter>
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${deliveryCenter.id}" />
						</td>
						<td>
							${deliveryCenter.name}
						</td>
						<td>
							${deliveryCenter.consignor}
						</td>
						<td>
							${deliveryCenter.area.name}
						</td>
						<td>
							${deliveryCenter.address}
						</td>
						<td>
							<span class="${deliveryCenter.isDefault?string('true','false')}Icon">&nbsp;</span>
						</td>
						<td>
							<a href="delivery_center!edit.action?id=${deliveryCenter.id}" title="编辑">[编辑]</a>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.result?size > 0)>
				<div class="pagerBar">
					<div class="delete">
						<input type="button" id="deleteButton" class="formButton" url="delivery_center!delete.action" value="删 除" disabled hidefocus />
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