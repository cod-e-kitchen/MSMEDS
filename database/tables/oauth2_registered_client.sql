USE [MSMEDS]
GO

/****** Object:  Table [dbo].[oauth2_registered_client]    Script Date: 10/15/2026 4:58:45 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[oauth2_registered_client](
	[id] [varchar](100) NOT NULL,
	[client_id] [varchar](100) NOT NULL,
	[client_id_issued_at] [datetime2](3) NOT NULL,
	[client_secret] [varchar](200) NULL,
	[client_secret_expires_at] [datetime2](3) NULL,
	[client_name] [varchar](200) NOT NULL,
	[client_authentication_methods] [varchar](1000) NOT NULL,
	[authorization_grant_types] [varchar](1000) NOT NULL,
	[redirect_uris] [varchar](1000) NULL,
	[post_logout_redirect_uris] [varchar](1000) NULL,
	[scopes] [varchar](1000) NOT NULL,
	[client_settings] [varchar](2000) NOT NULL,
	[token_settings] [varchar](2000) NOT NULL,
 CONSTRAINT [PK_OAUTH2_REGISTERED_CLIENT] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO


