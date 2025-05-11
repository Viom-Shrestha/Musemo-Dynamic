<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Dashboard</title>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<link rel="stylesheet" href="${contextPath}/css/dashboard.css">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
	rel="stylesheet">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.7.0/chart.min.js"></script>
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

		<a href="${contextPath}/dashboard" class="menu-item active"> <i
			class="fas fa-th-large"></i> <span>Dashboard</span>
		</a> <a href="${contextPath}/userManagement" class="menu-item"> <i
			class="fas fa-users"></i> <span>User Management</span>
		</a> <a href="${contextPath}/artifactManagement" class="menu-item"> <i
			class="fas fa-archive"></i> <span>Artifact Management</span>
		</a> <a href="${contextPath}/exhibitionManagement" class="menu-item">
			<i class="fas fa-calendar-alt"></i> <span>Exhibitions</span>
		</a> <a href="${contextPath}/adminProfile" class="menu-item"> <i
			class="fas fa-user-cog"></i> <span>Admin Profile</span>
		</a>
	</div>

	<div class="main-content">
		<div class="header">
			<div class="greeting">
				<h1>Welcome back, Admin</h1>
				<p class="subtitle">Here's what's happening at your museum today</p>
			</div>
			<form action="${contextPath}/logout" method="post">
				<button class="logout-btn">
					<i class="fas fa-sign-out-alt"></i> Logout
				</button>
			</form>
		</div>

		<div class="stats-container">
			<div class="stat-card visitors-card">
				<div class="stat-header">Total Visitors</div>
				<div class="stat-value">${userCount}</div>
			</div>
			<div class="stat-card booking-card">
				<div class="stat-header">Total Bookings</div>
				<div class="stat-value">${bookingCount}</div>
			</div>
			<div class="stat-card exhibitions-card">
				<div class="stat-header">Active Exhibitions</div>
				<div class="stat-value">${activeExhibitionCount}</div>
			</div>
			<div class="stat-card artifacts-card">
				<div class="stat-header">Total Artifacts</div>
				<div class="stat-value">${artifactCount}</div>
			</div>
		</div>

		<div class="charts-row">
			<div class="chart-container">
				<div class="chart-header">
					<span>Artifact Distribution</span>

				</div>
				<canvas id="artifactChart"></canvas>
			</div>

			<div class="recent-activity">
				<div class="chart-header">
					<span>Recent Activity</span>
				</div>
				<ul class="activity-list">
					<li class="activity-item">
						<div class="activity-content">
							<div class="activity-title">New artifact "Ancient Vase"
								added to the collection</div>
							<div class="activity-time">Today, 10:30 AM</div>
						</div>
					</li>
					<li class="activity-item">
						<div class="activity-content">
							<div class="activity-title">Exhibition "Modern Art"
								schedule updated</div>
							<div class="activity-time">Yesterday, 2:15 PM</div>
						</div>
					</li>
					<li class="activity-item">
						<div class="activity-content">
							<div class="activity-title">182 visitors attended "Ancient
								Egypt" exhibition</div>
							<div class="activity-time">Yesterday, 5:00 PM</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
		<div class="data-section">
				<h2>User Booking Details</h2>
				<table>
					<thead>
						<tr>
							<th>Username</th>
							<th>Full Name</th>
							<th>Exhibition</th>
							<th>Date</th>
							<th>Time</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="detail" items="${userBookingDetails}">
							<tr>
								<td>${detail.username}</td>
								<td>${detail.fullName}</td>
								<td>${detail.exhibitionTitle}</td>
								<td>${detail.bookingDate}</td>
								<td>${detail.bookingTime}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

			<div class="data-section">
				<h2>Exhibitions and Their Artifacts</h2>
				<table>
					<thead>
						<tr>
							<th>Exhibition</th>
							<th>Artifact</th>
							<th>Type</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="detail" items="${exhibitionArtifactDetails}">
							<tr>
								<td>${detail.exhibitionTitle}</td>
								<td>${detail.artifactName}</td>
								<td>${detail.artifactType}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

			<div class="data-section">
				<h2>Top Booked Exhibitions</h2>
				<table>
					<thead>
						<tr>
							<th>Exhibition</th>
							<th>Bookings</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${mostBookedExhibitions}">
							<tr>
								<td>${item.exhibitionTitle}</td>
								<td>${item.totalBookings}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
	</div>

	<script>
	document.addEventListener('DOMContentLoaded', function () {
		const artifactDistributionData = {
			<c:forEach var="entry" items="${artifactDistribution}" varStatus="loop">
				"${entry.key}": ${entry.value}<c:if test="${!loop.last}">,</c:if>
			</c:forEach>
		};

		const labels = Object.keys(artifactDistributionData);
		const counts = Object.values(artifactDistributionData);

		const artifactCtx = document.getElementById('artifactChart').getContext('2d');
		const artifactChart = new Chart(artifactCtx, {
			type: 'pie',
			data: {
				labels: labels,
				datasets: [{
					data: counts,
					backgroundColor: [
						'rgba(26, 139, 160, 0.8)',
						'rgba(26, 139, 160, 0.6)',
						'rgba(26, 139, 160, 0.4)',
						'rgba(26, 139, 160, 0.2)'
					],
					borderColor: 'white',
					borderWidth: 2
				}]
			},
			options: {
				responsive: true,
				maintainAspectRatio: false,
				plugins: {
					legend: {
						position: 'bottom'
					}
				}
			}
		});
	});
	
    console.log("=== Debugging Session and Cookie ===");

    // Session attribute (set on login)
    const sessionUsername = "${sessionScope.username}";
    if (sessionUsername) {
        console.log("Session Username:", sessionUsername);
    } else {
        console.log("Session Username: Not set");
    }

    // Cookie for role
    const cookies = document.cookie.split(';');
    let role = null;
    cookies.forEach(cookie => {
        const [name, value] = cookie.trim().split('=');
        if (name === "role") {
            role = value;
        }
    });

    if (role) {
        console.log("Cookie Role:", role);
    } else {
        console.log("Cookie Role: Not set");
    }

    console.log("All Cookies:", document.cookie);
	</script>

</body>
</html>