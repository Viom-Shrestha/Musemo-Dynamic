package com.musemo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.musemo.model.ArtifactModel;
import com.musemo.service.ArtifactManagementService;

/**
 * Controller for handling requests related to artifacts, such as displaying a
 * list of artifacts based on search criteria.
 *
 *  @author 23048612 Viom Shrestha
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/artifact" })
public class ArtifactController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArtifactManagementService artifactService;

	/**
	 * Initializes the servlet by creating an instance of the
	 * ArtifactManagementService. This service will be used to interact with
	 * artifact data.
	 *
	 * @throws ServletException If an exception occurs during servlet
	 *                          initialization.
	 */
	@Override
	public void init() throws ServletException {
		artifactService = new ArtifactManagementService();
	}

	/**
	 * Handles GET requests to the /artifact URL. It retrieves search keywords and
	 * artifact types from the request parameters, uses the
	 * ArtifactManagementService to search for matching artifacts, and then forwards
	 * the list of artifacts to the artifact.jsp view page for display.
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
		// Get the search keyword from the request parameter (if provided)
		String keyword = request.getParameter("keyword");
		// Get the artifact type filter from the request parameter (if provided)
		String type = request.getParameter("type");

		// Use the ArtifactManagementService to search for artifacts based on the
		// keyword and type
		List<ArtifactModel> artifacts = artifactService.searchArtifactByKeywordAndType(keyword, type);

		// Set the list of artifacts as an attribute in the request
		request.setAttribute("artifactList", artifacts);

		// Forward the request to the artifact.jsp page to display the list of artifacts
		request.getRequestDispatcher("WEB-INF/pages/artifact.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests to the /artifact URL. In this implementation, it simply
	 * calls the doGet method to handle any post requests in the same way as get
	 * requests.
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