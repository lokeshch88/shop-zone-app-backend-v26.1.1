package com.shopzone.app.dto;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.shopzone.app.entity.ProductVariant;
import com.shopzone.app.entity.VariantImage;

public class VariantImageDTO {
	
	

    private Long id;


    private ProductVariant productVariantId;


    private String imageUrl;

    private String fileName;

    private Boolean primaryImage = false;

    private Integer displayOrder;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProductVariant getProductVariantId() {
		return productVariantId;
	}

	public void setProductVariantId(ProductVariant productVariantId) {
		this.productVariantId = productVariantId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Boolean getPrimaryImage() {
		return primaryImage;
	}

	public void setPrimaryImage(Boolean primaryImage) {
		this.primaryImage = primaryImage;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public VariantImageDTO(Long id, ProductVariant productVariantId, String imageUrl, String fileName, Boolean primaryImage,
			Integer displayOrder) {
		super();
		this.id = id;
		this.productVariantId = productVariantId;
		this.imageUrl = imageUrl;
		this.fileName = fileName;
		this.primaryImage = primaryImage;
		this.displayOrder = displayOrder;
	}

	public VariantImageDTO() {
		super();
	}
	
	
	 public VariantImageDTO(VariantImage variantImage) {
	        if (variantImage == null) return;
	        
	        this.id = variantImage.getId();
	        this.imageUrl = variantImage.getImageUrl();
	        this.fileName = variantImage.getFileName();
	        this.primaryImage = variantImage.getPrimaryImage();
	        this.displayOrder = variantImage.getDisplayOrder();
	    }

	@Override
	public String toString() {
		return "VariantImageDTO [id=" + id + ", productVariantId=" + productVariantId + ", imageUrl=" + imageUrl
				+ ", fileName=" + fileName + ", primaryImage=" + primaryImage + ", displayOrder=" + displayOrder + "]";
	}
    
    
    

}
