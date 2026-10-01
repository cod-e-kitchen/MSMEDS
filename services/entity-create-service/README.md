# Project - MSMEDS

MSMEDS - Micro, Small, Medium Enterprise Discounting System.
This project will help MSME to be able to do faster settlement and grow their supply chain.

# Entity Create Service

Onboarding an entity to MSMEDS. Entity can be Buyer, Seller or Financier.
Spring Boot REST API for creating entities using a SQL Server
stored procedure.

## Tech Stack

- Java 21
- Spring Boot
- Spring JDBC
- JdbcTemplate
- SimpleJdbcCall
- SQL Server
- Maven
- Lombok

## Architecture

Controller
↓
Service
↓
Repository
↓
SimpleJdbcCall
↓
SQL Server Stored Procedure

## API

POST /entity/create

## Request

{
"entity_name": "Tata Financial Services",
"entity_location_city": "Mumbai",
"entity_location_state": "Maharashtra",
"entity_location_country": "India",
"entity_type": "FINANCIER"
}

## Stored Procedure

dbo.sp_create_entity

## Error Handling

- ENTITY_NAME_REQUIRED
- ENTITY_CITY_REQUIRED
- ENTITY_STATE_REQUIRED
- ENTITY_COUNTRY_REQUIRED
- INVALID_ENTITY_TYPE
- CREATED_BY_REQUIRED
- ENTITY_ALREADY_EXISTS