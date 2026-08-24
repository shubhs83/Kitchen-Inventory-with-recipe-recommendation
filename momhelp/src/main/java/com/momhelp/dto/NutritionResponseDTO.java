package com.momhelp.dto;

public class NutritionResponseDTO {

	private String foodName;
	private Double servingSize;
	private String servingUnit;
	private Double calories;
	private Double protein;
	private Double carbohydrates;
	private Double fat;
	private Double fiber;
	private Double sugar;
	private Double sodium;
	private Double vitaminC;
	private Double calcium;
	private Double iron;

	public NutritionResponseDTO() {
	}

	public NutritionResponseDTO(String foodName, Double servingSize, String servingUnit, Double calories,
			Double protein, Double carbohydrates, Double fat, Double fiber, Double sugar, Double sodium,
			Double vitaminC, Double calcium, Double iron) {
		this.foodName = foodName;
		this.servingSize = servingSize;
		this.servingUnit = servingUnit;
		this.calories = calories;
		this.protein = protein;
		this.carbohydrates = carbohydrates;
		this.fat = fat;
		this.fiber = fiber;
		this.sugar = sugar;
		this.sodium = sodium;
		this.vitaminC = vitaminC;
		this.calcium = calcium;
		this.iron = iron;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public Double getServingSize() {
		return servingSize;
	}

	public void setServingSize(Double servingSize) {
		this.servingSize = servingSize;
	}

	public String getServingUnit() {
		return servingUnit;
	}

	public void setServingUnit(String servingUnit) {
		this.servingUnit = servingUnit;
	}

	public Double getCalories() {
		return calories;
	}

	public void setCalories(Double calories) {
		this.calories = calories;
	}

	public Double getProtein() {
		return protein;
	}

	public void setProtein(Double protein) {
		this.protein = protein;
	}

	public Double getCarbohydrates() {
		return carbohydrates;
	}

	public void setCarbohydrates(Double carbohydrates) {
		this.carbohydrates = carbohydrates;
	}

	public Double getFat() {
		return fat;
	}

	public void setFat(Double fat) {
		this.fat = fat;
	}

	public Double getFiber() {
		return fiber;
	}

	public void setFiber(Double fiber) {
		this.fiber = fiber;
	}

	public Double getSugar() {
		return sugar;
	}

	public void setSugar(Double sugar) {
		this.sugar = sugar;
	}

	public Double getSodium() {
		return sodium;
	}

	public void setSodium(Double sodium) {
		this.sodium = sodium;
	}

	public Double getVitaminC() {
		return vitaminC;
	}

	public void setVitaminC(Double vitaminC) {
		this.vitaminC = vitaminC;
	}

	public Double getCalcium() {
		return calcium;
	}

	public void setCalcium(Double calcium) {
		this.calcium = calcium;
	}

	public Double getIron() {
		return iron;
	}

	public void setIron(Double iron) {
		this.iron = iron;
	}
}