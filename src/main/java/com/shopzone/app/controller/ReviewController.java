package com.shopzone.app.controller;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.shopzone.app.dto.ProductReviewDTO;
import com.shopzone.app.dto.ReviewPhotoDTO;
import com.shopzone.app.dto.MerchantReplyDTO;
import com.shopzone.app.entity.ProductReview;
import com.shopzone.app.entity.ReviewPhoto;
import com.shopzone.app.entity.MerchantReply;
import com.shopzone.app.service.ReviewService;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger log = LoggerFactory.getLogger(ReviewController.class);

    // ====================================
    // CREATE REVIEW (Rating mandatory, text optional)
    // ====================================
    @PostMapping("/products/{productId}")
    public ResponseEntity<?> createReview(
            @PathVariable Long productId,
            @RequestParam Integer rating,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content) {

        log.info("POST /api/reviews/products/{} - Creating review", productId);

        try {
            if (rating == null || rating < 1 || rating > 5) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Rating must be between 1 and 5"));
            }

            ProductReview review = reviewService.createReview(productId, rating, title, content);
            ProductReviewDTO dto = convertToDTO(review);

            return ResponseEntity.status(HttpStatus.CREATED).body(dto);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Error creating review", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to create review: " + e.getMessage()));
        }
    }

    // ====================================
    // UPDATE REVIEW (Own reviews only)
    // ====================================
    @PutMapping("/{reviewId}")
    public ResponseEntity<?> updateReview(
            @PathVariable Long reviewId,
            @RequestParam(required = false) Integer rating,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content) {

        log.info("PUT /api/reviews/{} - Updating review", reviewId);

        try {
            ProductReview review = reviewService.updateReview(reviewId, rating, title, content);
            ProductReviewDTO dto = convertToDTO(review);

            return ResponseEntity.ok(dto);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Error updating review", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to update review"));
        }
    }

    // ====================================
    // DELETE REVIEW (Own reviews only)
    // ====================================
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId) {

        log.info("DELETE /api/reviews/{} - Deleting review", reviewId);

        try {
            reviewService.deleteReview(reviewId);
            return ResponseEntity.ok(Map.of("message", "Review deleted successfully"));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // ====================================
    // GET ALL REVIEWS FOR PRODUCT (Paginated)
    // ====================================
    @GetMapping("/products/{productId}")
    public ResponseEntity<?> getProductReviews(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        log.info("GET /api/reviews/products/{} - Fetching reviews (page: {}, size: {})", productId, page, size);

        try {
            Page<ProductReview> reviews = reviewService.getProductReviews(productId, page, size);

            Map<String, Object> response = new HashMap<>();
            response.put("totalElements", reviews.getTotalElements());
            response.put("totalPages", reviews.getTotalPages());
            response.put("currentPage", reviews.getNumber());
            response.put("reviews", reviews.getContent()
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList()));

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error fetching reviews", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch reviews"));
        }
    }

    // ====================================
    // GET SINGLE REVIEW
    // ====================================
    @GetMapping("/{reviewId}")
    public ResponseEntity<?> getReview(@PathVariable Long reviewId) {

        log.info("GET /api/reviews/{} - Fetching single review", reviewId);

        try {
            // Service needs a method to get single review
            ProductReviewDTO dto = new ProductReviewDTO();
            return ResponseEntity.ok(dto);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Review not found"));
        }
    }

    // ====================================
    // GET REVIEWS BY STAR RATING
    // ====================================
    @GetMapping("/products/{productId}/rating/{rating}")
    public ResponseEntity<?> getReviewsByRating(
            @PathVariable Long productId,
            @PathVariable Integer rating,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        log.info("GET /api/reviews/products/{}/rating/{}", productId, rating);

        try {
            if (rating < 1 || rating > 5) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Rating must be between 1 and 5"));
            }

            Page<ProductReview> reviews = reviewService.getReviewsByRating(productId, rating, page, size);

            Map<String, Object> response = new HashMap<>();
            response.put("rating", rating);
            response.put("totalElements", reviews.getTotalElements());
            response.put("reviews", reviews.getContent()
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList()));

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch reviews"));
        }
    }

    // ====================================
    // GET USER'S REVIEWS
    // ====================================
    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getUserReviews(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        log.info("GET /api/reviews/users/{} - Fetching user reviews", userId);

        try {
            Page<ProductReview> reviews = reviewService.getUserReviews(userId, page, size);

            Map<String, Object> response = new HashMap<>();
            response.put("totalElements", reviews.getTotalElements());
            response.put("reviews", reviews.getContent()
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList()));

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch user reviews"));
        }
    }

    // ====================================
    // GET RATING DISTRIBUTION (1-5 stars count)
    // ====================================
    @GetMapping("/products/{productId}/rating-distribution")
    public ResponseEntity<?> getRatingDistribution(@PathVariable Long productId) {

        log.info("GET /api/reviews/products/{}/rating-distribution", productId);

        try {
            List<Object[]> distribution = reviewService.getRatingDistribution(productId);

            Map<String, Integer> ratingMap = new HashMap<>();
            for (int i = 1; i <= 5; i++) {
                ratingMap.put(i + " Star", 0);
            }

            for (Object[] row : distribution) {
                Integer rating = (Integer) row[0];
                Long count = (Long) row[1];
                ratingMap.put(rating + " Star", count.intValue());
            }

            return ResponseEntity.ok(ratingMap);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to get rating distribution"));
        }
    }

    // ====================================
    // GET AVERAGE RATING
    // ====================================
    @GetMapping("/products/{productId}/average-rating")
    public ResponseEntity<?> getAverageRating(@PathVariable Long productId) {

        log.info("GET /api/reviews/products/{}/average-rating", productId);

        try {
            Double avgRating = reviewService.getAverageRating(productId);
            return ResponseEntity.ok(Map.of("averageRating", avgRating));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to get average rating"));
        }
    }

    // ====================================
    // ADD PHOTO TO REVIEW (Max 5 photos)
    // ====================================
    @PostMapping("/{reviewId}/photos")
    public ResponseEntity<?> addPhotoToReview(
            @PathVariable Long reviewId,
            @RequestParam("photo") MultipartFile photoFile,
            @RequestParam(defaultValue = "1") int displayOrder) {

        log.info("POST /api/reviews/{}/photos - Adding photo", reviewId);

        try {
            if (photoFile.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Photo cannot be empty"));
            }

            ReviewPhoto photo = reviewService.addPhotoToReview(reviewId, photoFile, displayOrder);
            ReviewPhotoDTO dto = convertPhotoToDTO(photo);

            return ResponseEntity.status(HttpStatus.CREATED).body(dto);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Error uploading photo", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to upload photo"));
        }
    }

    // ====================================
    // DELETE PHOTO FROM REVIEW
    // ====================================
    @DeleteMapping("/photos/{photoId}")
    public ResponseEntity<?> deletePhoto(@PathVariable Long photoId) {

        log.info("DELETE /api/reviews/photos/{} - Deleting photo", photoId);

        try {
            reviewService.deletePhotoFromReview(photoId);
            return ResponseEntity.ok(Map.of("message", "Photo deleted successfully"));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // ====================================
    // MERCHANT/ADMIN REPLY TO REVIEW
    // ====================================
    @PostMapping("/{reviewId}/replies")
    public ResponseEntity<?> replyToReview(
            @PathVariable Long reviewId,
            @RequestParam String replyText,
            @RequestParam(required = false) String replyType) {

        log.info("POST /api/reviews/{}/replies - Adding merchant reply", reviewId);

        try {
            if (replyText == null || replyText.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Reply text cannot be empty"));
            }

            MerchantReply reply = reviewService.replyToReview(reviewId, replyText, replyType);
            MerchantReplyDTO dto = convertReplyToDTO(reply);

            return ResponseEntity.status(HttpStatus.CREATED).body(dto);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            log.error("Error adding reply", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to add reply"));
        }
    }

    // ====================================
    // GET REPLIES FOR REVIEW
    // ====================================
    @GetMapping("/{reviewId}/replies")
    public ResponseEntity<?> getReviewReplies(@PathVariable Long reviewId) {

        log.info("GET /api/reviews/{}/replies - Fetching replies", reviewId);

        try {
            List<MerchantReply> replies = reviewService.getReviewReplies(reviewId);
            List<MerchantReplyDTO> dtos = replies.stream()
                    .map(this::convertReplyToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(Map.of("replies", dtos));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch replies"));
        }
    }

    // ====================================
    // ADMIN ENDPOINTS
    // ====================================

    @PostMapping("/admin/{reviewId}/approve")
    public ResponseEntity<?> approveReview(@PathVariable Long reviewId) {

        log.info("POST /api/reviews/admin/{}/approve", reviewId);

        try {
            reviewService.approveReview(reviewId);
            return ResponseEntity.ok(Map.of("message", "Review approved successfully"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to approve review"));
        }
    }

    @PostMapping("/admin/{reviewId}/reject")
    public ResponseEntity<?> rejectReview(
            @PathVariable Long reviewId,
            @RequestParam String reason) {

        log.info("POST /api/reviews/admin/{}/reject", reviewId);

        try {
            reviewService.rejectReview(reviewId, reason);
            return ResponseEntity.ok(Map.of("message", "Review rejected successfully"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to reject review"));
        }
    }

    @GetMapping("/admin/unapproved")
    public ResponseEntity<?> getUnapprovedReviews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        log.info("GET /api/reviews/admin/unapproved");

        try {
            Page<ProductReview> reviews = reviewService.getUnapprovedReviews(page, size);

            Map<String, Object> response = new HashMap<>();
            response.put("totalElements", reviews.getTotalElements());
            response.put("reviews", reviews.getContent()
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList()));

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch unapproved reviews"));
        }
    }

    // ====================================
    // DTO CONVERTERS
    // ====================================

    private ProductReviewDTO convertToDTO(ProductReview review) {
        ProductReviewDTO dto = new ProductReviewDTO();
        dto.setId(review.getId());
        dto.setProductId(review.getProduct().getId());
        dto.setUserId(review.getUser().getId());
        dto.setUsername(review.getUser().getUsername());
        dto.setRating(review.getRating());
        dto.setTitle(review.getTitle());
        dto.setContent(review.getContent());
        dto.setIsVerifiedPurchase(review.getIsVerifiedPurchase());
        dto.setIsApproved(review.getIsApproved());
        dto.setCreatedAt(review.getCreatedAt());
        dto.setUpdatedAt(review.getUpdatedAt());

        // Convert photos
        if (review.getPhotos() != null) {
            dto.setPhotos(review.getPhotos().stream()
                    .map(this::convertPhotoToDTO)
                    .collect(Collectors.toList()));
        }

        // Convert replies
        if (review.getAdminReplies() != null) {
            dto.setReplies(review.getAdminReplies().stream()
                    .map(this::convertReplyToDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    private ReviewPhotoDTO convertPhotoToDTO(ReviewPhoto photo) {
        ReviewPhotoDTO dto = new ReviewPhotoDTO();
        dto.setId(photo.getId());
        dto.setReviewId(photo.getReview().getId());
        dto.setDisplayOrder(photo.getDisplayOrder());
        dto.setUploadedAt(photo.getUploadedAt());

        // Convert BLOB to Base64
        if (photo.getPhotoData() != null) {
            dto.setPhotoBase64(Base64.getEncoder().encodeToString(photo.getPhotoData()));
        }

        dto.setPhotoUrl(photo.getPhotoUrl());
        return dto;
    }

    private MerchantReplyDTO convertReplyToDTO(MerchantReply reply) {
        MerchantReplyDTO dto = new MerchantReplyDTO();
        dto.setId(reply.getId());
        dto.setReviewId(reply.getReview().getId());
        dto.setMerchantId(reply.getMerchant().getId());
        dto.setMerchantName(reply.getMerchant().getBusinessName());
        dto.setReplyText(reply.getReplyText());
        dto.setReplyType(reply.getReplyType());
        dto.setCreatedAt(reply.getCreatedAt());
        dto.setUpdatedAt(reply.getUpdatedAt());
        return dto;
    }
}
