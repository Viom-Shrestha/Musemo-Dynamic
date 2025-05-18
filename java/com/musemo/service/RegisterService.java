package com.musemo.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.musemo.config.DbConfig;
import com.musemo.model.UserModel;

/**
 * RegisterService handles the registration of new users. It manages database
 * interactions for user registration, including adding the user to the database
 * and checking for the uniqueness of username, email, and contact number.
 * 
 * @author 23048612 Viom Shrestha
 */
public class RegisterService {

	private Connection dbConn;

	/**
	 * Constructor initializes the database connection.
	 */
	public RegisterService() {
		try {
			this.dbConn = DbConfig.getDbConnection();
		} catch (SQLException | ClassNotFoundException ex) {
			System.err.println("Database connection error: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * Registers a new user in the database.
	 *
	 * @param userModel The user details to be registered.
	 * @return Boolean indicating the success of the operation (true if successful,
	 *         false otherwise), or null if the database connection is unavailable.
	 */
	public Boolean addUser(UserModel userModel) {
		if (dbConn == null) {
			System.err.println("Database connection is not available.");
			return null;
		}

		String insertQuery = "INSERT INTO user (username, fullName, password, role, gender, email, contact, dateOfBirth, userImage) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement insertStmt = dbConn.prepareStatement(insertQuery)) {
			insertStmt.setString(1, userModel.getUsername());
			insertStmt.setString(2, userModel.getFullName());
			insertStmt.setString(3, userModel.getPassword());
			insertStmt.setString(4, "User"); // Default role for registered users
			insertStmt.setString(5, userModel.getGender());
			insertStmt.setString(6, userModel.getEmail());
			insertStmt.setString(7, userModel.getContact());
			insertStmt.setDate(8, Date.valueOf(userModel.getDateOfBirth()));
			insertStmt.setString(9, userModel.getUserImage());

			return insertStmt.executeUpdate() > 0; // Returns true if one or more rows were affected

		} catch (SQLException e) {
			System.err.println("Error during user registration: " + e.getMessage());
			e.printStackTrace();
			return null; // Indicate failure due to SQL exception
		}
	}

	/**
	 * Checks if the provided username, email, or contact number is already taken by
	 * another user in the database.
	 *
	 * @param username The username to check.
	 * @param email    The email address to check.
	 * @param contact  The contact number to check.
	 * @return A string message indicating which information is already taken
	 *         ("Username is already taken.", "Email is already registered.",
	 *         "Contact number is already registered."), or null if all provided
	 *         information is unique. Returns "Database connection not available."
	 *         if the connection fails, or "Server error occurred. Please try again
	 *         later." if any other SQLException occurs.
	 */
	public String isUserInfoTaken(String username, String email, String contact) {
		if (dbConn == null) {
			return "Database connection not available.";
		}

		String query = "SELECT username, email, contact FROM user WHERE username = ? OR email = ? OR contact = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setString(1, username);
			stmt.setString(2, email);
			stmt.setString(3, contact);

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				if (username.equalsIgnoreCase(rs.getString("username"))) {
					return "Username is already taken.";
				} else if (email.equalsIgnoreCase(rs.getString("email"))) {
					return "Email is already registered.";
				} else if (contact.equals(rs.getString("contact"))) {
					return "Contact number is already registered.";
				}
			}
		} catch (SQLException e) {
			System.err.println("Error checking user uniqueness: " + e.getMessage());
			e.printStackTrace();
			return "Server error occurred. Please try again later.";
		}

		return null; // All provided information is unique
	}
}