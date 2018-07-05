<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<HTML>
<HEAD>
<meta http-equiv="Content-Language" content="zh-cn"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<%-- <link href="${pageContext.request.contextPath }/admin/css/Style.css"
	rel="stylesheet" type="text/css"> </link> --%>
<!-- <link rel="stylesheet" href="../css/Style.css" type="text/css"></link> -->
<style type="text/css">
/*分页*/
.pagination {
	padding: 5px;
	width: 600px;
	margin: 5px;
	text-align: center;
}

.pagination ul {
	margin: 5;
	padding: 5;
	font-size: 12px;
}

.pagination li {
	list-style-type: none;
	display: inline;
	padding: 1px;
	margin: 1px;
}

.pagination a,.pagination a:visited {
	padding: 5px;
	border: 1px solid #9aafe5;
	text-decoration: none;
	color: #2e6ab1;
}

.pagination a:hover,.pagination a:active {
	border: 1px solid #2b66a5;
	color: #000;
	background-color: lightyellow;
}

.pagination li.currentpage {
	font-weight: bold;
	padding: 5px;
	border: 1px solid navy;
	background-color: #2e6ab1;
	color: #FFF;
}

.pagination li.disablepage {
	padding: 5px;
	border: 1px solid #929292;
	color: #929292;
}

.pagination li.nextpage, .pagination li.previousPage {
	font-weight: bold;
	padding: 5px;
}
</style>
<script language="javascript"
	src="${pageContext.request.contextPath}/admin/js/public.js"></script>
<script type="text/javascript">
	function addProduct() {
		window.location.href = "${pageContext.request.contextPath}/admin/products/add.jsp";
	}
	
	function checkAll() {
		var checkStatus = document.getElementById("checkAllButton").checked;
		var checks = document.getElementsByName("isbns");
		for ( var i = 0; i < checks.length; i++) {
			checks[i].checked = checkStatus;
		}
	}
	
	/* function deleteBooks() {
		var checks = document.getElementsByName("check");
		var paramValues = "";
		for ( var i = 0; i < checks.length; i++) {
			if(checks[i].checked)
				paramValues += "isbn=" + checks[i].value + "&";
		}
		paramValues = paramValues.substring(0, paramValues.length - 1);	
		location.href = "${pageContext.request.contextPath }/servlet/deleteservlet?" + paramValues;
	} */
	/* function deleteBooks() {
		var deleteFrom = document.getElementById("deleteFrom");	
		deleteFrom.submit();
	} */
	
	/* function deleteBooks() {
		var form1 = document.getElementById("Form1");
		var inputs = form1.all.tags("input");
		var params = "bookIsbn=" + inputs[0].value;
		params += "&name=" + inputs[1].value;	
		params += "&minPrice=" + inputs[2].value;	
		params += "&maxPrice=" + inputs[3].value;
		params += "&category=" + form1.all.tags("select")[0].value;		
		var deleteFrom = document.getElementById("deleteFrom");	
		deleteFrom.action += "?" + params;
		deleteFrom.submit();
	} */
	
	function deleteBooks() {
		var deleteFrom = document.getElementById("deleteFrom");
		deleteFrom.submit();
	}
	
	function deleteBook(action, isbn, currentPage) {
		var form1 = document.getElementById("Form1");
		/* form1.innerHTML +=	"<input type='hidden' name='isbn' value='" + isbn + "'/>"; */
		var isbnTag = document.getElementsByName("isbn")[0];	
		isbnTag.value = isbn;
		var currentPageTag = document.getElementsByName("currentPage")[0];
		currentPageTag.value = currentPage;
		form1.action = action;
		form1.submit();
	}
	
	function changePage(i) {
		var form1 = document.getElementById("Form1");
		/* var currentPage = form1.getElementsByName("currentPage")[0]; */
		var currentPage = document.getElementsByName("currentPage")[0];
		currentPage.value = i;
		form1.submit();
	}
	
	function edit(action, isbn, currentPage) {
		var form1 = document.getElementById("Form1");
		var isbnTag = document.getElementsByName("isbn")[0];	
		isbnTag.value = isbn;
		var currentPageTag = document.getElementsByName("currentPage")[0];
		currentPageTag.value = currentPage;
		form1.action = action;
		form1.submit();
	}
	
