package com.momhelp.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "shopping_list")
public class ShoppingList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "item_name", nullable = false)
	private String itemName;

	@Column(name = "quantity")
	private Double quantity;

	@Column(name = "unit", length = 20)
	private String unit; // kg, grams, liters, pieces, etc.

	@Column(name = "category", length = 50)
	private String category; // Vegetables, Fruits, Dairy, Grains, Spices, etc.

	@Column(name = "priority", length = 20)
	private String priority; // HIGH, MEDIUM, LOW

	@Column(name = "is_purchased")
	private Boolean isPurchased;

	@Column(name = "estimated_price")
	private Double estimatedPrice;

	@Column(name = "notes", columnDefinition = "TEXT")
	private String notes;

	@Column(name = "added_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date addedDate;

	@Column(name = "purchased_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date purchasedDate;

	public ShoppingList() {
		this.addedDate = new Date();
		this.isPurchased = false;
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

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Boolean getIsPurchased() {
		return isPurchased;
	}

	public void setIsPurchased(Boolean isPurchased) {
		this.isPurchased = isPurchased;
	}

	public Double getEstimatedPrice() {
		return estimatedPrice;
	}

	public void setEstimatedPrice(Double estimatedPrice) {
		this.estimatedPrice = estimatedPrice;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	public Date getPurchasedDate() {
		return purchasedDate;
	}

	public void setPurchasedDate(Date purchasedDate) {
		this.purchasedDate = purchasedDate;
	}
}