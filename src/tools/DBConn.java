package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {
	public Connection conn;
	private static String dbClassName;//保存数据库驱动的变量
	private static String dbUrl;
	private static String dbUser;
	private static String dbPwd;
	public DBConn(){
		dbClassName="com.mysql.jdbc.Driver";//获取数据库驱动
		dbUrl="jdbc:mysql://localhost:3306/login?useUnicode=true&characterEncoding=utf-8";//获取URL
		dbUser="root";
		dbPwd="123456";
	}
	//获取数据库连接
	public  Connection getConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Connection conn=null;
		Class.forName(dbClassName).newInstance();
		conn=DriverManager.getConnection(dbUrl,dbUser,dbPwd);
		if(conn==null)
			System.out.println("数据库连接错误!");
		return conn;
	}
	//关闭数据库连接
	public void closeDB() throws SQLException{
		if(conn!=null&&conn.isClosed()==false){
			conn.close();
		}
	}

}
