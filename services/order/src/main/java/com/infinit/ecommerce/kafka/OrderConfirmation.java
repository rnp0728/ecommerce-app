package com.infinit.ecommerce.kafka;

import com.infinit.ecommerce.customer.CustomerResponse;
import com.infinit.ecommerce.order.PaymentMethod;
import com.infinit.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customerResponse,
        List<PurchaseResponse> products
) {
}
