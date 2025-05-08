<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>MUSEMO - Exhibition Management</title>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
	rel="stylesheet">
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<link rel="stylesheet"
	href="${contextPath}/css/exhibitionManagement.css" />
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

		<a href="${contextPath}/dashboard" class="menu-item"><i
			class="fas fa-th-large"></i> <span>Dashboard</span></a> <a
			href="${contextPath}/userManagement" class="menu-item"><i
			class="fas fa-users"></i> <span>User Management</span></a> <a
			href="${contextPath}/artifactManagement" class="menu-item"><i
			class="fas fa-archive"></i> <span>Artifact Management</span></a> <a
			href="${contextPath}/exhibitionManagement" class="menu-item active"><i
			class="fas fa-calendar-alt"></i> <span>Exhibitions</span></a> <a
			href="${contextPath}/adminProfile" class="menu-item"><i
			class="fas fa-user-cog"></i> <span>Admin Profile</span></a>
	</div>

	<!-- Main Content -->
	<div class="main-content">
		<h1 class="page-title">Exhibition Management</h1>

		<!-- Search & Filter -->
		<div class="search-filter">
			<form method="get" action="${contextPath}/exhibitionManagement"
				class="search-filter">
				<div class="search-input">
					<i class="fas fa-search"></i> <input type="text" name="keyword"
						placeholder="Search" value="${param.keyword}">
				</div>
				<div class="filter-select">
					<select name="searchBy">
						<option value="">Search By</option>
						<option value="exhibitionTitle"
							${param.searchBy == 'exhibitionTitle' ? 'selected' : ''}>Title</option>
						<option value="exhibitionDescription"
							${param.searchBy == 'exhibitionDescription' ? 'selected' : ''}>Description</option>
					</select>
				</div>
			</form>
		</div>

		<!-- Exhibition Table -->
		<table class="exhibition-table">
			<thead>
				<tr>
					<th>Image</th>
					<th>ID</th>
					<th>Title</th>
					<th>Description</th>
					<th style="width: 100px;">Start Date</th>
					<th style="width: 100px;">End Date</th>
					<th style="width: 80px;">Actions</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="exhibition" items="${exhibitions}">
					<tr>
						<td class="image-cell"><img
							src="${contextPath}/resources/images/exhibition/${exhibition.exhibitionImage}"
							alt="Exhibition Image" class="user-image"></td>
						<td>${exhibition.exhibitionId}</td>
						<td>${exhibition.exhibitionTitle}</td>
						<td>${exhibition.exhibitionDescription}</td>
						<td><fmt:formatDate value="${exhibition.startDate}"
								pattern="yyyy-MM-dd" /></td>
						<td><fmt:formatDate value="${exhibition.endDate}"
								pattern="yyyy-MM-dd" /></td>
						<td>
							<div class="action-buttons">
								<a
									href="${contextPath}/exhibitionManagement?editId=${exhibition.exhibitionId}"
									class="edit-button"><i class="fa-solid fa-pen-to-square"></i></a>
								<a
									href="${contextPath}/exhibitionManagement?deleteId=${exhibition.exhibitionId}"
									class="delete-button"
									onclick="return confirm('Are you sure you want to delete this exhibition?');"><i
									class="fa-solid fa-trash"></i></a>
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<div class="form-messages">
			<c:if test="${not empty error}">
				<p class="error-message">${error}</p>
			</c:if>
			<c:if test="${not empty success}">
				<p class="success-message">${success}</p>
			</c:if>
		</div>

		<!-- Exhibition Form -->
		<div class="form-container">
			<h2>${editExhibition != null ? "Update Exhibition" : "Add New Exhibition"}</h2>
			<form action="${contextPath}/exhibitionManagement" method="post"
				enctype="multipart/form-data" class="artifact-form">

				<div class="form-row">
					<div class="form-group">
						<label for="exhibitionId">Exhibition ID</label> <input type="text"
							id="exhibitionId" name="exhibitionId"
							value="${editExhibition.exhibitionId}"
							<c:if test="${editExhibition != null}">readonly</c:if> required>
					</div>
					<div class="form-group">
						<label for="exhibitionTitle">Title</label> <input type="text"
							id="exhibitionTitle" name="exhibitionTitle"
							value="${editExhibition.exhibitionTitle}" required>
					</div>
				</div>

				<div class="form-group">
					<label for="exhibitionDescription">Description</label>
					<textarea id="exhibitionDescription" name="exhibitionDescription"
						rows="4" required><c:out
							value="${editExhibition.exhibitionDescription}" /></textarea>
				</div>

				<div class="form-row">
					<div class="form-group">
						<label for="startDate">Start Date</label> <input type="date"
							id="startDate" name="startDate"
							value="<fmt:formatDate value='${editExhibition.startDate}' pattern='yyyy-MM-dd' />"
							required>
					</div>
					<div class="form-group">
						<label for="endDate">End Date</label> <input type="date"
							id="endDate" name="endDate"
							value="<fmt:formatDate value='${editExhibition.endDate}' pattern='yyyy-MM-dd' />"
							required>
					</div>
				</div>

				<div class="form-group">
					<label for="exhibitionImage">Exhibition Image</label> <input
						type="file" id="exhibitionImage" name="exhibitionImage"
						accept="image/*"
						<c:if test="${editExhibition == null}">required</c:if>>
				</div>

				<div class="form-buttons">
					<c:choose>
						<c:when test="${editExhibition != null}">
							<button type="submit" name="action" value="update"
								class="update-button">Update Exhibition</button>
							<a href="${contextPath}/exhibitionManagement"
								class="cancel-button">Cancel Update</a>
						</c:when>
						<c:otherwise>
							<button type="submit" name="action" value="add"
								class="add-button">Add Exhibition</button>
						</c:otherwise>
					</c:choose>
					<button type="reset" class="clear-button">Clear</button>
				</div>
			</form>
		</div>

		<!-- exhibition artifact section -->
		<div class="relation-section">
			<h3>Assigned Artifacts to Exhibitions</h3>
			<table class="relation-table">
				<thead>
					<tr>
						<th>Exhibition</th>
						<th>Artifact</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="rel" items="${relations}">
						<tr>
							<td>${rel.exhibitionTitle}</td>
							<td>${rel.artifactName}</td>
							<td><a
								href="exhibitionManagement?removeExhibitionId=${rel.exhibitionId}&removeArtifactId=${rel.artifactId}"
								class="table-delete-btn"
								onclick="return confirm('Remove this artifact from the exhibition?');"
								class="fa-solid fa-trash">Remove</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

			<h3>Assign Artifact to Exhibition</h3>
			<form action="exhibitionManagement" method="post"
				class="relation-form">
				<input type="hidden" name="action" value="assign" /> <label
					for="exhibitionId">Exhibition:</label> <select name="exhibitionId"
					required>
					<option value="">Select Exhibition</option>
					<c:forEach var="exhibition" items="${exhibitions}">
						<option value="${exhibition.exhibitionId}">${exhibition.exhibitionTitle}</option>
					</c:forEach>
				</select> <label for="artifactId">Artifact:</label> <select name="artifactId"
					required>
					<option value="">Select Artifact</option>
					<c:forEach var="artifact" items="${artifacts}">
						<option value="${artifact.artifactID}">${artifact.artifactName}</option>
					</c:forEach>
				</select>

				<button type="submit">Assign</button>
			</form>
		</div>


	</div>
</body>
</html>
