package com.sentinelx.events.fraud;

import java.time.LocalDateTime;

public class FraudAlertEvent {

    private String transactionId;
    private String userId;
    private Double amount;
    private String reason;
    private Double riskScore;
    private LocalDateTime timestamp;

    public FraudAlertEvent() {
    }

    public FraudAlertEvent(
            String transactionId,
            String userId,
            Double amount,
            String reason,
            Double riskScore,
            LocalDateTime timestamp
    ) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.amount = amount;
        this.reason = reason;
        this.riskScore = riskScore;
        this.timestamp = timestamp;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Double getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Double riskScore) {
        this.riskScore = riskScore;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}