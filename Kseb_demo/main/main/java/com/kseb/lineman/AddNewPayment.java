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

import com.kseb.DBConnection;

@WebServlet("/addNewPayment")
public class AddNewPayment extends HttpServlet{

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
	
	int allocID=0;
	String date=null;
	Float amount=0f;
	String details=null;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			
			date=request.getParameter("date").toString();
			allocID=Integer.parseInt(request.getParameter("allocationID"));
			amount=Float.parseFloat(request.getParameter("amount"));
			details=request.getParameter("amountDetails").toString();
			
			con=dbCon.getConnection();
			PrintWriter out = response.getWriter();
			
			pst = con.prepareStatement("insert into payment_bill(payment_work_alloc_id, payment_bill_date, payment_bill_amount, payment_bill_details) values(?, ?, ?, ?)");
			pst.setInt(1, allocID);
			pst.setString(2, date);
			pst.setFloat(3, amount);
			pst.setString(4, details);
			pst.executeUpdate();

			out.println("<h2 style='color:#fff'>New Payment Added</h2>");
			
		} 	catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
