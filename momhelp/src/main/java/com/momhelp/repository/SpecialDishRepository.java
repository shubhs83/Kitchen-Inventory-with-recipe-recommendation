package com.momhelp.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.momhelp.entity.SpecialDish;

@Repository
public interface SpecialDishRepository extends JpaRepository<SpecialDish, Long> {

	// Find by main ingredient
	List<SpecialDish> findByMainIngredientContainingIgnoreCase(String ingredient);

	// Find by category
	List<SpecialDish> findByCategory(String category);

	// Find by cuisine type
	List<SpecialDish> findByCuisineType(String cuisineType);

	@Query(
		    value = "SELECT * FROM special_dishes ORDER BY RAND()",
		    nativeQuery = true
		)
		List<SpecialDish> findRandomSpecialDishes(Pageable pageable);


	// Get popular dishes
	@Query("SELECT s FROM SpecialDish s ORDER BY s.popularityScore DESC")
	List<SpecialDish> findPopularDishes();
}