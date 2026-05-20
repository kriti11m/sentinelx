package com.sentinelx.notification.kafka.consumer;

import com.sentinelx.events.transaction.TransactionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationConsumer {

    @KafkaListener(
            topics = "transaction-created",
            groupId = "notification-group"
    )
    public void consume(
            TransactionEvent event
    ) {

        log.info(
                "Notification Service Received Transaction: {}",
                event
        );

        log.info(
                "Sending transaction notification to user..."
        );
    }
}