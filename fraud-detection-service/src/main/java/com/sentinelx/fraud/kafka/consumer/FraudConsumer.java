package com.sentinelx.fraud.kafka.consumer;

import com.sentinelx.events.fraud.AccountFreezeEvent;
import com.sentinelx.events.transaction.TransactionEvent;
import com.sentinelx.fraud.kafka.producer.FreezeEventProducer;
import com.sentinelx.fraud.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FraudConsumer {

    private final FraudRuleService
            fraudRuleService;

    private final VelocityFraudService
            velocityFraudService;

    private final FraudDecisionService
            fraudDecisionService;

    private final ProcessedEventService
            processedEventService;

    private final DeviceFingerprintService
            deviceFingerprintService;

    private final IpReputationService
            ipReputationService;

    private final FraudAlertService
            fraudAlertService;

    private final FreezeEventProducer
            freezeEventProducer;

    @KafkaListener(
            topics = "transaction-created",
            groupId = "fraud-group",
            containerFactory =
                    "kafkaListenerContainerFactory"
    )
    public void consume(
            TransactionEvent event
    ) {

        log.info(
                "Fraud Service Received Event: {}",
                event
        );

        // =====================================
        // IDEMPOTENCY CHECK
        // =====================================

        if (processedEventService.isProcessed(
                event.getTransactionId()
        )) {

            log.warn(
                    "Duplicate Transaction Ignored: {}",
                    event.getTransactionId()
            );

            return;
        }

        try {

            // =====================================
            // INITIAL RISK SCORE
            // =====================================

            int riskScore = 0;

            // =====================================
            // RULE BASED FRAUD CHECKS
            // =====================================

            riskScore +=
                    fraudRuleService
                            .calculateRiskScore(event);

            // =====================================
            // VELOCITY FRAUD CHECK
            // =====================================

            riskScore +=
                    velocityFraudService
                            .checkTransactionVelocity(
                                    event.getSender()
                            );

            // =====================================
            // DEVICE FINGERPRINT ANALYSIS
            // =====================================

            riskScore +=
                    deviceFingerprintService
                            .analyzeDevice(
                                    event.getDeviceId()
                            );

            // =====================================
            // IP REPUTATION ANALYSIS
            // =====================================

            riskScore +=
                    ipReputationService
                            .checkIpRisk(
                                    event.getIpAddress()
                            );

            // =====================================
            // FINAL DECISION
            // =====================================

            String decision =
                    fraudDecisionService
                            .decide(riskScore);

            // =====================================
            // LOGGING
            // =====================================

            log.warn(
                    "FINAL RISK SCORE: {}",
                    riskScore
            );

            log.warn(
                    "FRAUD DECISION: {}",
                    decision
            );

            // =====================================
            // SAVE FRAUD ALERT
            // =====================================

            fraudAlertService.saveFraudAlert(
                    event.getTransactionId(),
                    event.getSender(),
                    event.getAmount(),
                    riskScore,
                    decision,
                    "Suspicious Transaction",
                    event.getIpAddress(),
                    event.getDeviceId()
            );

            // =====================================
            // FREEZE ACCOUNT LOGIC
            // =====================================

            if ("FREEZE".equals(decision)) {

                log.error(
                        "CRITICAL FRAUD DETECTED - FREEZING ACCOUNT"
                );

                AccountFreezeEvent freezeEvent =
                        AccountFreezeEvent.builder()
                                .userId(
                                        event.getSender()
                                )
                                .reason(
                                        "High Fraud Risk"
                                )
                                .riskScore(
                                        riskScore
                                )
                                .build();

                freezeEventProducer
                        .publishFreezeEvent(
                                freezeEvent
                        );
            }

            // =====================================
            // MARK EVENT AS PROCESSED
            // =====================================

            processedEventService.markProcessed(
                    event.getTransactionId()
            );

            log.info(
                    "Transaction Processed Successfully"
            );

        } catch (Exception ex) {

            log.error(
                    "Error Processing Transaction: {}",
                    event.getTransactionId(),
                    ex
            );

            throw ex;
        }
    }
}