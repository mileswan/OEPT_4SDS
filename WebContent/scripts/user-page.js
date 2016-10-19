/**
 * 用户信息更新
 */
function form_userUpdate(){
	 var str_data=$("#update_form input").map(function(){
		  return ($(this).attr("name")+'='+$(this).val());
	 }).get().join("&");
	 var position_data=$('#select_position option:selected').val();
	 var from_data = {
			 position:$('#select_position option:selected').val(),
			 userId:$("#userId").text(),
			 username:$("#name").val(),
			 emailaddress:$("#emailaddress").val(),
			 lastname:$("#lastname").val(),
			 firstname:$("#firstname").val()
	 };
	$.ajax({
		   type: "POST",
		   url: "update.do",
		   data: from_data,
		   success: function(msg){}
		});
	
	window.location.href="/OEPT_4SDS/user/list.do"; 
}