package com.example.authenticationauthorizationservice.config;


import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.UUID;

@Configuration
public class OAuthClientConfig {

    @Bean
    public ApplicationRunner registerPostmanClient(
            RegisteredClientRepository registeredClientRepository) {

        return args -> {

            String clientId = "postman";

            RegisteredClient existingClient =
                    registeredClientRepository.findByClientId(clientId);

            if (existingClient == null) {

                RegisteredClient postmanClient =
                        RegisteredClient.withId(UUID.randomUUID().toString())
                                .clientId(clientId)
                                .clientName("Postman")
                                .clientAuthenticationMethod(
                                        ClientAuthenticationMethod.NONE
                                )
                                .authorizationGrantType(
                                        AuthorizationGrantType.AUTHORIZATION_CODE
                                )
                                .authorizationGrantType(
                                        AuthorizationGrantType.REFRESH_TOKEN
                                )
                                .redirectUri(
                                        "https://oauth.pstmn.io/v1/browser-callback"
                                )
                                .scope("openid")
                                .scope("profile")
                                .scope("mapping.read")
                                .scope("mapping.write")
                                .clientSettings(
                                        ClientSettings.builder()
                                                .requireProofKey(true)
                                                .requireAuthorizationConsent(true)
                                                .build()
                                )
                                .build();

                registeredClientRepository.save(postmanClient);
            }
        };
    }
}
