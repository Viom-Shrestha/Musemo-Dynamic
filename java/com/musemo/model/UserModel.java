package com.musemo.model;

import java.time.LocalDate;

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

	public UserModel() {
		super();
	}

	public UserModel(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public UserModel(String username, String fullName, String password, String email, String contact) {
		super();
		this.username = username;
		this.fullName = fullName;
		this.password = password;
		this.email = email;
		this.contact = contact;
	}

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

}
