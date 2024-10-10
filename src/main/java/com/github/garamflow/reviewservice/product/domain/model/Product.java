package com.github.garamflow.reviewservice.product.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer reviewCount;
    private Float score;



    @Builder
    public Product(Integer reviewCount, Float score) {
        this.reviewCount = reviewCount;
        this.score = score;
    }

    public void updateReviewCountAndScore(Float newScore) {
        this.reviewCount += 1;
        this.score = ((this.score * (this.reviewCount - 1)) + newScore) / this.reviewCount;
    }
}
