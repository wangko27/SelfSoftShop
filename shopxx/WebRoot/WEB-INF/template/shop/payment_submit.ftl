<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>在线支付</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
</head>
<body onload="javascript: document.forms[0].submit();">
	<form action="${paymentUrl}" method="post">
		<#list parameterMap?keys as key>
			<input type="hidden" name="${key}" value="${parameterMap.get(key)}">
		</#list>
	</form>
</body>
</html>