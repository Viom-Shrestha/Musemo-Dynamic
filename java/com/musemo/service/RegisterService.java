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
 * interactions for user registration.
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
	 * @param userModel the user details to be registered
	 * @return Boolean indicating the success of the operation
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
			insertStmt.setString(4, "User");
			insertStmt.setString(5, userModel.getGender());
			insertStmt.setString(6, userModel.getEmail());
			insertStmt.setString(7, userModel.getContact());
			insertStmt.setDate(8, Date.valueOf(userModel.getDateOfBirth())); // assuming getDateOfBirth returns
																				// LocalDate
			insertStmt.setString(9, userModel.getUserImage()); // can be null or a default image

			return insertStmt.executeUpdate() > 0;

		} catch (SQLException e) {
			System.err.println("Error during user registration: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

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

		return null;
	}

}