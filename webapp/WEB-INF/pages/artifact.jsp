<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MUSEMO - Artifacts</title>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
	rel="stylesheet">
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/header.css" />
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/artifact.css" />
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/footer.css" />
</head>
<body>
	<jsp:include page="header.jsp" />

	<!-- Search and Filter Section -->
	<div class="search-section">
		<form method="get" action="${contextPath}/artifact"
			class="search-form">
			<div class="category">
			<select name="type" class="category-dropdown">
				<option value="">Type: All</option>
				<option value="Painting"
					${param.type == 'Painting' ? 'selected' : ''}>Painting</option>
				<option value="Relic" ${param.type == 'Relic' ? 'selected' : ''}>Relic</option>
				<option value="Sculpture"
					${param.type == 'Sculpture' ? 'selected' : ''}>Sculpture</option>
			</select>
			</div>
			<div class="search-bar">
				<i class="fa-solid fa-search search-icon"></i> <input type="text"
					name="keyword" class="search-input" placeholder="Search"
					value="${param.keyword}">
			</div>
		</form>
	</div>

	<c:if test="${not empty param.keyword || not empty param.type}">
		<div class="search-info">
			<p>
				Showing results for:
				<c:if test="${not empty param.keyword}">
                keyword "<strong>${param.keyword}</strong>"
            </c:if>
				<c:if test="${not empty param.type}">
                and type "<strong>${param.type}</strong>"
            </c:if>
			</p>
		</div>
	</c:if>


	<!-- Gallery Grid -->
	<div class="gallery-container">
		<c:choose>
			<c:when test="${not empty artifactList}">
				<c:forEach var="artifact" items="${artifactList}">
					<div class="artifact-card">
						<div class="card-image">
							<img
								src="${contextPath}/resources/images/artifact/${artifact.artifactImage}"
								alt="${artifact.artifactName}">
						</div>
						<div class="card-content">
							<h3 class="card-title">${artifact.artifactName}</h3>
							<p class="card-description">
								<strong>Type:</strong> ${artifact.artifactType}
							</p>
							<p class="card-description">${artifact.description}</p>
							<a
								href="${contextPath}/artifactDetails?id=${artifact.artifactID}"
								class="view-more-btn">View More</a>
						</div>
					</div>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<p>No artifacts found.</p>
			</c:otherwise>
		</c:choose>
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>
