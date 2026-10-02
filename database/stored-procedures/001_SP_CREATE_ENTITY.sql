USE [MSMEDS]
GO

/****** Object:  StoredProcedure [dbo].[SP_CREATE_ENTITY]    Script Date: 10/2/2026 6:11:44 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

-----------------------------------------------------------------------------
/*
Written - Sujeet Pawar
Execution script:-

EXEC sp_create_entity
    @entity_name = 'ABC Enterprises',
    @entity_location_city = 'Mumbai',
    @entity_location_state = 'Maharashtra',
    @entity_location_country = 'India',
    @entity_type = 'BUYER',
    @created_by = 'sujeet';

--Test error execution script--
EXEC sp_create_entity 
    @entity_name = NULL, 
    @entity_location_city = 'Mumbai', 
    @entity_location_state = 'Maharashtra', 
    @entity_location_country = 'India', 
    @entity_type = 'BUYER', 
    @created_by = 'sujeet';


*/


-----------------------------------------------------------------------------


CREATE   PROCEDURE [dbo].[SP_CREATE_ENTITY]
(
    @entity_name NVARCHAR(200),
    @entity_location_city NVARCHAR(100),
    @entity_location_state NVARCHAR(100),
    @entity_location_country NVARCHAR(100),
    @entity_type VARCHAR(20),
    @created_by VARCHAR(100)
)
AS
BEGIN

    SET NOCOUNT ON;

    ------------------------------------------------
    -- ENTITY NAME VALIDATION
    ------------------------------------------------

    IF @entity_name IS NULL
        THROW 50001, 'ENTITY_NAME_REQUIRED', 1;

    IF LEN(LTRIM(RTRIM(@entity_name))) = 0
        THROW 50001, 'ENTITY_NAME_REQUIRED', 1;


    ------------------------------------------------
    -- CITY VALIDATION
    ------------------------------------------------

    IF @entity_location_city IS NULL
        THROW 50002, 'ENTITY_CITY_REQUIRED', 1;

    IF LEN(LTRIM(RTRIM(@entity_location_city))) = 0
        THROW 50002, 'ENTITY_CITY_REQUIRED', 1;


    ------------------------------------------------
    -- STATE VALIDATION
    ------------------------------------------------

    IF @entity_location_state IS NULL
        THROW 50003, 'ENTITY_STATE_REQUIRED', 1;

    IF LEN(LTRIM(RTRIM(@entity_location_state))) = 0
        THROW 50003, 'ENTITY_STATE_REQUIRED', 1;


    ------------------------------------------------
    -- COUNTRY VALIDATION
    ------------------------------------------------

    IF @entity_location_country IS NULL
        THROW 50004, 'ENTITY_COUNTRY_REQUIRED', 1;

    IF LEN(LTRIM(RTRIM(@entity_location_country))) = 0
        THROW 50004, 'ENTITY_COUNTRY_REQUIRED', 1;


    ------------------------------------------------
    -- ENTITY TYPE VALIDATION
    ------------------------------------------------

    IF @entity_type IS NULL
        THROW 50005, 'INVALID_ENTITY_TYPE', 1;

    IF @entity_type NOT IN
    (
        'BUYER',
        'SELLER',
        'FINANCIER'
    )
        THROW 50005, 'INVALID_ENTITY_TYPE', 1;


    ------------------------------------------------
    -- CREATED BY VALIDATION
    ------------------------------------------------

    IF @created_by IS NULL
        THROW 50006, 'CREATED_BY_REQUIRED', 1;

    IF LEN(LTRIM(RTRIM(@created_by))) = 0
        THROW 50006, 'CREATED_BY_REQUIRED', 1;


    ------------------------------------------------
    -- DUPLICATE VALIDATION
    ------------------------------------------------

    IF EXISTS
    (
        SELECT 1
        FROM ENTITY_MASTER
        WHERE entity_name = @entity_name
          AND entity_type = @entity_type
          AND city = @entity_location_city
          AND state = @entity_location_state
          AND country = @entity_location_country
    )
        THROW 50017, 'ENTITY_ALREADY_EXISTS', 1;


    ------------------------------------------------
    -- INSERT
    ------------------------------------------------

    INSERT INTO ENTITY_MASTER
    (
        entity_name,
        city,
        state,
        country,
        entity_type,
        status,
        created_at,
        created_by
    )
    VALUES
    (
        LTRIM(RTRIM(@entity_name)),
        LTRIM(RTRIM(@entity_location_city)),
        LTRIM(RTRIM(@entity_location_state)),
        LTRIM(RTRIM(@entity_location_country)),
        UPPER(LTRIM(RTRIM(@entity_type))),
        'ACTIVE',
        SYSUTCDATETIME(),
        LTRIM(RTRIM(@created_by))
    );


    ------------------------------------------------
    -- RETURN CREATED ENTITY
    ------------------------------------------------

    SELECT
        entity_id,
        entity_name,
        city,
        state,
        country,
        entity_type,
        status,
        created_at,
        created_by
    FROM ENTITY_MASTER
    WHERE entity_id = SCOPE_IDENTITY();

END;
GO


