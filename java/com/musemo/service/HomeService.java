package com.musemo.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.musemo.config.DbConfig; // Assuming DbConfig provides connections (ideally pooled)
import com.musemo.model.ArtifactModel;
import com.musemo.model.ExhibitionModel;

/**
 * Service class for retrieving data for the home page.
 * Connects to the database using try-with-resources for each operation.
 */
public class HomeService {

	// NO Connection field here - connections managed per-method

	/**
	 * Constructor - No database connection initialization needed here anymore.
	 * If DbConfig needs initialization, it should handle that itself.
	 */
	public HomeService() {
		// Initialization logic if needed, but not for a shared connection.
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
	 * Retrieves the count of visitors for the current date.
	 * @return the number of visitors today.
	 * @throws SQLException if a database access error occurs.
     * @throws ClassNotFoundException if the JDBC driver is not found (potentially from DbConfig).
	 */
	public int getVisitorCountToday() throws SQLException, ClassNotFoundException {
		String sql = "SELECT COUNT(*) FROM user WHERE role='User' ";
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
	 * Retrieves the count of bookings made for the current date.
	 * @return the number of bookings today.
	 * @throws SQLException if a database access error occurs.
     * @throws ClassNotFoundException if the JDBC driver is not found (potentially from DbConfig).
	 */
	public int getBookingCountToday() throws SQLException, ClassNotFoundException {
		String sql = "SELECT COUNT(*) FROM booking WHERE bookingDate = CURRENT_DATE";
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
	 * Retrieves a list of featured exhibitions.
	 * @return a list of featured ExhibitionModel objects.
	 * @throws SQLException if a database access error occurs.
     * @throws ClassNotFoundException if the JDBC driver is not found (potentially from DbConfig).
	 */
	public List<ExhibitionModel> getFeaturedExhibitions() throws SQLException, ClassNotFoundException {
		List<ExhibitionModel> exhibitions = new ArrayList<>();
		String sql = "SELECT exhibitionTitle, exhibitionDescription, exhibitionImage, startDate, endDate "
                   + "FROM exhibition ORDER BY startDate DESC LIMIT 3";

		try (Connection conn = DbConfig.getDbConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {


			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					ExhibitionModel ex = new ExhibitionModel();
					ex.setExhibitionTitle(rs.getString("exhibitionTitle"));
					ex.setExhibitionDescription(rs.getString("exhibitionDescription"));
					ex.setExhibitionImage(rs.getString("exhibitionImage"));
					ex.setStartDate(rs.getDate("startDate"));
					ex.setEndDate(rs.getDate("endDate"));
					exhibitions.add(ex);
				}
			}
		}
		return exhibitions;
	}

	/**
	 * Retrieves a list of featured artifacts.
	 * @return a list of featured ArtifactModel objects.
	 * @throws SQLException if a database access error occurs.
     * @throws ClassNotFoundException if the JDBC driver is not found (potentially from DbConfig).
	 */
	public List<ArtifactModel> getFeaturedArtifacts() throws SQLException, ClassNotFoundException {
		List<ArtifactModel> artifacts = new ArrayList<>();
        // Selecting the 'id' column used in ORDER BY might be good practice, though not always strictly required.
		String sql = "SELECT artifactName, timePeriod, description, artifactImage "
                   + "FROM artifact ORDER BY artifactId DESC LIMIT 3";

		try (Connection conn = DbConfig.getDbConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					ArtifactModel art = new ArtifactModel();
                    // Assuming ArtifactModel has setters for all these, including ID if needed.
					art.setArtifactName(rs.getString("artifactName"));
					art.setTimePeriod(rs.getString("timePeriod"));
					art.setDescription(rs.getString("description"));
					art.setArtifactImage(rs.getString("artifactImage"));
					artifacts.add(art);
				}
			}
		}
		return artifacts;
	}
}