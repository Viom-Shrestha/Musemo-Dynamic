<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>User Profile - MUSEMO</title>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${contextPath}/css/profile.css" />
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/header.css" />
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/footer.css" />
</head>

<body>
	<jsp:include page="header.jsp" />
	<div class="profile-container">
		<div class="sidebar">
			<div class="profile-image-container">
				<div class="profile-image" id="profileImageDisplay">
					<img src="${contextPath}/resources/imagesuser/${user.userImage}"
						alt="Profile Photo"
						style="width: 150px; height: 150px; border-radius: 50%;">
				</div>


			</div>
			<div class="stats-container">
				<h3 class="stats-title">Your Museum Activity</h3>
				<div class="stats-item">
					<div class="stats-label">Total Bookings</div>
					<div class="stats-value">${totalBookings}</div>
				</div>
				<div class="stats-item">
					<div class="stats-label">Exhibitions Visited</div>
					<div class="stats-value">${exhibitionsVisited}</div>
				</div>
			</div>
		</div>
		<div class="content">
			<h2 class="section-title">Personal Information</h2>
			<div class="form-messages">
				<c:if test="${not empty error}">
					<p class="error-message">${error}</p>
				</c:if>
				<c:if test="${not empty success}">
					<p class="success-message">${success}</p>
				</c:if>
			</div>
			<form class="form-container" action="${contextPath}/profile"
				method="post" enctype="multipart/form-data">

				<div class="form-group">
					<label for="username">Username:</label> <input type="text"
						id="username" name="username" value="${user.username}"
						placeholder="Username" readonly>
				</div>

				<div class="form-group">
					<label for="fullname">Full Name:</label> <input type="text"
						id="fullname" name="fullName" value="${user.fullName}"
						placeholder="Full Name">
				</div>

				<div class="form-group">
					<label for="newPassword">New Password:</label> <input
						type="password" id="newPassword" name="newPassword"
						placeholder="Leave blank to keep current password">
				</div>

				<div class="form-group">
					<label for="confirmNewPassword">Confirm New Password:</label> <input
						type="password" id="confirmNewPassword" name="confirmNewPassword"
						placeholder="Confirm new password">
				</div>

				<div class="form-group">
					<label for="gender">Gender:</label> <select id="gender"
						name="gender">
						<option value="female"
							${user.gender == 'female' ? 'selected' : ''}>Female</option>
						<option value="male" ${user.gender == 'male' ? 'selected' : ''}>Male</option>
						<option value="nonbinary"
							${user.gender == 'nonbinary' ? 'selected' : ''}>Non-binary</option>
						<option value="other" ${user.gender == 'other' ? 'selected' : ''}>Other</option>
						<option value="prefer"
							${user.gender == 'prefer' ? 'selected' : ''}>Prefer not
							to say</option>
					</select>
				</div>

				<div class="form-group full-width">
					<label for="email">Email:</label> <input type="email" id="email"
						name="email" value="${user.email}" placeholder="Email">
				</div>

				<div class="form-group">
					<label for="dob">Date of Birth:</label> <input type="date" id="dob"
						name="dateOfBirth"
						value="${user.dateOfBirth != null ? user.dateOfBirth.toString() : ''}">

				</div>


				<div class="form-group">
					<label for="contact">Contact:</label> <input type="tel"
						id="contact" name="contact" value="${user.contact}"
						placeholder="Contact">
				</div>
				<div class="form-group">
					<label for="userImage">Upload new Image:</label> <input type="file"
						id="profileImageInput" name="userImage" accept="image/*">
				</div>

				<div class="buttons">
					<button type="button" class="btn btn-cancel"
						onclick="window.location.href='${contextPath}/home';">Cancel</button>
					<button type="submit" class="btn btn-save">Save Changes</button>
				</div>
			</form>
		</div>
	</div>
	<jsp:include page="footer.jsp" />
</body>

</html>
