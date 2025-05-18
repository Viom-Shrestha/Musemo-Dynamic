<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Register - Musemo Art and History Museum</title>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/register.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">

</head>

<body
	style="background-image: url('${contextPath}/resources/images/login_bg.jpg');">
	<div class="form-messages">
		<c:if test="${not empty error}">
			<p class="error-message">${error}</p>
		</c:if>
		<c:if test="${not empty success}">
			<p class="success-message">${success}</p>
		</c:if>
	</div>
	<div class="login-container">
		<div class="sidebar"
			style="background-image: url('${contextPath}/resources/images/register_sidebar.jpg');">

			<h1 class="museum-title">Musemo Art and History Museum</h1>
			<p class="museum-desc">Join our community of art enthusiasts and
				history buffs. Create an account to receive updates about new
				exhibits, special events, and member-exclusive content.</p>
			<p class="museum-desc">Your journey through art and history
				begins with a simple registration.</p>
		</div>

		<div class="register-form">
			<div class="login-header">
				<div class="nav-links">
					<a href="${contextPath}/login" class="nav-link">Login</a> <a
						href="${contextPath}/register" class="nav-link active">Register</a>
				</div>
			</div>

			<form id="registerForm" action="${contextPath}/register"
				method="post" enctype="multipart/form-data">

				<div class="form-row">
					<div class="form-group">
						<label class="form-label" for="username">Username</label> <input
							type="text" id="username" name="username" class="form-input"
							placeholder="Choose a username" value="${username}" required>

					</div>
					<div class="form-group">
						<label class="form-label" for="fullName">Full Name</label> <input
							type="text" id="fullName" name="fullName" class="form-input"
							placeholder="Enter your full name" value="${fullName}" required>
					</div>
				</div>

				<div class="form-row">
					<div class="form-group" style="position: relative;">
						<label class="form-label" for="password">Password</label> <input
							type="password" id="password" name="password" class="form-input"
							placeholder="Create a password" value="${password}" required>
						<button type="button" class="password-toggle"
							onclick="togglePassword()"
							style="position: absolute; right: 10px; top: 38px; background: none; border: none; cursor: pointer;">
							<i id="toggleIcon" class="fa-solid fa-eye"></i>
						</button>
					</div>

					<div class="form-group">
						<label class="form-label" for="gender">Gender</label> <select
							name="gender" id="gender" class="form-select">
							<option value="male"
								${gender == 'male' || gender == null ? 'selected' : ''}>Male</option>
							<option value="female" ${gender == 'female' ? 'selected' : ''}>Female</option>
							<option value="other" ${gender == 'other' ? 'selected' : ''}>Other</option>
						</select>
					</div>
				</div>

				<div class="form-group full-width">
					<label class="form-label" for="email">Email</label> <input
						type="email" id="email" name="email" class="form-input"
						placeholder="Enter your email address" value="${email}" required>
				</div>

				<div class="form-row">
					<div class="form-group">
						<label class="form-label" for="dob">Date of Birth</label> <input
							type="date" id="dob" name="dob" class="form-input" value="${dob}"
							required>
					</div>
					<div class="form-group">
						<label class="form-label" for="contact">Contact</label> <input
							type="tel" id="contact" name="contact" class="form-input"
							placeholder="Enter your phone number" value="${contact}" required>
					</div>

				</div>
				<div class="form-group full-width">
					<label class="form-label" for="userImage">Upload Profile
						Image</label> <input type="file" id="image" name="image"
						class="form-input">
				</div>

				<div class="form-checkbox">
					<input type="checkbox" id="privacy" class="checkbox-input">
					<label for="privacy" class="checkbox-label">I accept the
						Privacy Policy and Terms of Service</label>
				</div>

				<button type="submit" class="register-btn">Create an
					Account</button>
			</form>

		</div>

	</div>
	<script>
		// Form privacy checkbox validation
		document.getElementById("registerForm").onsubmit = function(e) {
			if (!document.getElementById("privacy").checked) {
				e.preventDefault();
				alert("You must accept the Privacy Policy and Terms of Service to continue.");
			}
		};

		// Toggle password show/hide
		function togglePassword() {
			const passwordField = document.getElementById("password");
			const toggleIcon = document.getElementById("toggleIcon");

			if (passwordField.type === "password") {
				passwordField.type = "text";
				toggleIcon.classList.remove('fa-eye');
				toggleIcon.classList.add('fa-eye-slash');
			} else {
				passwordField.type = "password";
				toggleIcon.classList.remove('fa-eye-slash');
				toggleIcon.classList.add('fa-eye');
			}
		}
	</script>

</body>
</html>