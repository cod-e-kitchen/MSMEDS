package com.example.entitymappingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendingMappingApprovalResponse {
    private Long requestId;
    private Long buyerEntityId;
    private Long sellerEntityId;
    private String status;
    private LocalDateTime reqeustedDate;
    private String requestedBy;
}
