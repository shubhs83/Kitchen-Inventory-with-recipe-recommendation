package com.momhelp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "special_dishes")
public class SpecialDish {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "dish_name", nullable = false, length = 200)
	private String dishName;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@Column(name = "category", length = 50)
	private String category;

	@Column(name = "cuisine_type", length = 50)
	private String cuisineType;

	@Column(name = "main_ingredient", length = 100)
	private String mainIngredient;

	@Column(name = "difficulty_level", length = 20)
	private String difficultyLevel;

	@Column(name = "prep_time")
	private Integer prepTime;

	@Column(name = "cook_time")
	private Integer cookTime;

	@Column(name = "servings")
	private Integer servings;

	@Column(name = "is_special")
	private Boolean isSpecial = true;

	@Column(name = "popularity_score")
	private Integer popularityScore = 0;

	// Constructors
	public SpecialDish() {
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCuisineType() {
		return cuisineType;
	}

	public void setCuisineType(String cuisineType) {
		this.cuisineType = cuisineType;
	}

	public String getMainIngredient() {
		return mainIngredient;
	}

	public void setMainIngredient(String mainIngredient) {
		this.mainIngredient = mainIngredient;
	}

	public String getDifficultyLevel() {
		return difficultyLevel;
	}

	public void setDifficultyLevel(String difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
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

	public Integer getServings() {
		return servings;
	}

	public void setServings(Integer servings) {
		this.servings = servings;
	}

	public Boolean getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(Boolean isSpecial) {
		this.isSpecial = isSpecial;
	}

	public Integer getPopularityScore() {
		return popularityScore;
	}

	public void setPopularityScore(Integer popularityScore) {
		this.popularityScore = popularityScore;
	}
}