package com.kseb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class Login extends HttpServlet{
	/**
	 * @author Godwin
	 */
	private static final long serialVersionUID = 1L;
	
	Connection con=null;
	PreparedStatement pst=null;
	ResultSet rs=null;
	DBConnection dbCon = new DBConnection();
	RequestDispatcher dispatcher=null;
	

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			
			String username=request.getParameter("username");
			String password=request.getParameter("password");
			String usertype=request.getParameter("usertype");
			String userType="";
			int staffID=0;

			con=dbCon.getConnection();
			
			
			pst=con.prepareStatement("select user_type, login_staff_id from staff_login_info where username=? and password=? and user_type=?");
			pst.setString(1, username);
			pst.setString(2, password);
			pst.setString(3, usertype);
			rs=pst.executeQuery();
			

			while(rs.next()) {
				userType=rs.getString(1);
				staffID=rs.getInt(2);
			}
			
			HttpSession session = request.getSession();
			session.setAttribute("username", username);
			session.setAttribute("staffID", staffID);
			
			if(userType.equals("admin")) {
				dispatcher=request.getRequestDispatcher("admin");
				dispatcher.forward(request, response);
			}else if(userType.equals("executive")) {
				dispatcher=request.getRequestDispatcher("executive");
				dispatcher.forward(request, response);
			}else if(userType.equals("material")) {
				dispatcher=request.getRequestDispatcher("material");
				dispatcher.forward(request, response);
			}else if(userType.equals("lineman")) {
				dispatcher=request.getRequestDispatcher("lineMan");
				dispatcher.forward(request, response);
			}else {
				PrintWriter out = response.getWriter();
				out.print("<script>alert('Invalid username or password')</script>");
				dispatcher=request.getRequestDispatcher("login.html");
				dispatcher.include(request, response);
			}

			
		} 	catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

}
