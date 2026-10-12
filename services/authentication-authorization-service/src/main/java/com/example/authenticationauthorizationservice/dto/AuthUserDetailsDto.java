package com.example.authenticationauthorizationservice.dto;

import lombok.Data;

@Data
public class AuthUserDetailsDto {

    private Long userId;
    private String username;
    private String passwordHash;
    private String status;
    private Long entityId;
    private String role;
    private String entityType;


}
