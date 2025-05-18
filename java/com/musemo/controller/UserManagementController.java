package com.musemo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.musemo.model.UserModel;
import com.musemo.service.UserManagementService;

/**
 * Controller for handling administrative operations related to users, including
 * listing and searching users.
 * 
 * @author 23048612 Viom Shrestha
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/userManagement" })
public class UserManagementController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserManagementService service;

	/**
	 * Initializes the servlet by creating an instance of the UserManagementService.
	 */
	public void init() {
		service = new UserManagementService();
	}

	/**
	 * Handles GET requests to the /userManagement URL. It supports listing all
	 * users or searching for users based on a specified filter and keyword.
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
		// Get the filter criteria from the request parameter (e.g., "username",
		// "fullName")
		String filter = request.getParameter("filter");
		// Get the search keyword from the request parameter
		String keyword = request.getParameter("keyword");

		List<UserModel> users;
		// If a filter and keyword are provided, search for users
		if (filter != null && keyword != null && !keyword.trim().isEmpty()) {
			users = service.searchUsers(filter, keyword);
		} else {
			// Otherwise, retrieve all users
			users = service.getAllUsers();
		}

		// Set the list of users as a request attribute
		request.setAttribute("users", users);
		// Forward the request to the userManagement.jsp page to display the list of
		// users
		request.getRequestDispatcher("/WEB-INF/pages/userManagement.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests to the /userManagement URL. In this implementation, it
	 * simply calls the doGet method to handle any post requests in the same way as
	 * get requests. This is common for pages that primarily display data based on
	 * initial load or simple filtering.
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