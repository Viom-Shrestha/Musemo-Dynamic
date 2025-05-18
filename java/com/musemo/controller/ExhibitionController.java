package com.musemo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.musemo.model.ExhibitionModel;
import com.musemo.service.ExhibitionManagementService;

/**
 * Controller for handling requests related to exhibitions, such as displaying a
 * list of all exhibitions or exhibitions based on a search keyword.
 *
 * @author 23048612 Viom Shrestha
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/exhibition" })
public class ExhibitionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ExhibitionManagementService exhibitionService;

	/**
	 * Initializes the servlet by creating an instance of the
	 * ExhibitionManagementService. This service will be used to fetch and search
	 * for exhibition data.
	 *
	 * @throws ServletException If an exception occurs during servlet
	 *                          initialization.
	 */
	@Override
	public void init() throws ServletException {
		exhibitionService = new ExhibitionManagementService();
	}

	/**
	 * Handles GET requests to the /exhibition URL. It retrieves an optional search
	 * keyword from the request parameters. If a keyword is provided, it uses the
	 * ExhibitionManagementService to search for exhibitions matching the keyword.
	 * Otherwise, it retrieves all exhibitions. The resulting list of
	 * ExhibitionModel objects is then set as a request attribute and forwarded to
	 * the exhibition.jsp view page for display.
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
		// Initialize a list to hold the exhibition models
		List<ExhibitionModel> exhibition;

		// Check if a keyword was provided for searching
		if (keyword != null && !keyword.trim().isEmpty()) {
			// Use the ExhibitionManagementService to search for exhibitions by the keyword
			exhibition = exhibitionService.searchExhibitions(keyword);
		} else {
			// If no keyword was provided, retrieve all exhibitions
			exhibition = exhibitionService.getAllExhibitions();
		}

		// Set the list of exhibitions as a request attribute
		request.setAttribute("exhibition", exhibition);
		// Forward the request to the exhibition.jsp page to display the list of
		// exhibitions
		request.getRequestDispatcher("WEB-INF/pages/exhibition.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests to the /exhibition URL. In this implementation, it
	 * simply calls the doGet method to handle any post requests in the same way as
	 * get requests. This is common for pages that primarily display data based on
	 * initial load or simple filtering.
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