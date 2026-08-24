package com.momhelp.dto;

import jakarta.validation.constraints.*;
import java.util.List;

public class AiRecipeRequestDTO {

	@NotNull(message = "Vegetables list is required")
	@Size(min = 1, message = "At least one vegetable is required")
	private List<String> vegetables;

	@NotNull(message = "Meal type is required")
	@Pattern(regexp = "BREAKFAST|LUNCH_DINNER|DESSERT", message = "Meal type must be BREAKFAST, LUNCH_DINNER, or DESSERT")
	private String mealType;

	@NotNull(message = "Number of servings is required")
	@Min(value = 1, message = "Servings must be at least 1")
	@Max(value = 20, message = "Servings cannot exceed 20")
	private Integer servings;

	@NotNull(message = "Language is required")
	@Pattern(regexp = "EN|HI|TE", message = "Language must be EN (English), HI (Hindi), or TE (Telugu)")
	private String language;

	// Constructors
	public AiRecipeRequestDTO() {
	}

	public AiRecipeRequestDTO(List<String> vegetables, String mealType, Integer servings, String language) {
		this.vegetables = vegetables;
		this.mealType = mealType;
		this.servings = servings;
		this.language = language;
	}

	// Getters and Setters
	public List<String> getVegetables() {
		return vegetables;
	}

	public void setVegetables(List<String> vegetables) {
		this.vegetables = vegetables;
	}

	public String getMealType() {
		return mealType;
	}

	public void setMealType(String mealType) {
		this.mealType = mealType;
	}

	public Integer getServings() {
		return servings;
	}

	public void setServings(Integer servings) {
		this.servings = servings;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
}