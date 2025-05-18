package com.musemo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.musemo.model.ArtifactModel;
import com.musemo.model.ExhibitionModel;
import com.musemo.service.HomeService;

/**
 * Controller for handling requests to the home page of the museum. It fetches
 * various statistics and featured content to display to the user.
 *
 * @author 23048612 Viom Shrestha
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/home" })
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HomeService homeService;

	/**
	 * Initializes the servlet by creating an instance of the HomeService.
	 *
	 * @throws ServletException If an exception occurs during servlet
	 *                          initialization.
	 */
	public void init() throws ServletException {
		super.init();
		homeService = new HomeService();
	}

	/**
	 * Handles GET requests to the /home URL. It retrieves the total count of
	 * artifacts, the number of visitors today, the number of bookings today, a list
	 * of featured exhibitions, and a list of featured artifacts using the
	 * HomeService. These values and lists are then set as request attributes and
	 * forwarded to the home.jsp view for rendering. Error handling is included to
	 * gracefully manage potential exceptions during data fetching.
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
			// Fetch the total count of artifacts from the home service
			int artifactCount = homeService.getArtifactCount();
			// Fetch the number of visitors today from the home service
			int visitorCountToday = homeService.getVisitorCount();
			// Fetch the number of bookings made today from the home service
			int bookingCountToday = homeService.getBookingCountToday();
			// Fetch a list of featured exhibitions from the home service
			List<ExhibitionModel> featuredExhibitions = homeService.getFeaturedExhibitions();
			// Fetch a list of featured artifacts from the home service
			List<ArtifactModel> featuredArtifacts = homeService.getFeaturedArtifacts();

			// Set the fetched data as attributes in the request so they can be accessed in
			// the JSP
			request.setAttribute("artifactCount", artifactCount);
			request.setAttribute("visitorCount", visitorCountToday);
			request.setAttribute("bookingCount", bookingCountToday);
			request.setAttribute("featuredExhibitions", featuredExhibitions);
			request.setAttribute("featuredArtifacts", featuredArtifacts);
			// Forward the request to the home.jsp page to render the home page view
			request.getRequestDispatcher("WEB-INF/pages/home.jsp").forward(request, response);

		} catch (Exception e) {
			// Log the exception for debugging purposes
			e.printStackTrace();
			// Handle the exception by setting an error message as a request attribute
			request.setAttribute("errorMessage", "An error occurred while fetching data for the home page.");
			// Optionally, forward to an error page instead of the login page
			request.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(request, response);
		}
	}

	/**
	 * Handles POST requests to the /home URL. In this implementation, it simply
	 * calls the doGet method to handle any post requests in the same way as get
	 * requests. This is a common pattern for pages that primarily display data
	 * fetched on initial load.
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