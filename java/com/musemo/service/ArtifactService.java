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
}
