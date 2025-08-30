CREATE TABLE [users] (
  [id_user] integer PRIMARY KEY IDENTITY(1, 1),
  [name] nvarchar(255),
  [username] nvarchar(255),
  [password] nvarchar(255),
  [role] int,
  [status] tinyint
)
GO

CREATE TABLE [customers] (
  [id_customer] integer PRIMARY KEY IDENTITY(1, 1),
  [name] nvarchar(255),
  [email] nvarchar(255),
  [birthday] date,
  [id_number] nvarchar(255),
  [nacionality] nvarchar(255),
  [status] tinyint
)
GO

CREATE TABLE [airplanes] (
  [id_airplane] integer PRIMARY KEY IDENTITY(1, 1),
  [tail_number] nvarchar(255),
  [seats_number] int,
  [status] nvarchar(255)
)
GO

CREATE TABLE [seat_types] (
  [id_seat_type] integer PRIMARY KEY IDENTITY(1, 1),
  [name] nvarchar(255),
  [status] tinyint
)
GO

CREATE TABLE [seats] (
  [id_seat] integer PRIMARY KEY IDENTITY(1, 1),
  [id_airplane] integer NOT NULL,
  [id_seat_type] integer NOT NULL,
  [row] nvarchar(255) NOT NULL,
  [number] integer NOT NULL
)
GO

CREATE TABLE [routes] (
  [id_route] integer PRIMARY KEY IDENTITY(1, 1),
  [origin] nvarchar(255),
  [destination] nvarchar(255),
  [status] tinyint
)
GO

CREATE TABLE [schedules] (
  [id_schedule] integer PRIMARY KEY IDENTITY(1, 1),
  [id_airplane] integer NOT NULL,
  [id_route] integer NOT NULL,
  [start_date] datetime,
  [end_date] datetime,
  [price] decimal(10,2),
  [status] tinyint
)
GO

CREATE TABLE [sales] (
  [id_sale] integer PRIMARY KEY IDENTITY(1, 1),
  [id_customer] integer NOT NULL,
  [id_user] integer NOT NULL,
  [amount] decimal(12,2),
  [registered] datetime NOT NULL,
  [status] tinyint
)
GO

CREATE TABLE [sale_details] (
  [id_sale_detail] integer PRIMARY KEY IDENTITY(1, 1),
  [id_sale] integer NOT NULL,
  [id_seat] integer NOT NULL,
  [id_schedule] integer NOT NULL,
  [subtotal] decimal(12,2),
  [passenger_name] varchar(200)
)
GO

CREATE UNIQUE INDEX [users_index_0] ON [users] ("username")
GO

CREATE UNIQUE INDEX [airplanes_index_1] ON [airplanes] ("tail_number")
GO

CREATE UNIQUE INDEX [seats_index_2] ON [seats] ("id_airplane", "row", "number")
GO

CREATE INDEX [seats_index_3] ON [seats] ("id_airplane")
GO

CREATE INDEX [seats_index_4] ON [seats] ("id_seat_type")
GO

ALTER TABLE [seats] ADD FOREIGN KEY ([id_airplane]) REFERENCES [airplanes] ([id_airplane])
GO

ALTER TABLE [seats] ADD FOREIGN KEY ([id_seat_type]) REFERENCES [seat_types] ([id_seat_type])
GO

ALTER TABLE [schedules] ADD FOREIGN KEY ([id_airplane]) REFERENCES [airplanes] ([id_airplane])
GO

ALTER TABLE [schedules] ADD FOREIGN KEY ([id_route]) REFERENCES [routes] ([id_route])
GO

ALTER TABLE [sales] ADD FOREIGN KEY ([id_customer]) REFERENCES [customers] ([id_customer])
GO

ALTER TABLE [sales] ADD FOREIGN KEY ([id_user]) REFERENCES [users] ([id_user])
GO

ALTER TABLE [sale_details] ADD FOREIGN KEY ([id_sale]) REFERENCES [sales] ([id_sale])
GO

ALTER TABLE [sale_details] ADD FOREIGN KEY ([id_seat]) REFERENCES [seats] ([id_seat])
GO

ALTER TABLE [sale_details] ADD FOREIGN KEY ([id_schedule]) REFERENCES [schedules] ([id_schedule])
GO
