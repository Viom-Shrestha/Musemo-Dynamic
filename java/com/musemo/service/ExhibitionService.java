package com.musemo.service;

import java.sql.*;
import java.util.*;

import com.musemo.config.DbConfig;
import com.musemo.model.ExhibitionModel;

public class ExhibitionService {

    public List<ExhibitionModel> getAllExhibitions() {
        List<ExhibitionModel> exhibitions = new ArrayList<>();
        String query = "SELECT * FROM exhibition"; // Adjust table name if needed

        try (Connection conn = DbConfig.getDbConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ExhibitionModel e = new ExhibitionModel();
                e.setExhibitionId(rs.getString("exhibitionId"));
                e.setExhibitionTitle(rs.getString("exhibitionTitle"));
                e.setExhibitionDescription(rs.getString("exhibitionDescription"));
                e.setExhibitionImage(rs.getString("exhibitionImage"));
                exhibitions.add(e);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return exhibitions;
    }
}
