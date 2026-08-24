package com.momhelp.controller;

import com.momhelp.dto.AiRecipeRequestDTO;
import com.momhelp.dto.AiRecipeResponseDTO;
import com.momhelp.service.AiRecipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai-recipe")
@CrossOrigin(origins = "http://localhost:3000")
public class AiRecipeController {

	@Autowired
	private AiRecipeService aiRecipeService;

	// Generate new recipe using AI
	@PostMapping("/generate")
	public ResponseEntity<?> generateRecipe(@Valid @RequestBody AiRecipeRequestDTO requestDTO) {
		try {
			AiRecipeResponseDTO recipe = aiRecipeService.generateRecipe(requestDTO);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Recipe generated successfully!");
			response.put("data", recipe);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorResponse(e.getMessage()));
		}
	}

	// Get recipe by ID
	@GetMapping("/{id}")
	public ResponseEntity<?> getRecipeById(@PathVariable Long id) {
		try {
			AiRecipeResponseDTO recipe = aiRecipeService.getRecipeById(id);
			return ResponseEntity.ok(recipe);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
		}
	}

	// Get all generated recipes
	@GetMapping("/all")
	public ResponseEntity<List<AiRecipeResponseDTO>> getAllRecipes() {
		List<AiRecipeResponseDTO> recipes = aiRecipeService.getAllRecipes();
		return ResponseEntity.ok(recipes);
	}

	// Get favorite recipes
	@GetMapping("/favorites")
	public ResponseEntity<List<AiRecipeResponseDTO>> getFavoriteRecipes() {
		List<AiRecipeResponseDTO> recipes = aiRecipeService.getFavoriteRecipes();
		return ResponseEntity.ok(recipes);
	}

	// Toggle favorite status
	@PutMapping("/{id}/favorite")
	public ResponseEntity<?> toggleFavorite(@PathVariable Long id) {
		try {
			AiRecipeResponseDTO recipe = aiRecipeService.toggleFavorite(id);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", recipe.getIsFavorite() ? "Added to favorites!" : "Removed from favorites!");
			response.put("data", recipe);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
		}
	}

	// Delete recipe
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteRecipe(@PathVariable Long id) {
		try {
			aiRecipeService.deleteRecipe(id);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Recipe deleted successfully!");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
		}
	}

	// Get recent recipes
	@GetMapping("/recent")
	public ResponseEntity<List<AiRecipeResponseDTO>> getRecentRecipes() {
		List<AiRecipeResponseDTO> recipes = aiRecipeService.getRecentRecipes();
		return ResponseEntity.ok(recipes);
	}

	// Helper method
	private Map<String, Object> createErrorResponse(String message) {
		Map<String, Object> error = new HashMap<>();
		error.put("success", false);
		error.put("message", message);
		return error;
	}
}
