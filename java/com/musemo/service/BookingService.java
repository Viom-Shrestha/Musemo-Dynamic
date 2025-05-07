package com.musemo.service;

import com.musemo.config.DbConfig;
import com.musemo.model.BookingModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookingService {
    private Connection dbConn;

    public BookingService() {
        try {
            dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean saveBooking(BookingModel booking) {
        String sql = "INSERT INTO booking (exhibitionId, username, bookingDate, bookingTime, ticket) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
            int exhibitionId = booking.getExhibitionId();
            if (exhibitionId == -1) {
                System.out.println("Invalid exhibition ID");
                return false;
            }
            
            // Log input values
            System.out.println("Exhibition ID: " + exhibitionId);
            System.out.println("Username: " + booking.getUsername());
            System.out.println("Booking Date: " + booking.getBookingDate());
            System.out.println("Booking Time: " + booking.getBookingTime());
            System.out.println("Ticket: " + booking.getTicket());

            ps.setInt(1, exhibitionId);
            ps.setString(2, booking.getUsername());

            java.sql.Date bookingDate = new java.sql.Date(booking.getBookingDate().getTime());
            ps.setDate(3, bookingDate);

            if (booking.getBookingTime() != null) {
                ps.setTime(4, booking.getBookingTime());
            } else {
                ps.setNull(4, java.sql.Types.TIME);
            }

            ps.setString(5, booking.getTicket());

            int rowsAffected = ps.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            
            return rowsAffected > 0; // If no rows are affected, something went wrong
        } catch (SQLException e) {
            e.printStackTrace(); // Log any exceptions
        }
        return false;
    }

}
