package com.example.authenticationauthorizationservice.security;

import com.example.authenticationauthorizationservice.dto.AuthUserDetailsDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final AuthUserDetailsDto user;

    // Used when Spring Security initially authenticates the user.
    public CustomUserDetails(AuthUserDetailsDto user) {
        this.user = user;
    }

    // Used by Jackson when restoring the principal from SQL Server.
    @JsonCreator
    public CustomUserDetails(
            @JsonProperty("userId") Long userId,
            @JsonProperty("username") String username,
            @JsonProperty("status") String status,
            @JsonProperty("entityId") Long entityId,
            @JsonProperty("role") String role,
            @JsonProperty("entityType") String entityType) {

        AuthUserDetailsDto restoredUser = new AuthUserDetailsDto();

        restoredUser.setUserId(userId);
        restoredUser.setUsername(username);
        restoredUser.setStatus(status == null ? "ACTIVE" : status);
        restoredUser.setEntityId(entityId);
        restoredUser.setRole(role);
        restoredUser.setEntityType(entityType);

        // This restored principal is not used to authenticate a password.
        // Do not restore or persist the password hash here.
        restoredUser.setPasswordHash(null);

        this.user = restoredUser;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority("ROLE_" + user.getRole())
        );
    }

    @Override
    @Nullable
    @JsonIgnore
    public String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return !"LOCKED".equals(user.getStatus());
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return "ACTIVE".equals(user.getStatus());
    }

    public Long getUserId() {
        return user.getUserId();
    }

    public Long getEntityId() {
        return user.getEntityId();
    }

    public String getRole() {
        return user.getRole();
    }

    public String getEntityType() {
        return user.getEntityType();
    }

    public String getStatus() {
        return user.getStatus();
    }
}