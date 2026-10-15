USE [MSMEDS]
GO

/****** Object:  Table [dbo].[oauth2_authorization_consent]    Script Date: 10/15/2026 4:58:54 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[oauth2_authorization_consent](
	[registered_client_id] [varchar](100) NOT NULL,
	[principal_name] [varchar](200) NOT NULL,
	[authorities] [varchar](1000) NOT NULL,
 CONSTRAINT [PK_OAUTH2_AUTHORIZATION_CONSENT] PRIMARY KEY CLUSTERED 
(
	[registered_client_id] ASC,
	[principal_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO


