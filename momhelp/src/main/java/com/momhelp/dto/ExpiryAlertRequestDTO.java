package com.momhelp.dto;

import java.util.Date;

public class ExpiryAlertRequestDTO {

	private Long userId;
	private Long vegetableId;
	private String vegetableName;
	private Date expiryDate;
	private String alertType;
	private Integer daysUntilExpiry;

	public ExpiryAlertRequestDTO() {
	}

	// Getters and Setters
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
}