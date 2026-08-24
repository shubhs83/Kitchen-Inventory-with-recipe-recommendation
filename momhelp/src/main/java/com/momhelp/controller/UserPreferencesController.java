package com.momhelp.controller;

import com.momhelp.dto.UserPreferencesRequestDTO;
import com.momhelp.dto.UserPreferencesResponseDTO;
import com.momhelp.service.UserPreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/preferences")
@CrossOrigin(origins = "http://localhost:3000")
public class UserPreferencesController {

	@Autowired
	private UserPreferencesService userPreferencesService;

	@PostMapping("/save")
	public ResponseEntity<?> savePreferences(@RequestBody UserPreferencesRequestDTO requestDTO) {
		try {
			UserPreferencesResponseDTO preferences = userPreferencesService.savePreferences(requestDTO);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Preferences saved successfully!");
			response.put("data", preferences);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@PutMapping("/{userId}")
	public ResponseEntity<?> updatePreferences(@PathVariable Long userId,
			@RequestBody UserPreferencesRequestDTO requestDTO) {
		try {
			UserPreferencesResponseDTO preferences = userPreferencesService.updatePreferences(userId, requestDTO);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Preferences updated successfully!");
			response.put("data", preferences);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/{userId}")
	public ResponseEntity<?> getPreferences(@PathVariable Long userId) {
		try {
			UserPreferencesResponseDTO preferences = userPreferencesService.getPreferencesByUserId(userId);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", preferences);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/default")
	public ResponseEntity<?> getDefaultPreferences() {
		UserPreferencesResponseDTO preferences = userPreferencesService.getDefaultPreferences();
		Map<String, Object> response = new HashMap<>();
		response.put("success", true);
		response.put("data", preferences);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deletePreferences(@PathVariable Long userId) {
		try {
			userPreferencesService.deletePreferences(userId);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Preferences deleted successfully!");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	private Map<String, Object> createErrorResponse(String message) {
		Map<String, Object> error = new HashMap<>();
		error.put("success", false);
		error.put("message", message);
		return error;
	}
}