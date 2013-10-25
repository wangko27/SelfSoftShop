<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>查看退货单 - Powered By SHOP++</title>
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
		查看退货单
	</div>
	<div class="body">
		<table class="inputTable">
			<tr>
				<th>
					退货编号: 
				</th>
				<td>
					${reship.reshipSn}
				</td>
				<th>
					订单编号: 
				</th>
				<td>
					${(reship.order.orderSn)!}
				</td>
			</tr>
			<tr>
				<th>
					退货日期: 
				</th>
				<td>
					${reship.createDate?string("yyyy-MM-dd HH:mm:ss")}
				</td>
				<th>
					配送方式名称: 
				</th>
				<td>
					${reship.deliveryTypeName}
				</td>
			</tr>
			<tr>
				<th>
					物流公司名称: 
				</th>
				<td>
					${reship.deliveryCorpName}
				</td>
				<th>
					物流单号: 
				</th>
				<td>
					${reship.deliverySn}
				</td>
			</tr>
			<tr>
				<th>
					物流费用: 
				</th>
				<td>
					${reship.deliveryFee?string(currencyFormat)}
				</td>
				<th>
					退货人姓名: 
				</th>
				<td>
					${reship.reshipName}
				</td>
			</tr>
			<tr>
				<th>
					退货地区: 
				</th>
				<td>
					${reship.reshipArea.displayName}
				</td>
				<th>
					退货地址: 
				</th>
				<td>
					${reship.reshipAddress}
				</td>
			</tr>
			<tr>
				<th>
					邮编: 
				</th>
				<td>
					${reship.reshipZipCode}
				</td>
				<th>
					电话: 
				</th>
				<td>
					${reship.reshipPhone}
				</td>
			</tr>
			<tr>
				<th>
					手机: 
				</th>
				<td>
					${reship.reshipMobile}
				</td>
				<th>
					备注: 
				</th>
				<td>
					${reship.memo}
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
				<th>退货数</th>
			</tr>
			<#list reship.deliveryItemSet as deliveryItem>
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