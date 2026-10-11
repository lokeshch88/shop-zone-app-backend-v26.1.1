package com.shopzone.app.dto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.shopzone.app.entity.ProductVariant;
import com.shopzone.app.entity.VariantImage;

public class VariantDTO {
	
	private Long id;
    private String size;
    private String color;
    private Double price;
    private Integer stock;
    private String imageUrl;
    private String sku;
    
    private Boolean isActive; // to mark if this variant is active or not
    private String material; // material of the product variant
    private String pattern; // pattern of the product variant
    private String style; // style of the product variant
    private String gender; // gender for which the variant is intended
    private String season; // season for which the variant is intended
    private String occasion; // occasion for which the variant is intended
    private String fit; // fit type of the product variant
    
    
    //variant img
    private List<VariantImageDTO> images = new ArrayList<>(); 
    
    public VariantDTO(ProductVariant variant) {

//        this.id = variant.getId();

        // map your actual Variant fields
    	this.id = variant.getId();
        this.size = variant.getSize();
        this.color = variant.getColor();
        this.price = variant.getPrice();
        this.stock = variant.getStock();
        this.sku = variant.getSku();
        this.isActive = variant.getIsActive();
        this.material = variant.getMaterial();
        this.pattern = variant.getPattern();
        this.style = variant.getStyle();
        this.gender = variant.getGender();
        this.season = variant.getSeason();
        this.occasion = variant.getOccasion();
        this.fit = variant.getFit();
        
        
        // Map variant images applying your exact sorting & ordering rules
        if (variant.getImages() != null) {
            this.setImages(variant.getImages()
                    .stream()
                    .sorted(
                        Comparator.comparing(
                            VariantImage::getDisplayOrder,
                            Comparator.nullsLast(Comparator.naturalOrder())
                        )
                    )
                    .map(VariantImageDTO::new) // calls VariantImageDTO constructor
                    .collect(Collectors.toList()));
        } else {
            this.setImages(new ArrayList<>());
        }
        
    }
    
    
    
	public VariantDTO() {
		super();
	}



	



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
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



	public List<VariantImageDTO> getImages() {
		return images;
	}



	public void setImages(List<VariantImageDTO> images) {
		this.images = images;
	}



	@Override
	public String toString() {
		return "VariantDTO [size=" + size + ", color=" + color + ", price=" + price + ", stock=" + stock + ", imageUrl="
				+ imageUrl + ", sku=" + sku + ", isActive=" + isActive + ", material=" + material + ", pattern="
				+ pattern + ", style=" + style + ", gender=" + gender + ", season=" + season + ", occasion=" + occasion
				+ ", fit=" + fit + ", images=" + images + "]";
	}
    
	
	
    
}
