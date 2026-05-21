package com.sentinelx.fraud.service;

import org.springframework.stereotype.Service;

@Service
public class FraudDecisionService {

    public String decide(int riskScore) {

        if (riskScore <= 30) {

            return "ALLOW";
        }

        if (riskScore <= 60) {

            return "VERIFY";
        }

        if (riskScore <= 90) {

            return "HOLD";
        }

        return "FREEZE";
    }
}