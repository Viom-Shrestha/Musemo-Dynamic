package com.musemo.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Configuration class for establishing a connection to the MySQL database.
 * 
 * @author 23048612 Viom Shrestha
 */
public class DbConfig {
// Database configuration information
	private static final String DB_NAME = "musemo";
	private static final String URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";

	/**
	 * Establishes a connection to the MySQL database using the provided credentials
	 * and driver.
	 *
	 * @return Connection object for the database. Ensure to close the connection
	 *         after use to release resources.
	 * @throws SQLException           If a database access error occurs during the
	 *                                connection attempt.
	 * @throws ClassNotFoundException If the JDBC driver class for MySQL is not
	 *                                found in the classpath.
	 */

	public static Connection getDbConnection() throws SQLException, ClassNotFoundException {
		// Load the MySQL JDBC driver class
		Class.forName("com.mysql.cj.jdbc.Driver");
		// Establish and return the database connection
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}
}
