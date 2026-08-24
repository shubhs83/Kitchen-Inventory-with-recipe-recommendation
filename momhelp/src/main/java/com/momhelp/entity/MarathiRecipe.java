package com.momhelp.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "marathi_recipes")
public class MarathiRecipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "spoonacular_recipe_id", unique = true)
	private Long spoonacularRecipeId;

	@Column(name = "vegetable_name", length = 100)
	private String vegetableName;

	@Column(name = "dish_name_english", length = 200)
	private String dishNameEnglish;

	@Column(name = "dish_name_marathi", length = 200)
	private String dishNameMarathi;

	@Column(name = "instructions_english", columnDefinition = "TEXT")
	private String instructionsEnglish;

	@Column(name = "instructions_marathi", columnDefinition = "TEXT")
	private String instructionsMarathi;

	@Column(name = "ingredients_english", columnDefinition = "TEXT")
	private String ingredientsEnglish;

	@Column(name = "ingredients_marathi", columnDefinition = "TEXT")
	private String ingredientsMarathi;

	@Column(name = "is_custom_recipe")
	private Boolean isCustomRecipe = false;

	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "last_updated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdated;

	public MarathiRecipe() {
		this.createdDate = new Date();
		this.lastUpdated = new Date();
	}

	public MarathiRecipe(Long spoonacularRecipeId, String vegetableName, String dishNameEnglish, String dishNameMarathi,
			String instructionsEnglish, String instructionsMarathi, String ingredientsEnglish,
			String ingredientsMarathi, Boolean isCustomRecipe) {
		this.spoonacularRecipeId = spoonacularRecipeId;
		this.vegetableName = vegetableName;
		this.dishNameEnglish = dishNameEnglish;
		this.dishNameMarathi = dishNameMarathi;
		this.instructionsEnglish = instructionsEnglish;
		this.instructionsMarathi = instructionsMarathi;
		this.ingredientsEnglish = ingredientsEnglish;
		this.ingredientsMarathi = ingredientsMarathi;
		this.isCustomRecipe = isCustomRecipe;
		this.createdDate = new Date();
		this.lastUpdated = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSpoonacularRecipeId() {
		return spoonacularRecipeId;
	}

	public void setSpoonacularRecipeId(Long spoonacularRecipeId) {
		this.spoonacularRecipeId = spoonacularRecipeId;
	}

	public String getVegetableName() {
		return vegetableName;
	}

	public void setVegetableName(String vegetableName) {
		this.vegetableName = vegetableName;
	}

	public String getDishNameEnglish() {
		return dishNameEnglish;
	}

	public void setDishNameEnglish(String dishNameEnglish) {
		this.dishNameEnglish = dishNameEnglish;
	}

	public String getDishNameMarathi() {
		return dishNameMarathi;
	}

	public void setDishNameMarathi(String dishNameMarathi) {
		this.dishNameMarathi = dishNameMarathi;
	}

	public String getInstructionsEnglish() {
		return instructionsEnglish;
	}

	public void setInstructionsEnglish(String instructionsEnglish) {
		this.instructionsEnglish = instructionsEnglish;
	}

	public String getInstructionsMarathi() {
		return instructionsMarathi;
	}

	public void setInstructionsMarathi(String instructionsMarathi) {
		this.instructionsMarathi = instructionsMarathi;
	}

	public String getIngredientsEnglish() {
		return ingredientsEnglish;
	}

	public void setIngredientsEnglish(String ingredientsEnglish) {
		this.ingredientsEnglish = ingredientsEnglish;
	}

	public String getIngredientsMarathi() {
		return ingredientsMarathi;
	}

	public void setIngredientsMarathi(String ingredientsMarathi) {
		this.ingredientsMarathi = ingredientsMarathi;
	}

	public Boolean getIsCustomRecipe() {
		return isCustomRecipe;
	}

	public void setIsCustomRecipe(Boolean isCustomRecipe) {
		this.isCustomRecipe = isCustomRecipe;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
}