package com.sentinelx.fraud.kafka.consumer;

import com.sentinelx.events.transaction.TransactionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FraudConsumer {

    @KafkaListener(
            topics = "transaction-created",
            groupId = "fraud-group"
    )
    public void consume(
            TransactionEvent event
    ) {

        log.info(
                "Fraud Service Received Event: {}",
                event
        );

        if (event.getAmount() > 50000) {

            log.warn(
                    "High Risk Transaction Detected!"
            );
        }
    }
}