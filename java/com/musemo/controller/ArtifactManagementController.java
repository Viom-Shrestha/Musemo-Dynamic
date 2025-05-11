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
 * @author Viom Shrestha
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/artifactManagement" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class ArtifactManagementController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArtifactManagementService service;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public void init() {
		service = new ArtifactManagementService();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String keyword = req.getParameter("keyword");
		String searchBy = req.getParameter("searchBy");
		String deleteId = req.getParameter("deleteId");
		String editId = req.getParameter("editId");

		List<ArtifactModel> artifacts;
		if (keyword != null && searchBy != null && !keyword.trim().isEmpty()) {
			artifacts = service.searchArtifacts(searchBy, keyword);
		} else {
			artifacts = service.getAllArtifacts();
		}
		if (deleteId != null) {
			service.deleteArtifact(deleteId);
			resp.sendRedirect("artifactManagement");
			return;
		}

		if (editId != null) {
			ArtifactModel artifactToEdit = service.getArtifactById(editId);
			req.setAttribute("editArtifact", artifactToEdit);
		}

		req.setAttribute("artifacts", artifacts);
		req.getRequestDispatcher("WEB-INF/pages/artifactManagement.jsp").forward(req, resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String artifactID = request.getParameter("artifactID");
		System.out.println("artifactID = " + artifactID); // DEBUG
		String artifactName = request.getParameter("artifactName");
		System.out.println("artifactName = " + artifactName); // DEBUG
		String artifactType = request.getParameter("artifactType");
		String creatorName = request.getParameter("creatorName");
		String timePeriod = request.getParameter("timePeriod");
		String origin = request.getParameter("origin");
		String condition = request.getParameter("condition");
		String description = request.getParameter("description");
		String action = request.getParameter("action"); // either "add" or "update"
		System.out.println("Action = " + action); // DEBUG

		ImageUtil imageUtil = new ImageUtil();
		String artifactImageFileName = null;

		try {
			Part artifactImagePart = request.getPart("artifactImage");

			// Only validate and process if a new image was uploaded
			if (artifactImagePart != null && artifactImagePart.getSize() > 0) {
				if (!ValidationUtil.isValidImageExtension(artifactImagePart)) {
					request.setAttribute("error", "Invalid image format. Only jpg, jpeg, png, and gif are allowed.");
					doGet(request, response);
					return;
				}

				System.out.println("Received artifact image: " + artifactImagePart.getSubmittedFileName());
				System.out.println("File size: " + artifactImagePart.getSize());

				artifactImageFileName = imageUtil.getImageNameFromPart(artifactImagePart);
				System.out.println("Extracted image name: " + artifactImageFileName);

				String uploadPath = request.getServletContext().getRealPath("/") + "resources/images/artifact";
				System.out.println("Resolved upload path: " + uploadPath);
				System.out.println("Final path: " + uploadPath + "/" + artifactImageFileName);

				boolean uploaded = imageUtil.uploadImage(artifactImagePart, uploadPath, artifactImageFileName);
				if (!uploaded) {
					System.out.println("Image upload failed.");
					request.setAttribute("error", "Artifact image upload failed.");
					doGet(request, response);
					return;
				} else {
					System.out.println("Image uploaded successfully.");
				}
			} else {
				// No new image uploaded — use the existing one
				ArtifactModel existingArtifact = service.getArtifactById(artifactID);
				if (existingArtifact != null) {
					artifactImageFileName = existingArtifact.getArtifactImage();
				}
			}
		} catch (IOException | ServletException e) {
			request.setAttribute("error", "Error handling image file. Please ensure the file is valid.");
		}

		// Create an artifact object with the collected data
		ArtifactModel artifact = new ArtifactModel(artifactID, artifactName, artifactType, creatorName, timePeriod,
				origin, condition, description, artifactImageFileName);

		// Check if the artifact exists to decide whether to add or update
		if ("update".equals(action)) {
			// Only update if the artifact exists
			if (service.getArtifactById(artifactID) != null) {
				service.updateArtifact(artifact);
				request.setAttribute("success", "Artifact updated sucessfully.");
			} else {
				// Optionally handle "update" for non-existing artifact (edge case)
				request.setAttribute("error", "Artifact not found for update.");
				doGet(request, response);
				return;
			}
		} else if ("add".equals(action)) {
			// Only add if the artifact does not already exist
			if (service.getArtifactById(artifactID) == null) {
				service.addArtifact(artifact);
				request.setAttribute("success", "Artifact added sucessfully.");
			} else {
				request.setAttribute("error", "Artifact with this ID already exists.");
				doGet(request, response);
				return;
			}
		}

		doGet(request, response);

	}
}
