package com.musemo.service;

import com.musemo.config.DbConfig; // Assuming this is where your database connection logic is

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class DashboardService {

	/**
	 * Retrieves the total count of users with the 'User' role directly from the
	 * database.
	 * 
	 * @return The total number of users.
	 * @throws SQLException           If a database access error occurs.
	 * @throws ClassNotFoundException If the JDBC driver is not found.
	 */
	public int getUserCount() throws SQLException, ClassNotFoundException {
		String sql = "SELECT COUNT(*) FROM user WHERE role = 'User'";
		try (Connection conn = DbConfig.getDbConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			if (rs.next()) {
				return rs.getInt(1);
			}
		}
		return 0;
	}

	/**
	 * Retrieves the total count of bookings directly from the database.
	 * 
	 * @return The total number of bookings.
	 * @throws SQLException           If a database access error occurs.
	 * @throws ClassNotFoundException If the JDBC driver is not found.
	 */
	public int getBookingCount() throws SQLException, ClassNotFoundException {
		String sql = "SELECT COUNT(*) FROM booking";
		try (Connection conn = DbConfig.getDbConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			if (rs.next()) {
				return rs.getInt(1);
			}
		}
		return 0;
	}

	/**
	 * Retrieves the count of currently active exhibitions directly from the
	 * database. An exhibition is considered active if its end date is on or after
	 * the current date.
	 * 
	 * @return The number of active exhibitions.
	 * @throws SQLException           If a database access error occurs.
	 * @throws ClassNotFoundException If the JDBC driver is not found.
	 */
	public int getActiveExhibitionCount() throws SQLException, ClassNotFoundException {
		String sql = "SELECT COUNT(*) FROM exhibition WHERE endDate >= CURRENT_DATE";
		try (Connection conn = DbConfig.getDbConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			if (rs.next()) {
				return rs.getInt(1);
			}
		}
		return 0;
	}

	/**
	 * Retrieves the total count of artifacts directly from the database.
	 * 
	 * @return The total number of artifacts.
	 * @throws SQLException           If a database access error occurs.
	 * @throws ClassNotFoundException If the JDBC driver is not found.
	 */
	public int getArtifactCount() throws SQLException, ClassNotFoundException {
		String sql = "SELECT COUNT(*) FROM artifact";
		try (Connection conn = DbConfig.getDbConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			if (rs.next()) {
				return rs.getInt(1);
			}
		}
		return 0;
	}

	/**
	 * Retrieves the distribution of artifacts by category directly from the
	 * database. Returns a Map where the key is the category and the value is the
	 * count.
	 * 
	 * @return A map of artifact categories and their counts.
	 * @throws SQLException           If a database access error occurs.
	 * @throws ClassNotFoundException If the JDBC driver is not found.
	 */
	public Map<String, Integer> getArtifactDistribution() throws SQLException, ClassNotFoundException {
		Map<String, Integer> distribution = new HashMap<>();
		String sql = "SELECT artifactType, COUNT(*) AS count FROM artifact GROUP BY artifactType";
		try (Connection conn = DbConfig.getDbConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				distribution.put(rs.getString("artifactType"), rs.getInt("count"));
			}
		}
		return distribution;
	}
}
