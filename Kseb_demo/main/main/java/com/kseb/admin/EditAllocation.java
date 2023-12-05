package com.kseb.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/editAllocation")
public class EditAllocation extends HttpServlet{

	/**
	 * @author Godwin
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");

			PrintWriter out = response.getWriter();

			int allocationID = Integer.parseInt(request.getParameter("allocationID"));

			out.print("<html>");
			out.print("<head><link rel=\"stylesheet\" type=\"text/css\" href=\"css/addComplaint.css\" /></head>");
			out.print("<body>");
			out.print("<div class='add-complaint-box'> ");
			out.print(" <h2>Edit Work Allocation</h2> ");
			out.print(" <form method='post' action='editAllocationSave'>");
			out.print(" <div class='user-box'> ");
			out.print(" <input type='number' name='allocationID' required='' id='allocationID' readonly='readonly' value='"+ allocationID + "'>");
			out.print(" <label class='selectLabel'>Allocation ID</label> ");
			out.print(" </div> ");
			out.print(" <div class='user-box'> ");
			out.print(" <input type='text' name='complaintDetails' required='' id='complaintDetails'>");
			out.print(" <label>Complaint Details</label>");
			out.print(" </div> ");
			out.print(" <div class='wrapper'>");
			out.print(" <input type='submit' value='ADD COMPLAINT' id='submit' />");
			out.print(" <span></span> <span></span> <span></span> <span></span> <span></span>");
			out.print(" </div> ");
			out.print(" </form>");
			out.print("</div>");
			out.print("</body>");
			out.print("</html>");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
