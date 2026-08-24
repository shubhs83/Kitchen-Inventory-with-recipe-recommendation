package com.momhelp.controller;

import com.momhelp.dto.MealPlanRequestDTO;
import com.momhelp.dto.MealPlanResponseDTO;
import com.momhelp.service.MealPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/meal-plans")
@CrossOrigin(origins = "http://localhost:3000")
public class MealPlanController {

	@Autowired
	private MealPlanService mealPlanService;

	@PostMapping("/add")
	public ResponseEntity<?> addMealPlan(@RequestBody MealPlanRequestDTO requestDTO) {
		try {
			MealPlanResponseDTO mealPlan = mealPlanService.addMealPlan(requestDTO);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Meal plan added successfully!");
			response.put("data", mealPlan);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateMealPlan(@PathVariable Long id, @RequestBody MealPlanRequestDTO requestDTO) {
		try {
			MealPlanResponseDTO mealPlan = mealPlanService.updateMealPlan(id, requestDTO);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Meal plan updated successfully!");
			response.put("data", mealPlan);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@PatchMapping("/{id}/prepare")
	public ResponseEntity<?> markAsPrepared(@PathVariable Long id) {
		try {
			MealPlanResponseDTO mealPlan = mealPlanService.markAsPrepared(id);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Meal marked as prepared!");
			response.put("data", mealPlan);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@PatchMapping("/{id}/unprepare")
	public ResponseEntity<?> markAsUnprepared(@PathVariable Long id) {
		try {
			MealPlanResponseDTO mealPlan = mealPlanService.markAsUnprepared(id);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Meal marked as pending!");
			response.put("data", mealPlan);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getAllMealPlans(@PathVariable Long userId) {
		try {
			List<MealPlanResponseDTO> mealPlans = mealPlanService.getAllMealPlans(userId);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", mealPlans);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/user/{userId}/date/{date}")
	public ResponseEntity<?> getMealPlansByDate(@PathVariable Long userId,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
		try {
			List<MealPlanResponseDTO> mealPlans = mealPlanService.getMealPlansByDate(userId, date);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", mealPlans);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/user/{userId}/range")
	public ResponseEntity<?> getMealPlansByDateRange(@PathVariable Long userId,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
		try {
			List<MealPlanResponseDTO> mealPlans = mealPlanService.getMealPlansByDateRange(userId, startDate, endDate);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", mealPlans);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/user/{userId}/type/{mealType}")
	public ResponseEntity<?> getMealPlansByType(@PathVariable Long userId, @PathVariable String mealType) {
		try {
			List<MealPlanResponseDTO> mealPlans = mealPlanService.getMealPlansByType(userId, mealType);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", mealPlans);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/user/{userId}/pending")
	public ResponseEntity<?> getPendingMealPlans(@PathVariable Long userId) {
		try {
			List<MealPlanResponseDTO> mealPlans = mealPlanService.getPendingMealPlans(userId);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", mealPlans);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteMealPlan(@PathVariable Long id) {
		try {
			mealPlanService.deleteMealPlan(id);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Meal plan deleted successfully!");
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