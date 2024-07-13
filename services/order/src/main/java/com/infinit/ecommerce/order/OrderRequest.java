package com.infinit.ecommerce.order;

import com.infinit.ecommerce.product.PurchaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        String reference,
        @Positive(message = "The amount must be positive")
        BigDecimal amount,
        @NotNull(message = "Payment method should be provided")
        PaymentMethod paymentMethod,
        @NotNull(message = "Customer id should be provided")
        @NotEmpty(message = "Products should not be empty")
        @NotBlank(message = "Customer id should not be blank")
        String customerId,
        @NotEmpty(message = "Products should not be empty")
        List<PurchaseRequest> products
) {
}
