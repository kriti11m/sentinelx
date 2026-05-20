package com.sentinelx.fraud.kafka.consumer;

import com.sentinelx.events.transaction.TransactionEvent;
import com.sentinelx.fraud.service.ProcessedEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FraudConsumer {

    private final ProcessedEventService
            processedEventService;

    @KafkaListener(
            topics = "transaction-created",
            groupId = "fraud-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(TransactionEvent event) {

        log.info(
                "Fraud Service Received Event: {}",
                event
        );

        // ===============================
        // IDEMPOTENCY CHECK
        // ===============================

        if (processedEventService.isProcessed(
                event.getTransactionId()
        )) {

            log.warn(
                    "Duplicate Transaction Ignored: {}",
                    event.getTransactionId()
            );

            return;
        }

        // ===============================
        // MARK AS PROCESSED
        // ===============================

        processedEventService.markProcessed(
                event.getTransactionId()
        );

        // ===============================
        // FRAUD ANALYSIS LOGIC
        // ===============================

        if (event.getAmount() > 50000) {

            log.error(
                    "Fraud Analysis Failed for Transaction: {}",
                    event.getTransactionId()
            );

            throw new RuntimeException(
                    "Fraud Analysis Failed!"
            );
        }

        log.info(
                "Transaction Processed Successfully"
        );
    }
}