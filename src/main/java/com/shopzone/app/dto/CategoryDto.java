package com.shopzone.app.dto;

import com.shopzone.app.entity.Category;

public class CategoryDto {
	private Long id;
	private String name;
	
	private String description;
	private String createdBy;
	private Long merchantId;
	
	private String createdAt;
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public CategoryDto(Category category) {
		super();
		this.id = category.getId();
		this.name = category.getName();
		this.description = category.getDescription();
		this.createdBy = category.getCreatedBy();
		this.merchantId = category.getMerchant() != null ? category.getMerchant().getId() : null;
		this.createdAt = category.getCreatedAt() != null ? category.getCreatedAt().toString() : null;
	}
	
	
	
	
	
	
}
