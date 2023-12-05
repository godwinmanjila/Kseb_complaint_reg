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

@WebServlet("/editComplaintSave")
public class EditComplaintSave extends HttpServlet{

	/**
	 * @author Godwin
	 */
	private static final long serialVersionUID = 1L;

	Connection con=null;
	PreparedStatement pst=null;
	ResultSet rs=null;
	PreparedStatement pstNew=null;
	ResultSet rsNew=null;
	PreparedStatement pstNewx=null;
	ResultSet rsNewx=null;
	PreparedStatement pstNewxy=null;
	ResultSet rsNewxy=null;
	DBConnection dbCon = new DBConnection();
	RequestDispatcher dispatcher=null;

	int requestID=0;
	int allocID=0;
	String materialDetails=null;
	int complaintID=0;
	int linemanID=0;
	String complaintDetails=null;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
	try {
		response.setContentType("text/html");
		
		con=dbCon.getConnection();
		PrintWriter out = response.getWriter();
		
		int complaintID = Integer.parseInt(request.getParameter("complaintID"));
		String complaintDetails = request.getParameter("complaintDetails");
		
		out.print("<html>");
		out.print("<head><link rel=\"stylesheet\" type=\"text/css\" href=\"css/addComplaint.css\" /></head>");
		out.print("<body>");	
		
		pst=con.prepareStatement("update complaint set complaint_details='"+complaintDetails+"' where complaint_id='"+complaintID+"'");
		int result=pst.executeUpdate();
		
		if(result>0) {
			out.print("<h1>Complaint Edited</h1>");
		}
		
		out.print("</body>");
		out.print("</html>");
	    
	} catch (SQLException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}

	}
}
