package com.momhelp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.momhelp.dto.RecipeBookRequestDTO;
import com.momhelp.dto.RecipeBookResponseDTO;
import com.momhelp.service.RecipeBookService;

@RestController
@RequestMapping("/api/recipe-book")
@CrossOrigin(origins = "http://localhost:3000")
public class RecipeBookController {

	@Autowired
	private RecipeBookService recipeBookService;

	@PostMapping("/add")
	public ResponseEntity<?> addRecipe(@RequestBody RecipeBookRequestDTO requestDTO) {
		try {
			RecipeBookResponseDTO recipe = recipeBookService.addRecipe(requestDTO);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Recipe added successfully!");
			response.put("data", recipe);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateRecipe(@PathVariable Long id, @RequestBody RecipeBookRequestDTO requestDTO) {
		try {
			RecipeBookResponseDTO recipe = recipeBookService.updateRecipe(id, requestDTO);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Recipe updated successfully!");
			response.put("data", recipe);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@PatchMapping("/{id}/favorite")
	public ResponseEntity<?> toggleFavorite(@PathVariable Long id) {
		try {
			RecipeBookResponseDTO recipe = recipeBookService.toggleFavorite(id);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", recipe.getIsFavorite() ? "Added to favorites!" : "Removed from favorites!");
			response.put("data", recipe);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@PatchMapping("/{id}/rating")
	public ResponseEntity<?> updateRating(@PathVariable Long id, @RequestParam Integer rating) {
		try {
			RecipeBookResponseDTO recipe = recipeBookService.updateRating(id, rating);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Rating updated successfully!");
			response.put("data", recipe);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getAllRecipes(@PathVariable Long userId) {
		try {
			List<RecipeBookResponseDTO> recipes = recipeBookService.getAllRecipes(userId);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", recipes);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getRecipeById(@PathVariable Long id) {
		try {
			RecipeBookResponseDTO recipe = recipeBookService.getRecipeById(id);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", recipe);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/user/{userId}/favorites")
	public ResponseEntity<?> getFavoriteRecipes(@PathVariable Long userId) {
		try {
			List<RecipeBookResponseDTO> recipes = recipeBookService.getFavoriteRecipes(userId);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", recipes);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/user/{userId}/category/{category}")
	public ResponseEntity<?> getRecipesByCategory(@PathVariable Long userId, @PathVariable String category) {
		try {
			List<RecipeBookResponseDTO> recipes = recipeBookService.getRecipesByCategory(userId, category);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", recipes);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/user/{userId}/cuisine/{cuisineType}")
	public ResponseEntity<?> getRecipesByCuisine(@PathVariable Long userId, @PathVariable String cuisineType) {
		try {
			List<RecipeBookResponseDTO> recipes = recipeBookService.getRecipesByCuisine(userId, cuisineType);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", recipes);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/user/{userId}/difficulty/{difficultyLevel}")
	public ResponseEntity<?> getRecipesByDifficulty(@PathVariable Long userId, @PathVariable String difficultyLevel) {
		try {
			List<RecipeBookResponseDTO> recipes = recipeBookService.getRecipesByDifficulty(userId, difficultyLevel);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", recipes);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/user/{userId}/rating/{rating}")
	public ResponseEntity<?> getRecipesByRating(@PathVariable Long userId, @PathVariable Integer rating) {
		try {
			List<RecipeBookResponseDTO> recipes = recipeBookService.getRecipesByRating(userId, rating);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", recipes);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/user/{userId}/search")
	public ResponseEntity<?> searchRecipes(@PathVariable Long userId, @RequestParam String query) {
		try {
			List<RecipeBookResponseDTO> recipes = recipeBookService.searchRecipes(userId, query);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("data", recipes);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(e.getMessage()));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteRecipe(@PathVariable Long id) {
		try {
			recipeBookService.deleteRecipe(id);
			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("message", "Recipe deleted successfully!");
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
