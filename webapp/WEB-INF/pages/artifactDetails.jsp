<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MUSEMO - Artifact Details</title>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
	rel="stylesheet">
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/header.css" />
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/artifactDetails.css" />
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/footer.css" />
</head>
<body>
	<jsp:include page="header.jsp" />

	<!-- Artifact Content -->
	<main class="artifact-container">
		<!-- Left Image Section -->
		<div class="artifact-image">
			<div class="image-placeholder">
				<img
					src="${contextPath}/resources/images/artifact/${artifact.artifactImage}"
					alt="${artifact.artifactName}" />
			</div>
		</div>

		<!-- Right Details Section -->
		<div class="artifact-details">
			<!-- Basic Information -->
			<div class="details-header">
				<div class="details-info">
					<span class="info-label">Name:</span> <span>${artifact.artifactName}</span>

					<span class="info-label">Type:</span> <span>${artifact.artifactType}</span>

					<span class="info-label">Creator:</span> <span>${artifact.creatorName}</span>

					<span class="info-label">Time Period:</span> <span>${artifact.timePeriod}</span>

					<span class="info-label">Origin:</span> <span>${artifact.origin}</span>

					<span class="info-label">Condition:</span> <span>${artifact.condition}</span>
				</div>
			</div>

			<!-- Description -->
			<div class="description">
				<h3>Description</h3>
				<p>${artifact.description}</p>
			</div>
			
			
			<!-- Exhibition Info (Optional or Static for now) 
			<div class="current-exhibition">
				<h3>Current Exhibition</h3>
				<p>To be determined or retrieved from database</p>
			</div>
			
			-->
		</div>
	</main>

	<jsp:include page="footer.jsp" />
</body>
</html>
