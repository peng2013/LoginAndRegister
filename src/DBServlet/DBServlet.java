package DBServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tools.DBConn;

public class DBServlet extends HttpServlet {
	//用于连接数据库的Connection对象
	protected java.sql.Connection conn=null;
	//执行各种SQL语句的方法
	protected java.sql.ResultSet execSQL(String sql,Object...args) throws SQLException{
		//建立PreparedStatement对象
		java.sql.PreparedStatement pStmt=conn.prepareStatement(sql);
		//为pStmt对象设置SQL参数值
		for(int i=0;i<args.length;i++){
			pStmt.setObject(i+1, args[i]);//设置SQL参数值
		}
		pStmt.execute();//执行SQL语句
		//返回结果集，如果执行的SQL语句不返回结果集，则返回null
		return pStmt.getResultSet();
	}
	//核对用户输入的验证码是否合法
	protected boolean checkValidationCode(HttpServletRequest request,String validationCode){
		//从HttpSession对象中获得系统随机生成的验证码
		String validationCodeSession=(String)request.getSession().getAttribute("validation_code");
		//如果获得的验证码为null,说明验证码过期，用户必须刷新客户端页面,以重新获得新的验证码
		if(validationCodeSession==null){
			//设置result.jsp需要的结果信息
			request.setAttribute("info","验证码过期");
			//设置login.jsp需要的错误信息
			request.setAttribute("codeError", "验证码过期");
			return false;
		}
		//将用户输入的验证码和系统随机生成的验证码进行比较
		if(!validationCode.equalsIgnoreCase(validationCodeSession)){
			//设置result.jsp需要的结果信息
			request.setAttribute("info", "验证码不正确");
			//设置login.jsp需要的错误信息
			request.setAttribute("codeError", "验证码不正确");
			return false;
		}
		return true;
	}
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//如果conn为null,打开数据库连接
		if(conn==null){
			//获取数据库连接
			DBConn dbConn=new DBConn();
			try {
				conn=dbConn.getConnection();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
	@Override
	public void destroy() {
		DBConn dbConn=new DBConn();
		try {
			dbConn.closeDB();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
