package com.musemo.service;

import com.musemo.config.DbConfig;
import com.musemo.model.ArtifactModel;
import com.musemo.model.ExhibitionModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ExhibitionDetailsService {

    public ExhibitionModel getExhibitionById(String id) {
        ExhibitionModel exhibition = null;
        String exhibitionQuery = "SELECT * FROM exhibition WHERE exhibitionId = ?";
        //String artifactsQuery = "SELECT * FROM artifact WHERE exhibitionId = ?";

        try (Connection conn = DbConfig.getDbConnection()) {

            // Fetch exhibition details
            try (PreparedStatement stmt = conn.prepareStatement(exhibitionQuery)) {
                stmt.setString(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    exhibition = new ExhibitionModel();
                    exhibition.setExhibitionId(rs.getString("exhibitionId"));
                    exhibition.setExhibitionTitle(rs.getString("exhibitionTitle"));
                    exhibition.setExhibitionDescription(rs.getString("exhibitionDescription"));
                    exhibition.setStartDate(rs.getDate("startDate"));
                    exhibition.setEndDate(rs.getDate("endDate"));
                    exhibition.setExhibitionImage(rs.getString("exhibitionImage"));
                }
            }
            /*
            // Fetch associated artifacts
            if (exhibition != null) {
                try (PreparedStatement stmt = conn.prepareStatement(artifactsQuery)) {
                    stmt.setString(1, id);
                    ResultSet rs = stmt.executeQuery();
                    List<ArtifactModel> artifacts = new ArrayList<>();
                    while (rs.next()) {
                        ArtifactModel artifact = new ArtifactModel();
                        artifact.setArtifactID(rs.getString("artifactId"));
                        artifact.setArtifactName(rs.getString("artifactName"));
                        artifact.setArtifactImage(rs.getString("artifactImage"));
                        artifacts.add(artifact);
                    }
                    exhibition.setArtifacts(artifacts);
                }
            }
            */

        } catch (Exception e) {
            e.printStackTrace();
        }

        return exhibition;
    }
}