</script>

<script type="text/javascript">
	//设置类别的默认值
	function setProductCategory(t) {
		var category = document.getElementById("category");

		var ops = category.options;
		for ( var i = 0; i < ops.length; i++) {

			if (ops[i].value == t) {
				ops[i].selected = true;
				return;
			}
		}

	};
</script>

</HEAD>

<body onload="setProductCategory('${queryCondition.category }')">
	<br>
	<table cellSpacing="1" cellPadding="0" width="100%" align="center"
		bgColor="#f5fafe" border="0">
		<TBODY>
			<tr>
				<td class="ta_01" align="center" bgColor="#afd1f3"><strong>查
						询 条 件</strong>
				</td>
			</tr>
			<tr>
				<td>
					<form id="Form1" name="Form1"
						action="${pageContext.request.contextPath}/background/queryBooks"
						method="get">
						<!-- <input type="hidden" name="action" value="queryBooks"/> -->
						<input type="hidden" name="currentPage" value=""/>
						<input type="hidden" name="currentPageSize" value="${page.currentPageSize }"/>
						<input type="hidden" name="isbn" value=""/>
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<tr>
								<td height="22" align="center" bgColor="#f5fafe" class="ta_01">
									商品编号：</td>
								<td class="ta_01" bgColor="#ffffff"><input type="text"
									name="bookIsbn" size="15" value="${queryCondition.bookIsbn }" id="bookIsbn" class="bg" />
								</td>
								<td height="22" align="center" bgColor="#f5fafe" class="ta_01">
									类别：</td>
								<td class="ta_01" bgColor="#ffffff"><select name="category"
									id="category">
										<option value="" selected="selected">--选择商品类加--</option>
										<option value="文学">文学</option>
										<option value="生活">生活</option>
										<option value="计算机">计算机</option>
										<option value="外语">外语</option>
										<option value="经营">经营</option>
										<option value="励志">励志</option>
										<option value="社科">社科</option>
										<option value="学术">学术</option>
										<option value="少儿">少儿</option>
										<option value="艺术">艺术</option>
										<option value="原版">原版</option>
										<option value="科技">科技</option>
										<option value="考试">考试</option>
										<option value="生活百科">生活百科</option>
								</select></td>
							</tr>
	
							<tr>
								<td height="22" align="center" bgColor="#f5fafe" class="ta_01">
									商品名称：</td>
								<td class="ta_01" bgColor="#ffffff"><input type="text"
									name="name" size="15" value="${queryCondition.name }" id="name" class="bg" />
								</td>
								<td height="22" align="center" bgColor="#f5fafe" class="ta_01">
									价格区间(元)：</td>
								<td class="ta_01" bgColor="#ffffff"><input type="text"
									name="minPrice" size="10" value="${queryCondition.minPrice }" id="minPrice"/>&nbsp;-&nbsp;<input type="text"
									name="maxPrice" size="10" value="${queryCondition.maxPrice }" id="maxPrice"/></td>
							</tr>
	
							<tr>
								<td width="100" height="22" align="center" bgColor="#f5fafe"
									class="ta_01"></td>
								<td class="ta_01" bgColor="#ffffff"><font face="宋体"
									color="red"> &nbsp;</font>
								</td>
								<td align="right" bgColor="#ffffff" class="ta_01"><br>
									<br></td>
								<td align="right" bgColor="#ffffff" class="ta_01">
									<button type="submit" id="search" name="search"
										value="&#26597;&#35810;" class="button_view">
										&#26597;&#35810;</button> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input
									type="reset" name="reset" value="&#37325;&#32622;"
									class="button_view" />
								</td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
			<tr>
				<td class="ta_01" align="center" bgColor="#afd1f3"><strong>商品列表</strong>
				</TD>
			</tr>
			<tr>
				<td class="ta_01" align="right">
					<button type="button" id="add" name="add" value="&#28155;&#21152;"
						class="button_add" onclick="addProduct()">&#28155;&#21152;
					</button>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button type="button" id="delete" name="delete" value="删除"
						class="button_delete" onclick="deleteBooks()">删除
					</button>
				</td>
			</tr>
			<tr>
				<td class="ta_01" align="center" bgColor="#f5fafe">
					<form id="deleteFrom" action="${pageContext.request.contextPath }/background/deleteBook" method="post">
						<input type="hidden" name="currentPage" value="${page.currentPage }"/>
						<input type="hidden" name="currentPageSize" value="${page.currentPageSize }"/>
						
						<input type="hidden" name="bookIsbn" value="${queryCondition.bookIsbn }"/>
						<input type="hidden" name="category" value="${queryCondition.category }"/>
						<input type="hidden" name="name" value="${queryCondition.name }"/>
						<input type="hidden" name="minPrice" value="${queryCondition.minPrice }"/>
						<input type="hidden" name="maxPrice" value="${queryCondition.maxPrice }"/>
						
						<table cellspacing="0" cellpadding="1" rules="all"
							bordercolor="gray" border="1" id="DataGrid1"
							style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
							<tr
								style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">
								<td align="center" width="2%"><input type="checkbox" id="checkAllButton" onclick="checkAll()"/></td>
								<td align="center" width="24%">商品编号</td>
								<td align="center" width="18%">商品名称</td>
								<td align="center" width="9%">商品价格</td>
								<td align="center" width="9%">商品数量</td>
								<td width="8%" align="center">商品类别</td>
								<td width="8%" align="center">编辑</td>
	
								<td width="8%" align="center">删除</td>
							</tr>
							
							<c:forEach var="book" items="${page.books }">
								<tr onmouseover="this.style.backgroundColor = 'white'"
									onmouseout="this.style.backgroundColor = '#F5FAFE'">
									<td align="center" width="2%">
										<input type="checkbox" name="isbns" value="${book.isbn }"/>
									</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="23">${book.isbn }</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="18%">${book.name }</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="8%">${book.price }</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center"
										width="8%">${book.pnum }</td>
									<td style="CURSOR: hand; HEIGHT: 22px" align="center">${book.category }</td>
									
									<td align="center" style="HEIGHT: 22px" width="7%"><a
										href="javascript:edit('${pageContext.request.contextPath}/background/getBook', '${book.isbn}', ${page.currentPage })">
											<img
											src="${pageContext.request.contextPath}/admin/images/i_edit.gif"
											border="0" style="CURSOR: hand"></a>
									</td>
	
									<td align="center" style="HEIGHT: 22px" width="7%"><a
										href="javascript:deleteBook('${pageContext.request.contextPath }/background/deleteBook', '${book.isbn}', ${page.currentPage })">
											<img
											src="${pageContext.request.contextPath}/admin/images/i_del.gif"
											width="16" height="16" border="0" style="CURSOR: hand"> </a>
									</td>
								</tr>
							</c:forEach>	
							
							<!-- <tr>
								<td>
									<input type="submit" id="submitForm" value="确认"/>
								</td>
							</tr> -->											
						</table>
						
					</form>	
					
					<div class="pagination">
						<ul>
							<li class="previousPage"><a href="javascript:changePage(${page.currentPage == 1 ? page.currentPage : page.currentPage - 1 })">&lt;&lt;上一页</a></li>														
							<li>第${page.currentPage }页/共${page.pageNumber }页</li>
							<li class="nextPage"><a href="javascript:changePage(${page.currentPage == page.pageNumber ? page.currentPage : page.currentPage + 1 })">下一页&gt;&gt;</a></li>
						</ul>
					</div>
					
				</td>
			</tr>
		</TBODY>
	</table>
</body>
</HTML>

