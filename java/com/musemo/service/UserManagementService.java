package com.musemo.service;

import com.musemo.config.DbConfig;
import com.musemo.model.UserModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserManagementService {
	private Connection dbConn;

	public UserManagementService() {
		try {
			dbConn = DbConfig.getDbConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<UserModel> getAllUsers() {
		List<UserModel> userList = new ArrayList<>();
		String sql = "SELECT username, fullName, password, gender, email, dateOfBirth, contact, role, userImage FROM user WHERE role = 'User' ";
		try (PreparedStatement ps = dbConn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				UserModel user = new UserModel();
				user.setUsername(rs.getString("username"));
				user.setFullName(rs.getString("fullName"));
				user.setPassword(rs.getString("password"));
				user.setGender(rs.getString("gender"));
				user.setEmail(rs.getString("email"));
				user.setContact(rs.getString("contact"));
				user.setRole(rs.getString("role"));
				Date dob = rs.getDate("dateOfBirth");
				if (dob != null)
					user.setDateOfBirth(dob.toLocalDate());
				user.setUserImage(rs.getString("userImage"));
				userList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userList;
	}

	public List<UserModel> searchUsers(String filter, String keyword) {
		List<UserModel> userList = new ArrayList<>();

		String column;
		switch (filter) {
		case "username":
			column = "username";
			break;
		case "name":
			column = "fullName";
			break;
		case "email":
			column = "email";
			break;
		case "role":
			column = "role";
			break;
		default:
			return getAllUsers(); // fallback
		}

		String sql = "SELECT username, fullName, password, gender, email, dateOfBirth, contact, role, userImage "
				+ "FROM user WHERE " + column + " LIKE ?";
		try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
			ps.setString(1, "%" + keyword + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				UserModel user = new UserModel();
				user.setUsername(rs.getString("username"));
				user.setFullName(rs.getString("fullName"));
				user.setPassword(rs.getString("password"));
				user.setGender(rs.getString("gender"));
				user.setEmail(rs.getString("email"));
				user.setContact(rs.getString("contact"));
				user.setRole(rs.getString("role"));
				Date dob = rs.getDate("dateOfBirth");
				if (dob != null)
					user.setDateOfBirth(dob.toLocalDate());
				user.setUserImage(rs.getString("userImage"));
				userList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userList;
	}

}
