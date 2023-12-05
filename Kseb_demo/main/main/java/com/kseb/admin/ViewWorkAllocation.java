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

@WebServlet("/viewWorkAllocation")
public class ViewWorkAllocation extends HttpServlet{

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

	int allocationID=0;
	String date=null;
	int complaintID=0;
	int lineManID=0;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			
			con=dbCon.getConnection();
			PrintWriter out = response.getWriter();

			pst=con.prepareStatement("select * from work_allocation");
			rs=pst.executeQuery();
			
			out.print("<html>");
			out.print("<head><link rel=\"stylesheet\" type=\"text/css\" href=\"css/table.css\" /></head>");
			out.print("<body>");
			out.print("<h1>Work Allocations</h1>");
			out.print("<div class='tableop'>");
			out.print("<table>");
			out.print("<tr><th>Allocation ID</th><th>Date</th><th>Complaint ID</th><th>LineMan ID</th><th>Edit</th></tr>");
			
			while(rs.next()) {
				allocationID=rs.getInt(1);
				complaintID=rs.getInt(2);
				lineManID=rs.getInt(3);
				date=rs.getDate(4).toString();

				out.print("<tr><td>"+allocationID+"</td><td>"+date+"</td><td>"+complaintID+"</td><td>"+lineManID+"</td><td><a href='editAllocation?allocationID="+allocationID+"'>Edit</a></td></tr>");
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
