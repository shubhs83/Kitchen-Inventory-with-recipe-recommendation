package com.momhelp.repository;

import com.momhelp.entity.Dish;
import com.momhelp.entity.Vegetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

	// Find dishes by vegetable ID
	List<Dish> findByVegetableId(Long vegetableId);

	// Find dishes by vegetable name
	List<Dish> findByVegetableNameContainingIgnoreCase(String vegetableName);

	// Find dishes for available vegetables (not spoiled, not expired)
	@Query("SELECT d FROM Dish d WHERE d.vegetable.id IN "
			+ "(SELECT v.id FROM Vegetable v WHERE v.isSpoiled = false AND v.useBeforeDate >= CURRENT_DATE)")
	List<Dish> findDishesForAvailableVegetables();

	// Find dishes by specific vegetable that is available
	@Query("SELECT d FROM Dish d WHERE d.vegetable.id = :vegetableId "
			+ "AND d.vegetable.id IN (SELECT v.id FROM Vegetable v WHERE v.isSpoiled = false AND v.useBeforeDate >= CURRENT_DATE)")
	List<Dish> findAvailableDishesByVegetableId(Long vegetableId);
}