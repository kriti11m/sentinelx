package com.sentinelx.fraud.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "fraud_alerts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FraudAlert {

    @Id
    @GeneratedValue(strategy =
            GenerationType.IDENTITY)
    private Long id;

    private String transactionId;

    private String sender;

    private Double amount;

    private Integer riskScore;

    private String decision;

    private String reason;

    private String ipAddress;

    private String deviceId;

    private LocalDateTime createdAt;
}