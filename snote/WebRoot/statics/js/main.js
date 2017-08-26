$(function(){
	//加载云记存档   按照日期分组：按照年|月
	countNoteByPubDate()
	//加载云记类别   按照typeName
	countNoteByTypeName();
})
function countNoteByPubDate(){
/*
 *<li><a href="main?act=queryNoteByParams&pubDate=16年8月">2016年08月 <span class="badge">24</span></a></li> 
 * 
 */
	$.ajax({
		type:"post",
		url:"count",
		data:"act=countNoteByPubDate",
		dataType:"json",
		success:function(data){
			if(data!=null && data.length>0){
				var li="";
				for(var i=0;i<data.length;i++){
					li=li+"<li><a href='main?act=queryNoteByParams&pubDate="+data[i].pubDate+"'>"+data[i].pubDate
					+"<span class='badge'>"+data[i].count+"</span></a></li>";
				}
				$("#data1").append(li);
			}
		}
	})
}
function countNoteByTypeName(){
	$.ajax({
		type:"post",
		url:"count",
		data:"act=countNoteByTypeName",
		dataType:"json",
		success:function(data){
			if(data!=null && data.length>0){
				var li="";
				for(var i=0;i<data.length;i++){
					li=li+"<li><a href='main?act=queryNoteByParams&typeName="+data[i].typeName+"'>"+data[i].typeName
					+"<span class='badge'>"+data[i].count+"</span></a></li>";
				}
				$("#data2").append(li);
			}
		}
	})
}