<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/my.js">
	
</script>


<script type="text/javascript">
	
	function createXMLHttpRequest() {
		var xmlhttp;
		if (window.XMLHttpRequest) { // code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else { // code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}		
		return xmlhttp;
	}
	
	function changeBackgroundColorByMouseOver(object) {
		object.style.backgroundColor = "gray";
	}
	function changeBackgroundColorByMouseUp(object) {
		object.style.backgroundColor = "white";
	}
	function searchName(object) {
		var bookList = document.getElementById("bookList");
		if(object.value != "") {
			// 创建XMLHttpRequest
			var xmlhttp = createXMLHttpRequest();
			// 注册事件处理函数
			xmlhttp.onreadystatechange = function() {
				if(xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					if(xmlhttp.status == 200) {
						var bookListStr = xmlhttp.responseText;			
						var innerHTML = "";
						var books = eval("(" + bookListStr + ")");
						for ( var i = 0; i < books.length; i++) {
							innerHTML += "<div style='text-align: left; width: 100%; background-color: white;' onmouseover='changeBackgroundColorByMouseOver(this)' onmouseout='changeBackgroundColorByMouseUp(this)'>" + 
											"<a href='${pageContext.request.contextPath }/book?action=searchBooks&searchname="+ books[i] +"'>" + "<div>" +books[i] + "</div>" +"</a>" + 
										 "</div>";
						}
						bookList.style.display = "block";
						bookList.innerHTML = innerHTML;
					} else {
						alert("服务器繁忙，请稍后重试！（状态码：" + xmlhttp.status + "）");
					}
					
				} 
			};
			// 创建连接
			var url = "${pageContext.request.contextPath }/book?action=searchBooks&echo=true&searchname=" + encodeURI(object.value) + "&time=" + new Date().toLocaleDateString();
			xmlhttp.open("get", url);
			xmlhttp.send(null);
		} else {
			bookList.style.display = "none";
			bookList.innerHTML = "";	
		}		
	}
</script>


<div id="divmenu">
	<a
		href="${pageContext.request.contextPath}/book?action=showBooks&category=文学">文学</a>
	<a
		href="${pageContext.request.contextPath}/book?action=showBooks&category=生活">生活</a>
	<a
		href="${pageContext.request.contextPath}/book?action=showBooks&category=计算机">计算机</a>
	<a
		href="${pageContext.request.contextPath}/book?action=showBooks&category=外语">外语</a>
	<a
		href="${pageContext.request.contextPath}/book?action=showBooks&category=经营">经管</a>
	<a
		href="${pageContext.request.contextPath}/book?action=showBooks&category=励志">励志</a>
	<a
		href="${pageContext.request.contextPath}/book?action=showBooks&category=社科">社科</a>
	<a
		href="${pageContext.request.contextPath}/book?action=showBooks&category=学术">学术</a>
	<a
		href="${pageContext.request.contextPath}/book?action=showBooks&category=少儿">少儿</a>
	<a
		href="${pageContext.request.contextPath}/book?action=showBooks&category=艺术">艺术</a>
	<a
		href="${pageContext.request.contextPath}/book?action=showBooks&category=原版">原版</a>
	<a
		href="${pageContext.request.contextPath}/book?action=showBooks&category=科技">科技</a>
	<a
		href="${pageContext.request.contextPath}/book?action=showBooks&category=考试">考试</a>
	<a
		href="${pageContext.request.contextPath}/book?action=showBooks&category=生活百科">生活百科</a>
	<a  href="${pageContext.request.contextPath}/book?action=showBooks&category=全部图书"
		style="color:#FFFF00">全部商品目录</a>
</div>
<div id="divsearch">
	<form action="${pageContext.request.contextPath}/book"
		method="get">
		<table width="100%" border="0" cellspacing="0">
			<tr>
				<td><input type="hidden" name="action" value="searchBooks"/></td>
				<td style="text-align:right; padding-right:220px">
				Search <input
					type="text" name="searchname" class="inputtable" onkeyup="searchName(this);"
					id="name" value="${page.searchName }"/> 
					<input type="image" src="${pageContext.request.contextPath }/images/serchbutton.gif"
					border="0" style="margin-bottom:-4px">
				</td>
			</tr>
		</table>

	</form>
</div>
<div id="bookList" 
	style="width: 128px; background-color: white; position: absolute; left: 945px; top: 134px; border: 10px, solid, blue; display: none;">
</div>
