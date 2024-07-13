package com.infinity.ecommerce.kafka;

import com.infinity.ecommerce.email.EmailService;
import com.infinity.ecommerce.kafka.order.OrderConfirmation;
import com.infinity.ecommerce.kafka.payment.PaymentConfirmation;
import com.infinity.ecommerce.notification.Notification;
import com.infinity.ecommerce.notification.NotificationRepository;
import com.infinity.ecommerce.notification.NotificationType;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotification(
            PaymentConfirmation paymentConfirmation
    ) throws MessagingException {
        log.info("Consuming payment notification: {}", paymentConfirmation);

        notificationRepository.save(
                Notification.builder()
                        .type(NotificationType.PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build()
        );

        // todo send email notification to customer
        var customerName  = paymentConfirmation.customerFirstName() + paymentConfirmation.customerLastName();
        emailService.sendPaymentSuccessEmail(
                paymentConfirmation.customerEmail(),
                customerName,
                paymentConfirmation.amount(),
                paymentConfirmation.paymentReference()
        );
    }
    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotification(
            OrderConfirmation orderConfirmation
    ) throws MessagingException {
        log.info("Consuming order confirmation notification: {}", orderConfirmation);

        notificationRepository.save(
                Notification.builder()
                        .type(NotificationType.ORDER_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()
        );

        var customerName  = orderConfirmation.customer().firstName() + orderConfirmation.customer().lastName();
        emailService.sendOrderConfirmationEmail(
                orderConfirmation.customer().email(),
                customerName,
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
        );
    }
}
