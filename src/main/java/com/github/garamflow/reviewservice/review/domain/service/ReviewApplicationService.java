package com.github.garamflow.reviewservice.review.domain.service;

import com.github.garamflow.reviewservice.product.domain.model.Product;
import com.github.garamflow.reviewservice.product.domain.repository.ProductRepository;
import com.github.garamflow.reviewservice.review.application.dto.CreateReviewRequest;
import com.github.garamflow.reviewservice.review.application.dto.ReviewListResponse;
import com.github.garamflow.reviewservice.review.application.dto.ReviewResponse;
import com.github.garamflow.reviewservice.review.application.service.ReviewDomainService;
import com.github.garamflow.reviewservice.review.domain.model.Review;
import com.github.garamflow.reviewservice.review.domain.repository.ReviewRepository;
import com.github.garamflow.reviewservice.shared.application.service.S3StorageService;
import com.github.garamflow.reviewservice.user.domain.model.User;
import com.github.garamflow.reviewservice.user.domain.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReviewApplicationService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final S3StorageService s3StorageService;
    private final ReviewDomainService reviewDomainService;


    public ReviewApplicationService(ReviewRepository reviewRepository, ProductRepository productRepository, UserRepository userRepository, S3StorageService s3StorageService, ReviewDomainService reviewDomainService) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.s3StorageService = s3StorageService;
        this.reviewDomainService = reviewDomainService;
    }

    @Transactional
    public ReviewResponse addReview(Long productId, CreateReviewRequest request, MultipartFile image) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("상품이 존재하지 않습니다."));

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new NoSuchElementException("사용자가 존재하지 않습니다."));

        if (reviewRepository.existsByProductIdAndUserId(productId, user.getId())) {
            throw new EntityExistsException("이미 작성하신 리뷰가 존재합니다.");
        }

        String imageUrl = s3StorageService.uploadFile(image);

        Review review = reviewDomainService.addReview(product, user, request.content(), request.score(), imageUrl);
        Review savedReview = reviewRepository.save(review);

        return new ReviewResponse(savedReview);
    }

    @Transactional(readOnly = true)
    public ReviewListResponse getReviews(Long productId, Long cursor, int size) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("상품이 존재하지 않습니다."));

        Pageable pageable = PageRequest.of(0, size);
        List<Review> reviews = reviewRepository.findByProductIdWithCursor(productId, cursor, pageable);

        Long nextCursor = reviews.isEmpty() ? null : reviews.get(reviews.size() - 1).getId();

        List<ReviewResponse> reviewResponses = reviews.stream()
                .map(ReviewResponse::new)
                .toList();

        return new ReviewListResponse(product.getReviewCount(), product.getScore(), nextCursor, reviewResponses);
    }
}
