<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>订单详情<#if setting.isShowPoweredInfo> - Powered By SHOP++</#if></title>
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
<body class="memberCenter">
	<#include "/WEB-INF/template/shop/header.ftl">
	<div class="body orderList">
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
	                        	<li class="current"><a href="order!list.action">我的订单</a></li>
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
	                            <li><a href="message!inbox.action">收件箱</a></li>
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
				<div class="top">订单详情[${order.orderSn}]</div>
				<div class="middle">
					<div class="blank"></div>
					<div class="operationInfo">
						<strong class="green">订单状态 [订单编号: ${order.orderSn}]</strong>
						<#if order.orderStatus == "unprocessed">
							<p class="red">等待订单处理</p>
						<#elseif order.orderStatus == "completed">
							<p class="red">您的订单已完成</p>
						<#elseif order.orderStatus == "invalid">
							<p class="red">您的订单已作废</p>
						</#if>
						<#if order.orderStatus != "completed" && order.orderStatus != "invalid">
							<#if order.paymentConfig??>
								<#if order.paymentStatus == "unpaid">
									<form action="payment!submit.action" method="post">
										<#if order.paymentConfig.paymentConfigType == "deposit">
											<input type="hidden" name="paymentType" value="deposit" />
										<#elseif  order.paymentConfig.paymentConfigType == "offline">
											<input type="hidden" name="paymentType" value="offline" />
										<#else>
											<input type="hidden" name="paymentType" value="online" />
										</#if>
										<input type="hidden" name="order.id" value="${order.id}" />
										<p class="red">您尚未完成订单支付</p>
										<input type="submit" class="formButton" value="立即支付" />
									</form>
								<#elseif order.paymentStatus == "partPayment">
									<form action="payment!submit.action" method="post">
										<#if order.paymentConfig.paymentConfigType == "deposit">
											<input type="hidden" name="paymentType" value="deposit" />
										<#elseif  order.paymentConfig.paymentConfigType == "offline">
											<input type="hidden" name="paymentType" value="offline" />
										<#else>
											<input type="hidden" name="paymentType" value="online" />
										</#if>
										<input type="hidden" name="order.id" value="${order.id}" />
										<p class="red">您尚未完成订单支付</p>
										<input type="submit" class="formButton" value="支付余款" />
									</form>
								<#else>
									<p class="red">${action.getText("PaymentStatus." + order.paymentStatus)}</p>
								</#if>
							</#if>
							<#if order.shippingStatus != "unshipped">
								<p class="red">${action.getText("ShippingStatus." + order.shippingStatus)}</p>
							</#if>
						</#if>
					</div>
					<div class="blank"></div>
					<table class="listTable">
						<tr>
							<th colspan="4">收货信息</th>
						</tr>
						<tr>
							<td class="title">收货人: </td>
							<td>${order.shipName}</td>
							<td class="title">收货地区: </td>
							<td>${order.shipArea.displayName}</td>
						</tr>
						<tr>
							<td class="title">收货地址: </td>
							<td>${order.shipAddress}</td>
							<td class="title">邮编: </td>
							<td>${order.shipZipCode}</td>
						</tr>
						<tr>
							<td class="title">电话: </td>
							<td>${order.shipPhone}</td>
							<td class="title">手机: </td>
							<td>${order.shipMobile}</td>
						</tr>
						<tr>
							<td class="title">附言: </td>
							<td colspan="3">${order.memo}</td>
						</tr>
					</table>
					<div class="blank"></div>
					<table class="listTable">
						<tr>
							<th colspan="4">支付信息</th>
						</tr>
						<tr>
							<td class="title">配送方式: </td>
							<td>${order.deliveryTypeName}</td>
							<td class="title">支付方式: </td>
							<td>${order.paymentConfigName}</td>
						</tr>
						<tr>
							<td class="title">商品重量: </td>
							<td>${order.totalProductWeight} 克</td>
							<td class="title">配送费用: </td>
							<td><span class="red">${order.deliveryFee?string(currencyFormat)}</span></td>
						</tr>
					</table>
					<div class="blank"></div>
					<div class="blank"></div>
					<table class="listTable">
						<tr>
							<th>商品名称</th>
							<th>购买价格</th>
							<th>购买数量</th>
							<th>小计</th>
						</tr>
						<#list order.orderItemSet as orderItem>
							<tr>
								<td>
									<a href="${base}${orderItem.goodsHtmlPath}" target="_blank">
										${orderItem.productName}
									</a>
								</td>
								<td>
									${orderItem.productPrice?string(currencyFormat)}
								</td>
								<td>
									${orderItem.productQuantity}
								</td>
								<td>
									<span class="subtotalPrice">${orderItem.subtotalPrice?string(currencyFormat)}</span>
								</td>
							</tr>
						</#list>
						<tr>
							<td class="info" colspan="4">
								商品总金额: <span class="red">${order.totalProductPrice?string(currencyFormat)}</span>&nbsp;&nbsp;
								配送费用: <span class="red">${order.deliveryFee?string(currencyFormat)!}</span>&nbsp;&nbsp;
								支付手续费: <span class="red">${order.paymentFee?string(currencyFormat)!}</span>&nbsp;&nbsp;
								订单总金额: <span class="red">${order.totalAmount?string(currencyFormat)}</span>
							</td>
						</tr>
					</table>
					<div class="blank"></div>
					<table class="listTable">
						<tr>
							<th colspan="5">订单日志</th>
						</tr>
						<tr>
							<th>序号</th>
							<th>日志类型</th>
							<th>日志信息</th>
							<th>操作时间</th>
							<th>操作人</th>
						</tr>
						<#list order.orderLogSet as orderLog>
							<tr>
								<td>${orderLog_index + 1}</td>
								<td>${action.getText("OrderLogType." + orderLog.orderLogType)}</td>
								<td>${orderLog.info!"-"}</td>
								<td>${orderLog.createDate?string("yyyy-MM-dd HH:mm")}</td>
								<td>${orderLog.operator!"[用户]"}</td>
							</tr>
						</#list>
					</table>
					<div class="blank"></div>
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