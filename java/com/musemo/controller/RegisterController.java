package com.musemo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.time.LocalDate;

import com.musemo.model.UserModel;
import com.musemo.service.RegisterService;
import com.musemo.util.ImageUtil;
import com.musemo.util.PasswordUtil;
import com.musemo.util.ValidationUtil;

/**
 * Controller for handling user registration requests. It validates user input,
 * checks for duplicate information, encrypts the password, uploads the user's
 * profile image, and adds the new user to the database.
 *
 *  @author 23048612 Viom Shrestha
 */
@WebServlet("/register")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB: Threshold after which files are written to disk
		maxFileSize = 1024 * 1024 * 10, // 10MB: Maximum size allowed for uploaded files
		maxRequestSize = 1024 * 1024 * 50) // 50MB: Maximum size allowed for the entire multipart request
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final RegisterService registerService = new RegisterService();

	/**
	 * Handles GET requests to the /register URL. It simply forwards the request to
	 * the register.jsp page to display the registration form.
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
		request.getRequestDispatcher("WEB-INF/pages/register.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests to the /register URL. It validates the registration
	 * form data, extracts the user information, encrypts the password, handles the
	 * uploaded image, and attempts to add the new user to the database. Based on
	 * the outcome, it either redirects to the login page with a success message or
	 * redisplays the registration form with an error message.
	 *
	 * @param req  The HttpServletRequest object containing the client's request.
	 * @param resp The HttpServletResponse object for sending the response to the
	 *             client.
	 * @throws ServletException If a servlet-specific error occurs during
	 *                          processing.
	 * @throws IOException      If an I/O error occurs while handling the request.
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			// Validate the registration form data
			String validationMessage = validateRegistrationForm(req);
			if (validationMessage != null) {
				handleError(req, resp, validationMessage);
				return;
			}

			// Extract user information from the request
			UserModel userModel = extractUserModel(req);
			// Attempt to add the new user to the database
			Boolean isAdded = registerService.addUser(userModel);

			// Handle the result of the user registration attempt
			if (isAdded == null) {
				handleError(req, resp, "Our server is under maintenance. Please try again later!");
			} else if (isAdded) {
				// If user was added successfully, attempt to upload the image
				try {
					if (uploadImage(req)) {
						req.setAttribute("success", "Your account is successfully created!");
						req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
					} else {
						handleError(req, resp, "Could not upload the image. Please try again later!");
					}
				} catch (IOException | ServletException e) {
					handleError(req, resp, "An error occurred while uploading the image. Please try again later!");
					e.printStackTrace(); // Log the exception
				}
			} else {
				handleError(req, resp, "Could not register your account. Please try again later!");
			}
		} catch (Exception e) {
			handleError(req, resp, "An unexpected error occurred. Please try again later!");
			e.printStackTrace(); // Log the exception
		}
	}

	/**
	 * Validates the registration form data submitted by the user.
	 *
	 * @param req The HttpServletRequest object containing the form data.
	 * @return An error message if any validation fails, or null if all inputs are
	 *         valid.
	 * @throws IOException      If an I/O error occurs while handling the request.
	 * @throws ServletException If a servlet-specific error occurs while handling
	 *                          the request.
	 */
	private String validateRegistrationForm(HttpServletRequest req) throws IOException, ServletException {
		String username = req.getParameter("username");
		String dobStr = req.getParameter("dob");
		String email = req.getParameter("email");
		String contact = req.getParameter("contact");
		String password = req.getParameter("password");

		// Check if the username, email, or contact are already taken
		String duplicateError = registerService.isUserInfoTaken(username, email, contact);
		if (duplicateError != null) {
			return duplicateError;
		}

		// Validate the date of birth format
		LocalDate dob;
		try {
			dob = LocalDate.parse(dobStr);
		} catch (Exception e) {
			return "Invalid date format. Please use<ctrl3348>-MM-DD.";
		}

		// Validate individual fields using ValidationUtil
		if (!ValidationUtil.isAlphanumericStartingWithLetter(username))
			return "Username must start with a letter and contain only letters and numbers.";
		if (!ValidationUtil.isValidEmail(email))
			return "Invalid email format.";
		if (!ValidationUtil.isValidPhoneNumber(contact))
			return "Phone number must be 10 digits and start with 98.";
		if (!ValidationUtil.isValidPassword(password))
			return "Password must be at least 8 characters long, with 1 uppercase letter, 1 number, and 1 symbol.";
		if (!ValidationUtil.isValidAge(dob))
			return "You must be at least 16 years old and less than 100 years to register.";

		// Validate the uploaded image
		Part image = req.getPart("image");
		if (image != null && image.getSize() > 0 && !ValidationUtil.isValidImageExtension(image))
			return "Invalid image format. Only jpg, jpeg, png, and gif are allowed.";

		return null;
	}

	/**
	 * Extracts user information from the HttpServletRequest and creates a UserModel
	 * object.
	 *
	 * @param req The HttpServletRequest object containing the user's registration
	 *            data.
	 * @return A UserModel object populated with the registration information.
	 * @throws Exception If an error occurs during data extraction.
	 */
	private UserModel extractUserModel(HttpServletRequest req) throws Exception {
		String fullName = req.getParameter("fullName");
		String username = req.getParameter("username");
		LocalDate dob = LocalDate.parse(req.getParameter("dob"));
		String gender = req.getParameter("gender");
		String email = req.getParameter("email");
		String contact = req.getParameter("contact");
		String password = req.getParameter("password");

		// Encrypt the user's password
		String encryptedPassword = PasswordUtil.encrypt(username, password);

		// Handle the uploaded image
		ImageUtil imageUtil = new ImageUtil();
		Part image = req.getPart("image");
		String imageName = (image != null && image.getSize() > 0) ? imageUtil.getImageNameFromPart(image) : null;

		// Set the default role for new users
		return new UserModel(username, fullName, encryptedPassword, "User", gender, email, contact, dob, imageName);
	}

	/**
	 * Uploads the user's profile image to the server.
	 *
	 * @param req The HttpServletRequest object containing the uploaded image.
	 * @return True if the image was uploaded successfully, false otherwise.
	 * @throws IOException      If an I/O error occurs during image upload.
	 * @throws ServletException If a servlet-specific error occurs during image
	 *                          upload.
	 */
	private boolean uploadImage(HttpServletRequest req) throws IOException, ServletException {
		Part image = req.getPart("image");
		if (image != null && image.getSize() > 0) {
			ImageUtil imageUtil = new ImageUtil();
			String uploadPath = req.getServletContext().getRealPath("/") + "resources/images/user";
			return imageUtil.uploadImage(image, uploadPath, imageUtil.getImageNameFromPart(image));
		}
		return true; // No image to upload, consider it successful
	}

	/**
	 * Handles registration errors by setting an error message as a request
	 * attribute and forwarding back to the registration page, repopulating the form
	 * with the user's previous input.
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
		req.setAttribute("fullName", req.getParameter("fullName"));
		req.setAttribute("username", req.getParameter("username"));
		req.setAttribute("dob", req.getParameter("dob"));
		req.setAttribute("gender", req.getParameter("gender"));
		req.setAttribute("email", req.getParameter("email"));
		req.setAttribute("contact", req.getParameter("contact"));
		req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
	}
}