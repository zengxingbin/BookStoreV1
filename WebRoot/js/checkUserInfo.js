//��֤�û������ע����Ϣ

function createXMLHttpRequest() {
	var xmlhttp;
	if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	} else {// code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}		
	return xmlhttp;
}

function showMessage(messageId, result, rightMessage, errorMessage) {
	var messageSpan = window.document.getElementById(messageId);
	if(result) {
		messageSpan.innerHTML = rightMessage.fontcolor("green");
	} else {
		messageSpan.innerHTML = errorMessage.fontcolor("red");
	}
}

function checkFormat(input, regExpStr) {
	var regExp = new RegExp(regExpStr);
	return regExp.test(input.value);
}

function checkFormatsByName(name) {
	var result = false;
	var inputs = window.document.getElementsByName(name);
	for(var i = 0; i < inputs.length; ++i) {
		result = inputs[i].checked || result;
	}
	return result;
}

function checkEmail(obj, appPath) {
	// 获取font标签
	var emailMessage = document.getElementById("emailMessage");
	if(obj.value != "") {
		// 检查邮箱格式是否正确
		var regExpStr = "^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$";
		if(checkFormat(obj, regExpStr)) {
			// 创建XMLHttpRequest对象
			var xmlhttp = createXMLHttpRequest();
			// 注册状态监控回调函数
			xmlhttp.onreadystatechange = function() {
				if(xmlhttp.readyState == 4) {
					if( xmlhttp.status == 200) {
						if(xmlhttp.responseText != "true")
							emailMessage.innerHTML = "<font color='red'>邮箱已注册</font>";
						else
							emailMessage.innerHTML = "<font color='green'>邮箱正确</font>";
					}else {
						alert("服务器繁忙，请稍后注册！（状态码：" + xmlhttp.status + "）");
					}
					
				} 
			};
			// 建立与服务器的异步连接
			var url = appPath + "/user?action=checkEmail&email=" + obj.value;
			xmlhttp.open("get", url);
			xmlhttp.send(null);
		} else {
			emailMessage.innerHTML = "<font color='red'>邮箱格式错误</font>";				
		}
		
	} else {
		emailMessage.innerHTML = "请输入有效的邮箱地址";	
	}
}

/*function checkUsername(obj) {
	var result = false;
	if(obj.value != "") {
		var regExpStr = "^[\\w\\d]{6,}$";
		result = checkFormat(obj, regExpStr);
		showMessage("usernameMessage", result, "用户名格式正确", "用户名格式错误");
	} else {
		window.document.getElementById("usernameMessage").innerHTML = "用户名设置至少6位";
	}
	
	return result;
}*/

function checkUsername(obj, appPath) {
	var usernameMessage = window.document.getElementById("usernameMessage");
	if(obj.value != "") {
		var regExpStr = "^[\\w\\d]{6,}$";
		if(checkFormat(obj, regExpStr)) {
			// 创建XMLHttpRequest对象
			var xmlhttp = createXMLHttpRequest();
			// 注册状态监控回调函数
			xmlhttp.onreadystatechange = function() {
				if(xmlhttp.readyState == 4) {
					if( xmlhttp.status == 200) {
						if(xmlhttp.responseText != "true")
							usernameMessage.innerHTML = "<font color='red'>用户名已存在</font>";
						else
							usernameMessage.innerHTML = "<font color='green'>用户名正确</font>";
					}else {
						alert("服务器繁忙，请稍后注册！（状态码：" + xmlhttp.status + "）");
					}
					
				} 
			};
			// 建立与服务器的异步连接
			var url = appPath + "/user?action=checkUserName&username=" + obj.value;
			xmlhttp.open("get", url);
			xmlhttp.send(null);
		} else {
			usernameMessage.innerHTML = "<font color='red'>用户名格式错误</font>";
		}
	} else {
		usernameMessage.innerHTML = "用户名设置至少6位";
	}
}

function checkPassword(obj) {
	if(obj.value != "") {
		var regExpStr = "^[\\w\\d]{6,}$";
		result = checkFormat(obj, regExpStr);
		showMessage("pwdMessage", result, "密码格式正确", "密码格式错误");
	} else {
		window.document.getElementById("pwdMessage").innerHTML = "密码设置至少6位";
	}
	result = result || checkRePassword();
}

function checkRePassword(obj) {
	var password = window.document.getElementsByName("password")[0];
	var result = false;
	if(obj.value != "" && password.value != "") {
		result = (obj.value == password.value); 
		showMessage("rePwdMessage", result, "密码正确", "密码错误");
	} else {
		window.document.getElementById("rePwdMessage").innerHTML = "";
	}
}

function checkSex() {
	var result = checkFormatsByName("sex");
	showMessage("sexMessage", result, "", "请选择性别");
	
	return result;
}

function checkTelephone(obj) {
	
}

var validateCodeStatus = false;
function checkvalidateCode(obj, appPath) {
	var validateCodeMsg =window.document.getElementById("validateCodeMsg");
	if(obj.value != "") {
		var xmlhttp = createXMLHttpRequest();
		xmlhttp.onreadystatechange = function() {
			if(xmlhttp.readyState == 4) {
				if(xmlhttp.status == 200) {
					if(xmlhttp.responseText == "true") {
						validateCodeMsg.innerHTML = "<font color='green'>验证码正确</font>";
						validateCodeStatus = true;
					} else {
						validateCodeMsg.innerHTML = "<font color='red'>验证码错误</font>";
					}
				} else {
					alert("服务器繁忙，请稍后注册！（状态码：" + xmlhttp.status + "）");
				}				
			}
		};
		
		// 建立与服务器的异步连接
		var url = appPath + "/checkvalidatecode?userInputValidateCode=" + obj.value;
		xmlhttp.open("get", url);
		xmlhttp.send(null);		
	}
}

function checkHobbies() {
	var result = checkFormatsByName("hobbies");	
	showMessage("hobbiesMessage", result, "", "��ѡ����Ȥ");
	return result;
}

function checkBirthday() {
	var result = false;
	var birthday = window.document.getElementsByName("birthday")[0];
	if(birthday.value != "") {
		var regExpStr = "^[12][0-9]{3}-[01][0-9]-[0123][0-9]$";
		var result = checkFormat(birthday, regExpStr);
		showMessage("birthdayMessage", result, "���ڸ�ʽ��ȷ", "���ڸ�ʽ����");
	} else {
		window.document.getElementById("birthdayMessage").innerHTML = "���ڸ�ʽ��yyyy-MM-dd";
	}
	
	return result;
}

function isEmpty(name, message, errorMsg) {
	var input = window.document.getElementsByName(name)[0];
	if(input.value == "") {
		document.getElementById(message).innerHTML = "<font color='red'>" + errorMsg + "</font>";
		return false;
	}
	
	return true;
}

function checkForm() {
	var result = true;
	if(validateCodeStatus) {
		result = isEmpty("email", "emailMessage", "邮箱不能为空") && result; 
		result = isEmpty("username", "usernameMessage", "用户名不能为空") && result; 
		result = isEmpty("password", "pwdMessage", "密码不能为空") && result; 
		result = isEmpty("repassword", "rePwdMessage", "请确认密码") && result; 
	} else {
		result = false;
	}
	return result;
}