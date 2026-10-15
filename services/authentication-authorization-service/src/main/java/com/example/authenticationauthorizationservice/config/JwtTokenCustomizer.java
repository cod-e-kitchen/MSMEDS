package com.example.authenticationauthorizationservice.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import com.example.authenticationauthorizationservice.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;

@Configuration
public class JwtTokenCustomizer {

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> accessTokenCustomizer() {
        return context -> {
            if(!OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
                return;
            }
            Authentication authentication = context.getPrincipal();
            Object principal = authentication.getPrincipal();
            if(principal instanceof CustomUserDetails user) {
                context.getClaims().claim("user_id",user.getUserId());
//                context.getClaims().claim("user_name",user.getUsername());
                context.getClaims().claim("entity_id",user.getEntityId());
                context.getClaims().claim("entity_type",user.getEntityType());
                context.getClaims().claim("role",user.getRole());
            }
        };
    }
}
