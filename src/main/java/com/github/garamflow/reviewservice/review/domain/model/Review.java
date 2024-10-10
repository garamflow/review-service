package com.github.garamflow.reviewservice.review.domain.model;

import com.github.garamflow.reviewservice.product.domain.model.Product;
import com.github.garamflow.reviewservice.user.domain.model.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "Review", indexes = {
        @Index(name = "idx_product_createdAt", columnList = "product_id, createdAt")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = {"product", "user"})
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Float score;

    private String imageUrl;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Review(Builder builder) {
        this.product = builder.product;
        this.user = builder.user;
        this.content = builder.content;
        this.score = builder.score;
        this.imageUrl = builder.imageUrl;
        this.createdAt = builder.createdAt;
    }

    public static class Builder {
        private final Product product;
        private final User user;
        private final String content;
        private final Float score;
        private final LocalDateTime createdAt;

        private String imageUrl;

        public Builder(Product product, User user, String content, Float score, LocalDateTime createdAt) {
            this.product = product;
            this.user = user;
            this.content = content;
            this.score = score;
            this.createdAt = createdAt;

            product.updateReviewCountAndScore(build().score);
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Review build() {
            if (score < 1 || score > 5) {
                throw new IllegalArgumentException("Score must be between 1 and 5");
            }

            return new Review(this);
        }
    }
}
