package com.shopzone.app.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "product_variant")
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
//    @JoinColumn(name = "product_id")
    private Product product;

    private String size;     // S, M, L, XL, etc.
    private String color;    // Red, Blue, etc.
    private Double price;
    private Integer stock;

    private Boolean isActive; // to mark if this variant is active or not
    private String material; // material of the product variant
    private String pattern; // pattern of the product variant
    private String style; // style of the product variant
    private String gender; // gender for which the variant is intended
    private String season; // season for which the variant is intended
    private String occasion; // occasion for which the variant is intended
    private String fit; // fit type of the product variant
    
    private String imageUrl; // image specific to variant

    // Unique combination
//    @Column(unique = true)
    private String sku; // Stock Keeping Unit like "TSHIRT-M-RED"
    
    
    @OneToMany(
            mappedBy = "productVariant", 
            cascade = CascadeType.ALL, 
            orphanRemoval = true,
            fetch = FetchType.LAZY
        )
        private List<VariantImage> images = new ArrayList<>(); 

    
    
    //geteter setter
    
    
    
    
	public Long getId() {
		return id;
	}

	public List<VariantImage> getImages() {
		return images;
	}

	public void setImages(List<VariantImage> images) {
		this.images = images;
	}

	public ProductVariant() {
		super();
	}

	public ProductVariant(Long id, Product product, String size, String color, Double price, Integer stock,
			Boolean isActive, String material, String pattern, String style, String gender, String season,
			String occasion, String fit, String imageUrl, String sku) {
		super();
		this.id = id;
		this.product = product;
		this.size = size;
		this.color = color;
		this.price = price;
		this.stock = stock;
		this.isActive = isActive;
		this.material = material;
		this.pattern = pattern;
		this.style = style;
		this.gender = gender;
		this.season = season;
		this.occasion = occasion;
		this.fit = fit;
		this.imageUrl = imageUrl;
		this.sku = sku;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getOccasion() {
		return occasion;
	}

	public void setOccasion(String occasion) {
		this.occasion = occasion;
	}

	public String getFit() {
		return fit;
	}

	public void setFit(String fit) {
		this.fit = fit;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

    
    
}
