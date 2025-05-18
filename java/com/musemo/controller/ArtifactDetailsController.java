package com.musemo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.musemo.model.ArtifactModel;
import com.musemo.service.ArtifactManagementService;

/**
 * Controller for handling requests to display the details of a specific
 * artifact.
 *
 *  @author 23048612 Viom Shrestha
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/artifactDetails" })
public class ArtifactDetailsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArtifactManagementService artifactDetailsService;

	/**
	 * Initializes the servlet by creating an instance of the
	 * ArtifactManagementService. This service will be used to fetch artifact
	 * details from the data source.
	 *
	 * @throws ServletException If an exception occurs during servlet
	 *                          initialization.
	 */
	@Override
	public void init() throws ServletException {
		artifactDetailsService = new ArtifactManagementService();
	}

	/**
	 * Handles GET requests to the /artifactDetails URL. It retrieves the artifact
	 * ID from the request parameter, uses the ArtifactManagementService to fetch
	 * the details of the artifact with that ID, and then forwards the artifact
	 * object to the artifactDetails.jsp view page for display. If the artifact is
	 * not found or the ID is missing, it sends an appropriate error response.
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
		// Get the artifact ID from the request parameter named "id"
		String artifactId = request.getParameter("id");

		// Check if the artifact ID is not null and not empty
		if (artifactId != null && !artifactId.isEmpty()) {
			// Use the ArtifactManagementService to retrieve the artifact details by its ID
			ArtifactModel artifact = artifactDetailsService.getArtifactById(artifactId);
			// Check if the artifact was found
			if (artifact != null) {
				// Set the artifact object as an attribute in the request
				request.setAttribute("artifact", artifact);
				// Forward the request to the artifactDetails.jsp page to display the artifact's
				// details
				request.getRequestDispatcher("WEB-INF/pages/artifactDetails.jsp").forward(request, response);
			} else {
				// If the artifact with the given ID was not found, send a 404 Not Found error
				// response
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Artifact not found.");
			}
		} else {
			// If the artifact ID is missing in the request, send a 400 Bad Request error
			// response
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing artifact ID.");
		}
	}

	/**
	 * Handles POST requests to the /artifactDetails URL. In this implementation, it
	 * simply calls the doGet method to handle any post requests in the same way as
	 * get requests. This is common for detail pages that primarily display
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Delegate the handling of POST requests to the doGet method
		doGet(request, response);
	}
}