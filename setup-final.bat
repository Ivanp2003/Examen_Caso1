@echo off
title Configurar Base de Datos - Final
color 0B

echo ====================================
echo    CONFIGURANDO BASE DE DATOS
echo ====================================
echo.

echo [1/2] Verificando MySQL...
netstat -an | findstr :3306 > nul
if %errorlevel% neq 0 (
    echo [ERROR] MySQL no está corriendo en puerto 3306
    echo Por favor inicia MySQL antes de continuar
    echo.
    echo Puedes iniciar MySQL desde:
    echo - Services.msc -> MySQL94
    echo - O desde MySQL Workbench
    echo.
    pause
    exit /b 1
)

echo [2/2] Ejecutando script SQL...
echo.

REM Ejecutar el archivo SQL temporal
"C:\Program Files\MySQL\MySQL Server 9.4\bin\mysql.exe" -u root -p1234 < temp_setup.sql

if %errorlevel% equ 0 (
    echo.
    echo ✅ Base de datos configurada exitosamente!
    echo.
    echo Usuarios creados:
    echo - admin@test.com / 1234
    echo - student@test.com / 1234
    echo - teacher@test.com / 1234
    echo.
    echo Ahora puedes iniciar el servidor con: run-simple.bat
    echo.
    echo Luego prueba en Thunder Client:
    echo POST http://localhost:8080/backend-api/api/login
    echo Body: correo=admin@test.com&clave=1234
    echo.
) else (
    echo.
    echo ❌ Error al configurar la base de datos
    echo.
    echo Verifica:
    echo 1. Que MySQL esté corriendo
    echo 2. Que la contraseña sea '1234'
    echo 3. Que el servicio MySQL94 esté activo
    echo.
    echo Si la contraseña es diferente, edita este archivo
    echo y cambia -p1234 por -p[tu_contraseña]
    echo.
)

REM Limpiar archivo temporal
del temp_setup.sql 2>nul

pause
