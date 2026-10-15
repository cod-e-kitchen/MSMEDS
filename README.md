# MSMEDS --- MSME Discounting System

A microservices-based backend system for managing MSME entities, users,
authentication and authorization, and buyer--seller relationships as the
foundation for an MSME discounting platform.

## Overview

MSMEDS is being developed as a modular microservices ecosystem. The
system separates authentication and authorization, entity management,
entity relationships, and shared cross-cutting functionality into
independently deployable services.

### Technology Stack

-   Java 21
-   Spring Boot
-   Spring Security
-   Spring Authorization Server
-   OAuth 2.0 / OpenID Connect
-   JWT
-   SQL Server
-   Spring JDBC (`JdbcTemplate` / `SimpleJdbcCall`)
-   Stored Procedures
-   Maven
-   REST APIs

## Architecture

``` text
                         Client
                    (Postman / UI)
                           |
                    OAuth 2.0 / OIDC
                           |
                           v
              +---------------------------+
              | Authentication &           |
              | Authorization Service      |
              |                           |
              | OAuth2 / OIDC             |
              | JWT issuance              |
              | RSA signing               |
              | User authentication       |
              +-------------+-------------+
                            |
                       JWT Access Token
                            |
          +-----------------+-----------------+
          |                 |                 |
          v                 v                 v
 +----------------+ +----------------+ +----------------+
 | Entity Create  | | Entity Mapping | | Future MSMEDS  |
 | Service        | | Service        | | Services       |
 |                | |                | |                |
 | Entity +       | | Buyer/Seller   | | Discounting    |
 | initial ADMIN  | | mapping        | | Invoice        |
 | user           | | workflow       | | Financing      |
 +-------+--------+ +-------+--------+ +----------------+
         |                  |
         +--------+---------+
                  v
          +---------------+
          |   SQL Server  |
          |               |
          | Entities      |
          | Users         |
          | Roles         |
          | OAuth2 data   |
          | Mappings      |
          | Stored Procs  |
          +---------------+

          +----------------------+
          |  msmeds-common-util  |
          |                      |
          | CurrentUser          |
          | AuthenticatedUser    |
          | Exceptions           |
          | Error responses      |
          +----------------------+
```

## Repository Structure

``` text
MSMEDS/
|
+-- services/
|   +-- authentication-authorization-service/
|   |   +-- README.md
|   |
|   +-- entity-create-service/
|   |   +-- README.md
|   |
|   +-- entity-mapping-service/
|       +-- README.md
|
+-- common/
|   +-- msmeds-common-util/
|       +-- README.md
|
+-- database/
|   +-- tables/
|   +-- stored-procedures/
|
+-- docs/
|
+-- README.md
```

## Core Domain

### Entity Types

-   `BUYER`
-   `SELLER`
-   `FINANCIER`

### User Roles

-   `MAKER`
-   `CHECKER`
-   `ADMIN`

`ADMIN` includes maker/checker capabilities plus administrative
capabilities such as user creation.

## Authentication and Authorization

The Authentication & Authorization Service acts as the OAuth 2.0 /
OpenID Connect Authorization Server.

Current design:

-   Authorization Code flow with PKCE for human users
-   OpenID Connect
-   JWT access tokens
-   RSA asymmetric signing
-   JWKS-based public-key validation by resource services
-   Custom MSMEDS JWT claims
-   JDBC persistence for OAuth2 authorization-server data

A typical access token contains claims such as:

``` json
{
  "sub": "username",
  "user_id": 100,
  "entity_id": 10000017,
  "entity_type": "BUYER",
  "role": "ADMIN",
  "scope": [
    "mapping.read",
    "mapping.write"
  ],
  "iss": "http://localhost:9000"
}
```

Resource services use these claims to establish the authenticated user's
identity and make authorization decisions.

## Shared Common Utility

`msmeds-common-util` is a reusable Maven library intended to eliminate
duplication of cross-cutting functionality across MSMEDS services.

Current security abstractions:

``` text
security/
+-- AuthenticatedUser
+-- CurrentUser
```

The intended flow is:

``` text
JWT
 |
 v
CurrentUser
 |
 v
AuthenticatedUser
 |
 v
Business Logic
```

This prevents individual microservices from duplicating JWT extraction
and authenticated-user handling.

The common library is also intended to contain shared exception and
error-response infrastructure while keeping domain-specific business
logic inside individual services.

## Entity Mapping

The Entity Mapping Service currently supports the buyer--seller mapping
workflow.

``` text
BUYER
  |
  | Raise mapping request
  v
Mapping Request
  |
  | Pending
  v
SELLER
  |
  +-- Approve
  |
  +-- Reject
  v
Mapping Result
```

Authenticated entity identity is derived from the JWT rather than
trusted from client-supplied entity identifiers.

For a buyer raising a request:

``` text
JWT
 +-- entity_id = authenticated buyer

Request
 +-- seller_entity_id = requested seller

Server
 +-- buyer_entity_id = JWT entity_id
 +-- seller_entity_id = request value
```

This prevents a client from simply claiming to represent another entity.

## Database

SQL Server is the current persistence layer.

