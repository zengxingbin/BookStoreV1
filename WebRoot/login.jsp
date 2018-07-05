<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>电子书城</title>

<link rel="stylesheet" href="css/main.css" type="text/css" />
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

function doLogin() {
	var username = document.getElementsByName("username")[0];
	var password = document.getElementsByName("password")[0];
	var errorMsg = document.getElementById("errorMsg");
	if(username.value == "" || password.value == "") {
		errorMsg.innerHTML = "用户名和密码不能为空！";
	} else {
	
		var rememberUsername = document.getElementsByName("isRememberUsername")[0];
		var isRememberUsername = "false";
		if(rememberUsername.checked == true)
			isRememberUsername = "true";
			
		var autoLogin = document.getElementsByName("isAutoLogin")[0];
		var isAutoLogin = "false";
		if(autoLogin.checked == true)
			isAutoLogin = true;
			
		// 创建XMLHttpRequest对象
		var xmlhttp = createXMLHttpRequest();
		// 注册状态监控回调函数
		xmlhttp.onreadystatechange = function() {
			if(xmlhttp.readyState == 4) {
				if( xmlhttp.status == 200) {
					if(xmlhttp.responseText != "false")
						location.href = "${pageContext.request.contextPath }" + xmlhttp.responseText;
					else
						errorMsg.innerHTML = "用户名或者密码不正确！";
				}else {
					alert("服务器繁忙，请稍后登录！（状态码：" + xmlhttp.status + "）");
				}
				
			} 
		};
		// 建立与服务器的异步连接
		var url = "${pageContext.request.contextPath }/user?action=login&username=" 
									+ username.value + "&password=" + password.value + "&isRememberUsername=" + isRememberUsername + "&isAutoLogin=" + isAutoLogin + 
									"&time=" + new Date().getTime();
		xmlhttp.open("get", url);
		xmlhttp.send(null);
	}
	
}
</script>
</head>

<body class="main">

	<jsp:include page="head.jsp" />
	<jsp:include page="menu_search.jsp" />

	<div id="divcontent">
		<form action="${pageContext.request.contextPath }/user?action=login" method="post">
			<table width="900px" border="0" cellspacing="0">
				<tr>
					<td style="padding:30px"><div style="height:470px">
							<b>&nbsp;&nbsp;首页&nbsp;&raquo;&nbsp;个人用户登录</b>
							<div>
								<table width="85%" border="0" cellspacing="0">
									<tr>
										<td>
											<div id="logindiv">
												<table width="100%" border="0" cellspacing="0">
													<tr>
														<td style="text-align:center; padding-top:20px"><img
															src="images/logintitle.gif" width="150" height="30" />
														</td>
													</tr>
													<tr>
														<td style="text-align:center;padding-top:20px;"><font id="errorMsg"
															color="#ff0000">${requestScope["loginErrorMsg"]}</font>
														</td>
													</tr>
													<tr>
														<td style="text-align:center">
															<table width="80%" border="0" cellspacing="0"
																style="margin-top:15px ;margin-left:auto; margin-right:auto">
																<tr>
																	<td
																		style="text-align:right; padding-top:5px; width:25%">用户名：</td>
																	<td style="text-align:left"><input name="username"
																		type="text" class="textinput" value="${cookie.rememberUserName.value }"/>
																	</td>
																</tr>
																<tr>
																	<td style="text-align:right; padding-top:5px">密&nbsp;&nbsp;&nbsp;&nbsp;码：</td>
																	<td style="text-align:left"><input name="password"
																		type="password" class="textinput" />
																	</td>
																</tr>
																<tr>
																	<td colspan="2" style="text-align:center"><input
																		type="checkbox" name="isRememberUsername" />
																		记住用户名&nbsp;&nbsp; <input type="checkbox"
																		name="isAutoLogin"/> 自动登录</td>
																</tr>
																<tr>
																	<td colspan="2"
																		style="padding-top:10px; text-align:center"><!-- <input
																		name="image" type="image" onclick="return formcheck()"
																		src="images/loginbutton.gif" width="83" height="22" /> -->
																		<a href="javascript:doLogin()"><img name="image"
																			src="images/loginbutton.gif" width="83" height="22"/>
																		</a>
																	</td>
																</tr>

																<tr>
																	<td colspan="2" style="padding-top:10px"><img
																		src="images/loginline.gif" width="241" height="10" />
																	</td>
																</tr>
																<tr>
																	<td colspan="2"
																		style="padding-top:10px; text-align:center"><a
																		href="register.jsp"><img name="image"
																			src="images/signupbutton.gif" width="135" height="33" />
																	</a></td>
																</tr>
															</table>
														</td>
													</tr>
												</table>
											</div></td>
										<td style="text-align:left; padding-top:30px; width:60%"><h1>您还没有注册？</h1>
											<p>注册新会员，享受更优惠价格!</p>
											<p>千种图书，供你挑选！注册即享受丰富折扣和优惠，便宜有好货！超过万本图书任您选。</p>
											<p>超人气社区！精彩活动每一天。买卖更安心！支付宝交易超安全。</p>
											<p style="text-align:right">
												<a href="register.jsp"><img
													src="images/signupbutton.gif" width="135" height="33" /> </a>
											</p>
										</td>
									</tr>
								</table>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>



	<jsp:include page="foot.jsp" />


</body>
</html>
