<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="jakarta.servlet.http.HttpSession"%>
<%@ page import="jakarta.servlet.http.HttpServletRequest"%>

<%
HttpSession userSession = request.getSession(false);
String currentUser = (String) (userSession != null ? userSession.getAttribute("username") : null);
pageContext.setAttribute("currentUser", currentUser);
%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<link rel="stylesheet" href="${contextPath}/resources/css/header.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />

<div id="header">
	<header>
		<div class="logo">
			<a href="${contextPath}/home"> <img
				src="${contextPath}/resources/images/system/Logo.png"
				alt="Musemo Logo" />
			</a>
		</div>

		<nav>
			<ul class="main-nav">
				<li><a href="${contextPath}/home">Home</a></li>
				<li><a href="${contextPath}/exhibition">Exhibition</a></li>
				<li><a href="${contextPath}/artifacts">Artifacts</a></li>
				<li><a href="${contextPath}/about">About</a></li>
				<li><a href="${contextPath}/contact">Contact</a></li>

				<c:if test="${empty currentUser}">
					<li><a href="${contextPath}/register">Register</a></li>
				</c:if>

				<li><c:choose>
						<c:when test="${not empty currentUser}">
							<form action="${contextPath}/logout" method="post"
								class="logout-form">
								<input type="submit" class="nav-button" value="Logout" />
							</form>
						</c:when>
						<c:otherwise>
							<a href="${contextPath}/login">Login</a>
						</c:otherwise>
					</c:choose></li>
			</ul>
		</nav>

		<c:choose>
			<c:when test="${not empty currentUser}">
				<a href="${contextPath}/user/profile" class="user-icon"
					title="View Profile"> <i class="fas fa-user-circle"></i>
				</a>
			</c:when>
			<c:otherwise>
				<div class="user-icon" title="Guest">
					<i class="fas fa-user-circle"></i>
				</div>
			</c:otherwise>
		</c:choose>

	</header>
</div>
