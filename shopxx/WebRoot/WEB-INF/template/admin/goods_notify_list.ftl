<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>到货通知列表 - Powered By SHOP++</title>
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

	var $sendButton = $("#sendButton");
	var $allCheck = $("#listTable input.allCheck");// 全选复选框
	var $idsCheck = $("#listTable input[name='ids']");// ID复选框
	
	// 无复选框被选中时,发送按钮不可用
	$idsCheck.click( function() {
		var $idsChecked = $("#listTable input[name='ids']:checked");
		if ($idsChecked.size() > 0) {
			$sendButton.attr("disabled", false);
		} else {
			$sendButton.attr("disabled", true)
		}
	});
	
	// 全选
	$allCheck.click( function() {
		if ($(this).attr("checked") == true) {
			$idsCheck.attr("checked", true);
			$sendButton.attr("disabled", false);
		} else {
			$idsCheck.attr("checked", false);
			$sendButton.attr("disabled", true);
		}
	});
	
	// 发送通知
	$sendButton.click( function() {
		var url = "goods_notify!send.action";
		var $idsCheckedCheck = $("#listTable input[name='ids']:checked");
		if (confirm("您确定要发送到货通知吗?") == true) {
			$.ajax({
				url: url,
				data: $idsCheckedCheck.serialize(),
				type: "POST",
				dataType: "json",
				cache: false,
				success: function(data) {
					if (data.status == "success") {
						$idsCheckedCheck.parent().parent().find(".isSent").attr("src", "${base}/template/admin/images/list_true_icon.gif");
					}
					$sendButton.attr("disabled", true);
					$allCheck.attr("checked", false);
					$idsCheckedCheck.attr("checked", false);
					$.message({type: data.status, content: data.message});
				}
			});
		}
	});

})
</script>
</head>
<body class="list">
	<div class="bar">
		到货通知列表&nbsp;总记录数: ${pager.totalCount} (共${pager.pageCount}页)
	</div>
	<div class="body">
		<form id="listForm" action="goods_notify!list.action" method="post">
			<div class="listBar">
				<input type="button" id="sendButton" class="formButton" value="发送通知" disabled />
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
						<a href="#" class="sort" name="product" hidefocus>缺货商品名称</a>
					</th>
					<th>
						<a href="#" class="sort" name="member" hidefocus>会员</a>
					</th>
					<th>
						<a href="#" class="sort" name="email" hidefocus>E-mail</a>
					</th>
					<th>
						<a href="#" class="sort" name="sendDate" hidefocus>通知时间</a>
					</th>
					<th>
						<a href="#" class="sort" name="createDate" hidefocus>登记时间</a>
					</th>
					<th>
						<a href="#" class="sort" name="isSent" hidefocus>已发送</a>
					</th>
					<th>
						<span>缺货状态</span>
					</th>
				</tr>
				<#list pager.result as goodsNotify>
					<#assign product = goodsNotify.product />
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${goodsNotify.id}" />
						</td>
						<td>
							<a href="${base}${product.goods.htmlPath}" target="_blank">
								${product.name}
							</a>
						</td>
						<td>
							${(goodsNotify.member.username)!"-"}
						</td>
						<td>
							${goodsNotify.email}
						</td>
						<td>
							<#if goodsNotify.sendDate??>
								<span title="${goodsNotify.sendDate?string("yyyy-MM-dd HH:mm:ss")}">${goodsNotify.sendDate}</span>
							<#else>
								-
							</#if>
						</td>
						<td>
							<span title="${goodsNotify.createDate?string("yyyy-MM-dd HH:mm:ss")}">
								${goodsNotify.createDate}
							</span>
						</td>
						<td>
							<span class="${goodsNotify.isSent?string('true','false')}Icon">&nbsp;</span>
						</td>
						<td>
							<#if product.isOutOfStock>
								无货
							<#else>
								有货
							</#if>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.result?size > 0)>
				<div class="pagerBar">
					<div class="delete">
						<input type="button" id="deleteButton" class="formButton" url="goods_notify!delete.action" value="删 除" disabled hidefocus />
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