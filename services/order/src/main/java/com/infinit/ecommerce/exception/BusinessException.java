package com.infinit.ecommerce.exception;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {
    public final String message;
    public BusinessException(
            @NotNull(message = "Customer id should be provided")
            @NotEmpty(message = "Products should not be empty")
            @NotBlank(message = "Customer id should not be blank")
            String s) {
        this.message = "Business Exception: " + s;
    }
}
