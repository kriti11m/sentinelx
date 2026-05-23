package com.sentinelx.fraud.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object>
    redisTemplate(
            RedisConnectionFactory connectionFactory
    ) {

        RedisTemplate<String, Object> template =
                new RedisTemplate<>();

        template.setConnectionFactory(
                connectionFactory
        );

        // =========================
        // KEY SERIALIZER
        // =========================

        template.setKeySerializer(
                new StringRedisSerializer()
        );

        template.setHashKeySerializer(
                new StringRedisSerializer()
        );

        // =========================
        // VALUE SERIALIZER
        // =========================

        template.setValueSerializer(
                new GenericJackson2JsonRedisSerializer()
        );

        template.setHashValueSerializer(
                new GenericJackson2JsonRedisSerializer()
        );

        template.afterPropertiesSet();

        return template;
    }
}