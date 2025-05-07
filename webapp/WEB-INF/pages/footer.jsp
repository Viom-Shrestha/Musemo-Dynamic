<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<div class="footer-content">
	<div class="footer-column">
		<h3>Quick Links</h3>
		<ul>
			<li><a href="${contextPath}/home">Home</a></li>
			<li><a href="${contextPath}/exhibition">Exhibitions</a></li>
			<li><a href="${contextPath}/artifact">Artifacts</a></li>
			<li><a href="${contextPath}/about">About</a></li>
			<li><a href="${contextPath}/contact">Contact</a></li>
		</ul>
	</div>

	<div class="footer-column contact-us">
		<h3>Contact Us</h3>
		<ul>
			<li><i class="fas fa-map-marker-alt"></i> 123 Museum Avenue,
				City</li>
			<li><i class="fas fa-phone"></i> (555) 123-4567</li>
			<li><i class="fas fa-envelope"></i> info@musemo.org</li>
		</ul>

		<div class="social-links">
			<a href="https://www.facebook.com/viom.shrestha"><i
				class="fab fa-facebook-f"></i></a> <a href="https://x.com/"><i
				class="fab fa-twitter"></i></a> <a
				href="https://www.instagram.com/invisi_tea_/"><i
				class="fab fa-instagram"></i></a> <a
				href="https://www.youtube.com/watch?v=wn0IyvGBeUI"><i
				class="fab fa-youtube"></i></a> <a href="https://www.pinterest.com/"><i
				class="fab fa-pinterest"></i></a>
		</div>
	</div>
</div>

<div class="copyright">
	<p>&copy; 2025 Musemo: Museum of Art and Culture. All rights
		reserved.</p>
</div>
</body>
</html>
