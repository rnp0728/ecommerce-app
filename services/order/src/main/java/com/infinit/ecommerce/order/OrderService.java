package com.infinit.ecommerce.order;

import com.infinit.ecommerce.customer.CustomerClient;
import com.infinit.ecommerce.exception.BusinessException;
import com.infinit.ecommerce.kafka.OrderConfirmation;
import com.infinit.ecommerce.kafka.OrderProducer;
import com.infinit.ecommerce.orderLine.OrderLineRequest;
import com.infinit.ecommerce.orderLine.OrderLineService;
import com.infinit.ecommerce.payment.PaymentClient;
import com.infinit.ecommerce.payment.PaymentRequest;
import com.infinit.ecommerce.product.ProductClient;
import com.infinit.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final PaymentClient paymentClient;

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    public Integer createOrder(OrderRequest request) {
        // steps
        // 1. check the customer
        var customer = this.customerClient.findCustomerById(
                request.customerId()
        ).orElseThrow(
                () -> new BusinessException("Cannot find customer with id: " + request.customerId() + " in the system.")
        );
        // 2. purchase the products (using ProductService)
        var purchasedProducts = this.productClient.purchaseProducts(
          request.products()
        );
        // 3. create the order (using OrderMapper)
        // 4. save the order (using OrderRepository)
        var order = this.orderRepository.save(orderMapper.toOrder(request));
        for (PurchaseRequest purchaseRequest : request.products()){
            // add the order line to the order
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }
        // 5. start the payment process (using PaymentService)
        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);
        // 6. send the order confirmation email (using NotificationService)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );
        return order.getId();
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository
            .findAll()
            .stream()
            .map(orderMapper::toOrderResponse)
            .toList();
    }

    public OrderResponse findById(Integer orderId) {
        return orderRepository.findById(orderId)
               .map(orderMapper::toOrderResponse)
               .orElseThrow(
                       () -> new EntityNotFoundException("Cannot find order with id: " + orderId + " in the system.")
               );
    }
}
