<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MUSEMO - Museum of Art And Culture</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/about.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/header.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/footer.css" />
</head>
<body>
	<jsp:include page="header.jsp" />
	<!-- Hero Section -->
	<section class="hero" id="home">
		<div class="container">
			<div class="hero-content">
				<h1>About the Museum</h1>
				<p>Preserving the past, inspiring the future through art and
					history</p>
				<a href="${pageContext.request.contextPath}/pages/exhibition.jsp" class="cta-button">Explore Exhibitions</a>
			</div>
		</div>
	</section>

	<!-- About Section -->
	<section class="about" id="about">
		<div class="container">
			<h2 class="section-title">Our Story</h2>
			<div class="about-grid">
				<div class="about-card">
					<div class="about-card-content">
						<h3>Our History</h3>
						<p>Founded in 1985, MUSEMO has been a cornerstone of cultural
							preservation and education for over four decades. What began as a
							small gallery has evolved into a world-class institution
							dedicated to celebrating art, history, and innovation.</p>
					</div>
				</div>
				<div class="about-card">
					<div class="about-card-content">
						<h3>Our Collection</h3>
						<p>With over 25,000 artifacts spanning five millennia, our
							diverse collection represents cultural achievements from around
							the globe. From ancient civilizations to contemporary
							masterpieces, we offer a comprehensive view of human creativity.</p>
					</div>
				</div>
				<div class="about-card">
					<div class="about-card-content">
						<h3>Education And Research</h3>
						<p>MUSEMO is committed to advancing knowledge through
							innovative educational programs, scholarships, and research
							initiatives. We collaborate with academic institutions worldwide
							to deepen understanding of our shared cultural heritage.</p>
					</div>
				</div>
			</div>
		</div>
	</section>

	<!-- Founder Section -->
	<section class="founder">
		<div class="container">
			<h2 class="section-title">Message from Founder</h2>
			<div class="founder-container">
				<div class="founder-image">
					<img src="${pageContext.request.contextPath}/resources/images/system/founder.jpg" alt="Museum Founder">
				</div>
				<div class="founder-content">
					<h3>Museo Mussolini</h3>
					<h4>Museum Director</h4>
					<p>Welcome to MUSEMO, where we believe in the transformative
						power of art and history. Our museum stands as a testament to
						human creativity, innovation, and cultural expression throughout
						the ages. We've created a space where visitors can connect with
						the past while being inspired to shape the future.</p>
					<p>Our mission extends beyond preservation—we aim to make
						history accessible, engaging, and relevant to contemporary
						audiences. Through thoughtful curation, educational programming,
						and community outreach, we strive to foster a deeper understanding
						of our collective heritage.</p>
					<p>I invite you to explore our galleries, participate in our
						events, and join us in celebrating the remarkable achievements of
						human civilization. Together, we can ensure that the wisdom of the
						past continues to illuminate our path forward.</p>
				</div>
			</div>
		</div>
	</section>

	<!-- Vision Mission Section -->
	<section class="vision-mission">
		<div class="container">
			<h2 class="section-title" style="color: white;">Our Vision And
				Mission</h2>
			<div class="vm-container">
				<div class="vm-card">
					<h3>OUR MISSION</h3>
					<p>To collect, preserve, study, exhibit, and interpret
						outstanding works of human creativity for the inspiration and
						education of the public. We aim to foster critical thinking,
						stimulate imagination, and advance appreciation for diverse
						cultural expressions throughout history.</p>
				</div>
				<div class="vm-card">
					<h3>OUR VISION</h3>
					<p>To be a leading cultural institution that bridges past and
						present, connects diverse communities, and inspires future
						generations through exceptional art experiences. We envision a
						world where cultural understanding promotes harmony, innovation,
						and social progress.</p>
				</div>
			</div>
		</div>
	</section>

	<!-- Purpose Section -->
	<section class="purpose">
		<div class="container">
			<div class="purpose-content">
				<h3>Our Purpose</h3>
				<p>MUSEMO exists to safeguard our cultural heritage while making
					it accessible and meaningful to contemporary audiences. We believe
					that understanding our shared history fosters empathy, critical
					thinking, and innovation. Through our collections, exhibitions, and
					programs, we aim to inspire curiosity, spark dialogue, and enrich
					lives through the transformative power of art and historical
					knowledge.</p>
			</div>
		</div>
	</section>
	<jsp:include page="footer.jsp" />
</body>
</html>