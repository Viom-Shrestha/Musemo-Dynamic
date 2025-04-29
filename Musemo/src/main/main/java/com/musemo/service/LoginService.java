package com.musemo.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.musemo.config.DbConfig;
import com.musemo.model.UserModel;
import com.musemo.util.PasswordUtil;

/**
 * Service class for handling login operations. Connects to the database,
 * verifies user credentials, and returns login status.
 */
public class LoginService {

	private Connection dbConn;
	private boolean isConnectionError = false;

	/**
	 * Constructor initializes the database connection. Sets the connection error
	 * flag if the connection fails.
	 */
	public LoginService() {
		try {
			dbConn = DbConfig.getDbConnection();
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			isConnectionError = true;
		}
	}

	/**
	 * Validates the user credentials against the database records.
	 *
	 * @param studentModel the StudentModel object containing user credentials
	 * @return true if the user credentials are valid, false otherwise; null if a
	 *         connection error occurs
	 */
	public String loginUser(UserModel userModel) {
		if (isConnectionError) {
			System.out.println("Connection Error!");
			return null;
		}

		String query = "SELECT username, password, role FROM user WHERE username = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setString(1, userModel.getUsername());
			ResultSet result = stmt.executeQuery();

			if (result.next()) {
				return validatePassword(result, userModel); // returns the role or "invalid"
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return "invalid"; // username not found
	}

	/**
	 * Validates the password retrieved from the database.
	 *
	 * @param result    the ResultSet containing the username and password from the
	 *                  database
	 * @param userModel the UserModel object containing user credentials
	 * @return true if the passwords match, false otherwise
	 * @throws SQLException if a database access error occurs
	 */
	private String validatePassword(ResultSet result, UserModel userModel) throws SQLException {
		String dbUsername = result.getString("username");
		String dbPassword = result.getString("password");
		String role = result.getString("role");

		String decrypted = PasswordUtil.decrypt(dbPassword, dbUsername);

		if (dbUsername.equals(userModel.getUsername()) && decrypted != null
				&& decrypted.equals(userModel.getPassword())) {
			return role;
		}

		return "invalid";
	}

}