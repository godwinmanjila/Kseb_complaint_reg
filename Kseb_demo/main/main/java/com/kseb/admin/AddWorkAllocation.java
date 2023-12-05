package com.kseb.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kseb.DBConnection;

@WebServlet("/addWorkAllocation")
public class AddWorkAllocation extends HttpServlet{

	/**
	 * @author Godwin
	 */
	private static final long serialVersionUID = 1L;
	
	Connection con=null;
	PreparedStatement pst=null;
	ResultSet rs=null;
	PreparedStatement pstNew=null;
	ResultSet rsNew=null;
	DBConnection dbCon = new DBConnection();
	RequestDispatcher dispatcher=null;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			
			con=dbCon.getConnection();
			PrintWriter out = response.getWriter();
			
			out.print("<html>");
			out.print("<head><link rel=\"stylesheet\" type=\"text/css\" href=\"css/addComplaint.css\" /></head>");
			out.print("<body>");
			out.print("<div class='add-complaint-box'> ");
			out.print(" <h2>Allocate Work</h2> ");
			out.print(" <form method='post' action='addWork'>");
			out.print(" <div class='user-box'> ");
			out.print(" <input type='date' name='date' required='' id='date'>");
			out.print(" <label class='selectLabel'>Date</label> ");
			out.print(" </div> ");
			out.print(" <div class='user-box'> ");
			out.print(" <select name='complaintID' id='complaintID'>");
			
			pst=con.prepareStatement("select complaint_id from complaint");
			rs=pst.executeQuery();
			while(rs.next()) {
				out.print("<option value="+rs.getInt(1)+">"+rs.getInt(1)+"</option>");
			}
			
			out.print("</select>");
			out.print("<label class='selectLabel'>Complaint ID</label> ");
			out.print(" </div> ");
			out.print(" <div class='user-box'> ");
			out.print(" <select name='lineManID' id='lineManID'>");
			
			pst=con.prepareStatement("select login_staff_id from staff_login_info where user_type=?");
			pst.setString(1, "lineman");
			rs=pst.executeQuery();
			while(rs.next()) {
				out.print("<option value="+rs.getInt(1)+">"+rs.getInt(1)+"</option>");
			}
			rs.close();
			
			out.print("</select>");
			out.print(" <label class='selectLabel'>Line Man ID</label>");
			out.print(" </div> ");
			out.print(" <div class='wrapper'>");
			out.print(" <input type='submit' value='ADD COMPLAINT' id='submit' />");
			out.print(" <span></span> <span></span> <span></span> <span></span> <span></span>");
			out.print(" </div> ");
			out.print(" </form>");
			out.print("</div>");
			out.print("</body>");
			out.print("</html>");
			
		} 	catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
