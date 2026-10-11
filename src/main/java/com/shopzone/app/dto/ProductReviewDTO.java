package com.shopzone.app.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ProductReviewDTO {
    
    private Long id;
    private Long productId;
    private Long userId;
    private String username;
    private Integer rating;
    private String title;
    private String content;
    private Boolean isVerifiedPurchase;
    private Boolean isApproved;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ReviewPhotoDTO> photos;
    private List<MerchantReplyDTO> replies;

    // Constructors
    public ProductReviewDTO() {}

    public ProductReviewDTO(Long id, Integer rating, String title) {
        this.id = id;
        this.rating = rating;
        this.title = title;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsVerifiedPurchase() {
        return isVerifiedPurchase;
    }

    public void setIsVerifiedPurchase(Boolean isVerifiedPurchase) {
        this.isVerifiedPurchase = isVerifiedPurchase;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
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

    public List<ReviewPhotoDTO> getPhotos() {
        return photos;
    }

    public void setPhotos(List<ReviewPhotoDTO> photos) {
        this.photos = photos;
    }

    public List<MerchantReplyDTO> getReplies() {
        return replies;
    }

    public void setReplies(List<MerchantReplyDTO> replies) {
        this.replies = replies;
    }
}
