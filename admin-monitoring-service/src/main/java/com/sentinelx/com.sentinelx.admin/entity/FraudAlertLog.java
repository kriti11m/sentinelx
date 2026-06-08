package com.sentinelx.admin.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "fraud_alert_logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FraudAlertLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionId;

    private Double amount;

    private Double riskScore;

    private String riskLevel;

    private String reason;

    private LocalDateTime createdAt;
}