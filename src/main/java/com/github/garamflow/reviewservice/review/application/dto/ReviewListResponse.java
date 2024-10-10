package com.github.garamflow.reviewservice.review.application.dto;

import java.util.List;

public record ReviewListResponse(
        Integer totalCount,
        Double score,
        Long cursor,
        List<ReviewResponse> reviews
) {
}