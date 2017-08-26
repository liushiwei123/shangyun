<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>

<title>云记类别</title>
</head>

<body>
	<div class="data_list">
		<div class="data_list_title">
			<span class="glyphicon glyphicon-list"></span>&nbsp;类型列表 <span
				class="noteType_add">
				<button class="btn btn-sm btn-success" type="button" id="addBtn">添加类别</button>
			</span>
		</div>

		<c:choose>
			<c:when test="${empty noteTypes}">
				<h3>暂无类别显示</h3>
			</c:when>
			<c:otherwise>
				<div>
					<table class="table table-hover table-striped ">
						<tbody>
							<tr>
								<th>编号</th>
								<th>类型</th>
								<th>操作</th>
							</tr>
							<c:forEach items="${noteTypes}" var="noteType">
								<tr id="tr${noteType.id}">
									<td>${noteType.id}</td>
									<td>${noteType.typeName }</td>
									<td>
										<button class="btn btn-primary update" type="button">修改</button>&nbsp;
										<button class="btn btn-danger del" type="button">删除</button>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
	<!-- 添加类型模态框 -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">新增</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<input type="hidden" name="id" id="id" /> <label for="typename">类型名称</label>
						<input type="text" name="typeName" class="form-control"
							id="typeName" placeholder="类型名称">
					</div>
					<div>
						<span style="color:red" id="msg"></span>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<span class="glyphicon glyphicon-remove"></span>关闭
					</button>
					<button type="button" id="btn_submit" class="btn btn-primary">
						保存</button>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			$("#addBtn").click(function(){
				$("#typeName").val("");
				$("#id").val("");
				$("#msg").html("");
				$("#myModalLabel").html("新增类别");
				$("#btn_submit").html("保存");
				$("#myModal").modal("show");
			})
			//类型名称：绑定失去焦点事件
			$("#typeName").blur(function(){
				var typeName=$("#typeName").val();
				if($.trim(typeName)==""){
					$("#msg").html("类别名称不能为空！");
					$("#btn_submit").attr("disabled",true);
					return;
				}
				//类型唯一性较验
				checkNoteType(typeName);
			})
			//模态框的提交事件
			$("#btn_submit").click(function(){
				//先获取id
				var id=$("#id").val();
				var typeName=$("#typeName").val();
				$.ajax({
					type:"post",
					url:"noteType",
					data:"act=savaOrUpdateNoteType&id="+id+"&typeName="+typeName,
					dataType:"json",
					success:function(data){
						if(data.resultCode==200){
							if(id==null|| id==""){//此时证明为新增
								var id2=data.result.id;
								var typeName=data.result.typeName;
								addTr(id2,typeName);
							}else{//修改
								$("#tr"+id).children("td").eq(1).html(data.result.typeName);
							}
							//隐藏模态框,并将其中的值清除
							$("#typeName").val("");
							$("#msg").html("");
							$("#myModal").modal("hide");
						}
					}
				})
			})
			//绑修改事件
			attachUpdate();
			//绑定删除事件
			attachDel();
		})
		//类别唯一性较验
		function checkNoteType(typeName){
			$.ajax({
				type:"post",
				url:"noteType",
				data:"act=checkNoteType&typeName="+typeName,
				dataType:"json",
				success:function(data){
					if(data.resultCode==300){
						$("#msg").html(data.msg);
						$("#btn_submit").attr("disabled",true);
					}else{
						$("#msg").html("");
						$("#btn_submit").attr("disabled",false);
					}
				}
			})
		}
		//操作DOM，添加节点
		function addTr(id,typeName){
			/**
				<tr id="tr${noteType.id}">
					<td>${noteType.id}</td>
					<td>${noteType.typeName }</td>
					<td>
						<button class="btn btn-primary update" type="button">修改</button>&nbsp;
						<button class="btn btn-danger del" type="button">删除</button>
					</td>
				</tr>
				
				<table class="table table-hover table-striped ">
			*/
			var table=$(".table.table-hover.table-striped");
			var tr="<tr id='tr"+id+"'>";
			tr=tr+"<td>"+id+"</td>";
			tr=tr+"<td>"+typeName+"</td>";
			tr=tr+"<td><button class='btn btn-primary update' type='button'>修改</button>&nbsp;";
			tr=tr+"<button class='btn btn-danger del' type='button'>删除</button></td></tr>";
			if(table.length==0){//代表table不存在
				/**
				<table class="table table-hover table-striped ">
						<tbody>
							<tr>
								<th>编号</th>
								<th>类型</th>
								<th>操作</th>
							</tr>
						</tbody>
					</table>
				*/
				var tab="<table class='table table-hover table-striped '>";
				tab=tab+"<tbody><tr><th>编号</th><th>类型</th><th>操作</th></tr>";
				tab=tab+tr+"</tbody></table>";
				var div=$("h3").parent();//最外层的div
				div.append(tab);
				$("h3").remove();
			}else{
				table.append(tr);
			}
			//绑修改事件
			attachUpdate();
			//绑定删除事件
			attachDel();
		}
		//绑定修改事件：修改模态框
		function attachUpdate(){
			$(".btn.btn-primary.update").click(function(){
				var tr=$(this).parent().parent();
				var id=tr.children("td").eq(0).html();
				var typeName=tr.children("td").eq(1).html();
				$("#typeName").val(typeName);
				$("#id").val(id);
				$("#msg").html("");
				$("#myModalLabel").html("修改类别");
				$("#btn_submit").html("修改");
				$("#myModal").modal("show");
			})
		}
		//绑定删除事件
		function attachDel(){
			$(".btn.btn-danger.del").click(function(){
				var tr=$(this).parent().parent();
				var id=tr.children("td").eq(0).html();
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
								url:"noteType",
								data:"act=delNoteType&id="+id,
								dataType:"json",
								success:function(data){
									if(data.resultCode==200){
									 	swal('删除成功!','ok','success');
										var tal=tr.parent().parent();//<table><tbody></tbody></table>
										var	div=tal.parent();//最外层div
										var trs = tr.parent().children("tr");//拿到<tbody>中所有<tr>
										if(trs.length==2){
											div.append("<h3>暂无类别</h3>");
											tal.remove();
										}else{
											tr.remove();
										}
									}else{
										swal(data.msg,'error','error');
									}
								}
							})	
						}
					})
			})
		}
	</script>
</body>
</html>
