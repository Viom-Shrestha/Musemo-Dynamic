package com.musemo.model;

import java.sql.Time;
import java.util.Date;

public class BookingModel {
	private int bookingId;
	private int exhibitionId;
	private String username;
	private Date bookingDate;
	private Time bookingTime;
	private String ticket;

	public BookingModel() {
		super();
	}

	public BookingModel(int exhibitionId, String username, Date bookingDate, Time bookingTime, String ticket) {
		super();
		this.exhibitionId = exhibitionId;
		this.username = username;
		this.bookingDate = bookingDate;
		this.bookingTime = bookingTime;
		this.ticket = ticket;
	}

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

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public int getExhibitionId() {
		return exhibitionId;
	}

	public void setExhibitionId(int exhibitionId) {
		this.exhibitionId = exhibitionId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public Time getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(Time bookingTime) {
		this.bookingTime = bookingTime;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

}
