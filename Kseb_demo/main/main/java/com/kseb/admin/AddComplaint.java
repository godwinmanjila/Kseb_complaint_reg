package com.kseb.admin;

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

import com.kseb.DBConnection;

@WebServlet("/addComplaint")
public class AddComplaint extends HttpServlet {

	/**
	 * @author Godwin
	 */
	private static final long serialVersionUID = 1L;
	
	Connection con=null;
	PreparedStatement pst=null;
	ResultSet rs=null;
	DBConnection dbCon = new DBConnection();
	RequestDispatcher dispatcher=null;
	
	String date="";
	String name="";
	String phoneNo="";
	String location="";
	int postNo=0;
	String complaint="";
	int consumerID=0;
	int ksebID=0;
	int complaintID=0;
	String status="open";
	String user="";
	String statusDetail="Registered";

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setContentType("text/html");
			
			date=request.getParameter("date").toString();
			name=request.getParameter("custName");
			phoneNo=request.getParameter("phone").toString();
			location=request.getParameter("location");
			postNo=Integer.parseInt(request.getParameter("postNo"));
			complaint=request.getParameter("complaint");
			
			HttpSession session = request.getSession();
			user = session.getAttribute("username").toString();
			
			con = dbCon.getConnection();
			PrintWriter out = response.getWriter();
			
			pst = con.prepareStatement("insert into consumer_personal(consumer_name, consumer_address, consumer_phone) values(?, ?, ?)");
			pst.setString(1, name);
			pst.setString(2, location);
			pst.setString(3, phoneNo);
			pst.executeUpdate();
			
			pst=con.prepareStatement("select consumer_id from consumer_personal where consumer_name=? and consumer_address=? and consumer_phone=?");
			pst.setString(1, name);
			pst.setString(2, location);
			pst.setString(3, phoneNo);
			rs=pst.executeQuery();
			
			while(rs.next()) {
				consumerID=rs.getInt(1);
			}
			
			pst = con.prepareStatement("insert into consumer_kseb(consumer_id, consumer_kseb_meter_no, consumer_kseb_post_no) values(?, ?, ?)");
			pst.setInt(1, consumerID);
			pst.setInt(2, (postNo+1000));
			pst.setInt(3, postNo);
			pst.executeUpdate();
			
			pst=con.prepareStatement("select consumer_kseb_id from consumer_kseb where consumer_id=? and consumer_kseb_meter_no=? and consumer_kseb_post_no=?");
			pst.setInt(1, consumerID);
			pst.setInt(2, (postNo+1000));
			pst.setInt(3, postNo);
			rs=pst.executeQuery();
			
			while(rs.next()) {
				ksebID=rs.getInt(1);
			}
			
			pst = con.prepareStatement("insert into complaint(consumer_kseb_id, complaint_details, complaint_logged_date) values(?, ?, ?)");
			pst.setInt(1, ksebID);
			pst.setString(2, complaint);
			pst.setString(3, date);
			pst.executeUpdate();
			
			pst=con.prepareStatement("select complaint_id from complaint where consumer_kseb_id=? and complaint_details=? and complaint_logged_date=?");
			pst.setInt(1, ksebID);
			pst.setString(2, complaint);
			pst.setString(3, date);
			rs=pst.executeQuery();
			
			while(rs.next()) {
				complaintID=rs.getInt(1);
			}
			
			pst = con.prepareStatement("insert into complaint_status(complaint_id_status, complaint_status_updated_date, complaint_status, complaint_status_updated_by, complaint_status_details) values(?, ?, ?, ?, ?)");
			pst.setInt(1, complaintID);
			pst.setString(2, date);
			pst.setString(3, status);
			pst.setString(4, user);
			pst.setString(5, statusDetail);
			pst.executeUpdate();

			out.println("<h2 style='color:#fff'>Complaint Added</h2>");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
