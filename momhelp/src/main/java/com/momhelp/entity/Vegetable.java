package com.momhelp.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "vegetables")
public class Vegetable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "vegetable_name", nullable = false, length = 100)
	private String name;

	@Column(name = "weight", nullable = false)
	private Double weight;

	@Column(name = "unit", nullable = false, length = 10)
	private String unit;

	@Column(name = "added_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date addedDate;

	@Column(name = "use_before_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date useBeforeDate;

	@Column(name = "is_spoiled", columnDefinition = "boolean default false")
	private boolean isSpoiled = false;

	// 🔴 USER OWNERSHIP
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Vegetable() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public boolean isSpoiled() {
		return isSpoiled;
	}

	public void setSpoiled(boolean spoiled) {
		this.isSpoiled = spoiled;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
