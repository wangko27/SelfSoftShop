<#assign sec=JspTaglibs["/WEB-INF/tld/security.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>管理菜单 - Powered By SHOP++</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body class="menu">
	<div class="body">
		<@sec.authorize ifAnyGranted="ROLE_SETTING,ROLE_INSTANT_MESSAGING">
			<dl>
				<dt>
					<span>网站设置</span>
				</dt>
				<@sec.authorize ifAnyGranted="ROLE_SETTING">
					<dd>
						<a href="setting!edit.action" target="mainFrame">系统设置</a>
					</dd>
				</@sec.authorize>
				<@sec.authorize ifAnyGranted="ROLE_INSTANT_MESSAGING">
					<dd>
						<a href="instant_messaging!edit.action" target="mainFrame">在线客服</a>
					</dd>
				</@sec.authorize>
			</dl>
		</@sec.authorize>
		<@sec.authorize ifAnyGranted="ROLE_PAYMENT_CONFIG">
			<dl>
				<dt>
					<span>支付管理</span>
				</dt>
				<dd>
					<a href="payment_config!list.action" target="mainFrame">支付方式</a>
				</dd>
			</dl>
		</@sec.authorize>
		<@sec.authorize ifAnyGranted="ROLE_DELIVERY_TYPE,ROLE_AREA,ROLE_DELIVERY_CORP">
			<dl>
				<dt>
					<span>配送管理</span>
				</dt>
				<@sec.authorize ifAnyGranted="ROLE_DELIVERY_TYPE">
					<dd>
						<a href="delivery_type!list.action" target="mainFrame">配送方式</a>
					</dd>
				</@sec.authorize>
				<@sec.authorize ifAnyGranted="ROLE_AREA">
					<dd>
						<a href="area!list.action" target="mainFrame">地区管理</a>
					</dd>
				</@sec.authorize>
				<@sec.authorize ifAnyGranted="ROLE_DELIVERY_CORP">
					<dd>
						<a href="delivery_corp!list.action" target="mainFrame">物流公司</a>
					</dd>
				</@sec.authorize>
			</dl>
		</@sec.authorize>
	</div>
</body>
</html>