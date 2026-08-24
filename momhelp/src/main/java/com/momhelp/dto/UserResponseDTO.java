package com.momhelp.dto;

import java.util.Date;

public class UserResponseDTO {

	private Long id;
	private String username;
	private String email;
	private String fullName;
	private String phoneNumber;
	private Date createdDate;
	private Boolean isActive;

	public UserResponseDTO() {
	}

	public UserResponseDTO(Long id, String username, String email, String fullName, String phoneNumber,
			Date createdDate, Boolean isActive) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.createdDate = createdDate;
		this.isActive = isActive;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}