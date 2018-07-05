<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>


<title>bookStore列表</title>

<%--导入css --%>
<!-- <link rel="stylesheet" href="css/main.css" type="text/css" /> -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/css/main.css" type="text/css"></link>

</head>

<body class="main">

	<jsp:include page="head.jsp" />
	<jsp:include page="menu_search.jsp" />

	<div id="divpagecontent">
		<table width="100%" border="0" cellspacing="0">
			<tr>

				<td>
					<div style="text-align:right; margin:5px 10px 5px 0px">
						<a href="index.jsp">首页</a>&nbsp;&nbsp;&nbsp;&nbsp;&gt;&nbsp;&nbsp;&nbsp;&nbsp;${page.category }&nbsp;&nbsp;&nbsp;&nbsp;&gt;&nbsp;&nbsp;&nbsp;&nbsp;图书列表
					</div>
					
					<table cellspacing="0" class="listcontent">
						<tr>
							<td>
								<h1>商品目录</h1>
								<hr />
								<h1>${page.category }</h1>&nbsp;&nbsp;&nbsp;&nbsp;共${page.bookNumber }种商品
								<hr />
								<div style="margin-top:20px; margin-bottom:5px">
									<img src="images/productlist.gif" width="100%" height="38" />
								</div>
								
								<!-- 显示图书列表 -->
								<table cellspacing="0" class="booklist" align="center">
									<c:choose>
										<c:when test="${page.currentPageSize > 0 }">
											<c:forEach var="row" begin="0" end="${page.rowNumber - 1 }" step="1">
												<tr>
													<c:forEach var="column" begin="0" end="${page.columnNumber - 1 }" step="1">
														<c:set var="index" value="${row * page.columnNumber + column }"></c:set>													
														<c:choose>
															<c:when test="${index + 1 <= page.currentPageSize }">
																<td align="center">	
																	<div class="divbookpic">
																		<p>
																			<a href="${pageContext.request.contextPath }/book?action=showBookInformation&isbn=${page.books[index].isbn }"><img src="${pageContext.request.contextPath }/upload/bookcover${page.books[index].bookCoverPath }" width="115" height="129"
																				border="0" /> </a>
																		</p>
																	</div>
						
																	<div class="divlisttitle">
																		<a href="${pageContext.request.contextPath }/book?action=showBookInformation&isbn=${page.books[index].isbn }">书名:${page.books[index].name }<br />售价:${page.books[index].price } </a>
																	</div>
																</td>
															</c:when>
															<c:otherwise>
																<td align="center">	
																	
																</td>
															</c:otherwise>
														</c:choose>								
													</c:forEach>										
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr>
												<td colspan="${page.columnNumber }" align="center">暂时没有图书！</td>
											</tr>
										</c:otherwise>
									</c:choose>
									
									
									<tr align="center">
										<td colspan="${page.columnNumber }" align="center">
											<div class="pagination">
												<ul>
			
													<li class="previouspage"><a href="${pageContext.request.contextPath }/book?action=changePage&previouspage=true">&lt;&lt;上一页</a></li>
																							
													<li>第${page.currentPage }页/共${page.pageNumber }页</li>
			
													<li class="nextPage"><a href="${pageContext.request.contextPath }/book?action=changePage&nextPage=true">下一页&gt;&gt;</a></li>
			
												</ul>
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						
					</table>
				</td>
			</tr>
		</table>
	</div>

	<jsp:include page="foot.jsp" />

</body>
</html>
