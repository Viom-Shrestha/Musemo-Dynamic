package com.musemo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.musemo.model.ArtifactModel;
import com.musemo.service.ArtifactService;

/**
 * @author Viom Shrestha
 */ 
@WebServlet(asyncSupported = true, urlPatterns = { "/artifact" })
public class ArtifactController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArtifactService artifactService;

	@Override
	public void init() throws ServletException {
		artifactService = new ArtifactService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        String type = request.getParameter("type");

        List<ArtifactModel> artifacts = artifactService.searchArtifacts(keyword, type);
        request.setAttribute("artifactList", artifacts);
        request.getRequestDispatcher("WEB-INF/pages/artifact.jsp").forward(request, response);
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
