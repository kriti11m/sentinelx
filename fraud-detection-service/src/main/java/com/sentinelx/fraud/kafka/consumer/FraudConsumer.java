package com.sentinelx.fraud.kafka.consumer;

import com.sentinelx.events.transaction.TransactionEvent;
import com.sentinelx.fraud.service.FraudDecisionService;
import com.sentinelx.fraud.service.FraudRuleService;
import com.sentinelx.fraud.service.ProcessedEventService;
import com.sentinelx.fraud.service.VelocityFraudService;
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
            // FRAUD RULE ENGINE
            // =====================================

            int riskScore =
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
            // FRAUD DECISION ENGINE
            // =====================================

            String decision =
                    fraudDecisionService
                            .decide(riskScore);

            // =====================================
            // LOG FINAL RESULTS
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
            // FAILURE SIMULATION
            // =====================================

            if (riskScore >= 90) {

                log.error(
                        "CRITICAL FRAUD DETECTED - ACCOUNT SHOULD BE FROZEN"
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