package com.sentinelx.fraud.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceFingerprintService {

    private final RedisTemplate<String, Object>
            redisTemplate;

    public int analyzeDevice(
            String deviceId
    ) {

        String key =
                "device:" + deviceId;

        Long count =
                redisTemplate.opsForValue()
                        .increment(key);

        if (count != null && count > 5) {

            return 25;
        }

        return 0;
    }
}