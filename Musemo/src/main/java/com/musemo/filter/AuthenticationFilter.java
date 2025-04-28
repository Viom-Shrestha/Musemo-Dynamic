package com.musemo.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.musemo.util.CookieUtil;
import com.musemo.util.SessionUtil;

@WebFilter(asyncSupported = true, urlPatterns = "/*")
public class AuthenticationFilter implements Filter {

	private static final String LOGIN = "/login";
	private static final String REGISTER = "/register";
	private static final String HOME = "/home";
	private static final String ROOT = "/";
	private static final String ABOUT = "/about";
	private static final String CONTACT = "/contact";
	private static final String EXHIBITION = "/exhibition";
	private static final String ARTIFACTS = "/artifacts";
	private static final String BOOKING = "/booking";
	private static final String USER_PROFILE = "/profile";

	private static final String DASHBOARD = "/dashboard";
	private static final String ADMIN_PROFILE = "/adminProfile";
	private static final String USER_MANAGEMENT = "/userManagement";
	private static final String ARTIFACT_MANAGEMENT = "/artifactManagement";
	private static final String EXHIBITION_MANAGEMENT = "/exhibitionManagement";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Initialization logic, if required
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String uri = req.getRequestURI();

		// Allow access to resources (CSS, images, etc.)
		if (uri.endsWith(".png") || uri.endsWith(".jpg") || uri.endsWith(".jpeg") || uri.endsWith(".gif")
				|| uri.endsWith(".css") || uri.endsWith(".js")) {
			chain.doFilter(request, response);
			return;
		}

		@SuppressWarnings("unused")
		boolean isLoggedIn = SessionUtil.getAttribute(req, "username") != null; // Changed to boolean
		String userRole = CookieUtil.getCookie(req, "role") != null ? CookieUtil.getCookie(req, "role").getValue()
				: null;

		if ("Admin".equals(userRole)) {
			// Admin is logged in
			if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER)) {
				res.sendRedirect(req.getContextPath() + DASHBOARD);
				return; // Added return
			} else if (uri.endsWith(DASHBOARD) || uri.endsWith(ADMIN_PROFILE) || uri.endsWith(USER_MANAGEMENT)
					|| uri.endsWith(ARTIFACT_MANAGEMENT) || uri.endsWith(HOME) || uri.endsWith(ROOT) || uri.endsWith(EXHIBITION_MANAGEMENT)) {
				// Allow access to all admin pages
				chain.doFilter(request, response);
				return; // Added return
			} else {
				res.sendRedirect(req.getContextPath() + DASHBOARD);
				return; // Added return
			}
		} else if ("User".equals(userRole)) {
			// User is logged in
			if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER)) {
				res.sendRedirect(req.getContextPath() + HOME);
				return; // Added return
			} else if (uri.endsWith(HOME) || uri.endsWith(ROOT) || uri.endsWith(ABOUT) || uri.endsWith(CONTACT)
					|| uri.endsWith(EXHIBITION) || uri.endsWith(ARTIFACTS) || uri.endsWith(BOOKING)
					|| uri.endsWith(USER_PROFILE)) {
				chain.doFilter(request, response);
				return; // Added return
			} else if (uri.startsWith("/admin")) {
				res.sendRedirect(req.getContextPath() + HOME);
				return; // Added return
			} else {
				res.sendRedirect(req.getContextPath() + HOME);
				return; // Added return
			}
		} else {
			// Not logged in
			if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER) || uri.endsWith(HOME) || uri.endsWith(ROOT)
					|| uri.endsWith(ABOUT) || uri.endsWith(CONTACT) || uri.endsWith(EXHIBITION)
					|| uri.endsWith(ARTIFACTS)) {
				chain.doFilter(request, response);
				return; // Added return
			} else {
				res.sendRedirect(req.getContextPath() + LOGIN);
				return; // Added return
			}
		}
	}

	@Override
	public void destroy() {
		// Cleanup logic, if required
	}
}
