package com.kseb.material;

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

@WebServlet("/viewPaymentsDepo")
public class ViewPaymentsDepo extends HttpServlet {

	
	private static final long serialVersionUID = 1L;

	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	PreparedStatement pstNew = null;
	ResultSet rsNew = null;
	DBConnection dbCon = new DBConnection();
	RequestDispatcher dispatcher = null;

	int allocationID = 0;
	int paymentID = 0;
	String date = null;
	Float amount = 0f;
	String details = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");

			con = dbCon.getConnection();
			PrintWriter out = response.getWriter();

			out.print("<html>");
			out.print("<head><link rel=\"stylesheet\" type=\"text/css\" href=\"css/table.css\" /></head>");
			out.print("<body>");
			out.print("<h1>Payments</h1>");
			out.print("<div class='tableop'>");
			out.print("<table>");
			out.print("<tr><th>Payment ID</th><th>Payment Date</th><th>Allocation ID</th><th>Payment Amount</th><th>Payment Details</th></tr>");

			pst = con.prepareStatement("select * from payment_bill");
			rs = pst.executeQuery();
			while (rs.next()) {
				paymentID = rs.getInt(1);
				allocationID = rs.getInt(2);
				date = rs.getString(3);
				amount = rs.getFloat(4);
				details = rs.getString(5);
				out.print("<tr><td>" + paymentID + "</td><td>" + date + "</td><td>" + allocationID + "</td><td>" + amount + "</td><td>" + details + "</td></tr>");
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
