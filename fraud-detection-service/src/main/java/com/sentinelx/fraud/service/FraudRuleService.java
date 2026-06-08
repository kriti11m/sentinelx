package com.sentinelx.fraud.service;

import com.sentinelx.events.transaction.TransactionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FraudRuleService {

    public int calculateRiskScore(
            TransactionEvent event
    ) {

        int riskScore = 0;

        if (event.getAmount() > 50000) {

            riskScore += 35;

            log.warn(
                    "High Amount Rule Triggered"
            );
        }

        if(event.getTimestamp() == null){
            return 0;
        }

        int hour =
                event.getTimestamp().getHour();

        if (hour >= 0 && hour <= 5) {

            riskScore += 20;

            log.warn(
                    "Midnight Transaction Rule Triggered"
            );
        }

        return riskScore;
    }
}