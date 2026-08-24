package com.momhelp.dto;

import java.util.Date;

public class ExpiryAlertResponseDTO {

	private Long id;
	private Long userId;
	private Long vegetableId;
	private String vegetableName;
	private Date expiryDate;
	private String alertType;
	private Integer daysUntilExpiry;
	private Boolean isNotified;
	private Date notificationSentDate;
	private Boolean emailSent;
	private Date createdDate;

	public ExpiryAlertResponseDTO() {
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getVegetableId() {
		return vegetableId;
	}

	public void setVegetableId(Long vegetableId) {
		this.vegetableId = vegetableId;
	}

	public String getVegetableName() {
		return vegetableName;
	}

	public void setVegetableName(String vegetableName) {
		this.vegetableName = vegetableName;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getAlertType() {
		return alertType;
	}

	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}

	public Integer getDaysUntilExpiry() {
		return daysUntilExpiry;
	}

	public void setDaysUntilExpiry(Integer daysUntilExpiry) {
		this.daysUntilExpiry = daysUntilExpiry;
	}

	public Boolean getIsNotified() {
		return isNotified;
	}

	public void setIsNotified(Boolean isNotified) {
		this.isNotified = isNotified;
	}

	public Date getNotificationSentDate() {
		return notificationSentDate;
	}

	public void setNotificationSentDate(Date notificationSentDate) {
		this.notificationSentDate = notificationSentDate;
	}

	public Boolean getEmailSent() {
		return emailSent;
	}

	public void setEmailSent(Boolean emailSent) {
		this.emailSent = emailSent;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}