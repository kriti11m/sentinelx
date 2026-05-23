package com.sentinelx.fraud.service;

import com.sentinelx.fraud.entity.FraudAlert;
import com.sentinelx.fraud.repository.FraudAlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FraudAlertService {

    private final FraudAlertRepository
            fraudAlertRepository;

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
                        .createdAt(
                                LocalDateTime.now()
                        )
                        .build();

        fraudAlertRepository.save(alert);
    }
}