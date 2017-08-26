<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  
    
    <title>用户中心</title>
    
	

  </head>
  
  <body>
    <div class="data_list">
    <div class="data_list_title">
        <span class="glyphicon glyphicon-edit"></span>&nbsp;个人中心
    </div>
    <div class="container-fluid">
        <div class="row" style="padding-top: 20px;">
            <div class="col-md-8">
                <form class="form-horizontal" method="post" action="user?act=updateUser"
                    enctype="multipart/form-data" >
                    <div class="form-group">
                        <input type="hidden" name="act" value="save"> <label
                            for="nickName" class="col-sm-2 control-label">昵称:</label>
                        <div class="col-sm-3">
                            <input class="form-control" name="nickName" id="nickName"
                                placeholder="昵称" value="${userInfo.result.nickName}">
                        </div>
                        <label for="img" class="col-sm-2 control-label">头像:</label>
                        <div class="col-sm-5">
                            <input type="file" id="img" name="img">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="mood" class="col-sm-2 control-label">心情:</label>
                        <div class="col-sm-10">
                            <textarea class="form-control" name="mood" id="mood" rows="3">${userInfo.result.mood}
                            </textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" id="btn" class="btn btn-success">修改</button>
                            &nbsp;&nbsp;<span style="color:red" id="msg"></span>
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-md-4">
                <img  class="img-responsive center-block"
                    src="user?act=upImg&img=${userInfo.result.img}">
            </div>
        </div>
    </div>
</div>
   <script type="text/javascript">
   		$(function(){
   			//var nickName=$("#nickName").val();
   			//让昵称框获取焦点
   			$("#nickName").focus(function(){
   				$("#btn").attr("disabled",false);
   				$("#msg").html("");
   			})
   			//当昵称失去焦点
   			$("#nickName").blur(function(){
   				//获取原始名称
   				var nickName='${userInfo.result.nickName}';
   				//获取修改后的名称
   				var nickName2=$("#nickName").val();
   				if(nickName2==""){
   					$("#msg").html("昵称不能为空！");
   					$("#btn").attr("disabled",true);
   					return;
   				}
   				if(nickName!=nickName2){
   					$.ajax({
   						type:"post",
   						url:"user",
   						data:"act=queryNickName&nickName="+nickName2,
   						dataType:"json",
   						success:function(data){
   							if(data.resultCode==300){
   								$("#btn").attr("disabled",true);
   								$("#msg").html(data.msg);
   							}else{
   								$("#btn").attr("disabled",false);
   								$("#msg").html("");
   							}
   						}
   					})
   				}
   				
   			})
   		})
   </script>
  </body>
</html>
