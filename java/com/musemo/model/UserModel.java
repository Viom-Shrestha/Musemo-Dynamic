package com.musemo.model;

import java.time.LocalDate;

/**
 * Represents a user of the system with various personal and authentication
 * details.
 * 
 * @author 23048612 Viom Shrestha
 */
public class UserModel {
	private String username;
	private String fullName;
	private String password;
	private String role;
	private String gender;
	private String email;
	private String contact;
	private LocalDate dateOfBirth;
	private String userImage;

	/**
	 * Default constructor for the UserModel.
	 */
	public UserModel() {
		super();
	}

	/**
	 * Constructor for the UserModel with username and password for authentication).
	 *
	 * @param username The unique username of the user.
	 * @param password The password associated with the user account.
	 */
	public UserModel(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	/**
	 * Constructor for the UserModel with all available details.
	 *
	 * @param username    The unique username of the user.
	 * @param fullName    The full name of the user.
	 * @param password    The password associated with the user account.
	 * @param role        The role of the user within the system (e.g., admin,
	 *                    user).
	 * @param gender      The gender of the user.
	 * @param email       The email address of the user.
	 * @param contact     The contact information (e.g., phone number) of the user.
	 * @param dateOfBirth The date of birth of the user.
	 * @param userImage   The file path or URL to the user's profile image.
	 */
	public UserModel(String username, String fullName, String password, String role, String gender, String email,
			String contact, LocalDate dateOfBirth, String userImage) {
		super();
		this.username = username;
		this.fullName = fullName;
		this.password = password;
		this.role = role;
		this.gender = gender;
		this.email = email;
		this.contact = contact;
		this.dateOfBirth = dateOfBirth;
		this.userImage = userImage;
	}

	/**
	 * Gets the unique username of the user.
	 *
	 * @return The username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the unique username of the user.
	 *
	 * @param username The username to set.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the full name of the user.
	 *
	 * @return The full name.
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * Sets the full name of the user.
	 *
	 * @param fullName The full name to set.
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * Gets the password associated with the user account.
	 *
	 * @return The password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password associated with the user account.
	 *
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the role of the user within the system.
	 *
	 * @return The user's role.
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Sets the role of the user within the system.
	 *
	 * @param role The role to set.
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * Gets the gender of the user.
	 *
	 * @return The user's gender.
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Sets the gender of the user.
	 *
	 * @param gender The gender to set.
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Gets the email address of the user.
	 *
	 * @return The email address.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email address of the user.
	 *
	 * @param email The email address to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the contact information of the user.
	 *
	 * @return The contact information.
	 */
	public String getContact() {
		return contact;
	}

	/**
	 * Sets the contact information of the user.
	 *
	 * @param contact The contact information to set.
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}

	/**
	 * Gets the date of birth of the user.
	 *
	 * @return The date of birth.
	 */
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * Sets the date of birth of the user.
	 *
	 * @param dateOfBirth The date of birth to set.
	 */
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * Gets the file path or URL to the user's profile image.
	 *
	 * @return The user image path or URL.
	 */
	public String getUserImage() {
		return userImage;
	}

	/**
	 * Sets the file path or URL to the user's profile image.
	 *
	 * @param userImage The user image path or URL to set.
	 */
	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}
}