package com.infinit.ecommerce.payment;

import com.infinit.ecommerce.customer.CustomerResponse;
import com.infinit.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
