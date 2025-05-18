package com.musemo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import com.musemo.service.DashboardService;

import java.util.List;
import java.util.Map;

/**
 * Controller for handling the display of the admin dashboard. It fetches
 * various statistics and data points from the DashboardService and makes them
 * available to the dashboard view page.
 *
 * @author 23048612 Viom Shrestha
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/dashboard" })
public class DashboardController extends HttpServlet {
	private final DashboardService dashboardService = new DashboardService();
	private static final long serialVersionUID = 1L;

	/**
	 * Handles GET requests to the /dashboard URL. It retrieves counts for users,
	 * bookings, active exhibitions, and artifacts, as well as detailed data like
	 * artifact distribution by type, user booking details, artifacts in
	 * exhibitions, and the most booked exhibitions. This data is then set as
	 * request attributes and forwarded to the dashboard.jsp view for rendering.
	 * Error handling is included for database-related exceptions.
	 *
	 * @param request  The HttpServletRequest object containing the client's
	 *                 request.
	 * @param response The HttpServletResponse object for sending the response to
	 *                 the client.
	 * @throws ServletException If a servlet-specific error occurs during
	 *                          processing.
	 * @throws IOException      If an I/O error occurs while handling the request.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			// Fetch the total number of users from the dashboard service
			int userCount = dashboardService.getUserCount();
			// Fetch the total number of bookings from the dashboard service
			int bookingCount = dashboardService.getBookingCount();
			// Fetch the count of currently active exhibitions
			int activeExhibitionCount = dashboardService.getActiveExhibitionCount();
			// Fetch the total number of artifacts
			int artifactCount = dashboardService.getArtifactCount();
			// Fetch the distribution of artifacts by their type
			Map<String, Integer> artifactDistribution = dashboardService.getArtifactDistribution();

			// Fetch a list of maps containing details of users and their bookings
			List<Map<String, Object>> userBookingDetails = dashboardService.getUserBookingDetails();
			// Fetch a list of maps detailing which artifacts are part of which exhibitions
			List<Map<String, Object>> exhibitionArtifactDetails = dashboardService.getArtifactsInExhibitions();
			// Fetch a list of maps of exhibitions with the highest number of bookings
			List<Map<String, Object>> mostBookedExhibitions = dashboardService.getExhibitionBookingCounts();

			// Set the fetched data as attributes in the request
			request.setAttribute("userCount", userCount);
			request.setAttribute("bookingCount", bookingCount);
			request.setAttribute("activeExhibitionCount", activeExhibitionCount);
			request.setAttribute("artifactCount", artifactCount);
			request.setAttribute("artifactDistribution", artifactDistribution);
			request.setAttribute("userBookingDetails", userBookingDetails);
			request.setAttribute("exhibitionArtifactDetails", exhibitionArtifactDetails);
			request.setAttribute("mostBookedExhibitions", mostBookedExhibitions);

			// Forward the request to the dashboard.jsp page to render the dashboard view
			request.getRequestDispatcher("/WEB-INF/pages/dashboard.jsp").forward(request, response);

		} catch (SQLException | ClassNotFoundException e) {
			request.setAttribute("error", "Error loading dashboard data: " + e.getMessage());
			request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
		}
	}

	/**
	 * Handles POST requests to the /dashboard URL. In this implementation, it
	 * simply calls the doGet method to handle any post requests in the same way as
	 * get requests. This is common for pages that primarily display data fetched on
	 * initial load.
	 *
	 * @param request  The HttpServletRequest object containing the client's
	 *                 request.
	 * @param response The HttpServletResponse object for sending the response to
	 *                 the client.
	 * @throws ServletException If a servlet-specific error occurs during
	 *                          processing.
	 * @throws IOException      If an I/O error occurs while handling the request.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Delegate the handling of POST requests to the doGet method
		doGet(request, response);
	}
}