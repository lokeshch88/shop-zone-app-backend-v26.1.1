package com.shopzone.app.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "product_reviews", indexes = {
    @Index(name = "idx_product_reviews_product_id", columnList = "product_id"),
    @Index(name = "idx_product_reviews_user_id", columnList = "user_id"),
    @Index(name = "idx_product_reviews_rating", columnList = "rating"),
    @Index(name = "uk_product_reviews_user_product", columnList = "product_id,user_id", unique = true)
})
public class ProductReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must not exceed 5")
    @Column(name = "rating", nullable = false)
    private Integer rating;  // 1-5 stars (MANDATORY)

    @Size(min = 5, max = 500, message = "Title must be between 5 and 500 characters")
    @Column(name = "title")
    private String title;  // Optional title

    @Size(min = 10, max = 3000, message = "Review must be between 10 and 3000 characters")
    @Lob
    @Column(name = "content")
    private String content;  // Optional review text

    @Column(name = "is_verified_purchase", nullable = false)
    private Boolean isVerifiedPurchase = false;  // Purchased from us or not

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved = false;  // Admin approval

    @Column(name = "rejection_reason")
    private String rejectionReason;

    // One review can have max 5 photos
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("displayOrder ASC")
    private List<ReviewPhoto> photos = new ArrayList<>();

    // Admin/Merchant can reply to reviews
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createdAt DESC")
    private List<MerchantReply> adminReplies = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public List<ReviewPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<ReviewPhoto> photos) {
        this.photos = photos;
    }

    public List<MerchantReply> getAdminReplies() {
        return adminReplies;
    }

    public void setAdminReplies(List<MerchantReply> adminReplies) {
        this.adminReplies = adminReplies;
    }

    @Override
    public String toString() {
        return "ProductReview [id=" + id + ", rating=" + rating + ", title=" + title 
                + ", isVerifiedPurchase=" + isVerifiedPurchase + ", createdAt=" + createdAt 
                + ", isApproved=" + isApproved + ", photos=" + photos.size() + "]";
    }
}
