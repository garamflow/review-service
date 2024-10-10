package com.github.garamflow.reviewservice.review.application.dto;

import com.github.garamflow.reviewservice.review.domain.model.Review;

import java.time.LocalDateTime;

public record ReviewResponse(
        Long id,
        Long userId,
        Float score,
        String content,
        String imageUrl,
        LocalDateTime createdAt
) {
    public ReviewResponse(Review review) {
        this(
                review.getId(),
                review.getUser().getId(),
                review.getScore(),
                review.getContent(),
                review.getImageUrl(),
                review.getCreatedAt()
        );
    }
}
