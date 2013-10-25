<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${goods.name}<#if setting.isShowPoweredInfo> - Powered By SHOP++</#if></title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<#if (goods.metaKeywords)! != ""><meta name="keywords" content="${goods.metaKeywords}" /></#if>
<#if (goods.metaDescription)! != ""><meta name="description" content="${goods.metaDescription}" /></#if>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/common/css/jquery.zoomimage.css" rel="stylesheet" type="text/css" />
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
<body id="goodsContent" class="goodsContent">
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
			<div class="blank"></div>
			<div class="hotGoods">
				<div class="top">热销排行</div>
				<div class="middle">
					<ul>
						<@goods_list goods_category_id=goods.goodsCategory.id type="hot" count=10; goodsList>
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
			<div class="listBar">
				<div class="left"></div>
				<div class="middle">
					<div class="path">
						<a href="${base}/" class="shop"><span class="icon">&nbsp;</span>首页</a> &gt;
						<#list pathList as path>
							<a href="${base}${path.url}">${path.name}</a> &gt;
						</#list>
					</div>
				</div>
				<div class="right"></div>
			</div>
			<div class="blank"></div>
			<div class="goodsTop">
				<div class="goodsTopLeft">
					<div class="goodsImage">
						<#if goods.goodsImageList??>
		                	<#list goods.goodsImageList as goodsImage>
		                		<a href="${base}${goodsImage.bigImagePath}" class="tabContent zoom<#if (goodsImage_index > 0)> nonFirst</#if>">
									<img src="${base}${goodsImage.smallImagePath}" alt="点击放大" />
								</a>
							</#list>
						<#else>
	            			<a href="${base}${setting.defaultBigGoodsImagePath}" class="zoom">
								<img src="${base}${setting.defaultSmallGoodsImagePath}" alt="点击放大" />
							</a>
	                	</#if>
                	</div>
					<div class="thumbnailGoodsImage">
						<a class="prev browse" href="javascript: void(0);" hidefocus></a>
						<div id="thumbnailGoodsImageScrollable" class="scrollable">
							<ul id="goodsImageTab" class="items goodsImageTab">
								<#if goods.goodsImageList??>
									<#list goods.goodsImageList as goodsImage>
										<li><img src="${base}${goodsImage.thumbnailImagePath}" alt="${goodsImage.description}" /></li>
									</#list>
								<#else>
									<li><img src="${base}${setting.defaultThumbnailGoodsImagePath}" /></li>
	                        	</#if>
							</ul>
						</div>
						<a class="next browse" href="javascript: void(0);" hidefocus></a>
					</div>
				</div>
				<div class="goodsTopRight">
					<h1 class="title">${substring(goods.name, 50, "...")}</h1>
					<ul class="goodsAttribute">
						<li>商品编号: ${goods.goodsSn}</li>
						<li>货品编号: <span id="productSn">${goods.defaultProduct.productSn}</span></li>
						<#list (goods.goodsType.goodsAttributeSet)! as goodsAttribute>
							<#if goods.getGoodsAttributeValue(goodsAttribute)?? && goods.getGoodsAttributeValue(goodsAttribute) != "">
	                    		<li>${goodsAttribute.name}: ${substring(goods.getGoodsAttributeValue(goodsAttribute), 26)}</li>
							</#if>
						</#list>
					</ul>
					<div class="blank"></div>
					<div class="goodsPrice">
						<div class="left"></div>
						<div class="right">
							<div class="top">
								销 售 价:
								<span id="price" class="price">${goods.price?string(currencyFormat)}</span>
							</div>
							<div class="bottom">
								市 场 价:
								<#if setting.isShowMarketPrice>
									<span id="marketPrice" class="marketPrice">${goods.marketPrice?string(currencyFormat)}</span>
								<#else>
									-
								</#if>
							</div>
						</div>
					</div>
					<div class="blank"></div>
					<table id="buyInfo" class="buyInfo">
						<#if goods.isSpecificationEnabled>
							<#assign specificationValueSet = goods.specificationValueSet>
							<tr class="specificationTips">
								<th id="tipsTitle">请选择:</th>
								<td>
									<div id="tipsContent" class="tipsContent">
										<#list goods.specificationSet as specification>
											${specification.name} 
										</#list>
									</div>
									<div id="closeHighlight" class="closeHighlight" title="关闭"></div>
								</td>
							</tr>
							<#list goods.specificationSet as specification>
								<#if specification.specificationType == "text">
									<tr class="text">
										<th>${specification.name}:</th>
										<td>
											<ul>
												<#list specification.specificationValueList as specificationValue>
													<#if specificationValueSet.contains(specificationValue)>
														<li class="${specificationValue.id}" title="${specificationValue.name}" specificationValueId="${specificationValue.id}">
															${specificationValue.name}
															<span title="点击取消选择"></span>
														</li>
													</#if>
												</#list>
											</ul>
										</td>
									</tr>
								<#else>
									<tr class="image">
										<th>${specification.name}:</th>
										<td>
											<ul>
												<#list specification.specificationValueList as specificationValue>
													<#if specificationValueSet.contains(specificationValue)>
														<li class="${specificationValue.id}" title="${specificationValue.name}" specificationValueId="${specificationValue.id}">
															<#if specificationValue.imagePath??>
																<img src="${base}${specificationValue.imagePath}" alt="${specificationValue.name}" />
															<#else>
																<img src="${base}/template/shop/images/default_specification.gif" />
															</#if>
															<span title="点击取消选择"></span>
														</li>
													</#if>
												</#list>
											</ul>
										</td>
									</tr>
								</#if>
							</#list>
						</#if>
						<tr>
							<th>购买数量:</th>
							<td>
								<input type="text" id="quantity" value="1" />
								<#if setting.scoreType == "goodsSet" && goods.score != 0>
									&nbsp;&nbsp;( 所得积分: ${goods.score} )
								</#if>
							</td>
						</tr>
						<tr>
							<th>&nbsp;</th>
							<td>
								<#if !goods.isSpecificationEnabled && goods.isOutOfStock>
									<input type="button" id="goodsButton" class="goodsNotifyButton" value="" hidefocus />
								<#else>
									<input type="button" id="goodsButton" class="addCartItemButton" value="" hidefocus />
								</#if>
								 <input type="button" id="addFavorite" class="addFavoriteButton" goodsId="${goods.id}" hidefocus />
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="blank"></div>
			<div class="goodsBottom">
				<ul id="goodsParameterTab" class="goodsParameterTab">
					<li>
						<a href="javascript: void(0);" class="current" hidefocus>商品介绍</a>
					</li>
					<li>
						<a href="javascript: void(0);" name="goodsAttribute" hidefocus>商品参数</a>
					</li>
					<#if setting.isCommentEnabled>
						<li>
							<a href="javascript: void(0);" hidefocus>商品评论</a>
						</li>
					</#if>
				</ul>
				<div class="tabContent goodsIntroduction">
					${goods.introduction}
				</div>
				<div class="tabContent goodsAttribute">
					<table class="goodsParameterTable">
						<#list (goods.goodsType.goodsParameterList)! as goodsParameter>
							<#if goods.goodsParameterValueMap.get(goodsParameter.id)?? && goods.goodsParameterValueMap.get(goodsParameter.id) != "">
								<tr>
									<th>
										${goodsParameter.name}
									</th>
									<td>
										${(goods.goodsParameterValueMap.get(goodsParameter.id))!}
									</td>
								</tr>
							</#if>
						</#list>
					</table>
				</div>
				<#if setting.isCommentEnabled>
					<div id="comment" class="tabContent comment">
						<@comment_list goods_id=goods.id count=5; commentList>
							<#list commentList as comment>
								<#assign isHasComment = true />
								<div class="commentItem" id="commentItem${comment.id}">
									<p><span class="red">${(comment.username)!"游客"}</span> ${comment.createDate?string("yyyy-MM-dd HH: mm")} <a href="#commentForm" class="commentReply" forCommentId="${comment.id}">[回复此评论]</a></p>
									<p><pre>${comment.content}</pre></p>
									<#list comment.replyCommentSet as replyComment>
										<#if replyComment.isShow>
											<div class="reply">
												<p><span class="red"><#if replyComment.isAdminReply>管理员<#else>${(replyComment.username)!"游客"}</#if></span> ${replyComment.createDate?string("yyyy-MM-dd HH: mm")}</p>
												<p><pre>${replyComment.content}</pre></p>
											</div>
										</#if>
									</#list>
								</div>
								<#if comment_has_next>
									<div class="blank"></div>
								</#if>
							</#list>
							<#if (commentList?size > 0)>
								<div class="info">
									<a href="${base}/shop/comment_list/${goods.id}.htm">查看所有评论&gt;&gt;</a>
								</div>
							</#if>
						</@comment_list>
						<form id="commentForm" name="commentForm" method="post">
							<input type="hidden" name="comment.goods.id" value="${goods.id}" />
							<input type="hidden" id="forCommentId" name="forCommentId" />
							<table class="sendTable">
								<tr class="title">
									<td width="100">
										<span id="sendTitle">发表评论</span>
									</td>
									<td>
										<a href="javascript: void(0);" id="sendComment" class="sendComment">切换到发表新评论&gt;&gt;</a>
									</td>
								</tr>
								<tr>
									<th>
										评论内容: 
									</th>
									<td>
										<textarea id="commentContent" name="comment.content" class="formTextarea"></textarea>
									</td>
								</tr>
								<tr>
									<th>
										联系方式: 
									</th>
									<td>
										<input type="text" name="comment.contact" class="formText" />
									</td>
								</tr>
								<#if setting.isCommentCaptchaEnabled>
									<tr>
					                	<th>
					                		验证码: 
					                	</th>
					                    <td>
					                    	<input type="text" id="commentCaptcha" name="j_captcha" class="formText captcha" />
					                    	<img id="commentCaptchaImage" class="captchaImage" src="${base}/captcha.jpeg" alt="换一张" />
					                    </td>
					                </tr>
				                </#if>
								<tr>
									<th>
										&nbsp;
									</th>
									<td>
										<input type="submit" class="formButton" value="提交评论" />
									</td>
								</tr>
							</table>
						</form>
					</div>
				</#if>
			</div>
		</div>
		<div class="blank"></div>
		<#include "/WEB-INF/template/shop/friend_link.ftl">
	</div>
	<div class="blank"></div>
	<#include "/WEB-INF/template/shop/footer.ftl">
	<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
	<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
	<script type="text/javascript" src="${base}/template/common/js/jquery.zoomimage.js"></script>
	<script type="text/javascript" src="${base}/template/shop/js/base.js"></script>
	<script type="text/javascript" src="${base}/template/shop/js/shop.js"></script>
	<script type="text/javascript">
	$().ready( function() {
	
		$productSn = $("#productSn");
		$price = $("#price");
		$marketPrice = $("#marketPrice");
		$buyInfo = $("#buyInfo");
		$tipsTitle = $("#tipsTitle");
		$tipsContent = $("#tipsContent");
		$closeHighlight = $("#closeHighlight");
		$specificationValue = $("#buyInfo li");
		$quantity = $("#quantity");
		$goodsButton = $("#goodsButton");
	
		// 添加商品浏览记录
		$.addGoodsHistory("${substring(goods.name, 24, "...")}", "${base}${goods.htmlPath}");
		
		<#if goods.isSpecificationEnabled>
			var productDatas = {};
			<#list goods.productSet as product>
				<#if product.isMarketable>
					productDatas['${product.id}'] = {
						productSn: "${product.productSn}",
						price: "${product.price?string(currencyFormat)}",
						marketPrice: "${product.marketPrice?string(currencyFormat)}",
						isOutOfStock: ${product.isOutOfStock?string("true", "false")}
					};
				</#if>
			</#list>
			
			var specificationValueDatas = {};
			<#list goods.productSet as product>
				<#if product.isMarketable>
					specificationValueDatas['${product.id}'] = new Array(<#list product.specificationValueList as specificationValue>"${specificationValue.id}"<#if specificationValue_has_next>, </#if></#list>);
				</#if>
			</#list>
			
			var specificationValueSelecteds = new Array();
			var selectedProductId = null;
			
			$specificationValue.click(function () {
				var $this = $(this);
				if ($this.hasClass("notAllowed")) {
					return false;
				}
				
				if ($this.hasClass("selected")) {
					$this.removeClass("selected");
				} else {
					$this.addClass("selected");
				}
				$this.siblings("li").removeClass("selected");
				$specificationValue.addClass("notAllowed");
				
				var $specificationValueSelected = $("#buyInfo li.selected");
				if ($specificationValueSelected.length == 1) {
					$specificationValueSelected.siblings("li").removeClass("notAllowed");
				}
				
				var specificationValueSelecteds = new Array();
				selectedProductId = null;
				var tipsContentText = "";
				$specificationValueSelected.each(function(i) {
					var $this = $(this);
					tipsContentText += $this.attr("title") + " ";
					specificationValueSelecteds.push($this.attr("specificationValueId"));
				});
				if (tipsContentText != "") {
					$tipsTitle.text("已选择: ");
					$tipsContent.text(tipsContentText);
				} else {
					$tipsTitle.text("请选择: ");
					$tipsContent.text("<#list goods.specificationSet as specification>${specification.name} </#list>");
				}
				$.each(specificationValueDatas, function(i) {
					if (arrayContains(specificationValueDatas[i], specificationValueSelecteds)) {
						for (var j in specificationValueDatas[i]) {
							$("." + specificationValueDatas[i][j]).removeClass("notAllowed");
						}
					}
					if (arrayEqual(specificationValueDatas[i], specificationValueSelecteds)) {
						$productSn = $productSn.text(productDatas[i].productSn);
						$price = $price.text(productDatas[i].price);
						$marketPrice = $marketPrice.text(productDatas[i].marketPrice);
						selectedProductId = i;
						$buyInfo.removeClass("highlight");
						if (productDatas[i].isOutOfStock) {
							$goodsButton.addClass("goodsNotifyButton");
							$goodsButton.removeClass("addCartItemButton");
						} else {
							$goodsButton.addClass("addCartItemButton");
							$goodsButton.removeClass("goodsNotifyButton");
						}
					}
				});
			});
			
			// 添加商品至购物车/到货通知
			$goodsButton.click(function () {
				var $this = $(this);
				if (selectedProductId != null) {
					if ($this.hasClass("addCartItemButton")) {
						$.addCartItem(selectedProductId, $quantity.val());
					} else {
						location.href = '${base}/shop/goods_notify!add.action?product.id=' + selectedProductId;
					}
				} else {
					$buyInfo.addClass("highlight");
					$tipsTitle.text('系统提示:');
					$tipsContent.text('请选择商品信息!');
				}
			});
			
			// 关闭购买信息提示
			$closeHighlight.click(function () {
				$buyInfo.removeClass("highlight");
				$tipsTitle.html("请选择: ");
				$tipsContent.html("<#list goods.specificationSet as specification>${specification.name} </#list>");
			});
			
			// 判断数组是否包含另一个数组中所有元素
			function arrayContains(array1, array2) {
				if(!(array1 instanceof Array) || !(array2 instanceof Array)) {
					return false;
				}
				if(array1.length < array2.length) {
					return false;
				}
				for (var i in array2) {
					if ($.inArray(array2[i], array1) == -1) {
						return false;
					}
				}
				return true;
			}
			
			// 判断两个数组中的所有元素是否相同
			function arrayEqual(array1, array2) {
				if(!(array1 instanceof Array) || !(array2 instanceof Array)) {
					return false;
				}
				if(array1.length != array2.length) {
					return false;
				}
				for (var i in array2) {
					if ($.inArray(array2[i], array1) == -1) {
						return false;
					}
				}
				return true;
			}
		<#else>
			var selectedProductId = "${goods.defaultProduct.id}";
			
			// 添加商品至购物车/到货通知
			$goodsButton.click(function () {
				var $this = $(this);
				if ($this.hasClass("addCartItemButton")) {
					$.addCartItem(selectedProductId, $quantity.val());
				} else {
					location.href='${base}/shop/goods_notify!add.action?product.id=' + selectedProductId;
				}
			});
		</#if>
	
	})
	</script>
</body>
</html>