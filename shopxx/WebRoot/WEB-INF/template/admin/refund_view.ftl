<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>查看退款单 - Powered By SHOP++</title>
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
		查看退款单
	</div>
	<div class="body">
		<table class="inputTable">
			<tr>
				<th>
					退款编号: 
				</th>
				<td>
					${refund.refundSn}
				</td>
				<th>
					订单编号: 
				</th>
				<td>
					${(refund.order.orderSn)!}
				</td>
			</tr>
			<tr>
				<th>
					退款类型: 
				</th>
				<td>
					${action.getText("PaymentType." + refund.refundType)}
				</td>
				<th>
					退款方式: 
				</th>
				<td>
					${refund.paymentConfigName}
				</td>
			</tr>
			<tr>
				<th>
					退款银行名称: 
				</th>
				<td>
					${refund.bankName}
				</td>
				<th>
					退款银行账号: 
				</th>
				<td>
					${refund.bankAccount}
				</td>
			</tr>
			<tr>
				<th>
					支付金额: 
				</th>
				<td>
					${refund.totalAmount?string(currencyFormat)}
				</td>
				<th>
					收款人: 
				</th>
				<td>
					${refund.payee}
				</td>
			</tr>
			<tr>
				<th>
					操作员: 
				</th>
				<td>
					${refund.operator}
				</td>
				<th>
					备注: 
				</th>
				<td>
					${refund.memo}
				</td>
			</tr>
		</table>
		<div class="buttonArea">
			<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
		</div>
	</div>
</body>
</html>