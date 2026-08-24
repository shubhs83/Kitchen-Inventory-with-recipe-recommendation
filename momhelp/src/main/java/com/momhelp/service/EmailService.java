package com.momhelp.service;

import java.util.List;

public interface EmailService {

	void sendExpiryAlert(String toEmail, String vegetableName, int daysUntilExpiry);

	void sendBulkExpiryAlert(String toEmail, List<String> vegetableNames);

	void sendExpiredAlert(String toEmail, List<String> vegetableNames);

	// ADD THIS NEW METHOD
	void sendCustomEmail(String toEmail, String subject, String body);
}