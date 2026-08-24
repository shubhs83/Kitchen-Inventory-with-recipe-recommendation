package com.momhelp.controller;

import com.momhelp.dto.NutritionRequestDTO;
import com.momhelp.dto.NutritionResponseDTO;
import com.momhelp.service.NutritionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/nutrition")
@CrossOrigin(origins = "http://localhost:3000")
public class NutritionController {

	@Autowired
	private NutritionService nutritionService;

	@PostMapping("/get-info")
	public ResponseEntity<?> getNutritionInfo(@Valid @RequestBody NutritionRequestDTO requestDTO) {
		try {
			NutritionResponseDTO nutrition = nutritionService.getNutritionInfo(requestDTO);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", nutrition);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/vegetable/{name}")
	public ResponseEntity<?> getNutritionByVegetable(@PathVariable String name) {
		try {
			NutritionResponseDTO nutrition = nutritionService.getNutritionByVegetableName(name);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", nutrition);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/test")
	public ResponseEntity<?> testEndpoint() {
		Map<String, Object> response = new HashMap<>();
		response.put("success", true);
		response.put("message", "Nutrition service is running");
		return ResponseEntity.ok(response);
	}

	private Map<String, Object> createErrorResponse(String message) {
		Map<String, Object> error = new HashMap<>();
		error.put("success", false);
		error.put("message", message);
		return error;
	}
}