package com.shopzone.app.dto;


import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class ProductDto {

    private Long id;

    @NotNull
    @Size(min = 3, max = 255)
    private String name;

    @NotNull
    private String description;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal price;

    @Min(0)
    private Integer quantityInStock;

    @NotNull
    @Size(min = 2, max = 100)
    private String brand;

//    @NotNull
//    @Size(min = 2, max = 100)
//    private String category;

    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9_-]+$")
    private String sku;

    private Double weight;

    private String dimensions;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean isActive;

    private String imageUrl;

    private String manufacturer;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "5.0")
    private Double rating;

    @DecimalMin(value = "0.01")
    private BigDecimal mrp;

    @DecimalMin(value = "0.01")
    private BigDecimal discountPrice;

    // Optional calculated field (transient in response)
    private BigDecimal discountPercentage;
  
  @NotNull
    private Long categoryId;

  private List<VariantDTO> variants;
  
  private List<String> variantColors;
  private List<String> variantSizes;
  

    public BigDecimal getDiscountPercentage() {
        if (mrp != null && discountPrice != null && mrp.compareTo(BigDecimal.ZERO) > 0) {
            return (mrp.subtract(discountPrice))
                    .divide(mrp, 2, BigDecimal.ROUND_HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }
        return BigDecimal.ZERO;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getQuantityInStock() {
		return quantityInStock;
	}

	public void setQuantityInStock(Integer quantityInStock) {
		this.quantityInStock = quantityInStock;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}


	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getDimensions() {
		return dimensions;
	}

	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public BigDecimal getMrp() {
		return mrp;
	}

	public void setMrp(BigDecimal mrp) {
		this.mrp = mrp;
	}

	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}

	public void setDiscountPercentage(BigDecimal discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public List<VariantDTO> getVariants() {
		return variants;
	}

	public void setVariants(List<VariantDTO> variants) {
		this.variants = variants;
	}

   
}

