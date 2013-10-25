<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>收件箱<#if setting.isShowPoweredInfo> - Powered By SHOP++</#if></title>
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
<script type="text/javascript">
$().ready( function() {

	var $deleteMessage = $("#messageTable .deleteMessage");
	var $showMessage = $("#messageTable .showMessage");
	
	// 删除
	$deleteMessage.click( function() {
		var $this = $(this);
		var messageId = $this.attr("messageId");
		$.dialog({type: "warn", content: "您确定要删除吗?", ok: "确 定", cancel: "取 消", modal: true, okCallback: deleteMessage});
		function deleteMessage() {
			$.ajax({
				url: "message!ajaxDelete.action",
				data: {id: messageId},
				type: "POST",
				dataType: "json",
				cache: false,
				success: function(data) {
					$.message({type: data.status, content: data.message});
					$this.parent().parent().remove();
				}
			});
		}
		return false;
	});
	
	// 显示消息内容
	$showMessage.click( function() {
		var $this = $(this);
		var $messageContentTr = $this.parent().parent().next(".messageContentTr");
		if ($.trim($messageContentTr.find("td").text()) == "") {
			var messageId = $this.attr("messageId");
			$.ajax({
				url: "message!ajaxShowMessage.action",
				data: {"id": messageId},
				type: "POST",
				dataType: "json",
				async: false,
				cache: false,
				beforeSend: function(data) {
					$messageContentTr.find("td").html('<span class="loadingIcon">&nbsp;</span> 加载中...');
				},
				success: function(data) {
					if (data.status == "success") {
						$messageContentTr.find("td").html(htmlEscape(data.content));
					} else {
						$.message({type: data.status, content: data.message});
					}
				}
			});
		}
		var $showMessageContentIcon = $this.prev("span");
		if ($showMessageContentIcon.hasClass("downIcon")) {
			$messageContentTr.show();
			$showMessageContentIcon.removeClass("downIcon").addClass("upIcon");
		} else {
			$messageContentTr.hide();
			$showMessageContentIcon.removeClass("upIcon").addClass("downIcon");
		}
		return false;
	});
	
})
</script>
<style type="text/css">
<!--

.messageInbox .messageContentTr {
	display: none;
	background-color: #fafafa;
}

.messageInbox .messageContentTr td {
	padding-left: 30px;
}

-->
</style>
</head>
<body class="memberCenter">
	<#include "/WEB-INF/template/shop/header.ftl">
	<div class="body messageInbox">
		<div class="bodyLeft">
			<div class="memberInfo">
				<div class="top"></div>
				<div class="middle">
					<p>欢迎您!&nbsp;&nbsp;<span class="username">${loginMember.username}</span>&nbsp;&nbsp;[<a class="userLogout" href="member!logout.action"">退出</a>]</p>
					<p>会员等级: <span class="red"> ${loginMember.memberRank.name}</span></p>
				</div>
				<div class="bottom"></div>
			</div>
			<div class="blank"></div>
			<div class="memberMenu">
				<div class="top">
					<a href="member_center!index.action">会员中心首页</a>
				</div>
				<div class="middle">
					<ul>
	                	<li class="order">
	                    	<ul>
	                        	<li><a href="order!list.action">我的订单</a></li>
	                        </ul>
	                    </li>
	                    <li class="category favorite">
	                    	<ul>
	                        	<li><a href="favorite!list.action">商品收藏</a></li>
	                        	<li><a href="goods_notify!list.action">缺货登记</a></li>
	                        </ul>
	                    </li>
	                  	<li class="message">
	                    	<ul>
	                        	<li><a href="message!send.action">发送消息</a></li>
	                            <li class="current"><a href="message!inbox.action">收件箱</a></li>
	                            <li><a href="message!draftbox.action">草稿箱</a></li>
	                            <li><a href="message!outbox.action">发件箱</a></li>
	                        </ul>
	                    </li>
	                    <li class="profile">
	                    	<ul>
	                        	<li><a href="profile!edit.action">个人信息</a></li>
	                            <li><a href="password!edit.action">修改密码</a></li>
	                            <li><a href="receiver!list.action">收货地址</a></li>
	                        </ul>
	                    </li>
	                    <li class="deposit">
	                    	<ul>
	                    		<li><a href="deposit!list.action">我的预存款</a></li>
	                        	<li><a href="deposit!recharge.action">预存款充值</a></li>
	                        </ul>
	                    </li>
	                </ul>
				</div>
				<div class="bottom"></div>
			</div>
		</div>
		<div class="bodyRight">
			<div class="memberCenterDetail">
				<div class="top">收件箱</div>
				<div class="middle">
					<div class="blank"></div>
					<table id="messageTable" class="listTable">
						<tr>
							<th>标题</th>
							<th>发件人</th>
							<th>已读</th>
							<th>时间</th>
							<th>操作</th>
						</tr>
						<#list pager.result as message>
							<tr>
								<td>
									<span class="downIcon">&nbsp;</span>
									<a href="#" class="showMessage" messageId="${message.id}">${message.title}</a>
								</td>
								<td>
									${(message.fromMember.username)!"管理员"}
								</td>
								<td>
									<#if message.isRead>
										是
									<#else>
										<span class="red">否</span>
									</#if>
								</td>
								<td>
									${message.createDate?string("yyyy-MM-dd HH: mm")}
								</td>
								<td>
									<a href="${base}/shop/message!reply.action?id=${message.id}">回复</a>
									<a href="#" class="deleteMessage" messageId="${message.id}">删除</a>
								</td>
							</tr>
							<tr class="messageContentTr">
								<td colspan="5"></td>
							</tr>
						</#list>
					</table>
					<div class="blank"></div>
					<@pagination pager=pager baseUrl="/shop/message!inbox.action">
         				<#include "/WEB-INF/template/shop/pager.ftl">
         			</@pagination>
				</div>
				<div class="bottom"></div>
			</div>
		</div>
		<div class="blank"></div>
		<#include "/WEB-INF/template/shop/friend_link.ftl">
	</div>
	<div class="blank"></div>
	<#include "/WEB-INF/template/shop/footer.ftl">
</body>
</html>