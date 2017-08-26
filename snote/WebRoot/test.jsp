<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>测试富文本编辑器</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="statics/js/jquery-1.11.3.js"></script>
	<!-- 配置文本   注意：必须将配置文本置于编辑器源码文件上方-->
	<script type="text/javascript" src="statics/UEditor/ueditor.config.js"></script>
	<!-- 编辑器源码文件 -->
	<script type="text/javascript" src="statics/UEditor/ueditor.all.js"></script>
  </head>
  
  <body>
    	<textarea id="container" name="content">富文本编辑器</textarea><br/>
    	<script type="text/javascript">
    		$(function(){
    			var ue=UE.getEditor('container');
    		})
    	</script>
  </body>
</html>
