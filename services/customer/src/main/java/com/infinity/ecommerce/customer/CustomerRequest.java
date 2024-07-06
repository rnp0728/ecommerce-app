package com.infinity.ecommerce.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        String id,

        @NotNull(message = "First Name cannot be null")
        String firstName,

        @NotNull(message = "Last Name cannot be null")
        String lastName,

        @NotNull(message = "Email cannot be null")
        @Email(message = "Email must be valid")
        String email,

        Address address
) {
}
