package com.momhelp.service.impl;

import com.momhelp.entity.Vegetable;
import com.momhelp.repository.VegetableRepository;
import com.momhelp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@EnableScheduling
public class ScheduledAlertService {

	@Autowired
	private VegetableRepository vegetableRepository;

	@Autowired
	private EmailService emailService;

	// CRON expression: Run every day at 9 AM
	@Scheduled(cron = "0 0 9 * * ?") // 9:00 AM daily
	public void checkAndSendExpiryAlerts() {
		System.out.println("🔄 Running automated expiry check...");

		List<Vegetable> vegetables = vegetableRepository.findAll();
		Date today = new Date();

		List<String> expiringSoonList = new ArrayList<>();
		List<String> expiringTodayList = new ArrayList<>();
		List<String> expiredList = new ArrayList<>();

		for (Vegetable veg : vegetables) {
			if (veg.getUseBeforeDate() != null && !veg.isSpoiled()) {
				long diffInMillis = veg.getUseBeforeDate().getTime() - today.getTime();
				int daysUntilExpiry = (int) TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);

				// Add to appropriate list based on days left
				if (daysUntilExpiry == 3) {
					expiringSoonList.add(veg.getName() + " (3 days left)");
				} else if (daysUntilExpiry == 0) {
					expiringTodayList.add(veg.getName() + " (expires today!)");
				} else if (daysUntilExpiry < 0) {
					expiredList.add(veg.getName());
				}
			}
		}

		// Send emails if there are items in any list
		sendAlertEmails(expiringSoonList, expiringTodayList, expiredList);
	}

	private void sendAlertEmails(List<String> expiringSoon, List<String> expiringToday, List<String> expired) {

		// This should be replaced with actual user emails from database
		List<String> userEmails = Arrays.asList("user1@example.com", "user2@example.com");

		for (String userEmail : userEmails) {
			try {
				// Send 3-days notice email
				if (!expiringSoon.isEmpty()) {
					sendExpiringSoonEmail(userEmail, expiringSoon);
				}

				// Send today expiry email
				if (!expiringToday.isEmpty()) {
					sendExpiringTodayEmail(userEmail, expiringToday);
				}

				// Send expired items email
				if (!expired.isEmpty()) {
					sendExpiredEmail(userEmail, expired);
				}
			} catch (Exception e) {
				System.err.println("Failed to send email to " + userEmail + ": " + e.getMessage());
			}
		}

		// Log summary
		System.out.println("✅ Automated alerts sent:");
		System.out.println("   - Expiring in 3 days: " + expiringSoon.size());
		System.out.println("   - Expiring today: " + expiringToday.size());
		System.out.println("   - Already expired: " + expired.size());
	}

	private void sendExpiringSoonEmail(String toEmail, List<String> vegetables) {
		try {
			StringBuilder body = new StringBuilder();
			body.append("Hello!\n\n");
			body.append("🔄 Automated alert from May I Help You...Mom!\n\n");
			body.append("⚠️ The following vegetables will expire in 3 days:\n\n");

			for (int i = 0; i < vegetables.size(); i++) {
				body.append(String.format("%d. %s\n", i + 1, vegetables.get(i)));
			}

			body.append("\n💡 Please use these vegetables soon to avoid waste.\n");
			body.append("   You can find recipe suggestions in the app!\n\n");
			body.append("Best regards,\n");
			body.append("May I Help You...Mom! Team\n\n");
			body.append("📱 Login to app: http://localhost:3000");

			emailService.sendCustomEmail(toEmail, "⏰ 3-Day Expiry Alert - " + vegetables.size() + " items",
					body.toString());

		} catch (Exception e) {
			System.err.println("Failed to send 3-day alert: " + e.getMessage());
		}
	}

	private void sendExpiringTodayEmail(String toEmail, List<String> vegetables) {
		try {
			StringBuilder body = new StringBuilder();
			body.append("Hello!\n\n");
			body.append("🚨 URGENT ALERT from May I Help You...Mom!\n\n");
			body.append("⚠️ The following vegetables expire TODAY:\n\n");

			for (int i = 0; i < vegetables.size(); i++) {
				body.append(String.format("%d. %s\n", i + 1, vegetables.get(i)));
			}

			body.append("\n❗ Please use these vegetables TODAY to avoid waste.\n");
			body.append("   Check 'Choose Me' section for quick recipe ideas!\n\n");
			body.append("Best regards,\n");
			body.append("May I Help You...Mom! Team\n\n");
			body.append("📱 Take action: http://localhost:3000/choose-me");

			emailService.sendCustomEmail(toEmail, "🚨 TODAY'S EXPIRY - " + vegetables.size() + " items",
					body.toString());

		} catch (Exception e) {
			System.err.println("Failed to send today alert: " + e.getMessage());
		}
	}

	private void sendExpiredEmail(String toEmail, List<String> vegetables) {
		try {
			StringBuilder body = new StringBuilder();
			body.append("Hello!\n\n");
			body.append("🗑️ IMPORTANT NOTICE from May I Help You...Mom!\n\n");
			body.append("❌ The following vegetables have EXPIRED:\n\n");

			for (int i = 0; i < vegetables.size(); i++) {
				body.append(String.format("%d. %s\n", i + 1, vegetables.get(i)));
			}

			body.append("\n🔍 Please check and remove these from your inventory.\n");
			body.append("   You can mark them as spoiled in the app.\n\n");
			body.append("Best regards,\n");
			body.append("May I Help You...Mom! Team\n\n");
			body.append("📱 Manage spoiled: http://localhost:3000/spoiled");

			emailService.sendCustomEmail(toEmail, "🗑️ ACTION REQUIRED: " + vegetables.size() + " items expired",
					body.toString());

		} catch (Exception e) {
			System.err.println("Failed to send expired alert: " + e.getMessage());
		}
	}
}