package com.momhelp.controller;

import com.momhelp.entity.SpecialDish;
import com.momhelp.repository.SpecialDishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/auto-suggest")
@CrossOrigin(origins = "http://localhost:3000")
public class AutoSuggestController {
    
    @Autowired
    private SpecialDishRepository specialDishRepository;
    
    // Get ONE random special dish with details (NO vegetable selection needed)
    @GetMapping("/random-dish")
    public ResponseEntity<?> getRandomSpecialDish() {
        try {
            // Get a random special dish from database
            List<SpecialDish> randomDishes = specialDishRepository.findRandomSpecialDishes(PageRequest.of(0, 1));
            
            if (randomDishes.isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(createErrorResponse("No special dishes available. Please add some to the database."));
            }
            
            SpecialDish dish = randomDishes.get(0);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("dish", convertToMap(dish));
            response.put("message", "✨ Here's something special for you today!");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.err.println("Error in getRandomSpecialDish: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500)
                .body(createErrorResponse("Failed to get suggestion: " + e.getMessage()));
        }
    }
    
    // Get multiple random dishes for variety
    @GetMapping("/random-dishes/{count}")
    public ResponseEntity<?> getMultipleRandomDishes(@PathVariable int count) {
        try {
            List<SpecialDish> randomDishes = specialDishRepository.findRandomSpecialDishes(PageRequest.of(0, count));
            
            List<Map<String, Object>> dishes = new ArrayList<>();
            randomDishes.forEach(dish -> dishes.add(convertToMap(dish)));
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("dishes", dishes);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(createErrorResponse(e.getMessage()));
        }
    }
    
    // Get dishes by category
    @GetMapping("/by-category/{category}")
    public ResponseEntity<?> getDishesByCategory(@PathVariable String category) {
        try {
            List<SpecialDish> dishes = specialDishRepository.findByCategory(category.toUpperCase());
            
            if (dishes.isEmpty()) {
                return ResponseEntity.ok(Collections.emptyMap());
            }
            
            // Pick random dish from this category
            Random random = new Random();
            SpecialDish dish = dishes.get(random.nextInt(dishes.size()));
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("dish", convertToMap(dish));
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(createErrorResponse(e.getMessage()));
        }
    }
    
    // Helper method
    private Map<String, Object> convertToMap(SpecialDish dish) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", dish.getId());
        map.put("dishName", dish.getDishName());
        map.put("description", dish.getDescription());
        map.put("category", dish.getCategory());
        map.put("cuisineType", dish.getCuisineType());
        map.put("mainIngredient", dish.getMainIngredient());
        map.put("difficultyLevel", dish.getDifficultyLevel());
        map.put("prepTime", dish.getPrepTime());
        map.put("cookTime", dish.getCookTime());
        map.put("servings", dish.getServings());
        map.put("readyInMinutes", (dish.getPrepTime() != null ? dish.getPrepTime() : 0) + 
                                   (dish.getCookTime() != null ? dish.getCookTime() : 0));
        return map;
    }
    
    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("message", message);
        return errorResponse;
    }
}