package com.musemo.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.musemo.config.DbConfig;
import com.musemo.model.ArtifactModel;

public class ArtifactService {

	public List<ArtifactModel> getAllArtifacts() {
		List<ArtifactModel> artifacts = new ArrayList<>();

		String sql = "SELECT * FROM artifact";

		try (Connection conn = DbConfig.getDbConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

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

		} catch (Exception e) {
			e.printStackTrace();
		}

		return artifacts;
	}

	public List<ArtifactModel> searchArtifacts(String keyword, String type) {
		List<ArtifactModel> artifactList = new ArrayList<>();

		StringBuilder sql = new StringBuilder(
				"SELECT artifactID, artifactName, artifactType, description, artifactImage FROM artifact WHERE 1=1");

		if (keyword != null && !keyword.trim().isEmpty()) {
			sql.append(" AND (artifactName LIKE ? OR description LIKE ?)");
		}

		if (type != null && !type.trim().isEmpty()) {
			sql.append(" AND artifactType = ?");
		}

		try (Connection conn = DbConfig.getDbConnection(); 
			PreparedStatement ps = conn.prepareStatement(sql.toString())) {
			int index = 1;

			if (keyword != null && !keyword.trim().isEmpty()) {
				String likeKeyword = "%" + keyword + "%";
				ps.setString(index++, likeKeyword);
				ps.setString(index++, likeKeyword);
			}

			if (type != null && !type.trim().isEmpty()) {
				ps.setString(index++, type);
			}

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ArtifactModel artifact = new ArtifactModel();
				artifact.setArtifactID(rs.getString("artifactID"));
				artifact.setArtifactName(rs.getString("artifactName"));
				artifact.setArtifactType(rs.getString("artifactType"));
				artifact.setDescription(rs.getString("description"));
				artifact.setArtifactImage(rs.getString("artifactImage"));
				artifactList.add(artifact);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return artifactList;
	}

}
