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
		String sql = "SELECT username, password, fullName FROM user WHERE role='Admin' ";
		try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				UserModel admin = new UserModel();
				admin.setUsername(rs.getString("username"));
				admin.setPassword(rs.getString("password"));
				admin.setFullName(rs.getString("fullName"));
				return admin;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean updateAdminCredentials(UserModel admin) {
		String sql = "UPDATE user SET fullName = ?, username = ?, password = ? WHERE role = 'Admin'";

		try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
			ps.setString(1, admin.getFullName());
			ps.setString(2, admin.getUsername());

			// Handle password logic
			if (admin.getPassword() != null && !admin.getPassword().isEmpty()) {
				ps.setString(3, admin.getPassword()); // already encrypted in controller
			} else {
				// Fetch existing password from DB
				UserModel existing = getAdmin();
				if (existing == null) {
					System.out.println("Existing admin not found.");
					return false;
				}
				ps.setString(3, existing.getPassword());
			}

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
