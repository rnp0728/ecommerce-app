package com.infinit.ecommerce.orderLine;

public record OrderLineRequest(
        Integer id,
        Integer orderId,
        Integer productId,
        Integer quantity
) {

}
