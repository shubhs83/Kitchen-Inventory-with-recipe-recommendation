package com.momhelp.service.impl;

import com.momhelp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender mailSender;

	public void sendCustomEmail(String toEmail, String subject, String body) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("noreply@momhelp.com");
			message.setTo(toEmail);
			message.setSubject(subject);
			message.setText(body);

			mailSender.send(message);
			System.out.println("✅ Email sent to: " + toEmail);
		} catch (Exception e) {
			System.err.println("❌ Failed to send email: " + e.getMessage());
		}
	}

	@Override
	public void sendExpiryAlert(String toEmail, String vegetableName, int daysUntilExpiry) {
		String subject = "⚠️ Vegetable Expiry Alert - " + vegetableName;
		String body = String.format(
				"Hello!\n\n" + "This is an automated alert from May I Help You...Mom!\n\n" + "🥬 Vegetable: %s\n"
						+ "⏰ Days Until Expiry: %d day(s)\n\n" + "Please use this vegetable soon to avoid waste.\n\n"
						+ "Best regards,\n" + "May I Help You...Mom! Team",
				vegetableName, daysUntilExpiry);

		sendCustomEmail(toEmail, subject, body);
	}

	@Override
	public void sendBulkExpiryAlert(String toEmail, List<String> vegetableNames) {
		StringBuilder body = new StringBuilder();
		body.append("Hello!\n\n");
		body.append("This is an automated alert from May I Help You...Mom!\n\n");
		body.append("The following vegetables are expiring soon:\n\n");

		for (int i = 0; i < vegetableNames.size(); i++) {
			body.append(String.format("%d. %s\n", i + 1, vegetableNames.get(i)));
		}

		body.append("\nPlease use these vegetables soon to avoid waste.\n\n");
		body.append("Best regards,\n");
		body.append("May I Help You...Mom! Team");

		sendCustomEmail(toEmail, "⚠️ Multiple Vegetables Expiring Soon", body.toString());
	}

	@Override
	public void sendExpiredAlert(String toEmail, List<String> vegetableNames) {
		StringBuilder body = new StringBuilder();
		body.append("Hello!\n\n");
		body.append("This is an automated alert from May I Help You...Mom!\n\n");
		body.append("The following vegetables have EXPIRED:\n\n");

		for (int i = 0; i < vegetableNames.size(); i++) {
			body.append(String.format("%d. %s\n", i + 1, vegetableNames.get(i)));
		}

		body.append("\nPlease remove these from your inventory.\n\n");
		body.append("Best regards,\n");
		body.append("May I Help You...Mom! Team");

		sendCustomEmail(toEmail, "🚨 Vegetables Have Expired", body.toString());
	}
}