package com.sentinelx.notification.kafka.consumer;

import com.sentinelx.events.fraud.FraudAlertEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FraudAlertConsumer {

    @KafkaListener(
            topics = "fraud-alert",
            groupId = "notification-group"
    )
    public void consume(
            FraudAlertEvent event
    ) {

        log.warn(
                "FRAUD ALERT RECEIVED: {}",
                event
        );

        log.warn(
                "Sending fraud warning notification..."
        );
    }
}