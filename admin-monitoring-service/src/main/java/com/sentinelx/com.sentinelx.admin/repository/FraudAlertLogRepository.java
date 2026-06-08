package com.sentinelx.admin.repository;

import com.sentinelx.admin.entity.FraudAlertLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FraudAlertLogRepository
        extends JpaRepository<
        FraudAlertLog,
        Long
        > {
}