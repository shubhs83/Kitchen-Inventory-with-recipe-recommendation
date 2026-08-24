package com.momhelp.dto;

public class SeasonalDishDTO {
	private Long id;
	private String dishName;
	private String description;
	private String season;
	private String ingredients;
	private String instructions;
	private Integer prepTime;
	private Integer cookTime;
	private String imageUrl;

	// Constructors
	public SeasonalDishDTO() {
	}

	public SeasonalDishDTO(Long id, String dishName, String description, String season, String ingredients,
			String instructions, Integer prepTime, Integer cookTime, String imageUrl) {
		this.id = id;
		this.dishName = dishName;
		this.description = description;
		this.season = season;
		this.ingredients = ingredients;
		this.instructions = instructions;
		this.prepTime = prepTime;
		this.cookTime = cookTime;
		this.imageUrl = imageUrl;
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