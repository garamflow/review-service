package com.github.garamflow.reviewservice.review.domain.repository;

import com.github.garamflow.reviewservice.review.domain.model.Review;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewRepository {
    List<Review> findByProductIdWithCursor(Long productId, Long cursor, Pageable pageable);
    boolean existsByProductIdAndUserId(Long productId, Long userId);

    Review save(Review review);
}
