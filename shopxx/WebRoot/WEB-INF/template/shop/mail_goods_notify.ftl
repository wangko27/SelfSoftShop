<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>到货通知 - Powered By SHOP++</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
</head>
<body>
<p>亲爱的${(goodsNotify.member.username)!"顾客"}: </p>
<p>感谢您的光临购物,您之前缺货登记的商品现在已经到货,您可以进入会员中心或者浏览网站进行购买。如有其他问题,请联系我们,祝您购物愉快。</p>
<p>&nbsp;</p>
<p>商品名称: ${goodsNotify.product.name}</p>
<p>货品编号: ${goodsNotify.product.productSn}</p>
<p>
	商品详情: <a href="${setting.shopUrl}${goodsNotify.product.goods.htmlPath}" target="_blank">点击查看</a>
</p>
</body>
</html>