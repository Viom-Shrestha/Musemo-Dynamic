package com.musemo.service;

import com.musemo.model.UserModel;
import com.musemo.config.DbConfig;
import com.musemo.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Service class for managing user profiles.
 */
public class ProfileService {

    private Connection dbConn;
    private boolean isConnectionError = false;

    /**
     * Constructor initializes the database connection.
     */
    public ProfileService() {
        try {
            dbConn = DbConfig.getDbConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            isConnectionError = true;
        }
    }

    /**
     * Retrieves user details based on username.
     *
     * @param username the username
     * @return UserModel object or null if not found / connection error
     */
    public UserModel getUserByUsername(String username) {
        if (isConnectionError) {
            System.out.println("Connection Error!");
            return null;
        }

        UserModel user = null;
        // ✅ Include the userImage column in the SELECT query
        String sql = "SELECT username, fullname, password, gender, email, dateOfBirth, contact, userImage FROM user WHERE username = ?";
        try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new UserModel();
                user.setUsername(rs.getString("username"));
                user.setFullName(rs.getString("fullname"));
                user.setPassword(rs.getString("password"));
                user.setGender(rs.getString("gender"));
                user.setEmail(rs.getString("email"));
                java.sql.Date sqlDate = rs.getDate("dateOfBirth");
                if (sqlDate != null) {
                    user.setDateOfBirth(sqlDate.toLocalDate());
                }
                user.setContact(rs.getString("contact"));
                // ✅ Retrieve the userImage from the ResultSet
                user.setUserImage(rs.getString("userImage"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Retrieves the total number of bookings for the user.
     *
     * @param username the username
     * @return total bookings count
     */
    public int getTotalBookings(String username) {
        if (isConnectionError) {
            System.out.println("Connection Error!");
            return 0;
        }

        int totalBookings = 0;
        String sql = "SELECT COUNT(*) FROM booking WHERE username = ?";
        try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalBookings = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalBookings;
    }

    /**
     * Retrieves the number of distinct exhibitions visited by the user.
     *
     * @param username the username
     * @return count of exhibitions visited
     */
    public int getExhibitionsVisited(String username) {
        if (isConnectionError) {
            System.out.println("Connection Error!");
            return 0;
        }

        int exhibitionsVisited = 0;
        String sql = "SELECT COUNT(DISTINCT exhibitionId) FROM booking WHERE username = ?";
        try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                exhibitionsVisited = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exhibitionsVisited;
    }

    /**
     * Updates the user profile information.
     *
     * @param user the updated UserModel object
     */
    public void updateUser(UserModel user) {
        if (isConnectionError) {
            System.out.println("Connection Error!");
            return;
        }

        // ✅ Include userImage in the UPDATE statement
        String sql = "UPDATE user SET fullName = ?, password = ?, gender = ?, email = ?, dateOfBirth = ?, contact = ?, userImage = ? WHERE username = ?";
        try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
            ps.setString(1, user.getFullName());

            // Only update password if a new one is provided
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                String encryptedPassword = PasswordUtil.encrypt(user.getUsername(), user.getPassword());
                ps.setString(2, encryptedPassword);
            } else {
                UserModel existingUser = getUserByUsername(user.getUsername());
                if (existingUser != null) {
                    ps.setString(2, existingUser.getPassword());
                } else {
                    System.err.println("Warning: Existing user not found while updating password.");
                    return;
                }
            }

            ps.setString(3, user.getGender());
            ps.setString(4, user.getEmail());

            if (user.getDateOfBirth() != null) {
                ps.setDate(5, java.sql.Date.valueOf(user.getDateOfBirth()));
            } else {
                ps.setDate(5, null);
            }

            ps.setString(6, user.getContact());
            // ✅ Set the userImage in the PreparedStatement
            ps.setString(7, user.getUserImage());
            ps.setString(8, user.getUsername());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}