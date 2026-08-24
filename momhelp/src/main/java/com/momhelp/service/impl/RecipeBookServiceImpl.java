package com.momhelp.service.impl;

import com.momhelp.dto.RecipeBookRequestDTO;
import com.momhelp.dto.RecipeBookResponseDTO;
import com.momhelp.entity.RecipeBook;
import com.momhelp.repository.RecipeBookRepository;
import com.momhelp.service.RecipeBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecipeBookServiceImpl implements RecipeBookService {

	@Autowired
	private RecipeBookRepository recipeBookRepository;

	@Override
	public RecipeBookResponseDTO addRecipe(RecipeBookRequestDTO requestDTO) {
		RecipeBook recipe = new RecipeBook();
		recipe.setUserId(requestDTO.getUserId());
		recipe.setRecipeName(requestDTO.getRecipeName());
		recipe.setCategory(requestDTO.getCategory());
		recipe.setCuisineType(requestDTO.getCuisineType());
		recipe.setIngredients(requestDTO.getIngredients());
		recipe.setInstructions(requestDTO.getInstructions());
		recipe.setPreparationTime(requestDTO.getPreparationTime());
		recipe.setCookingTime(requestDTO.getCookingTime());
		recipe.setServings(requestDTO.getServings());
		recipe.setDifficultyLevel(requestDTO.getDifficultyLevel());
		recipe.setRating(requestDTO.getRating() != null ? requestDTO.getRating() : 0);
		recipe.setNotes(requestDTO.getNotes());
		recipe.setImageUrl(requestDTO.getImageUrl());
		recipe.setIsFavorite(false);

		RecipeBook saved = recipeBookRepository.save(recipe);
		return convertToDTO(saved);
	}

	@Override
	public RecipeBookResponseDTO updateRecipe(Long id, RecipeBookRequestDTO requestDTO) {
		RecipeBook recipe = recipeBookRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Recipe not found: " + id));

		recipe.setRecipeName(requestDTO.getRecipeName());
		recipe.setCategory(requestDTO.getCategory());
		recipe.setCuisineType(requestDTO.getCuisineType());
		recipe.setIngredients(requestDTO.getIngredients());
		recipe.setInstructions(requestDTO.getInstructions());
		recipe.setPreparationTime(requestDTO.getPreparationTime());
		recipe.setCookingTime(requestDTO.getCookingTime());
		recipe.setServings(requestDTO.getServings());
		recipe.setDifficultyLevel(requestDTO.getDifficultyLevel());
		recipe.setRating(requestDTO.getRating());
		recipe.setNotes(requestDTO.getNotes());
		recipe.setImageUrl(requestDTO.getImageUrl());
		recipe.setUpdatedDate(new Date());

		RecipeBook updated = recipeBookRepository.save(recipe);
		return convertToDTO(updated);
	}

	@Override
	public RecipeBookResponseDTO toggleFavorite(Long id) {
		RecipeBook recipe = recipeBookRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Recipe not found: " + id));

		recipe.setIsFavorite(!recipe.getIsFavorite());
		recipe.setUpdatedDate(new Date());

		RecipeBook updated = recipeBookRepository.save(recipe);
		return convertToDTO(updated);
	}

	@Override
	public RecipeBookResponseDTO updateRating(Long id, Integer rating) {
		RecipeBook recipe = recipeBookRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Recipe not found: " + id));

		recipe.setRating(rating);
		recipe.setUpdatedDate(new Date());

		RecipeBook updated = recipeBookRepository.save(recipe);
		return convertToDTO(updated);
	}

	@Override
	public List<RecipeBookResponseDTO> getAllRecipes(Long userId) {
		List<RecipeBook> recipes = recipeBookRepository.findByUserId(userId);
		return recipes.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<RecipeBookResponseDTO> getFavoriteRecipes(Long userId) {
		List<RecipeBook> recipes = recipeBookRepository.findByUserIdAndIsFavorite(userId, true);
		return recipes.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<RecipeBookResponseDTO> getRecipesByCategory(Long userId, String category) {
		List<RecipeBook> recipes = recipeBookRepository.findByUserIdAndCategory(userId, category);
		return recipes.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<RecipeBookResponseDTO> getRecipesByCuisine(Long userId, String cuisineType) {
		List<RecipeBook> recipes = recipeBookRepository.findByUserIdAndCuisineType(userId, cuisineType);
		return recipes.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<RecipeBookResponseDTO> getRecipesByDifficulty(Long userId, String difficultyLevel) {
		List<RecipeBook> recipes = recipeBookRepository.findByUserIdAndDifficultyLevel(userId, difficultyLevel);
		return recipes.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<RecipeBookResponseDTO> getRecipesByRating(Long userId, Integer rating) {
		List<RecipeBook> recipes = recipeBookRepository.findByUserIdAndRating(userId, rating);
		return recipes.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<RecipeBookResponseDTO> searchRecipes(Long userId, String query) {
		List<RecipeBook> recipes = recipeBookRepository.findByUserIdAndRecipeNameContainingIgnoreCase(userId, query);
		return recipes.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public RecipeBookResponseDTO getRecipeById(Long id) {
		RecipeBook recipe = recipeBookRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Recipe not found: " + id));
		return convertToDTO(recipe);
	}

	@Override
	public void deleteRecipe(Long id) {
		recipeBookRepository.deleteById(id);
	}

	private RecipeBookResponseDTO convertToDTO(RecipeBook entity) {
		RecipeBookResponseDTO dto = new RecipeBookResponseDTO();
		dto.setId(entity.getId());
		dto.setUserId(entity.getUserId());
		dto.setRecipeName(entity.getRecipeName());
		dto.setCategory(entity.getCategory());
		dto.setCuisineType(entity.getCuisineType());
		dto.setIngredients(entity.getIngredients());
		dto.setInstructions(entity.getInstructions());
		dto.setPreparationTime(entity.getPreparationTime());
		dto.setCookingTime(entity.getCookingTime());
		dto.setServings(entity.getServings());
		dto.setDifficultyLevel(entity.getDifficultyLevel());
		dto.setIsFavorite(entity.getIsFavorite());
		dto.setRating(entity.getRating());
		dto.setNotes(entity.getNotes());
		dto.setImageUrl(entity.getImageUrl());
		dto.setCreatedDate(entity.getCreatedDate());
		dto.setUpdatedDate(entity.getUpdatedDate());
		return dto;
	}
}