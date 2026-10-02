USE [MSMEDS]
GO

/****** Object:  StoredProcedure [dbo].[SP_APPROVE_MAPPING_REQUEST]    Script Date: 10/2/2026 6:11:35 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE   PROCEDURE [dbo].[SP_APPROVE_MAPPING_REQUEST]
(
    @request_id BIGINT,
    @seller_entity_id BIGINT,
    @modified_by VARCHAR(100)
)
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY

        BEGIN TRANSACTION;


        -- 1. Find and lock the request
        IF NOT EXISTS
        (
            SELECT 1
            FROM dbo.BUYER_SELLER_MAPPING_REQUEST WITH (UPDLOCK, HOLDLOCK)
            WHERE REQUEST_ID = @request_id
              AND SELLER_ENTITY_ID = @seller_entity_id
              AND STATUS = 'PENDING_APPROVAL'
        )
        BEGIN
            THROW 50009, 'Mapping request not found or cannot be approved', 1;
        END;


        -- 2. Get buyer/seller from request
        DECLARE @buyer_id BIGINT;
        DECLARE @seller_id BIGINT;

        SELECT
            @buyer_id = BUYER_ENTITY_ID,
            @seller_id = SELLER_ENTITY_ID
        FROM dbo.BUYER_SELLER_MAPPING_REQUEST
        WHERE REQUEST_ID = @request_id;


        -- 3. Double-check active mapping
        IF EXISTS
        (
            SELECT 1
            FROM dbo.BUYER_SELLER_MAPPING
            WHERE BUYER_ENTITY_ID = @buyer_id
              AND SELLER_ENTITY_ID = @seller_id
              AND STATUS = 'ACTIVE'
        )
        BEGIN
            THROW 50010, 'Buyer and seller are already mapped', 1;
        END;


        -- 4. Update request
        UPDATE dbo.BUYER_SELLER_MAPPING_REQUEST
        SET
            STATUS = 'APPROVED',
            MODIFIED_AT = SYSUTCDATETIME(),
            MODIFIED_BY = @modified_by
        WHERE REQUEST_ID = @request_id;


        -- 5. Create active mapping
        INSERT INTO dbo.BUYER_SELLER_MAPPING
        (
            BUYER_ENTITY_ID,
            SELLER_ENTITY_ID,
            STATUS,
            CREATED_AT,
            CREATED_BY
        )
        VALUES
        (
            @buyer_id,
            @seller_id,
            'ACTIVE',
            SYSUTCDATETIME(),
            @modified_by
        );


        COMMIT TRANSACTION;


        -- 6. Return result
        SELECT
            @request_id AS REQUEST_ID,
            @buyer_id AS BUYER_ENTITY_ID,
            @seller_id AS SELLER_ENTITY_ID,
            'ACTIVE' AS STATUS;


    END TRY

    BEGIN CATCH

        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION;

        THROW;

    END CATCH
END
GO


