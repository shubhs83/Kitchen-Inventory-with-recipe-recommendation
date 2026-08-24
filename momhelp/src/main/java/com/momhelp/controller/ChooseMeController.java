package com.momhelp.controller;

import com.momhelp.dto.VegetableResponseDTO;
import com.momhelp.service.VegetableService;
import com.momhelp.service.SpoonacularService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/choose-me")
@CrossOrigin(origins = "http://localhost:3000")
public class ChooseMeController {

	@Autowired
	private VegetableService vegetableService;

	@Autowired
	private SpoonacularService spoonacularService;

	@GetMapping("/available-vegetables")
	public ResponseEntity<List<VegetableResponseDTO>> getAvailableVegetables() {
		List<VegetableResponseDTO> vegetables = vegetableService.getAvailableVegetables();
		return ResponseEntity.ok(vegetables);
	}

	@GetMapping("/dishes/{vegetableId}")
	public ResponseEntity<?> getDishesForVegetable(@PathVariable Long vegetableId,
			@RequestParam(defaultValue = "en") String language) {
		try {
			VegetableResponseDTO vegetable = vegetableService.getVegetableById(vegetableId);

			List<Map<String, Object>> dishes = spoonacularService.searchRecipesByIngredient(vegetable.getName(),
					language);

			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("vegetableId", vegetableId);
			response.put("vegetableName", vegetable.getName());
			response.put("dishes", dishes);
			response.put("language", language);

			return ResponseEntity.ok(response);
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
		}
	}

	@GetMapping("/recipe/{recipeId}")
	public ResponseEntity<?> getRecipeForDish(@PathVariable Long recipeId,
			@RequestParam(defaultValue = "en") String language) {
		try {
			Map<String, Object> recipe = spoonacularService.getRecipeDetails(recipeId, language);

			if (recipe.isEmpty()) {
				return ResponseEntity.badRequest().body(createErrorResponse("Recipe not found"));
			}

			Map<String, Object> response = new HashMap<>();
			response.put("success", true);
			response.put("recipe", recipe);
			response.put("language", language);

			return ResponseEntity.ok(response);
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
		}
	}

	private Map<String, Object> createErrorResponse(String message) {
		Map<String, Object> errorResponse = new HashMap<>();
		errorResponse.put("success", false);
		errorResponse.put("message", message);
		return errorResponse;
	}
}