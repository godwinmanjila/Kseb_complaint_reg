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

@WebServlet("/addWork")
public class AddWork extends HttpServlet{

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
	
	String date=null;
	int complaintID=0;
	int linemanID=0;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			
			date=request.getParameter("date").toString();
			complaintID=Integer.parseInt(request.getParameter("complaintID"));
			linemanID=Integer.parseInt(request.getParameter("lineManID"));
			
			con=dbCon.getConnection();
			PrintWriter out = response.getWriter();
			
			pst = con.prepareStatement("insert into work_allocation(complaint_id, staff_id, work_allocation_date) values(?, ?, ?)");
			pst.setInt(1, complaintID);
			pst.setInt(2, linemanID);
			pst.setString(3, date);
			pst.executeUpdate();

			out.println("<h2 style='color:#fff'>Complaint Added</h2>");
			
		} 	catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
