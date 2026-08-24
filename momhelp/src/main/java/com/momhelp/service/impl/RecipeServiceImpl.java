package com.momhelp.service.impl;

import com.momhelp.dto.IngredientDTO;
import com.momhelp.dto.RecipeResponseDTO;
import com.momhelp.entity.Recipe;
import com.momhelp.repository.DishRepository;
import com.momhelp.repository.RecipeRepository;
import com.momhelp.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
public class RecipeServiceImpl implements RecipeService {
    
    @Autowired
    private RecipeRepository recipeRepository;
    
    @Autowired
    private DishRepository dishRepository;
    
    @Override
    public RecipeResponseDTO getRecipeByDishId(Long dishId) {
        // Check if dish exists
        if (!dishRepository.existsById(dishId)) {
            throw new RuntimeException("Dish not found with id: " + dishId);
        }
        
        Recipe recipe = recipeRepository.findByDishIdWithIngredients(dishId)
                .orElseThrow(() -> new RuntimeException("Recipe not found for dish id: " + dishId));
        
        return convertToResponseDTO(recipe);
    }
    
    @Override
    public RecipeResponseDTO getRecipeById(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + recipeId));
        
        // Load ingredients if not already loaded
        if (recipe.getIngredients() == null || recipe.getIngredients().isEmpty()) {
            recipe = recipeRepository.findByDishIdWithIngredients(recipe.getDish().getId())
                    .orElseThrow(() -> new RuntimeException("Recipe not found"));
        }
        
        return convertToResponseDTO(recipe);
    }
    
    // Helper method to convert Entity to Response DTO
    private RecipeResponseDTO convertToResponseDTO(Recipe recipe) {
        // Convert ingredients to DTOs
        java.util.List<IngredientDTO> ingredientDTOs = recipe.getIngredients().stream()
                .map(ingredient -> new IngredientDTO(
                        ingredient.getId(),
                        ingredient.getIngredientName(),
                        ingredient.getQuantity(),
                        ingredient.getUnit()
                ))
                .collect(Collectors.toList());
        
        return new RecipeResponseDTO(
                recipe.getId(),
                recipe.getDish().getId(),
                recipe.getDish().getDishName(),
                recipe.getDish().getVegetableName(),
                recipe.getInstructions(),
                ingredientDTOs
        );
    }
}