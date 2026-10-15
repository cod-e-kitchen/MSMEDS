package com.example.msmedscommonutil.security;

public record AuthenticatedUser (
        Long userId,
        Long entityId,
        String entityType,
        String role,
        String username
){
}
