package com.infinit.ecommerce.payment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "payment-service",
        url = "${application.config.payment-url}"  // default to localhost:8082 if not provided in application.properties
)
public interface PaymentClient {
    @PostMapping
    Integer requestOrderPayment(
            @RequestBody PaymentRequest paymentRequest
    );
}
