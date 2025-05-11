package com.musemo.controller;

import com.musemo.model.ArtifactModel;
import com.musemo.model.ExhibitionArtifactModel;
import com.musemo.model.ExhibitionModel;
import com.musemo.service.ExhibitionManagementService;
import com.musemo.service.ArtifactService;
import com.musemo.util.ImageUtil;
import com.musemo.util.ValidationUtil;

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
	private ExhibitionManagementService exhibitionService;
	private ArtifactService artifactService;

	public void init() {
		exhibitionService = new ExhibitionManagementService();
		artifactService = new ArtifactService();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String keyword = req.getParameter("keyword");
		String searchBy = req.getParameter("searchBy");
		String deleteId = req.getParameter("deleteId");
		String editId = req.getParameter("editId");
		String removeExhibitionId = req.getParameter("removeExhibitionId");
		String removeArtifactId = req.getParameter("removeArtifactId");

		List<ExhibitionArtifactModel> relations = exhibitionService.getAllExhibitionArtifactRelations();
		List<ArtifactModel> artifacts = artifactService.getAllArtifacts();
		List<ExhibitionModel> exhibitions;
		if (keyword != null && searchBy != null && !keyword.trim().isEmpty()) {
			exhibitions = exhibitionService.searchExhibitions(searchBy, keyword);
		} else {
			exhibitions = exhibitionService.getAllExhibitions();
		}

		if (deleteId != null) {
			exhibitionService.deleteExhibition(Integer.parseInt(deleteId));
			resp.sendRedirect("exhibitionManagement");
			return;
		}

		if (editId != null) {
			ExhibitionModel exhibitionToEdit = exhibitionService.getExhibitionById(Integer.parseInt(editId));
			req.setAttribute("editExhibition", exhibitionToEdit);
		}

		if (removeExhibitionId != null && removeArtifactId != null && removeExhibitionId.matches("\\d+")) {

			int exhibitionId = Integer.parseInt(removeExhibitionId);
			String artifactId = removeArtifactId;
			exhibitionService.removeArtifactFromExhibition(exhibitionId, artifactId);

			resp.sendRedirect("exhibitionManagement");
			return;
		}

		req.setAttribute("relations", relations);
		req.setAttribute("artifacts", artifacts);
		req.setAttribute("exhibitions", exhibitions);
		req.getRequestDispatcher("WEB-INF/pages/exhibitionManagement.jsp").forward(req, resp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String idParam = request.getParameter("exhibitionId");

		// Validate that the ID is numeric
		if (idParam == null || !idParam.matches("\\d+")) {
			request.setAttribute("error", "Exhibition ID must be a numeric value.");
			doGet(request, response);
			return;
		}

		int exhibitionId = Integer.parseInt(idParam);
		String exhibitionTitle = request.getParameter("exhibitionTitle");
		String exhibitionDescription = request.getParameter("exhibitionDescription");

		String action = request.getParameter("action");

		if ("assign".equals(action)) {
			String artifactId = request.getParameter("artifactId");

			boolean exists = exhibitionService.relationExists(exhibitionId, artifactId);
			if (!exists) {
				exhibitionService.addArtifactToExhibition(exhibitionId, artifactId);
				request.setAttribute("success", "Artifact assigned successfully.");
			} else {
				request.setAttribute("error", "This artifact is already assigned to this exhibition.");
			}
			doGet(request, response);
			return;
		}

		Date startDate = Date.valueOf(request.getParameter("startDate"));
		Date endDate = Date.valueOf(request.getParameter("endDate"));

		// Validate that startDate is not after endDate
		if (startDate.after(endDate)) {
			request.setAttribute("error", "Start date cannot be after end date.");
			doGet(request, response);
			return;
		}

		String exhibitionImageFileName = null;

		ImageUtil imageUtil = new ImageUtil();

		try {
			Part exhibitionImagePart = request.getPart("exhibitionImage");

			if (exhibitionImagePart != null && exhibitionImagePart.getSize() > 0) {
				if (!ValidationUtil.isValidImageExtension(exhibitionImagePart)) {
					request.setAttribute("error", "Invalid image format. Only jpg, jpeg, png, and gif are allowed.");
					doGet(request, response);
					return;
				}

				exhibitionImageFileName = imageUtil.getImageNameFromPart(exhibitionImagePart);
				String uploadPath = request.getServletContext().getRealPath("/") + "resources/images/exhibition";

				boolean uploaded = imageUtil.uploadImage(exhibitionImagePart, uploadPath, exhibitionImageFileName);
				if (!uploaded) {
					request.setAttribute("error", "Exhibition image upload failed.");
					doGet(request, response);
					return;
				}
			} else {
				// No image uploaded — retain the existing image
				ExhibitionModel existing = exhibitionService.getExhibitionById(exhibitionId);
				if (existing != null) {
					exhibitionImageFileName = existing.getExhibitionImage();
				}
			}
		} catch (IOException | ServletException e) {
			request.setAttribute("error", "Error handling image file. Please ensure the file is valid.");
		}

		ExhibitionModel exhibition = new ExhibitionModel(exhibitionId, exhibitionTitle, exhibitionDescription,
				startDate, endDate, exhibitionImageFileName);

		if ("update".equals(action)) {
			if (exhibitionService.getExhibitionById(exhibitionId) != null) {
				exhibitionService.updateExhibition(exhibition);
				request.setAttribute("success", "Exhibition updated successfully.");
			} else {
				request.setAttribute("error", "Exhibition not found for update.");
				doGet(request, response);
				return;
			}
		} else if ("add".equals(action)) {
			if (exhibitionService.getExhibitionById(exhibitionId) == null) {
				exhibitionService.addExhibition(exhibition);
				request.setAttribute("success", "Exhibition added successfully.");
			} else {
				request.setAttribute("error", "Exhibition with this ID already exists.");
				doGet(request, response);
				return;
			}
		}

		doGet(request, response);

	}

}
