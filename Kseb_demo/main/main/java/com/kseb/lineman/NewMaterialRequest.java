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

@WebServlet("/newMaterialRequest")
public class NewMaterialRequest extends HttpServlet{

	
	private static final long serialVersionUID = 1L;

	Connection con=null;
	PreparedStatement pst=null;
	ResultSet rs=null;
	PreparedStatement pstNew=null;
	ResultSet rsNew=null;
	DBConnection dbCon = new DBConnection();
	RequestDispatcher dispatcher=null;
	
	String date=null;
	int allocationID=0;
	String materialDetails=null;
	int requestID=0;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			
			date=request.getParameter("date").toString();
			allocationID=Integer.parseInt(request.getParameter("allocationID"));
			materialDetails=request.getParameter("materialDetails");
			
			HttpSession session = request.getSession();
			String message = session.getAttribute("username").toString();
			
			con=dbCon.getConnection();
			PrintWriter out = response.getWriter();
			
			pst = con.prepareStatement("insert into material_request(fk_work_alloc_id, material_request_date, material_requested) values(?, ?, ?)");
			pst.setInt(1, allocationID);
			pst.setString(2, date);
			pst.setString(3, materialDetails);
			pst.executeUpdate();
			
			pst=con.prepareStatement("select material_request_id from material_request where fk_work_alloc_id=?");
			pst.setInt(1, allocationID);
			rs=pst.executeQuery();
			while(rs.next()) {
				requestID=rs.getInt(1);
			}

			pst = con.prepareStatement("insert into material_request_status(fk_material_request_id, material_request_status_updated_date, material_request_status, material_request_status_updated_by, material_request_status_details) values(?, ?, ?, ?, ?)");
			pst.setInt(1, requestID);
			pst.setString(2, date);
			pst.setString(3, "Pending");
			pst.setString(4, message);
			pst.setString(5, "waiting for approval from material department");
			pst.executeUpdate();
			
			out.println("<h2 style='color:#fff'>Material Request Added</h2>");
			
		} 	catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
