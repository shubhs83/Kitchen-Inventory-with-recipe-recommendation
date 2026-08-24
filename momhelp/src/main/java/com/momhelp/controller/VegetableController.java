package com.momhelp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.momhelp.dto.UseVegetableRequestDTO;
import com.momhelp.dto.VegetableRequestDTO;
import com.momhelp.dto.VegetableResponseDTO;
import com.momhelp.service.VegetableService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vegetables")
@CrossOrigin(origins = "http://localhost:3000") // For React frontend
public class VegetableController {

	@Autowired
	private VegetableService vegetableService;

	// Add new vegetable
	@PostMapping("/add")
	public ResponseEntity<?> addVegetable(@Valid @RequestBody VegetableRequestDTO vegetableRequestDTO) {
		try {
			VegetableResponseDTO response = vegetableService.addVegetable(vegetableRequestDTO);
			Map<String, Object> responseBody = new HashMap<>();
			responseBody.put("success", true);
			responseBody.put("message", "Vegetable added successfully!");
			responseBody.put("data", response);
			return ResponseEntity.ok(responseBody);
		} catch (RuntimeException e) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("success", false);
			errorResponse.put("message", e.getMessage());
			return ResponseEntity.badRequest().body(errorResponse);
		}
	}

	// Update vegetable
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateVegetable(@PathVariable Long id,
			@Valid @RequestBody VegetableRequestDTO vegetableRequestDTO) {
		try {
			VegetableResponseDTO response = vegetableService.updateVegetable(id, vegetableRequestDTO);
			Map<String, Object> responseBody = new HashMap<>();
			responseBody.put("success", true);
			responseBody.put("message", "Vegetable updated successfully!");
			responseBody.put("data", response);
			return ResponseEntity.ok(responseBody);
		} catch (RuntimeException e) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("success", false);
			errorResponse.put("message", e.getMessage());
			return ResponseEntity.badRequest().body(errorResponse);
		}
	}

	// Delete vegetable
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteVegetable(@PathVariable Long id) {
		try {
			vegetableService.deleteVegetable(id);
			Map<String, Object> responseBody = new HashMap<>();
			responseBody.put("success", true);
			responseBody.put("message", "Vegetable deleted successfully!");
			return ResponseEntity.ok(responseBody);
		} catch (RuntimeException e) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("success", false);
			errorResponse.put("message", e.getMessage());
			return ResponseEntity.badRequest().body(errorResponse);
		}
	}

	// Get vegetable by ID
	@GetMapping("/{id}")
	public ResponseEntity<?> getVegetableById(@PathVariable Long id) {
		try {
			VegetableResponseDTO response = vegetableService.getVegetableById(id);
			return ResponseEntity.ok(response);
		} catch (RuntimeException e) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("success", false);
			errorResponse.put("message", e.getMessage());
			return ResponseEntity.badRequest().body(errorResponse);
		}
	}

	// Get all vegetables
	@GetMapping("/all")
	public ResponseEntity<List<VegetableResponseDTO>> getAllVegetables() {
		List<VegetableResponseDTO> vegetables = vegetableService.getAllVegetables();
		return ResponseEntity.ok(vegetables);
	}

	// Search vegetables by name
	@GetMapping("/search")
	public ResponseEntity<List<VegetableResponseDTO>> searchVegetables(@RequestParam String name) {
		List<VegetableResponseDTO> vegetables = vegetableService.searchVegetablesByName(name);
		return ResponseEntity.ok(vegetables);
	}

	// Get available vegetables (not expired)
	@GetMapping("/available")
	public ResponseEntity<List<VegetableResponseDTO>> getAvailableVegetables() {
		List<VegetableResponseDTO> vegetables = vegetableService.getAvailableVegetables();
		return ResponseEntity.ok(vegetables);
	}

	// Get spoiled vegetables
	@GetMapping("/spoiled")
	public ResponseEntity<List<VegetableResponseDTO>> getSpoiledVegetables() {
		List<VegetableResponseDTO> vegetables = vegetableService.getSpoiledVegetables();
		return ResponseEntity.ok(vegetables);
	}

	// Mark vegetable as spoiled
	@PutMapping("/mark-spoiled/{id}")
	public ResponseEntity<?> markAsSpoiled(@PathVariable Long id) {
		try {
			VegetableResponseDTO response = vegetableService.markAsSpoiled(id);
			Map<String, Object> responseBody = new HashMap<>();
			responseBody.put("success", true);
			responseBody.put("message", "Vegetable marked as spoiled!");
			responseBody.put("data", response);
			return ResponseEntity.ok(responseBody);
		} catch (RuntimeException e) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("success", false);
			errorResponse.put("message", e.getMessage());
			return ResponseEntity.badRequest().body(errorResponse);
		}
	}

	// Remove spoiled vegetable
	@DeleteMapping("/remove-spoiled/{id}")
	public ResponseEntity<?> removeSpoiledVegetable(@PathVariable Long id) {
		try {
			vegetableService.removeSpoiledVegetable(id);
			Map<String, Object> responseBody = new HashMap<>();
			responseBody.put("success", true);
			responseBody.put("message", "Spoiled vegetable removed successfully!");
			return ResponseEntity.ok(responseBody);
		} catch (RuntimeException e) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("success", false);
			errorResponse.put("message", e.getMessage());
			return ResponseEntity.badRequest().body(errorResponse);
		}
	}

	// Use vegetable (reduce weight)
	@PutMapping("/use/{id}")
	public ResponseEntity<?> useVegetable(@PathVariable Long id, @Valid @RequestBody UseVegetableRequestDTO request) {
		try {
			VegetableResponseDTO response = vegetableService.useVegetable(id, request.getWeightUsed());

			Map<String, Object> responseBody = new HashMap<>();
			responseBody.put("success", true);

			if (response == null) {
				responseBody.put("message", "Vegetable used completely and removed from inventory!");
				responseBody.put("fullyUsed", true);
			} else {
				responseBody.put("message", "Vegetable weight updated successfully!");
				responseBody.put("data", response);
				responseBody.put("fullyUsed", false);
			}

			return ResponseEntity.ok(responseBody);
		} catch (RuntimeException e) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("success", false);
			errorResponse.put("message", e.getMessage());
			return ResponseEntity.badRequest().body(errorResponse);
		}
	}
}