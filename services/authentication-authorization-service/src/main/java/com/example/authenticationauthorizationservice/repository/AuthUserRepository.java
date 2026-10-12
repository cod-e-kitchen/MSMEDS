package com.example.authenticationauthorizationservice.repository;

import com.example.authenticationauthorizationservice.dto.AuthUserDetailsDto;
import com.example.authenticationauthorizationservice.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AuthUserRepository {

    private final JdbcTemplate jdbcTemplate;

    public Optional<AuthUserDetailsDto> findByUsername(String username) {

        log.info("AuthUserRepository - Finding UserDetails for {}", username);

        String sql = """
                SELECT
                    u.USER_ID,
                    u.USERNAME,
                    u.PASSWORD_HASH,
                    u.STATUS,
                    urm.ENTITY_ID,
                    urm.ROLE,
                    em.ENTITY_TYPE
                FROM dbo.AUTH_USER u
                INNER JOIN dbo.USER_ROLE_MAPPING urm
                    ON u.USER_ID = urm.USER_ID
                INNER JOIN dbo.ENTITY_MASTER em
                    ON urm.ENTITY_ID = em.ENTITY_ID
                WHERE u.USERNAME = ?
                """;
        try {

            List<AuthUserDetailsDto> users = jdbcTemplate.query
                    (sql, new BeanPropertyRowMapper<>(AuthUserDetailsDto.class), username);

        log.info("AuthUserRepository - Found UserDetails {}", users.stream().findFirst().orElse(null));
        return users.stream().findFirst();
        }catch (UserNotFoundException ex){
            throw new UserNotFoundException("User not found");
        }
    }
}
