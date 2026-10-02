# MSMEDS Database

Database: MSMEDS
Database Engine: SQL Server

## Structure

### Tables

1. ENTITY_MASTER
2. BUYER_SELLER_MAPPING
3. BUYER_SELLER_MAPPING_REQUEST

### Stored Procedures

1. sp_create_entity
2. sp_create_mapping_request
3. sp_get_pending_mapping_requests
4. sp_approve_mapping_request
5. sp_reject_mapping_request

## Execution Order

### Tables

1. 001_ENTITY_MASTER.sql
2. 002_BUYER_SELLER_MAPPING.sql
3. 003_BUYER_SELLER_MAPPING_REQUEST.sql

### Stored Procedures

1. 001_SP_CREATE_ENTITY.sql
2. 002_SP_CREATE_MAPPING_REQUEST.sql
3. 003_SP_GET_PENDING_MAPPING_REQUESTS.sql
4. 004_SP_APPROVE_MAPPING_REQUEST.sql
5. 005_SP_REJECT_MAPPING_REQUEST.sql