package com.musemo.controller;

import com.musemo.model.UserModel;
import com.musemo.service.ProfileService;
import com.musemo.util.SessionUtil;
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

@WebServlet(asyncSupported = true, urlPatterns = { "/profile" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class ProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final ProfileService profileService = new ProfileService();
	private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session != null) {
			String loggedInUsername = (String) session.getAttribute("username");
			if (loggedInUsername == null) {
				response.sendRedirect(request.getContextPath() + "/login");
				return;
			}

			UserModel user = profileService.getUserByUsername(loggedInUsername);
			if (user == null) {
				response.sendRedirect(request.getContextPath() + "/login");
				return;
			}

			request.setAttribute("user", user);
			request.setAttribute("dateOfBirth", user.getDateOfBirth());
			request.setAttribute("totalBookings", profileService.getTotalBookings(loggedInUsername));
			request.setAttribute("exhibitionsVisited", profileService.getExhibitionsVisited(loggedInUsername));

			request.getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/login");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = (String) SessionUtil.getAttribute(request, "username");

		if (username == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		// Validate form fields (excluding password confirmation if new password is
		// empty)
		String validationMessage = validateProfileForm(request);
		if (validationMessage != null) {
			handleError(request, response, validationMessage);
			return;
		}

		// Extract fields
		String fullname = request.getParameter("fullName");
		String password = request.getParameter("newPassword");
		String confirmPassword = request.getParameter("confirmNewPassword");
		String gender = request.getParameter("gender");
		String email = request.getParameter("email");
		String contact = request.getParameter("contact");
		String dobStr = request.getParameter("dateOfBirth");
		LocalDate dateOfBirth = null;

		if (dobStr != null && !dobStr.isEmpty()) {
			try {
				dateOfBirth = LocalDate.parse(dobStr, dateFormatter);
			} catch (DateTimeParseException e) {
				request.setAttribute("error", "Invalid date format. Please use YYYY-MM-DD.");
				doGet(request, response);
				return;
			}
		}

		if (password != null && !password.isEmpty() && !password.equals(confirmPassword)) {
			request.setAttribute("error", "Passwords do not match.");
			doGet(request, response);
			return;
		}

		// Prepare updated UserModel
		UserModel user = new UserModel();
		user.setUsername(username);
		user.setFullName(fullname);

		// Only set the password if a new one was entered
		if (password != null && !password.isEmpty()) {
			user.setPassword(password);
		}

		user.setGender(gender);
		user.setEmail(email);
		user.setContact(contact);
		user.setDateOfBirth(dateOfBirth);

		ImageUtil imageUtil = new ImageUtil();
		Part profilePhotoPart = request.getPart("userImage");
		String profilePhotoFileName = null;

		if (profilePhotoPart != null && profilePhotoPart.getSize() > 0) {
			System.out.println("Received profile image: " + profilePhotoPart.getSubmittedFileName());
			System.out.println("File size: " + profilePhotoPart.getSize());

			profilePhotoFileName = imageUtil.getImageNameFromPart(profilePhotoPart);
			System.out.println("Extracted image name: " + profilePhotoFileName);

			String uploadPath = request.getServletContext().getRealPath("/") + "resources/imagesuser";
			System.out.println("Resolved upload path: " + uploadPath);

			boolean uploaded = imageUtil.uploadImage(profilePhotoPart, uploadPath, imageUtil.getImageNameFromPart(profilePhotoPart));

			if (!uploaded) {
				System.out.println("Image upload failed.");
				request.setAttribute("error", "Profile photo upload failed.");
				doGet(request, response);
				return;
			} else {
				System.out.println("Image uploaded successfully.");
				// Save the relative path inside DB
				user.setUserImage(profilePhotoFileName);
				profileService.updateUser(user);

				request.setAttribute("success", "Profile updated successfully!");
				doGet(request, response);
			}

			
		} else {
			System.out.println("No new profile photo provided. Retaining existing image.");
			UserModel existingUser = profileService.getUserByUsername(username);
			if (existingUser != null) {
				user.setUserImage(existingUser.getUserImage());
			}
		}

		// Perform update only after image logic is complete
		System.out.println("Updating user profile in database...");
		profileService.updateUser(user);
		System.out.println("User profile updated successfully.");


		// Update in DB
		
	}

	private String validateProfileForm(HttpServletRequest req) {
		String fullName = req.getParameter("fullName");
		String gender = req.getParameter("gender");
		String email = req.getParameter("email");
		String contact = req.getParameter("contact");
		String password = req.getParameter("newPassword");
		String confirmPassword = req.getParameter("confirmNewPassword");

		if (!ValidationUtil.isAlphabetic(fullName.replaceAll("\\s+", ""))) {
			return "Full Name must contain only letters and spaces.";
		}
		if (!ValidationUtil.isValidGender(gender)) {
			return "Invalid gender.";
		}
		if (!ValidationUtil.isValidEmail(email)) {
			return "Invalid email format.";
		}
		if (!ValidationUtil.isValidPhoneNumber(contact)) {
			return "Phone number must be 10 digits and start with 98.";
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

	private void handleError(HttpServletRequest req, HttpServletResponse resp, String message)
			throws ServletException, IOException {
		req.setAttribute("error", message);
		req.setAttribute("user", createTempUserModel(req));
		doGet(req, resp);
	}

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
