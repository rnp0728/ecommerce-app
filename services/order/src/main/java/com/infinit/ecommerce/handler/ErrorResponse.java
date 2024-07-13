package com.infinit.ecommerce.handler;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {
}
