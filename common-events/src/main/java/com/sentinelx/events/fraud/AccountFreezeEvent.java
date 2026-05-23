package com.sentinelx.events.fraud;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountFreezeEvent {

    private String userId;

    private String reason;

    private Integer riskScore;
}