## Buyer Seller Mapping Service

Service to raise, approve, reject, a mapping request.

While there can be 3 entities namely BUYER SELLER AND FINANCIER. Following points are to be noted:-
1. Buyer can raise a request for mapping against any onboarded Seller Entity.
   a. Endpoint - /mappings/raise-request
2. Seller can either approve the mapping request or reject it.
   a. [POST] Approval Endpoint - /mappings/seller-approve-mapping
   b. [POST] Reject Endpoint - /mappings/seller-reject-mapping
3. Seller can view the list of PENDING-FOR-APPROVAL request.
   a. [GET] Endpoint - /mappings/pending-approvals
4. Without a valid mapping between Buyer and Seller Entity, transactions cannot be raised on the discounting platform.


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

## API - Raise Mapping Request (By Buyer)

POST /mappings/raise-request

## Request

{
"buyerEntityId": "10000011",
"sellerEntityId": "10000010"
}

## Stored Procedures

dbo.SP_CREATE_MAPPING_REQUEST

## Error Handling

- ENTITY_IS_NOT_BUYER
- SELLER_ENTITY_DOES_NOT_EXIST
- ENTITY_IS_NOT_SELLER
- SAME_ENTITY_ID_PROVIDED
- ENTITIES_ALREADY_MAPPED
- MAPPING_REQUEST_ALREADY_EXISTS


## API 2 - View Pending for Approval Requests (By Seller)

GET /mappings/pending-approvals

## Stored Procedures

dbo.SP_GET_PENDING_MAPPING_REQUESTS

## Error Handling

- INVALID_SELLER_ENTITY_ID