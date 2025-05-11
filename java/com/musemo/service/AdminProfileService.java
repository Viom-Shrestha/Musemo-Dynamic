package com.musemo.service;

import com.musemo.config.DbConfig;
import com.musemo.model.UserModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminProfileService {

	private Connection dbConn;

	public AdminProfileService() {
		try {
			dbConn = DbConfig.getDbConnection();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public UserModel getAdmin() {
		String sql = "SELECT username, password, fullName, email, contact FROM user WHERE role='Admin'";
		try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				UserModel admin = new UserModel();
				admin.setUsername(rs.getString("username"));
				admin.setPassword(rs.getString("password"));
				admin.setFullName(rs.getString("fullName"));
				admin.setEmail(rs.getString("email"));
				admin.setContact(rs.getString("contact"));
				return admin;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean updateAdminCredentials(UserModel admin) {
		String sql = "UPDATE user SET fullName = ?, username = ?, password = ?, email = ?, contact = ? WHERE role = 'Admin'";

		try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
			ps.setString(1, admin.getFullName());
			ps.setString(2, admin.getUsername());

			// Handle password logic
			if (admin.getPassword() != null && !admin.getPassword().isEmpty()) {
				ps.setString(3, admin.getPassword()); // Password is already encrypted in the controller
			} else {
				// Fetch the existing password if not provided
				UserModel existing = getAdmin();
				if (existing == null) {
					System.out.println("Existing admin not found.");
					return false;
				}
				ps.setString(3, existing.getPassword());
			}

			// Set email and contact fields
			ps.setString(4, admin.getEmail());
			ps.setString(5, admin.getContact());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String isUserInfoTaken(String username, String email, String contact) {
		if (dbConn == null) {
			return "Database connection not available.";
		}

		String query = "SELECT username, email, contact FROM user WHERE (email = ? OR contact = ?) AND username != ?";

		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setString(1, email);
			stmt.setString(2, contact);
			stmt.setString(3, username); // Exclude current user's record

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				if (email.equalsIgnoreCase(rs.getString("email"))) {
					return "This Email is already in used.";
				} else if (contact.equals(rs.getString("contact"))) {
					return "Contact number is already registered.";
				}
			}
		} catch (SQLException e) {
			System.err.println("Error checking user uniqueness: " + e.getMessage());
			e.printStackTrace();
			return "Server error occurred. Please try again later.";
		}

		return null; // No conflicts
	}

}
