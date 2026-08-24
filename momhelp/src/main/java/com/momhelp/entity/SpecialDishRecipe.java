package com.momhelp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "special_dish_recipes")
public class SpecialDishRecipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "special_dish_id", nullable = false)
	private SpecialDish specialDish;

	@Column(name = "ingredients", columnDefinition = "TEXT")
	private String ingredients;

	@Column(name = "instructions", columnDefinition = "TEXT", nullable = false)
	private String instructions;

	@Column(name = "tips", columnDefinition = "TEXT")
	private String tips;

	// Constructors
	public SpecialDishRecipe() {
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SpecialDish getSpecialDish() {
		return specialDish;
	}

	public void setSpecialDish(SpecialDish specialDish) {
		this.specialDish = specialDish;
	}

	public String getIngredients() {
		return ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	@Override
	public String toString() {
		return "SpecialDishRecipe [id=" + id + ", ingredients=" + ingredients + ", instructions=" + instructions
				+ ", tips=" + tips + "]";
	}
}