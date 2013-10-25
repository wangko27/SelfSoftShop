<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>查看发货单 - Powered By SHOP++</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
</head>
<body class="input">
	<div class="bar">
		查看发货单
	</div>
	<div class="body">
		<table class="inputTable">
			<tr>
				<th>
					发货编号: 
				</th>
				<td>
					${shipping.shippingSn}
				</td>
				<th>
					订单编号: 
				</th>
				<td>
					${(shipping.order.orderSn)!}
				</td>
			</tr>
			<tr>
				<th>
					发货日期: 
				</th>
				<td>
					${shipping.createDate?string("yyyy-MM-dd HH:mm:ss")}
				</td>
				<th>
					配送方式名称: 
				</th>
				<td>
					${shipping.deliveryTypeName}
				</td>
			</tr>
			<tr>
				<th>
					物流公司名称: 
				</th>
				<td>
					${shipping.deliveryCorpName}
				</td>
				<th>
					物流单号: 
				</th>
				<td>
					${shipping.deliverySn}
				</td>
			</tr>
			<tr>
				<th>
					物流费用: 
				</th>
				<td>
					${shipping.deliveryFee?string(currencyFormat)}
				</td>
				<th>
					收货人姓名: 
				</th>
				<td>
					${shipping.shipName}
				</td>
			</tr>
			<tr>
				<th>
					收货地区: 
				</th>
				<td>
					${shipping.shipArea.displayName}
				</td>
				<th>
					收货地址: 
				</th>
				<td>
					${shipping.shipAddress}
				</td>
			</tr>
			<tr>
				<th>
					邮编: 
				</th>
				<td>
					${shipping.shipZipCode}
				</td>
				<th>
					电话: 
				</th>
				<td>
					${shipping.shipPhone}
				</td>
			</tr>
			<tr>
				<th>
					手机: 
				</th>
				<td>
					${shipping.shipMobile}
				</td>
				<th>
					备注: 
				</th>
				<td>
					${shipping.memo}
				</td>
			</tr>
			<tr>
				<td colspan="4">
					&nbsp;
				</td>
			</tr>
		</table>
		<table class="inputTable">
			<tr class="title">
				<th>货号</th>
				<th>商品名称</th>
				<th>发货数</th>
			</tr>
			<#list shipping.deliveryItemSet as deliveryItem>
				<tr>
					<td>
						<a href="${base}${deliveryItem.goodsHtmlPath}" target="_blank">
							${deliveryItem.productSn}
						</a>
					</td>
					<td>
						<a href="${base}${deliveryItem.goodsHtmlPath}" target="_blank">
							${deliveryItem.productName}
						</a>
					</td>
					<td>
						${deliveryItem.deliveryQuantity}
					</td>
				</tr>
			</#list>
		</table>
		<div class="buttonArea">
			<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
		</div>
	</div>
</body>
</html>