package com.momhelp.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_preferences")
public class UserPreferences {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id", unique = true)
	private Long userId;

	@Column(name = "dietary_preference", length = 50)
	private String dietaryPreference; // VEGETARIAN, NON_VEGETARIAN, VEGAN

	@Column(name = "spice_level", length = 20)
	private String spiceLevel; // MILD, MEDIUM, HOT

	@Column(name = "allergies", columnDefinition = "TEXT")
	private String allergies; // Comma-separated: nuts, dairy, gluten, etc.

	@Column(name = "favorite_cuisines", columnDefinition = "TEXT")
	private String favoriteCuisines; // Comma-separated: Indian, Chinese, Italian, etc.

	@Column(name = "cooking_skill_level", length = 20)
	private String cookingSkillLevel; // BEGINNER, INTERMEDIATE, ADVANCED

	@Column(name = "preferred_meal_types", columnDefinition = "TEXT")
	private String preferredMealTypes; // BREAKFAST, LUNCH, DINNER, SNACKS

	@Column(name = "avoid_ingredients", columnDefinition = "TEXT")
	private String avoidIngredients; // Ingredients to avoid

	@Column(name = "max_cooking_time")
	private Integer maxCookingTime; // Maximum cooking time in minutes

	@Column(name = "serving_size_preference")
	private Integer servingSizePreference; // Default number of servings

	@Column(name = "language_preference", length = 10)
	private String languagePreference; // EN, HI, TE

	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	public UserPreferences() {
		this.createdDate = new Date();
		this.updatedDate = new Date();
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDietaryPreference() {
		return dietaryPreference;
	}

	public void setDietaryPreference(String dietaryPreference) {
		this.dietaryPreference = dietaryPreference;
	}

	public String getSpiceLevel() {
		return spiceLevel;
	}

	public void setSpiceLevel(String spiceLevel) {
		this.spiceLevel = spiceLevel;
	}

	public String getAllergies() {
		return allergies;
	}

	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}

	public String getFavoriteCuisines() {
		return favoriteCuisines;
	}

	public void setFavoriteCuisines(String favoriteCuisines) {
		this.favoriteCuisines = favoriteCuisines;
	}

	public String getCookingSkillLevel() {
		return cookingSkillLevel;
	}

	public void setCookingSkillLevel(String cookingSkillLevel) {
		this.cookingSkillLevel = cookingSkillLevel;
	}

	public String getPreferredMealTypes() {
		return preferredMealTypes;
	}

	public void setPreferredMealTypes(String preferredMealTypes) {
		this.preferredMealTypes = preferredMealTypes;
	}

	public String getAvoidIngredients() {
		return avoidIngredients;
	}

	public void setAvoidIngredients(String avoidIngredients) {
		this.avoidIngredients = avoidIngredients;
	}

	public Integer getMaxCookingTime() {
		return maxCookingTime;
	}

	public void setMaxCookingTime(Integer maxCookingTime) {
		this.maxCookingTime = maxCookingTime;
	}

	public Integer getServingSizePreference() {
		return servingSizePreference;
	}

	public void setServingSizePreference(Integer servingSizePreference) {
		this.servingSizePreference = servingSizePreference;
	}

	public String getLanguagePreference() {
		return languagePreference;
	}

	public void setLanguagePreference(String languagePreference) {
		this.languagePreference = languagePreference;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
}