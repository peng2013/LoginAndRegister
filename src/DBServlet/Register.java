package DBServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Register extends DBServlet {
	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Register的service方法执行成功");
		String userName=null;
		//下面的语句必须放在try{}前面，否则会调用finally中的forward
		//在调用sendRedirect方法后，不能再调用forward方法
		if(request.getParameter("login")!=null){
			//重定向到login.jsp
			response.sendRedirect("/LoginAndRegister/login.jsp");
			return;
		}
		//调用DBServlet的service方法
		super.service(request, response);
		//获得参数username,password,email和validation_code的值
		userName=request.getParameter("username");
		String password=request.getParameter("password");
		String email=request.getParameter("email");
		String validationCode=request.getParameter("validation_code");
		System.out.println("userName="+userName+" "+"password="+password+" "+"email="+email+" validationCode="+validationCode);
		//用户名和密码必须输入
		if(userName.equals("")||password.equals("")||validationCode.equals(""))
			return;
		//进行编码转换，以支持中文用户名
		userName=new String(userName.getBytes("ISO-8859-1"),"utf-8");
		//在result.jsp中要跳转到register.jsp页
		request.setAttribute("page", "register.jsp");
		//核对校验码
		if(!checkValidationCode(request, validationCode)){
			return;
		}
		email=(email==null)?"":email;//将空值的email赋为空串
		//用MD5算法对密码字符串加密
		try {
			String passwordMD5=tools.Encrypter.md5Encrypt(password);
			System.out.println("passwordMD5="+passwordMD5);
			//定义插入记录的SQL语句
			String sql="insert into t_users(user_name,password_md5,email) values(?,?,?)";
			//执行SQL语句
			execSQL(sql, userName,passwordMD5,email);
			//定义result.jsp中使用的消息
			request.setAttribute("info", "用户注册成功!");
			System.err.println("注册成功!");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			//转发到result.jsp
			RequestDispatcher rd=request.getRequestDispatcher("/result.jsp");
			rd.forward(request, response);
		}	
	}

}
