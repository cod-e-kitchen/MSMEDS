## Authentication Service

Authentication of a user to provide a secure JWT token with claims. 

## Tech Stack

- Java 21
- OAuth 2
- JWT
- RSA 2048 encryption
- RSA Private & Public key
- Spring Boot
- Spring JDBC
- JdbcTemplate
- SQL Server
- Maven
- Lombok

## Architecture
                ┌──────────────────────┐
                │      Postman /       │
                │   OAuth2 Client      │
                └──────────┬───────────┘
                           │
                    /oauth2/authorize
                           │
                           ▼
    ┌──────────────────────────────────────────────────────┐
    │          Authentication / Authorization Service      │
    │                                                      │
    │                                                      │
    │  Spring Authorization Server                         │
    │          │                                           │
    │          ├── Login                                   │
    │          │      │                                    │
    │          │      ▼                                    │
    │          │ CustomUserDetailsService                  │
    │          │      │                                    │
    │          │      ▼                                    │
    │          │ AuthUserRepository                        │
    │          │      │                                    │
    │          │      ▼                                    │
    │          │ SQL Server                                │
    │          │  AUTH_USER                                │
    │          │  USER_ROLE_MAPPING                        │
    │          │  ENTITY_MASTER                            │
    │          │                                           │
    │          └── RSA Private Key                         │
    │                    │                                 │
    │                    ▼                                 │
    │                 JWT Access Token                     │
    └────────────────────────┬─────────────────────────────┘
                             │
                         Bearer JWT
                             │
                             ▼
                  ┌──────────────────────┐
                  │    Resource Server   │
                  └──────────────────────┘
## Endpoints

Uses OAuth2 in built endpoints to generate token.
