package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {
	public Connection conn;
	private static String dbClassName;//�������ݿ������ı���
	private static String dbUrl;
	private static String dbUser;
	private static String dbPwd;
	public DBConn(){
		dbClassName="com.mysql.jdbc.Driver";//��ȡ���ݿ�����
		dbUrl="jdbc:mysql://localhost:3306/login?useUnicode=true&characterEncoding=utf-8";//��ȡURL
		dbUser="root";
		dbPwd="123456";
	}
	//��ȡ���ݿ�����
	public  Connection getConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Connection conn=null;
		Class.forName(dbClassName).newInstance();
		conn=DriverManager.getConnection(dbUrl,dbUser,dbPwd);
		if(conn==null)
			System.out.println("���ݿ����Ӵ���!");
		return conn;
	}
	//�ر����ݿ�����
	public void closeDB() throws SQLException{
		if(conn!=null&&conn.isClosed()==false){
			conn.close();
		}
	}

}
