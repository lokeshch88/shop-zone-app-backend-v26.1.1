package com.shopzone.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopzone.app.entity.ProductReview;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {

    // Find all approved reviews for a product with pagination
    Page<ProductReview> findByProductIdAndIsApprovedTrue(Long productId, Pageable pageable);

    // Find all reviews by product (for admin)
    List<ProductReview> findByProductId(Long productId);

    // Find reviews by user (user's review history)
    Page<ProductReview> findByUserId(Long userId, Pageable pageable);

    // Find single review by user and product (one review per user per product)
    Optional<ProductReview> findByProductIdAndUserId(Long productId, Long userId);

    // Check if user has already reviewed this product (prevent duplicate)
    @Query("SELECT CASE WHEN COUNT(pr) > 0 THEN true ELSE false END FROM ProductReview pr " +
           "WHERE pr.product.id = :productId AND pr.user.id = :userId")
    Boolean hasUserReviewedProduct(@Param("productId") Long productId, @Param("userId") Long userId);

    // Calculate average rating for a product
    @Query("SELECT AVG(pr.rating) FROM ProductReview pr WHERE pr.product.id = :productId AND pr.isApproved = true")
    Double getAverageRatingByProductId(@Param("productId") Long productId);

    // Count approved reviews for a product
    @Query("SELECT COUNT(pr) FROM ProductReview pr WHERE pr.product.id = :productId AND pr.isApproved = true")
    Integer countApprovedReviewsByProductId(@Param("productId") Long productId);

    // Find verified purchase reviews only
    Page<ProductReview> findByProductIdAndIsVerifiedPurchaseTrueAndIsApprovedTrue(Long productId, Pageable pageable);

    // Recent reviews
    @Query("SELECT pr FROM ProductReview pr WHERE pr.product.id = :productId AND pr.isApproved = true " +
           "ORDER BY pr.createdAt DESC")
    Page<ProductReview> getRecentReviews(@Param("productId") Long productId, Pageable pageable);

    // Rating distribution (1-5 stars count)
    @Query("SELECT pr.rating, COUNT(pr) FROM ProductReview pr WHERE pr.product.id = :productId " +
           "AND pr.isApproved = true GROUP BY pr.rating ORDER BY pr.rating DESC")
    List<Object[]> getRatingDistribution(@Param("productId") Long productId);

    // Find unapproved reviews (for admin moderation)
    Page<ProductReview> findByIsApprovedFalse(Pageable pageable);

    // Reviews with specific rating
    @Query("SELECT pr FROM ProductReview pr WHERE pr.product.id = :productId AND pr.rating = :rating " +
           "AND pr.isApproved = true")
    Page<ProductReview> findByProductIdAndRating(@Param("productId") Long productId, 
                                                   @Param("rating") Integer rating, Pageable pageable);
}
