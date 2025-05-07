<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Musemo - Art and History Museum</title>
    <c:set var="contextPath" value="${pageContext.request.contextPath}" />
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/header.css" />
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/home.css" />
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/footer.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
    <jsp:include page="header.jsp" />

    <div class="hero" style="background-image: url('${contextPath}/resources/images/home_hero.jpg');">
        <div class="hero-content">
            <h1>Welcome to Musemo</h1>
            <h2>ART AND HISTORY MUSEUM</h2>
            <div class="hero-text">Experience art and cultures from
                different corners of the world</div>
            <a href="${contextPath}/booking" class="btn">Book your Visit</a>
        </div>
    </div>

    <h3 class="section-title">Featured Exhibitions</h3>
    <div class="card-container">

        <c:choose>
            <c:when test="${not empty featuredExhibitions}">

                <c:forEach var="exhibition" items="${featuredExhibitions}">
                    <div class="card">
                        <div class="card-img">
                            <img src="${contextPath}/resources/images/exhibition/${exhibition.exhibitionImage}" alt="${exhibition.exhibitionTitle}">
                        </div>
                        <div class="card-content">
                            <h4 class="card-title">${exhibition.exhibitionTitle}</h4>
 
                            <p class="card-subtitle">${exhibition.exhibitionDescription}</p>
                           
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p>No featured exhibitions available at this time.</p>
            </c:otherwise>
        </c:choose>
    </div>

    <h3 class="section-title">Featured Artifacts</h3>
    <div class="card-container">
        <c:choose>
            <c:when test="${not empty featuredArtifacts}">
                <c:forEach var="artifact" items="${featuredArtifacts}">
                    <div class="artifact-card">
                        <div class="artifact-img">
                            <img src="${contextPath}/resources/images/artifact/${artifact.artifactImage}" alt="${artifact.artifactName}">
                        </div>
                        <div class="artifact-content">
                            <h4 class="artifact-title">${artifact.artifactName}</h4>
                            <p class="artifact-period">${artifact.timePeriod}</p>
                            <p class="artifact-description">${artifact.description}</p>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p>No featured artifacts available at this time.</p>
            </c:otherwise>
        </c:choose>
    </div>

    <div class="plan-section">
        <div class="plan-info">
            <h3 class="plan-title">Plan Your Visit</h3>
            <p class="plan-description">Join us for an unforgettable journey
                through the art and history of Japanese animation!</p>

            <div class="museum-details">
                <div class="detail-section">
                    <h4>Hours</h4>
                    <p>10:00 AM - 5:00 PM</p>
                    <p>Closed on Mondays and major holidays</p>
                </div>
                <div class="detail-section">
                    <h4>Admission</h4>
                    <p>Adults: Rs 200</p>
                    <p>Seniors (65+): Rs 150</p>
                    <p>Students (with ID): Rs 100</p>
                    <p>Children (under 12): Free</p>
                </div>
                <div class="detail-section">
                    <h4>Location</h4>
                    <p>123 Museum Avenue</p>
                    <p>City, State 12345</p>
                    <p>Located in the heart of the Cultural District</p>
                </div>
            </div>
            <a href="${contextPath}/booking" class="btn">Book your Visit</a>
        </div>
        <div class="map-img">
            <img src="${contextPath}/resources/images/system/plan.jpg" alt="Museum Map">
        </div>
    </div>

    <div class="stats-container">
        <div class="stat-card">
            <div class="stat-img">
                <img src="${contextPath}/resources/images/icons/artifacts_icon.png" alt="Artifacts Icon">
            </div>
            <div class="stat-content">
                <p class="stat-title">Artifacts Collection</p>
                <p class="stat-number">${artifactCount}</p>
            </div>
        </div>

        <div class="stat-card">
            <div class="stat-img">
                <img src="${contextPath}/resources/images/icons/visitor_icon.png" alt="Visitor Icon">
            </div>
            <div class="stat-content">
                <p class="stat-title">Visitors Today</p> 
                <p class="stat-number">${visitorCount}</p>
            </div>
        </div>

        <div class="stat-card">
            <div class="stat-img">
                <img src="${contextPath}/resources/images/icons/bookings_icon.png" alt="Bookings Icon">
            </div>
            <div class="stat-content">
                <p class="stat-title">Bookings Today</p>
                <p class="stat-number">${bookingCount}</p>
            </div>
        </div>
    </div>

    <jsp:include page="footer.jsp" />

</body>
</html>