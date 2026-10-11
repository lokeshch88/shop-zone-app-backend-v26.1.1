package com.shopzone.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shopzone.app.entity.Merchant;
import com.shopzone.app.entity.Product;
import com.shopzone.app.entity.ProductReview;
import com.shopzone.app.entity.ReviewPhoto;
import com.shopzone.app.entity.MerchantReply;
import com.shopzone.app.entity.User;
import com.shopzone.app.repository.ProductReviewRepository;
import com.shopzone.app.repository.ReviewPhotoRepository;
import com.shopzone.app.repository.MerchantReplyRepository;
import com.shopzone.app.repo.ProductRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ProductReviewRepository productReviewRepository;

    @Autowired
    private ReviewPhotoRepository reviewPhotoRepository;

    @Autowired
    private MerchantReplyRepository merchantReplyRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AuthenticationService authenticationService;

    private static final Logger log = LoggerFactory.getLogger(ReviewService.class);

    // ====================================
    // CREATE REVIEW
    // ====================================
    public ProductReview createReview(
            Long productId,
            Integer rating,
            String title,
            String content) {

        log.info("Creating review for product: {}", productId);

        try {
            // 1. Get current user
            User currentUser = authenticationService.getCurrentUser();
            if (currentUser == null) {
                throw new RuntimeException("User not authenticated");
            }
            
            //check product merchant review limit and enabled
            

            // 2. Get product
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // 3. Check: User cannot have duplicate review
            if (productReviewRepository.hasUserReviewedProduct(productId, currentUser.getId())) {
                throw new IllegalArgumentException("You have already reviewed this product");
            }

            // 4. Validate rating (1-5)
            if (rating == null || rating < 1 || rating > 5) {
                throw new IllegalArgumentException("Rating must be between 1 and 5");
            }

            // 5. Create review
            ProductReview review = new ProductReview();
            review.setProduct(product);
            review.setUser(currentUser);
            review.setRating(rating);
            review.setTitle(title);
            review.setContent(content);
            review.setIsVerifiedPurchase(checkIfUserPurchased(currentUser.getId(), productId));
            review.setIsApproved(false);  // Pending admin approval
            review.setCreatedAt(LocalDateTime.now());
            review.setUpdatedAt(LocalDateTime.now());

            ProductReview savedReview = productReviewRepository.save(review);
            
            log.info("Review created successfully for product: {} by user: {}", productId, currentUser.getUsername());
            return savedReview;

        } catch (Exception e) {
            log.error("Error creating review", e);
            throw new RuntimeException("Failed to create review: " + e.getMessage());
        }
    }

    // ====================================
    // UPDATE REVIEW (Own reviews only)
    // ====================================
    public ProductReview updateReview(
            Long reviewId,
            Integer rating,
            String title,
            String content) {

        log.info("Updating review: {}", reviewId);

        try {
            User currentUser = authenticationService.getCurrentUser();
            ProductReview review = productReviewRepository.findById(reviewId)
                    .orElseThrow(() -> new RuntimeException("Review not found"));

            // Only owner can edit
            if (!review.getUser().getId().equals(currentUser.getId())) {
                throw new UnauthorizedException("You can only edit your own review");
            }

            // Update fields
            if (rating != null && rating >= 1 && rating <= 5) {
                review.setRating(rating);
            }
            if (title != null) {
                review.setTitle(title);
            }
            if (content != null) {
                review.setContent(content);
            }
            review.setUpdatedAt(LocalDateTime.now());
            review.setIsApproved(false);  // Re-approve after edit

            ProductReview updated = productReviewRepository.save(review);
            log.info("Review updated: {}", reviewId);
            return updated;

        } catch (Exception e) {
            log.error("Error updating review", e);
            throw new RuntimeException("Failed to update review: " + e.getMessage());
        }
    }

    // ====================================
    // DELETE REVIEW (Own reviews only)
    // ====================================
    public void deleteReview(Long reviewId) {
        log.info("Deleting review: {}", reviewId);

        try {
            User currentUser = authenticationService.getCurrentUser();
            ProductReview review = productReviewRepository.findById(reviewId)
                    .orElseThrow(() -> new RuntimeException("Review not found"));

            // Only owner can delete
            if (!review.getUser().getId().equals(currentUser.getId())) {
                throw new UnauthorizedException("You can only delete your own review");
            }

            productReviewRepository.deleteById(reviewId);
            log.info("Review deleted: {}", reviewId);

        } catch (Exception e) {
            log.error("Error deleting review", e);
            throw new RuntimeException("Failed to delete review: " + e.getMessage());
        }
    }

    // ====================================
    // GET REVIEWS FOR PRODUCT
    // ====================================
    public Page<ProductReview> getProductReviews(Long productId, int page, int size) {
        log.info("Fetching reviews for product: {}", productId);

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<ProductReview> reviews = productReviewRepository.findByProductIdAndIsApprovedTrue(productId, pageable);

        log.info("Found {} reviews for product: {}", reviews.getTotalElements(), productId);
        return reviews;
    }

    // ====================================
    // GET REVIEWS BY RATING
    // ====================================
    public Page<ProductReview> getReviewsByRating(Long productId, Integer rating, int page, int size) {
        log.info("Fetching {} star reviews for product: {}", rating, productId);

        if (rating == null || rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return productReviewRepository.findByProductIdAndRating(productId, rating, pageable);
    }

    // ====================================
    // GET USER'S REVIEWS
    // ====================================
    public Page<ProductReview> getUserReviews(Long userId, int page, int size) {
        log.info("Fetching reviews by user: {}", userId);

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return productReviewRepository.findByUserId(userId, pageable);
    }

    // ====================================
    // GET RATING DISTRIBUTION
    // ====================================
    public List<Object[]> getRatingDistribution(Long productId) {
        log.info("Fetching rating distribution for product: {}", productId);
        return productReviewRepository.getRatingDistribution(productId);
    }

    // ====================================
    // GET AVERAGE RATING
    // ====================================
    public Double getAverageRating(Long productId) {
        Double average = productReviewRepository.getAverageRatingByProductId(productId);
        return average != null ? average : 0.0;
    }

    // ====================================
    // UPDATE PRODUCT RATING (Call after review approved)
    // ====================================
    public void updateProductRating(Long productId) {
        log.info("Updating product rating for: {}", productId);

        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            Double avgRating = productReviewRepository.getAverageRatingByProductId(productId);
            Integer count = productReviewRepository.countApprovedReviewsByProductId(productId);

            product.setAverageRating(BigDecimal.valueOf(avgRating != null ? avgRating : 0.0));
            product.setTotalReviews(count != null ? count : 0);

            productRepository.save(product);
            log.info("Product rating updated: {}", productId);

        } catch (Exception e) {
            log.error("Error updating product rating", e);
        }
    }

    // ====================================
    // ADD PHOTO TO REVIEW (Max 5)
    // ====================================
    public ReviewPhoto addPhotoToReview(Long reviewId, MultipartFile photoFile, int displayOrder) throws IOException {
        log.info("Adding photo to review: {}", reviewId);

        try {
            User currentUser = authenticationService.getCurrentUser();
            ProductReview review = productReviewRepository.findById(reviewId)
                    .orElseThrow(() -> new RuntimeException("Review not found"));

            // Only review owner can add photos
            if (!review.getUser().getId().equals(currentUser.getId())) {
                throw new UnauthorizedException("You can only add photos to your own review");
            }

            // Check: Max 5 photos per review
            int photoCount = reviewPhotoRepository.countByReviewId(reviewId);
            if (photoCount >= 5) {
                throw new IllegalArgumentException("Maximum 5 photos allowed per review");
            }

            // Validate order
            if (displayOrder < 1 || displayOrder > 5) {
                throw new IllegalArgumentException("Display order must be between 1 and 5");
            }

            // Create photo
            ReviewPhoto photo = new ReviewPhoto();
            photo.setReview(review);
            photo.setPhotoData(photoFile.getBytes());
            photo.setDisplayOrder(displayOrder);
            photo.setUploadedAt(LocalDateTime.now());

            ReviewPhoto savedPhoto = reviewPhotoRepository.save(photo);
            log.info("Photo added to review: {}", reviewId);
            return savedPhoto;

        } catch (IOException e) {
            log.error("Error uploading photo", e);
            throw new IOException("Failed to upload photo: " + e.getMessage());
        }
    }

    // ====================================
    // DELETE PHOTO FROM REVIEW
    // ====================================
    public void deletePhotoFromReview(Long photoId) {
        log.info("Deleting photo: {}", photoId);

        try {
            User currentUser = authenticationService.getCurrentUser();
            ReviewPhoto photo = reviewPhotoRepository.findById(photoId)
                    .orElseThrow(() -> new RuntimeException("Photo not found"));

            // Only review owner can delete
            if (!photo.getReview().getUser().getId().equals(currentUser.getId())) {
                throw new UnauthorizedException("You can only delete your own photos");
            }

            reviewPhotoRepository.deleteById(photoId);
            log.info("Photo deleted: {}", photoId);

        } catch (Exception e) {
            log.error("Error deleting photo", e);
            throw new RuntimeException("Failed to delete photo: " + e.getMessage());
        }
    }

    // ====================================
    // MERCHANT/ADMIN REPLY TO REVIEW
    // ====================================
    public MerchantReply replyToReview(
            Long reviewId,
            String replyText,
            String replyType) {

        log.info("Adding reply to review: {}", reviewId);

        try {
            User currentUser = authenticationService.getCurrentUser();
            Merchant merchant = currentUser.getMerchant();

            if (merchant == null) {
                throw new UnauthorizedException("Only merchants can reply to reviews");
            }

            ProductReview review = productReviewRepository.findById(reviewId)
                    .orElseThrow(() -> new RuntimeException("Review not found"));

            // Verify merchant owns product
            if (!review.getProduct().getMerchant().getId().equals(merchant.getId())) {
                throw new UnauthorizedException("You can only reply to reviews of your products");
            }

            // Validate reply
            if (replyText == null || replyText.trim().isEmpty()) {
                throw new IllegalArgumentException("Reply cannot be empty");
            }

            if (replyText.length() < 5 || replyText.length() > 2000) {
                throw new IllegalArgumentException("Reply must be between 5 and 2000 characters");
            }

            // Create reply
            MerchantReply reply = new MerchantReply();
            reply.setReview(review);
            reply.setMerchant(merchant);
            reply.setReplyText(replyText);
            reply.setReplyType(replyType);
            reply.setCreatedAt(LocalDateTime.now());
            reply.setUpdatedAt(LocalDateTime.now());

            MerchantReply savedReply = merchantReplyRepository.save(reply);
            log.info("Reply added to review: {}", reviewId);
            return savedReply;

        } catch (Exception e) {
            log.error("Error adding reply", e);
            throw new RuntimeException("Failed to add reply: " + e.getMessage());
        }
    }

    // ====================================
    // GET REPLIES FOR REVIEW
    // ====================================
    public List<MerchantReply> getReviewReplies(Long reviewId) {
        log.info("Fetching replies for review: {}", reviewId);
        return merchantReplyRepository.findByReviewId(reviewId);
    }

    // ====================================
    // Check if user purchased product
    // ====================================
    private boolean checkIfUserPurchased(Long userId, Long productId) {
        // TODO: Check order history
        // For now, return false (implement order verification)
        return false;
    }

    // ====================================
    // ADMIN APPROVE REVIEW
    // ====================================
    public void approveReview(Long reviewId) {
        log.info("Approving review: {}", reviewId);

        try {
            // TODO: Add admin role check
            ProductReview review = productReviewRepository.findById(reviewId)
                    .orElseThrow(() -> new RuntimeException("Review not found"));

            review.setIsApproved(true);
            review.setRejectionReason(null);
            productReviewRepository.save(review);

            // Update product rating
            updateProductRating(review.getProduct().getId());
            log.info("Review approved: {}", reviewId);

        } catch (Exception e) {
            log.error("Error approving review", e);
            throw new RuntimeException("Failed to approve review: " + e.getMessage());
        }
    }

    // ====================================
    // ADMIN REJECT REVIEW
    // ====================================
    public void rejectReview(Long reviewId, String reason) {
        log.info("Rejecting review: {}", reviewId);

        try {
            // TODO: Add admin role check
            ProductReview review = productReviewRepository.findById(reviewId)
                    .orElseThrow(() -> new RuntimeException("Review not found"));

            review.setIsApproved(false);
            review.setRejectionReason(reason);
            productReviewRepository.save(review);
            log.info("Review rejected: {}", reviewId);

        } catch (Exception e) {
            log.error("Error rejecting review", e);
            throw new RuntimeException("Failed to reject review: " + e.getMessage());
        }
    }

    // ====================================
    // GET UNAPPROVED REVIEWS (Admin)
    // ====================================
    public Page<ProductReview> getUnapprovedReviews(int page, int size) {
        log.info("Fetching unapproved reviews");

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());
        return productReviewRepository.findByIsApprovedFalse(pageable);
    }
}

class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
