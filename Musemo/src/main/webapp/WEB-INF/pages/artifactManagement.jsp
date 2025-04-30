<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>MUSEMO - Artifact Management</title>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
	rel="stylesheet">
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${contextPath}/css/artifactManagement.css" />
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
		</a> <a href="${contextPath}/userManagement" class="menu-item"> <i
			class="fas fa-users"></i> <span>User Management</span>
		</a> <a href="${contextPath}/artifactManagement" class="menu-item active">
			<i class="fas fa-archive"></i> <span>Artifact Management</span>
		</a> <a href="${contextPath}/exhibitionManagement" class="menu-item"> <i
			class="fas fa-calendar-alt"></i> <span>Exhibitions And
				Bookings</span>
		</a> <a href="${contextPath}/adminProfile" class="menu-item"> <i
			class="fas fa-user-cog"></i> <span>Admin Profile</span>
		</a>
	</div>

	<!-- Main Content Area -->
	<div class="main-content">
		<h1 class="page-title">Artifact Management</h1>

		<!-- Search and Filter Controls -->
		<div class="search-bar-container">
			<div class="search-bar">
				<i class="fa-solid fa-search search-icon"></i> <input type="text"
					class="search-input" placeholder="Search" id="artifact-search">
			</div>

			<div class="filter-controls">
				<div style="position: relative;">
					<select class="search-by" id="search-by">
						<option>Search By</option>
						<option>Name</option>
						<option>Type</option>
						<option>Origin</option>
						<option>Time Period</option>
					</select> <i class="fa-solid fa-chevron-down dropdown-icon"></i>
				</div>

				<button class="sort-button" aria-label="Sort ascending">
					<i class="fa-solid fa-arrow-up"></i>
				</button>

				<button class="sort-button" aria-label="Sort descending">
					<i class="fa-solid fa-arrow-down"></i>
				</button>
			</div>
		</div>

		<!-- Artifacts Table -->
		<table class="artifact-table" id="artifacts-table">
			<thead>
				<tr>
					<th>Image</th>
					<th>Artifact Id</th>
					<th>Name</th>
					<th>Type</th>
					<th>Creator</th>
					<th>Time Period</th>
					<th>Origin</th>
					<th>Condition</th>
					<th>Description</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
				<tr>

					<td class="image-cell"><img
						src="${contextPath}/resources/images/artifact/MonaLisa.jpg"
						alt="User Image" class="user-image"></td>
					<td>A0123</td>
					<td>Ancient Greek Vase</td>
					<td>Ceramic</td>
					<td>A</td>
					<td>5th Century BCE</td>
					<td>Greece</td>
					<td>Good</td>
					<td>The Moan Lisa is a half-length portrait painting by the
						Italian artist Leonardo da Vinci. Considered an archetypal
						masterpiece of the Italian Renaissance.</td>
					<td>
						<div class="action-buttons">
							<button class="edit-button" aria-label="Edit">
								<i class="fa-solid fa-pen-to-square"></i>
							</button>
							<button class="delete-button" aria-label="Delete">
								<i class="fa-solid fa-trash"></i>
							</button>
						</div>
					</td>
				</tr>
				<tr>

					<td class="image-cell"><img
						src="${contextPath}/resources/images/artifact/WingedVictory.jpg"
						alt="User Image" class="user-image"></td>
					<td>A0041</td>
					<td>Ming Dynasty Vase</td>
					<td>Ceramic</td>
					<td>B</td>
					<td>15th Century</td>
					<td>China</td>
					<td>Bad</td>
					<td>The Thinker (French: Le Penseur), by Auguste Rodin, is a
						bronze sculpture depicting a nude male figure of heroic size,
						seated on a large rock, leaning forward, right elbow placed upon
						the left thigh, back of the right hand supporting the chin in a
						posture evocative of deep thought and contemplation.</td>
					<td>
						<div class="action-buttons">
							<button class="edit-button" aria-label="Edit">
								<i class="fa-solid fa-pen-to-square"></i>
							</button>
							<button class="delete-button" aria-label="Delete">
								<i class="fa-solid fa-trash"></i>
							</button>
						</div>
					</td>
				</tr>
				<tr>

					<td class="image-cell"><img
						src="${contextPath}/resources/images/artifact/TheThinker.jpg"
						alt="User Image" class="user-image"></td>
					<td>A1234</td>
					<td>Ancient Vase</td>
					<td>Pottery</td>
					<td>C</td>
					<td>500 BCE</td>
					<td>Unknown</td>
					<td>Bad</td>
					<td>The Rosetta Stoned is a stele of granodiorite inscribed
						with three versions of a decree issued in 196 BC during the
						Ptolemaic dynasty of Egypt, on behalf of King Ptolemy V Epiphanes.</td>
					<td>
						<div class="action-buttons">
							<button class="edit-button" aria-label="Edit">
								<i class="fa-solid fa-pen-to-square"></i>
							</button>
							<button class="delete-button" aria-label="Delete">
								<i class="fa-solid fa-trash"></i>
							</button>
						</div>
					</td>
				</tr>
			</tbody>
		</table>

		<!-- Add New Artifact Button -->
		<button class="add-artifact-button">Add New Artifact</button>
	</div>

	<script>
        // Add basic search functionality
        document.getElementById('artifact-search').addEventListener('input', function(e) {
            const searchTerm = e.target.value.toLowerCase();
            const rows = document.querySelectorAll('#artifacts-table tbody tr');
            
            rows.forEach(row => {
                let found = false;
                const cells = row.querySelectorAll('td');
                
                cells.forEach(cell => {
                    if (cell.textContent.toLowerCase().includes(searchTerm)) {
                        found = true;
                    }
                });
                
                row.style.display = found ? '' : 'none';
            });
        });

        // Add delete functionality
        const deleteButtons = document.querySelectorAll('.delete-button');
        deleteButtons.forEach(button => {
            button.addEventListener('click', function() {
                if (confirm('Are you sure you want to delete this artifact?')) {
                    const row = this.closest('tr');
                    row.remove();
                }
            });
        });

        // Add edit functionality (placeholder)
        const editButtons = document.querySelectorAll('.edit-button');
        editButtons.forEach(button => {
            button.addEventListener('click', function() {
                const row = this.closest('tr');
                const artifactId = row.querySelector('td:nth-child(2)').textContent;
                alert(`Edit artifact ${artifactId}`);
                // In a real application, this would open an edit form or modal
            });
        });

        // Add New Artifact button functionality
        document.querySelector('.add-artifact-button').addEventListener('click', function() {
            alert('Add new artifact form would open here');
            // In a real application, this would open a form or modal for adding a new artifact
        });
    </script>
</body>
</html>