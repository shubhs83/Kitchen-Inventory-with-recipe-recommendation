package com.momhelp.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "usage_history")
public class UsageHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "vegetable_name", nullable = false)
	private String vegetableName;

	@Column(name = "weight_used", nullable = false)
	private Double weightUsed;

	@Column(name = "unit", nullable = false)
	private String unit;

	@Column(name = "used_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date usedDate;

	@Column(name = "dish_name")
	private String dishName;

	// Constructors
	public UsageHistory() {
	}

	public UsageHistory(String vegetableName, Double weightUsed, String unit, Date usedDate, String dishName) {
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