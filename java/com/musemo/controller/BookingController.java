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
import com.musemo.service.ExhibitionManagementService;

/**
 * Controller for handling user booking operations for exhibitions. This includes
 * loading the booking page with available exhibitions and processing the
 * submission of a new booking.
 *
 *  @author 23048612 Viom Shrestha
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/booking" })
public class BookingController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ExhibitionManagementService exhibitionService = new ExhibitionManagementService();
	private BookingService bookingService = new BookingService();

	/**
	 * Handles GET requests to the /booking URL. It fetches all available
	 * exhibitions from the ExhibitionManagementService and makes them available
	 * to the booking.jsp view page for the user to select from.
	 *
	 * @param request  The HttpServletRequest object containing the client's request.
	 * @param response The HttpServletResponse object for sending the response to the client.
	 * @throws ServletException If a servlet-specific error occurs during processing.
	 * @throws IOException      If an I/O error occurs while handling the request.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Fetch a list of all exhibitions using the exhibition service
		List<ExhibitionModel> exhibitions = exhibitionService.getAllExhibitions();
		// Set the list of exhibitions as a request attribute so it can be accessed in the JSP
		request.setAttribute("exhibitions", exhibitions);

		// Forward the request to the booking.jsp page to display the booking form
		request.getRequestDispatcher("/WEB-INF/pages/booking.jsp").forward(request, response);
	}

	/**
	 * Handles POST requests to the /booking URL. This method processes the
	 * submission of a new booking from the user. It retrieves the selected
	 * exhibition ID, username, ticket type, and preferred time from the form
	 * data, creates a BookingModel object, sets the current date as the booking
	 * date, and then uses the BookingService to save the new booking. Finally,
	 * it sets a success or error message as a request attribute and redirects
	 * back to the booking page to display the feedback.
	 *
	 * @param request  The HttpServletRequest object containing the client's request.
	 * @param response The HttpServletResponse object for sending the response to the client.
	 * @throws ServletException If a servlet-specific error occurs during processing.
	 * @throws IOException      If an I/O error occurs while handling the request.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Retrieve the selected exhibition ID from the form
		String exhibitionIdStr = request.getParameter("exhibition");
		// Retrieve the username of the user making the booking
		String username = request.getParameter("username");
		// Retrieve the selected ticket type
		String ticket = request.getParameter("ticket-type");
		// Retrieve the preferred booking time as a String (e.g., "09:00")
		String timeStr = request.getParameter("time");

		// Parse the time string into a java.sql.Time object, adding seconds ":00"
		Time time = Time.valueOf(timeStr + ":00");

		// Convert the exhibition ID string to an integer
		int exhibitionId = Integer.parseInt(exhibitionIdStr);
		// Get the current date to set as the booking date
		Date currentDate = new Date(System.currentTimeMillis());
		// Create a new BookingModel object to store the booking details
		BookingModel booking = new BookingModel();
		booking.setExhibitionId(exhibitionId);
		booking.setUsername(username);
		booking.setTicket(ticket);
		booking.setBookingDate(currentDate);
		booking.setBookingTime(time);
		// Use the BookingService to save the new booking to the database
		boolean success = bookingService.saveBooking(booking);
		// Set a success or error message as a request attribute based on the booking outcome
		if (success) {
			request.setAttribute("success", "Booking successful!");
		} else {
			request.setAttribute("error", "Booking failed. Please try again.");
		}
		// Redirect back to the booking page to display the message and the exhibition list again
		doGet(request, response);
	}
}