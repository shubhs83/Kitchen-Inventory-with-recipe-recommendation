package com.momhelp.service.impl;

import com.momhelp.dto.DishDTO;
import com.momhelp.entity.Dish;
import com.momhelp.entity.Vegetable;
import com.momhelp.repository.DishRepository;
import com.momhelp.repository.VegetableRepository;
import com.momhelp.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DishServiceImpl implements DishService {

	@Autowired
	private DishRepository dishRepository;

	@Autowired
	private VegetableRepository vegetableRepository;

	@Override
	public List<DishDTO> getAllDishes() {
		List<Dish> dishes = dishRepository.findAll();
		return dishes.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public DishDTO getDishById(Long id) {
		Dish dish = dishRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Dish not found with id: " + id));
		return convertToDTO(dish);
	}

	@Override
	public List<DishDTO> getDishesByVegetableId(Long vegetableId) {
		// Check if vegetable exists
		if (!vegetableRepository.existsById(vegetableId)) {
			throw new RuntimeException("Vegetable not found with id: " + vegetableId);
		}

		List<Dish> dishes = dishRepository.findByVegetableId(vegetableId);
		return dishes.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<DishDTO> getDishesByVegetableName(String vegetableName) {
		List<Dish> dishes = dishRepository.findByVegetableNameContainingIgnoreCase(vegetableName);
		return dishes.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<DishDTO> getDishesForAvailableVegetables() {
		List<Dish> dishes = dishRepository.findDishesForAvailableVegetables();
		return dishes.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<DishDTO> getAvailableDishesByVegetableId(Long vegetableId) {
		// Check if vegetable exists and is available
		Vegetable vegetable = vegetableRepository.findById(vegetableId)
				.orElseThrow(() -> new RuntimeException("Vegetable not found with id: " + vegetableId));

		if (vegetable.isSpoiled() || vegetable.getUseBeforeDate().before(new java.util.Date())) {
			throw new RuntimeException("Vegetable is not available (spoiled or expired)");
		}

		List<Dish> dishes = dishRepository.findAvailableDishesByVegetableId(vegetableId);
		return dishes.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public List<String> getVegetablesWithDishes() {
		// Get distinct vegetable names that have dishes
		List<Dish> dishes = dishRepository.findAll();
		return dishes.stream().map(Dish::getVegetableName).distinct().collect(Collectors.toList());
	}

	// Helper method to convert Entity to DTO
	private DishDTO convertToDTO(Dish dish) {
		return new DishDTO(dish.getId(), dish.getDishName(), dish.getDescription(), dish.getVegetable().getId(),
				dish.getVegetableName(), dish.getDifficultyLevel(), dish.getPrepTime(), dish.getCookTime(),
				dish.getServings(), dish.getCreatedDate());
	}
}