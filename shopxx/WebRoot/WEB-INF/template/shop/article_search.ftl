<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${(pager.keyword)!} 文章搜索结果<#if setting.isShowPoweredInfo> - Powered By SHOP++</#if></title>
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
<body class="articleList">
	<#include "/WEB-INF/template/shop/header.ftl">
	<div class="body">
		<div class="bodyLeft">
			<div class="recommendArticle">
				<div class="top">推荐文章</div>
				<div class="middle">
					<ul>
						<@article_list type="recommend" count=10; articleList>
							<#list articleList as article>
								<li>
									<span class="icon">&nbsp;</span>
									<a href="${base}${article.htmlPath}" title="${article.title}">${substring(article.title, 24, "...")}</a>
								</li>
							</#list>
						</@article_list>
					</ul>
				</div>
				<div class="bottom"></div>
			</div>
			<div class="blank"></div>
			<div class="hotArticle">
				<div class="top">热点文章</div>
				<div class="middle">
					<ul>
						<@article_list type="hot" count=10; articleList>
							<#list articleList as article>
								<li class="number${article_index + 1}">
									<span class="icon">&nbsp;</span>
									<a href="${base}${article.htmlPath}" title="${article.title}">${substring(article.title, 24, "...")}</a>
								</li>
							</#list>
						</@article_list>
					</ul>
				</div>
				<div class="bottom"></div>
			</div>
		</div>
		<div class="bodyRight">
			<div class="listBar">
				<div class="left"></div>
				<div class="middle">
					<div class="path">
						<a href="${base}/" class="home"><span class="icon">&nbsp;</span>首页</a> &gt; 搜索 "${pager.keyword}" 结果列表 [${(pager.result?size)!0}条]
					</div>
					<div id="articleSearch" class="articleSearch">
						<form id="articleSearchForm" action="${base}/shop/article!search.action" method="post">
							<input type="text" name="pager.keyword" id="articleSearchKeyword" class="keyword" value="${pager.keyword}" />
							<input type="submit" class="searchButton" value="" />
						</form>
					</div>
				</div>
				<div class="right"></div>
			</div>
			<div class="blank"></div>
			<div class="articleList">
				<div class="articleListTop"></div>
				<div class="articleListMiddle">
					<ul class="articleListDetail">
						<#list pager.result as article>
                			<li>
                            	<a href="${base}${article.htmlPath}" class="title">
									${substring(article.title, 40, "...")}
								</a>
                                <span class="author">
                                	作者: <#if article.author == "">未知<#else>${article.author}</#if>
                                </span>
                                <span class="createDate">
                                	${article.createDate?string("yyyy-MM-dd")}
                                </span>
                                <div class="contentText">
									${substring(article.contentText, 200, "...")}
									<a href="${base}${article.htmlPath}">[阅读全文]</a>
								</div>
      		        		</li>
                		</#list>
                		<#if (pager.result?size == 0)!>
                			<li class="noRecord">非常抱歉,没有找到相关文章!</li>
                		</#if>
					</ul>
					<div class="blank"></div>
					<@pagination pager=pager baseUrl="/shop/article!search.action">
         				<#include "/WEB-INF/template/shop/pager.ftl">
         			</@pagination>
				</div>
				<div class="articleListBottom"></div>
			</div>
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