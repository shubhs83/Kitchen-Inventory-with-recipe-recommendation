package com.momhelp.service;

import com.momhelp.dto.DishDTO;
import java.util.List;

public interface DishService {

	// Get all dishes
	List<DishDTO> getAllDishes();

	// Get dish by ID
	DishDTO getDishById(Long id);

	// Get dishes by vegetable ID
	List<DishDTO> getDishesByVegetableId(Long vegetableId);

	// Get dishes by vegetable name
	List<DishDTO> getDishesByVegetableName(String vegetableName);

	// Get dishes for available vegetables (not spoiled, not expired)
	List<DishDTO> getDishesForAvailableVegetables();

	// Get available dishes for a specific vegetable
	List<DishDTO> getAvailableDishesByVegetableId(Long vegetableId);

	// Get all vegetables that have dishes (for Choose Me section)
	List<String> getVegetablesWithDishes();
}