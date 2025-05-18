package com.musemo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.musemo.model.UserModel;
import com.musemo.service.ProfileService;
import com.musemo.util.PasswordUtil;
import com.musemo.util.ValidationUtil;

/**
 * Controller for handling admin profile management operations including viewing
 * and updating admin profile information.
 *
 * @author 23048612 Viom Shrestha
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/adminProfile" })
public class AdminProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProfileService service = new ProfileService();

	/**
	 * Handles GET requests to display the admin profile page. Retrieves the current
	 * admin's details from the ProfileService and forwards the request to the
	 * adminProfile.jsp view page.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve the current admin user's details using the service
		UserModel admin = service.getAdmin();

		// Set the admin UserModel as an attribute in the request
		request.setAttribute("admin", admin);

		// Forward the request to the admin profile view page to display the information
		request.getRequestDispatcher("/WEB-INF/pages/adminProfile.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests for updating the admin profile information submitted
	 * from the admin profile form. It validates the input fields, updates the
	 * admin's details in the database if the validation is successful, and then
	 * redirects back to the admin profile page with a success or error message.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// First, validate all the input fields from the admin profile form
		String validationMessage = validateAdminProfileForm(request);
		if (validationMessage != null) {
			// If any validation fails, handle the error and return to the form
			handleError(request, response, validationMessage);
			return;
		}

		// Retrieve the updated values from the request parameters
		String fullName = request.getParameter("fullName");
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String contact = request.getParameter("contact");
		String password = request.getParameter("password");

		// Create a new UserModel object to hold the updated admin details
		UserModel admin = new UserModel();
		admin.setFullName(fullName);
		admin.setUsername(username);
		admin.setEmail(email);
		admin.setContact(contact);

		// Only encrypt and set the password if a new password was provided by the user
		if (!ValidationUtil.isNullOrEmpty(password)) {
			admin.setPassword(PasswordUtil.encrypt(username, password));
		} else {
			admin.setPassword(null);
		}

		// Attempt to update the admin's credentials in the database using the service
		boolean updated = service.updateAdminCredentials(admin);
		if (updated) {
			// If the update was successful, set a success message as a request attribute
			request.setAttribute("success", "Profile updated successfully.");
		} else {
			// If the update failed, set an error message as a request attribute
			request.setAttribute("error", "Failed to update profile.");
		}

		// Refresh the admin data from the database to display the updated information
		admin = service.getAdmin();
		request.setAttribute("admin", admin);

		// Forward the request back to the admin profile page to display the updated
		// information or error message
		request.getRequestDispatcher("/WEB-INF/pages/adminProfile.jsp").forward(request, response);
	}

	/**
	 * Validates the admin profile form inputs. It checks for duplicate username,
	 * email, or contact, and validates the format of each field using the
	 * ValidationUtil class.
	 *
	 * @param request The HttpServletRequest containing the form parameters.
	 * @return An error message string if any validation fails, or null if all
	 *         inputs are valid.
	 */
	private String validateAdminProfileForm(HttpServletRequest request) {
		String username = request.getParameter("username");
		String fullName = request.getParameter("fullName");
		String email = request.getParameter("email");
		String contact = request.getParameter("contact");
		String password = request.getParameter("password");

		// Check if the provided username, email, or contact are already taken by
		// another user
		String duplicateError = service.isUserInfoTaken(username, email, contact);
		if (duplicateError != null) {
			return duplicateError;
		}
		// Validate that the full name contains only letters and spaces
		if (ValidationUtil.isNullOrEmpty(fullName) || !ValidationUtil.isAlphabetic(fullName.replaceAll("\\s+", ""))) {
			return "Full name must contain only letters and spaces.";
		}
		// Validate the format of the email address
		if (!ValidationUtil.isValidEmail(email)) {
			return "Invalid email format.";
		}
		// Validate the format of the phone number (must be 10 digits and start with 98)
		if (!ValidationUtil.isValidPhoneNumber(contact)) {
			return "Phone number must be 10 digits and start with 98.";
		}
		// Validate the password if it's not empty, checking for complexity requirements
		if (!ValidationUtil.isNullOrEmpty(password) && !ValidationUtil.isValidPassword(password)) {
			return "Password must be at least 8 characters long, include an uppercase letter, a number, and a symbol.";
		}
		// If all validations pass, return null
		return null;
	}

	/**
	 * Handles form validation errors by setting the error message as a request
	 * attribute and creating a temporary UserModel with the submitted values to
	 * repopulate the form fields before redisplaying the form.
	 *
	 * @param req     The HttpServletRequest object.
	 * @param resp    The HttpServletResponse object.
	 * @param message The error message to be displayed.
	 * @throws ServletException If a servlet-specific error occurs.
	 * @throws IOException      If an I/O error occurs.
	 */
	private void handleError(HttpServletRequest req, HttpServletResponse resp, String message)
			throws ServletException, IOException {
		// Set the error message as a request attribute
		req.setAttribute("error", message);

		// Create a temporary UserModel with the values submitted in the form
		req.setAttribute("admin", createTempUserModel(req));

		// Forward the request back to the admin profile page to display the error and
		// the form with previous inputs
		doGet(req, resp);
	}

	/**
	 * Creates a temporary UserModel object from the request parameters. This is
	 * used to repopulate the form fields with the user's input after a validation
	 * error occurs.
	 *
	 * @param req The HttpServletRequest object containing the form parameters.
	 * @return A UserModel object populated with the submitted form values.
	 */
	private UserModel createTempUserModel(HttpServletRequest req) {
		UserModel user = new UserModel();
		user.setFullName(req.getParameter("fullName"));
		user.setEmail(req.getParameter("email"));
		user.setContact(req.getParameter("contact"));
		user.setUsername(req.getParameter("username")); // Include username for redisplaying
		return user;
	}
}