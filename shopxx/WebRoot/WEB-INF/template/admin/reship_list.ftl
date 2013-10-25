<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>退货单列表 - Powered By SHOP++</title>
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
		退货单列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="reship!list.action" method="post">
			<div class="listBar">
				<label>查找: </label>
				<select name="pager.searchBy">
					<option value="reshipSn"<#if pager.searchBy == "reshipSn"> selected</#if>>
						退货编号
					</option>
					<option value="deliverySn"<#if pager.searchBy == "deliverySn"> selected</#if>>
						物流单号
					</option>
					<option value="reshipName"<#if pager.searchBy == "reshipName"> selected</#if>>
						退货人姓名
					</option>
					<option value="reshipAreaStore"<#if pager.searchBy == "reshipAreaStore"> selected</#if>>
						退货地区
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
						<a href="#" class="sort" name="reshipSn" hidefocus>退货编号</a>
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
						<a href="#" class="sort" name="reshipName" hidefocus>退货人姓名</a>
					</th>
					<th>
						<a href="#" class="sort" name="reshipAreaStore" hidefocus>退货地区</a>
					</th>
					<th>
						<a href="#" class="sort" name="createDate" hidefocus>退货时间</a>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as reship>
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${reship.id}" />
						</td>
						<td>
							${reship.reshipSn}
						</td>
						<td>
							${reship.deliveryTypeName}
						</td>
						<td>
							${reship.deliveryCorpName}
						</td>
						<td>
							${reship.deliverySn}
						</td>
						<td>
							${reship.deliveryFee?string(currencyFormat)}
						</td>
						<td>
							${reship.reshipName}
						</td>
						<td>
							${reship.reshipArea.displayName}
						</td>
						<td>
							<span title="${reship.createDate?string("yyyy-MM-dd HH:mm:ss")}">${reship.createDate}</span>
						</td>
						<td>
							<a href="reship!view.action?id=${reship.id}" title="查看">[查看]</a>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.result?size > 0)>
				<div class="pagerBar">
					<div class="delete">
						<input type="button" id="deleteButton" class="formButton" url="reship!delete.action" value="删 除" disabled hidefocus />
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