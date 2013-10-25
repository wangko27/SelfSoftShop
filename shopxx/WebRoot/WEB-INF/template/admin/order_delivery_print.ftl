<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>快递单打印</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<!--[if lte IE 6]>
	<script type="text/javascript" src="${base}/template/common/js/belatedPNG.js"></script>
	<script type="text/javascript">
		// 解决IE6透明PNG图片BUG
		DD_belatedPNG.fix(".belatedPNG");
	</script>
<![endif]-->
<script type="text/javascript">
$().ready(function() {
	
	var $deliveryPrintForm = $("#deliveryPrintForm");
	var $deliveryCenterId = $("#deliveryCenterId");
	var $deliveryTemplateId = $("#deliveryTemplateId");
	var $printButton = $("#printButton");
	
	// 变更发货点
	$deliveryCenterId.change( function() {
		$deliveryPrintForm.submit();
	});
	
	// 变更快递单模板
	$deliveryTemplateId.change( function() {
		$deliveryPrintForm.submit();
	});
	
	$printButton.click(function(){
		window.print();
		return false;
	});

})

try {
	var WScriptShell = new ActiveXObject("WScript.Shell");
	WScriptShell.RegWrite("HKEY_CURRENT_USER\\Software\\Microsoft\\Internet Explorer\\PageSetup\\header", "");
	WScriptShell.RegWrite("HKEY_CURRENT_USER\\Software\\Microsoft\\Internet Explorer\\PageSetup\\footer", "");
} catch(e) {

}
</script>
<style type="text/css" media="screen,print">
<!--

.print .header {
	height: 30px;
	clear: both;
}

.print .printBar {
	width: 100%;
	height: 30px;
	padding: 5px 0px;
	position: fixed;
	top: 0px;
	_position: absolute;
	_top: expression(eval(document.documentElement.scrollTop));
	z-index: 99;
	text-align: center;
	border-bottom: 1px solid #7b7b7b;
	background-color: #e9f0f4;
}

.print .body {
	padding: 20px 20px 60px 20px;
	position: absolute;
	font-size: 12px;
}

-->
</style>
<style type="text/css" media="print">

.print .header {
	display: none;
}

</style>
</head>
<body class="print">
	<div class="header">
		<div class="printBar">
			<form id="deliveryPrintForm" action="order!deliveryPrint.action" method="get">
				<input type="hidden" name="id" value="${id}" />
				发货点:
				<select id="deliveryCenterId" name="deliveryCenter.id">
					<#list allDeliveryCenterList as dc>
						<option value="${dc.id}"<#if dc = deliveryCenter> selected</#if>>
							${dc.name}
						</option>
					</#list>
				</select>
				&nbsp;&nbsp;&nbsp;&nbsp;
				快递单模板:
				<select id="deliveryTemplateId" name="deliveryTemplate.id">
					<#list allDeliveryTemplateList as dt>
						<option value="${dt.id}"<#if dt = deliveryTemplate> selected</#if>>
							${dt.name}
						</option>
					</#list>
				</select>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" id="printButton" class="formButton" value="打 印" />
			</form>
		</div>
	</div>
	<div class="body">
		<@deliveryTemplate.content?interpret />
	</div>
</body>
</html>