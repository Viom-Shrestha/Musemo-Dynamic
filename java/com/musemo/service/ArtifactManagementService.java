package com.musemo.service;

import com.musemo.config.DbConfig;
import com.musemo.model.ArtifactModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class responsible for managing artifact data in the database. This
 * class provides methods for retrieving, searching, adding, updating, and
 * deleting artifact records.
 * 
 * @author 23048612 Viom Shrestha
 */
public class ArtifactManagementService {
	private Connection dbConn;

	/**
	 * Constructs an ArtifactManagementService, establishing a connection to the
	 * database.
	 */
	public ArtifactManagementService() {
		try {
			dbConn = DbConfig.getDbConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Internal helper method to search artifacts based on a keyword, filter, and
	 * type.
	 *
	 * @param keyword The keyword to search for.
	 * @param filter  The specific field to filter by (e.g., artifactName, origin).
	 * @param type    The artifact type to search for.
	 * @return A list of ArtifactModel objects that match the search criteria.
	 */
	private List<ArtifactModel> searchArtifactsInternal(String keyword, String filter, String type) {
		List<ArtifactModel> artifactList = new ArrayList<>();
		String sql = "SELECT artifactID, artifactName, artifactType, creatorName, timePeriod, origin, "
				+ "`condition`, description, artifactImage FROM artifact WHERE 1=1";
		List<String> parameters = new ArrayList<>();

		// Handle filter-based search
		if (filter != null && !filter.trim().isEmpty()) {
			String column;
			switch (filter) {
			case "artifactName":
			case "artifactType":
			case "origin":
			case "condition":
				column = filter;
				break;
			default:
				return getAllArtifacts(); // Fallback for invalid filters
			}
			sql += " AND " + column + " LIKE ?";
			parameters.add("%" + keyword + "%");
		}
		// Handle general keyword and type search (if no filter)
		else {
			if (keyword != null && !keyword.trim().isEmpty()) {
				sql += " AND (artifactName LIKE ? OR description LIKE ?)";
				parameters.add("%" + keyword + "%");
				parameters.add("%" + keyword + "%");
			}
			if (type != null && !type.trim().isEmpty()) {
				sql += " AND artifactType = ?";
				parameters.add(type);
			}
		}

		try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
			// Set parameters for the prepared statement
			for (int i = 0; i < parameters.size(); i++) {
				ps.setString(i + 1, parameters.get(i));
			}
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ArtifactModel artifact = new ArtifactModel();
				artifact.setArtifactID(rs.getString("artifactID"));
				artifact.setArtifactName(rs.getString("artifactName"));
				artifact.setArtifactType(rs.getString("artifactType"));
				artifact.setCreatorName(rs.getString("creatorName"));
				artifact.setTimePeriod(rs.getString("timePeriod"));
				artifact.setOrigin(rs.getString("origin"));
				artifact.setCondition(rs.getString("condition"));
				artifact.setDescription(rs.getString("description"));
				artifact.setArtifactImage(rs.getString("artifactImage"));
				artifactList.add(artifact);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return artifactList;
	}

	/**
	 * Searches for artifacts by a keyword and artifact type.
	 *
	 * @param keyword The keyword to search for in the artifact name or description.
	 * @param type    The specific artifact type to filter by.
	 * @return A list of ArtifactModel objects matching the keyword and type.
	 */
	public List<ArtifactModel> searchArtifactByKeywordAndType(String keyword, String type) {
		return searchArtifactsInternal(keyword, null, type);
	}

	/**
	 * Searches for artifacts based on a specific filter (field) and keyword.
	 *
	 * @param filter  The field to filter by (e.g., "artifactName", "origin").
	 * @param keyword The keyword to search for in the specified field.
	 * @return A list of ArtifactModel objects matching the filter and keyword.
	 */
	public List<ArtifactModel> searchArtifactByFilter(String filter, String keyword) {
		return searchArtifactsInternal(keyword, filter, null);
	}

	/**
	 * Retrieves all artifacts from the database.
	 *
	 * @return A list of all ArtifactModel objects in the database. Returns an empty
	 *         list if no artifacts are found or if a database error occurs.
	 */
	public List<ArtifactModel> getAllArtifacts() {
		List<ArtifactModel> artifacts = new ArrayList<>();
		String sql = "SELECT * FROM artifact";

		try (PreparedStatement stmt = dbConn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				ArtifactModel artifact = new ArtifactModel();
				artifact.setArtifactID(rs.getString("artifactID"));
				artifact.setArtifactName(rs.getString("artifactName"));
				artifact.setArtifactType(rs.getString("artifactType"));
				artifact.setCreatorName(rs.getString("creatorName"));
				artifact.setTimePeriod(rs.getString("timePeriod"));
				artifact.setOrigin(rs.getString("origin"));
				artifact.setCondition(rs.getString("condition"));
				artifact.setDescription(rs.getString("description"));
				artifact.setArtifactImage(rs.getString("artifactImage"));
				artifacts.add(artifact);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return artifacts;
	}

	/**
	 * Retrieves an artifact from the database based on its ID.
	 *
	 * @param id The ID of the artifact to retrieve.
	 * @return An ArtifactModel object if found, null otherwise.
	 */
	public ArtifactModel getArtifactById(String id) {
		ArtifactModel artifact = null;
		String query = "SELECT * FROM artifact WHERE artifactId = ?";

		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setString(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				artifact = new ArtifactModel();
				artifact.setArtifactID(rs.getString("artifactId"));
				artifact.setArtifactName(rs.getString("artifactName"));
				artifact.setArtifactType(rs.getString("artifactType"));
				artifact.setDescription(rs.getString("description"));
				artifact.setTimePeriod(rs.getString("timePeriod"));
				artifact.setOrigin(rs.getString("origin"));
				artifact.setCreatorName(rs.getString("creatorName"));
				artifact.setCondition(rs.getString("condition"));
				artifact.setArtifactImage(rs.getString("artifactImage"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return artifact;
	}

	/**
	 * Updates an existing artifact in the database with the provided information.
	 *
	 * @param artifact The ArtifactModel object containing the updated artifact
	 *                 data.
	 * @return True if the update was successful, false otherwise.
	 */
	public boolean updateArtifact(ArtifactModel artifact) {
		String sql = "UPDATE artifact SET artifactName = ?, artifactType = ?, creatorName = ?, timePeriod = ?, origin = ?, `condition` = ?, description = ?, artifactImage = ? WHERE artifactId = ?";
		try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
			ps.setString(1, artifact.getArtifactName());
			ps.setString(2, artifact.getArtifactType());
			ps.setString(3, artifact.getCreatorName());
			ps.setString(4, artifact.getTimePeriod());
			ps.setString(5, artifact.getOrigin());
			ps.setString(6, artifact.getCondition());
			ps.setString(7, artifact.getDescription());
			ps.setString(8, artifact.getArtifactImage());
			ps.setString(9, artifact.getArtifactID());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Adds a new artifact to the database.
	 *
	 * @param artifact The ArtifactModel object containing the data for the new
	 *                 artifact.
	 */
	public void addArtifact(ArtifactModel artifact) {
		String sql = "INSERT INTO artifact (artifactId, artifactName, artifactType, creatorName, timePeriod, origin, `condition`, description, artifactImage) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
			ps.setString(1, artifact.getArtifactID());
			ps.setString(2, artifact.getArtifactName());
			ps.setString(3, artifact.getArtifactType());
			ps.setString(4, artifact.getCreatorName());
			ps.setString(5, artifact.getTimePeriod());
			ps.setString(6, artifact.getOrigin());
			ps.setString(7, artifact.getCondition());
			ps.setString(8, artifact.getDescription());
			ps.setString(9, artifact.getArtifactImage());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deletes an artifact from the database based on its ID.
	 *
	 * @param artifactId The ID of the artifact to delete.
	 * @return True if the deletion was successful, false otherwise.
	 */
	public boolean deleteArtifact(String artifactId) {
		String sql = "DELETE FROM artifact WHERE artifactId = ?";
		try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
			ps.setString(1, artifactId);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}