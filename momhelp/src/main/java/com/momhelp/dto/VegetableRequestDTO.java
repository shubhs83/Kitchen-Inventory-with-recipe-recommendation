package com.momhelp.dto;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Date;

public class VegetableRequestDTO {

	@NotBlank(message = "Vegetable name is required")
	private String name;

	@NotNull(message = "Weight is required")
	@Positive(message = "Weight must be positive")
	private Double weight;

	@NotBlank(message = "Unit is required")
	private String unit;

	@NotNull(message = "Added date is required")
	private Date addedDate;

	@NotNull(message = "Use before date is required")
	private Date useBeforeDate;

	// OPTIONAL: Image file (can be null)
	private MultipartFile image;

	// Getters and Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	public Date getUseBeforeDate() {
		return useBeforeDate;
	}

	public void setUseBeforeDate(Date useBeforeDate) {
		this.useBeforeDate = useBeforeDate;
	}

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}
}