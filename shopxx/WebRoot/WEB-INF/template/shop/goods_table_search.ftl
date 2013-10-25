<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${(pager.keyword)!} 商品搜索结果<#if setting.isShowPoweredInfo> - Powered By SHOP++</#if></title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<meta name="keywords" content="${pager.keyword}" />
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
<body class="goodsList">
	<#include "/WEB-INF/template/shop/header.ftl" />
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
			<div class="blank"></div>
			<div class="hotGoods">
				<div class="top">热销排行</div>
				<div class="middle">
					<ul>
						<@goods_list type="hot" count=10; goodsList>
							<#list goodsList as goods>
								<li class="number${goods_index + 1}">
									<span class="icon">&nbsp;</span>
									<a href="${base}${goods.htmlPath}" title="${goods.name}">${substring(goods.name, 24, "...")}</a>
								</li>
							</#list>
						</@goods_list>
					</ul>
				</div>
				<div class="bottom"></div>
			</div>
			<div class="blank"></div>
			<div id="goodsHistory" class="goodsHistory">
				<div class="top">浏览记录</div>
				<div class="middle">
					<ul id="goodsHistoryListDetail"></ul>
				</div>
				<div class="bottom"></div>
			</div>
		</div>
		<div class="bodyRight">
			<form id="goodsListForm" action="${base}/shop/goods!search.action" method="post">
				<input type="hidden" id="viewType" name="viewType" value="tableType" />
				<input type="hidden" id="pageNumber" name="pager.pageNumber" value="${pager.pageNumber}" />
				<input type="hidden" name="pager.keyword" value="${pager.keyword}" />
				<div class="listBar">
					<div class="left"></div>
					<div class="middle">
						<div class="path">
							<a href="${base}/" class="shop"><span class="icon">&nbsp;</span>首页</a> &gt; 搜索 "${pager.keyword}" 结果列表
						</div>
						<div class="total">共计: ${pager.totalCount} 款商品</div>
					</div>
					<div class="right"></div>
				</div>
				<div class="blank"></div>
				<div class="operateBar">
					<div class="left"></div>
					<div class="middle">
						<span class="tableDisabledIcon">&nbsp;</span>列表
						<span class="pictureIcon">&nbsp;</span><a id="pictureType" class="pictureType" href="#">图片</a>
						<span class="separator">&nbsp;</span>
						<select id="orderType" name="orderType">
							<option value="default"<#if orderType == "default"> selected="selected"</#if>>默认排序</option>
							<option value="priceAsc"<#if orderType == "priceAsc"> selected="selected"</#if>>价格从低到高</option>
							<option value="priceDesc"<#if orderType == "priceDesc"> selected="selected"</#if>>价格从高到低</option>
							<option value="dateAsc"<#if orderType == "dateAsc"> selected="selected"</#if>>按上架时间排序</option>
	                    </select>
	                    <span class="separator">&nbsp;</span> 显示数量: 
	                    <select name="pager.pageSize" id="pageSize">
							<option value="12" <#if pager.pageSize == 12>selected="selected" </#if>>
								12
							</option>
							<option value="20" <#if pager.pageSize == 20>selected="selected" </#if>>
								20
							</option>
							<option value="60" <#if pager.pageSize == 60>selected="selected" </#if>>
								60
							</option>
							<option value="120" <#if pager.pageSize == 120>selected="selected" </#if>>
								120
							</option>
						</select>
					</div>
					<div class="right"></div>
				</div>
				<div class="blank"></div>
				<div class="goodsTableList">
					<ul class="goodsListDetail">
						<#list pager.result as goods>
							<li>
								<a href="${base}${goods.htmlPath}" class="goodsImage" target="_blank">
									<img src="${base}${goods.defaultThumbnailGoodsImagePath}" alt="${goods.name}" />
								</a>
								<div class="goodsTitle">
									<a href="${base}${goods.htmlPath}" alt="${goods.name}" target="_blank">
										${substring(goods.name, 50, "...")}
									</a>
								</div>
								<div class="goodsRight">
									<div class="goodsPrice">
										<span class="price">${goods.price?string(currencyFormat)}</span>
										<#if setting.isShowMarketPrice>
											<span class="marketPrice">${goods.marketPrice?string(currencyFormat)}</span>
										</#if>
									</div>
									<div class="buttonArea">
										<a href="${base}${goods.htmlPath}" class="addCartItemButton">购买</a>
										<input type="button" class="addFavoriteButton addFavorite" value="收 藏" goodsId="${goods.id}" hidefocus />
									</div>
								</div>
							</li>
						</#list>
						<#if (pager.result?size == 0)!>
                			<li class="noRecord">非常抱歉,没有找到相关商品!</li>
                		</#if>
					</ul>
					<div class="blank"></div>
         			<#assign parameterMap = {"orderType": (orderType)!, "viewType": (viewType)!} />
					<@pagination pager=pager baseUrl="/shop/goods!search.action" parameterMap = parameterMap>
         				<#include "/WEB-INF/template/shop/pager.ftl">
         			</@pagination>
				</div>
			</form>
		</div>
		<div class="blank"></div>
		<#include "/WEB-INF/template/shop/friend_link.ftl" />
	</div>
	<div class="blank"></div>
	<#include "/WEB-INF/template/shop/footer.ftl" />
	<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
	<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
	<script type="text/javascript" src="${base}/template/shop/js/base.js"></script>
	<script type="text/javascript" src="${base}/template/shop/js/shop.js"></script>
</body>
</html>