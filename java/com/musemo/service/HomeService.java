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
 * Service class for retrieving data for the home page. Connects to the database
 * using try-with-resources for each operation.
 * 
 *  @author 23048612 Viom Shrestha
 */
public class HomeService {
	private Connection dbConn;

	public HomeService() {
		try {
			dbConn = DbConfig.getDbConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retrieves the total count of artifacts.
	 * 
	 * @return the total number of artifacts.
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver is not found (potentially
	 *                                from DbConfig).
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
	 * Retrieves the count of visitors for the current date.
	 * 
	 * @return the number of visitors today.
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver is not found (potentially
	 *                                from DbConfig).
	 */
	public int getVisitorCount() throws SQLException, ClassNotFoundException {
		String sql = "SELECT COUNT(*) FROM user WHERE role='User' ";
		try (Statement stmt = dbConn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
			if (rs.next()) {
				return rs.getInt(1);
			}
		}
		return 0;
	}

	/**
	 * Retrieves the count of bookings made for the current date.
	 * 
	 * @return the number of bookings today.
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver is not found (potentially
	 *                                from DbConfig).
	 */
	public int getBookingCountToday() throws SQLException, ClassNotFoundException {
		String sql = "SELECT COUNT(*) FROM booking WHERE bookingDate = CURRENT_DATE";
		try (Statement stmt = dbConn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
			if (rs.next()) {
				return rs.getInt(1);
			}
		}
		return 0;
	}

	/**
	 * Retrieves a list of featured exhibitions.
	 * 
	 * @return a list of featured ExhibitionModel objects.
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver is not found (potentially
	 *                                from DbConfig).
	 */
	public List<ExhibitionModel> getFeaturedExhibitions() throws SQLException, ClassNotFoundException {
		List<ExhibitionModel> exhibitions = new ArrayList<>();
		String sql = "SELECT exhibitionTitle, exhibitionDescription, exhibitionImage, startDate, endDate "
				+ "FROM exhibition ORDER BY startDate DESC LIMIT 3";

		try (PreparedStatement pstmt = dbConn.prepareStatement(sql)) {

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
	 * 
	 * @return a list of featured ArtifactModel objects.
	 * @throws SQLException           if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver is not found (potentially
	 *                                from DbConfig).
	 */
	public List<ArtifactModel> getFeaturedArtifacts() throws SQLException, ClassNotFoundException {
		List<ArtifactModel> artifacts = new ArrayList<>();
		String sql = "SELECT artifactName, timePeriod, description, artifactImage "
				+ "FROM artifact ORDER BY artifactId DESC LIMIT 3";

		try (PreparedStatement pstmt = dbConn.prepareStatement(sql)) {

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					ArtifactModel art = new ArtifactModel();
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