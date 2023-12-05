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

@WebServlet("/viewMyMaterialRequest")
public class ViewMyMaterialRequest extends HttpServlet{

	
	private static final long serialVersionUID = 1L;

	Connection con=null;
	PreparedStatement pst=null;
	ResultSet rs=null;
	PreparedStatement pstNew=null;
	ResultSet rsNew=null;
	
	PreparedStatement pstNewx=null;
	ResultSet rsNewx=null;
	
	DBConnection dbCon = new DBConnection();
	RequestDispatcher dispatcher=null;

	int allocationID=0;
	String date=null;
	int requestID=0;
	String materialDetails=null;
	String status=null;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
	try {
		response.setContentType("text/html");
		
		con=dbCon.getConnection();
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession();
		int staffID = Integer.parseInt(session.getAttribute("staffID").toString());
		
		out.print("<html>");
		out.print("<head><link rel=\"stylesheet\" type=\"text/css\" href=\"css/table.css\" /></head>");
		out.print("<body>");
		out.print("<h1>My Material Requests</h1>");
		out.print("<div class='tableop'>");
		out.print("<table>");
		out.print("<tr><th>Request ID</th><th>Request Date</th><th>Allocation ID</th><th>Material Details</th><th>Status</th><th>Edit</th></tr>");
		
		pst=con.prepareStatement("select work_alloc_id from work_allocation where staff_id=?");
		pst.setInt(1, staffID);
		rs=pst.executeQuery();
		while(rs.next()) {
			allocationID=rs.getInt(1);
			
			pstNew=con.prepareStatement("select material_request_id, material_request_date, material_requested from material_request where fk_work_alloc_id=?");
			pstNew.setInt(1, allocationID);
			rsNew=pstNew.executeQuery();
			while(rsNew.next()) {
				requestID=rsNew.getInt(1);
				date=rsNew.getString(2);
				materialDetails=rsNew.getString(3);
				
				pstNewx=con.prepareStatement("select material_request_status from material_request_status where fk_material_request_id=?");
				pstNewx.setInt(1, requestID);
				rsNewx=pstNewx.executeQuery();
				while(rsNewx.next()) {
					status=rsNewx.getString(1);
				}
				rsNewx.close();
				out.print("<tr><td>"+requestID+"</td><td>"+date+"</td><td>"+allocationID+"</td><td>"+materialDetails+"</td><td>"+status+"</td><td>edit</td></tr>");
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
