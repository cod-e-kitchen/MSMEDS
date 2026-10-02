USE [MSMEDS]
GO

/****** Object:  StoredProcedure [dbo].[SP_CREATE_MAPPING_REQUEST]    Script Date: 10/2/2026 6:13:37 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE   PROCEDURE [dbo].[SP_CREATE_MAPPING_REQUEST]
(
    @buyer_entity_id BIGINT,
    @seller_entity_id BIGINT,
    @created_by VARCHAR(100)
)
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY

        -- 1. Validate buyer exists
        IF NOT EXISTS
        (
            SELECT 1
            FROM dbo.ENTITY_MASTER
            WHERE ENTITY_ID = @buyer_entity_id
        )
        BEGIN
            THROW 50001, 'Buyer entity does not exist', 1;
        END;


        -- 2. Validate buyer type
        IF NOT EXISTS
        (
            SELECT 1
            FROM dbo.ENTITY_MASTER
            WHERE ENTITY_ID = @buyer_entity_id
              AND ENTITY_TYPE = 'BUYER'
        )
        BEGIN
            THROW 50002, 'Entity is not a buyer', 1;
        END;


        -- 3. Validate seller exists
        IF NOT EXISTS
        (
            SELECT 1
            FROM dbo.ENTITY_MASTER
            WHERE ENTITY_ID = @seller_entity_id
        )
        BEGIN
            THROW 50003, 'Seller entity does not exist', 1;
        END;


        -- 4. Validate seller type
        IF NOT EXISTS
        (
            SELECT 1
            FROM dbo.ENTITY_MASTER
            WHERE ENTITY_ID = @seller_entity_id
              AND ENTITY_TYPE = 'SELLER'
        )
        BEGIN
            THROW 50004, 'Entity is not a seller', 1;
        END;


        -- 5. Prevent buyer = seller
        IF @buyer_entity_id = @seller_entity_id
        BEGIN
            THROW 50005, 'Buyer and seller cannot be the same entity', 1;
        END;


        -- 6. Check existing ACTIVE mapping
        IF EXISTS
        (
            SELECT 1
            FROM dbo.BUYER_SELLER_MAPPING
            WHERE BUYER_ENTITY_ID = @buyer_entity_id
              AND SELLER_ENTITY_ID = @seller_entity_id
              AND STATUS = 'ACTIVE'
        )
        BEGIN
            THROW 50006, 'Buyer and seller are already mapped', 1;
        END;


        -- 7. Check existing pending request
        IF EXISTS
        (
            SELECT 1
            FROM dbo.BUYER_SELLER_MAPPING_REQUEST
            WHERE BUYER_ENTITY_ID = @buyer_entity_id
              AND SELLER_ENTITY_ID = @seller_entity_id
              AND STATUS = 'PENDING_APPROVAL'
        )
        BEGIN
            THROW 50007, 'Mapping request is already pending', 1;
        END;


        -- 8. Create request
        INSERT INTO dbo.BUYER_SELLER_MAPPING_REQUEST
        (
            BUYER_ENTITY_ID,
            SELLER_ENTITY_ID,
            CREATED_BY
        )
        VALUES
        (
            @buyer_entity_id,
            @seller_entity_id,
            @created_by
        );


        -- 9. Return created request
        SELECT
            REQUEST_ID,
            BUYER_ENTITY_ID,
            SELLER_ENTITY_ID,
            STATUS,
            CREATED_AT,
            CREATED_BY
        FROM dbo.BUYER_SELLER_MAPPING_REQUEST
        WHERE REQUEST_ID = SCOPE_IDENTITY();

    END TRY

    BEGIN CATCH
        THROW;
    END CATCH
END
GO


