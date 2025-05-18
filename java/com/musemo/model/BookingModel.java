package com.musemo.model;

import java.sql.Time;
import java.util.Date;

/**
 * Represents a booking made by a user for an exhibition.
 * 
 * @author 23048612 Viom Shrestha
 */
public class BookingModel {
	private int bookingId;
	private int exhibitionId;
	private String username;
	private Date bookingDate;
	private Time bookingTime;
	private String ticket;

	/**
	 * Default constructor for the BookingModel.
	 */
	public BookingModel() {
		super();
	}

	/**
	 * Constructor for the BookingModel with all fields.
	 *
	 * @param bookingId    The unique identifier for the booking.
	 * @param exhibitionId The identifier of the exhibition the booking is for.
	 * @param username     The username of the user who made the booking.
	 * @param bookingDate  The date on which the booking was made.
	 * @param bookingTime  The time for which the booking is scheduled.
	 * @param ticket       Information about the ticket associated with the booking.
	 */
	public BookingModel(int bookingId, int exhibitionId, String username, Date bookingDate, Time bookingTime,
			String ticket) {
		super();
		this.bookingId = bookingId;
		this.exhibitionId = exhibitionId;
		this.username = username;
		this.bookingDate = bookingDate;
		this.bookingTime = bookingTime;
		this.ticket = ticket;
	}

	/**
	 * Gets the unique identifier of the booking.
	 *
	 * @return The booking ID.
	 */
	public int getBookingId() {
		return bookingId;
	}

	/**
	 * Sets the unique identifier of the booking.
	 *
	 * @param bookingId The booking ID to set.
	 */
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	/**
	 * Gets the identifier of the exhibition the booking is for.
	 *
	 * @return The exhibition ID.
	 */
	public int getExhibitionId() {
		return exhibitionId;
	}

	/**
	 * Sets the identifier of the exhibition the booking is for.
	 *
	 * @param exhibitionId The exhibition ID to set.
	 */
	public void setExhibitionId(int exhibitionId) {
		this.exhibitionId = exhibitionId;
	}

	/**
	 * Gets the username of the user who made the booking.
	 *
	 * @return The username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username of the user who made the booking.
	 *
	 * @param username The username to set.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the date on which the booking was made.
	 *
	 * @return The booking date.
	 */
	public Date getBookingDate() {
		return bookingDate;
	}

	/**
	 * Sets the date on which the booking was made.
	 *
	 * @param bookingDate The booking date to set.
	 */
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	/**
	 * Gets the time for which the booking is scheduled.
	 *
	 * @return The booking time.
	 */
	public Time getBookingTime() {
		return bookingTime;
	}

	/**
	 * Sets the time for which the booking is scheduled.
	 *
	 * @param bookingTime The booking time to set.
	 */
	public void setBookingTime(Time bookingTime) {
		this.bookingTime = bookingTime;
	}

	/**
	 * Gets information about the ticket associated with the booking.
	 *
	 * @return The ticket information.
	 */
	public String getTicket() {
		return ticket;
	}

	/**
	 * Sets information about the ticket associated with the booking.
	 *
	 * @param ticket The ticket information to set.
	 */
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
}