package com.github.garamflow.reviewservice.review.application.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.garamflow.reviewservice.review.domain.service.ReviewApplicationService;
import com.github.garamflow.reviewservice.review.application.dto.CreateReviewRequest;
import com.github.garamflow.reviewservice.review.application.dto.ReviewListResponse;
import com.github.garamflow.reviewservice.review.application.dto.ReviewResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/products/{productsId}/reviews")
public class ReviewController {
    private final ReviewApplicationService reviewApplicationService;

    public ReviewController(ReviewApplicationService reviewApplicationService) {
        this.reviewApplicationService = reviewApplicationService;
    }

    @GetMapping
    public ResponseEntity<ReviewListResponse> getReviews(
            @PathVariable Long productsId,
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "10") int size
    ) {
        ReviewListResponse response = reviewApplicationService.getReviews(productsId, cursor, size);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ReviewResponse> addReview(
            @PathVariable Long productsId,
            @RequestPart("request") String request,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        CreateReviewRequest reviewRequest = objectMapper.readValue(request, CreateReviewRequest.class);

        ReviewResponse response = reviewApplicationService.addReview(productsId, reviewRequest, image);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
