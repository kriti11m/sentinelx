package com.sentinelx.fraud.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class VelocityFraudService {

    private final RedisTemplate<String, Object>
            redisTemplate;

    public int checkTransactionVelocity(
            String userId
    ) {

        String key =
                "txn_count:" + userId;

        Long count =
                redisTemplate.opsForValue()
                        .increment(key);

        redisTemplate.expire(
                key,
                1,
                TimeUnit.MINUTES
        );

        if (count != null && count > 5) {

            return 30;
        }

        return 0;
    }
}