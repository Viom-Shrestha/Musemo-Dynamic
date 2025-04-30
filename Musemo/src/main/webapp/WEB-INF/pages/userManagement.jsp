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
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
	rel="stylesheet">

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
		</a> <a href="${contextPath}/exhibitionManagement" class="menu-item"> <i
			class="fas fa-calendar-alt"></i> <span>Exhibitions And
				Bookings</span>
		</a> <a href="${contextPath}/adminProfile" class="menu-item"> <i
			class="fas fa-user-cog"></i> <span>Admin Profile</span>
		</a>
	</div>
	<!-- Main Content -->
	<main class="main-content">
		<div class="page-header">
			<h1>User Management</h1>
		</div>

		<!-- Search and Filter -->
		<div class="search-filter">
			<div class="search-input">
				<i class="fas fa-search"></i> <input type="text"
					placeholder="Search">
			</div>
			<div class="filter-select">
				<select>
					<option value="">Search By</option>
					<option value="username">Username</option>
					<option value="name">Name</option>
					<option value="email">Email</option>
					<option value="role">Role</option>
				</select>
			</div>
		</div>

		<!-- Pagination Controls -->
		<div class="pagination-controls">
			<button class="pagination-btn">
				<i class="fas fa-arrow-up"></i>
			</button>
			<button class="pagination-btn">
				<i class="fas fa-arrow-down"></i>
			</button>
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
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="image-cell"><img
						src="${contextPath}/resources/imagesuser/founder.jpg"
						alt="User Image" class="user-image"></td>
					<td>asdas</td>
					<td>Ramu Yadav</td>
					<td>****</td>
					<td>User</td>
					<td>Female</td>
					<td>a@gmail.com</td>
					<td>9853254511</td>
					<td>2005-01-05</td>
					<td>
						<div class="action-cell">
							<button class="action-btn delete-btn"
								onclick="showDeleteModal(1)">
								<i class="fas fa-trash"></i>
							</button>
						</div>
					</td>
				</tr>
				<tr>
					<td class="image-cell"><img
						src="${contextPath}/resources/imagesuser/Viom.jpg" alt="User Image"
						class="user-image"></td>

					<td>hahlpota</td>
					<td>Ming Pandey</td>
					<td>****</td>
					<td>User</td>
					<td>Male</td>
					<td>c@gmail.com</td>
					<td>9856433441</td>
					<td>2005-10-24</td>
					<td>
						<div class="action-cell">
							<button class="action-btn delete-btn"
								onclick="showDeleteModal(2)">
								<i class="fas fa-trash"></i>
							</button>
						</div>
					</td>
				</tr>
				<tr>
					<td class="image-cell"><img
						src="${contextPath}/resources/imagesuser/Alexa.png"
						alt="User Image" class="user-image"></td>

					<td>halatomat</td>
					<td>Alex Smith</td>
					<td>****</td>
					<td>User</td>
					<td>Female</td>
					<td>v@gmail.com</td>
					<td>9808688665</td>
					<td>2005-10-28</td>
					<td>
						<div class="action-cell">
							<button class="action-btn delete-btn"
								onclick="showDeleteModal(3)">
								<i class="fas fa-trash"></i>
							</button>
						</div>
					</td>
				</tr>
			</tbody>
		</table>

	</main>

	<!-- Delete Confirmation Modal -->
	<div class="modal" id="deleteModal">
		<div class="modal-content">
			<div class="modal-header">
				<h2 class="modal-title">Delete User</h2>
				<button class="close-modal" onclick="closeModal('deleteModal')">&times;</button>
			</div>
			<p>Are you sure you want to delete this user? This action cannot
				be undone.</p>
			<div class="modal-footer">
				<button class="modal-btn cancel-btn"
					onclick="closeModal('deleteModal')">Cancel</button>
				<button class="modal-btn confirm-btn" onclick="deleteUser()">Delete</button>
			</div>
		</div>
	</div>

	<script>
		// Variable to store the ID of the user being edited or deleted
		let currentUserId = null;

		// Show the add user modal
		function showAddModal() {
			alert("Add new user");
			// In a real application, you would show a modal with a form
		}

		// Show the delete confirmation modal
		function showDeleteModal(userId) {
			currentUserId = userId;

			document.getElementById('deleteModal').style.display = 'flex';
		}

		// Close any modal
		function closeModal(modalId) {
			document.getElementById(modalId).style.display = 'none';
		}

		// Delete the user
		function deleteUser() {
			// In a real application, you would make an API call to delete the user
			alert("User with ID: " + currentUserId + " has been deleted");
			closeModal('deleteModal');

			// In a real application, you would refresh the table or remove the row
		}

		// Close modal if clicking outside of it
		window.onclick = function(event) {
			const deleteModal = document.getElementById('deleteModal');
			if (event.target === deleteModal) {
				closeModal('deleteModal');
			}
		}
	</script>
</html>