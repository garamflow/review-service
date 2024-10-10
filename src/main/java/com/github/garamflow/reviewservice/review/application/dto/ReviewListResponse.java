package com.github.garamflow.reviewservice.review.application.dto;

import java.util.List;

public record ReviewListResponse(
        Integer totalCount,
        Float averageScore,
        Long cursor,
        List<ReviewResponse> reviews
) {
}