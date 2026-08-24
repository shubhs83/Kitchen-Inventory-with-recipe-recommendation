package com.momhelp.controller;

import com.momhelp.dto.DishDTO;
import com.momhelp.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dishes")
@CrossOrigin(origins = "http://localhost:3000")
public class DishController {

	@Autowired
	private DishService dishService;

	// Get all dishes
	@GetMapping("/all")
	public ResponseEntity<List<DishDTO>> getAllDishes() {
		List<DishDTO> dishes = dishService.getAllDishes();
		return ResponseEntity.ok(dishes);
	}

	// Get dish by ID
	@GetMapping("/{id}")
	public ResponseEntity<?> getDishById(@PathVariable Long id) {
		try {
			DishDTO dish = dishService.getDishById(id);
			return ResponseEntity.ok(dish);
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
		}
	}

	// Get dishes by vegetable ID
	@GetMapping("/by-vegetable/{vegetableId}")
	public ResponseEntity<?> getDishesByVegetableId(@PathVariable Long vegetableId) {
		try {
			List<DishDTO> dishes = dishService.getDishesByVegetableId(vegetableId);
			return ResponseEntity.ok(dishes);
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
		}
	}

	// Get dishes by vegetable name
	@GetMapping("/by-vegetable-name")
	public ResponseEntity<?> getDishesByVegetableName(@RequestParam String name) {
		try {
			List<DishDTO> dishes = dishService.getDishesByVegetableName(name);
			return ResponseEntity.ok(dishes);
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
		}
	}

	// Get dishes for available vegetables (not spoiled, not expired)
	@GetMapping("/available")
	public ResponseEntity<List<DishDTO>> getDishesForAvailableVegetables() {
		List<DishDTO> dishes = dishService.getDishesForAvailableVegetables();
		return ResponseEntity.ok(dishes);
	}

	// Get available dishes for a specific vegetable (for Choose Me section)
	@GetMapping("/available/{vegetableId}")
	public ResponseEntity<?> getAvailableDishesByVegetableId(@PathVariable Long vegetableId) {
		try {
			List<DishDTO> dishes = dishService.getAvailableDishesByVegetableId(vegetableId);
			return ResponseEntity.ok(dishes);
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
		}
	}

	// Get all vegetables that have dishes (for Choose Me dropdown)
	@GetMapping("/vegetables-with-dishes")
	public ResponseEntity<List<String>> getVegetablesWithDishes() {
		List<String> vegetables = dishService.getVegetablesWithDishes();
		return ResponseEntity.ok(vegetables);
	}

	// Helper method to create error response
	private Map<String, Object> createErrorResponse(String message) {
		Map<String, Object> errorResponse = new HashMap<>();
		errorResponse.put("success", false);
		errorResponse.put("message", message);
		return errorResponse;
	}
}