<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>查看商品评论 - Powered By SHOP++</title>
<meta name="Author" content="SHOP++ Team" />
<meta name="Copyright" content="SHOP++" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/admin/css/base.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.tools.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/base.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/admin.js"></script>
<script type="text/javascript">
$().ready(function() {
	
	var $validateErrorContainer = $("#validateErrorContainer");
	var $validateErrorLabelContainer = $("#validateErrorContainer ul");
	var $validateForm = $("#validateForm");
	var $showComment = $("#showComment");
	var $hiddenComment = $("#hiddenComment");
	var $deleteReply = $(".deleteReply");
	var $showReply = $(".showReply");
	var $hiddenReply = $(".hiddenReply");
	
	$hiddenComment.click( function() {
		var $this = $(this);
		$.dialog({type: "warn", content: "您确定要关闭显示吗?", ok: "确 定", cancel: "取 消", modal: true, okCallback: hiddenComment});
		function hiddenComment() {
			$.ajax({
				url: "comment!ajaxHiddenComment.action",
				data: {"id": "${comment.id}"},
				type: "POST",
				dataType: "json",
				cache: false,
				success: function(data) {
					$this.hide();
					$this.prev().show();
					$.message({type: data.status, content: data.message});
				}
			});
		}
	});
	
	$showComment.click( function() {
		var $this = $(this);
		$.dialog({type: "warn", content: "您确定要显示到页面吗?", ok: "确 定", cancel: "取 消", modal: true, okCallback: showComment});
		function showComment() {
			$.ajax({
				url: "comment!ajaxShowComment.action",
				data: {"id": "${comment.id}"},
				type: "POST",
				dataType: "json",
				cache: false,
				success: function(data) {
					$this.hide();
					$this.next().show();
					$.message({type: data.status, content: data.message});
				}
			});
		}
	});
	
	$deleteReply.click( function() {
		var $this = $(this);
		var replyId = $this.attr("replyId");
		$.dialog({type: "warn", content: "您确定要删除吗?", ok: "确 定", cancel: "取 消", modal: true, okCallback: deleteReply});
		function deleteReply() {
			$.ajax({
				url: "comment!ajaxDeleteReply.action",
				data: {"id": replyId},
				type: "POST",
				dataType: "json",
				cache: false,
				success: function(data) {
					if (data.status == "success") {
						$("." + replyId).remove();
					}
					$.message({type: data.status, content: data.message});
				}
			});
		}
	});
	
	$hiddenReply.click( function() {
		var $this = $(this);
		var replyId = $this.attr("replyId");
		$.dialog({type: "warn", content: "您确定要关闭显示吗?", ok: "确 定", cancel: "取 消", modal: true, okCallback: hiddenReply});
		function hiddenReply() {
			$.ajax({
				url: "comment!ajaxHiddenReply.action",
				data: {"id": replyId},
				type: "POST",
				dataType: "json",
				cache: false,
				success: function(data) {
					$this.hide();
					$this.prev().show();
					$.message({type: data.status, content: data.message});
				}
			});
		}
	});
	
	$showReply.click( function() {
		var $this = $(this);
		var replyId = $this.attr("replyId");
		$.dialog({type: "warn", content: "您确定要显示到页面吗?", ok: "确 定", cancel: "取 消", modal: true, okCallback: showReply});
		function showReply() {
			$.ajax({
				url: "comment!ajaxShowReply.action",
				data: {"id": replyId},
				type: "POST",
				dataType: "json",
				cache: false,
				success: function(data) {
					$this.hide();
					$this.next().show();
					$.message({type: data.status, content: data.message});
				}
			});
		}
	});
	
	// 表单验证
	$validateForm.validate({
		errorContainer: $validateErrorContainer,
		errorLabelContainer: $validateErrorLabelContainer,
		wrapper: "li",
		errorClass: "validateError",
		ignoreTitle: true,
		rules: {
			"comment.content": "required"
		},
		messages: {
			"comment.content": "请输入回复内容"
		},
		submitHandler: function(form) {
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
})
</script>
</head>
<body class="input">
	<div class="bar">查看商品评论</div>
	<div class="body">
		<div id="validateErrorContainer" class="validateErrorContainer">
			<div class="validateErrorTitle">以下信息填写有误,请重新填写</div>
			<ul></ul>
		</div>
		<form id="validateForm" action="comment!reply.action" method="post">
			<input type="hidden" name="id" value="${comment.id}" />
			<div class="tableItem">
				<table class="inputTable">
					<tr>
						<th colspan="2" class="title">
							商品评论
						</th>
					</tr>
					<tr>
						<th>
							评论人: 
						</th>
						<td>
							${(comment.username)!"游客"}
							<#if comment.isShow>
								<a href="javascript: void(0);" id="showComment" class="red hidden">[显示到页面]</a>
								<a href="javascript: void(0);" id="hiddenComment" class="red">[关闭显示]</a>
							<#else>
								<a href="javascript: void(0);" id="showComment" class="red">[显示到页面]</a>
								<a href="javascript: void(0);" id="hiddenComment" class="red hidden">[关闭显示]</a>
							</#if>
						</td>
					</tr>
					<tr>
						<th>
							评论商品: 
						</th>
						<td>
							<a href="${base}${comment.goods.htmlPath}" target="_blank" title="${comment.goods.name}">
								${substring(comment.goods.name, 60, "...")}
							</a>
						</td>
					</tr>
					<tr>
						<th>
							内容: 
						</th>
						<td>
							${comment.content}
						</td>
					</tr>
					<tr>
						<th>
							联系方式: 
						</th>
						<td>
							${comment.contact}
						</td>
					</tr>
					<tr>
						<th>
							评论日期: 
						</th>
						<td>
							${comment.createDate?string("yyyy-MM-dd HH:mm:ss")}
						</td>
					</tr>
					<tr>
						<th>
							IP: 
						</th>
						<td>
							${comment.ip}
						</td>
					</tr>
				</table>
				<#if (comment.replyCommentSet?? && comment.replyCommentSet?size > 0)>
					<div class="blank"></div>
					<table class="inputTable">
						<tr>
							<th colspan="2" class="title">回复</th>
						</tr>
						<#list comment.replyCommentSet as replyComment>
							<tr class="${replyComment.id}">
								<th>
									回复人: 
								</th>
								<td>
									<#if replyComment.isAdminReply>
										管理员
									<#else>
										${(replyComment.username)!"游客"}
									</#if>
									<a href="javascript: void(0);" class="red deleteReply" replyId="${replyComment.id}">[删除]</a>
									<#if replyComment.isShow>
										<a href="javascript: void(0);" class="red hidden showReply" replyId="${replyComment.id}">[显示到页面]</a>
										<a href="javascript: void(0);" class="red hiddenReply" replyId="${replyComment.id}">[关闭显示]</a>
									<#else>
										<a href="javascript: void(0);" class="red showReply" replyId="${replyComment.id}">[显示到页面]</a>
										<a href="javascript: void(0);" class="red hidden hiddenReply" replyId="${replyComment.id}">[关闭显示]</a>
									</#if>
								</td>
							</tr>
							<tr class="${replyComment.id}">
								<th>
									回复内容: 
								</th>
								<td>
									${replyComment.content}
								</td>
							</tr>
							<tr class="${replyComment.id}">
								<th>
									回复时间: 
								</th>
								<td>
									${replyComment.createDate?string("yyyy-MM-dd HH: mm")}
								</td>
							</tr>
							<#if replyComment_has_next>
								<tr class="${replyComment.id}">
									<td colspan="2">
										&nbsp;
									</td>
								</tr>
							</#if>
						</#list>
					</table>
				</#if>
				<div class="blank"></div>
				<table class="inputTable">
					<tr>
						<th colspan="2" class="title">
							管理员回复
						</th>
					</tr>
					<tr>
						<th>
							回复内容: 
						</th>
						<td>
							<textarea name="comment.content" class="formTextarea"></textarea>
						</td>
					</tr>
				</table>
				<div class="buttonArea">
					<input type="submit" class="formButton" value="确  定" hidefocus />&nbsp;&nbsp;
					<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus />
				</div>
			</div>
		</form>
	</div>
</body>
</html>