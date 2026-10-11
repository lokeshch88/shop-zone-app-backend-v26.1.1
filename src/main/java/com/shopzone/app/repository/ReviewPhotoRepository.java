package com.shopzone.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopzone.app.entity.ReviewPhoto;

import java.util.List;

@Repository
public interface ReviewPhotoRepository extends JpaRepository<ReviewPhoto, Long> {

    // Find all photos for a review (ordered by display order)
    List<ReviewPhoto> findByReviewIdOrderByDisplayOrder(Long reviewId);

    // Find all photos for a review
    List<ReviewPhoto> findByReviewId(Long reviewId);

    // Count photos for a review (MAX 5)
    int countByReviewId(Long reviewId);

    // Delete all photos for a review
    void deleteByReviewId(Long reviewId);
}
