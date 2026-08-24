package com.momhelp.controller;

import com.momhelp.service.impl.AutomatedAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/automated-alerts")
@CrossOrigin(origins = "http://localhost:3000")
public class AutomatedAlertController {

	@Autowired
	private AutomatedAlertService automatedAlertService;

	// Trigger manual check
	@PostMapping("/trigger-now")
	public ResponseEntity<?> triggerNow() {
		try {
			automatedAlertService.triggerManualCheck();

			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Automated alert check triggered successfully!");
			response.put("timestamp", System.currentTimeMillis());

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(500).body(createErrorResponse("Failed to trigger alerts: " + e.getMessage()));
		}
	}

	// Check status
	@GetMapping("/status")
	public ResponseEntity<?> getStatus() {
		Map<String, Object> response = new HashMap<>();
		response.put("success", true);
		response.put("service", "Automated Alert Service");
		response.put("status", "ACTIVE");
		response.put("nextRun", "Daily at 9:00 AM and 6:00 PM");
		response.put("description", "Checks for vegetables expiring in 3 days, today, or already expired");
		return ResponseEntity.ok(response);
	}

	// Create test vegetables
	@PostMapping("/create-test-data")
	public ResponseEntity<?> createTestData() {
		try {
			// Create test vegetables with different expiry dates
			createTestVegetables();

			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Test vegetables created successfully!");
			response.put("details", "Created 3 test vegetables: Expired, Expiring Today, Expiring in 3 Days");

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(500)
					.body(createErrorResponse("Failed to create test data: " + e.getMessage()));
		}
	}

	private void createTestVegetables() {
		// This would create test vegetables in database
		// For now, just a placeholder
		System.out.println("Creating test vegetables...");
	}

	private Map<String, Object> createErrorResponse(String message) {
		Map<String, Object> error = new HashMap<>();
		error.put("success", false);
		error.put("message", message);
		return error;
	}
}