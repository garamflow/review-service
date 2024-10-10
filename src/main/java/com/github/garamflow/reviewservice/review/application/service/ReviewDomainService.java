package com.github.garamflow.reviewservice.review.application.service;

import com.github.garamflow.reviewservice.product.domain.model.Product;
import com.github.garamflow.reviewservice.review.domain.model.Review;
import com.github.garamflow.reviewservice.user.domain.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReviewDomainService {
    public Review addReview(Product product, User user, String content, Integer score, String imageUrl) {
        return new Review.Builder(product, user, content, score, LocalDateTime.now())
                .imageUrl(imageUrl)
                .build();
    }
}
