package com.sentinelx.fraud.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DLQConsumer {

    @KafkaListener(
            topics = "transaction-created.DLQ",
            groupId = "dlq-group"
    )
    public void consume(
            Object message
    ) {

        log.error(
                "DLQ MESSAGE RECEIVED: {}",
                message
        );

        log.error(
                "Manual investigation required!"
        );
    }
}