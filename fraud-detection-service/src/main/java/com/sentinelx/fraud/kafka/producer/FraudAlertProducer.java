package com.sentinelx.fraud.kafka.producer;

import com.sentinelx.events.fraud.FraudAlertEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FraudAlertProducer {

    private final KafkaTemplate<String,
            FraudAlertEvent> kafkaTemplate;

    public void sendFraudAlert(
            FraudAlertEvent event
    ) {

        log.info(
                "Publishing Fraud Alert: {}",
                event
        );

        kafkaTemplate.send(
                "fraud-alert",
                event
        );
    }
}