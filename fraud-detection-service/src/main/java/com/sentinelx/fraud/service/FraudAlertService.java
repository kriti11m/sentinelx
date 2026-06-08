package com.sentinelx.fraud.service;

import com.sentinelx.events.fraud.FraudAlertEvent;
import com.sentinelx.fraud.entity.FraudAlert;
import com.sentinelx.fraud.kafka.producer.FraudAlertProducer;
import com.sentinelx.fraud.repository.FraudAlertRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class FraudAlertService {

    private final FraudAlertRepository fraudAlertRepository;

    private final FraudAlertProducer fraudAlertProducer;

    public void saveFraudAlert(
            String transactionId,
            String sender,
            Double amount,
            Integer riskScore,
            String decision,
            String reason,
            String ipAddress,
            String deviceId
    ) {

        FraudAlert alert =
                FraudAlert.builder()
                        .transactionId(transactionId)
                        .sender(sender)
                        .amount(amount)
                        .riskScore(riskScore)
                        .decision(decision)
                        .reason(reason)
                        .ipAddress(ipAddress)
                        .deviceId(deviceId)
                        .createdAt(LocalDateTime.now())
                        .build();

        fraudAlertRepository.save(alert);

        FraudAlertEvent event =
                new FraudAlertEvent(
                        transactionId,
                        sender,
                        amount,
                        reason,
                        riskScore.doubleValue(),
                        LocalDateTime.now()
                );

        fraudAlertProducer.sendFraudAlert(event);

        log.warn(
                "Fraud Alert Published To Kafka : {}",
                transactionId
        );
    }
}