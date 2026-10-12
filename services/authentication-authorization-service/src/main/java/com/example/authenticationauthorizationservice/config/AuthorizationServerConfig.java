package com.example.authenticationauthorizationservice.config;

import com.example.authenticationauthorizationservice.security.CustomUserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.jackson.SecurityJacksonModules;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;

@Configuration
public class AuthorizationServerConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(
            HttpSecurity http,
            RegisteredClientRepository registeredClientRepository,
            OAuth2AuthorizationService authorizationService,
            OAuth2AuthorizationConsentService authorizationConsentService)
            throws Exception {


            http
                    .oauth2AuthorizationServer(authorizationServer ->
                            authorizationServer
                                    .registeredClientRepository(registeredClientRepository)
                                    .authorizationService(authorizationService)
                                    .authorizationConsentService(authorizationConsentService)
                                    .oidc(Customizer.withDefaults())
                    )
                    .authorizeHttpRequests(authorize ->
                            authorize
                                    .anyRequest().authenticated()
                    )
                    .formLogin(Customizer.withDefaults());

            return http.build();

    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }

    @Bean
    public OAuth2AuthorizationService authorizationService(
            JdbcTemplate jdbcTemplate,
            RegisteredClientRepository registeredClientRepository) {

        JdbcOAuth2AuthorizationService authorizationService =
                new JdbcOAuth2AuthorizationService(
                        jdbcTemplate,
                        registeredClientRepository
                );

        ClassLoader classLoader = getClass().getClassLoader();

        BasicPolymorphicTypeValidator.Builder validator =
                BasicPolymorphicTypeValidator.builder()
                        .allowIfSubType(CustomUserDetails.class);

        JsonMapper jsonMapper = JsonMapper.builder()
                .addModules(
                        SecurityJacksonModules.getModules(
                                classLoader,
                                validator
                        )
                )
                .build();

        authorizationService.setAuthorizationRowMapper(
                new JdbcOAuth2AuthorizationService
                        .JsonMapperOAuth2AuthorizationRowMapper(
                        registeredClientRepository,
                        jsonMapper
                )
        );

        return authorizationService;
    }

    @Bean
    public OAuth2AuthorizationConsentService authorizationConsentService
            (JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                .issuer("http://localhost:9000")
                .build();
    }

}
