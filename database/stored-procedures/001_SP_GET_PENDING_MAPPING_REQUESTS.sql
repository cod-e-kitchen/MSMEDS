USE [MSMEDS]
GO

/****** Object:  StoredProcedure [dbo].[SP_GET_PENDING_MAPPING_REQUESTS]    Script Date: 10/2/2026 6:14:50 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE   PROCEDURE [dbo].[SP_GET_PENDING_MAPPING_REQUESTS]
(
    @seller_entity_id BIGINT
)
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY

        -- Validate seller exists
        IF NOT EXISTS
        (
            SELECT 1
            FROM dbo.ENTITY_MASTER
            WHERE ENTITY_ID = @seller_entity_id
              AND ENTITY_TYPE = 'SELLER'
        )
        BEGIN
            THROW 50008, 'Invalid seller entity', 1;
        END;


        SELECT
            REQUEST_ID,
            BUYER_ENTITY_ID,
            SELLER_ENTITY_ID,
            STATUS,
            CREATED_AT,
            CREATED_BY
        FROM dbo.BUYER_SELLER_MAPPING_REQUEST
        WHERE SELLER_ENTITY_ID = @seller_entity_id
          AND STATUS = 'PENDING_APPROVAL'
        ORDER BY CREATED_AT ASC;

    END TRY

    BEGIN CATCH
        THROW;
    END CATCH
END
GO


