package com.musemo.service;

import com.musemo.config.DbConfig;
import com.musemo.model.BookingModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Service class responsible for handling booking operations, specifically
 * saving new bookings to the database.
 * 
 * @author 23048612 Viom Shrestha
 */
public class BookingService {
	private Connection dbConn;

	/**
	 * Constructs a BookingService, establishing a connection to the database.
	 */
	public BookingService() {
		try {
			dbConn = DbConfig.getDbConnection();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saves a new booking to the database.
	 *
	 * @param booking The BookingModel object containing the booking details to be
	 *                saved.
	 * @return True if the booking was successfully saved, false otherwise.
	 */
	public boolean saveBooking(BookingModel booking) {
		String sql = "INSERT INTO booking (exhibitionId, username, bookingDate, bookingTime, ticket) VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
			int exhibitionId = booking.getExhibitionId();
			ps.setInt(1, exhibitionId);
			ps.setString(2, booking.getUsername());

			// Convert java.util.Date to java.sql.Date for database compatibility
			java.sql.Date bookingDate = new java.sql.Date(booking.getBookingDate().getTime());
			ps.setDate(3, bookingDate);

			// Handle potential null booking time
			if (booking.getBookingTime() != null) {
				ps.setTime(4, booking.getBookingTime());
			} else {
				ps.setNull(4, java.sql.Types.TIME);
			}
			ps.setString(5, booking.getTicket());

			// Execute the insert statement and check the number of rows affected
			int rowsAffected = ps.executeUpdate();
			System.out.println("Booking Saved - Rows affected: " + rowsAffected);
			return rowsAffected > 0; // Return true if at least one row was inserted
		} catch (SQLException e) {
			e.printStackTrace(); 
		}
		return false; // Return false in case of an exception or if no rows were affected
	}
}