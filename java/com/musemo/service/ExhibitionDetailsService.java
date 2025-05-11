package com.musemo.service;

import com.musemo.config.DbConfig;
import com.musemo.model.ExhibitionModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
                    exhibition.setExhibitionId(rs.getInt("exhibitionId"));
                    exhibition.setExhibitionTitle(rs.getString("exhibitionTitle"));
                    exhibition.setExhibitionDescription(rs.getString("exhibitionDescription"));
                    exhibition.setStartDate(rs.getDate("startDate"));
                    exhibition.setEndDate(rs.getDate("endDate"));
                    exhibition.setExhibitionImage(rs.getString("exhibitionImage"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return exhibition;
    }
}
