package com.momhelp.repository;

import com.momhelp.entity.AiGeneratedRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AiRecipeRepository extends JpaRepository<AiGeneratedRecipe, Long> {

	// Find all favorite recipes
	List<AiGeneratedRecipe> findByIsFavoriteTrue();

	// Find recipes by meal type
	List<AiGeneratedRecipe> findByMealType(String mealType);

	// Find recipes by language
	List<AiGeneratedRecipe> findByLanguage(String language);

	// Find recent recipes (last 10)
	@Query("SELECT a FROM AiGeneratedRecipe a ORDER BY a.createdDate DESC")
	List<AiGeneratedRecipe> findRecentRecipes();

	// Find recipes containing specific vegetable
	@Query("SELECT a FROM AiGeneratedRecipe a WHERE a.vegetablesUsed LIKE %?1%")
	List<AiGeneratedRecipe> findByVegetableName(String vegetableName);
}