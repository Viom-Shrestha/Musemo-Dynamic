package com.musemo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import com.musemo.service.DashboardService;
import com.musemo.service.DashboardService.ArtifactExhibitionView;
import com.musemo.service.DashboardService.ExhibitionBookingCount;
import com.musemo.service.DashboardService.UserBookingDetails;

import java.util.List;
import java.util.Map;

/**
 * @author Viom Shrestha
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/dashboard"})
public class DashboardController extends HttpServlet {
    private final DashboardService dashboardService = new DashboardService();
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        try {
            int userCount = dashboardService.getUserCount();
            int bookingCount = dashboardService.getBookingCount();
            int activeExhibitionCount = dashboardService.getActiveExhibitionCount();
            int artifactCount = dashboardService.getArtifactCount();
            Map<String, Integer> artifactDistribution = dashboardService.getArtifactDistribution();
            List<UserBookingDetails> userBookingDetails = DashboardService.getUserBookingDetails();
            List<ArtifactExhibitionView> exhibitionArtifactDetails = DashboardService.getArtifactsInExhibitions();
            List<ExhibitionBookingCount> mostBookedExhibitions = DashboardService.getExhibitionBookingCounts();
            
            request.setAttribute("userCount", userCount);
            request.setAttribute("bookingCount", bookingCount);
            request.setAttribute("activeExhibitionCount", activeExhibitionCount);
            request.setAttribute("artifactCount", artifactCount);
            request.setAttribute("artifactDistribution", artifactDistribution);
            request.setAttribute("userBookingDetails", userBookingDetails);
            request.setAttribute("exhibitionArtifactDetails", exhibitionArtifactDetails);
            request.setAttribute("mostBookedExhibitions", mostBookedExhibitions);
            request.getRequestDispatcher("/WEB-INF/pages/dashboard.jsp").forward(request, response);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // Handle errors appropriately
        }
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
