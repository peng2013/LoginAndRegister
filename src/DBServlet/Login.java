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
		//�������register����������ض���register.jspҳ��
		if(request.getParameter("register")!=null){
			response.sendRedirect("/LoginAndRegister/register.jsp");
			return;
		}
		String page="/login.jsp";
		String userName="";
		//���ø����service����
		super.service(request, response);
		//���username�������
		userName=request.getParameter("username");
		String password=request.getParameter("password");
		String validationCode=request.getParameter("validation_code");
		//�����������������һ��Ϊnull�����˳�service����
		if(userName==null||password==null||validationCode==null)
			return;
		//���б���ת�����Ա�֧�������û���
		userName=new String(userName.getBytes("ISO-8859-1"),"UTF-8");
		//�Ͷ�У����
		if(!checkValidationCode(request, validationCode)){
			return;
		}
		String sql="select user_name,password_md5 from t_users where user_name=? and password=?";
		//��ѯ��¼�û��Ƿ����
		try {
			ResultSet rs=execSQL(sql, new Object[]{userName});
			if(rs.next()==false){
				//����������login.jsp����ʾ�����������Ϣ
				request.setAttribute("passwordError", "�û��������벻��ȷ");
			}else{
				page="/main.jsp";
				//��½�ɹ�������Ҫת����ҳ��
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//���û������request��
			request.setAttribute("username", userName);
			RequestDispatcher rd=request.getRequestDispatcher(page);
			rd.forward(request, response);//ת������Ӧ��ҳ�棨Ĭ����main.jsp)
			
		}
		
		
	}
}
