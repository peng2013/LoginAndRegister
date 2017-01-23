<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<link type="text/css" rel="stylesheet" href="../css/style.css" />
<title>用户注册</title>
<script type="text/javascript">
	function refresh() {
	//获得验证码对象
		var img = document.getElementById("img_validation_code")
		img.src = "servlet/ValidationCode?" + Math.random();
		//附加随机请求参数，以保证每次的src属性值不同
	}
	function checkEmail(email) {
		// 验证mail
		var email = email.value;
		var pattern = /^([a-zA-Z0-9._-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
		flag = pattern.test(email);
		if (!flag) {
			alert("email格式不正确!");
			email.focus();
			return false;
		}
		return true;
	}
	function checkRegister() {
		var username = document.getElementById("username");

		// 用户名必须输入
		if (username.value == "") {
			alert("必须输入用户名!");
			username.focus();
			return;
		}
		var password = document.getElementById("password");
		// 密码必须输入
		if (password.value == "") {
			alert("必须输入密码!");
			password.focus();
			return;
		}

		var repassword = document.getElementById("repassword");

		// 两次输入的密码必须一致
		if (password.value != repassword.value) {
			alert("输入的密码不一致!");
			repassword.focus();
			return;
		}

		var email = document.getElementById("email");
		//  验证E-mail
		if (email.value != "") {
			if (!checkEmail(email))
				return;
		}

		var validation_code = document.getElementById("validation_code");

		if (validation_code.value == "") {
			alert("验证码必须输入!");
			validation_code.focus();
			return;
		}

		// 必须输入验证码
		register_form.submit();
	}
</script>
</head>
<body>

	<div
		style="margin-top:20px; margin-left:20px; font-size:20px; height:50px">
		请输入用户注册信息</div>

	<form name="register_form" action="servlet/Register" method="post">
		<ul>
			<li>
				<div class="name_list font_space float_left">
					<span class="require">*</span> 用户名：
				</div>
				<div class="input_list font_space float_left">
					<input type="text" id="username" name="username" size="30"
						maxLength="30" />
				</div></li>
			<li>
				<div class="name_list font_space float_left">
					<span class="require">*</span> 密 码：
				</div>
				<div class="input_list font_space float_left">
					<input type="password" id="password" name="password" size="30"
						maxLength="30" />
			</li>
			<li>
				<div class="name_list font_space float_left">
					<span class="require">*</span> 请再次输入密码：
				</div>
				<div class="input_list font_space float_left">
					<input type="password" id="repassword" name="repassword" size="30"
						maxlength="30" />
			</li>
			<li>
				<div class="name_list font_space float_left">邮箱地址：</div>
				<div class="input_list font_space float_left">
					<input type="text" id="email" name="email" size="30" maxlength="30" />
			</li>
			<li>
				<div class="name_list font_space float_left">
					<span class="require">*</span>验证码：
				</div>
				<div class="input_list font_space float_left">
					<input type="text" id="validation_code" name="validation_code"
						style="width:60px;margin-top: 2px" size="30" maxlength="30" /> <img
						id="img_validation_code" src="servlet/ValidationCode" /> <input
						type="button" value="刷新" onclick="refresh()" />
			</li>

			<li>
				<div class="name_list font_space float_left"></div>
				<div class="input_list font_space float_left">
					<input type="button" value="注册" onclick="checkRegister()" />&nbsp;&nbsp;&nbsp;
					<input type="submit" value="登录" name="login" />
			</li>

		</ul>

	</form>
</body>
</html>
