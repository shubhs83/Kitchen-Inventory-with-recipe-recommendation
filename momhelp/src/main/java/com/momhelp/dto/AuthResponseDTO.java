package com.momhelp.dto;

public class AuthResponseDTO {

	private String token;
	private String type = "Bearer";
	private Long userId;
	private String username;
	private String email;
	private String fullName;

	public AuthResponseDTO() {
	}

	public AuthResponseDTO(String token, Long userId, String username, String email, String fullName) {
		this.token = token;
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.fullName = fullName;
	}

	// Getters and Setters
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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
}