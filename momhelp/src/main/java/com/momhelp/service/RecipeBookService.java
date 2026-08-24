package com.momhelp.service;

import com.momhelp.dto.RecipeBookRequestDTO;
import com.momhelp.dto.RecipeBookResponseDTO;

import java.util.List;

public interface RecipeBookService {

	RecipeBookResponseDTO addRecipe(RecipeBookRequestDTO requestDTO);

	RecipeBookResponseDTO updateRecipe(Long id, RecipeBookRequestDTO requestDTO);

	RecipeBookResponseDTO toggleFavorite(Long id);

	RecipeBookResponseDTO updateRating(Long id, Integer rating);

	List<RecipeBookResponseDTO> getAllRecipes(Long userId);

	List<RecipeBookResponseDTO> getFavoriteRecipes(Long userId);

	List<RecipeBookResponseDTO> getRecipesByCategory(Long userId, String category);

	List<RecipeBookResponseDTO> getRecipesByCuisine(Long userId, String cuisineType);

	List<RecipeBookResponseDTO> getRecipesByDifficulty(Long userId, String difficultyLevel);

	List<RecipeBookResponseDTO> getRecipesByRating(Long userId, Integer rating);

	List<RecipeBookResponseDTO> searchRecipes(Long userId, String query);

	RecipeBookResponseDTO getRecipeById(Long id);

	void deleteRecipe(Long id);
}