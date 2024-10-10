package com.github.garamflow.reviewservice.product.domain.repository;

import com.github.garamflow.reviewservice.product.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
