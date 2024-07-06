package com.infinity.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductPurchaseRequest(
        @NotNull(message = "Product id is required")
        Integer productId,
        @NotNull(message = "Quantity is mandatory")
        @Positive(message = "Quantity can not be negative")
        Integer quantity
) {
}
