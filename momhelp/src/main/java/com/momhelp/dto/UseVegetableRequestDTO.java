package com.momhelp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class UseVegetableRequestDTO {

	@NotNull(message = "Vegetable ID is required")
	private Long vegetableId;

	@NotNull(message = "Weight used is required")
	@Positive(message = "Weight must be positive")
	private Double weightUsed;
	
	private String unitUsed;   

	public String getUnitUsed() {
		return unitUsed;
	}

	public void setUnitUsed(String unitUsed) {
		this.unitUsed = unitUsed;
	}

	@Override
	public String toString() {
		return "UseVegetableRequestDTO [vegetableId=" + vegetableId + ", weightUsed=" + weightUsed + ", unitUsed="
				+ unitUsed + "]";
	}

	// Constructors
	public UseVegetableRequestDTO() {
	}

	public UseVegetableRequestDTO(Long vegetableId, Double weightUsed) {
		this.vegetableId = vegetableId;
		this.weightUsed = weightUsed;
	}

	// Getters and Setters
	public Long getVegetableId() {
		return vegetableId;
	}

	public void setVegetableId(Long vegetableId) {
		this.vegetableId = vegetableId;
	}

	public Double getWeightUsed() {
		return weightUsed;
	}

	public void setWeightUsed(Double weightUsed) {
		this.weightUsed = weightUsed;
	}
}