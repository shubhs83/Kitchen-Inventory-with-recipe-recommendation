package com.momhelp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "seasonal_dishes")
public class SeasonalDish {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "dish_name", nullable = false)
	private String dishName;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@Column(name = "season", nullable = false)
	private String season; // SUMMER, RAINY, WINTER

	@Column(name = "ingredients", columnDefinition = "TEXT")
	private String ingredients;

	@Column(name = "instructions", columnDefinition = "TEXT")
	private String instructions;

	@Column(name = "prep_time")
	private Integer prepTime;

	@Column(name = "cook_time")
	private Integer cookTime;

	@Column(name = "image_url")
	private String imageUrl;

	// Constructors
	public SeasonalDish() {
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
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

	public Integer getPrepTime() {
		return prepTime;
	}

	public void setPrepTime(Integer prepTime) {
		this.prepTime = prepTime;
	}

	public Integer getCookTime() {
		return cookTime;
	}

	public void setCookTime(Integer cookTime) {
		this.cookTime = cookTime;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}