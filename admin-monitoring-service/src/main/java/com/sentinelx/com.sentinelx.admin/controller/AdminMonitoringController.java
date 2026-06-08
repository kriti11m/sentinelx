package com.sentinelx.admin.controller;

import com.sentinelx.admin.entity.FraudAlertLog;
import com.sentinelx.admin.repository.FraudAlertLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminMonitoringController {

    private final FraudAlertLogRepository
            repository;

    @GetMapping("/alerts")
    public List<FraudAlertLog> getAlerts() {

        return repository.findAll();
    }
}