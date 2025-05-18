package com.musemo.util;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;
import jakarta.servlet.http.Part;

/**
 * Utility class providing various methods for validating different types of
 * data.
 * 
 * @author 23048612 Viom Shrestha
 */
public class ValidationUtil {

	/**
	 * Checks if a given string is null or empty after trimming whitespace.
	 *
	 * @param value The string to check.
	 * @return True if the string is null or empty, false otherwise.
	 */
	public static boolean isNullOrEmpty(String value) {
		return value == null || value.trim().isEmpty();
	}

	/**
	 * Checks if a given string contains only alphabetic characters (a-z and A-Z).
	 *
	 * @param value The string to check.
	 * @return True if the string contains only letters, false otherwise.
	 */
	public static boolean isAlphabetic(String value) {
		return value != null && value.matches("^[a-zA-Z]+$");
	}

	/**
	 * Checks if a given string starts with a letter and is composed of only letters
	 * and numbers.
	 *
	 * @param value The string to check.
	 * @return True if the string starts with a letter and contains only
	 *         alphanumeric characters, false otherwise.
	 */
	public static boolean isAlphanumericStartingWithLetter(String value) {
		return value != null && value.matches("^[a-zA-Z][a-zA-Z0-9]*$");
	}

	/**
	 * Checks if a given string is a valid email address based on a common email
	 * format.
	 *
	 * @param email The string to check.
	 * @return True if the string matches the email format, false otherwise.
	 */
	public static boolean isValidEmail(String email) {
		String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
		return email != null && Pattern.matches(emailRegex, email);
	}

	/**
	 * Checks if a given string is a valid 10-digit phone number starting with "98".
	 * This is specific to a certain regional phone number format.
	 *
	 * @param number The string to check.
	 * @return True if the string is a 10-digit number starting with "98", false
	 *         otherwise.
	 */
	public static boolean isValidPhoneNumber(String number) {
		return number != null && number.matches("^98\\d{8}$");
	}

	/**
	 * Checks if a given password meets the following criteria: - At least one
	 * uppercase letter. - At least one digit. - At least one special symbol
	 * (@$!%*?&). - Minimum length of 8 characters.
	 *
	 * @param password The string to check.
	 * @return True if the password meets all the criteria, false otherwise.
	 */
	public static boolean isValidPassword(String password) {
		String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
		return password != null && Pattern.matches(passwordRegex, password);
	}

	/**
	 * Checks if the file extension of a Part object (representing an uploaded file)
	 * matches one of the common image file extensions (jpg, jpeg, png, gif).
	 *
	 * @param imagePart The Part object representing the uploaded file.
	 * @return True if the file has a valid image extension, false otherwise.
	 */
	public static boolean isValidImageExtension(Part imagePart) {
		if (imagePart == null || isNullOrEmpty(imagePart.getSubmittedFileName())) {
			return false;
		}
		String fileName = imagePart.getSubmittedFileName().toLowerCase();
		return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png")
				|| fileName.endsWith(".gif");
	}

	/**
	 * Checks if two password strings are equal.
	 *
	 * @param password       The first password string.
	 * @param retypePassword The second password string to compare with the first.
	 * @return True if both password strings are not null and equal, false
	 *         otherwise.
	 */
	public static boolean doPasswordsMatch(String password, String retypePassword) {
		return password != null && password.equals(retypePassword);
	}

	/**
	 * Validates if a given date of birth is not in the future and if the calculated
	 * age based on the current date is between 16 and 100 years (inclusive).
	 *
	 * @param dob The LocalDate object representing the date of birth.
	 * @return True if the date of birth is valid according to the age constraints,
	 *         false otherwise.
	 */
	public static boolean isValidAge(LocalDate dob) {
		if (dob == null || dob.isAfter(LocalDate.now())) {
			return false;
		}
		int age = Period.between(dob, LocalDate.now()).getYears();
		return age >= 16 && age <= 100;
	}
}