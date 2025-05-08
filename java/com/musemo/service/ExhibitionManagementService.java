package com.musemo.service;

import com.musemo.config.DbConfig;
import com.musemo.model.ExhibitionArtifactModel;
import com.musemo.model.ExhibitionModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExhibitionManagementService {
	private Connection dbConn;

	public ExhibitionManagementService() {
		try {
			dbConn = DbConfig.getDbConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<ExhibitionModel> getAllExhibitions() {
		List<ExhibitionModel> exhibitionList = new ArrayList<>();
		String sql = "SELECT exhibitionId, exhibitionTitle, exhibitionDescription, startDate, endDate, exhibitionImage FROM exhibition";

		try (PreparedStatement ps = dbConn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
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

	public ExhibitionModel getExhibitionById(int exhibitionId) {
		String sql = "SELECT exhibitionId, exhibitionTitle, exhibitionDescription, startDate, endDate, exhibitionImage FROM exhibition WHERE exhibitionId = ?";
		try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
			ps.setInt(1, exhibitionId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				ExhibitionModel exhibition = new ExhibitionModel();
				exhibition.setExhibitionId(rs.getInt("exhibitionId"));
				exhibition.setExhibitionTitle(rs.getString("exhibitionTitle"));
				exhibition.setExhibitionDescription(rs.getString("exhibitionDescription"));
				exhibition.setStartDate(rs.getDate("startDate"));
				exhibition.setEndDate(rs.getDate("endDate"));
				exhibition.setExhibitionImage(rs.getString("exhibitionImage"));
				return exhibition;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

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

	public boolean deleteExhibition(int exhibitionId) {
		String sql = "DELETE FROM exhibition WHERE exhibitionId = ?";
		try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
			ps.setInt(1, exhibitionId);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<ExhibitionModel> searchExhibitions(String filter, String keyword) {
		List<ExhibitionModel> exhibitionList = new ArrayList<>();

		String column;
		switch (filter) {
		case "exhibitionTitle":
			column = "exhibitionTitle";
			break;
		case "exhibitionDescription":
			column = "exhibitionDescription";
			break;
		default:
			return getAllExhibitions(); // fallback
		}

		String sql = "SELECT exhibitionId, exhibitionTitle, exhibitionDescription, startDate, endDate, exhibitionImage "
				+ "FROM exhibition WHERE " + column + " LIKE ?";

		try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
			ps.setString(1, "%" + keyword + "%");
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

	public List<ExhibitionArtifactModel> getAllExhibitionArtifactRelations() {
		List<ExhibitionArtifactModel> list = new ArrayList<>();
		String sql = "SELECT ea.exhibitionId, ea.artifactId, e.exhibitionTitle, a.artifactName "+
				    "FROM exhibitionartifact ea "+
				    "JOIN exhibition e ON ea.exhibitionId = e.exhibitionId "+
				    "JOIN artifact a ON ea.artifactId = a.artifactId";

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
