<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>MUSEMO - Admin Dashboard</title>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<link rel="stylesheet" href="${contextPath}/css/userManagement.css">

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
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
		</a> <a href="${contextPath}/userManagement" class="menu-item active">
			<i class="fas fa-users"></i> <span>User Management</span>
		</a> <a href="${contextPath}/artifactManagement" class="menu-item"> <i
			class="fas fa-archive"></i> <span>Artifact Management</span>
		</a> <a href="${contextPath}/exhibitionManagement" class="menu-item">
			<i class="fas fa-calendar-alt"></i> <span>Exhibitions</span>
		</a> <a href="${contextPath}/adminProfile" class="menu-item"> <i
			class="fas fa-user-cog"></i> <span>Admin Profile</span>
		</a>
	</div>
	<!-- Main Content -->
	<main class="main-content">
		<h1 class="page-title">User Management</h1>

		<!-- Search and Filter -->
		<div class="search-filter">
			<form method="get" action="${contextPath}/userManagement"
				class="search-filter">
				<div class="search-input">
					<i class="fas fa-search"></i> <input type="text" name="keyword"
						placeholder="Search" value="${param.keyword}">
				</div>
				<div class="filter-select">
					<select name="filter">
						<option value="">Search By</option>
						<option value="username"
							${param.filter == 'username' ? 'selected' : ''}>Username</option>
						<option value="name" ${param.filter == 'name' ? 'selected' : ''}>Name</option>
						<option value="email" ${param.filter == 'email' ? 'selected' : ''}>Email</option>
					</select>
				</div>
			</form>
		</div>

		<!-- User Table -->
		<table class="user-table">
			<thead>
				<tr>
					<th>Image</th>
					<th>Username</th>
					<th>Name</th>
					<th>Password</th>
					<th>Role</th>
					<th>Gender</th>
					<th>Email</th>
					<th>Contact</th>
					<th>Date of Birth</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${not empty users}">
						<c:forEach var="user" items="${users}">
							<tr>
								<td class="image-cell"><img
									src="${contextPath}/resources/imagesuser/${user.userImage}"
									alt="User Image" class="user-image"></td>
								<td>${user.username}</td>
								<td>${user.fullName}</td>
								<td>****</td>
								<td>${user.role}</td>
								<td>${user.gender}</td>
								<td>${user.email}</td>
								<td>${user.contact}</td>
								<td>${user.dateOfBirth}</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="9" style="text-align: center; padding: 20px;">No
								results found.</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>

	</main>
</html>