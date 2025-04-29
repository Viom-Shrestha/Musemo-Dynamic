package com.musemo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.musemo.model.ExhibitionModel;
import com.musemo.service.ExhibitionDetailsService;

/**
 * @author Viom Shrestha
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/exhibitionDetails" })
public class ExhibitionDetailsController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ExhibitionDetailsService exhibitionDetailsService;

    @Override
    public void init() throws ServletException {
        exhibitionDetailsService = new ExhibitionDetailsService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String exhibitionId = request.getParameter("id");

        if (exhibitionId != null && !exhibitionId.isEmpty()) {
            ExhibitionModel exhibition = exhibitionDetailsService.getExhibitionById(exhibitionId);
            if (exhibition != null) {
                request.setAttribute("exhibition", exhibition);
                request.getRequestDispatcher("WEB-INF/pages/exhibitionDetails.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Exhibition not found.");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing exhibition ID.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
