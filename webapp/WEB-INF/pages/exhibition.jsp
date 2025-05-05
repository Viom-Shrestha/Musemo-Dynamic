<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MUSEMO - Exhibitions</title>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
	rel="stylesheet">
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/header.css" />
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/exhibition.css" />
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/footer.css" />
</head>
<body>
	<jsp:include page="header.jsp" />
	<!-- Search and Filter Section -->
	<div class="search-section">
		<div class="search-bar">
			<i class="fa-solid fa-search search-icon"></i> <input type="text"
				class="search-input" placeholder="Search">
		</div>
	</div>

	<!-- Gallery Grid -->
	<div class="gallery-container">
		<c:choose>
			<c:when test="${not empty exhibition}">
				<c:forEach var="exhibition" items="${exhibition}">
					<div class="exhibition-card">
						<div class="card-image">
							<img
								src="${contextPath}/resources/images/exhibition/${exhibition.exhibitionImage}"
								alt="${exhibition.exhibitionTitle}">
						</div>
						<div class="card-content">
							<h3 class="card-title">${exhibition.exhibitionTitle}</h3>
							<p class="card-description">${exhibition.exhibitionDescription}</p>
							<a
								href="${contextPath}/exhibitionDetails?id=${exhibition.exhibitionId}"
								class="view-more-btn">View More</a>
						</div>
					</div>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<p>No exhibitions found.</p>
			</c:otherwise>
		</c:choose>
	</div>

	<jsp:include page="footer.jsp" />
</body>
</html>