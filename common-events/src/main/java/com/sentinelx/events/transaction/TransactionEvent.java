package com.sentinelx.events.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEvent {

    private String transactionId;

    private String sender;

    private String receiver;

    private Double amount;

    private String status;

    private String deviceId;

    private String ipAddress;

    @JsonFormat(
            pattern = "yyyy-MM-dd'T'HH:mm:ss"
    )
    private LocalDateTime timestamp;
}