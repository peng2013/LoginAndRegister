<%@ page language="java" pageEncoding="UTF-8"%>
<html>
	<head>
		 <link  type="text/css" rel="stylesheet" href="../css/style.css"/>
		 <title>用户登录</title>
	<script type="text/javascript">		 
		function refresh()
        {
            var img = document.getElementById("img_validation_code")
            img.src = "servlet/ValidationCode?" + Math.random();            
        }
        function checkLogin()
		{
		    var username = document.getElementById("username");

    		// 用户名必须输入
    		if(username.value == "")
    		{
        		alert("必须输入用户名!");
        		username.focus();
        		return;
    		}
    		var password = document.getElementById("password");
   			 // 密码必须输入
   			if(password.value == "")
    		{
        		alert("必须输入密码!");
        		password.focus();
        		return;
    		}
    		
    	
    		var validation_code = document.getElementById("validation_code");
    		
    		if(validation_code.value == "")
    		{
    		    alert("验证码必须输入!");
        		validation_code.focus();
        		return;
    		}
    		
    		// 必须输入验证码
   			login_form.submit();
		}  
        
		 
	</script>
	</head>
	<body>
	<div style="margin-top:20px;  margin-left:20px; font-size:20px; height:50px">
	    请输入用户名和密码
	</div>	
		<form name = "login_form" action="servlet/Login" method="post">
		
       <ul>
            <li>
                <div class="name_list font_space float_left">用户名：</div>
                <div class="input_list1 font_space float_left">
        			<input type="text" id = "username" value="${requestScope.username}" class="input_list" name="username" size="30" maxLength="30"/>
        			&nbsp;&nbsp;<font color="#FF0000">${requestScope.userError}</font>        			 
				</div>
    		</li>
            <li>
                <div class="name_list font_space float_left"> 密 码：</div>
                <div class="input_list1 font_space float_left">
        <input type="password"  id="password" class="input_list" name="password" size="30" maxLength="30" />&nbsp;&nbsp;	<font color="#FF0000">${requestScope.passwordError}</font>	
    </li>
     <li>
      <div class="name_list font_space float_left"> 验证码：</div>
      <div class="input_list1 font_space float_left">
       <input type="text" id="validation_code"  name="validation_code" style="width:60px;margin-top: 2px" size="30" maxlength="30"/>
        <img id="img_validation_code" src="servlet/ValidationCode"/>
        <input type="button"  value="刷新" onclick="refresh()" />&nbsp;&nbsp;<font color="#FF0000">${requestScope.codeError}</font>

    </li>
  <li>
      <div class="name_list font_space float_left"> </div>
      <div class="input_list1 font_space float_left">
      <input type="button" value="登录" name="login" onclick="checkLogin()" />&nbsp;&nbsp;&nbsp;
			<input type="submit" value="注册" name="register" />
      
  </li>
    <li>   
    </li>
		</ul>	
		</form>
	</body>
</html>
