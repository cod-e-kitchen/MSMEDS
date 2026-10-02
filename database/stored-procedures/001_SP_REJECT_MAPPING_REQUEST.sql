USE [MSMEDS]
GO

/****** Object:  StoredProcedure [dbo].[SP_REJECT_MAPPING_REQUEST]    Script Date: 10/2/2026 6:15:29 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE   PROCEDURE [dbo].[SP_REJECT_MAPPING_REQUEST]
(
    @request_id BIGINT,
    @seller_entity_id BIGINT,
    @rejection_reason VARCHAR(500),
    @modified_by VARCHAR(100)
)
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY

        BEGIN TRANSACTION;


        -- 1. Verify request belongs to seller
        IF NOT EXISTS
        (
            SELECT 1
            FROM dbo.BUYER_SELLER_MAPPING_REQUEST WITH (UPDLOCK, HOLDLOCK)
            WHERE REQUEST_ID = @request_id
              AND SELLER_ENTITY_ID = @seller_entity_id
              AND STATUS = 'PENDING_APPROVAL'
        )
        BEGIN
            THROW 50011, 'Mapping request not found or cannot be rejected', 1;
        END;


        -- 2. Validate rejection reason
        IF @rejection_reason IS NULL
           OR LTRIM(RTRIM(@rejection_reason)) = ''
        BEGIN
            THROW 50012, 'Rejection reason is required', 1;
        END;


        -- 3. Reject request
        UPDATE dbo.BUYER_SELLER_MAPPING_REQUEST
        SET
            STATUS = 'REJECTED',
            MODIFIED_AT = SYSUTCDATETIME(),
            MODIFIED_BY = @modified_by,
            REJECTION_REASON = @rejection_reason
        WHERE REQUEST_ID = @request_id;


        COMMIT TRANSACTION;


        -- 4. Return result
        SELECT
            REQUEST_ID,
            BUYER_ENTITY_ID,
            SELLER_ENTITY_ID,
            STATUS,
            REJECTION_REASON,
            MODIFIED_AT,
            MODIFIED_BY
        FROM dbo.BUYER_SELLER_MAPPING_REQUEST
        WHERE REQUEST_ID = @request_id;


    END TRY

    BEGIN CATCH

        IF @@TRANCOUNT > 0
            ROLLBACK TRANSACTION;

        THROW;

    END CATCH
END
GO


