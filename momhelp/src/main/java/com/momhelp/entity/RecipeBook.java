package com.momhelp.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "recipe_book")
public class RecipeBook {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "recipe_name", nullable = false)
	private String recipeName;

	@Column(name = "category", length = 50)
	private String category; // BREAKFAST, LUNCH, DINNER, SNACK, DESSERT

	@Column(name = "cuisine_type", length = 50)
	private String cuisineType; // Indian, Chinese, Italian, etc.

	@Column(name = "ingredients", columnDefinition = "TEXT")
	private String ingredients;

	@Column(name = "instructions", columnDefinition = "TEXT")
	private String instructions;

	@Column(name = "preparation_time")
	private Integer preparationTime;

	@Column(name = "cooking_time")
	private Integer cookingTime;

	@Column(name = "servings")
	private Integer servings;

	@Column(name = "difficulty_level", length = 20)
	private String difficultyLevel; // EASY, MEDIUM, HARD

	@Column(name = "is_favorite")
	private Boolean isFavorite;

	@Column(name = "rating")
	private Integer rating; // 1-5 stars

	@Column(name = "notes", columnDefinition = "TEXT")
	private String notes;

	@Column(name = "image_url")
	private String imageUrl;

	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	public RecipeBook() {
		this.createdDate = new Date();
		this.updatedDate = new Date();
		this.isFavorite = false;
		this.rating = 0;
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

	public String getRecipeName() {
		return recipeName;
	}

	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
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

	public Integer getPreparationTime() {
		return preparationTime;
	}

	public void setPreparationTime(Integer preparationTime) {
		this.preparationTime = preparationTime;
	}

	public Integer getCookingTime() {
		return cookingTime;
	}

	public void setCookingTime(Integer cookingTime) {
		this.cookingTime = cookingTime;
	}

	public Integer getServings() {
		return servings;
	}

	public void setServings(Integer servings) {
		this.servings = servings;
	}

	public String getDifficultyLevel() {
		return difficultyLevel;
	}

	public void setDifficultyLevel(String difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}

	public Boolean getIsFavorite() {
		return isFavorite;
	}

	public void setIsFavorite(Boolean isFavorite) {
		this.isFavorite = isFavorite;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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