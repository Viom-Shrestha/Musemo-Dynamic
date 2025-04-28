<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Contact Us - MUSEMO</title>
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/contact.css" />
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/header.css" />
<link rel="stylesheet" type="text/css"
	href="${contextPath}/css/footer.css" />

</head>
<body>
	<jsp:include page="header.jsp" />

	<section class="hero" style="background-image: url('${contextPath}/resources/images/contact_hero.jpg');">
		<div class="hero-content container">
			<h1>Contact Us</h1>
			<p>We're here to help with your questions, feedback, and museum
				inquiries</p>
		</div>
	</section>

	<div class="container">
		<h2 class="section-title">Get in Touch</h2>

		<div class="contact-grid">
			<div class="contact-card">
				<h3>Call Us</h3>
				<p>General Inquiries: (555) 123-4567</p>
				<p>Membership: (555) 123-4568</p>
				<p>Group Visits: (555) 123-4569</p>
				<p>Monday-Friday: 9am - 5pm</p>
			</div>

			<div class="contact-card">
				<h3>Email Us</h3>
				<p>General: info@arthistorymuseum.org</p>
				<p>Membership: members@arthistorymuseum.org</p>
				<p>Education: education@arthistorymuseum.org</p>
				<p>Press: media@arthistorymuseum.org</p>
			</div>

			<div class="contact-card">
				<h3>Visit Us</h3>
				<p>123 Culture Avenue</p>
				<p>Heritage District</p>
				<p>City, State 12345</p>
				<p>Open Tuesday-Sunday: 10am - 5pm</p>
			</div>
		</div>

		<div class="contact-form">
			<h2 class="form-header">Send Us a Message</h2>

			<form>
				<div class="form-row">
					<div class="form-group">
						<label for="name">Full Name:</label> <input type="text" id="name"
							placeholder="Enter your name">
					</div>

					<div class="form-group">
						<label for="email">Email:</label> <input type="email" id="email"
							placeholder="Email">
					</div>
				</div>

				<div class="form-group">
					<label for="contact">Contact:</label> <input type="text"
						id="contact" placeholder="Enter your contact information">
				</div>

				<div class="form-group">
					<label for="subject">Subject:</label> <select id="subject">
						<option value="">Select a subject</option>
						<option value="general">General Inquiry</option>
						<option value="tickets">Ticket Information</option>
						<option value="membership">Membership</option>
						<option value="group">Group Visit</option>
						<option value="feedback">Feedback</option>
						<option value="other">Other</option>
					</select>
				</div>

				<div class="form-group">
					<label for="message">Your Message:</label>
					<textarea id="message" placeholder="..."></textarea>
				</div>

				<button type="submit" class="btn">Send Message</button>
			</form>
		</div>

		<div class="faq">
			<h2 class="section-title">Frequently Asked Questions</h2>

			<div class="faq-item">
				<div class="faq-question">What are your visiting hours?</div>
				<div class="faq-answer">
					<p>The Art and History Museum is open Tuesday through Sunday from
						10:00 AM to 5:00 PM, with extended hours until 8:00 PM on
						Thursdays. We are closed on Mondays, as well as on New Year's Day,
						Thanksgiving Day, and Christmas Day.</p>
				</div>
			</div>

			<div class="faq-item">
				<div class="faq-question">How much do tickets cost?</div>
				<div class="faq-answer">
					<p>General admission is Rs.200 for adults, Rs.150 for seniors (65+),
						Rs.100 for students with valid ID, and free for children (6-17). Museum
						members and children under 6 enter for free. We also offer free
						admission on the first Sunday of each month.</p>
				</div>
			</div>

			<div class="faq-item">
				<div class="faq-question">Is photography allowed in the
					museum?</div>
				<div class="faq-answer">
					<p>Non-flash photography is permitted in the permanent
						collection galleries for personal, non-commercial use. Photography
						is not allowed in special exhibitions unless otherwise noted.
						Tripods, selfie sticks, and video recording equipment require
						prior permission.</p>
				</div>
			</div>

			<div class="faq-item">
				<div class="faq-question">Do you offer guided tours?</div>
				<div class="faq-answer">
					<p>Yes, we offer guided tours daily at 11:00 AM and 2:00 PM.
						These tours are included with your admission. Private and group
						tours can be arranged with at least two weeks' advance notice by
						contacting our education department.</p>
				</div>
			</div>

			<div class="faq-item">
				<div class="faq-question">How do I become a member?</div>
				<div class="faq-answer">
					<p>Membership can be purchased online, at the museum's front
						desk, or by calling our membership office at (555) 123-4568. We
						offer several membership levels starting at Rs.1000 for individuals
						and Rs.2000 for families. Benefits include unlimited free admission,
						discounts at the museum shop and café, and invitations to
						exclusive member events.</p>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="footer.jsp" />

	<script>
    document.querySelectorAll('.faq-question').forEach(question => {
        question.addEventListener('click', () => {
            const item = question.parentNode;
            item.classList.toggle('active');
            
            // Close others
            document.querySelectorAll('.faq-item').forEach(faqItem => {
                if (faqItem !== item) {
                    faqItem.classList.remove('active');
                }
            });
        });
    });

    </script>
</body>
</html>