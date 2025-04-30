package com.musemo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.musemo.model.ExhibitionModel;
import com.musemo.service.ExhibitionService;

/**
 * @author Viom Shrestha
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/exhibition" })
public class ExhibitionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ExhibitionService exhibitionService;

    @Override
    public void init() throws ServletException {
        exhibitionService = new ExhibitionService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<ExhibitionModel> exhibition = exhibitionService.getAllExhibitions();

        request.setAttribute("exhibition", exhibition);
        request.getRequestDispatcher("WEB-INF/pages/exhibition.jsp").forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
