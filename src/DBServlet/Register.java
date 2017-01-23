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
		System.out.println("Register��service����ִ�гɹ�");
		String userName=null;
		//��������������try{}ǰ�棬��������finally�е�forward
		//�ڵ���sendRedirect�����󣬲����ٵ���forward����
		if(request.getParameter("login")!=null){
			//�ض���login.jsp
			response.sendRedirect("/LoginAndRegister/login.jsp");
			return;
		}
		//����DBServlet��service����
		super.service(request, response);
		//��ò���username,password,email��validation_code��ֵ
		userName=request.getParameter("username");
		String password=request.getParameter("password");
		String email=request.getParameter("email");
		String validationCode=request.getParameter("validation_code");
		System.out.println("userName="+userName+" "+"password="+password+" "+"email="+email+" validationCode="+validationCode);
		//�û����������������
		if(userName.equals("")||password.equals("")||validationCode.equals(""))
			return;
		//���б���ת������֧�������û���
		userName=new String(userName.getBytes("ISO-8859-1"),"utf-8");
		//��result.jsp��Ҫ��ת��register.jspҳ
		request.setAttribute("page", "register.jsp");
		//�˶�У����
		if(!checkValidationCode(request, validationCode)){
			return;
		}
		email=(email==null)?"":email;//����ֵ��email��Ϊ�մ�
		//��MD5�㷨�������ַ�������
		try {
			String passwordMD5=tools.Encrypter.md5Encrypt(password);
			System.out.println("passwordMD5="+passwordMD5);
			//��������¼��SQL���
			String sql="insert into t_users(user_name,password_md5,email) values(?,?,?)";
			//ִ��SQL���
			execSQL(sql, userName,passwordMD5,email);
			//����result.jsp��ʹ�õ���Ϣ
			request.setAttribute("info", "�û�ע��ɹ�!");
			System.err.println("ע��ɹ�!");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			//ת����result.jsp
			RequestDispatcher rd=request.getRequestDispatcher("/result.jsp");
			rd.forward(request, response);
		}	
	}

}
