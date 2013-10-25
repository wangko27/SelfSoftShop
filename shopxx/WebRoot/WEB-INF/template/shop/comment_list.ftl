<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>商品评论<#if setting.isShowPoweredInfo> - Powered By SHOP++</#if></title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/shop/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/shop.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/template/shop/js/base.js"></script>
<script type="text/javascript" src="${base}/template/shop/js/shop.js"></script>
<!--[if lte IE 6]>
	<script type="text/javascript" src="${base}/template/common/js/belatedPNG.js"></script>
	<script type="text/javascript">
		// 解决IE6透明PNG图片BUG
		DD_belatedPNG.fix(".belatedPNG");
	</script>
<![endif]-->
</head>
<body class="singlePage commentList">
	<#include "/WEB-INF/template/shop/header.ftl">
	<div class="body">
		<div class="titleBar">
			<div class="left"></div>
			<div class="middle">
				<span class="icon">&nbsp;</span>商品评论 [ <a href="${base}${goods.htmlPath}">${goods.name}</a> ]
			</div>
			<div class="right"></div>
		</div>
		<div class="blank"></div>
		<div class="singlePageDetail">
			<div id="comment">
				<#list pager.result as comment>
					<div class="commentItem" id="commentItem${comment.id}">
						<p>
							<span class="red">${(comment.username)!"游客"}</span>
							${comment.createDate?string("yyyy-MM-dd HH: mm")}
							<a href="#commentForm" class="commentReply" forCommentId="${comment.id}">[回复此评论]</a>
						</p>
						<p>
							<pre>${comment.content}</pre>
						</p>
						<#list comment.replyCommentSet as replyComment>
							<#if replyComment.isShow>
								<div class="reply">
									<p>
										<span class="red">
											<#if replyComment.isAdminReply>管理员<#else>${(replyComment.username)!"游客"}</#if>
										</span>
										${replyComment.createDate?string("yyyy-MM-dd HH: mm")}
									</p>
									<p>
										<pre>${replyComment.content}</pre>
									</p>
								</div>
							</#if>
						</#list>
					</div>
					<#if comment_has_next>
						<div class="blank"></div>
					</#if>
				</#list>
				<#if (pager.result?size > 0)>
					<div class="blank"></div>
					<@pagination pager=pager baseUrl="/shop/comment_list/${goods.id}.htm">
         				<#include "/WEB-INF/template/shop/pager.ftl">
         			</@pagination>
				<#else>
					<div class="commentItem">
						暂无商品评论!
					</div>
				</#if>
				<div class="blank"></div>
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
		</div>
		<div class="blank"></div>
		<#include "/WEB-INF/template/shop/friend_link.ftl">
	</div>
	<div class="blank"></div>
	<#include "/WEB-INF/template/shop/footer.ftl">
</body>
</html>