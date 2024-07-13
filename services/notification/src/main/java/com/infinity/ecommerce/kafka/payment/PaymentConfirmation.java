package com.infinity.ecommerce.kafka.payment;

import java.math.BigDecimal;

public record PaymentConfirmation(
        String paymentReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerFirstName,
        String customerLastName,
        String customerEmail
) {
}
