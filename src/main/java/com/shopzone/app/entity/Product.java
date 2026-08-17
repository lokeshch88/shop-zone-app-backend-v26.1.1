package com.shopzone.app.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;


    @Size(min = 3, max = 255, message = "Product name must be between 3 and 255 characters")
    @Column(name = "name", nullable = false, unique = true)
    private String name;


    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @DecimalMin(value = "0.01", message = "Price must be greater than zero")
    @Column(name = "price", nullable = false)
    private BigDecimal price;  // This could represent the actual selling price

    @Min(0)
    @Column(name = "quantity_in_stock", nullable = false)
    private Integer quantityInStock;

    @NotNull
    @Size(min = 2, max = 100, message = "Brand name must be between 2 and 100 characters")
    @Column(name = "brand")
    private String brand;

//    @NotNull
//    @Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters")
//    @Column(name = "category")
//    private String category;

    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "SKU must contain only alphanumeric characters, dashes, and underscores")
    @Column(name = "sku", nullable = false, unique = true)
    private String sku;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "dimensions")
    private String dimensions;

    @Column(name = "created_at", nullable = false)
   // @Temporal(TemporalType.TIMESTAMP) //not reqd if localdatetime used
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    @Column(name = "is_active", nullable = false)
    private String isActive;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "rating")
    @DecimalMin(value = "0.0", message = "Rating must be between 0 and 5")
    @DecimalMax(value = "5.0", message = "Rating must be between 0 and 5")
    private Double rating;

    @DecimalMin(value = "0.01", message = "MRP must be greater than zero")
    @Column(name = "mrp", nullable = false)
    private BigDecimal mrp;  // (MRP)

    @DecimalMin(value = "0.01", message = "Discount price must be greater than zero")
    @Column(name = "discount_price")
    private BigDecimal discountPrice;  // discounted price after discount

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id" )
    @JsonIgnore
    private Category categoryId;
    
//    @ElementCollection
//    @CollectionTable(name = "product_variant", joinColumns = @JoinColumn(name = "user_id"))
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariant> variants;

	@Column(name="compare_price")
	private BigDecimal comparePrice;
	
	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] imageData;
    
	
    public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	public BigDecimal getComparePrice() {
		return comparePrice;
	}

	public void setComparePrice(BigDecimal comparePrice) {
		this.comparePrice = comparePrice;
	}

	public List<ProductVariant> getVariants() {
		return variants;
	}

	public void setVariants(List<ProductVariant> variants) {
		this.variants = variants;
	}

	public Category getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Category categoryId) {
		this.categoryId = categoryId;
	}

	@Transient  // for not be persisted to the database
    public BigDecimal getDiscountPercentage() {
        if (mrp != null && discountPrice != null && mrp.compareTo(BigDecimal.ZERO) > 0) {
            return (mrp.subtract(discountPrice)).divide(mrp, 2, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));
        }
        return BigDecimal.ZERO;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

//    public void setId(Long id) {
//        this.id = id;
//    }

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

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", quantityInStock=" + quantityInStock + ", brand=" + brand + ", sku=" + sku + ", weight=" + weight
				+ ", dimensions=" + dimensions + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", isActive="
				+ isActive + ", imageUrl=" + imageUrl + ", manufacturer=" + manufacturer + ", rating=" + rating
				+ ", mrp=" + mrp + ", discountPrice=" + discountPrice + ", categoryId=" + categoryId + ", variants="
				+ variants + ", comparePrice=" + comparePrice + "]";
	}
    
    
}
