<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>发货单列表 - Powered By SHOP++</title>
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
		发货单列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="shipping!list.action" method="post">
			<div class="listBar">
				<label>查找: </label>
				<select name="pager.searchBy">
					<option value="shippingSn"<#if pager.searchBy == "shippingSn"> selected</#if>>
						发货编号
					</option>
					<option value="deliverySn"<#if pager.searchBy == "deliverySn"> selected</#if>>
						物流单号
					</option>
					<option value="shipName"<#if pager.searchBy == "shipName"> selected</#if>>
						收货人姓名
					</option>
					<option value="shipAreaStore"<#if pager.searchBy == "shipAreaStore"> selected</#if>>
						收货地区
					</option>
				</select>
				<input type="text" name="pager.keyword" value="${pager.keyword!}" />
				<input type="button" id="searchButton" class="formButton" value="搜 索" hidefocus />
				&nbsp;&nbsp;
				<label>每页显示: </label>
				<select name="pager.pageSize" id="pageSize">
					<option value="10"<#if pager.pageSize == 10> selected</#if>>
						10
					</option>
					<option value="20"<#if pager.pageSize == 20> selected</#if>>
						20
					</option>
					<option value="50"<#if pager.pageSize == 50> selected</#if>>
						50
					</option>
					<option value="100"<#if pager.pageSize == 100> selected</#if>>
						100
					</option>
				</select>
			</div>
			<table id="listTable" class="listTable">
				<tr>
					<th class="check">
						<input type="checkbox" class="allCheck" />
					</th>
					<th>
						<a href="#" class="sort" name="shippingSn" hidefocus>发货编号</a>
					</th>
					<th>
						<a href="#" class="sort" name="deliveryTypeName" hidefocus>配送方式名称</a>
					</th>
					<th>
						<a href="#" class="sort" name="deliveryCorpName" hidefocus>物流公司名称</a>
					</th>
					<th>
						<a href="#" class="sort" name="deliverySn" hidefocus>物流单号</a>
					</th>
					<th>
						<a href="#" class="sort" name="deliveryFee" hidefocus>物流费用</a>
					</th>
					<th>
						<a href="#" class="sort" name="shipName" hidefocus>收货人姓名</a>
					</th>
					<th>
						<a href="#" class="sort" name="shipAreaStore" hidefocus>收货地区</a>
					</th>
					<th>
						<a href="#" class="sort" name="createDate" hidefocus>发货时间</a>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as shipping>
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${shipping.id}" />
						</td>
						<td>
							${shipping.shippingSn}
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
						<td>
							<a href="shipping!view.action?id=${shipping.id}" title="查看">[查看]</a>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.result?size > 0)>
				<div class="pagerBar">
					<div class="delete">
						<input type="button" id="deleteButton" class="formButton" url="shipping!delete.action" value="删 除" disabled hidefocus />
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