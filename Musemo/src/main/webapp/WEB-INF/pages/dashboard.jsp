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
				<img src="${contextPath}/resources/images/system/admin_logo.png"
					alt="Admin Logo" style="width: 40px; height: 40px;">
			</div>
			<div class="brand-name">MUSEMO Admin</div>
		</div>

		<div class="menu-item active">
			<i class="fas fa-th-large"></i> <span>Dashboard</span>
		</div>

		<div class="menu-item">
			<i class="fas fa-users"></i> <span>User Management</span>
		</div>

		<div class="menu-item">
			<i class="fas fa-archive"></i> <span>Artifact Management</span>
		</div>

		<div class="menu-item">
			<i class="fas fa-calendar-alt"></i> <span>Exhibitions</span>
		</div>
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
	</script>

</body>
</html>