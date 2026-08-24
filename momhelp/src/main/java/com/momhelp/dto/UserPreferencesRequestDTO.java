package com.momhelp.dto;

import java.util.List;

public class UserPreferencesRequestDTO {

	private Long userId;
	private String dietaryPreference;
	private String spiceLevel;
	private List<String> allergies;
	private List<String> favoriteCuisines;
	private String cookingSkillLevel;
	private List<String> preferredMealTypes;
	private List<String> avoidIngredients;
	private Integer maxCookingTime;
	private Integer servingSizePreference;
	private String languagePreference;

	public UserPreferencesRequestDTO() {
	}

	// Getters and Setters
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

	public List<String> getAllergies() {
		return allergies;
	}

	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
	}

	public List<String> getFavoriteCuisines() {
		return favoriteCuisines;
	}

	public void setFavoriteCuisines(List<String> favoriteCuisines) {
		this.favoriteCuisines = favoriteCuisines;
	}

	public String getCookingSkillLevel() {
		return cookingSkillLevel;
	}

	public void setCookingSkillLevel(String cookingSkillLevel) {
		this.cookingSkillLevel = cookingSkillLevel;
	}

	public List<String> getPreferredMealTypes() {
		return preferredMealTypes;
	}

	public void setPreferredMealTypes(List<String> preferredMealTypes) {
		this.preferredMealTypes = preferredMealTypes;
	}

	public List<String> getAvoidIngredients() {
		return avoidIngredients;
	}

	public void setAvoidIngredients(List<String> avoidIngredients) {
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
}