<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MUSEMO - Admin Profile</title>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${contextPath}/css/adminProfile.css" />
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
	rel="stylesheet">
</head>
<body>
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
		</a> <a href="${contextPath}/artifactManagement" class="menu-item"> <i
			class="fas fa-archive"></i> <span>Artifact Management</span>
		</a> <a href="${contextPath}/exhibitionManagement" class="menu-item">
			<i class="fas fa-calendar-alt"></i> <span>Exhibitions And
				Bookings</span>
		</a> <a href="${contextPath}/adminProfile" class="menu-item active"> <i
			class="fas fa-user-cog"></i> <span>Admin Profile</span>
		</a>
	</div>

	<!-- Main Content Area -->
	<div class="main-content">
		<div class="profile-wrapper">
			<h1 class="page-title">Admin Profile</h1>
			<div class="profile-container">
				<!-- Profile Sidebar -->
				<div class="profile-sidebar">
					<div class="profile-avatar">
						<img src="${contextPath}/resources/imagesuser/founder.jpg"
							alt="Admin Avatar" class="avatar-image" id="avatarPreview">
					</div>

				</div>

				<!-- Profile Form -->
				<div class="profile-details">
					<h2 class="profile-header">Admin Information</h2>

					<form id="admin-profile-form">
						<div class="form-group">
							<label for="username" class="form-label">Username:</label> <input
								type="text" id="username" class="form-input"
								placeholder="Username" value="admin123">
						</div>

						<div class="form-group">
							<label for="password" class="form-label">Password:</label> <input
								type="password" id="password" class="form-input"
								placeholder="Password" value="********">
						</div>

						<div class="form-actions">
							<button type="button" class="btn btn-cancel" id="cancel-button">Cancel</button>
							<button type="submit" class="btn btn-save">Save Changes</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<script>
		// Form submission handler
		document.getElementById('admin-profile-form').addEventListener(
				'submit', function(e) {
					e.preventDefault();
					const username = document.getElementById('username').value;
					const password = document.getElementById('password').value;

					// Validate form
					if (!username || !password) {
						alert('Please fill in all fields');
						return;
					}

					// In a real application, this would send the data to the server
					alert('Profile updated successfully!');
				});

		// Cancel button handler
		document.getElementById('cancel-button').addEventListener('click',
				function() {
					// In a real application, this would redirect or reset the form
					alert('Changes cancelled');
					// Optionally reset the form
					document.getElementById('admin-profile-form').reset();
				});
	</script>
</body>
</html>