package com.momhelp.controller;

import com.momhelp.dto.CookingTipRequestDTO;
import com.momhelp.dto.CookingTipResponseDTO;
import com.momhelp.service.CookingTipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cooking-tips")
@CrossOrigin(origins = "http://localhost:3000")
public class CookingTipController {

	@Autowired
	private CookingTipService cookingTipService;

	@PostMapping("/add")
	public ResponseEntity<?> addTip(@RequestBody CookingTipRequestDTO requestDTO) {
		try {
			CookingTipResponseDTO tip = cookingTipService.addTip(requestDTO);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Cooking tip added successfully!");
			response.put("data", tip);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateTip(@PathVariable Long id, @RequestBody CookingTipRequestDTO requestDTO) {
		try {
			CookingTipResponseDTO tip = cookingTipService.updateTip(id, requestDTO);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Cooking tip updated successfully!");
			response.put("data", tip);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@PatchMapping("/{id}/favorite")
	public ResponseEntity<?> toggleFavorite(@PathVariable Long id) {
		try {
			CookingTipResponseDTO tip = cookingTipService.toggleFavorite(id);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", tip.getIsFavorite() ? "Added to favorites!" : "Removed from favorites!");
			response.put("data", tip);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@PatchMapping("/{id}/view")
	public ResponseEntity<?> incrementViewCount(@PathVariable Long id) {
		try {
			CookingTipResponseDTO tip = cookingTipService.incrementViewCount(id);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", tip);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@PatchMapping("/{id}/helpful")
	public ResponseEntity<?> incrementHelpfulCount(@PathVariable Long id) {
		try {
			CookingTipResponseDTO tip = cookingTipService.incrementHelpfulCount(id);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Marked as helpful!");
			response.put("data", tip);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getAllTips(@PathVariable Long userId) {
		try {
			List<CookingTipResponseDTO> tips = cookingTipService.getAllTips(userId);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", tips);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getTipById(@PathVariable Long id) {
		try {
			CookingTipResponseDTO tip = cookingTipService.getTipById(id);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", tip);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/user/{userId}/favorites")
	public ResponseEntity<?> getFavoriteTips(@PathVariable Long userId) {
		try {
			List<CookingTipResponseDTO> tips = cookingTipService.getFavoriteTips(userId);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", tips);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/user/{userId}/category/{category}")
	public ResponseEntity<?> getTipsByCategory(@PathVariable Long userId, @PathVariable String category) {
		try {
			List<CookingTipResponseDTO> tips = cookingTipService.getTipsByCategory(userId, category);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", tips);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/user/{userId}/difficulty/{difficultyLevel}")
	public ResponseEntity<?> getTipsByDifficulty(@PathVariable Long userId, @PathVariable String difficultyLevel) {
		try {
			List<CookingTipResponseDTO> tips = cookingTipService.getTipsByDifficulty(userId, difficultyLevel);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", tips);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/user/{userId}/search")
	public ResponseEntity<?> searchTips(@PathVariable Long userId, @RequestParam String query) {
		try {
			List<CookingTipResponseDTO> tips = cookingTipService.searchTips(userId, query);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", tips);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/user/{userId}/most-viewed")
	public ResponseEntity<?> getMostViewedTips(@PathVariable Long userId) {
		try {
			List<CookingTipResponseDTO> tips = cookingTipService.getMostViewedTips(userId);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", tips);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/user/{userId}/most-helpful")
	public ResponseEntity<?> getMostHelpfulTips(@PathVariable Long userId) {
		try {
			List<CookingTipResponseDTO> tips = cookingTipService.getMostHelpfulTips(userId);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", tips);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTip(@PathVariable Long id) {
		try {
			cookingTipService.deleteTip(id);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Cooking tip deleted successfully!");
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