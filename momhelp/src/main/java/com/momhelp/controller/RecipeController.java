package com.momhelp.controller;

import com.momhelp.dto.RecipeResponseDTO;
import com.momhelp.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/recipes")
@CrossOrigin(origins = "http://localhost:3000")
public class RecipeController {
    
    @Autowired
    private RecipeService recipeService;
    
    // Get recipe by dish ID
    @GetMapping("/by-dish/{dishId}")
    public ResponseEntity<?> getRecipeByDishId(@PathVariable Long dishId) {
        try {
            RecipeResponseDTO recipe = recipeService.getRecipeByDishId(dishId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", recipe);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
        }
    }
    
    // Get recipe by recipe ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getRecipeById(@PathVariable Long id) {
        try {
            RecipeResponseDTO recipe = recipeService.getRecipeById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", recipe);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
        }
    }
    
    // Helper method to create error response
    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("message", message);
        return errorResponse;
    }
}