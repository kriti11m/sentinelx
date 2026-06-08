package com.sentinelx.admin.kafka;

import com.sentinelx.admin.entity.FraudAlertLog;
import com.sentinelx.admin.repository.FraudAlertLogRepository;
import com.sentinelx.admin.service.WebSocketAlertService;
import com.sentinelx.events.fraud.FraudAlertEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class FraudAlertConsumer {

    private final FraudAlertLogRepository repository;

    private final WebSocketAlertService webSocketAlertService;

    @KafkaListener(
            topics = "fraud-alert",
            groupId = "admin-monitor-group"
    )
    public void consume(FraudAlertEvent event) {

        log.warn(
                "ADMIN ALERT RECEIVED : {}",
                event
        );

        String riskLevel;

        if (event.getRiskScore() >= 80) {
            riskLevel = "HIGH";
        } else if (event.getRiskScore() >= 50) {
            riskLevel = "MEDIUM";
        } else {
            riskLevel = "LOW";
        }

        FraudAlertLog logEntity =
                FraudAlertLog.builder()
                        .transactionId(event.getTransactionId())
                        .amount(event.getAmount())
                        .riskScore(event.getRiskScore())
                        .riskLevel(riskLevel)
                        .reason(event.getReason())
                        .createdAt(LocalDateTime.now())
                        .build();

        repository.save(logEntity);

        webSocketAlertService.sendFraudAlert(event);

        log.info(
                "Fraud alert stored successfully for transaction {}",
                event.getTransactionId()
        );
    }
}