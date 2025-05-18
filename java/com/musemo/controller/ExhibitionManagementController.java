package com.musemo.controller;

import com.musemo.model.ArtifactModel;
import com.musemo.model.ExhibitionArtifactModel;
import com.musemo.model.ExhibitionModel;
import com.musemo.service.ExhibitionManagementService;
import com.musemo.service.ArtifactManagementService;
import com.musemo.util.ImageUtil;
import com.musemo.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

/**
 * Controller for handling administrative operations related to exhibitions,
 * including listing, searching, deleting, editing, adding, and updating
 * exhibitions, as well as managing the artifacts associated with them.
 *
 *  @author 23048612 Viom Shrestha
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/exhibitionManagement" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB: Threshold after which files are written to disk
		maxFileSize = 1024 * 1024 * 10, // 10MB: Maximum size allowed for uploaded files
		maxRequestSize = 1024 * 1024 * 50 // 50MB: Maximum size allowed for the entire multipart request
)
public class ExhibitionManagementController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ExhibitionManagementService exhibitionService;
	private ArtifactManagementService artifactService;

	/**
	 * Initializes the servlet by creating instances of ExhibitionManagementService
	 * and ArtifactManagementService.
	 */
	public void init() {
		exhibitionService = new ExhibitionManagementService();
		artifactService = new ArtifactManagementService();
	}

	/**
	 * Handles GET requests to the /exhibitionManagement URL. It supports listing
	 * exhibitions (with optional keyword search), deleting exhibitions, preparing
	 * an exhibition for editing, and removing artifacts from an exhibition.
	 *
	 * @param req  The HttpServletRequest object containing the client's request.
	 * @param resp The HttpServletResponse object for sending the response to the
	 *             client.
	 * @throws ServletException If a servlet-specific error occurs during
	 *                          processing.
	 * @throws IOException      If an I/O error occurs while handling the request.
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String keyword = req.getParameter("keyword");
		String searchBy = req.getParameter("searchBy");
		String deleteId = req.getParameter("deleteId");
		String editId = req.getParameter("editId");
		String removeExhibitionId = req.getParameter("removeExhibitionId");
		String removeArtifactId = req.getParameter("removeArtifactId");

		// Retrieve all exhibition-artifact relationships
		List<ExhibitionArtifactModel> relations = exhibitionService.getAllExhibitionArtifactRelations();
		// Retrieve all available artifacts
		List<ArtifactModel> artifacts = artifactService.getAllArtifacts();
		List<ExhibitionModel> exhibitions;

		// Search exhibitions by keyword if provided
		if (keyword != null && searchBy != null && !keyword.trim().isEmpty()) {
			exhibitions = exhibitionService.searchExhibitions(searchBy, keyword);
		} else {
			// Otherwise, retrieve all exhibitions
			exhibitions = exhibitionService.getAllExhibitions();
		}

		// Handle exhibition deletion
		if (deleteId != null) {
			exhibitionService.deleteExhibition(Integer.parseInt(deleteId));
			resp.sendRedirect("exhibitionManagement");
			return;
		}

		// Prepare an exhibition for editing
		if (editId != null) {
			ExhibitionModel exhibitionToEdit = exhibitionService.getExhibitionById(Integer.parseInt(editId));
			req.setAttribute("editExhibition", exhibitionToEdit);
		}

		// Remove an artifact from an exhibition
		if (removeExhibitionId != null && removeArtifactId != null && removeExhibitionId.matches("\\d+")) {
			int exhibitionId = Integer.parseInt(removeExhibitionId);
			String artifactId = removeArtifactId;
			exhibitionService.removeArtifactFromExhibition(exhibitionId, artifactId);
			resp.sendRedirect("exhibitionManagement");
			return;
		}

		// Set attributes for the JSP page
		req.setAttribute("relations", relations);
		req.setAttribute("artifacts", artifacts);
		req.setAttribute("exhibitions", exhibitions);
		// Forward the request to the exhibition management page
		req.getRequestDispatcher("WEB-INF/pages/exhibitionManagement.jsp").forward(req, resp);
	}

	/**
	 * Handles POST requests to the /exhibitionManagement URL. It supports adding
	 * new exhibitions, updating existing exhibitions, and assigning artifacts to
	 * exhibitions. It also handles the uploading and validation of the exhibition's
	 * image.
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

		// Handle artifact assignment to exhibition
		if ("assign".equals(action)) {
			String artifactId = request.getParameter("artifactId");
			// Check if the relation already exists
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

		// Retrieve start and end dates
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

			// Process uploaded image if present
			if (exhibitionImagePart != null && exhibitionImagePart.getSize() > 0) {
				// Validate image extension
				if (!ValidationUtil.isValidImageExtension(exhibitionImagePart)) {
					request.setAttribute("error", "Invalid image format. Only jpg, jpeg, png, and gif are allowed.");
					doGet(request, response);
					return;
				}
				// Get image filename and upload the image
				exhibitionImageFileName = imageUtil.getImageNameFromPart(exhibitionImagePart);
				String uploadPath = request.getServletContext().getRealPath("/") + "resources/images/exhibition";
				boolean uploaded = imageUtil.uploadImage(exhibitionImagePart, uploadPath, exhibitionImageFileName);
				if (!uploaded) {
					request.setAttribute("error", "Exhibition image upload failed.");
					doGet(request, response);
					return;
				}
			} else {
				// If no new image, retain the existing one during update
				ExhibitionModel existing = exhibitionService.getExhibitionById(exhibitionId);
				if (existing != null) {
					exhibitionImageFileName = existing.getExhibitionImage();
				}
			}
		} catch (IOException | ServletException e) {
			request.setAttribute("error", "Error handling image file. Please ensure the file is valid.");
			doGet(request, response);
			return;
		}

		// Create ExhibitionModel object
		ExhibitionModel exhibition = new ExhibitionModel(exhibitionId, exhibitionTitle, exhibitionDescription,
				startDate, endDate, exhibitionImageFileName);

		// Handle update action
		if ("update".equals(action)) {
			if (exhibitionService.getExhibitionById(exhibitionId) != null) {
				exhibitionService.updateExhibition(exhibition);
				request.setAttribute("success", "Exhibition updated successfully.");
			} else {
				request.setAttribute("error", "Exhibition not found for update.");
				doGet(request, response);
				return;
			}
		}
		// Handle add action
		else if ("add".equals(action)) {
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