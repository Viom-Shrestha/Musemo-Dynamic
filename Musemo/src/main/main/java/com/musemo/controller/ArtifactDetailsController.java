package com.musemo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.musemo.model.ArtifactModel;
import com.musemo.service.ArtifactDetailsService;

/**
 * @author Viom Shrestha
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/artifactDetails" })
public class ArtifactDetailsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArtifactDetailsService artifactDetailsService;

	@Override
	public void init() throws ServletException {
		artifactDetailsService = new ArtifactDetailsService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String artifactId = request.getParameter("id");

		if (artifactId != null && !artifactId.isEmpty()) {
			ArtifactModel artifact = artifactDetailsService.getArtifactById(artifactId);
			if (artifact != null) {
				request.setAttribute("artifact", artifact);
				request.getRequestDispatcher("WEB-INF/pages/artifactDetails.jsp").forward(request, response);
			} else {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Artifact not found.");
			}
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing artifact ID.");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
