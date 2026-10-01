package com.example.entitycreateservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EntityErrorResponse {
    private LocalDateTime timestamp;
    private String message;
    private String error;
}
