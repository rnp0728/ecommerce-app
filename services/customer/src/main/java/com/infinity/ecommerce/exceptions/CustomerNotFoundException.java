package com.infinity.ecommerce.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerNotFoundException extends RuntimeException {
    public final String message;
    public CustomerNotFoundException(String id) {
        super("Customer not found with id: " + id);
        this.message = "Customer not found with id: " + id;
    }
}
