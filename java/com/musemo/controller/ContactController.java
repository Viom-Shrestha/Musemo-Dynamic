package com.musemo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller for handling requests to the contact page. This controller simply
 * forwards the request to the contact.jsp view.
 *
 *  @author 23048612 Viom Shrestha
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/contact" })
public class ContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Handles GET requests to the /contact URL. It forwards the request to the
	 * contact.jsp page, which presumably displays contact information for the
	 * museum.
	 *
	 * @param request  The HttpServletRequest object containing the client's
	 *                 request.
	 * @param response The HttpServletResponse object for sending the response to
	 *                 the client.
	 * @throws ServletException If a servlet-specific error occurs during
	 *                          processing.
	 * @throws IOException      If an I/O error occurs while handling the request.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Forward the request to the contact.jsp page located in the WEB-INF/pages
		// directory
		request.getRequestDispatcher("WEB-INF/pages/contact.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests to the /contact URL. In this implementation, it simply
	 * calls the doGet method to handle any post requests in the same way as get
	 * requests. This is common for pages that primarily display static content or
	 * have simple form submissions that don't require distinct processing.
	 *
	 * @param request  The HttpServletRequest object containing the client's
	 *                 request.
	 * @param response The HttpServletResponse object for sending the response to
	 *                 the client.
	 * @throws ServletException If a servlet-specific error occurs during
	 *                          processing.
	 * @throws IOException      If an I/O error occurs while handling the request.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Delegate the handling of POST requests to the doGet method
		doGet(request, response);
	}
}