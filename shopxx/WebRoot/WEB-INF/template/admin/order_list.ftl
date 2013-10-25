<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>订单列表 - Powered By SHOP++</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.pager.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready(function() {

	var $print = $("#listForm .print");
	
	// 打印选择
	$print.change( function() {
		var $this = $(this);
		
		if ($this.val() != "") {
			window.open($this.val());
		}
	});

})
</script>
</head>
<body class="list">
	<div class="bar">
		订单列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="order!list.action" method="post">
			<div class="listBar">
				<label>查找: </label>
				<select name="pager.searchBy">
					<option value="orderSn"<#if pager.searchBy == "orderSn"> selected</#if>>
						订单编号
					</option>
					<option value="member.username"<#if pager.searchBy == "member.username"> selected</#if>>
						用户名
					</option>
					<option value="shipName"<#if pager.searchBy == "shipName"> selected</#if>>
						收货人
					</option>
					<option value="shipAddress"<#if pager.searchBy == "shipAddress"> selected</#if>>
						收货地址
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
						<a href="#" class="sort" name="orderSn" hidefocus>订单编号</a>
					</th>
					<th>
						<a href="#" class="sort" name="member" hidefocus>用户名</a>
					</th>
					<th>
						<a href="#" class="sort" name="totalAmount" hidefocus>订单总额</a>
					</th>
					<th>
						<a href="#" class="sort" name="paymentStatus" hidefocus>付款状态</a>
					</th>
					<th>
						<a href="#" class="sort" name="shippingStatus" hidefocus>配送状态</a>
					</th>
					<th>
						<a href="#" class="sort" name="paymentConfigName" hidefocus>支付方式</a>
					</th>
					<th>
						<a href="#" class="sort" name="deliveryTypeName" hidefocus>配送方式</a>
					</th>
					<th>
						<a href="#" class="sort" name="createDate" hidefocus>下单时间</a>
					</th>
					<th>
						<span>打印</span>
					</th>
					<th>
						<span>操作</span>
					</th>
				</tr>
				<#list pager.result as order>
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${order.id}" />
						</td>
						<td>
							${order.orderSn}
						</td>
						<td>
							${(order.member.username)!"-"}
						</td>
						<td>
							${order.totalAmount?string(currencyFormat)}
						</td>
						<td>
							${action.getText("PaymentStatus." + order.paymentStatus)}
						</td>
						<td>
							${action.getText("ShippingStatus." + order.shippingStatus)}
						</td>
						<td>
							${order.paymentConfigName}
						</td>
						<td>
							${order.deliveryTypeName}
						</td>
						<td>
							<span title="${order.createDate?string("yyyy-MM-dd HH:mm:ss")}">${order.createDate}</span>
						</td>
						<td>
							<select class="print">
								<option value="">
									请选择
								</option>
								<option value="order!orderPrint.action?id=${order.id}">
									订&nbsp;&nbsp;&nbsp;单
								</option>
								<option value="order!goodsPrint.action?id=${order.id}">
									购物单
								</option>
								<option value="order!shippingPrint.action?id=${order.id}">
									配送单
								</option>
								<option value="order!deliveryPrint.action?id=${order.id}">
									快递单
								</option>
							</select>
						</td>
						<td>
							<a href="order!view.action?id=${order.id}" title="查看">[查看]</a>
							<#if order.orderStatus != "completed" && order.orderStatus != "invalid" && order.paymentStatus == "unpaid" && order.shippingStatus == "unshipped">
								<a href="order!edit.action?id=${order.id}" title="编辑">[编辑]</a>
							<#else>
								<span title="订单状态无法编辑">[编辑]</span>
							</#if>
							<a href="order!process.action?id=${order.id}" title="处理">[处理]</a>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.result?size > 0)>
				<div class="pagerBar">
					<div class="delete">
						<input type="button" id="deleteButton" class="formButton" url="order!delete.action" value="删 除" disabled hidefocus />
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