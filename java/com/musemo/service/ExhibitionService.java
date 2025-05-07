package com.musemo.service;

import java.sql.*;
import java.util.*;

import com.musemo.config.DbConfig;
import com.musemo.model.ExhibitionModel;

public class ExhibitionService {
	private Connection dbConn;

	public ExhibitionService() {
		try {
			dbConn = DbConfig.getDbConnection();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public List<ExhibitionModel> getAllExhibitions() {
		List<ExhibitionModel> exhibitions = new ArrayList<>();
		String query = "SELECT * FROM exhibition"; // Adjust table name if needed

		try (Connection conn = DbConfig.getDbConnection();
				PreparedStatement ps = conn.prepareStatement(query);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				ExhibitionModel e = new ExhibitionModel();
				e.setExhibitionId(rs.getInt("exhibitionId"));
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

	public ExhibitionModel getExhibitionById(int exhibitionId) {
		String sql = "SELECT exhibitionId, name, startDate, endDate FROM exhibition WHERE exhibitionId = ?";
		try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
			ps.setInt(1, exhibitionId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				ExhibitionModel exhibition = new ExhibitionModel();
				exhibition.setExhibitionId(rs.getInt("exhibitionId"));
				exhibition.setExhibitionTitle(rs.getString("exhibitionTitle"));
				exhibition.setStartDate(rs.getDate("startDate"));
				exhibition.setEndDate(rs.getDate("endDate"));
				return exhibition;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<ExhibitionModel> searchExhibitions(String keyword) {
		List<ExhibitionModel> list = new ArrayList<>();
		String sql = "SELECT * FROM exhibition WHERE exhibitionTitle LIKE ? OR exhibitionDescription LIKE ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {

			String searchTerm = "%" + keyword + "%";
			stmt.setString(1, searchTerm);
			stmt.setString(2, searchTerm);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				ExhibitionModel e = new ExhibitionModel();
		        e.setExhibitionId(rs.getInt("exhibitionId"));
		        e.setExhibitionTitle(rs.getString("exhibitionTitle"));
		        e.setExhibitionDescription(rs.getString("exhibitionDescription"));
		        e.setExhibitionImage(rs.getString("exhibitionImage"));
				list.add(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
