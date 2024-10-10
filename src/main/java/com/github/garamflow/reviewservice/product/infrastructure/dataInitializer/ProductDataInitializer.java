package com.github.garamflow.reviewservice.product.infrastructure.dataInitializer;

import com.github.garamflow.reviewservice.product.domain.repository.ProductRepository;
import com.github.garamflow.reviewservice.product.domain.model.Product;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

@Component
public class ProductDataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;

    public ProductDataInitializer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() == 0) {
            for (int i = 1; i <= 100; i++) {
                Product product = Product.builder()
                        .reviewCount(0) // 초기 리뷰 개수는 0
                        .score(0.0f)    // 초기 평균 점수는 0.0
                        .build();

                productRepository.save(product);
            }
        }
    }
}
