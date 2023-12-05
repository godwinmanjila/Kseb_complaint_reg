package com.kseb.lineman;

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
import javax.servlet.http.HttpSession;

import com.kseb.DBConnection;

@WebServlet("/viewMyAllocations")
public class ViewMyAllocations extends HttpServlet{

	
	private static final long serialVersionUID = 1L;

	Connection con=null;
	PreparedStatement pst=null;
	ResultSet rs=null;
	PreparedStatement pstNew=null;
	ResultSet rsNew=null;
	DBConnection dbCon = new DBConnection();
	RequestDispatcher dispatcher=null;

	int allocationID=0;
	String date=null;
	int complaintID=0;
	int lineManID=0;
	int ksebID=0;
	String complaint=null;
	int post=0;
	int customerID=0;
	String name=null;
	String phone=null;
	String status=null;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
	try {
		response.setContentType("text/html");
		
		con=dbCon.getConnection();
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession();
		int staffID = Integer.parseInt(session.getAttribute("staffID").toString());

		pst=con.prepareStatement("select * from work_allocation where staff_id=?");
		pst.setInt(1, staffID);
		rs=pst.executeQuery();
		
		out.print("<html>");
		out.print("<head><link rel=\"stylesheet\" type=\"text/css\" href=\"css/table.css\" /></head>");
		out.print("<body>");
		out.print("<h1>My Work Allocations</h1>");
		out.print("<div class='tableop'>");
		out.print("<table>");
		out.print("<tr><th>Allocation ID</th><th>Allocation Date</th><th>Complaint ID</th><th>Consumer Name</th><th>Phone No</th><th>Post No</th><th>Complaint</th><th>Status</th><th>Edit Status</th></tr>");
		
		while(rs.next()) {
			allocationID=rs.getInt(1);
			complaintID=rs.getInt(2);
			lineManID=rs.getInt(3);
			date=rs.getDate(4).toString();
			
			pstNew=con.prepareStatement("select consumer_kseb_id, complaint_details from complaint where complaint_id=?");
			pstNew.setInt(1, complaintID);
			rsNew=pstNew.executeQuery();
			while(rsNew.next()) {
				ksebID=rsNew.getInt(1);
				complaint=rsNew.getString(2);
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
			
			pstNew=con.prepareStatement("select complaint_status_details from complaint_status where complaint_id_status=?");
			pstNew.setInt(1, complaintID);
			rsNew=pstNew.executeQuery();
			while(rsNew.next()) {
				status=rsNew.getString(1);
			}

			out.print("<tr><td>"+allocationID+"</td><td>"+date+"</td><td>"+complaintID+"</td><td>"+name+"</td><td>"+phone+"</td><td>"+post+"</td><td>"+complaint+"</td><td>"+status+"</td></tr>");
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
