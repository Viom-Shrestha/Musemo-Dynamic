package com.musemo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

// Assuming you have a DatabaseConnection class
import com.musemo.config.DbConfig;

public class DashboardDao {

	/**
     * Retrieves the total count of users with the 'User' role.
     * @return the total number of users.
     * @throws SQLException if a database access error occurs.
     * @throws ClassNotFoundException if the JDBC driver is not found.
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
     * Retrieves the total count of bookings.
     * @return the total number of bookings.
     * @throws SQLException if a database access error occurs.
     * @throws ClassNotFoundException if the JDBC driver is not found.
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
     * Retrieves the count of currently active exhibitions.
     * An exhibition is considered active if its end date is on or after the current date.
     * @return the number of active exhibitions.
     * @throws SQLException if a database access error occurs.
     * @throws ClassNotFoundException if the JDBC driver is not found.
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
	 * Retrieves the total count of artifacts.
	 * @return the total number of artifacts.
	 * @throws SQLException if a database access error occurs.
     * @throws ClassNotFoundException if the JDBC driver is not found (potentially from DbConfig).
	 */
	public int getArtifactCount() throws SQLException, ClassNotFoundException {
		// Use try-with-resources for Connection, Statement, and ResultSet
		String sql = "SELECT COUNT(*) FROM artifact";
		try (Connection conn = DbConfig.getDbConnection(); // Get connection from config (pool preferred)
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(sql)) {
			if (rs.next()) {
				return rs.getInt(1);
			}
		}
		// Should not happen with COUNT(*) unless table is empty, but return 0 is reasonable.
        // Or throw an exception if count must be >= 0
		return 0;
	}

	/**
     * Retrieves the distribution of artifacts by category.
     * Returns a Map where the key is the category and the value is the count.
     * @return a map of artifact categories and their counts.
     * @throws SQLException if a database access error occurs.
     * @throws ClassNotFoundException if the JDBC driver is not found.
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