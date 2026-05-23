package com.sentinelx.fraud.service;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class IpReputationService {

    private final Set<String>
            blacklistedIps =
            Set.of(
                    "192.168.1.200",
                    "10.0.0.99"
            );

    public int checkIpRisk(
            String ipAddress
    ) {

        if (blacklistedIps.contains(
                ipAddress
        )) {

            return 40;
        }

        return 0;
    }
}