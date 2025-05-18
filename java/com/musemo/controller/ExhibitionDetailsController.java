package com.musemo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.musemo.model.ExhibitionModel;
import com.musemo.service.ExhibitionManagementService;

/**
 * Controller for handling requests to display the details of a specific
 * exhibition.
 *
 * @author 23048612 Viom Shrestha
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/exhibitionDetails" })
public class ExhibitionDetailsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ExhibitionManagementService exhibitionDetailsService;

	/**
	 * Initializes the servlet by creating an instance of the
	 * ExhibitionManagementService. This service will be used to fetch the details
	 * of an exhibition by its ID.
	 *
	 * @throws ServletException If an exception occurs during servlet
	 *                          initialization.
	 */
	@Override
	public void init() throws ServletException {
		exhibitionDetailsService = new ExhibitionManagementService();
	}

	/**
	 * Handles GET requests to the /exhibitionDetails URL. It retrieves the
	 * exhibition ID from the request parameter, uses the
	 * ExhibitionManagementService to fetch the details of the exhibition with that
	 * ID, and then forwards the exhibition object to the exhibitionDetails.jsp view
	 * page for display. If the exhibition is not found or the ID is missing or
	 * invalid, it sends an appropriate error response.
	 *
	 * @param request  The HttpServletRequest object containing the client's
	 *                 request.
	 * @param response The HttpServletResponse object for sending the response to
	 *                 the client.
	 * @throws ServletException If a servlet-specific error occurs during
	 *                          processing.
	 * @throws IOException      If an I/O error occurs while handling the request.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Get the exhibition ID from the request parameter named "id"
		String exhibitionIdParam = request.getParameter("id");

		try {
			// Check if the exhibition ID parameter is not null and not empty
			if (exhibitionIdParam != null && !exhibitionIdParam.isEmpty()) {
				// Parse the exhibition ID string to an integer
				int exhibitionId = Integer.parseInt(exhibitionIdParam);
				// Use the ExhibitionManagementService to retrieve the exhibition details by its
				// ID
				ExhibitionModel exhibition = exhibitionDetailsService.getExhibitionById(exhibitionId);

				// Check if the exhibition was found
				if (exhibition != null) {
					// Set the exhibition object as an attribute in the request
					request.setAttribute("exhibition", exhibition);
					// Forward the request to the exhibitionDetails.jsp page to display the
					// exhibition's details
					request.getRequestDispatcher("WEB-INF/pages/exhibitionDetails.jsp").forward(request, response);
				} else {
					// If the exhibition with the given ID was not found, send a 404 Not Found error
					// response
					response.sendError(HttpServletResponse.SC_NOT_FOUND, "Exhibition not found.");
				}
			} else {
				// If the exhibition ID parameter is missing, send a 400 Bad Request error
				// response
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing exhibition ID.");
			}
		} catch (NumberFormatException e) {
			// If the exhibition ID parameter is not a valid integer, send a 400 Bad Request
			// error response
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid exhibition ID format. Must be a number.");
		}
	}

	/**
	 * Handles POST requests to the /exhibitionDetails URL. In this implementation,
	 * it simply calls the doGet method to handle any post requests in the same way
	 * as get requests. This is common for detail pages that primarily display
	 * information.
	 *
	 * @param request  The HttpServletRequest object containing the client's
	 *                 request.
	 * @param response The HttpServletResponse object for sending the response to
	 *                 the client.
	 * @throws ServletException If a servlet-specific error occurs during
	 *                          processing.
	 * @throws IOException      If an I/O error occurs while handling the request.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Delegate the handling of POST requests to the doGet method
		doGet(request, response);
	}
}