<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>云记查看</title>
</head>
<body>
	<div class="data_list">
		<div class="data_list_title">
			<span class="glyphicon glyphicon-eye-open"></span>&nbsp;查看云记
		</div>
		<div>
			<div class="note_title">
				<h2>${note.title }</h2>
			</div>
			<div class="note_info">
				发布时间：『
				<fmt:formatDate value="${note.pubDate }"
					pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
				』&nbsp;&nbsp; 云记类别：${note.typeName }
			</div>
			<div class="note_content">
				<p>${note.content}</p>
			</div>
			<div class="note_btn">
				<button class="btn btn-primary" onclick="toUpdateNote()"
					type="button">修改</button>
				<button class="btn btn-danger" onclick="delNote()" type="button">删除</button>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		//修改按钮
		function toUpdateNote(){
			window.location.href="note?act=toAddNotePage&id="+${note.id};
		}
		//删除
		function delNote(){
			swal({
				  title: '确定删除吗?',
				  text: '删除操作不能恢复!',
				  type: 'warning',
				  showCancelButton: true,
				  confirmButtonColor:'#3085d6',
				  confirmButtonText: '确定',
				  cancelButtonText: '取消'
				}).then(function(isConfirm) {
					if(isConfirm){
						$.ajax({
							type:"post",
							url:"note",
							data:"act=delNote&id="+${note.id},
							dataType:"json",
							success:function(data){
								if(data.resultCode==200){
								 	swal('删除成功!','ok','success');
									window.location.href="main?act=queryNoteByParams";
								}else{
									swal('error',data.msg,'error');
								}
							}
						})	
					}
				})
		}
	</script>
	

</body>
</html>
