package com.musemo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.musemo.util.CookieUtil;
import com.musemo.util.SessionUtil;

/**
 * Controller for handling user logout functionality. It removes the user's role
 * cookie and invalidates the current HTTP session, effectively logging the user
 * out of the application.
 * 
 * @author 23048612 Viom Shrestha
 */ 
@WebServlet(asyncSupported = true, urlPatterns = { "/logout" })
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	/**
	 * Handles GET requests to the /logout URL. It simply calls the doPost method to
	 * perform the logout actions. This allows users to log out by either a POST or
	 * a GET request.
	 *
	 * @param request  The HttpServletRequest object containing the client's
	 *                 request.
	 * @param response The HttpServletResponse object for sending the response to
	 *                 the client.
	 * @throws ServletException If a servlet-specific error occurs during
	 *                          processing.
	 * @throws IOException      If an I/O error occurs while handling the request.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Delegate the handling of GET requests to the doPost method
		doPost(request, response);
	}

	/**
	 * Handles POST requests to the /logout URL. It deletes the "role" cookie from
	 * the client's browser and invalidates the current HTTP session. After clearing
	 * the authentication information, it redirects the user to the login page.
	 *
	 * @param request  The HttpServletRequest object containing the client's
	 *                 request.
	 * @param response The HttpServletResponse object for sending the response to
	 *                 the client.
	 * @throws ServletException If a servlet-specific error occurs during
	 *                          processing.
	 * @throws IOException      If an I/O error occurs while handling the request.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Delete the "role" cookie, which stores the user's role for authentication
		CookieUtil.deleteCookie(response, "role");
		// Invalidate the current HTTP session, removing any session attributes
		SessionUtil.invalidateSession(request);
		// Redirect the user to the login page after logging out
		response.sendRedirect(request.getContextPath() + "/login");
	}

}