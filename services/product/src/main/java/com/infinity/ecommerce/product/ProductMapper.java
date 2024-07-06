package com.infinity.ecommerce.product;

import com.infinity.ecommerce.category.Category;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public Product toProduct(ProductRequest r) {
        return Product.builder()
                .name(r.name())
                .description(r.description())
                .price(r.price())
                .availableQuantity(r.availableQuantity())
                .category(
                        Category.builder()
                                .id(r.categoryId())
                                .build()
                )
                .build();
    }

    public ProductResponse toProductResponse(Product product) {
        return new ProductResponse(product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAvailableQuantity(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCategory().getDescription()
        );
    }
}
