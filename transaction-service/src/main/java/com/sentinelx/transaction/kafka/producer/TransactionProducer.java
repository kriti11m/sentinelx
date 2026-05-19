package com.sentinelx.transaction.kafka.producer;

import com.sentinelx.events.transaction.TransactionEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionProducer {

    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;

    public void sendTransactionEvent(
            TransactionEvent event
    ) {

        log.info(
                "Sending Transaction Event: {}",
                event
        );

        kafkaTemplate.send(
                "transaction-created",
                event
        );
    }
}