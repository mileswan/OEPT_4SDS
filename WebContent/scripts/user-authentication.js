/**
 * Author: mwan
 * Version: 1.0
 * Date: 2015/05/27
 * Description: User and password validation with JS when submit on login page.
 */
var UserAuthentication = function(){
	// for script login authentication
	var username;
	var password;
	
	return {
		post: function(){
			
			username = $("#username").val();
			password = $("#password").val();
			
			$.post("auth/login.do?json=#",
					  {
						username:username,
						password:password
					  },
					  function(data,status){
						  					    
					    var responseJson = eval ("(" + data + ")");
					    if(responseJson.status === "login.success"){
					    	location.href = "auth/dashboard.do";
					    }
					    else{
					    	$('.login-form .alert-danger span').text("用户名或密码错误。");
					    	$('.alert-danger', $('.login-form')).show();
					    }
				      });
			
		}
		
	};

}();