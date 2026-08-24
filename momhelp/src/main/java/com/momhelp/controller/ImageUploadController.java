package com.momhelp.controller;

import com.momhelp.service.ImageDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/image")
@CrossOrigin(origins = "http://localhost:3000")
public class ImageUploadController {

	@Autowired
	private ImageDetectionService imageDetectionService;

	// Detect vegetables from uploaded image
	@PostMapping("/detect-vegetables")
	public ResponseEntity<?> detectVegetables(@RequestParam("image") MultipartFile image) {
		try {
			// Validate image
			if (!imageDetectionService.validateImage(image)) {
				return ResponseEntity.badRequest().body(
						createErrorResponse("Invalid image file. Supported formats: JPG, PNG, WEBP. Max size: 5MB"));
			}

			// Detect vegetables
			List<Map<String, Object>> detectedVegetables = imageDetectionService.detectVegetables(image);

			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Image processed successfully");
			response.put("detectedVegetables", detectedVegetables);
			response.put("count", detectedVegetables.size());

			return ResponseEntity.ok(response);

		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
		} catch (Exception e) {
			System.err.println("Error processing image: " + e.getMessage());
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(createErrorResponse("Failed to process image: " + e.getMessage()));
		}
	}

	// Get supported image formats
	@GetMapping("/supported-formats")
	public ResponseEntity<?> getSupportedFormats() {
		Map<String, Object> response = new HashMap<>();
		response.put("success", true);
		response.put("formats", imageDetectionService.getSupportedFormats());
		response.put("maxSizeBytes", 5 * 1024 * 1024);
		response.put("maxSizeMB", 5);
		return ResponseEntity.ok(response);
	}

	// Test endpoint
	@GetMapping("/test")
	public ResponseEntity<?> testEndpoint() {
		Map<String, Object> response = new HashMap<>();
		response.put("success", true);
		response.put("message", "Image detection service is running");
		return ResponseEntity.ok(response);
	}

	// Helper method
	private Map<String, Object> createErrorResponse(String message) {
		Map<String, Object> error = new HashMap<>();
		error.put("success", false);
		error.put("message", message);
		return error;
	}
}