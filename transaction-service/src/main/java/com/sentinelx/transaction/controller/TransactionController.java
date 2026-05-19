package com.sentinelx.transaction.controller;

import com.sentinelx.events.transaction.TransactionEvent;
import com.sentinelx.transaction.kafka.producer.TransactionProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionProducer producer;

    @PostMapping
    public String createTransaction(
            @RequestBody TransactionEvent event
    ) {

        producer.sendTransactionEvent(event);

        return "Transaction Event Published";
    }
}