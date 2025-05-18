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
<body
	style="background-image: url('${contextPath}/resources/images/booking_background.jpg');">
	<jsp:include page="header.jsp" />

	<!-- Booking Section -->
	<section class="booking-section">
		<div class="booking-container">
			<div class="booking-header">
				<h2>Book your Visit Now</h2>
			</div>
			<!-- Show messages below button -->
			<c:if test="${not empty error}">
				<p class="form-message error-message">${error}</p>
			</c:if>
			<c:if test="${not empty success}">
				<p class="form-message success-message">${success}</p>
			</c:if>
			<div class="booking-form">

				<form id="visit-booking-form" action="${contextPath}/booking"
					method="post">
					<div class="form-row">
						<div class="form-column">

							<!-- Exhibition selection -->
							<div class="form-group">
								<label for="exhibition">Select Exhibition</label> <select
									id="exhibition" class="form-control" name="exhibition" required>
									<c:forEach var="exhibition" items="${exhibitions}">
										<option value="${exhibition.exhibitionId}">${exhibition.exhibitionTitle}</option>
									</c:forEach>
								</select>
							</div>

							<!-- Time selection -->
							<div class="form-group">
								<label for="time">Select Preferred Time</label> <select
									id="time" class="form-control" name="time" required>
									<option value="09:00">9:00 AM</option>
									<option value="10:00">10:00 AM</option>
									<option value="11:00">11:00 AM</option>
									<option value="12:00">12:00 PM</option>
									<option value="13:00">1:00 PM</option>
									<option value="14:00">2:00 PM</option>
									<option value="15:00">3:00 PM</option>
									<option value="16:00">4:00 PM</option>
								</select>
							</div>

							<!-- Username -->
							<div class="form-group">
								<label for="username">Username:</label> 
								<input type="text" id="username" class="form-control" name="username" value="${username}" readonly>
							</div>
						</div>

						<div class="form-column">
							<!-- Ticket Selection -->
							<div class="form-group">
								<label>Ticket Selection</label>
								<div class="radio-group">
									<label><input type="radio" name="ticket-type"
										value="General Ticket" checked> General Ticket: Rs 200</label>
									<label><input type="radio" name="ticket-type"
										value="Senior Ticket"> Senior Ticket (65+): Rs 150</label> <label><input
										type="radio" name="ticket-type" value="Student Ticket">
										Student Ticket: Rs 100</label> <label><input type="radio"
										name="ticket-type" value="Children Ticket"> Children
										Ticket: Free</label>
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

</body>
</html>
