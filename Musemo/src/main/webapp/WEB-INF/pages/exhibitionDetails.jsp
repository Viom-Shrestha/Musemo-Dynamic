<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MUSEMO - Exhibition Details</title>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
	rel="stylesheet">
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/header.css" />
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/exhibitionDetails.css" />
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/footer.css" />
</head>
<body>
	<jsp:include page="header.jsp" />

	<!-- Page Header -->
	<header class="page-header">
		<h1>Exhibition Details</h1>
		<h2>National Museum of History</h2>
	</header>

	<!-- Main Content -->
	<main class="main-content">
		<!-- Exhibition Image -->
		<div class="exhibition-image">
			<img
				src="${contextPath}/resources/images/exhibition/${exhibition.exhibitionImage}"
				alt="${exhibition.exhibitionTitle}" />
		</div>

		<!-- Exhibition Information -->
		<section class="exhibition-info">
			<div class="exhibition-title">
				<h2>${exhibition.exhibitionTitle}</h2>
				<a href="${contextPath}/booking" class="btn">Book your Visit</a>
			</div>
			<p>${exhibition.startDate}- ${exhibition.endDate}</p>
			<br>
			<div class="exhibition-description">
				<p>${exhibition.exhibitionDescription}</p>
			</div>
		</section>

		<!-- Featured Artifacts 
    <section class="featured-section">
        <h2 class="featured-title">Featured Artifacts</h2>
        <div class="artifacts-grid">
            <c:forEach var="artifact" items="${artifactList}">
                <div class="artifact-card">
                    <div class="artifact-img">
                        <img src="${artifact.artifactImage}" alt="${artifact.artifactName}" />
                    </div>
                    <div class="artifact-info">
                        <p class="artifact-title">${artifact.artifactName}</p>
                        <p class="artifact-subtitle">${artifact.artifactType}</p>
                        <a href="${contextPath}/artifactDetails?id=${artifact.artifactID}" class="view-more-btn">View More</a>
                    </div>
                </div>
            </c:forEach>
        </div>
    </section>
    -->
	</main>

	<jsp:include page="footer.jsp" />
</body>
</html>
