<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>云记添加</title>
</head>
<body>
	<div class="data_list">
		<div class="data_list_title">
			<span class="glyphicon glyphicon-cloud-upload"></span>&nbsp;发表云记
		</div>
		<div class="container-fluid">
			<div class="container-fluid">
				<div class="row" style="padding-top: 20px;">
					<div class="col-md-8">
						<form class="form-horizontal" id="note">
							<div class="form-group">
								<label for="typeId" class="col-sm-2 control-label">类别:</label>
								<div class="col-sm-6">
									<div class="col-sm-6">
										<select id="typeId" class="form-control" name="typeId" style="width: 150px">
										</select>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label for="title" class="col-sm-2 control-label">标题:</label>
								<div class="col-sm-10">
									<input class="form-control" name="title" id="title"
										placeholder="云记标题" value="${note.title}">
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-12">
									<textarea id="content" name="content"> ${note.content}</textarea>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-6 col-sm-4">

									<input type="button" id="submit" class="btn btn-primary"
										value="保存">
								</div>
							</div>
							<input type="hidden" name="act" value="saveOrUpdateNote" />
							<input type="hidden" name="id" value="${note.id}">
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		var editor;
		$(function(){
			editor = UE.ui.Editor({
				initialFrameHeight : 300,
				initialFrameWidth : 760
			});
			//渲染编辑器的DOM到指定容器,通过id渲染
			editor.render("content");
			//加载类别类型下拉框
			loadNoteTypeData();
			//新增|修改方法
			$("#submit").click(function(){
				$.ajax({
					type:"post",
					url:"note",
					data:$("#note").serialize(),
					dataType:"json",
					success:function(data){
						if(data.resultCode==200){
							swal('添加成功!','ok','success');
							window.location.href="main?act=queryNoteByParams";
						}else{
							swal('添加失败!',data.msg,'error');
						}
					}
				});
			});
		});
		//加载类别下拉框
		var typeId='${note.typeId}';
		function loadNoteTypeData(){
			$.ajax({
				type:"post",
				url:"noteType",
				data:"act=queryNoteTypeByUid",
				dataType:"json",
				success:function(data){
					if(data!=null&&data.length>0){
						var op="<option value='-1'>请选择类别</option>"
						for(var i=0;i<data.length;i++){
							if(typeId==data[i].id){
								op=op+"<option value='"+data[i].id+"' selected='selected'>"+data[i].typeName+"</option>";
							}else{
								op=op+"<option value='"+data[i].id+"'>"+data[i].typeName+"</option>";
							}
						}
						$("#typeId").append(op);
					}
				}
			})
		}
		
	</script>
</body>
</html>
