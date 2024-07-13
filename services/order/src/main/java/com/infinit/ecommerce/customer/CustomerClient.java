package com.infinit.ecommerce.customer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(
        name = "customer-service",
        url = "${application.config.customer-url}"  // default to localhost:8081 if not provided in application.properties
)
public interface CustomerClient {
    @GetMapping("/{customerId}")
    Optional<CustomerResponse> findCustomerById(
            @PathVariable("customerId") String customerId
    );
}
