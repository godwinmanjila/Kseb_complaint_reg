package com.kseb;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/lineMan")
public class LineMan extends HttpServlet{

	/**
	 * @author Godwin
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			response.setContentType("text/html");

			HttpSession session = request.getSession();
			String message = session.getAttribute("username").toString();

			PrintWriter out = response.getWriter();
			out.println("<h1 style='color:#fff'>Welcome " + message + ",</h1>");

			RequestDispatcher rd = request.getRequestDispatcher("lineman.html");
			rd.include(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}

	}
	
}
