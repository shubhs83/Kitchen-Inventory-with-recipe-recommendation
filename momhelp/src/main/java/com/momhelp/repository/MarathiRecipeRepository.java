package com.momhelp.repository;

import com.momhelp.entity.MarathiRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarathiRecipeRepository extends JpaRepository<MarathiRecipe, Long> {

	Optional<MarathiRecipe> findBySpoonacularRecipeId(Long spoonacularRecipeId);

	List<MarathiRecipe> findByVegetableNameIgnoreCase(String vegetableName);

	List<MarathiRecipe> findByIsCustomRecipeTrue();

	List<MarathiRecipe> findByVegetableNameIgnoreCaseAndIsCustomRecipeTrue(String vegetableName);

	boolean existsBySpoonacularRecipeId(Long spoonacularRecipeId);

	@Query("SELECT DISTINCT mr.vegetableName FROM MarathiRecipe mr")
	List<String> findDistinctVegetableNames();
}