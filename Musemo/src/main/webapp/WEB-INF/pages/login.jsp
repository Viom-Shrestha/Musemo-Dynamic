<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Musemo Art and History Museum</title>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/login.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
</head>
<body
	style="background-image: url('${contextPath}/resources/images/login_bg.jpg');">
	<div class="login-container">
		<div class="sidebar"
			style="background-image: url('${contextPath}/resources/images/login_sidebar.jpg');">
			<h1 class="museum-title">Musemo Art and History Museum</h1>
			<p class="museum-desc">Experience centuries of artistic
				brilliance and historical treasures. Our collections span from
				ancient civilizations to contemporary masterpieces, offering a
				journey through human creativity and innovation.</p>
			<p class="museum-desc">Sign in to plan your visit</p>
		</div>

		<div class="login-form">
			<form action="${contextPath}/login" method="post">
				<div class="login-header">
					<div class="nav-links">
						<a href="${contextPath}/login" class="nav-link active">Login</a> <a
							href="${contextPath}/register" class="nav-link">Register</a>
					</div>
					<div class="museum-logo"></div>
				</div>

				<div class="form-group">
					<label class="form-label">User-Name:</label> <input type="text"
						name="username" class="form-input"
						placeholder="Enter your username">
				</div>

				<div class="form-group" style="position: relative;">
					<label class="form-label">Password:</label> <input type="password"
						id="password" name="password" class="form-input"
						placeholder="Enter your password">
					<button type="button" class="password-toggle"
						onclick="togglePassword()"
						style="position: absolute; right: 10px; top: 40px; background: none; border: none; cursor: pointer;">
						<i id="toggleIcon" class="fa-solid fa-eye"></i>
					</button>
				</div>


				<button type="submit" class="login-btn">Login</button>

				<!-- Show messages below button -->
				<c:if test="${not empty error}">
					<p class="form-message error-message">${error}</p>
				</c:if>
				<c:if test="${not empty success}">
					<p class="form-message success-message">${success}</p>
				</c:if>
			</form>
		</div>

	</div>
</body>

<script>
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

</html>