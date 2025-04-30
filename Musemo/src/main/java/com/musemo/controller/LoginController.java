package com.musemo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.musemo.model.UserModel;
import com.musemo.service.LoginService;
import com.musemo.util.CookieUtil;
import com.musemo.util.SessionUtil;

/**
 * @author Viom Shrestha
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/login","/"})
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final LoginService loginService;

	/**
	 * Constructor initializes the LoginService.
	 */
	public LoginController() {
		this.loginService = new LoginService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(request, response);
	}

	/**
	 * Handles GET requests to the login page.
	 *
	 * @param request  HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    String username = req.getParameter("username");
	    String password = req.getParameter("password");

	    UserModel userModel = new UserModel(username, password);
	    String role = loginService.loginUser(userModel);

	    if (role == null) {
	        handleLoginFailure(req, resp, null); // DB or connection error
	        return; // Important: Exit after handling failure
	    } else if (role.equals("invalid")) {
	        handleLoginFailure(req, resp, false); // wrong username or password
	        return; // Important: Exit after handling failure
	    } else {
	        SessionUtil.setAttribute(req, "username", username);
	        CookieUtil.addCookie(resp, "role", role, 5 * 30);

	        if (role.equalsIgnoreCase("Admin")) {
	            resp.sendRedirect(req.getContextPath() + "/dashboard");
	        } else {
	            resp.sendRedirect(req.getContextPath() + "/home");
	        }
	    }
	}

	/**
	 * Handles login failures by setting attributes and forwarding to the login
	 * page.
	 *
	 * @param req         HttpServletRequest object
	 * @param resp        HttpServletResponse object
	 * @param loginStatus Boolean indicating the login status
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	private void handleLoginFailure(HttpServletRequest req, HttpServletResponse resp, Boolean loginStatus)
			throws ServletException, IOException {
		String errorMessage;
		if (loginStatus == null) {
			errorMessage = "Our server is under maintenance. Please try again later!";
		} else {
			errorMessage = "User credential mismatch. Please try again!";
		}
		req.setAttribute("error", errorMessage);
		req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
	}


}
