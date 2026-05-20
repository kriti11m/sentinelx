package com.sentinelx.fraud.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ProcessedEventService {

    private final Set<String>
            processedTransactions =
            new HashSet<>();

    public boolean isProcessed(
            String transactionId
    ) {

        return processedTransactions
                .contains(transactionId);
    }

    public void markProcessed(
            String transactionId
    ) {

        processedTransactions
                .add(transactionId);
    }
}