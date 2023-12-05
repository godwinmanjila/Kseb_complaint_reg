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

@WebServlet("/viewComplaint")
public class ViewComplaint extends HttpServlet{
	
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

	int complaintID=0;
	String date=null;
	String name=null;
	String phone=null;
	int post=0;
	String complaint=null;
	String updatedDate=null;
	String status=null;
	String updatedBy=null;
	String details=null;
	int ksebID=0;
	int customerID=0;
	
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			
			con=dbCon.getConnection();
			PrintWriter out = response.getWriter();

			pst=con.prepareStatement("select * from complaint_status");
			rs=pst.executeQuery();
			
			out.print("<html>");
			out.print("<head><link rel=\"stylesheet\" type=\"text/css\" href=\"css/table.css\" /></head>");
			out.print("<body>");
			out.print("<h1>Complaints</h1>");
			out.print("<div class='tableop'>");
			out.print("<table>");
			out.print("<tr><th>Complaint ID</th><th>Date</th><th>Consumer Name</th><th>Phone No</th><th>Post No</th><th>Complaint</th><th>Updated Date</th><th>Status</th><th>Status Updated By</th><th>Status Details</th><th>Edit</th></tr>");
			
			while(rs.next()) {
				complaintID=rs.getInt(2);
				updatedDate=rs.getDate(3).toString();
				status=rs.getString(4);
				updatedBy=rs.getString(5);
				details=rs.getString(6);
				
				pstNew=con.prepareStatement("select consumer_kseb_id, complaint_details, complaint_logged_date from complaint where complaint_id=?");
				pstNew.setInt(1, complaintID);
				rsNew=pstNew.executeQuery();
				while(rsNew.next()) {
					ksebID=rsNew.getInt(1);
					complaint=rsNew.getString(2);
					date=rsNew.getDate(3).toString();
				}
				
				pstNew=con.prepareStatement("select consumer_id, consumer_kseb_post_no from consumer_kseb where consumer_kseb_id=?");
				pstNew.setInt(1, ksebID);
				rsNew=pstNew.executeQuery();
				while(rsNew.next()) {
					customerID=rsNew.getInt(1);
					post=rsNew.getInt(2);
				}
				
				pstNew=con.prepareStatement("select consumer_name, consumer_phone from consumer_personal where consumer_id=?");
				pstNew.setInt(1, customerID);
				rsNew=pstNew.executeQuery();
				while(rsNew.next()) {
					name=rsNew.getString(1);
					phone=rsNew.getString(2);
				}
				
				rsNew.close();
				out.print("<tr><td>"+complaintID+"</td><td>"+date+"</td><td>"+name+"</td><td>"+phone+"</td><td>"+post+"</td><td>"+complaint+"</td><td>"+updatedDate+"</td><td>"+status+"</td><td>"+updatedBy+"</td><td>"+details+"</td><td><a href='editComplaint?complaintID="+complaintID+"'>Edit</a></td></tr>");
			}
			rs.close();
			
			out.print("</table>");
			out.print("</div>");
			out.print("</body>");
			out.print("</html>");
		    
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

}
