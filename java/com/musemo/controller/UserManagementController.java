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
 * Servlet implementation class userManagementController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/userManagement" })
public class UserManagementController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserManagementService service;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public void init() {
		service = new UserManagementService();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String filter = request.getParameter("filter");
	    String keyword = request.getParameter("keyword");
	    
		List<UserModel> users;
		if (filter != null && keyword != null && !keyword.trim().isEmpty()) {
	        users = service.searchUsers(filter, keyword);
	    } else {
	        users = service.getAllUsers();
	    }
		
		request.setAttribute("users", users);
		request.getRequestDispatcher("/WEB-INF/pages/userManagement.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
