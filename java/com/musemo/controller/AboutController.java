package com.musemo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller class for handling requests to the About page. Maps to the
 * "/about" URL pattern and supports asynchronous operations.
 * 
 *  @author 23048612 Viom Shrestha
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/about" })
public class AboutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Handles HTTP GET requests for the About page. Forwards the request to the
	 * about.jsp view in WEB-INF/pages directory.
	 *
	 * @param request  the HttpServletRequest object containing client request
	 * @param response the HttpServletResponse object for sending responses
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Forward to the about page view
		request.getRequestDispatcher("WEB-INF/pages/about.jsp").forward(request, response);
	}

	/**
	 * Handles HTTP POST requests for the About page. Delegates to the doGet method
	 * to handle the request uniformly.
	 *
	 * @param request  the HttpServletRequest object containing client request
	 * @param response the HttpServletResponse object for sending responses
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Treat POST requests the same as GET requests for this page
		doGet(request, response);
	}
}