package com.momhelp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AutomatedAlertService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private EmailServiceImpl emailService;

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	// ===== SCHEDULED JOBS =====

	// Job 1: Run daily at 9:00 AM
	@Scheduled(cron = "0 0 9 * * ?")
	public void morningExpiryCheck() {
		System.out.println("⏰ [9:00 AM] Running morning expiry check...");
		checkAndSendAlerts();
	}

	// Job 2: Run daily at 6:00 PM (optional)
	@Scheduled(cron = "0 0 18 * * ?")
	public void eveningExpiryCheck() {
		System.out.println("🌙 [6:00 PM] Running evening expiry check...");
		checkAndSendAlerts();
	}

	// Job 3: Run every hour for testing (comment out in production)
	// @Scheduled(cron = "0 0 * * * ?")
	public void hourlyCheck() {
		System.out.println("🔄 [Hourly] Checking for expiring vegetables...");
		checkAndSendAlerts();
	}

	// ===== MAIN ALERT LOGIC =====

	private void checkAndSendAlerts() {
		try {
			System.out.println("📊 Checking vegetables for expiry alerts...");

			// 1. Get all non-spoiled vegetables
			String sql = "SELECT id, vegetable_name, use_before_date FROM vegetables "
					+ "WHERE is_spoiled = false AND use_before_date IS NOT NULL";

			List<Map<String, Object>> vegetables = jdbcTemplate.queryForList(sql);
			System.out.println("📦 Found " + vegetables.size() + " vegetables to check");

			// 2. Categorize by expiry status
			List<AlertItem> expiringIn3Days = new ArrayList<>();
			List<AlertItem> expiringToday = new ArrayList<>();
			List<AlertItem> alreadyExpired = new ArrayList<>();

			Date today = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(today);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			Date todayStart = calendar.getTime();

			// 3 days from now
			calendar.add(Calendar.DAY_OF_MONTH, 3);
			Date threeDaysLater = calendar.getTime();

			for (Map<String, Object> veg : vegetables) {
				Date useBeforeDate = (Date) veg.get("use_before_date");
				if (useBeforeDate == null)
					continue;

				Long id = ((Number) veg.get("id")).longValue();
				String name = (String) veg.get("vegetable_name");

				// Reset calendar for each date comparison
				calendar.setTime(useBeforeDate);
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				Date expiryDate = calendar.getTime();

				// Check which category
				if (expiryDate.before(todayStart)) {
					alreadyExpired.add(new AlertItem(id, name, useBeforeDate, "EXPIRED"));
				} else if (expiryDate.equals(todayStart)) {
					expiringToday.add(new AlertItem(id, name, useBeforeDate, "TODAY"));
				} else if (expiryDate.equals(threeDaysLater)) {
					expiringIn3Days.add(new AlertItem(id, name, useBeforeDate, "3_DAYS"));
				}
			}

			// 3. Send alerts if any found
			if (!expiringIn3Days.isEmpty() || !expiringToday.isEmpty() || !alreadyExpired.isEmpty()) {
				sendAlertsToAllUsers(expiringIn3Days, expiringToday, alreadyExpired);
			} else {
				System.out.println("✅ No expiry alerts needed at this time");
			}

		} catch (Exception e) {
			System.err.println("❌ Error in automated alert check: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// ===== SEND EMAILS =====

	private void sendAlertsToAllUsers(List<AlertItem> expiringIn3Days, List<AlertItem> expiringToday,
			List<AlertItem> alreadyExpired) {

		// Get user emails from database or use defaults
		List<String> userEmails = getUserEmails();

		if (userEmails.isEmpty()) {
			System.out.println("⚠️ No user emails configured. Skipping email alerts.");
			return;
		}

		System.out.println("📧 Sending alerts to " + userEmails.size() + " users");

		for (String userEmail : userEmails) {
			try {
				// Send each type of alert if items exist
				if (!expiringIn3Days.isEmpty()) {
					sendThreeDayAlert(userEmail, expiringIn3Days);
					Thread.sleep(1000); // Small delay between emails
				}

				if (!expiringToday.isEmpty()) {
					sendTodayAlert(userEmail, expiringToday);
					Thread.sleep(1000);
				}

				if (!alreadyExpired.isEmpty()) {
					sendExpiredAlert(userEmail, alreadyExpired);
					Thread.sleep(1000);
				}

			} catch (Exception e) {
				System.err.println("❌ Failed to send email to " + userEmail + ": " + e.getMessage());
			}
		}

		// Log summary
		logAlertSummary(expiringIn3Days, expiringToday, alreadyExpired);
	}

	private List<String> getUserEmails() {
		try {
			// Try to get emails from users table
			String sql = "SELECT email FROM users WHERE receive_alerts = true";
			return jdbcTemplate.queryForList(sql, String.class);
		} catch (Exception e) {
			// If users table doesn't exist, use default
			System.out.println("ℹ️ Using default email (create users table for multiple users)");
			return Arrays.asList("admin@momhelp.com");
		}
	}

	// ===== EMAIL TEMPLATES =====

	private void sendThreeDayAlert(String toEmail, List<AlertItem> items) {
		StringBuilder body = new StringBuilder();
		body.append("Hello!\n\n");
		body.append("⏰ **3-DAY EXPIRY ALERT** from May I Help You...Mom!\n\n");
		body.append("The following vegetables will expire in 3 days:\n\n");

		for (int i = 0; i < items.size(); i++) {
			AlertItem item = items.get(i);
			body.append(String.format("%d. **%s** (Expires: %s)\n", i + 1, item.name, formatDate(item.expiryDate)));
		}

		body.append("\n💡 **Action Required:**\n");
		body.append("• Plan meals using these vegetables\n");
		body.append("• Check recipe suggestions in the app\n");
		body.append("• Consider freezing if not using soon\n\n");

		body.append("📱 **Quick Links:**\n");
		body.append("• Recipe Suggestions: http://localhost:3000/choose-me\n");
		body.append("• View All Vegetables: http://localhost:3000/vegetables\n\n");

		body.append("Best regards,\n");
		body.append("Your Kitchen Assistant 🤖\n");
		body.append("May I Help You...Mom!\n");

		String subject = "⏰ 3-Day Alert: " + items.size() + " vegetable(s) expiring soon";

		emailService.sendCustomEmail(toEmail, subject, body.toString());
	}

	private void sendTodayAlert(String toEmail, List<AlertItem> items) {
		StringBuilder body = new StringBuilder();
		body.append("Hello!\n\n");
		body.append("🚨 **URGENT: EXPIRES TODAY** from May I Help You...Mom!\n\n");
		body.append("The following vegetables expire **TODAY**:\n\n");

		for (int i = 0; i < items.size(); i++) {
			AlertItem item = items.get(i);
			body.append(String.format("%d. **%s** (Use by: %s)\n", i + 1, item.name, formatDate(item.expiryDate)));
		}

		body.append("\n❗ **Immediate Action Needed:**\n");
		body.append("• Use these vegetables TODAY\n");
		body.append("• Cook them for today's meals\n");
		body.append("• Check 'Quick Recipes' section\n\n");

		body.append("🍳 **Quick Recipe Ideas:**\n");
		body.append("• Stir-fry with available vegetables\n");
		body.append("• Vegetable soup or curry\n");
		body.append("• Add to pasta or rice dishes\n\n");

		body.append("📱 **Take Action Now:**\n");
		body.append("http://localhost:3000/lets-use\n\n");

		body.append("Best regards,\n");
		body.append("Your Kitchen Assistant 🤖\n");
		body.append("May I Help You...Mom!\n");

		String subject = "🚨 URGENT: " + items.size() + " vegetable(s) expire TODAY";

		emailService.sendCustomEmail(toEmail, subject, body.toString());
	}

	private void sendExpiredAlert(String toEmail, List<AlertItem> items) {
		StringBuilder body = new StringBuilder();
		body.append("Hello!\n\n");
		body.append("🗑️ **EXPIRED VEGETABLES** from May I Help You...Mom!\n\n");
		body.append("The following vegetables have **EXPIRED**:\n\n");

		for (int i = 0; i < items.size(); i++) {
			AlertItem item = items.get(i);
			body.append(String.format("%d. **%s** (Expired: %s)\n", i + 1, item.name, formatDate(item.expiryDate)));
		}

		body.append("\n🔍 **Required Actions:**\n");
		body.append("1. Check each vegetable for spoilage\n");
		body.append("2. Discard any spoiled items\n");
		body.append("3. Mark as spoiled in the app\n");
		body.append("4. Update your inventory\n\n");

		body.append("⚠️ **Safety First:**\n");
		body.append("• Don't consume if showing mold/smell\n");
		body.append("• When in doubt, throw it out\n");
		body.append("• Clean storage area after removal\n\n");

		body.append("📱 **Manage Spoiled Items:**\n");
		body.append("http://localhost:3000/spoiled\n\n");

		body.append("Best regards,\n");
		body.append("Your Kitchen Assistant 🤖\n");
		body.append("May I Help You...Mom!\n");

		String subject = "🗑️ Action Required: " + items.size() + " vegetable(s) have expired";

		emailService.sendCustomEmail(toEmail, subject, body.toString());
	}

	// ===== HELPER METHODS =====

	private String formatDate(Date date) {
		return dateFormat.format(date);
	}

	private void logAlertSummary(List<AlertItem> in3Days, List<AlertItem> today, List<AlertItem> expired) {
		System.out.println("\n📋 ALERT SUMMARY:");
		System.out.println("══════════════════════════════════");
		System.out.println("⏰ 3 Days Warning: " + in3Days.size() + " items");
		in3Days.forEach(item -> System.out.println("   • " + item.name));

		System.out.println("\n🚨 Expiring Today: " + today.size() + " items");
		today.forEach(item -> System.out.println("   • " + item.name));

		System.out.println("\n🗑️ Already Expired: " + expired.size() + " items");
		expired.forEach(item -> System.out.println("   • " + item.name));
		System.out.println("══════════════════════════════════\n");
	}

	// ===== TEST ENDPOINT =====

	public void triggerManualCheck() {
		System.out.println("🔄 Manually triggering expiry check...");
		checkAndSendAlerts();
	}

	// ===== HELPER CLASS =====

	private class AlertItem {
		Long id;
		String name;
		Date expiryDate;
		String type;

		AlertItem(Long id, String name, Date expiryDate, String type) {
			this.id = id;
			this.name = name;
			this.expiryDate = expiryDate;
			this.type = type;
		}
	}
}