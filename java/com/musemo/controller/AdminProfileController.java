package com.musemo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.musemo.model.UserModel;
import com.musemo.service.AdminProfileService;
import com.musemo.util.PasswordUtil;

/**
 * @author Viom Shrestha
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/adminProfile" })
public class AdminProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AdminProfileService service = new AdminProfileService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserModel admin = service.getAdmin();
		request.setAttribute("admin", admin);
		request.getRequestDispatcher("/WEB-INF/pages/adminProfile.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    String fullName = request.getParameter("fullName");
	    String username = request.getParameter("username");
	    String password = request.getParameter("password");

	    // Create admin model
	    UserModel admin = new UserModel();
	    admin.setFullName(fullName);
	    admin.setUsername(username);

	    // Only encrypt and set password if provided
	    if (password != null && !password.isEmpty()) {
	        String encryptedPassword = PasswordUtil.encrypt(username, password);
	        admin.setPassword(encryptedPassword);
	    } else {
	        admin.setPassword(null); // Let service handle keeping old password
	    }

	    // Update admin data
	    boolean updated = service.updateAdminCredentials(admin);

	    if (updated) {
	        request.setAttribute("success", "Profile updated successfully.");
	    } else {
	        request.setAttribute("error", "Failed to update profile.");
	    }

	    // Reload and send updated data to JSP
	    admin = service.getAdmin();
	    request.setAttribute("admin", admin);
	    request.getRequestDispatcher("/WEB-INF/pages/adminProfile.jsp").forward(request, response);
	}

}
