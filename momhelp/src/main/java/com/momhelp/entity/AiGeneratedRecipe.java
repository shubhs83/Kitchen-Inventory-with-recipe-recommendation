package com.momhelp.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ai_generated_recipes")
public class AiGeneratedRecipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "recipe_name", nullable = false, length = 300)
	private String recipeName;

	@Column(name = "ingredients", columnDefinition = "TEXT", nullable = false)
	private String ingredients;

	@Column(name = "instructions", columnDefinition = "TEXT", nullable = false)
	private String instructions;

	@Column(name = "meal_type", length = 50)
	private String mealType; // BREAKFAST, LUNCH_DINNER, DESSERT

	@Column(name = "servings")
	private Integer servings;

	@Column(name = "prep_time")
	private Integer prepTime;

	@Column(name = "cook_time")
	private Integer cookTime;

	@Column(name = "total_time")
	private Integer totalTime;

	@Column(name = "language", length = 10)
	private String language; // EN, HI, TE

	@Column(name = "nutritional_info", columnDefinition = "TEXT")
	private String nutritionalInfo;

	@Column(name = "vegetables_used", columnDefinition = "TEXT")
	private String vegetablesUsed;

	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "is_favorite")
	private Boolean isFavorite = false;

	// Constructors
	public AiGeneratedRecipe() {
		this.createdDate = new Date();
	}

	public AiGeneratedRecipe(String recipeName, String ingredients, String instructions, String mealType,
			Integer servings, String language, String vegetablesUsed) {
		this.recipeName = recipeName;
		this.ingredients = ingredients;
		this.instructions = instructions;
		this.mealType = mealType;
		this.servings = servings;
		this.language = language;
		this.vegetablesUsed = vegetablesUsed;
		this.createdDate = new Date();
		this.isFavorite = false;
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