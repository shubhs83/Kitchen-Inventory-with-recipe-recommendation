package com.momhelp.repository;

import com.momhelp.entity.RecipeBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeBookRepository extends JpaRepository<RecipeBook, Long> {

	List<RecipeBook> findByUserId(Long userId);

	List<RecipeBook> findByUserIdAndIsFavorite(Long userId, Boolean isFavorite);

	List<RecipeBook> findByUserIdAndCategory(Long userId, String category);

	List<RecipeBook> findByUserIdAndCuisineType(Long userId, String cuisineType);

	List<RecipeBook> findByUserIdAndDifficultyLevel(Long userId, String difficultyLevel);

	List<RecipeBook> findByUserIdAndRating(Long userId, Integer rating);

	List<RecipeBook> findByUserIdAndRecipeNameContainingIgnoreCase(Long userId, String recipeName);
}