CREATE TABLE [dbo].[Account] (
    [id] INT PRIMARY KEY IDENTITY(1,1),
    [account_number] NVARCHAR(20) NOT NULL UNIQUE,
    [balance] DECIMAL(18, 2) NOT NULL CHECK (balance >= 0),
    [user_id] INT NOT NULL,
    [created_at] DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_User_Account FOREIGN KEY (user_id) REFERENCES [dbo].[app_user](id)
);