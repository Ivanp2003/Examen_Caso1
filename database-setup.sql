-- Script para crear la base de datos y tabla de usuarios
-- Ejecutar este script en MySQL antes de probar el login

-- Crear base de datos si no existe
CREATE DATABASE IF NOT EXISTS matriculas;

-- Usar la base de datos
USE matriculas;

-- Crear tabla de usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    clave VARCHAR(100) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insertar usuarios de prueba
INSERT INTO usuarios (nombre, email, clave) VALUES 
('Administrador', 'admin@test.com', '1234'),
('Estudiante Test', 'student@test.com', '1234'),
('Profesor Test', 'teacher@test.com', '1234')
ON DUPLICATE KEY UPDATE 
nombre = VALUES(nombre), 
clave = VALUES(clave);

-- Mostrar los usuarios insertados para verificación
SELECT * FROM usuarios;
