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

		ImageUtil imageUtil = new ImageUtil();
		Part artifactImagePart = request.getPart("artifactImage"); // Get the artifact image part from the request
		String artifactImageFileName = null;

		// Check if a new image was uploaded
		if (artifactImagePart != null && artifactImagePart.getSize() > 0) {
			System.out.println("Received artifact image: " + artifactImagePart.getSubmittedFileName());
			System.out.println("File size: " + artifactImagePart.getSize());

			// Extract the image name from the Part
			artifactImageFileName = imageUtil.getImageNameFromPart(artifactImagePart);
			System.out.println("Extracted image name: " + artifactImageFileName);

			// Define the upload path for the image (assuming "artifact" folder)
			String uploadPath = request.getServletContext().getRealPath("/") + "resources/images/artifact";

			
			System.out.println("Resolved upload path: " + uploadPath);
			System.out.println("Final path: " + uploadPath + "/" + artifactImageFileName);

			// Upload the image to the server
			boolean uploaded = imageUtil.uploadImage(artifactImagePart, uploadPath, artifactImageFileName);

			if (!uploaded) {
				System.out.println("Image upload failed.");
				request.setAttribute("error", "Artifact image upload failed.");
				// Handle the failure appropriately, maybe returning an error or redirecting
				doGet(request, response);
				return;
			} else {
				System.out.println("Image uploaded successfully.");
			}

		} else {
			// Retain the existing image if none was uploaded
			ArtifactModel existingArtifact = service.getArtifactById(artifactID);
			if (existingArtifact != null) {
				artifactImageFileName = existingArtifact.getArtifactImage();
			}
		}

		// Create an artifact object with the collected data
		ArtifactModel artifact = new ArtifactModel(artifactID, artifactName, artifactType, creatorName, timePeriod,
				origin, condition, description, artifactImageFileName);

		// Check if the artifact exists to decide whether to add or update
		if (service.getArtifactById(artifactID) != null) {
			service.updateArtifact(artifact);
		} else {
			service.addArtifact(artifact);
		}

		response.sendRedirect("artifactManagement");
	}
}
