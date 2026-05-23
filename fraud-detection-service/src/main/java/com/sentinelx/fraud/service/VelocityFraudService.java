package com.sentinelx.fraud.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class VelocityFraudService {

    private final RedisTemplate<String, Object>
            redisTemplate;

    public int checkTransactionVelocity(
            String userId
    ) {

        // ================================
        // REDIS KEY
        // ================================

        String key =
                "txn_count:" + userId;

        // ================================
        // INCREMENT TRANSACTION COUNT
        // ================================

        Long count =
                redisTemplate.opsForValue()
                        .increment(key);

        // ================================
        // SET EXPIRY WINDOW
        // ================================

        redisTemplate.expire(
                key,
                1,
                TimeUnit.MINUTES
        );

        // ================================
        // LOG CURRENT COUNT
        // ================================

        log.info(
                "Transaction Count for {} : {}",
                userId,
                count
        );

        // ================================
        // VELOCITY FRAUD RULE
        // ================================

        if (count != null && count > 3) {

            log.warn(
                    "Velocity Fraud Triggered for {}",
                    userId
            );

            return 30;
        }

        return 0;
    }
}