Database responsibilities include:

-   Entity master data
-   Authentication users
-   User-role mappings
-   OAuth2 authorization-server persistence
-   Entity mapping data
-   Stored procedures for transactional/business operations

Java services interact with stored procedures using Spring JDBC
facilities such as `SimpleJdbcCall` and `JdbcTemplate`.

## Security Model

A protected request follows this general flow:

``` text
Client
  |
  | Authorization: Bearer <JWT>
  v
Resource Service
  |
  +-- JWT signature validation
  +-- Issuer validation
  +-- Expiration validation
  +-- Scope validation
  +-- Role validation
  +-- Entity-type/business authorization
  |
  v
Controller
  |
  v
Service
  |
  v
Repository
  |
  v
Stored Procedure
```

The security model separates three concepts:

  -----------------------------------------------------------------------
  Dimension                           Purpose
  ----------------------------------- -----------------------------------
  Authentication                      Who is the user?

  Scope                               Can the client use this API
                                      capability?

  Role / Entity Type                  Is this user/entity allowed to
                                      perform this business operation?
  -----------------------------------------------------------------------

## Error Handling

The intended error flow is:

``` text
SQL Server
    |
    | database error code
    v
Repository
    |
    | application exception
    v
Service
    |
    v
Controller Advice
    |
    v
Consistent REST Error Response
```

Common cross-cutting exceptions and error-response structures can be
shared through `msmeds-common-util`, while domain-specific business
exceptions remain inside their owning service.

## Development Principles

1.  Never trust entity identity supplied by a client when it can be
    derived from the authenticated JWT.
2.  Authenticate users through the Authorization Server.
3.  Validate JWTs in resource services.
4.  Use scopes for API-level permissions.
5.  Use roles and entity types for business authorization.
6.  Keep authentication credentials and secrets outside source control.
7.  Use asymmetric JWT signing so resource services do not need the
    Authorization Server's private key.
8.  Keep domain-specific business rules inside the service that owns the
    domain.
9.  Extract genuinely cross-cutting functionality into
    `msmeds-common-util`.
10. Keep database operations transactional and encapsulated through
    stored procedures where appropriate.

## Local Development

### Prerequisites

-   Java 21
-   Maven 3.9+
-   SQL Server
-   IntelliJ IDEA or another Java IDE
-   Postman

### Build a Service

``` powershell
mvn clean compile
```

### Package a Service

``` powershell
mvn clean package
```

### Build and Install the Common Library

From `msmeds-common-util`:

``` powershell
mvn clean install
```

This installs the shared library into the local Maven repository so
consuming services can resolve it as a dependency.

### Run a Spring Boot Service

``` powershell
mvn spring-boot:run
```

Service-specific configuration and testing instructions are documented
in each service's README.

## Local Service Ports

Current local development uses:

  Component                                                       Port
  ---------------------------------------- ---------------------------
  Authentication & Authorization Service                        `9000`
  Entity Mapping Service                                        `8081`
  Entity Create Service                      See service configuration

These are local development values and may change as the project
evolves.

## Current Development Status

-   [x] SQL Server database foundation
-   [x] Entity creation workflow
-   [x] Initial ADMIN user creation
-   [x] Authentication user model
-   [x] User-role mapping
-   [x] OAuth2 / OIDC Authorization Server
-   [x] JWT access tokens
-   [x] Custom MSMEDS JWT claims
-   [x] JWKS endpoint
-   [x] Resource-server JWT validation
-   [x] Shared `msmeds-common-util` library
-   [x] Shared `CurrentUser` / `AuthenticatedUser`
-   [x] Buyer--seller mapping workflow
-   [x] Authenticated entity identity in mapping operations
-   [x] Buyer/seller entity-type authorization
-   [ ] Complete role + scope authorization matrix
-   [ ] Production-grade RSA key persistence/rotation
-   [ ] User administration workflow
-   [ ] Additional MSME discounting workflows
-   [ ] Production deployment architecture
-   [ ] Automated integration/end-to-end test suite

## Design Direction

The project is being developed incrementally around the following
foundation:

``` text
Identity
   |
   v
Authentication
   |
   v
Authorization
   |
   v
Entity-aware Business Operations
   |
   v
Transactional Database Operations
   |
   v
MSME Discounting Workflows
```

As new services are introduced, shared infrastructure should be
extracted into `msmeds-common-util` only when it is genuinely
cross-cutting. Business/domain logic should remain inside the service
that owns the domain.

## Documentation

Each service contains its own README with service-specific information,
including:

-   Responsibilities
-   API endpoints
-   Configuration
-   Database procedures
-   Security requirements
-   Request/response examples
-   Local testing instructions

Use this root README for the overall system architecture and individual
service READMEs for implementation-specific details.

## Repository Safety

Never commit:

-   Database passwords
-   OAuth client secrets
-   JWT access or refresh tokens
-   Private RSA keys
-   API keys
-   Local environment files containing secrets

Use environment variables or an appropriate secret-management mechanism
for sensitive configuration.

## License

This project is currently under private development. Licensing
information can be added when the repository's distribution model is
finalized.
