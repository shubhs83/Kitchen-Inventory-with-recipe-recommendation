package com.momhelp.controller;

import com.momhelp.dto.ExpiryAlertRequestDTO;
import com.momhelp.dto.ExpiryAlertResponseDTO;
import com.momhelp.entity.Vegetable;
import com.momhelp.repository.VegetableRepository;
import com.momhelp.service.ExpiryAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/expiry-alerts")
@CrossOrigin(origins = "http://localhost:3000")
public class ExpiryAlertController {

	@Autowired
	private ExpiryAlertService expiryAlertService;

	@Autowired
	private VegetableRepository vegetableRepository;

	@PostMapping("/create")
	public ResponseEntity<?> createAlert(@RequestBody ExpiryAlertRequestDTO requestDTO) {
		try {
			ExpiryAlertResponseDTO alert = expiryAlertService.createAlert(requestDTO);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Alert created successfully!");
			response.put("data", alert);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getAllAlerts(@PathVariable Long userId) {
		try {
			List<ExpiryAlertResponseDTO> alerts = expiryAlertService.getAllAlerts(userId);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", alerts);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/user/{userId}/unnotified")
	public ResponseEntity<?> getUnnotifiedAlerts(@PathVariable Long userId) {
		try {
			List<ExpiryAlertResponseDTO> alerts = expiryAlertService.getUnnotifiedAlerts(userId);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", alerts);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/user/{userId}/type/{alertType}")
	public ResponseEntity<?> getAlertsByType(@PathVariable Long userId, @PathVariable String alertType) {
		try {
			List<ExpiryAlertResponseDTO> alerts = expiryAlertService.getAlertsByType(userId, alertType);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", alerts);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@PatchMapping("/{alertId}/notify")
	public ResponseEntity<?> markAsNotified(@PathVariable Long alertId) {
		try {
			ExpiryAlertResponseDTO alert = expiryAlertService.markAsNotified(alertId);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Alert marked as notified!");
			response.put("data", alert);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@PostMapping("/generate/{userId}")
	public ResponseEntity<?> generateAlerts(@PathVariable Long userId, @RequestParam String userEmail) {
		try {
			expiryAlertService.generateAlertsForExpiringVegetables(userId, userEmail);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Alerts generated successfully!");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@PostMapping("/send-emails/{userId}")
	public ResponseEntity<?> sendEmailNotifications(@PathVariable Long userId, @RequestParam String userEmail) {
		try {
			expiryAlertService.sendEmailNotifications(userId, userEmail);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Email notifications sent successfully!");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@DeleteMapping("/{alertId}")
	public ResponseEntity<?> deleteAlert(@PathVariable Long alertId) {
		try {
			expiryAlertService.deleteAlert(alertId);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Alert deleted successfully!");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@DeleteMapping("/vegetable/{vegetableId}")
	public ResponseEntity<?> deleteAlertsByVegetableId(@PathVariable Long vegetableId) {
		try {
			expiryAlertService.deleteAlertsByVegetableId(vegetableId);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Alerts deleted successfully!");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/test-vegetables")
	public ResponseEntity<?> testVegetableConnection() {
		try {
			List<Vegetable> vegetables = vegetableRepository.findAll();

			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Found " + vegetables.size() + " vegetables");
			response.put("sample", vegetables.stream().limit(3).map(v -> {
				Map<String, Object> vegMap = new HashMap<>();
				vegMap.put("id", v.getId());
				vegMap.put("name", v.getName());
				vegMap.put("expiryDate", v.getUseBeforeDate());
				vegMap.put("isSpoiled", v.isSpoiled());
				vegMap.put("weight", v.getWeight());
				vegMap.put("unit", v.getUnit());
				return vegMap;
			}).collect(Collectors.toList()));
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("success", false);
			errorResponse.put("message", "Error: " + e.getMessage());
			errorResponse.put("errorDetails", e.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

	@GetMapping("/test-health")
	public ResponseEntity<?> testHealth() {
		Map<String, Object> response = new HashMap<>();
		response.put("success", true);
		response.put("message", "Expiry Alert Controller is working!");
		response.put("status", "ACTIVE");
		response.put("timestamp", System.currentTimeMillis());
		response.put("endpoints",
				List.of("POST /api/expiry-alerts/create", "GET /api/expiry-alerts/user/{userId}",
						"POST /api/expiry-alerts/generate/{userId}", "POST /api/expiry-alerts/send-emails/{userId}",
						"GET /api/expiry-alerts/test-vegetables", "GET /api/expiry-alerts/test-health"));
		return ResponseEntity.ok(response);
	}

	private Map<String, Object> createErrorResponse(String message) {
		Map<String, Object> error = new HashMap<>();
		error.put("success", false);
		error.put("message", message);
		return error;
	}
}