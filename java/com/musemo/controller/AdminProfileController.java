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
import com.musemo.util.ValidationUtil;

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

		String validationMessage = validateAdminProfileForm(request);
		if (validationMessage != null) {
			handleError(request, response, validationMessage);
			return;
		}

		String fullName = request.getParameter("fullName");
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String contact = request.getParameter("contact");
		String password = request.getParameter("password");

		UserModel admin = new UserModel();
		admin.setFullName(fullName);
		admin.setUsername(username);
		admin.setEmail(email);
		admin.setContact(contact);

		if (!ValidationUtil.isNullOrEmpty(password)) {
			admin.setPassword(PasswordUtil.encrypt(username, password));
		} else {
			admin.setPassword(null); // Retain existing password
		}

		boolean updated = service.updateAdminCredentials(admin);
		if (updated) {
			request.setAttribute("success", "Profile updated successfully.");
		} else {
			request.setAttribute("error", "Failed to update profile.");
		}

		admin = service.getAdmin();
		request.setAttribute("admin", admin);
		request.getRequestDispatcher("/WEB-INF/pages/adminProfile.jsp").forward(request, response);
	}

	private String validateAdminProfileForm(HttpServletRequest request) {
		String username = request.getParameter("username");
		String fullName = request.getParameter("fullName");
		String email = request.getParameter("email");
		String contact = request.getParameter("contact");
		String password = request.getParameter("password");

		String duplicateError = service.isUserInfoTaken(username, email, contact);
		if (duplicateError != null) {
			return duplicateError;
		}

		if (ValidationUtil.isNullOrEmpty(fullName) || !ValidationUtil.isAlphabetic(fullName.replaceAll("\\s+", ""))) {
			return "Full name must contain only letters and spaces.";
		}

		if (!ValidationUtil.isValidEmail(email)) {
			return "Invalid email format.";
		}

		if (!ValidationUtil.isValidPhoneNumber(contact)) {
			return "Phone number must be 10 digits and start with 98.";
		}

		if (!ValidationUtil.isNullOrEmpty(password) && !ValidationUtil.isValidPassword(password)) {
			return "Password must be at least 8 characters long, include an uppercase letter, a number, and a symbol.";
		}

		return null;
	}

	private void handleError(HttpServletRequest req, HttpServletResponse resp, String message)
			throws ServletException, IOException {
		req.setAttribute("error", message);
		req.setAttribute("admin", createTempUserModel(req));
		doGet(req, resp);
	}

	private UserModel createTempUserModel(HttpServletRequest req) {
		UserModel user = new UserModel();
		user.setFullName(req.getParameter("fullName"));
		user.setEmail(req.getParameter("email"));
		user.setContact(req.getParameter("contact"));

		return user;
	}
}
