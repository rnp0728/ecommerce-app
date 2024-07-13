package com.infinity.ecommerrce.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {

    private final KafkaTemplate<String, PaymentNotificationRequest> kafkaTemplate;

    public  void sendPaymentNotification(
            PaymentNotificationRequest request
    ) {
        log.info("Sending payment notification: {}", request);

        Message<PaymentNotificationRequest> message =
                MessageBuilder.withPayload(request)
                        .setHeader(TOPIC, "payment-topic")
                        .build();
        kafkaTemplate.send(message);
    }
}
