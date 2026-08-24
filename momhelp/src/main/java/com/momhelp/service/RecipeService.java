package com.momhelp.service;

import com.momhelp.dto.RecipeResponseDTO;

public interface RecipeService {

	// Get recipe by dish ID
	RecipeResponseDTO getRecipeByDishId(Long dishId);

	// Get recipe by recipe ID
	RecipeResponseDTO getRecipeById(Long recipeId);
}