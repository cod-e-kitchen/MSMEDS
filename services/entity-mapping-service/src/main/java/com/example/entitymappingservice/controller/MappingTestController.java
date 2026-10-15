package com.example.entitymappingservice.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/mappings")
public class MappingTestController {

    @GetMapping("/whoami")
    public Map<String,Object> whoami(@AuthenticationPrincipal Jwt jwt){
        return Map.of(
                "username", jwt.getSubject(),
                "userId", jwt.getClaim("user_id"),
                "entityId", jwt.getClaim("entity_id"),
                "entityType", jwt.getClaim("entity_type"),
                "role", jwt.getClaim("role"),
                "scope", jwt.getClaimAsStringList("scope")
        );
    }

    /*TEST ENDPOINT - REMOVE LATER*/
    @GetMapping("/test")
    public String test(){
        return "mapping service working properly";
    }

    @GetMapping
    public String getMappings() {
        return "GET mapping - READ allowed";
    }

    @PostMapping
    public String createMapping() {
        return "POST mapping - WRITE allowed";
    }

    @GetMapping("/admin-check")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminCheck() {
        return "ADMIN access granted";
    }

    @GetMapping("/maker-check")
    @PreAuthorize("hasRole('MAKER')")
    public String makerCheck() {
        return "MAKER access granted";
    }

    @GetMapping("/checker-check")
    @PreAuthorize("hasRole('CHECKER')")
    public String checkerCheck() {
        return "CHECKER access granted";
    }
}
