package com.musemo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.List;

import com.musemo.model.ArtifactModel;
import com.musemo.service.ArtifactManagementService;
import com.musemo.util.ImageUtil;
import com.musemo.util.ValidationUtil;

/**
 * Controller for handling administrative operations related to artifacts,
 * including listing, searching, deleting, editing, adding, and updating
 * artifacts.
 *
 *  @author 23048612 Viom Shrestha
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/artifactManagement" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB: Threshold after which files are written to disk
		maxFileSize = 1024 * 1024 * 10, // 10MB: Maximum size allowed for uploaded files
		maxRequestSize = 1024 * 1024 * 50 // 50MB: Maximum size allowed for the entire multipart request
)
public class ArtifactManagementController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArtifactManagementService service;

	/**
	 * Initializes the servlet by creating an instance of the
	 * ArtifactManagementService, which will be used to interact with artifact data.
	 */
	public void init() {
		service = new ArtifactManagementService();
	}

	/**
	 * Handles GET requests to the /artifactManagement URL. It supports listing
	 * artifacts (with optional keyword search), deleting artifacts, and preparing
	 * an artifact for editing.
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

		List<ArtifactModel> artifacts;
		// If a keyword and search criteria are provided, search for artifacts
		if (keyword != null && searchBy != null && !keyword.trim().isEmpty()) {
			artifacts = service.searchArtifactByFilter(searchBy, keyword);
		} else {
			// Otherwise, retrieve all artifacts
			artifacts = service.getAllArtifacts();
		}

		// If a delete ID is provided, delete the corresponding artifact and redirect
		if (deleteId != null) {
			service.deleteArtifact(deleteId);
			resp.sendRedirect("artifactManagement");
			return;
		}

		// If an edit ID is provided, fetch the artifact to be edited and set it as a
		// request attribute
		if (editId != null) {
			ArtifactModel artifactToEdit = service.getArtifactById(editId);
			req.setAttribute("editArtifact", artifactToEdit);
		}

		// Set the list of artifacts as a request attribute
		req.setAttribute("artifacts", artifacts);
		// Forward the request to the artifactManagement.jsp page to display the
		// artifacts and any messages
		req.getRequestDispatcher("WEB-INF/pages/artifactManagement.jsp").forward(req, resp);
	}

	/**
	 * Handles POST requests to the /artifactManagement URL. It supports adding new
	 * artifacts and updating existing artifacts based on the submitted form data.
	 * It also handles the uploading and validation of the artifact's image.
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // Ensure proper handling of UTF-8 characters in the request

		// Retrieve artifact details from the form parameters
		String artifactID = request.getParameter("artifactID");
		String artifactName = request.getParameter("artifactName");
		String artifactType = request.getParameter("artifactType");
		String creatorName = request.getParameter("creatorName");
		String timePeriod = request.getParameter("timePeriod");
		String origin = request.getParameter("origin");
		String condition = request.getParameter("condition");
		String description = request.getParameter("description");
		String action = request.getParameter("action"); // "add" for new, "update" for existing

		ImageUtil imageUtil = new ImageUtil();
		String artifactImageFileName = null;

		try {
			// Get the Part representing the uploaded artifact image
			Part artifactImagePart = request.getPart("artifactImage");

			// Check if a new image was uploaded
			if (artifactImagePart != null && artifactImagePart.getSize() > 0) {
				// Validate the uploaded image file extension
				if (!ValidationUtil.isValidImageExtension(artifactImagePart)) {
					request.setAttribute("error", "Invalid image format. Only jpg, jpeg, png, and gif are allowed.");
					doGet(request, response);
					return;
				}

				// Extract the filename from the Part
				artifactImageFileName = imageUtil.getImageNameFromPart(artifactImagePart);
				// Determine the upload path in the application's resources directory
				String uploadPath = request.getServletContext().getRealPath("/") + "resources/images/artifact";
				// Upload the image file to the specified path
				boolean uploaded = imageUtil.uploadImage(artifactImagePart, uploadPath, artifactImageFileName);
				if (!uploaded) {
					request.setAttribute("error", "Artifact image upload failed.");
					doGet(request, response);
					return;
				}
			} else {
				// If no new image was uploaded during an update, retain the existing image
				// filename
				if ("update".equals(action)) {
					ArtifactModel existingArtifact = service.getArtifactById(artifactID);
					if (existingArtifact != null) {
						artifactImageFileName = existingArtifact.getArtifactImage();
					}
				}
				// For adding a new artifact without an image, artifactImageFileName will remain
				// null
			}
		} catch (IOException | ServletException e) {
			request.setAttribute("error", "Error handling image file. Please ensure the file is valid.");
			doGet(request, response);
			return;
		}

		// Create an ArtifactModel object with the data from the form
		ArtifactModel artifact = new ArtifactModel(artifactID, artifactName, artifactType, creatorName, timePeriod,
				origin, condition, description, artifactImageFileName);

		// Handle the "add" action
		if ("add".equals(action)) {
			// Check if an artifact with the given ID already exists
			if (service.getArtifactById(artifactID) == null) {
				service.addArtifact(artifact);
				request.setAttribute("success", "Artifact added successfully.");
			} else {
				request.setAttribute("error", "Artifact with this ID already exists.");
				doGet(request, response);
				return;
			}
		}
		// Handle the "update" action
		else if ("update".equals(action)) {
			// Check if the artifact with the given ID exists before attempting to update
			if (service.getArtifactById(artifactID) != null) {
				service.updateArtifact(artifact);
				request.setAttribute("success", "Artifact updated successfully.");
			} else {
				request.setAttribute("error", "Artifact not found for update.");
				doGet(request, response);
				return;
			}
		}

		// After processing (add or update), redirect back to the artifact management page to refresh the list
		doGet(request, response);
	}
}