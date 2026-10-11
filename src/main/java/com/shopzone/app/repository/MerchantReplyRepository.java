package com.shopzone.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopzone.app.entity.MerchantReply;

import java.util.List;
import java.util.Optional;

@Repository
public interface MerchantReplyRepository extends JpaRepository<MerchantReply, Long> {

    // Get all replies to a specific review
    List<MerchantReply> findByReviewId(Long reviewId);

    // Get all replies by a merchant
    List<MerchantReply> findByMerchantId(Long merchantId);

    // Get the most recent reply for a review
    Optional<MerchantReply> findFirstByReviewIdOrderByCreatedAtDesc(Long reviewId);

    // Check if merchant has replied to this review
    @Query("SELECT CASE WHEN COUNT(mr) > 0 THEN true ELSE false END FROM MerchantReply mr " +
           "WHERE mr.review.id = :reviewId AND mr.merchant.id = :merchantId")
    Boolean hasMerchantRepliedToReview(@Param("reviewId") Long reviewId, @Param("merchantId") Long merchantId);

    // Count total replies by merchant
    @Query("SELECT COUNT(mr) FROM MerchantReply mr WHERE mr.merchant.id = :merchantId")
    Integer countRepliesByMerchant(@Param("merchantId") Long merchantId);

    // Find replies by type (RESOLUTION, THANK_YOU, etc.)
    List<MerchantReply> findByReviewIdAndReplyType(Long reviewId, String replyType);
}
