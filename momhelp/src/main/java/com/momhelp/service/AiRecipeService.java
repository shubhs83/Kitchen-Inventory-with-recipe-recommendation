package com.momhelp.service;

import com.momhelp.dto.AiRecipeRequestDTO;
import com.momhelp.dto.AiRecipeResponseDTO;
import java.util.List;

public interface AiRecipeService {

	// Generate new recipe using AI
	AiRecipeResponseDTO generateRecipe(AiRecipeRequestDTO requestDTO);

	// Get recipe by ID
	AiRecipeResponseDTO getRecipeById(Long id);

	// Get all generated recipes
	List<AiRecipeResponseDTO> getAllRecipes();

	// Get favorite recipes
	List<AiRecipeResponseDTO> getFavoriteRecipes();

	// Mark recipe as favorite
	AiRecipeResponseDTO toggleFavorite(Long id);

	// Delete recipe
	void deleteRecipe(Long id);

	// Get recent recipes
	List<AiRecipeResponseDTO> getRecentRecipes();
}