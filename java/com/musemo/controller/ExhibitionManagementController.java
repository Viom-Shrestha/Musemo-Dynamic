package com.musemo.controller;

import com.musemo.model.ExhibitionModel;
import com.musemo.service.ExhibitionManagementService;
import com.musemo.util.ImageUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet(asyncSupported = true, urlPatterns = { "/exhibitionManagement" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class ExhibitionManagementController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ExhibitionManagementService service;

	public void init() {
		service = new ExhibitionManagementService();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String keyword = req.getParameter("keyword");
		String searchBy = req.getParameter("searchBy");
		String deleteId = req.getParameter("deleteId");
		String editId = req.getParameter("editId");

		List<ExhibitionModel> exhibitions;
		if (keyword != null && searchBy != null && !keyword.trim().isEmpty()) {
			exhibitions = service.searchExhibitions(searchBy, keyword);
		} else {
			exhibitions = service.getAllExhibitions();
		}

		if (deleteId != null) {
			service.deleteExhibition(Integer.parseInt(deleteId));
			resp.sendRedirect("exhibitionManagement");
			return;
		}

		if (editId != null) {
			ExhibitionModel exhibitionToEdit = service.getExhibitionById(Integer.parseInt(editId));
			req.setAttribute("editExhibition", exhibitionToEdit);
		}

		req.setAttribute("exhibitions", exhibitions);
		req.getRequestDispatcher("WEB-INF/pages/exhibitionManagement.jsp").forward(req, resp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		int exhibitionId = Integer.parseInt(request.getParameter("exhibitionId"));
		String exhibitionTitle = request.getParameter("exhibitionTitle");
		String exhibitionDescription = request.getParameter("exhibitionDescription");
		Date startDate = Date.valueOf(request.getParameter("startDate"));
		Date endDate = Date.valueOf(request.getParameter("endDate"));

		Part exhibitionImagePart = request.getPart("exhibitionImage");
		String exhibitionImageFileName = null;

		ImageUtil imageUtil = new ImageUtil();

		if (exhibitionImagePart != null && exhibitionImagePart.getSize() > 0) {
			exhibitionImageFileName = imageUtil.getImageNameFromPart(exhibitionImagePart);
			String uploadPath = request.getServletContext().getRealPath("/") + "resources/images/exhibition";

			boolean uploaded = imageUtil.uploadImage(exhibitionImagePart, uploadPath, exhibitionImageFileName);
			if (!uploaded) {
				request.setAttribute("error", "Exhibition image upload failed.");
				doGet(request, response);
				return;
			}
		} else {
			ExhibitionModel existing = service.getExhibitionById(exhibitionId);
			if (existing != null) {
				exhibitionImageFileName = existing.getExhibitionImage();
			}
		}

		ExhibitionModel exhibition = new ExhibitionModel(exhibitionId, exhibitionTitle, exhibitionDescription,
				startDate, endDate, exhibitionImageFileName);

		if (service.getExhibitionById(exhibitionId) != null) {
			service.updateExhibition(exhibition);
		} else {
			service.addExhibition(exhibition);
		}

		response.sendRedirect("exhibitionManagement");
	}
}
