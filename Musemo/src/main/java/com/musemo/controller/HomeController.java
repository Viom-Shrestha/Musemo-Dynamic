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
 * @author Viom Shrestha
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/home"})
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HomeService homeService;

	public void init() throws ServletException {
		super.init();
		homeService = new HomeService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			int artifactCount = homeService.getArtifactCount();
			int visitorCountToday = homeService.getVisitorCountToday();
			int bookingCountToday = homeService.getBookingCountToday();
			List<ExhibitionModel> featuredExhibitions = homeService.getFeaturedExhibitions();
			List<ArtifactModel> featuredArtifacts = homeService.getFeaturedArtifacts();

			request.setAttribute("artifactCount", artifactCount);
			request.setAttribute("visitorCount", visitorCountToday);
			request.setAttribute("bookingCount", bookingCountToday);
			request.setAttribute("featuredExhibitions", featuredExhibitions);
			request.setAttribute("featuredArtifacts", featuredArtifacts);
			request.getRequestDispatcher("WEB-INF/pages/home.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			// Handle the exception appropriately, perhaps set an error attribute and
			// forward to an error page
			request.setAttribute("errorMessage", "An error occurred while fetching data for the home page.");
			request.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
