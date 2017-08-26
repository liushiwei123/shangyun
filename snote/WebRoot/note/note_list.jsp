<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>云记列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body>
     <div class="data_list">
		<div class="data_list_title">
			<span class="glyphicon glyphicon glyphicon-th-list"></span>&nbsp;云记列表
		</div>
		<div class="note_datas">
			<ul>
				  <c:forEach items="${pageInfo.currentDatas }" var="note">
				    <li>
				      ${note.pubDateStr} <a href="note?act=queryNoteById&id=${note.id}">${note.title}</a>
				    </li>
				  </c:forEach>
			</ul>		
			<nav style="text-align: center">
			<ul class="pagination">
			   <c:if test="${pageInfo.hasPrePage}">
			    <li><a href="main?act=queryNoteByParams&pageNum=${pageInfo.prePage}&content=${content}&pubDate=${pubDate}&typeName=${typeName}">&laquo;</a></li>
			   </c:if>
			    <c:forEach var="p" begin="${pageInfo.startNavPage }" end="${pageInfo.endNavPage}">
			     <li><a href="main?act=queryNoteByParams&pageNum=${p}&content=${content}&pubDate=${pubDate}&typeName=${typeName}">${p}</a></li>
			    </c:forEach>
			   <c:if test="${pageInfo.hasNextPage}">
			     <li><a href="main?act=queryNoteByParams&pageNum=${pageInfo.nextPage}&content=${content}&pubDate=${pubDate}&typeName=${typeName}">&raquo;</a></li>
			   </c:if>
			</ul>
			</nav>
		</div>
	</div>
  </body>
</html>
