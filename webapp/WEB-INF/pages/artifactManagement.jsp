<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>MUSEMO - Artifact Management</title>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
	rel="stylesheet">
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${contextPath}/css/artifactManagement.css" />
</head>
<body>
	<!-- Sidebar -->
	<div class="sidebar">
		<div class="logo-container">
			<div class="logo">
				<img src="${contextPath}/resources/images/icons/admin_icon.png"
					alt="Admin Logo" style="width: 40px; height: 40px;">
			</div>
			<div class="brand-name">MUSEMO Admin</div>
		</div>

		<a href="${contextPath}/dashboard" class="menu-item"> <i
			class="fas fa-th-large"></i> <span>Dashboard</span>
		</a> <a href="${contextPath}/userManagement" class="menu-item"> <i
			class="fas fa-users"></i> <span>User Management</span>
		</a> <a href="${contextPath}/artifactManagement" class="menu-item active">
			<i class="fas fa-archive"></i> <span>Artifact Management</span>
		</a> <a href="${contextPath}/exhibitionManagement" class="menu-item">
			<i class="fas fa-calendar-alt"></i> <span>Exhibitions</span>
		</a> <a href="${contextPath}/adminProfile" class="menu-item"> <i
			class="fas fa-user-cog"></i> <span>Admin Profile</span>
		</a>
	</div>

	<!-- Main Content Area -->
	<div class="main-content">
		<h1 class="page-title">Artifact Management</h1>

		<!-- Search and Filter -->
		<div class="search-filter">
			<form method="get" action="${contextPath}/artifactManagement"
				class="search-filter">
				<div class="search-input">
					<i class="fas fa-search"></i> <input type="text" name="keyword"
						placeholder="Search" value="${param.keyword}">
				</div>
				<div class="filter-select">
					<select name="searchBy">
						<option value="">Search By</option>
						<option value="artifactName"
							${param.searchBy == 'artifactName' ? 'selected' : ''}>Name</option>
						<option value="artifactType"
							${param.searchBy == 'artifactType' ? 'selected' : ''}>Type</option>
						<option value="origin"
							${param.searchBy == 'origin' ? 'selected' : ''}>Origin</option>
						<option value="condition"
							${param.searchBy == 'condition' ? 'selected' : ''}>Condition</option>
					</select>
				</div>
			</form>
		</div>



		<!-- Artifacts Table -->
		<table class="artifact-table" id="artifacts-table">
			<thead>
				<tr>
					<th>Image</th>
					<th>Artifact Id</th>
					<th>Name</th>
					<th>Type</th>
					<th>Creator</th>
					<th>Time Period</th>
					<th>Origin</th>
					<th>Condition</th>
					<th>Description</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="artifact" items="${artifacts}">
					<tr>
						<td class="image-cell"><img
							src="${contextPath}/resources/images/artifact/${artifact.artifactImage}"
							alt="Artifact Image" class="user-image"></td>
						<td>${artifact.artifactID}</td>
						<td>${artifact.artifactName}</td>
						<td>${artifact.artifactType}</td>
						<td>${artifact.creatorName}</td>
						<td>${artifact.timePeriod}</td>
						<td>${artifact.origin}</td>
						<td>${artifact.condition}</td>
						<td>${artifact.description}</td>
						<td>
							<div class="action-buttons">
								<a
									href="${contextPath}/artifactManagement?editId=${artifact.artifactID}"
									class="edit-button" aria-label="Edit"> <i
									class="fa-solid fa-pen-to-square"></i>
								</a> <a
									href="${contextPath}/artifactManagement?deleteId=${artifact.artifactID}"
									class="delete-button" aria-label="Delete"
									onclick="return confirm('Are you sure you want to delete this artifact?');">
									<i class="fa-solid fa-trash"></i>
								</a>

							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<!-- Artifact Form Container -->
		<div class="form-container">
			<h2>${editArtifact != null ? "Update Artifact" : "Add New Artifact"}</h2>
			<form action="${contextPath}/artifactManagement" method="post"
				enctype="multipart/form-data" class="artifact-form">

				<div class="form-row">
					<div class="form-group">
						<label for="artifactID">Artifact ID</label> <input type="text"
							id="artifactID" name="artifactID"
							value="${editArtifact.artifactID}"
							<c:if test="${editArtifact != null}">readonly</c:if> required>
					</div>
					<div class="form-group">
						<label for="artifactName">Artifact Name</label> <input type="text"
							id="artifactName" name="artifactName"
							value="${editArtifact.artifactName}" required>
					</div>
				</div>

				<div class="form-group">
					<label for="artifactType">Artifact Type</label> <input type="text"
						id="artifactType" name="artifactType"
						value="${editArtifact.artifactType}" required>
				</div>

				<div class="form-row">
					<div class="form-group">
						<label for="creatorName">Creator Name</label> <input type="text"
							id="creatorName" name="creatorName"
							value="${editArtifact.creatorName}" required>
					</div>
					<div class="form-group">
						<label for="timePeriod">Time Period</label> <input type="text"
							id="timePeriod" name="timePeriod"
							value="${editArtifact.timePeriod}" required>
					</div>
				</div>

				<div class="form-row">
					<div class="form-group">
						<label for="origin">Origin</label> <input type="text" id="origin"
							name="origin" value="${editArtifact.origin}" required>
					</div>
					<div class="form-group">
						<label for="condition">Condition</label> <input type="text"
							id="condition" name="condition" value="${editArtifact.condition}"
							required>
					</div>
				</div>

				<div class="form-group">
					<label for="description">Description</label>
					<textarea id="description" name="description" rows="4" required><c:out
							value="${editArtifact.description}" /></textarea>
				</div>

				<div class="form-group">
					<label for="artifactImage">Artifact Image</label> <input
						type="file" id="artifactImage" name="artifactImage"
						accept="image/*"
						<c:if test="${editArtifact == null}">required</c:if>>
				</div>
				
				<div class="form-buttons">
					<c:choose>
						<c:when test="${editArtifact != null}">
							<button type="submit" class="update-button">Update
								Artifact</button>
							<a href="${contextPath}/artifactManagement" class="cancel-button">Cancel
								Update</a>
						</c:when>
						<c:otherwise>
							<button type="submit" class="add-button">Add Artifact</button>
						</c:otherwise>
					</c:choose>
					<button type="reset" class="clear-button">Clear</button>
				</div>
			</form>
		</div>

	</div>

</body>
</html>