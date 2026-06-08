package com.sentinelx.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketAlertService {

    private final SimpMessagingTemplate
            messagingTemplate;

    public void sendFraudAlert(
            Object alert
    ) {

        messagingTemplate.convertAndSend(
                "/topic/fraud-alerts",
                alert
        );
    }
}