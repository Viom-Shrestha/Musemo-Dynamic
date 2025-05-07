package com.musemo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import com.musemo.model.BookingModel;
import com.musemo.model.ExhibitionModel;
import com.musemo.service.BookingService;
import com.musemo.service.ExhibitionService;

/**
 * @author Viom Shrestha
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/booking" })
public class BookingController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ExhibitionService exhibitionService = new ExhibitionService();
	private BookingService bookingService = new BookingService();

	// Handle GET request to load the booking page with exhibitions
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Fetch all exhibitions to display in the drop-down
		List<ExhibitionModel> exhibitions = exhibitionService.getAllExhibitions();
		request.setAttribute("exhibitions", exhibitions);

		// Forward to the booking page
		request.getRequestDispatcher("/WEB-INF/pages/booking.jsp").forward(request, response);
	}

	// Handle POST request for booking submission
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Get the form data
		String exhibitionIdStr = request.getParameter("exhibition");
		String username = request.getParameter("username");
		String ticket = request.getParameter("ticket-type");
		String timeStr = request.getParameter("time"); // Capture the time as a String

		// Parse the time into a java.sql.Time object
		Time time = Time.valueOf(timeStr + ":00"); // Add seconds as ":00" (e.g., "09:00" becomes "09:00:00")

		// Fetch the selected exhibition
		int exhibitionId = Integer.parseInt(exhibitionIdStr);
		// Set current date for bookingDate
		Date currentDate = new Date(System.currentTimeMillis()); // Get current date

		// Proceed to save the booking
		BookingModel booking = new BookingModel();
		booking.setExhibitionId(exhibitionId);
		booking.setUsername(username);
		booking.setTicket(ticket);
		booking.setBookingDate(currentDate); // Set the current date for booking
		booking.setBookingTime(time); // Set the preferred time

		boolean success = bookingService.saveBooking(booking);

		if (success) {
			request.setAttribute("success", "Booking successful!");
		} else {
			request.setAttribute("error", "Booking failed. Please try again.");
		}

		request.getRequestDispatcher("/WEB-INF/pages/booking.jsp").forward(request, response);
	}
}
