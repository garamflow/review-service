package com.github.garamflow.reviewservice.review.application.dto;

public record CreateReviewRequest(
        Long userId,
        Integer score,
        String content
){
    public CreateReviewRequest {
        if(score < 1  || score > 5) {
            throw new IllegalArgumentException("Score must be between 1 and 5");
        }

        if(content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be empty");
        }
    }
}