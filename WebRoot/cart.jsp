<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>电子书城</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/main.css" type="text/css" />

<script type="text/javascript">
	function createXMLHttpRequest() {
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}		
		return xmlhttp;
	}
	function changeProductNum(numberChange, isbn, numberId, subtotalId, totalId) {
		// 创建XMLHttpRequest对象
		var xmlhttp = createXMLHttpRequest();
		// 注册状态监控回调函数
		xmlhttp.onreadystatechange = function() {
			if(xmlhttp.readyState == 4) {
				if( xmlhttp.status == 200) {
					var obj = eval("("+ xmlhttp.responseText + ")");
					var numberTag = document.getElementById(numberId);
					var subtotal = document.getElementById(subtotalId);
					var total = document.getElementById(totalId);
					numberTag.value = obj.number + "";	
					subtotal.innerHTML = obj.subtotal + "";
					total.innerHTML = obj.total + "";
				}else {
					alert("服务器繁忙！（状态码：" + xmlhttp.status + "）");
				}
				
			} 
		};
		// 建立与服务器的异步连接
		var url = "${pageContext.request.contextPath }/book?action=changeCart&isbn=" + isbn + 
														"&numberChange=" + numberChange + "&time=" + new Date().getTime();
		xmlhttp.open("get", url);
		xmlhttp.send(null);
	}
	
	function deleteCart(cartId, isbn, totalId) {
		// 创建XMLHttpRequest对象
		var xmlhttp = createXMLHttpRequest();
		// 注册状态监控回调函数
		xmlhttp.onreadystatechange = function() {
			if(xmlhttp.readyState == 4) {
				if( xmlhttp.status == 200) {
					var cart = document.getElementById(cartId);
					cart.parentNode.removeChild(cart);
					var total = document.getElementById(totalId);
					total.innerHTML = xmlhttp.responseText;	
					return 0;				
				}else {
					alert("服务器繁忙！（状态码：" + xmlhttp.status + "）");
				}
				
			} 
		};
		// 建立与服务器的异步连接
		var url = "${pageContext.request.contextPath }/book?action=deleteCart&isbn=" + isbn + "&time=" + new Date().getTime();
		xmlhttp.open("get", url);
		xmlhttp.send(null);	
	}
</script>


</head>

<body class="main">

	<jsp:include page="head.jsp" />

	<jsp:include page="menu_search.jsp" />


	<div id="divpagecontent">
		<table width="100%" border="0" cellspacing="0">
			<tr>

				<td><div style="text-align:right; margin:5px 10px 5px 0px">
						<a href="index.html">首页</a>&nbsp;&nbsp;&nbsp;&nbsp;&gt;&nbsp;&nbsp;&nbsp;&nbsp;购物车
					</div>

					<table cellspacing="0" class="infocontent">
						<tr>
							<td><img src="ad/page_ad.jpg" width="666" height="89" />
								<table width="100%" border="0" cellspacing="0">
									<tr>
										<td><img src="images/buy1.gif" width="635" height="38" />
										</td>
									</tr>
									<tr>
										<td>
											<table cellspacing="1" class="carttable">
												<tr>
													<td width="10%">序号</td>
													<td width="30%">商品名称</td>
													<td width="10%">价格</td>
													<td width="20%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数量</td>
													<td width="10%">库存</td>
													<td width="10%">小计</td>
													<td width="10%">取消</td>
												</tr>
											</table> 
												
												<c:set var="total" value="0"></c:set>
												<table width="100%" border="0" cellspacing="0">
													
													<c:forEach var="entry" items="${cart }" varStatus="vs">
														<tr id="cart${vs.count }">
															<td width="10%">${vs.count}</td>
															<td width="30%">${entry.key.name }</td>
	
															<td width="10%">${entry.key.price }</td>
															<td width="20%"><input type="button" value='-'
																style="width:20px"
																onclick="changeProductNum(-1, '${entry.key.isbn }', 'number${vs.count}', 'subtotal${vs.count }', 'total')">
	
																<input id="number${vs.count }" type="text" value="${entry.value }"
																style="width:40px;text-align:center" /> <input
																type="button" value='+' style="width:20px"
																onclick="changeProductNum(1, '${entry.key.isbn }', 'number${vs.count }', 'subtotal${vs.count}', 'total')">
	
															</td>
															<td width="10%">${entry.key.pnum }</td>
															<td id="subtotal${vs.count }" width="10%">${entry.key.price*entry.value }</td>
	
															<td width="10%"><a href="javascript:void(deleteCart('cart${vs.count }', '${entry.key.isbn }', 'total'))"
																style="color:#FF0000; font-weight:bold">X</a></td>
														</tr>
														<c:set var="total" value="${total+entry.key.price*entry.value  }"></c:set>
													</c:forEach>
													
												</table>
												


											<table cellspacing="1" class="carttable">
												<tr>
													<td style="text-align:right; padding-right:40px;"><font id="total"
														style="color:#FF6600; font-weight:bold">合计：&nbsp;&nbsp;${total}元</font>
													</td>
												</tr>
											</table>
											<div style="text-align:right; margin-top:10px">
												<a
													href="${pageContext.request.contextPath}/${page == null ? 'index.jsp' : 'product_list.jsp'}"><img
													src="images/gwc_jx.gif" border="0" /> </a>

												&nbsp;&nbsp;&nbsp;&nbsp;<a
													href="${pageContext.request.contextPath}/order.jsp"><img
													src="images/gwc_buy.gif" border="0" /> </a>
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table></td>
			</tr>
		</table>
	</div>



	<jsp:include page="foot.jsp" />


</body>
</html>
