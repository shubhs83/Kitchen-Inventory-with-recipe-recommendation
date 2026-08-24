package com.momhelp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class NutritionRequestDTO {

	@NotBlank(message = "Food item name is required")
	private String foodItem;

	@Positive(message = "Quantity must be positive")
	private Double quantity;

	private String unit; // grams, kg, piece, etc.

	public NutritionRequestDTO() {
	}

	public NutritionRequestDTO(String foodItem, Double quantity, String unit) {
		this.foodItem = foodItem;
		this.quantity = quantity;
		this.unit = unit;
	}

	public String getFoodItem() {
		return foodItem;
	}

	public void setFoodItem(String foodItem) {
		this.foodItem = foodItem;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}