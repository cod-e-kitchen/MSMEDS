USE [MSMEDS]
GO

/****** Object:  Table [dbo].[oauth2_authorization]    Script Date: 10/15/2026 4:59:01 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[oauth2_authorization](
	[id] [varchar](100) NOT NULL,
	[registered_client_id] [varchar](100) NOT NULL,
	[principal_name] [varchar](200) NOT NULL,
	[authorization_grant_type] [varchar](100) NOT NULL,
	[authorized_scopes] [varchar](1000) NULL,
	[attributes] [nvarchar](max) NULL,
	[state] [varchar](500) NULL,
	[authorization_code_value] [nvarchar](max) NULL,
	[authorization_code_issued_at] [datetime2](3) NULL,
	[authorization_code_expires_at] [datetime2](3) NULL,
	[authorization_code_metadata] [nvarchar](max) NULL,
	[access_token_value] [nvarchar](max) NULL,
	[access_token_issued_at] [datetime2](3) NULL,
	[access_token_expires_at] [datetime2](3) NULL,
	[access_token_metadata] [nvarchar](max) NULL,
	[access_token_type] [varchar](100) NULL,
	[access_token_scopes] [varchar](1000) NULL,
	[oidc_id_token_value] [nvarchar](max) NULL,
	[oidc_id_token_issued_at] [datetime2](3) NULL,
	[oidc_id_token_expires_at] [datetime2](3) NULL,
	[oidc_id_token_metadata] [nvarchar](max) NULL,
	[refresh_token_value] [nvarchar](max) NULL,
	[refresh_token_issued_at] [datetime2](3) NULL,
	[refresh_token_expires_at] [datetime2](3) NULL,
	[refresh_token_metadata] [nvarchar](max) NULL,
	[user_code_value] [nvarchar](max) NULL,
	[user_code_issued_at] [datetime2](3) NULL,
	[user_code_expires_at] [datetime2](3) NULL,
	[user_code_metadata] [nvarchar](max) NULL,
	[device_code_value] [nvarchar](max) NULL,
	[device_code_issued_at] [datetime2](3) NULL,
	[device_code_expires_at] [datetime2](3) NULL,
	[device_code_metadata] [nvarchar](max) NULL,
 CONSTRAINT [PK_OAUTH2_AUTHORIZATION] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO


