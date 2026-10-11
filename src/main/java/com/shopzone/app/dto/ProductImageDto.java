package com.shopzone.app.dto;

import com.shopzone.app.entity.ProductImage;

public class ProductImageDto {

    private Long id;
    private String imageUrl;
    private String fileName;
    private Boolean primaryImage;
    private Integer displayOrder;

    public ProductImageDto() {
    }

    public ProductImageDto(ProductImage image) {
        this.id = image.getId();
        this.imageUrl = image.getImageUrl();
        this.fileName = image.getFileName();
        this.primaryImage = image.getPrimaryImage();
        this.displayOrder = image.getDisplayOrder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}