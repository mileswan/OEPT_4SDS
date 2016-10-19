<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<% String url = basePath+""; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv=refresh content=5;url=<%=url %>>
<title>用户验证</title>
</head>
<body>
<p>用户验证失败,用户名或者密码错误,请重新输入! ${message}
<p><b style=color:blue><span id=jump>5</span> 秒钟后页面将自动返回登录页面...</b>
</body>
<script> 
function countDown(secs){ 
	jump.innerText=secs; 
	if(--secs>0) 
		setTimeout("countDown("+secs+" )",1000); 
	} 
countDown(5); 
</script>
</html>