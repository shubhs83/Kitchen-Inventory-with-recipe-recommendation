package com.momhelp.dto;

import java.util.Date;

public class UsageHistoryDTO {
	private Long id;
	private String vegetableName;
	private Double weightUsed;
	private String unit;
	private Date usedDate;
	private String dishName;

	// Constructors
	public UsageHistoryDTO() {
	}

	public UsageHistoryDTO(Long id, String vegetableName, Double weightUsed, String unit, Date usedDate,
			String dishName) {
		this.id = id;
		this.vegetableName = vegetableName;
		this.weightUsed = weightUsed;
		this.unit = unit;
		this.usedDate = usedDate;
		this.dishName = dishName;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVegetableName() {
		return vegetableName;
	}

	public void setVegetableName(String vegetableName) {
		this.vegetableName = vegetableName;
	}

	public Double getWeightUsed() {
		return weightUsed;
	}

	public void setWeightUsed(Double weightUsed) {
		this.weightUsed = weightUsed;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Date getUsedDate() {
		return usedDate;
	}

	public void setUsedDate(Date usedDate) {
		this.usedDate = usedDate;
	}

	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}
}