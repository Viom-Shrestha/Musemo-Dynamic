package com.musemo.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.musemo.util.CookieUtil;

/**
 * Servlet filter responsible for handling user authentication and authorization
 * for different parts of the web application. It intercepts incoming requests
 * and checks for the user's role stored in a cookie to determine access
 * permissions.
 * 
 * @author 23048612 Viom Shrestha
 */
@WebFilter(asyncSupported = true, urlPatterns = "/*")
public class AuthenticationFilter implements Filter {

	/**
	 * This method is invoked for every request that matches the filter's URL
	 * patterns. It performs authentication and authorization checks.
	 *
	 * @param request  The ServletRequest object, cast to HttpServletRequest for
	 *                 HTTP-specific functionality.
	 * @param response The ServletResponse object, cast to HttpServletResponse for
	 *                 HTTP-specific functionality.
	 * @param chain    The FilterChain object, used to pass the request and response
	 *                 to the next filter in the chain or the target servlet.
	 * @throws IOException      If an I/O error occurs during the processing of the
	 *                          filter.
	 * @throws ServletException If a servlet-specific error occurs during the
	 *                          processing of the filter.
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		String path = uri.substring(contextPath.length());

		// Skip requests for static resources (images, CSS, JavaScript)
		if (path.endsWith(".png") || path.endsWith(".jpg") || path.endsWith(".jpeg") || path.endsWith(".gif")
				|| path.endsWith(".css") || path.endsWith(".js")) {
			chain.doFilter(request, response); // Allow access to static resources without authentication
			return;
		}

		// Retrieve the user's role from the "role" cookie
		String userRole = CookieUtil.getCookie(req, "role") != null ? CookieUtil.getCookie(req, "role").getValue()
				: null;

		// Define public routes that do not require authentication
		if (path.equals("/login") || path.equals("/register") || path.equals("/home") || path.equals("/")
				|| path.equals("/about") || path.equals("/contact") || path.equals("/exhibition")
				|| path.equals("/artifact") || path.equals("/exhibitionDetails") || path.equals("/artifactDetails")) {
			chain.doFilter(request, response); // Allow access to public routes
			return;
		}

		// Authorization for Admin role
		if ("Admin".equals(userRole)) {
			// Allow access to admin-specific paths
			if (path.equals("/dashboard") || path.equals("/adminProfile") || path.equals("/userManagement")
					|| path.equals("/artifactManagement") || path.equals("/exhibitionManagement")
					|| path.equals("/logout")) {
				chain.doFilter(request, response); // Allow admin access
			} else {
				// Redirect unauthorized admin requests to the dashboard
				res.sendRedirect(contextPath + "/dashboard");
			}
			return;
		}

		// Authorization for User role
		if ("User".equals(userRole)) {
			// Allow access to user-specific paths
			if (path.equals("/booking") || path.equals("/profile") || path.equals("/logout")) {
				chain.doFilter(request, response); // Allow user access
			} else if (path.startsWith("/admin")) {
				// Redirect users trying to access admin areas to the home page
				res.sendRedirect(contextPath + "/home");
			} else {
				// Redirect other unauthorized user requests to the home page
				res.sendRedirect(contextPath + "/home");
			}
			return;
		}

		// If no role is found in the cookie, consider the user unauthenticated and
		// redirect to the login page
		res.sendRedirect(contextPath + "/login");
	}
}