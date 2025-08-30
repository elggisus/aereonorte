-- Script para crear la tabla airplanes
-- Ejecutar en SQL Server

CREATE TABLE [airplanes] (
  [id_airplane] integer PRIMARY KEY IDENTITY(1, 1),
  [tail_number] nvarchar(255),
  [seats_number] int,
  [status] nvarchar(255)
)
GO

-- Crear índice único para el número de cola
CREATE UNIQUE INDEX [airplanes_index_1] ON [airplanes] ("tail_number")
GO

-- Insertar algunos datos de ejemplo
INSERT INTO [airplanes] ([tail_number], [seats_number], [status]) VALUES 
('N12345', 150, 'ENABLED'),
('N67890', 200, 'ENABLED'),
('N11111', 120, 'MAINTENANCE')
GO

-- Verificar que se creó correctamente
SELECT * FROM [airplanes]
GO
