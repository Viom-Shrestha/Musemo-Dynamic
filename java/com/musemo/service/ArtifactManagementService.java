package com.musemo.service;

import com.musemo.config.DbConfig;
import com.musemo.model.ArtifactModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtifactManagementService {
	private Connection dbConn;

	public ArtifactManagementService() {
		try {
			dbConn = DbConfig.getDbConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<ArtifactModel> getAllArtifacts() {
		List<ArtifactModel> artifactList = new ArrayList<>();
		String sql = "SELECT artifactId, artifactName, artifactType, creatorName, timePeriod, origin, `condition`, description, artifactImage FROM artifact";

		try (PreparedStatement ps = dbConn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				ArtifactModel artifact = new ArtifactModel();
				artifact.setArtifactID(rs.getString("artifactId"));
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

	public List<ArtifactModel> searchArtifacts(String filter, String keyword) {
		List<ArtifactModel> artifactList = new ArrayList<>();

		String column;
		switch (filter) {
		case "artifactName":
			column = "artifactName";
			break;
		case "artifactType":
			column = "artifactType";
			break;
		case "origin":
			column = "origin";
			break;
		case "condition":
			column = "condition";
			break;
		default:
			return getAllArtifacts(); // fallback
		}

		String sql = "SELECT artifactId, artifactName, artifactType, creatorName, timePeriod, origin, `condition`, description, artifactImage FROM artifact WHERE "
				+ column + " LIKE ?";

		try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
			ps.setString(1, "%" + keyword + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ArtifactModel artifact = new ArtifactModel();
				artifact.setArtifactID(rs.getString("artifactId"));
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

	public ArtifactModel getArtifactById(String artifactId) {
		String sql = "SELECT artifactId, artifactName, artifactType, creatorName, timePeriod, origin, `condition`, description, artifactImage FROM artifact WHERE artifactId = ?";
		try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
			ps.setString(1, artifactId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				ArtifactModel artifact = new ArtifactModel();
				artifact.setArtifactID(rs.getString("artifactId"));
				artifact.setArtifactName(rs.getString("artifactName"));
				artifact.setArtifactType(rs.getString("artifactType"));
				artifact.setCreatorName(rs.getString("creatorName"));
				artifact.setTimePeriod(rs.getString("timePeriod"));
				artifact.setOrigin(rs.getString("origin"));
				artifact.setCondition(rs.getString("condition"));
				artifact.setDescription(rs.getString("description"));
				artifact.setArtifactImage(rs.getString("artifactImage"));
				return artifact;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

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
}
