<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="statics/css/note.css" rel="stylesheet">
<link href="statics/bootstrap3/css/bootstrap.css" rel="stylesheet">
<link href="statics/sweetAlert/css/sweetalert2.min.css" rel="stylesheet">
<script src="statics/js/jquery-1.11.3.js"></script>
<script src="statics/bootstrap3/js/bootstrap.js"></script>
<script type="text/javascript" src="statics/sweetAlert/js/sweetalert2.min.js"></script>
<!-- 配置文本   注意：必须将配置文本置于编辑器源码文件上方-->
<script type="text/javascript" src="statics/UEditor/ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="statics/UEditor/ueditor.all.js"></script>
<script type="text/javascript" src="statics/js/main.js"></script>
<style type="text/css">
  body {
       padding-top: 60px;
       padding-bottom: 40px;
     }
</style>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" style="font-size:25px" href="">尚云笔记</a>
    </div>
    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li class="active"><a href=""><i class="glyphicon glyphicon-cloud"></i>&nbsp;主&nbsp;&nbsp;页</a></li>
        <li><a href="note?act=toAddNotePage"><i class="glyphicon glyphicon-pencil"></i>&nbsp;发表云记</a></li>
        <li><a href="noteType?act=noteTypeInfo"><i class="glyphicon glyphicon-list"></i>&nbsp;类别管理</a></li>
        <li><a href="user?act=userInfo"><i class="glyphicon glyphicon-user"></i>&nbsp;个人中心</a>
       
      </li></ul>
      <form class="navbar-form navbar-right" role="search" action="main">
      	<input type="hidden" name="act" value="queryNoteByParams">
        <div class="form-group">
          <input type="hidden" name="act" value="searchKey">
          <input type="text" name="content" class="form-control" value="${content}" placeholder="搜索云记">
        </div>
        <button type="submit" class="btn btn-default">查询</button>
      </form>      
    </div>
  </div>
</nav>
<div class="container">
	<div class="row-fluid">
		<div class="col-md-3">
			<div class="data_list">
				<div class="data_list_title"><span class="glyphicon glyphicon-user"></span>&nbsp;个人中心&nbsp;&nbsp;&nbsp;&nbsp;<a href="user?act=logout">退出</a></div>
				<div class="userimg">
					<%-- <img src="user?act=pic&fn=${userInfo.result.pic}"> --%>
				    <img src="user?act=upImg&img=${userInfo.result.img}">
				</div>	
				<div class="nick">${userInfo.result.nickName }</div>
				<div class="mood">${userInfo.result.mood }</div>
			</div>	
			<div class="data_list">
				<div class="data_list_title">
					<span class="glyphicon glyphicon-calendar">
					</span>&nbsp;云记存档
				</div>
				
				<div>
					<ul class="nav nav-pills nav-stacked" id="data1">
					 
					</ul>						
				</div>
				
			</div>		
			<div class="data_list">
				<div class="data_list_title">
					<span class="glyphicon glyphicon-list-alt">
					</span>&nbsp;云记类别 
				</div>
				
				<div>
					<ul class="nav nav-pills nav-stacked" id="data2">
					</ul>						
				</div>
				
			</div>			
		</div>
	</div>
	<div class="col-md-9">
		<jsp:include page="${change}"></jsp:include>
	</div>
</div>
</body>
</html>