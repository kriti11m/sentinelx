package com.sentinelx.fraud.kafka.consumer;

import com.sentinelx.events.fraud.FraudAlertEvent;
import com.sentinelx.events.transaction.TransactionEvent;
import com.sentinelx.fraud.kafka.producer.FraudAlertProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class FraudConsumer {

    private final FraudAlertProducer fraudAlertProducer;

    @KafkaListener(
            topics = "transaction-created",
            groupId = "fraud-group"
    )
    public void consume(TransactionEvent event) {

        log.info(
                "Fraud Service Received Event: {}",
                event
        );

        if (event.getAmount() > 50000) {

            FraudAlertEvent fraudAlert =
                    new FraudAlertEvent();

            fraudAlert.setTransactionId(
                    event.getTransactionId()
            );

            // Using sender as suspicious account/user
            fraudAlert.setUserId(
                    event.getSender()
            );

            fraudAlert.setAmount(
                    event.getAmount()
            );

            fraudAlert.setReason(
                    "High Amount Transaction"
            );

            fraudAlert.setRiskScore(
                    0.95
            );

            fraudAlert.setTimestamp(
                    LocalDateTime.now()
            );

            fraudAlertProducer
                    .sendFraudAlert(fraudAlert);

            log.warn(
                    "Fraud Alert Generated: {}",
                    fraudAlert
            );
        }
    }
}