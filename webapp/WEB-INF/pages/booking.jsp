<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>MUSEMO - Book Your Visit</title>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${contextPath}/css/booking.css" />
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/header.css" />
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/footer.css" />

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body style="background-image: url('${contextPath}/resources/images/booking_background.jpg');">
	<jsp:include page="header.jsp" />

	<!-- Booking Section -->
	<section class="booking-section">
		<div class="booking-container">
			<div class="booking-header">
				<h2>Book your Visit Now</h2>
			</div>
			<div class="booking-form">
				<form id="visit-booking-form">
					<div class="form-row">
						<div class="form-column">
							<div class="form-group">
								<label for="date">Select Date of Visit</label>
								<div class="select-wrapper">
									<select id="date" class="form-control" required>
										<option value="" disabled selected></option>
										<option value="2025-04-30">April 30, 2025</option>
										<option value="2025-05-01">May 1, 2025</option>
										<option value="2025-05-02">May 2, 2025</option>
										<option value="2025-05-03">May 3, 2025</option>
										<option value="2025-05-04">May 4, 2025</option>
									</select>
								</div>
							</div>

							<div class="form-group">
								<label for="time">Select Preferred time</label>
								<div class="select-wrapper">
									<select id="time" class="form-control" required>
										<option value="" disabled selected></option>
										<option value="10:00">10:00 AM</option>
										<option value="11:00">11:00 AM</option>
										<option value="12:00">12:00 PM</option>
										<option value="13:00">1:00 PM</option>
										<option value="14:00">2:00 PM</option>
										<option value="15:00">3:00 PM</option>
										<option value="16:00">4:00 PM</option>
									</select>
								</div>
							</div>

							<div class="form-group">
								<label for="exhibition">Select Exhibition</label>
								<div class="select-wrapper">
									<select id="exhibition" class="form-control" required>
										<option value="" disabled selected></option>
										<option value="renaissance">Renaissance Masters</option>
										<option value="modern">Modern Expressions</option>
										<option value="ancient">Ancient Civilizations</option>
										<option value="digital">Digital Frontiers</option>
										<option value="all">All Exhibitions</option>
									</select>
								</div>
							</div>

							<div class="form-group">
								<label for="username">Username:</label> <input type="text"
									id="username" class="form-control" placeholder="Enter Username"
									required>
							</div>
						</div>

						<div class="form-column">
							<div class="form-group">
								<label>Ticket Selection</label>
								<div class="radio-group">
									<div class="radio-option">
										<input type="radio" id="general" name="ticket-type"
											value="general" checked> <label for="general">General
											Ticket: Rs 200</label>
									</div>
									<div class="radio-option">
										<input type="radio" id="senior" name="ticket-type"
											value="senior"> <label for="senior">Senior
											Ticket(65+): Rs 150</label>
									</div>
									<div class="radio-option">
										<input type="radio" id="student" name="ticket-type"
											value="student"> <label for="student">Student
											Ticket: Rs 100</label>
									</div>
									<div class="radio-option">
										<input type="radio" id="children" name="ticket-type"
											value="children"> <label for="children">Children
											Ticket: Free</label>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="btn-container">
						<button type="submit" class="btn">Book</button>
					</div>
				</form>
			</div>
		</div>
	</section>

	<!-- Footer -->
	<jsp:include page="footer.jsp" />

	<script>
		document.getElementById('visit-booking-form').addEventListener(
				'submit', function(e) {
					e.preventDefault();
					alert('Booking submitted successfully!');
				});
	</script>
</body>
</html>