package com.musemo.service;

import com.musemo.config.DbConfig; // Assuming this is where your database connection logic is

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	
	// Example 1: Artifacts with Their Exhibitions
    public static List<ArtifactExhibitionView> getArtifactsInExhibitions() throws SQLException, ClassNotFoundException{
        List<ArtifactExhibitionView> result = new ArrayList<>();
        String sql = "SELECT a.artifactId, a.artifactName, a.artifactType, " +
                     "e.exhibitionId, e.exhibitionTitle, e.startDate, e.endDate " +
                     "FROM artifact a " +
                     "JOIN exhibitionartifact ea ON a.artifactId = ea.artifactId " +
                     "JOIN exhibition e ON e.exhibitionId = ea.exhibitionId " +
                     "ORDER BY e.startDate DESC";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ArtifactExhibitionView view = new ArtifactExhibitionView();
                view.setArtifactId(rs.getString("artifactId"));
                view.setArtifactName(rs.getString("artifactName"));
                view.setArtifactType(rs.getString("artifactType"));
                view.setExhibitionId(rs.getInt("exhibitionId"));
                view.setExhibitionTitle(rs.getString("exhibitionTitle"));

                Date startDate = rs.getDate("startDate");
                Date endDate = rs.getDate("endDate");
                if (startDate != null) {
                    view.setStartDate(startDate.toLocalDate());
                }
                if (endDate != null) {
                    view.setEndDate(endDate.toLocalDate());
                }
                result.add(view);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Example 2: Total Bookings per Exhibition
    public static List<ExhibitionBookingCount> getExhibitionBookingCounts() throws SQLException, ClassNotFoundException {
        List<ExhibitionBookingCount> result = new ArrayList<>();
        String sql = "SELECT e.exhibitionId, e.exhibitionTitle, COUNT(b.bookingId) AS totalBookings " +
                     "FROM exhibition e " +
                     "LEFT JOIN booking b ON e.exhibitionId = b.exhibitionId " +
                     "GROUP BY e.exhibitionId, e.exhibitionTitle " +
                     "ORDER BY totalBookings DESC";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ExhibitionBookingCount count = new ExhibitionBookingCount();
                count.setExhibitionId(rs.getInt("exhibitionId"));
                count.setExhibitionTitle(rs.getString("exhibitionTitle"));
                count.setTotalBookings(rs.getInt("totalBookings"));
                result.add(count);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Example 3: User Booking Details
    public static List<UserBookingDetails> getUserBookingDetails() throws SQLException, ClassNotFoundException{
        List<UserBookingDetails> result = new ArrayList<>();
        String sql = "SELECT u.username, u.fullName, e.exhibitionTitle, b.bookingDate, b.bookingTime " +
                     "FROM user u " +
                     "JOIN booking b ON u.username = b.username " +
                     "JOIN exhibition e ON b.exhibitionId = e.exhibitionId " +
                     "ORDER BY b.bookingDate DESC, b.bookingTime DESC";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                UserBookingDetails details = new UserBookingDetails();
                details.setUsername(rs.getString("username"));
                details.setFullName(rs.getString("fullName"));
                details.setExhibitionTitle(rs.getString("exhibitionTitle"));
                details.setBookingDate(rs.getDate("bookingDate").toLocalDate());
                details.setBookingTime(rs.getTime("bookingTime").toLocalTime());
                result.add(details);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    // DTO for Example 1: Artifact with Exhibition Details
    public static class ArtifactExhibitionView {
        private String artifactId;
        private String artifactName;
        private String artifactType;
        private int exhibitionId;
        private String exhibitionTitle;
        private LocalDate startDate;
        private LocalDate endDate;

        // Getters and Setters
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getArtifactName() {
            return artifactName;
        }
        public void setArtifactName(String artifactName) {
            this.artifactName = artifactName;
        }
        public String getArtifactType() {
            return artifactType;
        }
        public void setArtifactType(String artifactType) {
            this.artifactType = artifactType;
        }
        public int getExhibitionId() {
            return exhibitionId;
        }
        public void setExhibitionId(int exhibitionId) {
            this.exhibitionId = exhibitionId;
        }
        public String getExhibitionTitle() {
            return exhibitionTitle;
        }
        public void setExhibitionTitle(String exhibitionTitle) {
            this.exhibitionTitle = exhibitionTitle;
        }
        public LocalDate getStartDate() {
            return startDate;
        }
        public void setStartDate(LocalDate startDate) {
            this.startDate = startDate;
        }
        public LocalDate getEndDate() {
            return endDate;
        }
        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
        }
    }

    // DTO for Example 2: Exhibition Booking Counts
    public static class ExhibitionBookingCount {
        private int exhibitionId;
        private String exhibitionTitle;
        private int totalBookings;

        // Getters and Setters
        public int getExhibitionId() {
            return exhibitionId;
        }
        public void setExhibitionId(int exhibitionId) {
            this.exhibitionId = exhibitionId;
        }
        public String getExhibitionTitle() {
            return exhibitionTitle;
        }
        public void setExhibitionTitle(String exhibitionTitle) {
            this.exhibitionTitle = exhibitionTitle;
        }
        public int getTotalBookings() {
            return totalBookings;
        }
        public void setTotalBookings(int totalBookings) {
            this.totalBookings = totalBookings;
        }
    }

    // DTO for Example 3: User Booking Details
    public static class UserBookingDetails {
        private String username;
        private String fullName;
        private String exhibitionTitle;
        private LocalDate bookingDate;
        private LocalTime bookingTime;

        // Getters and Setters
        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;
        }
        public String getFullName() {
            return fullName;
        }
        public void setFullName(String fullName) {
            this.fullName = fullName;
        }
        public String getExhibitionTitle() {
            return exhibitionTitle;
        }
        public void setExhibitionTitle(String exhibitionTitle) {
            this.exhibitionTitle = exhibitionTitle;
        }
        public LocalDate getBookingDate() {
            return bookingDate;
        }
        public void setBookingDate(LocalDate bookingDate) {
            this.bookingDate = bookingDate;
        }
        public LocalTime getBookingTime() {
            return bookingTime;
        }
        public void setBookingTime(LocalTime bookingTime) {
            this.bookingTime = bookingTime;
        }
    }
}
