package com.momhelp.dto;

import java.util.Date;

public class AiRecipeResponseDTO {

	private Long id;
	private String recipeName;
	private String ingredients;
	private String instructions;
	private String mealType;
	private Integer servings;
	private Integer prepTime;
	private Integer cookTime;
	private Integer totalTime;
	private String language;
	private String nutritionalInfo;
	private String vegetablesUsed;
	private Date createdDate;
	private Boolean isFavorite;

	// Constructors
	public AiRecipeResponseDTO() {
	}

	public AiRecipeResponseDTO(Long id, String recipeName, String ingredients, String instructions, String mealType,
			Integer servings, Integer prepTime, Integer cookTime, Integer totalTime, String language,
			String nutritionalInfo, String vegetablesUsed, Date createdDate, Boolean isFavorite) {
		this.id = id;
		this.recipeName = recipeName;
		this.ingredients = ingredients;
		this.instructions = instructions;
		this.mealType = mealType;
		this.servings = servings;
		this.prepTime = prepTime;
		this.cookTime = cookTime;
		this.totalTime = totalTime;
		this.language = language;
		this.nutritionalInfo = nutritionalInfo;
		this.vegetablesUsed = vegetablesUsed;
		this.createdDate = createdDate;
		this.isFavorite = isFavorite;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRecipeName() {
		return recipeName;
	}

	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
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

	public Integer getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Integer totalTime) {
		this.totalTime = totalTime;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getNutritionalInfo() {
		return nutritionalInfo;
	}

	public void setNutritionalInfo(String nutritionalInfo) {
		this.nutritionalInfo = nutritionalInfo;
	}

	public String getVegetablesUsed() {
		return vegetablesUsed;
	}

	public void setVegetablesUsed(String vegetablesUsed) {
		this.vegetablesUsed = vegetablesUsed;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Boolean getIsFavorite() {
		return isFavorite;
	}

	public void setIsFavorite(Boolean isFavorite) {
		this.isFavorite = isFavorite;
	}
}