package com.example.msmedscommonutil.security;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser {

    public AuthenticatedUser get() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found");
        }

        if (!(authentication.getPrincipal() instanceof Jwt jwt)) {
            throw new IllegalStateException(
                    "Authenticated principal is not a JWT"
            );
        }

        return new AuthenticatedUser(
                jwt.getClaim("user_id"),
                jwt.getClaim("entity_id"),
                jwt.getClaim("entity_type"),
                jwt.getClaim("role"),
                jwt.getSubject()
        );
    }
}
