package com.sentinelx.fraud.repository;

import com.sentinelx.fraud.entity.FraudAlert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FraudAlertRepository
        extends JpaRepository<
        FraudAlert,
        Long
        > {
}