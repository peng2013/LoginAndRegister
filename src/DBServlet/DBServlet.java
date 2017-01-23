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
	//�����������ݿ��Connection����
	protected java.sql.Connection conn=null;
	//ִ�и���SQL���ķ���
	protected java.sql.ResultSet execSQL(String sql,Object...args) throws SQLException{
		//����PreparedStatement����
		java.sql.PreparedStatement pStmt=conn.prepareStatement(sql);
		//ΪpStmt��������SQL����ֵ
		for(int i=0;i<args.length;i++){
			pStmt.setObject(i+1, args[i]);//����SQL����ֵ
		}
		pStmt.execute();//ִ��SQL���
		//���ؽ���������ִ�е�SQL��䲻���ؽ�������򷵻�null
		return pStmt.getResultSet();
	}
	//�˶��û��������֤���Ƿ�Ϸ�
	protected boolean checkValidationCode(HttpServletRequest request,String validationCode){
		//��HttpSession�����л��ϵͳ������ɵ���֤��
		String validationCodeSession=(String)request.getSession().getAttribute("validation_code");
		//�����õ���֤��Ϊnull,˵����֤����ڣ��û�����ˢ�¿ͻ���ҳ��,�����»���µ���֤��
		if(validationCodeSession==null){
			//����result.jsp��Ҫ�Ľ����Ϣ
			request.setAttribute("info","��֤�����");
			//����login.jsp��Ҫ�Ĵ�����Ϣ
			request.setAttribute("codeError", "��֤�����");
			return false;
		}
		//���û��������֤���ϵͳ������ɵ���֤����бȽ�
		if(!validationCode.equalsIgnoreCase(validationCodeSession)){
			//����result.jsp��Ҫ�Ľ����Ϣ
			request.setAttribute("info", "��֤�벻��ȷ");
			//����login.jsp��Ҫ�Ĵ�����Ϣ
			request.setAttribute("codeError", "��֤�벻��ȷ");
			return false;
		}
		return true;
	}
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//���connΪnull,�����ݿ�����
		if(conn==null){
			//��ȡ���ݿ�����
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
