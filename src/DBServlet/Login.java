package DBServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login extends DBServlet {
	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//如果存在register请求参数，重定向到register.jsp页面
		if(request.getParameter("register")!=null){
			response.sendRedirect("/LoginAndRegister/register.jsp");
			return;
		}
		String page="/login.jsp";
		String userName="";
		//调用父类的service方法
		super.service(request, response);
		//获得username请求参数
		userName=request.getParameter("username");
		String password=request.getParameter("password");
		String validationCode=request.getParameter("validation_code");
		//如果这三个参数中有一个为null，则退出service方法
		if(userName==null||password==null||validationCode==null)
			return;
		//进行编码转换，以便支持中文用户名
		userName=new String(userName.getBytes("ISO-8859-1"),"UTF-8");
		//和对校验码
		if(!checkValidationCode(request, validationCode)){
			return;
		}
		String sql="select user_name,password_md5 from t_users where user_name=? and password=?";
		//查询登录用户是否存在
		try {
			ResultSet rs=execSQL(sql, new Object[]{userName});
			if(rs.next()==false){
				//设置用于在login.jsp中显示的密码错误信息
				request.setAttribute("passwordError", "用户名或密码不正确");
			}else{
				page="/main.jsp";
				//登陆成功，设置要转发的页面
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//将用户存放在request中
			request.setAttribute("username", userName);
			RequestDispatcher rd=request.getRequestDispatcher(page);
			rd.forward(request, response);//转发到相应的页面（默认是main.jsp)
			
		}
		
		
	}
}
