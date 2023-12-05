package com.kseb.executive;

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

@WebServlet("/viewIssuedMaterialEE")
public class ViewIssuedMaterialEE extends HttpServlet{

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
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
	try {
		response.setContentType("text/html");
		
		con=dbCon.getConnection();
		PrintWriter out = response.getWriter();
		
		out.print("<html>");
		out.print("<head><link rel=\"stylesheet\" type=\"text/css\" href=\"css/table.css\" /></head>");
		out.print("<body>");
		out.print("<h1>Issued Material Requests</h1>");
		out.print("<div class='tableop'>");
		out.print("<table>");
		out.print("<tr><th>Complaint ID</th><th>Complaint Details</th><th>Lineman ID</th><th>Request ID</th><th>Materials Requested</th></tr>");
		
		pst=con.prepareStatement("select fk_material_request_id from material_request_status where material_request_status_details='approved'");
		rs=pst.executeQuery();
		while(rs.next()) {
			requestID=rs.getInt(1);
			
			pstNew=con.prepareStatement("select fk_work_alloc_id, material_requested from material_request where material_request_id=?");
			pstNew.setInt(1, requestID);
			rsNew=pstNew.executeQuery();
			while(rsNew.next()) {
				allocID=rsNew.getInt(1);
				materialDetails=rsNew.getString(2);
				
				pstNewx=con.prepareStatement("select complaint_id, staff_id from work_allocation where work_alloc_id=?");
				pstNewx.setInt(1, allocID);
				rsNewx=pstNewx.executeQuery();
				while(rsNewx.next()) {
					complaintID=rsNewx.getInt(1);
					linemanID=rsNewx.getInt(2);
					
					pstNewxy=con.prepareStatement("select complaint_details from complaint where complaint_id=?");
					pstNewxy.setInt(1, complaintID);
					rsNewxy=pstNewxy.executeQuery();
					while(rsNewxy.next()) {
						complaintDetails=rsNew.getString(2);
					}
					rsNewxy.close();
				}
				rsNewx.close();
				out.print("<tr><td>"+complaintID+"</td><td>"+complaintDetails+"</td><td>"+linemanID+"</td><td>"+requestID+"</td><td>"+materialDetails+"</td></tr>");
			}
			rsNew.close();
			
			
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
