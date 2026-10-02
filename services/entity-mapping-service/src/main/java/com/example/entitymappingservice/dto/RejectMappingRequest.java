package com.example.entitymappingservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RejectMappingRequest {

    @NotBlank(message = "Rejection reason is mandatory.")
    private String rejectionReason;
}
