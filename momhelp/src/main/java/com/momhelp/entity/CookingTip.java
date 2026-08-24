package com.momhelp.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cooking_tips")
public class CookingTip {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "category", length = 50)
	private String category; // BASIC_TECHNIQUES, INGREDIENT_PREP, COOKING_METHODS, STORAGE, SAFETY,
								// TIME_SAVING, HEALTHY_COOKING

	@Column(name = "tip_content", columnDefinition = "TEXT")
	private String tipContent;

	@Column(name = "difficulty_level", length = 20)
	private String difficultyLevel; // BEGINNER, INTERMEDIATE, ADVANCED

	@Column(name = "tags", columnDefinition = "TEXT")
	private String tags; // Comma-separated tags

	@Column(name = "video_url")
	private String videoUrl;

	@Column(name = "image_url")
	private String imageUrl;

	@Column(name = "is_favorite")
	private Boolean isFavorite;

	@Column(name = "view_count")
	private Integer viewCount;

	@Column(name = "helpful_count")
	private Integer helpfulCount;

	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	public CookingTip() {
		this.createdDate = new Date();
		this.updatedDate = new Date();
		this.isFavorite = false;
		this.viewCount = 0;
		this.helpfulCount = 0;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTipContent() {
		return tipContent;
	}

	public void setTipContent(String tipContent) {
		this.tipContent = tipContent;
	}

	public String getDifficultyLevel() {
		return difficultyLevel;
	}

	public void setDifficultyLevel(String difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Boolean getIsFavorite() {
		return isFavorite;
	}

	public void setIsFavorite(Boolean isFavorite) {
		this.isFavorite = isFavorite;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public Integer getHelpfulCount() {
		return helpfulCount;
	}

	public void setHelpfulCount(Integer helpfulCount) {
		this.helpfulCount = helpfulCount;
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