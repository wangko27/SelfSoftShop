<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${(setting.shopName)!}<#if setting.isShowPoweredInfo> - Powered By SHOP++</#if></title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<#if (setting.metaKeywords)! != ""><meta name="keywords" content="${setting.metaKeywords}" /></#if>
<#if (setting.metaDescription)! != ""><meta name="description" content="${setting.metaDescription}" /></#if>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/shop/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/shop.css" rel="stylesheet" type="text/css" />
<!--[if lte IE 6]>
	<script type="text/javascript" src="${base}/template/common/js/belatedPNG.js"></script>
	<script type="text/javascript">
		// 解决IE6透明PNG图片BUG
		DD_belatedPNG.fix(".belatedPNG");
	</script>
<![endif]-->
</head>
<body class="index">
	<#include "/WEB-INF/template/shop/header.ftl">
	<div class="body">
		<div class="bodyLeft">
			<div class="goodsCategory">
            	<div class="top">商品分类</div>
            	<div class="middle">
            		<ul id="goodsCategoryMenu" class="menu">
            			<@goods_category_tree; goodsCategoryTree>
	            			<#list goodsCategoryTree as firstGoodsCategory>
	            				<li class="mainCategory">
									<a href="${base}${firstGoodsCategory.url}">${firstGoodsCategory.name}</a>
								</li>
								<#if (firstGoodsCategory.children?? && firstGoodsCategory.children?size > 0)>
									<#list firstGoodsCategory.children as secondGoodsCategory>
										<li>
											<a href="${base}${secondGoodsCategory.url}">
												<span class="icon">&nbsp;</span>${secondGoodsCategory.name}
											</a>
										</li>
										<#if secondGoodsCategory_index + 1 == 5>
											<#break />
										</#if>
									</#list>
								</#if>
								<#if firstGoodsCategory_index + 1 == 3>
									<#break />
								</#if>
	            			</#list>
	            		</@goods_category_tree>
					</ul>
            	</div>
                <div class="bottom"></div>
			</div>
		</div>
		<div class="bodyRight">
			<div class="slider">
				<div id="sliderScrollable" class="scrollable">
					<div class="items">
						<div>
							<img src="http://demo.image.shopxx.net/201101/banner1.jpg" />
						</div>
						<div>
							<img src="http://demo.image.shopxx.net/201101/banner2.jpg" />
						</div>
						<div>
							<img src="http://demo.image.shopxx.net/201101/banner3.jpg" />
						</div>
					</div>
					<div class="navi"></div>
					<div class="prevNext">
						<a class="prev browse left"></a>
						<a class="next browse right"></a>
					</div>
				</div>
			</div>
			<div class="blank"></div>
			<div class="hotGoodsSlider">
				<div class="title">
					<strong>热卖商品</strong>HOT
				</div>
				<a class="prev browse"></a>
				<@goods_list type="hot" count=12; goodsList>
					<#if (goodsList?? && goodsList?size > 0)>
						<div id="hotGoodsScrollable" class="scrollable">
							<div class="items">
								<#list goodsList as goods>
									<#if goods_index + 1 == 1>
										<div>
										<ul>
									</#if>
									<li>
										<a href="${base}${goods.htmlPath}">
											<img src="${base}${goods.defaultThumbnailGoodsImagePath}" alt="${goods.name}" />
											<p title="${goods.name}">${substring(goods.name, 18, "...")}</p>
										</a>
									</li>
									<#if (goods_index + 1) % 4 == 0 || !goods_has_next || goods_index + 1 == 12>
										</ul>
										</div>
									</#if>
									<#if (goods_index + 1) % 4 == 0 && goods_has_next>
										<div>
										<ul>
									</#if>
								</#list>
							</div>
						</div>
					</#if>
				</@goods_list>
				<a class="next browse"></a>
			</div>
		</div>
		<div class="blank"></div>
		<img src="http://demo.image.shopxx.net/201101/banner4.jpg" />
		<div class="blank"></div>
		<div class="newGoods">
			<div class="left">
				<ul id="newGoodsTab" class="newGoodsTab">
					<@goods_category_list count=4; goodsCategoryList>
						<#list goodsCategoryList as goodsCategory>
							<li>${goodsCategory.name}</li>
						</#list>
					</@goods_category_list>
				</ul>
			</div>
			<div class="right">
				<@goods_category_list count=4; goodsCategoryList>
					<#list goodsCategoryList as goodsCategory>
						<ul class="newGoodsTabContent hidden">
							<@goods_list goods_category_id=goodsCategory.id type="new" count=4; goodsList>
								<#list goodsList as goods>
									<li>
										<a href="${base}${goods.htmlPath}">
											<img src="${base}${goods.defaultThumbnailGoodsImagePath}" alt="${goods.name}" />
											<p title="${goods.name}">${substring(goods.name, 18, "...")}</p>
										</a>
									</li>
								</#list>
							</@goods_list>
						</ul>
					</#list>
				</@goods_category_list>
			</div>
		</div>
		<div class="blank"></div>
		<div class="bodyLeft">
			<@goods_list type="hot" count=10; goodsList>
				<#if (goodsList?size > 0)>
					<div class="hotGoods">
						<div class="top">热销排行</div>
						<div class="middle">
							<ul>
								<#list goodsList as goods>
									<li class="number${goods_index + 1}">
										<span class="icon">&nbsp;</span>
										<a href="${base}${goods.htmlPath}" title="${goods.name}">${substring(goods.name, 24, "...")}</a>
									</li>
								</#list>
							</ul>
						</div>
						<div class="bottom"></div>
					</div>
					<div class="blank"></div>
				</#if>
			</@goods_list>
			<@article_list type="hot" count=10; articleList>
				<#if (articleList?size > 0)>
					<div class="hotArticle">
						<div class="top">热点文章</div>
						<div class="middle">
							<ul>
								<#list articleList as article>
									<li class="number${article_index + 1}">
										<span class="icon">&nbsp;</span>
										<a href="${base}${article.htmlPath}" title="${article.title}">${substring(article.title, 24, "...")}</a>
									</li>
								</#list>
							</ul>
						</div>
						<div class="bottom"></div>
					</div>
				</#if>
			</@article_list>
		</div>
		<div class="bodyRight">
			<@goods_list type="best" count=12; goodsList>
				<#if (goodsList?size > 0)>
					<div class="bestGoods">
						<div class="top">
							<strong>精品推荐</strong>BEST
						</div>
						<div class="middle">
							<ul>
								<#list goodsList as goods>
									<li>
										<a href="${base}${goods.htmlPath}">
											<img src="${base}${goods.defaultThumbnailGoodsImagePath}" alt="${goods.name}" />
											<p title="${goods.name}">${substring(goods.name, 18, "...")}</p>
											<p class="red">${goods.price?string(currencyFormat)}</p>
										</a>
									</li>
								</#list>
							</ul>
						</div>
						<div class="bottom"></div>
					</div>
				</#if>
			</@goods_list>
		</div>
		<div class="blank"></div>
		<#include "/WEB-INF/template/shop/friend_link.ftl">
	</div>
	<div class="blank"></div>
	<#include "/WEB-INF/template/shop/footer.ftl">
	<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
	<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
	<script type="text/javascript" src="${base}/template/shop/js/base.js"></script>
	<script type="text/javascript" src="${base}/template/shop/js/shop.js"></script>
</body>
</html>