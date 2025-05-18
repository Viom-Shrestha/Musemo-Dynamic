package com.musemo.service;

import com.musemo.config.DbConfig;
import com.musemo.model.ExhibitionArtifactModel;
import com.musemo.model.ExhibitionModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class responsible for managing exhibition data, including retrieving,
 * adding, updating, deleting, and searching exhibitions, as well as managing
 * the relationship between exhibitions and artifacts.
 * 
 *  @author 23048612 Viom Shrestha
 */
public class ExhibitionManagementService {
	private Connection dbConn;

	/**
	 * Constructs an ExhibitionManagementService, establishing a connection to the
	 * database.
	 */
	public ExhibitionManagementService() {
		try {
			dbConn = DbConfig.getDbConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retrieves all exhibitions from the database.
	 *
	 * @return A list of all ExhibitionModel objects.
	 */
	public List<ExhibitionModel> getAllExhibitions() {
		List<ExhibitionModel> exhibitions = new ArrayList<>();
		String query = "SELECT * FROM exhibition"; // Adjust table name if needed

		try (PreparedStatement ps = dbConn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				ExhibitionModel e = new ExhibitionModel();
				e.setExhibitionId(rs.getInt("exhibitionId"));
				e.setExhibitionTitle(rs.getString("exhibitionTitle"));
				e.setExhibitionDescription(rs.getString("exhibitionDescription"));
				e.setExhibitionImage(rs.getString("exhibitionImage"));
				e.setStartDate(rs.getDate("startDate"));
				e.setEndDate(rs.getDate("endDate"));
				exhibitions.add(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exhibitions;
	}

	/**
	 * Retrieves an exhibition from the database based on its ID.
	 *
	 * @param i The ID of the exhibition to retrieve.
	 * @return An ExhibitionModel object if found, null otherwise.
	 */
	public ExhibitionModel getExhibitionById(int i) {
		ExhibitionModel exhibition = null;
		String exhibitionQuery = "SELECT * FROM exhibition WHERE exhibitionId = ?";

		try (PreparedStatement stmt = dbConn.prepareStatement(exhibitionQuery)) {
			stmt.setInt(1, i);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				exhibition = new ExhibitionModel();
				exhibition.setExhibitionId(rs.getInt("exhibitionId"));
				exhibition.setExhibitionTitle(rs.getString("exhibitionTitle"));
				exhibition.setExhibitionDescription(rs.getString("exhibitionDescription"));
				exhibition.setStartDate(rs.getDate("startDate"));
				exhibition.setEndDate(rs.getDate("endDate"));
				exhibition.setExhibitionImage(rs.getString("exhibitionImage"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exhibition;
	}

	/**
	 * Adds a new exhibition to the database.
	 *
	 * @param exhibition The ExhibitionModel object containing the exhibition
	 *                   details.
	 */
	public void addExhibition(ExhibitionModel exhibition) {
		String sql = "INSERT INTO exhibition (exhibitionId, exhibitionTitle, exhibitionDescription, startDate, endDate, exhibitionImage) VALUES (?, ?, ?, ?, ?, ?)";

		try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
			ps.setInt(1, exhibition.getExhibitionId());
			ps.setString(2, exhibition.getExhibitionTitle());
			ps.setString(3, exhibition.getExhibitionDescription());
			ps.setDate(4, exhibition.getStartDate());
			ps.setDate(5, exhibition.getEndDate());
			ps.setString(6, exhibition.getExhibitionImage());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates an existing exhibition in the database.
	 *
	 * @param exhibition The ExhibitionModel object containing the updated details.
	 * @return True if the update was successful, false otherwise.
	 */
	public boolean updateExhibition(ExhibitionModel exhibition) {
		String sql = "UPDATE exhibition SET exhibitionTitle = ?, exhibitionDescription = ?, startDate = ?, endDate = ?, exhibitionImage = ? WHERE exhibitionId = ?";
		try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
			ps.setString(1, exhibition.getExhibitionTitle());
			ps.setString(2, exhibition.getExhibitionDescription());
			ps.setDate(3, exhibition.getStartDate());
			ps.setDate(4, exhibition.getEndDate());
			ps.setString(5, exhibition.getExhibitionImage());
			ps.setInt(6, exhibition.getExhibitionId());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Deletes an exhibition from the database based on its ID.
	 *
	 * @param exhibitionId The ID of the exhibition to delete.
	 * @return True if the deletion was successful, false otherwise.
	 */
	public boolean deleteExhibition(int exhibitionId) {
		String sql = "DELETE FROM exhibition WHERE exhibitionId = ?";
		try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
			ps.setInt(1, exhibitionId);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
			// Consider logging the error.
		}
	}

	/**
	 * Searches for exhibitions based on a keyword in either the title or
	 * description.
	 *
	 * @param keyword The keyword to search for.
	 * @return A list of ExhibitionModel objects matching the keyword.
	 */
	public List<ExhibitionModel> searchExhibitions(String keyword) {
		return searchExhibitions(null, keyword);
	}

	/**
	 * Searches for exhibitions based on a filter (title or description) and a
	 * keyword.
	 *
	 * @param filter  The field to filter by ("exhibitionTitle" or
	 *                "exhibitionDescription").
	 * @param keyword The keyword to search for.
	 * @return A list of ExhibitionModel objects matching the filter and keyword.
	 */
	public List<ExhibitionModel> searchExhibitions(String filter, String keyword) {
		List<ExhibitionModel> exhibitionList = new ArrayList<>();
		String sql;

		if (filter == null || filter.isEmpty()) {
			sql = "SELECT exhibitionId, exhibitionTitle, exhibitionDescription, startDate, endDate, exhibitionImage "
					+ "FROM exhibition WHERE exhibitionTitle LIKE ? OR exhibitionDescription LIKE ?";
		} else {
			String column;
			switch (filter) {
			case "exhibitionTitle":
				column = "exhibitionTitle";
				break;
			case "exhibitionDescription":
				column = "exhibitionDescription";
				break;
			default:
				return getAllExhibitions(); // Fallback for invalid filters
			}
			sql = "SELECT exhibitionId, exhibitionTitle, exhibitionDescription, startDate, endDate, exhibitionImage "
					+ "FROM exhibition WHERE " + column + " LIKE ?";
		}

		try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
			String searchTerm = "%" + keyword + "%";
			if (filter == null || filter.isEmpty()) {
				ps.setString(1, searchTerm);
				ps.setString(2, searchTerm);
			} else {
				ps.setString(1, searchTerm);
			}
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ExhibitionModel exhibition = new ExhibitionModel();
				exhibition.setExhibitionId(rs.getInt("exhibitionId"));
				exhibition.setExhibitionTitle(rs.getString("exhibitionTitle"));
				exhibition.setExhibitionDescription(rs.getString("exhibitionDescription"));
				exhibition.setStartDate(rs.getDate("startDate"));
				exhibition.setEndDate(rs.getDate("endDate"));
				exhibition.setExhibitionImage(rs.getString("exhibitionImage"));
				exhibitionList.add(exhibition);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exhibitionList;
	}

	/**
	 * Adds an artifact to an exhibition by creating a new entry in the
	 * exhibitionartifact table.
	 *
	 * @param exhibitionId The ID of the exhibition.
	 * @param artifactId   The ID of the artifact.
	 * @return True if the artifact was successfully added to the exhibition, false
	 *         otherwise.
	 */
	public boolean addArtifactToExhibition(int exhibitionId, String artifactId) {
		String sql = "INSERT INTO exhibitionartifact (exhibitionId, artifactId) VALUES (?, ?)";
		try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
			stmt.setInt(1, exhibitionId);
			stmt.setString(2, artifactId);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Removes an artifact from an exhibition by deleting the corresponding entry in
	 * the exhibitionartifact table.
	 *
	 * @param exhibitionId The ID of the exhibition.
	 * @param artifactId   The ID of the artifact to remove.
	 * @return True if the artifact was successfully removed from the exhibition,
	 *         false otherwise.
	 */
	public boolean removeArtifactFromExhibition(int exhibitionId, String artifactId) {
		String sql = "DELETE FROM exhibitionartifact WHERE exhibitionId = ? AND artifactId = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
			stmt.setInt(1, exhibitionId);
			stmt.setString(2, artifactId);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Checks if a relationship between an exhibition and an artifact already exists
	 * in the exhibitionartifact table.
	 *
	 * @param exhibitionId The ID of the exhibition.
	 * @param artifactId   The ID of the artifact.
	 * @return True if the relationship exists, false otherwise.
	 */
	public boolean relationExists(int exhibitionId, String artifactId) {
		String sql = "SELECT COUNT(*) FROM exhibitionartifact WHERE exhibitionId = ? AND artifactId = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
			stmt.setInt(1, exhibitionId);
			stmt.setString(2, artifactId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Retrieves all relationships between exhibitions and artifacts, along with the
	 * title of the exhibition and the name of the artifact.
	 *
	 * @return A list of ExhibitionArtifactModel objects representing the
	 *         relationships.
	 */
	public List<ExhibitionArtifactModel> getAllExhibitionArtifactRelations() {
		List<ExhibitionArtifactModel> list = new ArrayList<>();
		String sql = "SELECT ea.exhibitionId, ea.artifactId, e.exhibitionTitle, a.artifactName "
				+ "FROM exhibitionartifact ea " + "JOIN exhibition e ON ea.exhibitionId = e.exhibitionId "
				+ "JOIN artifact a ON ea.artifactId = a.artifactId";
		try (PreparedStatement stmt = dbConn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				ExhibitionArtifactModel rel = new ExhibitionArtifactModel();
				rel.setExhibitionId(rs.getInt("exhibitionId"));
				rel.setArtifactId(rs.getString("artifactID"));
				rel.setExhibitionTitle(rs.getString("exhibitionTitle"));
				rel.setArtifactName(rs.getString("artifactName"));
				list.add(rel);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}