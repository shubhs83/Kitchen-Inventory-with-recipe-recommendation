package com.momhelp.dto;

import java.util.List;

public class RecipeResponseDTO {
	private Long id;
	private Long dishId;
	private String dishName;
	private String vegetableName;
	private String instructions;
	private List<IngredientDTO> ingredients;

	// Constructors
	public RecipeResponseDTO() {
	}

	public RecipeResponseDTO(Long id, Long dishId, String dishName, String vegetableName, String instructions,
			List<IngredientDTO> ingredients) {
		this.id = id;
		this.dishId = dishId;
		this.dishName = dishName;
		this.vegetableName = vegetableName;
		this.instructions = instructions;
		this.ingredients = ingredients;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDishId() {
		return dishId;
	}

	public void setDishId(Long dishId) {
		this.dishId = dishId;
	}

	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	public String getVegetableName() {
		return vegetableName;
	}

	public void setVegetableName(String vegetableName) {
		this.vegetableName = vegetableName;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public List<IngredientDTO> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<IngredientDTO> ingredients) {
		this.ingredients = ingredients;
	}
}