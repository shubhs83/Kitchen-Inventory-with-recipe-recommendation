package com.momhelp.repository;

import com.momhelp.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

	// Find recipe by dish ID
	Optional<Recipe> findByDishId(Long dishId);

	// Find recipe with ingredients by dish ID
	@Query("SELECT r FROM Recipe r LEFT JOIN FETCH r.ingredients WHERE r.dish.id = :dishId")
	Optional<Recipe> findByDishIdWithIngredients(Long dishId);

	// Check if recipe exists for dish
	boolean existsByDishId(Long dishId);
}