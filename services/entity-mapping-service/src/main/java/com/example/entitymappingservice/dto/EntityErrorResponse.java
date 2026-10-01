package com.example.entitymappingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EntityErrorResponse {

    private LocalDateTime timestamp;
    private String message;
    private String error;
}
