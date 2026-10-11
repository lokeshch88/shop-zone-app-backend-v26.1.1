package com.shopzone.app.dto;

import java.time.LocalDateTime;

public class MerchantReplyDTO {
    
    private Long id;
    private Long reviewId;
    private Long merchantId;
    private String merchantName;
    private String replyText;
    private String replyType;  // RESOLUTION, THANK_YOU, APOLOGY, etc.
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public MerchantReplyDTO() {}

    public MerchantReplyDTO(Long id, String replyText, String replyType) {
        this.id = id;
        this.replyText = replyText;
        this.replyType = replyType;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getReplyText() {
        return replyText;
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }

    public String getReplyType() {
        return replyType;
    }

    public void setReplyType(String replyType) {
        this.replyType = replyType;
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
}
