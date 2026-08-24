package com.momhelp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ingredients")
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "recipe_id", nullable = false)
	private Recipe recipe;

	@Column(name = "ingredient_name", nullable = false, length = 200)
	private String ingredientName;

	@Column(name = "quantity", length = 50)
	private String quantity;

	@Column(name = "unit", length = 20)
	private String unit;

	// Constructors
	public Ingredient() {
	}

	public Ingredient(Recipe recipe, String ingredientName, String quantity, String unit) {
		this.recipe = recipe;
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

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
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