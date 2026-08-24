package com.momhelp.dto;

import java.util.Date;

public class DishDTO {
	private Long id;
	private String dishName;
	private String description;
	private Long vegetableId;
	private String vegetableName;
	private String difficultyLevel;
	private Integer prepTime;
	private Integer cookTime;
	private Integer servings;
	private Date createdDate;

	// Constructors
	public DishDTO() {
	}

	public DishDTO(Long id, String dishName, String description, Long vegetableId, String vegetableName,
			String difficultyLevel, Integer prepTime, Integer cookTime, Integer servings, Date createdDate) {
		this.id = id;
		this.dishName = dishName;
		this.description = description;
		this.vegetableId = vegetableId;
		this.vegetableName = vegetableName;
		this.difficultyLevel = difficultyLevel;
		this.prepTime = prepTime;
		this.cookTime = cookTime;
		this.servings = servings;
		this.createdDate = createdDate;
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

	public Long getVegetableId() {
		return vegetableId;
	}

	public void setVegetableId(Long vegetableId) {
		this.vegetableId = vegetableId;
	}

	public String getVegetableName() {
		return vegetableName;
	}

	public void setVegetableName(String vegetableName) {
		this.vegetableName = vegetableName;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}