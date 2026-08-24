package com.momhelp.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "expiry_alerts")
public class ExpiryAlert {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "vegetable_id")
	private Long vegetableId;

	@Column(name = "vegetable_name")
	private String vegetableName;

	@Column(name = "expiry_date")
	@Temporal(TemporalType.DATE)
	private Date expiryDate;

	@Column(name = "alert_type", length = 20)
	private String alertType; // EXPIRING_SOON, EXPIRED, EXPIRING_TODAY

	@Column(name = "days_until_expiry")
	private Integer daysUntilExpiry;

	@Column(name = "is_notified")
	private Boolean isNotified;

	@Column(name = "notification_sent_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date notificationSentDate;

	@Column(name = "email_sent")
	private Boolean emailSent;

	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	public ExpiryAlert() {
		this.createdDate = new Date();
		this.isNotified = false;
		this.emailSent = false;
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