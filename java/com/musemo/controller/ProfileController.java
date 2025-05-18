package com.musemo.controller;

import com.musemo.model.UserModel;
import com.musemo.service.ProfileService;
import com.musemo.util.SessionUtil;
import com.musemo.util.CookieUtil;
import com.musemo.util.ValidationUtil;
import com.musemo.util.ImageUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Controller for handling user profile management operations, including
 * viewing, updating, and deleting user profiles.
 * 
 * @author 23048612 Viom Shrestha
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/profile" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB: Threshold after which files are written to disk
		maxFileSize = 1024 * 1024 * 10, // 10MB: Maximum size allowed for uploaded files
		maxRequestSize = 1024 * 1024 * 50 // 50MB: Maximum size allowed for the entire multipart request
)
public class ProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final ProfileService service = new ProfileService();
	private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	/**
	 * Handles GET requests to the /profile URL. It retrieves the logged-in user's
	 * information from the session and displays their profile page. It also handles
	 * the user deletion process.
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
		HttpSession session = request.getSession(false);

		// Check if the user is logged in
		if (session != null) {
			String loggedInUsername = (String) session.getAttribute("username");

			// If no username in session, redirect to login
			if (loggedInUsername == null) {
				response.sendRedirect(request.getContextPath() + "/login");
				return;
			}

			// Handle user deletion request
			String action = request.getParameter("action");
			if ("delete".equals(action)) {
				boolean deleted = service.deleteUser(loggedInUsername);
				if (deleted) {
					// Invalidate session and clear cookies upon successful deletion
					session.invalidate();
					CookieUtil.deleteCookie(response, "username");
					response.sendRedirect("login");
				} else {
					request.setAttribute("error", "Failed to delete account.");
					response.sendRedirect("profile");
				}
				return;
			}

			// Fetch user details for profile display
			UserModel user = service.getUserByUsername(loggedInUsername);
			if (user == null) {
				response.sendRedirect(request.getContextPath() + "/login");
				return;
			}
 
			// Set user information and related data as request attributes
			request.setAttribute("user", user);
			request.setAttribute("dateOfBirth", user.getDateOfBirth());
			request.setAttribute("totalBookings", service.getTotalBookings(loggedInUsername));
			request.setAttribute("exhibitionsVisited", service.getExhibitionsVisited(loggedInUsername));

			// Forward to the profile.jsp page
			request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);
		} else {
			// If no session, redirect to login
			response.sendRedirect(request.getContextPath() + "/login");
		}
	}

	/**
	 * Handles POST requests to the /profile URL. It processes the submission of the
	 * user profile update form.
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
		// Retrieve username from session
		String username = (String) SessionUtil.getAttribute(request, "username");

		// Redirect to login if no username in session
		if (username == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		// Validate the profile update form fields
		String validationMessage = validateProfileForm(request);
		if (validationMessage != null) {
			handleError(request, response, validationMessage);
			return;
		}

		// Extract user profile information from the request parameters
		String fullname = request.getParameter("fullName");
		String password = request.getParameter("newPassword");
		String confirmPassword = request.getParameter("confirmNewPassword");
		String gender = request.getParameter("gender");
		String email = request.getParameter("email");
		String contact = request.getParameter("contact");
		String dobStr = request.getParameter("dateOfBirth");
		LocalDate dateOfBirth = null;

		// Parse the date of birth if provided
		if (dobStr != null && !dobStr.isEmpty()) {
			try {
				dateOfBirth = LocalDate.parse(dobStr, dateFormatter);
			} catch (DateTimeParseException e) {
				request.setAttribute("error", "Invalid date format. Please use YYYY-MM-DD.");
				doGet(request, response);
				return;
			}
		}

		// Check if new passwords match
		if (password != null && !password.isEmpty() && !password.equals(confirmPassword)) {
			request.setAttribute("error", "Passwords do not match.");
			doGet(request, response);
			return;
		}

		// Create a UserModel object with the updated information
		UserModel user = new UserModel();
		user.setUsername(username);
		user.setFullName(fullname);
		user.setGender(gender);
		user.setEmail(email);
		user.setContact(contact);
		user.setDateOfBirth(dateOfBirth);

		// Only set the password if a new one was entered
		if (password != null && !password.isEmpty()) {
			user.setPassword(password);
		}

		ImageUtil imageUtil = new ImageUtil();
		try {
			Part profilePhotoPart = request.getPart("userImage");

			// Handle profile photo upload
			if (profilePhotoPart != null && profilePhotoPart.getSize() > 0) {
				// Validate image extension
				if (!ValidationUtil.isValidImageExtension(profilePhotoPart)) {
					request.setAttribute("error", "Invalid image format. Only jpg, jpeg, png, and gif are allowed.");
					doGet(request, response);
					return;
				}

				// Upload the image and set the filename in the UserModel
				String profilePhotoFileName = imageUtil.getImageNameFromPart(profilePhotoPart);
				String uploadPath = request.getServletContext().getRealPath("/") + "resources/images/user";

				if (!imageUtil.uploadImage(profilePhotoPart, uploadPath, profilePhotoFileName)) {
					request.setAttribute("error", "Profile photo upload failed.");
					doGet(request, response);
					return;
				}
				user.setUserImage(profilePhotoFileName);
			} else {
				// Retain existing profile photo if no new one was uploaded
				UserModel existingUser = service.getUserByUsername(username);
				if (existingUser != null) {
					user.setUserImage(existingUser.getUserImage());
				}
			}

			// Update the user information in the database
			try {
				service.updateUser(user);
				// Invalidate session if password was changed to force re-login
				if (password != null && !password.isEmpty()) {
					request.getSession().invalidate();
					response.sendRedirect(request.getContextPath() + "/login");
				} else {
					request.setAttribute("success", "Profile updated successfully!");
					doGet(request, response);
				}
			} catch (Exception e) {
				request.setAttribute("error", "Failed to update profile.");
				doGet(request, response);
			}

		} catch (IOException | ServletException e) {
			request.setAttribute("error", "Error processing your request: " + e.getMessage());
			doGet(request, response);
		}
	}

	/**
	 * Validates the user profile form data.
	 *
	 * @param req The HttpServletRequest object.
	 * @return An error message if validation fails, null otherwise.
	 */
	private String validateProfileForm(HttpServletRequest req) {
		String username = req.getParameter("username");
		String fullName = req.getParameter("fullName");
		String email = req.getParameter("email");
		String contact = req.getParameter("contact");
		String password = req.getParameter("newPassword");
		String confirmPassword = req.getParameter("confirmNewPassword");
		String dobStr = req.getParameter("dateOfBirth");

		// Check for duplicate email or contact (excluding the current user)
		String duplicateError = service.isUserInfoTaken(username, email, contact);
		if (duplicateError != null) {
			return duplicateError;
		}

		LocalDate dob;
		try {
			dob = LocalDate.parse(dobStr);
		} catch (Exception e) {
			return "Invalid date format. Please use YYYY-MM-DD.";
		}

		if (!ValidationUtil.isAlphabetic(fullName.replaceAll("\\s+", ""))) {
			return "Full Name must contain only letters and spaces.";
		}
		if (!ValidationUtil.isValidEmail(email)) {
			return "Invalid email format.";
		}
		if (!ValidationUtil.isValidPhoneNumber(contact)) {
			return "Phone number must be 10 digits and start with 98.";
		}
		if (!ValidationUtil.isValidAge(dob)) {
			return "You must be at least 16 years old and less than 100 years to register.";
		}
		if ((password != null && !password.isEmpty()) || (confirmPassword != null && !confirmPassword.isEmpty())) {
			if (!ValidationUtil.isValidPassword(password)) {
				return "Password must be at least 8 characters long, with 1 uppercase letter, 1 number, and 1 symbol.";
			}
			if (!ValidationUtil.doPasswordsMatch(password, confirmPassword)) {
				return "Passwords do not match.";
			}
		}

		return null;
	}

	/**
	 * Handles form validation errors by setting the error message and repopulating
	 * the form with the user's input.
	 *
	 * @param req     The HttpServletRequest object.
	 * @param resp    The HttpServletResponse object.
	 * @param message The error message to display.
	 * @throws ServletException If a servlet-specific error occurs.
	 * @throws IOException      If an I/O error occurs.
	 */
	private void handleError(HttpServletRequest req, HttpServletResponse resp, String message)
			throws ServletException, IOException {
		req.setAttribute("error", message);
		req.setAttribute("user", createTempUserModel(req));
		doGet(req, resp);
	}

	/**
	 * Creates a temporary UserModel object to repopulate the form after an error.
	 *
	 * @param req The HttpServletRequest object.
	 * @return A UserModel object with the submitted form values.
	 */
	private UserModel createTempUserModel(HttpServletRequest req) {
		UserModel user = new UserModel();
		user.setFullName(req.getParameter("fullName"));
		user.setGender(req.getParameter("gender"));
		user.setEmail(req.getParameter("email"));
		user.setContact(req.getParameter("contact"));

		String dobStr = req.getParameter("dateOfBirth");
		if (dobStr != null && !dobStr.isEmpty()) {
			try {
				LocalDate dob = LocalDate.parse(dobStr, dateFormatter);
				user.setDateOfBirth(dob);
			} catch (DateTimeParseException e) {
				e.printStackTrace();
			}
		}
		return user;
	}
}