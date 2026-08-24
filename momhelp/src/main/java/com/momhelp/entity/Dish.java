package com.momhelp.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "dishes")
public class Dish {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "dish_name", nullable = false, length = 200)
	private String dishName;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@ManyToOne
	@JoinColumn(name = "vegetable_id", nullable = false)
	private Vegetable vegetable;

	@Column(name = "vegetable_name", nullable = false, length = 100)
	private String vegetableName;

	@Column(name = "difficulty_level", length = 20)
	private String difficultyLevel;

	@Column(name = "prep_time")
	private Integer prepTime;

	@Column(name = "cook_time")
	private Integer cookTime;

	@Column(name = "servings")
	private Integer servings;

	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	// Constructors
	public Dish() {
		this.createdDate = new Date();
	}

	public Dish(String dishName, String description, Vegetable vegetable, String vegetableName, String difficultyLevel,
			Integer prepTime, Integer cookTime, Integer servings) {
		this.dishName = dishName;
		this.description = description;
		this.vegetable = vegetable;
		this.vegetableName = vegetableName;
		this.difficultyLevel = difficultyLevel;
		this.prepTime = prepTime;
		this.cookTime = cookTime;
		this.servings = servings;
		this.createdDate = new Date();
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

	public Vegetable getVegetable() {
		return vegetable;
	}

	public void setVegetable(Vegetable vegetable) {
		this.vegetable = vegetable;
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