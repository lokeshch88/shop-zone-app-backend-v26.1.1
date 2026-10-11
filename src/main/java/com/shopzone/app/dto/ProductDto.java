package com.shopzone.app.dto;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.shopzone.app.entity.Product;
import com.shopzone.app.entity.ProductImage;
import java.util.ArrayList;

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

	private Date createdAt;

	private Date updatedAt;

	private String isActive;

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

	private BigDecimal comparePrice;

	private List<String> variantColors;
	private List<String> variantSizes;

	private String imageBase64;
	
	private List<ProductImageDto> images;
	
	// ===== REVIEW FIELDS =====
	private BigDecimal averageRating = BigDecimal.ZERO;
	private Integer totalReviews = 0;
	private List<ProductReviewDTO> reviews = new ArrayList<>();

	public ProductDto(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.price = product.getPrice();
		this.description = product.getDescription(); 
		this.categoryId = product.getCategoryId() != null ? product.getCategoryId().getId() : null;
		this.isActive = product.getIsActive();
		this.brand = product.getBrand();
		this.comparePrice = product.getComparePrice();
		this.mrp = product.getMrp();
		this.quantityInStock = product.getQuantityInStock();
		this.sku=product.getSku();
		
		// Add review fields
		this.averageRating = product.getAverageRating() != null ? product.getAverageRating() : BigDecimal.ZERO;
		this.totalReviews = product.getTotalReviews() != null ? product.getTotalReviews() : 0;
		
		if (product.getImageData() != null) {
			this.imageBase64 = Base64.getEncoder().encodeToString(product.getImageData());
		}
		
		if (product.getImages() != null) {

		    this.images = product.getImages()
		            .stream()
		            .sorted(
		                Comparator.comparing(
		                    ProductImage::getDisplayOrder,
		                    Comparator.nullsLast(
		                        Comparator.naturalOrder()
		                    )
		                )
		            )
		            .map(ProductImageDto::new)
		            .collect(Collectors.toList());
		}
		

	    // Map variants
	    if (product.getVariants() != null) {
	        this.variants = product.getVariants()
	                .stream()
	                .map(VariantDTO::new)  ///calls variant dto constructor
	                .collect(Collectors.toList());
	    } else {
	        this.variants = new ArrayList<>();
	    }
	}

	public BigDecimal getDiscountPercentage() {
		if (mrp != null && discountPrice != null && mrp.compareTo(BigDecimal.ZERO) > 0) {
			return (mrp.subtract(discountPrice)).divide(mrp, 2, BigDecimal.ROUND_HALF_UP)
					.multiply(BigDecimal.valueOf(100));
		}
		return BigDecimal.ZERO;
	}
	
	

	public List<String> getVariantColors() {
		return variantColors;
	}

	public void setVariantColors(List<String> variantColors) {
		this.variantColors = variantColors;
	}

	public List<String> getVariantSizes() {
		return variantSizes;
	}

	public void setVariantSizes(List<String> variantSizes) {
		this.variantSizes = variantSizes;
	}

	public List<ProductImageDto> getImages() {
		return images;
	}

	public void setImages(List<ProductImageDto> images) {
		this.images = images;
	}

	public ProductDto() {
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
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

	public BigDecimal getComparePrice() {
		return comparePrice;
	}

	public void setComparePrice(BigDecimal comparePrice) {
		this.comparePrice = comparePrice;
	}

	public String getImageBase64() {
		return imageBase64;
	}

	public void setImageBase64(String imageBase64) {
		this.imageBase64 = imageBase64;
	}

	// ===== REVIEW GETTERS & SETTERS =====
	public BigDecimal getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(BigDecimal averageRating) {
		this.averageRating = averageRating;
	}

	public Integer getTotalReviews() {
		return totalReviews;
	}

	public void setTotalReviews(Integer totalReviews) {
		this.totalReviews = totalReviews;
	}

	public List<ProductReviewDTO> getReviews() {
		return reviews;
	}

	public void setReviews(List<ProductReviewDTO> reviews) {
		this.reviews = reviews;
	}

	@Override
	public String toString() {
		return "ProductDto [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", quantityInStock=" + quantityInStock + ", brand=" + brand + ", sku=" + sku + ", weight=" + weight
				+ ", dimensions=" + dimensions + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", isActive="
				+ isActive + ", imageUrl=" + imageUrl + ", manufacturer=" + manufacturer + ", rating=" + rating
				+ ", mrp=" + mrp + ", discountPrice=" + discountPrice + ", discountPercentage=" + discountPercentage
				+ ", categoryId=" + categoryId + ", variants=" + variants + ", comparePrice=" + comparePrice
				+ ", variantColors=" + variantColors + ", variantSizes=" + variantSizes + ", imageBase64=" + imageBase64
				+ ", images=" + images + ", averageRating=" + averageRating + ", totalReviews=" + totalReviews
				+ ", reviews=" + reviews + "]";
	}

//	@Override
//	public String toString() {
//		return "ProductDto [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
//				+ ", quantityInStock=" + quantityInStock + ", brand=" + brand + ", sku=" + sku + ", weight=" + weight
//				+ ", dimensions=" + dimensions + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", isActive="
//				+ isActive + ", imageUrl=" + imageUrl + ", manufacturer=" + manufacturer + ", rating=" + rating
//				+ ", mrp=" + mrp + ", discountPrice=" + discountPrice + ", discountPercentage=" + discountPercentage
//				+ ", categoryId=" + categoryId + ", variants=" + variants + ", comparePrice=" + comparePrice
//				+ ", variantColors=" + variantColors + ", variantSizes=" + variantSizes + ", imageBase64=" + imageBase64
//				+ ", images=" + images + ", averageRating=" + averageRating + ", totalReviews=" + totalReviews
//				+ ", reviews=" + reviews + "]";
//	}
	
	
	
	

}