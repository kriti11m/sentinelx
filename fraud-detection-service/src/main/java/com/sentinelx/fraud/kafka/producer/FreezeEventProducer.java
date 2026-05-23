package com.sentinelx.fraud.kafka.producer;

import com.sentinelx.events.fraud.AccountFreezeEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FreezeEventProducer {

    private final KafkaTemplate<String, AccountFreezeEvent> kafkaTemplate;


    public void publishFreezeEvent(
            AccountFreezeEvent event
    ) {

        log.error(
                "Publishing Account Freeze Event: {}",
                event
        );

        kafkaTemplate.send(
                "account-freeze",
                event
        );
    }
}