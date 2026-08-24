package com.momhelp.dto;

public class IngredientDTO {
	private Long id;
	private String ingredientName;
	private String quantity;
	private String unit;

	// Constructors
	public IngredientDTO() {
	}

	public IngredientDTO(Long id, String ingredientName, String quantity, String unit) {
		this.id = id;
		this.ingredientName = ingredientName;
		this.quantity = quantity;
		this.unit = unit;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIngredientName() {
		return ingredientName;
	}

	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
}