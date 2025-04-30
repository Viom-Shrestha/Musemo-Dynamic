package com.musemo.service;

import com.musemo.model.ArtifactModel;
import com.musemo.config.DbConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ArtifactDetailsService {

    public ArtifactModel getArtifactById(String id) {
        ArtifactModel artifact = null;
        String query = "SELECT * FROM artifact WHERE artifactId = ?";

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return artifact;
    }
}
