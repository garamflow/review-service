package com.github.garamflow.reviewservice.review.infrastructure.persistence;

import com.github.garamflow.reviewservice.review.domain.model.Review;
import com.github.garamflow.reviewservice.review.domain.repository.ReviewRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaReviewRepository extends JpaRepository<Review, Long>, ReviewRepository {
    @Override
    @Query("SELECT r FROM Review r WHERE r.product.id = :productId AND (:cursor IS NULL OR r.id < :cursor) ORDER BY r.createdAt DESC")
    List<Review> findByProductIdWithCursor(@Param("productId") Long productId, @Param("cursor") Long cursor, Pageable pageable);

    @Override
    boolean existsByProductIdAndUserId(Long productId, Long userId);
}
