package com.musemo.service;

import com.musemo.config.DbConfig; // Assuming this is where your database connection logic is

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class responsible for fetching data and statistics for the admin
 * dashboard. This includes user counts, booking counts, active exhibition
 * counts, artifact counts, artifact distribution, artifacts in exhibitions,
 * exhibition booking counts, and user booking details.
 * 
 * @author 23048612 Viom Shrestha
 */
public class DashboardService {
	private Connection dbConn;

	/**
	 * Constructs a DashboardService, establishing a connection to the database.
	 */
	public DashboardService() {
		try {
			dbConn = DbConfig.getDbConnection();
		} catch (Exception e) {
			e.printStackTrace();
			// Consider more robust error handling, like logging or throwing a custom
			// exception.
		}
	}

	/**
	 * Retrieves the total count of users with the 'User' role directly from the
	 * database.
	 *
	 * @return The total number of users.
	 * @throws SQLException           If a database access error occurs.
	 * @throws ClassNotFoundException If the JDBC driver is not found (though this
	 *                                should be handled by DbConfig).
	 */
	public int getUserCount() throws SQLException, ClassNotFoundException {
		String sql = "SELECT COUNT(*) FROM user WHERE role = 'User'";
		try (Statement stmt = dbConn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
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
		try (Statement stmt = dbConn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
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
		try (Statement stmt = dbConn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
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
		try (Statement stmt = dbConn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
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
		try (Statement stmt = dbConn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				distribution.put(rs.getString("artifactType"), rs.getInt("count"));
			}
		}
		return distribution;
	}

	/**
	 * Retrieves a list of maps, where each map contains details of an artifact and
	 * the exhibition it belongs to.
	 *
	 * @return A list of maps containing artifact ID, name, type, exhibition ID,
	 *         title, start date, and end date.
	 * @throws SQLException           If a database access error occurs.
	 * @throws ClassNotFoundException If the JDBC driver is not found.
	 */
	public List<Map<String, Object>> getArtifactsInExhibitions() throws SQLException, ClassNotFoundException {
		List<Map<String, Object>> result = new ArrayList<>();
		String sql = "SELECT a.artifactId, a.artifactName, a.artifactType, "
				+ "e.exhibitionId, e.exhibitionTitle, e.startDate, e.endDate " + "FROM artifact a "
				+ "JOIN exhibitionartifact ea ON a.artifactId = ea.artifactId "
				+ "JOIN exhibition e ON e.exhibitionId = ea.exhibitionId " + "ORDER BY e.startDate DESC";

		try (PreparedStatement ps = dbConn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Map<String, Object> record = new HashMap<>();
				record.put("artifactId", rs.getString("artifactId"));
				record.put("artifactName", rs.getString("artifactName"));
				record.put("artifactType", rs.getString("artifactType"));
				record.put("exhibitionId", rs.getInt("exhibitionId"));
				record.put("exhibitionTitle", rs.getString("exhibitionTitle"));

				Date startDate = rs.getDate("startDate");
				record.put("startDate", startDate != null ? startDate.toLocalDate() : null);

				Date endDate = rs.getDate("endDate");
				record.put("endDate", endDate != null ? endDate.toLocalDate() : null);

				result.add(record);
			}
		}
		return result;
	}

	/**
	 * Retrieves a list of maps, where each map contains an exhibition's ID, title,
	 * and the total number of bookings for that exhibition, ordered by the number
	 * of bookings in descending order.
	 *
	 * @return A list of maps containing exhibition ID, title, and total bookings.
	 * @throws SQLException           If a database access error occurs.
	 * @throws ClassNotFoundException If the JDBC driver is not found.
	 */
	public List<Map<String, Object>> getExhibitionBookingCounts() throws SQLException, ClassNotFoundException {
		List<Map<String, Object>> result = new ArrayList<>();
		String sql = "SELECT e.exhibitionId, e.exhibitionTitle, COUNT(b.bookingId) AS totalBookings "
				+ "FROM exhibition e " + "LEFT JOIN booking b ON e.exhibitionId = b.exhibitionId "
				+ "GROUP BY e.exhibitionId, e.exhibitionTitle " + "ORDER BY totalBookings DESC";

		try (PreparedStatement ps = dbConn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Map<String, Object> record = new HashMap<>();
				record.put("exhibitionId", rs.getInt("exhibitionId"));
				record.put("exhibitionTitle", rs.getString("exhibitionTitle"));
				record.put("totalBookings", rs.getInt("totalBookings"));
				result.add(record);
			}
		}
		return result;
	}

	/**
	 * Retrieves a list of maps, where each map contains details of a user's
	 * booking, including their username, full name, the title of the booked
	 * exhibition, the booking date, and the booking time, ordered by booking date
	 * and time in descending order.
	 *
	 * @return A list of maps containing username, full name, exhibition title,
	 *         booking date, and booking time.
	 * @throws SQLException           If a database access error occurs.
	 * @throws ClassNotFoundException If the JDBC driver is not found.
	 */
	public List<Map<String, Object>> getUserBookingDetails() throws SQLException, ClassNotFoundException {
		List<Map<String, Object>> result = new ArrayList<>();
		String sql = "SELECT u.username, u.fullName, e.exhibitionTitle, b.bookingDate, b.bookingTime " + "FROM user u "
				+ "JOIN booking b ON u.username = b.username " + "JOIN exhibition e ON b.exhibitionId = e.exhibitionId "
				+ "ORDER BY b.bookingDate DESC, b.bookingTime DESC";

		try (PreparedStatement ps = dbConn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Map<String, Object> record = new HashMap<>();
				record.put("username", rs.getString("username"));
				record.put("fullName", rs.getString("fullName"));
				record.put("exhibitionTitle", rs.getString("exhibitionTitle"));
				record.put("bookingDate", rs.getDate("bookingDate").toLocalDate());
				record.put("bookingTime", rs.getTime("bookingTime").toLocalTime());
				result.add(record);
			}
		}
		return result;
	}
}