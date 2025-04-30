package com.musemo.service;

import com.musemo.dao.DashboardDao;
import java.sql.SQLException;
import java.util.Map;

public class DashboardService {

    private final DashboardDao dashboardDao;

    public DashboardService() {
        this.dashboardDao = new DashboardDao();
    }

    public int getUserCount() throws SQLException, ClassNotFoundException {
        return dashboardDao.getUserCount();
    }

    public int getBookingCount() throws SQLException, ClassNotFoundException {
        return dashboardDao.getBookingCount();
    }

    public int getActiveExhibitionCount() throws SQLException, ClassNotFoundException {
        return dashboardDao.getActiveExhibitionCount();
    }

    public int getArtifactCount() throws SQLException, ClassNotFoundException {
        return dashboardDao.getArtifactCount();
    }

    public Map<String, Integer> getArtifactDistribution() throws SQLException, ClassNotFoundException {
        return dashboardDao.getArtifactDistribution();
    }
}