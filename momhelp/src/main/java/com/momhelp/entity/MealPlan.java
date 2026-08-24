package com.momhelp.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "meal_plans")
public class MealPlan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "meal_date")
	@Temporal(TemporalType.DATE)
	private Date mealDate;

	@Column(name = "meal_type", length = 20)
	private String mealType; // BREAKFAST, LUNCH, DINNER, SNACK

	@Column(name = "dish_name")
	private String dishName;

	@Column(name = "recipe_id")
	private Long recipeId;

	@Column(name = "ingredients", columnDefinition = "TEXT")
	private String ingredients;

	@Column(name = "preparation_time")
	private Integer preparationTime;

	@Column(name = "servings")
	private Integer servings;

	@Column(name = "notes", columnDefinition = "TEXT")
	private String notes;

	@Column(name = "is_prepared")
	private Boolean isPrepared;

	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	public MealPlan() {
		this.createdDate = new Date();
		this.isPrepared = false;
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

	public Date getMealDate() {
		return mealDate;
	}

	public void setMealDate(Date mealDate) {
		this.mealDate = mealDate;
	}

	public String getMealType() {
		return mealType;
	}

	public void setMealType(String mealType) {
		this.mealType = mealType;
	}

	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	public Long getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(Long recipeId) {
		this.recipeId = recipeId;
	}

	public String getIngredients() {
		return ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	public Integer getPreparationTime() {
		return preparationTime;
	}

	public void setPreparationTime(Integer preparationTime) {
		this.preparationTime = preparationTime;
	}

	public Integer getServings() {
		return servings;
	}

	public void setServings(Integer servings) {
		this.servings = servings;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Boolean getIsPrepared() {
		return isPrepared;
	}

	public void setIsPrepared(Boolean isPrepared) {
		this.isPrepared = isPrepared;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}