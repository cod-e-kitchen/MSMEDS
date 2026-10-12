package com.example.authenticationauthorizationservice.security;

import com.example.authenticationauthorizationservice.dto.AuthUserDetailsDto;
import com.example.authenticationauthorizationservice.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {



    private final AuthUserRepository authUserRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("Loading UserDetails for {}", username);
        AuthUserDetailsDto user = authUserRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found with username: "+username));

        log.info("Found UserDetails for {}", username);
        return new CustomUserDetails(user);
    }
}

