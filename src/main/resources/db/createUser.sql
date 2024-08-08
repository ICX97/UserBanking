CREATE TABLE [dbo].[app_user] (
    [id] INT PRIMARY KEY IDENTITY(1,1),
    [first_name] NVARCHAR(50) NOT NULL,
    [last_name] NVARCHAR(50) NOT NULL,
    [email] NVARCHAR(100) NOT NULL UNIQUE,
    [phone_number] NVARCHAR(20),
    [created_at] DATETIME DEFAULT GETDATE()
);
